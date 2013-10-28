package com.mine.string;

import com.mine.config.GameConfig;

/**
 * 路径类
 * @author 百度java吧@okou19900722
 */
public class Path {

	/**
	 * 全局皮肤路径
	 */
	private final static String GLOBAL_THEMES_PATH = "themes/";
	/**
	 * 有色皮肤
	 */
	public final static String DEFAULT_PATH = "default";
	
	/**
	 * 无色皮肤
	 */
	public final static String GRAY_PATH = "gray";

	/**
	 * 系统配置文件路径
	 */
	public static final String CONFIG_PATH = "data/config.data";
	/**
	 * 心跳音，每秒播放一次
	 */
	public static final String IDLE_PATH = "sound/idle.wav";
	
	/**
	 * 失败音
	 */
	public static final String LOSE_PATH = "sound/lose.wav";
	/**
	 * 胜利音
	 */
	public static final String WIN_PATH = "sound/win.wav";
	
	
	
	
	/**
	 * 皮肤名
	 */
	private static String skinPath;
	
	/**
	 * 窗口图标路径
	 */
	public static String WINDOW_ICON_PATH = null;
	/**
	 * 基础地图状态
	 */
	public static String MAP_STATUS_PATH = null;
	
	/**
	 * 地图边框
	 */
	public static String MAP_BOX_PATH = null;
	
	/**
	 * 数字图片
	 */
	public static String NUM_PATH = null;
	/**
	 * 笑脸图片
	 */
	public static String SMILE_PATH = null;
	/**
	 * 顶部边框图片
	 */
	public static String TOP_BOX_PATH = null;
	/**
	 * 数字边框图片
	 */
	public static String NUM_BOX_PATH = null;
	/**
	 * 英雄榜边框图片
	 */
	public static String LEADERBOARD_BOX_PATH = null;
	
	
	public static void setSkin(){
		skinPath = GameConfig.getSystemConfig().isShowColor()?Path.DEFAULT_PATH:Path.GRAY_PATH;
		LEADERBOARD_BOX_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/recordWindow.png";
		NUM_BOX_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/number_box.png";
		TOP_BOX_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/top_box.png";
		SMILE_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/smile.png";
		NUM_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/number.png";
		MAP_BOX_PATH = GLOBAL_THEMES_PATH + skinPath + "/windows/box.png";
		MAP_STATUS_PATH = GLOBAL_THEMES_PATH + skinPath + "/base/map_status.png";
		WINDOW_ICON_PATH = GLOBAL_THEMES_PATH + skinPath + "/icon/icon.png";
		
		//重新获取图片
		Img.reset();
	}
	static{
		setSkin();
	}
	public static String getSkin(){
		return skinPath;
	}
	
}
