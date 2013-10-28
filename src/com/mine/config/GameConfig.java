package com.mine.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mine.string.Path;

/**
 * 配置入口
 * @author 百度java吧@okou19900722
 */
public class GameConfig {

	/**
	 * 系统配置
	 */
	private static SystemConfig systemConfig;
	static{
		//创建文件对象
		File configData = new File(Path.CONFIG_PATH);
		//如果文件不存在，创建文件
		if(!configData.exists()){
			try {
				configData.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//创建配置
			createConfig();
		}else{
			//加载配置
			loadConfig();
		}
	}
	
	
	/**
	 * 创建配置，保存
	 */
	private static void createConfig(){
		systemConfig = new SystemConfig();
		save();
	}
	/**
	 * 保存配置
	 */
	public static void save(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path.CONFIG_PATH));
			oos.writeObject(systemConfig);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 加载配置
	 */
	public static void loadConfig(){
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Path.CONFIG_PATH));
			systemConfig = (SystemConfig) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SystemConfig getSystemConfig(){
		return systemConfig;
	}
}
