package com.mine.ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.mine.string.Img;

/**
 * 边框面板，默认布局方式为null，添加的控件必须设置位置和大小
 * @author 百度java吧@okou19900722
 */
public class BoxPanel extends JPanel {
	private static final long serialVersionUID = 7550026044240579674L;
	
	/**
	 * 游戏边框
	 */
	public static final int GAME_BOX = 0;
	/**
	 * 顶部边框
	 */
	public static final int TOP_BOX = 1;
	/**
	 * 数字边框
	 */
	public static final int NUMBER_BOX = 2;
	/**
	 * 记录窗口边框
	 */
	public static final int LEADERBOARD_BOX = 3;

	private int type;
	public BoxPanel(int type){
		//通过父类JPanel的构造方法将布局方式设置为null
		//为什么不直接通过this.setLayout(null);
		//因为子类如果不写的话，在构造方法的第一行会默认调用父类的无参构造方法
		//而JPanel无参的构造方法中会最终会将布局方式设置为new FlowLayout(),
		//new 对象会消耗性能，虽然很少，但良好的代码习惯很重要
		super(null);
		this.type = type;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//边框边线宽度
		int boxBorder = 0;
		//边框图片
		Image mapBox = null;
		//边框图片宽
		int boxW = 0;
		//边框图片高
		int boxH = 0;
		switch(this.type){
		case GAME_BOX:
			mapBox = Img.GAME_MAP_BOX;
			boxBorder = Img.MAP_BOX_BORDER;
			boxW = Img.GAME_MAP_BOX_W;
			boxH = Img.GAME_MAP_BOX_H;
			break;
		case TOP_BOX:
			mapBox = Img.TOP_BOX;
			boxBorder = Img.TOP_BOX_BORDER;
			boxW = Img.TOP_BOX_W;
			boxH = Img.TOP_BOX_H;
			break;
		case NUMBER_BOX:
			mapBox = Img.NUMBER_BOX;
			boxBorder = Img.NUM_BOX_BORDER;
			boxW = Img.NUMBER_BOX_W;
			boxH = Img.NUMBER_BOX_H;
			break;
		case LEADERBOARD_BOX:
			mapBox = Img.LEADERBOARD_BOX;
			boxBorder = Img.LEADERBOARD_BOX_BORDER;
			boxW = Img.LEADERBOARD_BOX_W;
			boxH = Img.LEADERBOARD_BOX_H;
			break;
		}
		int gameWidth = this.getWidth() - 2 * boxBorder;
		int gameHeight = this.getHeight() - 2 * boxBorder;
		//左上
		g.drawImage(mapBox, 
				0, 0, boxBorder, boxBorder, 
				0, 0, boxBorder, boxBorder, null);
		//正上
		g.drawImage(mapBox, 
				boxBorder, 0, boxBorder + gameWidth, boxBorder, 
				boxBorder, 0, boxW - boxBorder, boxBorder, null);
		//右上
		g.drawImage(mapBox, 
				boxBorder + gameWidth, 0,2 * boxBorder + gameWidth,boxBorder,
				boxW - boxBorder, 0, boxW, boxBorder, null);
		//正左
		g.drawImage(mapBox, 
				0, boxBorder,boxBorder,boxBorder + gameHeight,
				0, boxBorder, boxBorder, boxH - boxBorder, null);
		//中间
		g.drawImage(mapBox, 
				boxBorder, boxBorder,boxBorder + gameWidth, boxBorder + gameHeight,
				boxBorder, boxBorder, boxW - boxBorder, boxH - boxBorder, null);
		//正右
		g.drawImage(mapBox, 
				boxBorder + gameWidth,  boxBorder,2 * boxBorder + gameWidth,boxBorder + gameHeight,
				boxW - boxBorder, boxBorder, boxW, boxH - boxBorder, null);
		//左下
		g.drawImage(mapBox, 
				0, boxBorder + gameHeight,boxBorder ,2 * boxBorder + gameHeight,
				0, boxH - boxBorder, boxBorder, boxH, null);
		//正下
		g.drawImage(mapBox, 
				boxBorder, boxBorder + gameHeight,boxBorder + gameWidth,  2 * boxBorder + gameHeight,
				boxBorder, boxH - boxBorder, boxW - boxBorder, boxH, null);
		//右下
		g.drawImage(mapBox, 
				boxBorder + gameWidth, boxBorder + gameHeight,2 * boxBorder + gameWidth,2 * boxBorder + gameHeight,
				boxW - boxBorder, boxH - boxBorder, boxW, boxH, null);
	}
}
