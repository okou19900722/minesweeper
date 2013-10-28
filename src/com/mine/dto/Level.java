package com.mine.dto;

import java.io.Serializable;

import com.mine.config.StringConfig;

/**
 * 游戏难度
 * @author 百度java吧@okou19900722
 */
public class Level implements Serializable{
	private static final long serialVersionUID = 6967956825974492718L;
	
	
	/**
	 * 初级难度
	 */
	public static final Level EASY = new Level(0,9,9,10);
	/**
	 * 中级难度
	 */
	public static final Level NORMAL = new Level(1,16,16,40);
	/**
	 * 高级难度
	 */
	public static final Level HARD = new Level(2,30,16,99);
	
	/**
	 * 等级ID
	 * 为了区分自定义设置和系统难度的
	 */
	private int levelId;
	/**
	 * 游戏区域宽度
	 */
	private int gameWidth;
	/**
	 * 游戏区域高度
	 */
	private int gameHeight;
	/**
	 * 当前等级雷数量
	 */
	private int mineNum;
	/**
	 * 生成自定义的等级
	 * @param gameWidth 宽度
	 * @param gameHeight 高度
	 * @param mineNum 雷数
	 */
	public Level(int gameWidth,int gameHeight,int mineNum){
		this(3, gameWidth, gameHeight, mineNum);
	}
	/**
	 * 游戏宽度的范围为9-30
	 * 游戏高度的范围为9-24
	 * 雷数最小为10，最大为(宽度-1)*(高度-1)
	 * @param levelId 等级Id,0为初级，1为中级，2为高级，3为自定义
	 * @param gameWidth
	 * @param gameHeight
	 * @param mineNum
	 */
	public Level(int levelId,int gameWidth,int gameHeight,int mineNum){
		
		if (gameHeight < 9) {
			gameHeight = 9;
		} else if (gameHeight > 24) {
			gameHeight = 24;
		}
		if (gameWidth < 9) {
			gameWidth = 9;
		} else if (gameWidth > 30) {
			gameWidth = 30;
		}
		int countMineNum = (gameHeight - 1) * (gameWidth - 1);
		if(mineNum<10){
			mineNum = 10;
		}else if(mineNum > countMineNum){
			mineNum = countMineNum ;
		}
		this.levelId = levelId;
		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		this.mineNum = mineNum;
	}
	
	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getGameWidth() {
		return gameWidth;
	}
	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}
	public int getGameHeight() {
		return gameHeight;
	}
	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}
	public int getMineNum() {
		return mineNum;
	}
	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}
	@Override
	public String toString(){
		return StringConfig.getStringLevel(levelId) + ":{h:" + gameHeight + ",w:" + gameWidth + ",m:" + mineNum + "]";
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==this)return true;
		if(obj==null || !(obj instanceof Level)){
			return false;
		}
		Level l1 = (Level)obj;
		Level l2 = this;
		return l1.levelId==l2.levelId && l1.gameHeight==l2.gameHeight && l1.gameWidth==l2.gameWidth && l1.mineNum==l2.mineNum;
	}
}
