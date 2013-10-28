package com.mine.config;

import java.io.Serializable;

import com.mine.dto.Level;
import com.mine.dto.Player;
/**
 * 系统配置，实现序列化接口将系统配置写入文件
 * @author 百度java吧@okou19900722
 */
public class SystemConfig implements Serializable{
	private static final long serialVersionUID = -5818220430534306633L;
	
	
	/**
	 * 皮肤路径，点击菜单->颜色转换
	 */
	private boolean showColor;
	/**
	 * 游戏等级
	 */
	private Level level;
	/**
	 * 是否显示标记<?>
	 */
	private boolean showQuestionMark;
	/**
	 * 是否播放声音
	 */
	private boolean playerSound;
	/**
	 * 游戏记录
	 */
	private Player[] records;
	
	public SystemConfig(){
		init();
	}
	/**
	 * 初始化系统配置
	 */
	public void init(){
		this.showColor = true;
		this.level = Level.EASY;
		this.showQuestionMark = true;
		this.records = new Player[]{
				new Player(),
				new Player(),
				new Player()
		};
	}
	public boolean isShowColor() {
		return this.showColor;
	}
	public void setShowColor(boolean showColor) {
		this.showColor = showColor;
	}
	public Level getLevel() {
		return this.level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public boolean isShowQuestionMark() {
		return this.showQuestionMark;
	}
	public void setShowQuestionMark(boolean showQuestionMark) {
		this.showQuestionMark = showQuestionMark;
	}
	public Player[] getRecords() {
		return this.records;
	}
	public void setRecords(Player[] records) {
		this.records = records;
	}
	public boolean isPlaySound() {
		return this.playerSound;
	}
	public void setPlayerSound(boolean playerSound) {
		this.playerSound = playerSound;
	}
	
}
