package com.mine.handle;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import com.mine.config.GameConfig;
import com.mine.dto.GameDto;
import com.mine.dto.Level;
import com.mine.dto.Player;
import com.mine.string.Img;
import com.mine.string.Path;
import com.mine.ui.FacePanel;
import com.mine.ui.GamePanel;
import com.mine.ui.Mine;
import com.mine.ui.window.CustomFrame;
import com.mine.ui.window.GameFrame;
import com.mine.ui.window.LeaderboardFrame;
import com.mine.ui.window.RecordFrame;
import com.mine.util.Audio;

/**
 * 游戏逻辑控制器
 * @author 百度java吧@okou19900722
 *
 */
public class GameHandle {

	/**
	 * 游戏数据
	 */
	private GameDto dto;
	/**
	 * 游戏主区域(方块区域)
	 */
	private GamePanel gamePanel;
	/**
	 * 难度自定义窗口
	 */
	private CustomFrame customFrame;
	/**
	 * 英雄榜窗口
	 */
	private LeaderboardFrame leaderboardFrame;
	/**
	 * 破记录窗口
	 */
	private RecordFrame recordFrame;
	/**
	 * 游戏窗口
	 */
	private GameFrame gameFrame;
	/**
	 * 玩家制件器
	 */
	private PlayerHandle playerHandle;
	/**
	 * 定时器线程
	 */
	private TimerThread timer;
	/**
	 * 标记笑脸按下前的状态，等鼠标移开时还原
	 */
	private int oldType = 0;
	/**
	 * 标记笑脸鼠标是否在笑脸上松开
	 * 当鼠标按下笑脸时，设置为true，否则忽略鼠标移动到笑脸上面并移开
	 */
	private boolean inFacePanel = false;
	
	/**
	 * 鼠标左键点击
	 */
	private boolean isLeftButtonClick = false;
	/**
	 * 鼠标右键点击
	 */
	private boolean isRightButtonClick = false;
	/**
	 * 记录鼠标移动到的方块
	 */
	private Mine mouseOnMine;
	
	public GameHandle() {

		//初始化玩家控制器
		this.playerHandle = new PlayerHandle(this);
		//初始化游戏数据
		this.dto = new GameDto(this.playerHandle);
		//初始化游戏面板
		this.gamePanel = new GamePanel(this.dto,this,this.playerHandle);
		//游戏面板增加监听
		this.gamePanel.addMouseListener(this.playerHandle);
		//初始化游戏窗口
		this.gameFrame = new GameFrame(this.gamePanel,this);
		//初始化自定义窗口
		this.customFrame = new CustomFrame(this.gameFrame,this);
		//初始化英雄榜窗口
		this.leaderboardFrame = new LeaderboardFrame(this.gameFrame);
		//初始化记录窗口
		this.recordFrame = new RecordFrame(this,this.gameFrame);
	}
	/**
	 * 处理鼠标按下事件
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		//事件对象
		Object obj = e.getSource();
		//鼠标按下的键
		int keyButton = e.getButton();
		if(keyButton == MouseEvent.BUTTON1){
			//设置左键按下
			this.isLeftButtonClick = true;
			//鼠标按下笑脸
			if(obj instanceof FacePanel){
				//鼠标当前在笑脸上方
				this.inFacePanel = true;
				//记录原始状态，如果鼠标移开笑脸，还原为原始状态
				this.oldType = this.getFace();
				//设置笑脸为按下状态
				this.setFace(FacePanel.PRESS);
			}else{
				//如果点击除笑脸外的其他面板，笑脸都为点击状态
				if(this.getFace()==FacePanel.DEFAULT){
					this.setFace(FacePanel.CLICK);
				}
				if (obj instanceof Mine && !this.isRightButtonClick) {
					Mine m = (Mine) obj;
					// 如果当前状态是初始状态，或者问号，且右键没有按下(不是同时按下双键)，设置为对应按下状态
					if (m.getType() == Mine.INITIAL) {
						m.setType(Mine.PRESS);
					} else if (m.getType() == Mine.TIP_UNCERTAIN) {
						m.setType(Mine.TIP_UNCERTAIN_PRESS);
					}
				}
			}
		}else if(keyButton==MouseEvent.BUTTON3){
			//设置右键按下
			this.isRightButtonClick = true;
			if(obj instanceof Mine && !this.isLeftButtonClick){
				Mine m = (Mine)obj;
				onTip(m);
			}else if(this.isLeftButtonClick){
				//如果点击除笑脸外的其他面板，笑脸都为点击状态
				if(this.getFace()==FacePanel.DEFAULT){
					this.setFace(FacePanel.CLICK);
				}
			}
		}
		//如果左右键同时按下，显示周围(不点开)
		if(this.isLeftButtonClick && this.isRightButtonClick && this.mouseOnMine != null){
			showRound(this.mouseOnMine);
		}
		//重画游戏面板
		this.gameFrame.repaint();
	}
	/**
	 * 处理鼠标松开事件
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		//松开时，先将笑脸设置为默认
		this.setFace(FacePanel.DEFAULT);
		int keyButton = e.getButton();
		//如果点击笑脸后在笑脸上松开，游戏重新开始，笑脸状态重置
		if(this.inFacePanel){
			this.reStart();
		}
		//由于鼠标移开，松开的时候，就不用管事件是谁触发的了，直接根据鼠标移动到的方块来执行
		if(this.mouseOnMine!=null){
			if(this.isLeftButtonClick && this.isRightButtonClick){
				//松开鼠标之前是双点击，将周围的按下状态还原
				resetRound(this.mouseOnMine);
				//然后检测周围是否可以点开
				checkRound(this.mouseOnMine);
			}else if(keyButton==MouseEvent.BUTTON1){
				//如果松开的为左键，而且前面的if肯定未进入，右键肯定未点击
				// 如果当前状态标识游戏未开始，那么游戏开始
				if (!this.dto.isStart()) {
					this.start(this.mouseOnMine);
				}
				// 如果鼠标下的方块为按下状态(普通按下或者标记<?>按下)，点开方块
				if (this.mouseOnMine.getType() == Mine.PRESS) {
					this.mouseOnMine.setType(Mine.INITIAL);
					open(this.mouseOnMine, 0);
				} else if (this.mouseOnMine.getType() == Mine.TIP_UNCERTAIN_PRESS) {
					this.mouseOnMine.setType(Mine.TIP_UNCERTAIN);
					open(this.mouseOnMine, 0);
				}
			}
		}
		//事件触发完后，再将点击设置为未点击
		if (keyButton == MouseEvent.BUTTON1) {
			this.isLeftButtonClick = false;
		}
		else if(keyButton == MouseEvent.BUTTON3){
			this.isRightButtonClick = false;
		}
		//重绘
		this.gameFrame.repaint();
	}
	/**
	 * 处理鼠标离开控件事件
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		Object obj = e.getSource();
		//如果按下笑脸之后离开笑脸
		if (this.inFacePanel && obj instanceof FacePanel) {
			// 当鼠标离开时，如果笑脸是按下状态，还原;
			if (this.getFace() == FacePanel.PRESS) {
				this.setFace(this.oldType);
				this.gameFrame.repaint();
			}
			//设置鼠标离开笑脸
			this.inFacePanel = false;
		}else if(obj instanceof Mine){
			this.mouseOnMine = null;
			Mine m = (Mine) obj;
			if (this.isLeftButtonClick) {
				if (m.getType() == Mine.PRESS) {
					m.setType(Mine.INITIAL);
					this.gameFrame.repaint();
				}
			}
			if(this.isLeftButtonClick && this.isRightButtonClick){
				this.resetRound(m);
				this.gameFrame.repaint();
			}
			
		}
	}
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();
		if(obj instanceof Mine){
			Mine m = (Mine) obj;
			this.mouseOnMine = m;
			if (this.isLeftButtonClick) {
				if (m.getType() == Mine.INITIAL) {
					m.setType(Mine.PRESS);
					this.gameFrame.repaint();
				}
			}
			if(this.isLeftButtonClick && this.isRightButtonClick){
				this.showRound(m);
				this.gameFrame.repaint();
			}
		}
	}
	public void showCustomWindow() {
		this.customFrame.setLocation(this.gameFrame.getX() + 3, this.gameFrame.getY() + 49);
		this.customFrame.setVisible(true);
	}
	public void showLeaderboardFrame() {
		this.leaderboardFrame.setLocation(this.gameFrame.getX() + 3, this.gameFrame.getY() + 91);
		this.leaderboardFrame.setVisible(true);
	}
	public void showRewardFrame() {
		this.recordFrame.setLocation(this.gameFrame.getX() + 3, this.gameFrame.getY() + 91);
		this.recordFrame.setVisible(true);
	}

	public void setLevel(Level level){
		GameConfig.getSystemConfig().setLevel(level);
		GameConfig.save();
		this.reStart();
	}
	
	/**
	 * 
	 */
	public void changeQuestionMark(boolean selected){
		GameConfig.getSystemConfig().setShowQuestionMark(selected);
		GameConfig.save();
	}
	/**
	 * 切换皮肤
	 * @param selected
	 */
	public void changeSkin(boolean selected) {
		GameConfig.getSystemConfig().setShowColor(selected);
		GameConfig.save();
		Path.setSkin();
		this.gameFrame.repaint();
	}
	
	public void changeSound(boolean selected){
		GameConfig.getSystemConfig().setPlayerSound(selected);
		GameConfig.save();
	}
	
	public void reStart(){
		this.setFace(FacePanel.DEFAULT);
		List<MouseListener> list = Arrays.asList(this.gamePanel.getMouseListeners());
		if(!list.contains(this.playerHandle)){
			this.gamePanel.addMouseListener(this.playerHandle);
		}
		this.isLeftButtonClick = false;
		this.isRightButtonClick = false;
		this.mouseOnMine = null;
		this.dto.initDto();
		this.gamePanel.createMap();
		this.gameFrame.resetSize();
		this.gameFrame.requestFocus();
		this.gameFrame.repaint();
	}	
	/**
	 * 退出
	 */
	public void exit() {
		System.exit(0);
		
	}

	public void setWindowSize(GameFrame frame, int h) {
		//gamePanel的距离左上角距离
		int x = 3;
		int y = 4;
		
		Insets i = frame.getInsets();
		int gameWidth = GameConfig.getSystemConfig().getLevel().getGameWidth() * Img.PIECE_W;
		int gameHeight = GameConfig.getSystemConfig().getLevel().getGameHeight() * Img.PIECE_H;
		int width = gameWidth + i.left + i.right + Img.MAP_BOX_BORDER * 2 + 9 + 5 + x;
		int height = gameHeight + i.top + i.bottom + Img.MAP_BOX_BORDER * 2 + 51 + h + 5 + y;
		frame.setSize(width , height );
		this.gamePanel.setBounds(x,y,width - i.left - i.right - x, height - i.top - i.bottom - y);
	}
	
	public void saveRecord(String name){
		Player p = new Player(name, this.dto.getNowTime());
		GameConfig.getSystemConfig().getRecords()[GameConfig.getSystemConfig().getLevel().getLevelId()] = p;
		GameConfig.save();
		this.showLeaderboardFrame();
	}
	
	private void checkEnd() {
		Mine[][] backMap = this.dto.getBackGroundMap();
		Mine[][] foreMap = this.dto.getForeGroundmap();
		Level level = GameConfig.getSystemConfig().getLevel();
		boolean finish = true;
		outside:for (int i = 0, w = level.getGameWidth(); i < w; i++) {
			for (int j = 0, h = level.getGameHeight(); j < h; j++) {
				//只要有一个方块不是雷，且还是初始状态，游戏没完成
				if(foreMap[i][j].getType()==Mine.INITIAL && backMap[i][j].getType()!=Mine.MINE){
					finish = false;
					break outside;
				}
			}
		}
		//如果游戏完成，将所有雷插旗
		if(finish){
			for (int i = 0, w = level.getGameWidth(); i < w; i++) {
				for (int j = 0, h = level.getGameHeight(); j < h; j++) {
					foreMap[i][j].removeMouseListener(this.playerHandle);
					if(backMap[i][j].getType()==Mine.MINE){
						foreMap[i][j].setType(Mine.TIP_MINE);
					}
				}
			}
			this.setFace(FacePanel.WIN);
			if(GameConfig.getSystemConfig().isPlaySound()){
				//播放胜利音乐
				Audio.play(Path.WIN_PATH);
			}
			this.end();
			//将所有雷都设为村记
			this.dto.setTip(GameConfig.getSystemConfig().getLevel().getMineNum());
			//如果难度不是自定义，且当前完成时间比记录时间短，弹出输入面板记录用户姓名
			if(GameConfig.getSystemConfig().getLevel().getLevelId()!=3 && this.dto.getNowTime() < GameConfig.getSystemConfig().getRecords()[GameConfig.getSystemConfig().getLevel().getLevelId()].getTime()){
				this.showRewardFrame();
			}
		}
	}
	/**
	 * 检测周围是否可开
	 * @param m
	 */
	private void checkRound(Mine m){
		//如果鼠标下的方块为数字，检测周围是否有足够的雷
		if(m.getType() >= Mine.ONE && m.getType() <= Mine.EIGHT){
			int i = m.getP().x;
			int j = m.getP().y;
			int w = GameConfig.getSystemConfig().getLevel().getGameWidth();
			int h = GameConfig.getSystemConfig().getLevel().getGameHeight();
			Mine[][] foreGroundMap = this.dto.getForeGroundmap();
			int mineTipNum = 0;
			// 左上(i-1,j-1)
			if (i > 0 && j > 0 && Mine.TIP_MINE==foreGroundMap[i - 1][j - 1].getType()) {
				mineTipNum++;
			}
			// 正上(i,j-1)
			if (j > 0 && Mine.TIP_MINE==foreGroundMap[i][j - 1].getType()) {
				mineTipNum++;
			}
			// 右上(i+1,j-1)
			if (i < w - 1 && j > 0 && Mine.TIP_MINE==foreGroundMap[i + 1][j - 1].getType()) {
				mineTipNum++;
			}
			// 正左(i-1,j)
			if (i > 0 && Mine.TIP_MINE==foreGroundMap[i - 1][j].getType()) {
				mineTipNum++;
			}
			// 正右(i+1,j)
			if (i < w - 1  && Mine.TIP_MINE==foreGroundMap[i + 1][j].getType()) {
				mineTipNum++;
			}
			// 左下(i-1,j+1)
			if (i > 0 && j < h - 1 && Mine.TIP_MINE==foreGroundMap[i - 1][j + 1].getType()) {
				mineTipNum++;
			}
			// 正下(i,j+1)
			if (j < h - 1 && Mine.TIP_MINE==foreGroundMap[i][j + 1].getType()) {
				mineTipNum++;
			}
			// 右下(i+1,j+1)
			if (i < w - 1 && j < h - 1 && Mine.TIP_MINE==foreGroundMap[i + 1][j + 1].getType()) {
				mineTipNum++;
			}
			if(mineTipNum == m.getType()){
				this.openAround(m,0);
			}
		
		}
	}
	/**
	 * 左右键同时按下时，将周围
	 * @param m
	 */
	private void showRound(Mine m) {
		if(m.getType() == Mine.INITIAL){
			m.setType(Mine.PRESS);
		}else if(m.getType() == Mine.TIP_UNCERTAIN){
			m.setType(Mine.TIP_UNCERTAIN_PRESS);
		}
		this.changeRoundType(m);
	}
	private void resetRound(Mine m){
		if(m.getType() == Mine.PRESS){
			m.setType(Mine.INITIAL);
		}else if(m.getType() == Mine.TIP_UNCERTAIN_PRESS){
			m.setType(Mine.TIP_UNCERTAIN);
		}
		this.changeRoundType(m);
	}
	/**
	 * 
	 * @param m
	 * @param except 排除的方位
	 * 
	 * 1 2 3
	 * 4 5 6
	 * 7 8 9
	 * 用1-9给3x3的区域标记，需要打开的方块为5，根据except判断不检测的方向
	 */
	private void openAround(Mine m,int except){
		int i = m.getP().x;
		int j = m.getP().y;
		int w = GameConfig.getSystemConfig().getLevel().getGameWidth();
		int h = GameConfig.getSystemConfig().getLevel().getGameHeight();
		Mine[][] fore = this.dto.getForeGroundmap();
		//如果不排除左上，检测左上(i-1,j-1)
		if(except != 1){
			if (i > 0 && j > 0) {
				this.open(fore[i-1][j-1],9);
			}
		}
		//如果不排除正上，检测正上(i,j-1)
		if(except != 2){
			if (j > 0 ) {
				this.open(fore[i][j-1],8);
			}
		}
		//如果不排除右上，检测右上(i+1,j-1)
		if(except != 3){
			if (i < w - 1 && j > 0  ) {
				this.open(fore[i+1][j-1],7);
			}
		}
		//如果不排除正左，检测正左(i-1,j)
		if(except != 4){
			if (i > 0  ) {
				this.open(fore[i-1][j],6);
			}
		}
		//如果不排除正右，检测正右(i+1,j)
		if(except != 6){
			if (i < w - 1  ) {
				this.open(fore[i+1][j],4);
			}
		}
		//如果不排除左下，检测左下(i-1,j+1)
		if(except != 7){
			if (i > 0 && j < h - 1  ) {
				this.open(fore[i-1][j+1],3);
			}
		}
		//如果不排除正下，检测正下(i,j+1)
		if(except != 8){
			if (j < h - 1  ) {
				this.open(fore[i][j+1],2);
			}
		}
		//如果不排除右下，检测右下(i+1,j+1)
		if(except != 9){
			if (i < w - 1 && j < h - 1 ) {
				this.open(fore[i+1][j+1],1);
			}
		}
	}

	private void changeRoundType(Mine m){
		int i = m.getP().x;
		int j = m.getP().y;
		int w = GameConfig.getSystemConfig().getLevel().getGameWidth();
		int h = GameConfig.getSystemConfig().getLevel().getGameHeight();
		Mine[][] fore = this.dto.getForeGroundmap();
		//如果周围的方块不在游戏区域外，且符合转变条件(初始状态或者按下状态)，改变
		// 左上(i-1,j-1)
		if (i > 0 && j > 0) {
			this.changeType(fore[i-1][j-1]);
		}
		// 正上(i,j-1)
		if (j > 0 ) {
			this.changeType(fore[i][j-1]);
		}
		// 右上(i+1,j-1)
		if (i < w - 1 && j > 0) {
			this.changeType(fore[i+1][j-1]);
		}
		// 正左(i-1,j)
		if (i > 0  ) {
			this.changeType(fore[i-1][j]);
		}
		// 正右(i+1,j)
		if (i < w - 1 ) {
			this.changeType(fore[i+1][j]);
		}
		// 左下(i-1,j+1)
		if (i > 0 && j < h - 1) {
			this.changeType(fore[i-1][j+1]);
			}
		// 正下(i,j+1)
		if (j < h - 1) {
			this.changeType(fore[i][j+1]);
			}
		// 右下(i+1,j+1)
		if (i < w - 1 && j < h - 1) {
			this.changeType(fore[i + 1][j + 1]);
		}
	}
	/**
		 * 将指定方块打开
		 * @param m 需要打开的方块
		 * @param except 排除的方位
		 * 
		 * 1 2 3
		 * 4 5 6
		 * 7 8 9
		 * 用1-9给3x3的区域标记，需要打开的方块为5，根据except判断openAround时，不检测的方向
		 */
		private void open(Mine m,int except) {
			Mine[][] map = this.dto.getBackGroundMap();
			// 用于循环时，判断
			int mapType = map[m.getP().x][m.getP().y].getType();
			if(m.getType() == Mine.INITIAL || m.getType() == Mine.TIP_UNCERTAIN){
				if (mapType == Mine.MINE) {
					m.setType(Mine.MINE_HIT);
					this.gameFailed();
				} else {
					m.setType(mapType);
					if (mapType == Mine.EMPTY) {
						this.openAround(m,except);
					}
					this.checkEnd();
				}
			}
		}
	private void changeType(Mine m){
		int type = m.getType();
		//如果不符合转变条件(初始状态或者按下状态)，不改变
		boolean change = true;
		if(type == Mine.INITIAL){
			type = Mine.PRESS;
		}else if(type == Mine.PRESS){
			type = Mine.INITIAL;
		}else if(type == Mine.TIP_UNCERTAIN){
			type = Mine.TIP_UNCERTAIN_PRESS;
		}else if(type == Mine.TIP_UNCERTAIN_PRESS){
			type = Mine.TIP_UNCERTAIN;
		}else{
			change = false;
		}
		if(change){
			m.setType(type);
		}
	}
	/**
	 * 游戏失败，显示雷的正确位置和错误标记
	 */
	private void gameFailed() {
		Mine[][] backMap = this.dto.getBackGroundMap();
		Mine[][] foreMap = this.dto.getForeGroundmap();
		Level level = GameConfig.getSystemConfig().getLevel();
		for (int i = 0, w = level.getGameWidth(); i < w; i++) {
			for (int j = 0, h = level.getGameHeight(); j < h; j++) {
				foreMap[i][j].removeMouseListener(this.playerHandle);
				if(foreMap[i][j].getType()==Mine.TIP_MINE && backMap[i][j].getType()!=Mine.MINE){
					//如果标记为雷，但实际不为雷，显示错误标记
					foreMap[i][j].setType(Mine.WRONG_TIP);
				}else if(backMap[i][j].getType()==Mine.MINE && foreMap[i][j].getType()!=Mine.TIP_MINE && foreMap[i][j].getType() != Mine.MINE_HIT){
					//如果为雷，显示
					foreMap[i][j].setType(Mine.MINE);
				}
			}
		}
		this.setFace(FacePanel.CRY);
		if(GameConfig.getSystemConfig().isPlaySound()){
			//播放失败音乐
			Audio.play(Path.LOSE_PATH);
		}
		this.end();
	}
	/**
	 * 
	 * @param m
	 */
	private void start(Mine m){
		this.dto.setStart(true);
		this.dto.onstart(m);
		this.timer = new TimerThread();
		this.timer.start();
	}
	/**
	 * 游戏结束执行(包括赢和输)
	 */
	private void end(){
		//游戏结束将鼠标下的方块置空，因为游戏结束，会将监听删除，导致出错
		this.mouseOnMine = null;
		this.gamePanel.removeMouseListener(this.playerHandle);
		this.dto.setStart(false);
	}
	/**
	 * 获取笑脸状态
	 * @return
	 */
	private int getFace(){
		return this.gamePanel.getFace();
	}
	/**
	 * 设置笑脸状态
	 * @param type
	 */
	private void setFace(int type){
		this.gamePanel.setFace(type);
	}

	private void onTip(Mine m){
		// 右键
		if (m.getType() == Mine.INITIAL) {
			//如果是初始状态，标记雷，并将标记数+1
			this.tip();
			//设置右键点击方块为标记<雷>状态
			m.setType(Mine.TIP_MINE);
		} else {
			//如果是标记着小红旗，则将标记数-1，并判断是否显示问号
			if(m.getType() == Mine.TIP_MINE){
				this.unTip();
				if(GameConfig.getSystemConfig().isShowQuestionMark()){
					m.setType(Mine.TIP_UNCERTAIN);
				}else{
					m.setType(Mine.INITIAL);
				}
			}else{
				//如果显示问号标记，直接设置为初始状态
				if(m.getType() == Mine.TIP_UNCERTAIN){
					m.setType(Mine.INITIAL);
				}
			}
		}
	}
	
	/**
	 * 标记地雷增加标记数量
	 */
	private void tip(){
		this.dto.setTip(this.dto.getTip() + 1);
	}
	/**
	 * 取消标记地雷减少标记数量
	 */
	private void unTip(){
		this.dto.setTip(this.dto.getTip() - 1);
	}
	/**
	 * 记录线程ID，每次起动+1，防止快速重新开始导致多条线程同时运行！
	 */
	private int threadId = 0;
	/**
	 * 定时器线程，每一秒将游戏所消耗的时间加1，游戏结束，线程停止
	 * @author 百度java吧@okou19900722
	 */
	private class TimerThread extends Thread{
		@Override
		public void run() {
			int i = -1;
			threadId++;
			while (true) {
				try {
					if(i==-1){
						i = threadId;
					}
					Thread.sleep(1000);
					if (!dto.isStart() || i!=threadId) {
						break;
					}
					
					dto.setNowTime(dto.getNowTime() + 1);
					gameFrame.repaint();
					if (GameConfig.getSystemConfig().isPlaySound()) {
						//播放心跳声音
						Audio.play(Path.IDLE_PATH);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
