package com.mine.ui;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import com.mine.handle.PlayerHandle;
import com.mine.string.Img;

/**
 * 方块类
 * @author 百度java吧@okou19900722
 */
public class Mine extends JPanel{
	private static final long serialVersionUID = -1032155618978384322L;
	
	
	/**
	 * 地图表示空，用户点击表示已点击，但未点开
	 */
	public static final int EMPTY = 0;
	/**
	 * 数字1
	 */
	public static final int ONE = 1;
	/**
	 * 数字2
	 */
	public static final int TWO = 2;
	/**
	 * 数字3
	 */
	public static final int THREE = 3;
	/**
	 * 数字4
	 */
	public static final int FOUR = 4;
	/**
	 * 数字5
	 */
	public static final int FIVE = 5;
	/**
	 * 数字6
	 */
	public static final int SIX = 6;
	/**
	 * 数字7
	 */
	public static final int SEVEN = 7;
	/**
	 * 数字8
	 */
	public static final int EIGHT = 8;
	/**
	 * 雷
	 */
	public static final int MINE = 9;
	/**
	 * 标记----雷
	 */
	public static final int TIP_MINE = 10;
	/**
	 * 标记----问号
	 */
	public static final int TIP_UNCERTAIN = 11;
	/**
	 * 初始状态
	 */
	public static final int INITIAL = 12;
	/**
	 * 踩中的地雷
	 */
	public static final int MINE_HIT = 13;
	/**
	 * 错误的标记
	 */
	public static final int WRONG_TIP = 14;
	/**
	 * 标记----问号按下
	 */
	public static final int TIP_UNCERTAIN_PRESS = 15;
	//若要增加状态，在此处增加，并保证PRESS与TYPE_NUM的值相等此为最大
	/**
	 * 标识鼠标按下但不松开的状态
	 * TODO 检测此处的值是否是最大的值，若不是最大的，会出错
	 */
	public static final int PRESS = 16;

	
	/**
	 * 状态数量
	 */
	public static final int TYPE_NUM = 16;
	
	/**
	 * 位置
	 */
	private Point point = new Point();
	
	
	private int type;
	

	public Mine(int type,PlayerHandle handle) {
		this(type,null,handle);
		this.type = type;
	}
	public Mine(int t,Point p,PlayerHandle handle){
		this.type = t;
		if(p!=null){
			this.point = p;
		}
		setSize(Img.PIECE_W, Img.PIECE_H);
		if(handle!=null){
			this.addMouseListener(handle);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		this.setLocation(point.x * Img.PIECE_W + Img.MAP_BOX_BORDER, point.y * Img.PIECE_H + Img.MAP_BOX_BORDER);
		int i = type % TYPE_NUM;
		g.drawImage(Img.MAP_STATUS, 
				0, 0,Img.PIECE_W, Img.PIECE_H,
				i * Img.PIECE_W,	0, (i + 1) * Img.PIECE_W, Img.PIECE_H,
				null);
	}
	public int getType() {
		return type;
	}
	public void setType(int type){
		this.type = type;
	}
	public Point getP(){
		return point;
	}
}
