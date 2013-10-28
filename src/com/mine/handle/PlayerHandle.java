package com.mine.handle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 玩家控制器类，继承自MouseAdapter，并实现KeyListener接口
 * @see MouseAdapter
 * @author 百度java吧@okou19900722
 */
public class PlayerHandle extends MouseAdapter{
//TODO 测试完成后，删除焦点事件
	private GameHandle gameHandle;
	public PlayerHandle(GameHandle handle){
		this.gameHandle = handle;
	}
	/**
	 * 监听鼠标按下事件
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.gameHandle.mousePressed(e);
	}
	/**
	 * 监听鼠标松开事件
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		this.gameHandle.mouseReleased(e);
	}
	/**
	 * 监听鼠标离开控件事件
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		this.gameHandle.mouseExited(e);
	}
	/**
	 * 监听鼠标进入控件事件
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		this.gameHandle.mouseEntered(e);
	}
}
