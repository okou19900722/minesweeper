package com.mine.string;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.mine.ui.Mine;

/**
 * 图片类
 * @author 百度java吧@okou19900722
 */
public class Img {

	/**
	 * 笑脸图片
	 */
	public static Image FACE;
	/**
	 * 笑脸宽度
	 */
	public static int FACE_W;
	/**
	 * 笑脸高度
	 */
	public static int FACE_H;
	
	/**
	 * 游戏地图模型图
	 */
	public static Image MAP_STATUS;
	/**
	 * 每小块模型图的宽
	 */
	public static int PIECE_W;
	/**
	 * 每小块模型图的高
	 */
	public static int PIECE_H;
	
	/**
	 * 数字面板图片信息
	 */
	public static Image NUMBER;
	
	/**
	 * 数字宽度
	 */
	public static int NUMBER_W;
	/**
	 * 数字高度
	 */
	public static int NUMBER_H;
	
	/**
	 * 游戏边框图
	 */
	public static Image GAME_MAP_BOX;
	/**
	 * 游戏边框图的宽
	 */
	public static int GAME_MAP_BOX_W;
	/**
	 * 游戏边框图的高
	 */
	public static int GAME_MAP_BOX_H;
	/**
	 * 游戏边框线宽
	 */
	public static int MAP_BOX_BORDER = 3;
	/**
	 * 顶部边框图
	 */
	public static Image TOP_BOX;
	/**
	 * 顶部边框图的宽
	 */
	public static int TOP_BOX_W;
	/**
	 * 顶部边框图的高
	 */
	public static int TOP_BOX_H;
	/**
	 * 顶部边框线宽
	 */
	public static int TOP_BOX_BORDER = 2;
	/**
	 * 数字边框图
	 */
	public static Image NUMBER_BOX;
	/**
	 * 数字边框图的宽
	 */
	public static int NUMBER_BOX_W;
	/**
	 * 数字边框图的高
	 */
	public static int NUMBER_BOX_H;
	/**
	 * 数字边框线宽
	 */
	public static int NUM_BOX_BORDER = 1;
	/**
	 * 英雄榜边框图
	 */
	public static Image LEADERBOARD_BOX;
	/**
	 * 英雄榜边框图的宽
	 */
	public static int LEADERBOARD_BOX_W;
	/**
	 * 英雄榜边框图的高
	 */
	public static int LEADERBOARD_BOX_H;
	/**
	 * 英雄榜边框线宽
	 */
	public static int LEADERBOARD_BOX_BORDER = 2;
	
	static {
		reset();
	}
	public static void reset(){
		//初始化笑脸图片信息
		FACE = new ImageIcon(Path.SMILE_PATH).getImage();
		FACE_W = FACE.getWidth(null) / 5;
		FACE_H = FACE.getHeight(null);
		//初始化地图图片信息
		MAP_STATUS = new ImageIcon(Path.MAP_STATUS_PATH).getImage();
		PIECE_W = MAP_STATUS.getWidth(null) / Mine.TYPE_NUM;
		PIECE_H = MAP_STATUS.getHeight(null);
		//初始化数字信息
		NUMBER = new ImageIcon(Path.NUM_PATH).getImage();
		NUMBER_W = NUMBER.getWidth(null) / 11;
		NUMBER_H = NUMBER.getHeight(null);
		//初始化游戏边框信息
		GAME_MAP_BOX = new ImageIcon(Path.MAP_BOX_PATH).getImage();
		GAME_MAP_BOX_W = GAME_MAP_BOX.getWidth(null);
		GAME_MAP_BOX_H = GAME_MAP_BOX.getHeight(null);
		//初始化顶部边框信息
		TOP_BOX = new ImageIcon(Path.TOP_BOX_PATH).getImage();
		TOP_BOX_W = TOP_BOX.getWidth(null);
		TOP_BOX_H = TOP_BOX.getHeight(null);
		//初始化数字边框信息
		NUMBER_BOX = new ImageIcon(Path.NUM_BOX_PATH).getImage();
		NUMBER_BOX_W = NUMBER_BOX.getWidth(null);
		NUMBER_BOX_H = NUMBER_BOX.getHeight(null);
		//初始化英雄榜边框信息
		LEADERBOARD_BOX = new ImageIcon(Path.LEADERBOARD_BOX_PATH).getImage();
		LEADERBOARD_BOX_W = LEADERBOARD_BOX.getWidth(null);
		LEADERBOARD_BOX_H = LEADERBOARD_BOX.getHeight(null);
		
		
	}
}
