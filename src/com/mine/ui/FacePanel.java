package com.mine.ui;

import java.awt.Graphics;

import javax.swing.JButton;

import com.mine.string.Img;

/**
 * 笑脸
 * @author 百度java吧@okou19900722
 */
public class FacePanel extends JButton{
	private static final long serialVersionUID = -5578083200807460169L;
	
	/**
	 * 笑脸正常状态
	 */
	public static final int DEFAULT = 0;
	/**
	 * 笑脸按下时
	 */
	public static final int PRESS = 1;
	/**
	 * 游戏界面鼠标点击时
	 */
	public static final int CLICK = 2;
	/**
	 * 胜利时
	 */
	public static final int WIN = 3;
	/**
	 * 失败时
	 */
	public static final int CRY = 4;
	
	private int type;
	public FacePanel(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	public void setType(int type){
		this.type = type;
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(Img.FACE, 
				0, 0,Img.FACE_W, Img.FACE_H,
				this.type * Img.FACE_W,	0, (this.type + 1) * Img.FACE_W, Img.FACE_H,
				null);
	}
	
}
