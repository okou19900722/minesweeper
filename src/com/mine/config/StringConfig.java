package com.mine.config;


/**
 * 文字配置，如果需要做多语言版，可将本类改为读取配置文件
 * 需要注意stringContent1和stringContent2可以使用“{$level}”来表示stringDefaultUName
 * @see StringConfig#stringContent1 
 * @see StringConfig#stringContent2
 * @see StringConfig#stringDefaultUName
 * @author 百度java吧@okou19900722
 */
public class StringConfig{
	
	/**
	 * 按钮文字
	 */
	private static String stringOk = "确定";
	private static String stringCancel = "取消";
	/**
	 * 窗口标题
	 */
	private static String stringGameName = "扫雷";
	private static String stringCustomFrameTitle = "自定义雷区";
	private static String stringLeaderboardFrameTitle = "扫雷英雄榜";
	/**
	 * 菜单文字
	 */
	private static String stringMenuGame = "游戏";
	private static String stringMenuStart = "开局";
	private static String stringMenuTip = "标记";
	private static String stringMenuColor = "颜色";
	private static String stringMenuSound = "声音";
	private static String stringMenuRecord = "扫雷英雄榜";
	private static String stringMenuExit = "退出";
	/**
	 * 	自定义窗口文字
	 */
	private static String stringWidth = "宽度";
	private static String stringHeight = "高度";
	private static String stringMineNum = "雷数";
	
	/**
	 * 默认用户名，
	 */
	private static String stringDefaultUName = "匿名";
	private static String stringReSetPoint = "重新计分";
	private static String stringSecond = "秒";
	/**
	 * 记录面板文字
	 */
	private static String stringContent1 = "已破{$level}记录。";
	private static String stringContent2 = "请留尊姓大名。";
	/**
	 * 等级菜单区域文字
	 */
	private static String[] stringLevel= {"初级","中级","高级","自定义"};
	
	public static String getStringOk() {
		return stringOk;
	}
	public static void setStringOk(String stringOk) {
		StringConfig.stringOk = stringOk;
	}
	public static String getStringCancel() {
		return stringCancel;
	}
	public static void setStringCancel(String stringCancel) {
		StringConfig.stringCancel = stringCancel;
	}
	public static String getStringCustomFrameTitle() {
		return stringCustomFrameTitle;
	}
	public static void setStringCustomFrameTitle(String stringCustomFrameTitle) {
		StringConfig.stringCustomFrameTitle = stringCustomFrameTitle;
	}
	public static String getStringWidth() {
		return stringWidth;
	}
	public static void setStringWidth(String stringWidth) {
		StringConfig.stringWidth = stringWidth;
	}
	public static String getStringHeight() {
		return stringHeight;
	}
	public static void setStringHeight(String stringHeight) {
		StringConfig.stringHeight = stringHeight;
	}
	public static String getStringMineNum() {
		return stringMineNum;
	}
	public static void setStringMineNum(String stringMineNum) {
		StringConfig.stringMineNum = stringMineNum;
	}
	public static String[] getStringLevel() {
		return stringLevel;
	}
	public static String getStringLevel(int index){
		return stringLevel[index];
	}
	public static void setStringLevel(String[] stringLevel) {
		StringConfig.stringLevel = stringLevel;
	}
	public static String getStringGameName() {
		return stringGameName;
	}
	public static void setStringGameName(String stringGameName) {
		StringConfig.stringGameName = stringGameName;
	}
	public static String getStringMenuGame() {
		return stringMenuGame;
	}
	public static void setStringMenuGame(String stringMenuGame) {
		StringConfig.stringMenuGame = stringMenuGame;
	}
	public static String getStringMenuStart() {
		return stringMenuStart;
	}
	public static void setStringMenuStart(String stringMenuStart) {
		StringConfig.stringMenuStart = stringMenuStart;
	}
	public static String getStringMenuTip() {
		return stringMenuTip;
	}
	public static void setStringMenuTip(String stringMenuTip) {
		StringConfig.stringMenuTip = stringMenuTip;
	}
	public static String getStringMenuColor() {
		return stringMenuColor;
	}
	public static void setStringMenuColor(String stringMenuColor) {
		StringConfig.stringMenuColor = stringMenuColor;
	}
	public static String getStringMenuSound() {
		return stringMenuSound;
	}
	public static void setStringMenuSound(String stringMenuSound) {
		StringConfig.stringMenuSound = stringMenuSound;
	}
	public static String getStringMenuRecord() {
		return stringMenuRecord;
	}
	public static void setStringMenuRecord(String stringMenuRecord) {
		StringConfig.stringMenuRecord = stringMenuRecord;
	}
	public static String getStringMenuExit() {
		return stringMenuExit;
	}
	public static void setStringMenuExit(String stringMenuExit) {
		StringConfig.stringMenuExit = stringMenuExit;
	}
	public static String getStringDefaultUName() {
		return stringDefaultUName;
	}
	public static void setStringDefaultUName(String stringDefaultUName) {
		StringConfig.stringDefaultUName = stringDefaultUName;
	}
	public static String getStringLeaderboardFrameTitle() {
		return stringLeaderboardFrameTitle;
	}
	public static void setStringLeaderboardFrameTitle(
			String stringLeaderboardFrameTitle) {
		StringConfig.stringLeaderboardFrameTitle = stringLeaderboardFrameTitle;
	}
	public static String getStringReSetPoint() {
		return stringReSetPoint;
	}
	public static void setStringReSetPoint(String stringReSetPoint) {
		StringConfig.stringReSetPoint = stringReSetPoint;
	}
	public static String getStringSecond() {
		return stringSecond;
	}
	public static void setStringSecond(String stringSecond) {
		StringConfig.stringSecond = stringSecond;
	}
	public static String getStringContent1() {
		return stringContent1;
	}
	public static void setStringContent1(String stringContent1) {
		StringConfig.stringContent1 = stringContent1;
	}
	public static String getStringContent2() {
		return stringContent2;
	}
	public static void setStringContent2(String stringContent2) {
		StringConfig.stringContent2 = stringContent2;
	}
	
}
