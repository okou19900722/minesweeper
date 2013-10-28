package com.mine.ui;

import java.awt.Graphics;

import com.mine.config.GameConfig;
import com.mine.dto.GameDto;
import com.mine.string.Img;

/**
 * 数字边框类
 * @author 百度java吧@okou19900722
 */
public class NumBoxPanel extends BoxPanel{
	private static final long serialVersionUID = -2370272398143748062L;
	/**
	 * 雷数数字边框
	 */
	public final static int MINE = 0;
	/**
	 * 时间数字边框
	 */
	public final static int TIMER = 1;
	
	private GameDto dto;
	private int numType;
	public NumBoxPanel(int type,GameDto dto,int numType) {
		super(type);
		this.dto = dto;
		this.numType = numType;
	}
	/**
	 * 显示位数，未实现其他位数，固定为3
	 */
	private final int digit = 3;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//根据类型显示雷数或者时间
		int showNum = 0;
		if(this.numType==NumBoxPanel.MINE){
			showNum = GameConfig.getSystemConfig().getLevel().getMineNum() - this.dto.getTip();
		}else{
			showNum = this.dto.getNowTime();
		}
		
		//如果数字是负数，第一位绘制负号，否则如果是三位数，显示百位，否则显示0		
		boolean isNegative = false;
		if(showNum<0){
			isNegative = true;
			showNum = 0- showNum;
		}
		String strNum = Integer.toString(showNum);
		int i = 0;
		int j = 0;
		if(isNegative){
			j=10;
		}else{
			if(strNum.length()>=digit - i){
				j = strNum.charAt(strNum.length() - digit + i) - '0';
			}else{
				j = 0;
			}
		}
		g.drawImage(Img.NUMBER, i * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUM_BOX_BORDER, (i + 1) * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUMBER_H + Img.NUM_BOX_BORDER, j * Img.NUMBER_W, 0, (j + 1) * Img.NUMBER_W, Img.NUMBER_H, null);
		//如果有二位数，显示十位，否则显示0
		i=1;
		if(strNum.length()>=digit - i){
			j = strNum.charAt(strNum.length() - digit + i) - '0';
		}else{
			j = 0;
		}
		g.drawImage(Img.NUMBER, i * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUM_BOX_BORDER, (i + 1) * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUMBER_H + Img.NUM_BOX_BORDER, j * Img.NUMBER_W, 0, (j + 1) * Img.NUMBER_W, Img.NUMBER_H, null);
		//显示个位
		i=2;
		if(strNum.length()>=digit - i){
			j = strNum.charAt(strNum.length() - digit + i) - '0';
		}else{
			j = 0;
		}
		g.drawImage(Img.NUMBER, i * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUM_BOX_BORDER, (i + 1) * Img.NUMBER_W + Img.NUM_BOX_BORDER, Img.NUMBER_H + Img.NUM_BOX_BORDER, j * Img.NUMBER_W, 0, (j + 1) * Img.NUMBER_W, Img.NUMBER_H, null);
		
	}
}
