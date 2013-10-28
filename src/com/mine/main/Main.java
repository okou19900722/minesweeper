package com.mine.main;

import javax.swing.UIManager;

import com.mine.handle.GameHandle;

/**
 * 游戏入口
 * @author 百度java吧@okou19900722
 */
public class Main {

	public static void main(String[] args) {
		//输入中文的时候，不会显示浮动框
		System.setProperty("java.awt.im.style","on-the-spot");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new GameHandle();
	}
}
