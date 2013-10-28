package com.mine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import sun.audio.AudioPlayer;

/**
 * 声音工具类
 * @author 百度java吧@okou19900722
 */
public class Audio{

	private Audio(String file) {
	}
	public static void play(String file) {
		try {
			//如果eclipse未导入rt.jar，会报红，但不影响
			//如果classpath配置出错，会报错，影响游戏
			AudioPlayer.player.start(new FileInputStream(new File(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}