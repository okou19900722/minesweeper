package com.mine.dto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mine.config.GameConfig;
import com.mine.handle.PlayerHandle;
import com.mine.ui.Mine;
/**
 * 游戏数据对象
 * @author 百度java吧@okou19900722
 */
public class GameDto {

	/**
	 * 游戏数据记录地图
	 */
	private Mine[][] backGroundMap;
	
	/**
	 * 用户点击后的记录地图
	 */
	private Mine[][] foreGroundmap;
	
	/**
	 * 插旗的数量
	 */
	private int tip = 0;
	
	/**
	 * 随机数生成器
	 * @see java.util.Random
	 */
	private static Random random = new Random();
	/**
	 * 玩家控制器，实现MouseAdapter
	 * @see java.awt.event.MouseAdapter
	 */
	private PlayerHandle playerHandle;
	/**
	 * 记录时间
	 */
	private int nowTime = 0;
	
	/**
	 * 游戏开始开关
	 */
	private boolean start;
	
	public GameDto(PlayerHandle handle){
		this.playerHandle = handle;
		this.initDto();
		
	}
	/**
	 * 记录游戏所有的点，在生成雷的时候，将生成的雷从这些点中取出，再从这些点中随机
	 */
	private List<Point> points;
	/**
	 * 初始化游戏数据
	 */
	public void initDto(){
		//获取游戏等级
		Level level = GameConfig.getSystemConfig().getLevel();
		//标记的雷数重置为0
		this.tip = 0;
		//当前时间重置为0
		this.nowTime = 0;
		//游戏开关关闭
		this.start = false;
		//重新生成游戏数组
		this.backGroundMap = new Mine[level.getGameWidth()][level.getGameHeight()];
		this.foreGroundmap = new Mine[level.getGameWidth()][level.getGameHeight()];
		//重新生成点集合
		this.points = new ArrayList<Point>(level.getGameWidth() * level.getGameHeight());
		//初始化显示地图(未点击状态)
		this.initMap(level);
	}
	/**
	 * 当游戏开始，点击后，生成地图，保证点第一下为雷的机率降低
	 * @param m
	 */
	public void onstart(Mine m){
		//获取游戏等级
		Level level = GameConfig.getSystemConfig().getLevel();
		//生成1到9的数，看是否为雷(9)，可通过调节此处设置点第一下中雷的机率
		int clickType = random.nextInt(9) + 1;
		//当前等级最大雷数
		int mineNum = level.getMineNum();
		//计算点击的点在point集合中的索引并删除
		int index = m.getP().x * level.getGameHeight() + m.getP().y;
		this.points.remove(index);
		//如果当前点击的方块生成的为雷，将地图设置为雷，并将雷数减1
		if(clickType == Mine.MINE){
			this.backGroundMap[m.getP().x][m.getP().y] = new Mine(Mine.MINE,m.getP(),null);
			mineNum -= 1;
		}
		//循环生成雷的位置，并将雷所在方块的点从集合中删除
		for(int i=0;i<mineNum;i++){
			int idx = random.nextInt(this.points.size());
			Point p = points.get(idx);
			this.backGroundMap[p.x][p.y] = new Mine(Mine.MINE,p,null);
			this.points.remove(p);
		}
		//循环检测未生成的方块并生成对应的数字
		for(int i=0,w=level.getGameWidth();i<w;i++){
			for (int j = 0, h = level.getGameHeight(); j < h; j++) {
				int num = 0;
				if (null == this.backGroundMap[i][j]) {
					// 左上(i-1,j-1)
					if (i > 0 && j > 0 && (this.backGroundMap[i - 1][j - 1] !=null && Mine.MINE==this.backGroundMap[i - 1][j - 1].getType())) {
						num++;
					}
					// 正上(i,j-1)
					if (j > 0 && (this.backGroundMap[i][j - 1] != null && Mine.MINE==this.backGroundMap[i][j - 1].getType())) {
						num++;
					}
					// 右上(i+1,j-1)
					if (i < w - 1 && j > 0 && (this.backGroundMap[i + 1][j - 1]!=null && Mine.MINE==this.backGroundMap[i + 1][j - 1].getType())) {
						num++;
					}
					// 正左(i-1,j)
					if (i > 0 && (this.backGroundMap[i - 1][j] != null && Mine.MINE==this.backGroundMap[i - 1][j].getType())) {
						num++;
					}
					// 正右(i+1,j)
					if (i < w - 1 && (this.backGroundMap[i + 1][j] != null && Mine.MINE==this.backGroundMap[i + 1][j].getType())) {
						num++;
					}
					// 左下(i-1,j+1)
					if (i > 0 && j < h - 1 && (this.backGroundMap[i - 1][j + 1] != null && Mine.MINE==this.backGroundMap[i - 1][j + 1].getType())) {
						num++;
					}
					// 正下(i,j+1)
					if (j < h - 1 && (this.backGroundMap[i][j + 1] != null && Mine.MINE==this.backGroundMap[i][j + 1].getType())) {
						num++;
					}
					// 右下(i+1,j+1)
					if (i < w - 1 && j < h - 1 && (this.backGroundMap[i + 1][j + 1] != null && Mine.MINE==this.backGroundMap[i + 1][j + 1].getType())) {
						num++;
					}
					this.backGroundMap[i][j] = new Mine(num,new Point(i,j),null);
				}
			}
		}
	}
	/**
	 * 将显示的地图全部重置为初始状态，将并所有点加入点集合
	 */
	private void initMap(Level level){
		for(int i=0,w=level.getGameWidth();i<w;i++){
			for (int j = 0, h = level.getGameHeight(); j < h; j++) {
				this.foreGroundmap[i][j] = new Mine(Mine.INITIAL,new Point(i,j),this.playerHandle);
				this.points.add(new Point(i, j));
			}
		}
	}

	public Mine[][] getBackGroundMap() {
		return this.backGroundMap;
	}

	public void setBackGroundMap(Mine[][] backGroundMap) {
		this.backGroundMap = backGroundMap;
	}

	public Mine[][] getForeGroundmap() {
		return this.foreGroundmap;
	}

	public void setForeGroundmap(Mine[][] foreGroundmap) {
		this.foreGroundmap = foreGroundmap;
	}

	public int getTip() {
		return this.tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public int getNowTime() {
		return this.nowTime;
	}
	public void setNowTime(int nowTime){
		//最大为999
		if(nowTime>999)nowTime = 999;
		this.nowTime  = nowTime;
	}

	public boolean isStart() {
		return this.start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}
}
