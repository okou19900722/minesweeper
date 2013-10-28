package com.mine.dto;

import java.io.Serializable;

import com.mine.config.StringConfig;

/**
 * 玩家信息
 * @author 百度java吧@okou19900722
 */
public class Player implements Serializable{
	private static final long serialVersionUID = -7594523355912548466L;
	
	
	/**
	 * 玩家名
	 */
	private String uName;
	/**
	 * 玩家完成游戏所用时间
	 */
	private int time;
	
	/**
	 * 生成默认用户，时间为999
	 */
	public Player() {
		this(StringConfig.getStringDefaultUName(), 999);
	}
	public Player(String uName, int time) {
		super();
		this.uName = uName;
		this.time = time;
	}
	public String getuName() {
		return this.uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public int getTime() {
		return this.time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
}
