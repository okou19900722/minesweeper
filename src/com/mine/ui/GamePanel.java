package com.mine.ui;


import java.awt.Color;

import javax.swing.JPanel;

import com.mine.config.GameConfig;
import com.mine.dto.GameDto;
import com.mine.dto.Level;
import com.mine.handle.GameHandle;
import com.mine.handle.PlayerHandle;
import com.mine.string.Img;

/**
 * 游戏面板
 * @author 百度java吧@okou19900722
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = -1358971785690366548L;
	
	
	private GameDto dto;
	private BoxPanel gameBoxPanel;
	private BoxPanel topBoxPanel;
	private BoxPanel mineNumBoxPanel;
	private BoxPanel timerBoxPanel;
	private FacePanel facePanel;
	public GamePanel(GameDto dto, GameHandle handle,PlayerHandle playerHandle){
		this.dto = dto;
		this.setLayout(null);
		//设置背景颜色
		this.setBackground(Color.LIGHT_GRAY);
		
		this.topBoxPanel = new BoxPanel(BoxPanel.TOP_BOX);
		
		this.mineNumBoxPanel = new NumBoxPanel(BoxPanel.NUMBER_BOX,this.dto,NumBoxPanel.MINE);
		//TODO 根据显示数字位数调整大小
		this.mineNumBoxPanel.setBounds(7, 6, 41, 25);
		this.facePanel = new FacePanel(FacePanel.DEFAULT);
		this.timerBoxPanel = new NumBoxPanel(BoxPanel.NUMBER_BOX,this.dto,NumBoxPanel.TIMER);
		
		this.facePanel.addMouseListener(playerHandle);
		
		this.topBoxPanel.add(this.facePanel);
		this.topBoxPanel.add(this.timerBoxPanel);
		this.topBoxPanel.add(this.mineNumBoxPanel);
		
		this.add(topBoxPanel);
		
		createMap();
	}
	
	public void createMap(){
		if(this.gameBoxPanel != null){
			this.remove(this.gameBoxPanel);
		}
		//获取游戏等级
		Level level = GameConfig.getSystemConfig().getLevel();
		int gameWidth = level.getGameWidth() * Img.PIECE_W;
		int gameHeight = level.getGameHeight() * Img.PIECE_H;
		
		
		Mine[][] map = dto.getForeGroundmap();
		this.gameBoxPanel = new BoxPanel(BoxPanel.GAME_BOX);
		this.gameBoxPanel.setBounds(9, 50, gameWidth + 2 * Img.MAP_BOX_BORDER, gameHeight + 2 * Img.MAP_BOX_BORDER);
		
		this.topBoxPanel.setBounds(9, 7, gameWidth + 2 * Img.MAP_BOX_BORDER, 37);
		this.facePanel.setBounds((int)Math.ceil((gameWidth + 2 * Img.MAP_BOX_BORDER - Img.FACE_W) / 2.0), (int)Math.ceil((37 - Img.FACE_H) / 2.0), Img.FACE_W, Img.FACE_H);
		//TODO 根据显示数字位数调整大小
		this.timerBoxPanel.setBounds(gameWidth + 2 * Img.MAP_BOX_BORDER - 9 - 41, 6, 41, 25);
		
		for (int i = 0, w = level.getGameWidth(); i < w; i++) {
			for (int j = 0, h = level.getGameHeight(); j < h; j++) {
				this.gameBoxPanel.add(map[i][j]);
			}
		}
		this.add(this.gameBoxPanel);
	}
	
	public int getFace(){
		return this.facePanel.getType();
	}
	public void setFace(int type){
		this.facePanel.setType(type);
	}
}
