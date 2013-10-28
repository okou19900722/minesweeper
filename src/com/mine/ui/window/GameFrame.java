package com.mine.ui.window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.mine.config.GameConfig;
import com.mine.config.StringConfig;
import com.mine.dto.Level;
import com.mine.handle.GameHandle;
import com.mine.string.Path;
import com.mine.ui.GamePanel;

/**
 * 游戏窗口
 * @author 百度java吧@okou19900722
 */
public class GameFrame extends JFrame{
	private static final long serialVersionUID = 2968006428296662511L;
	
	private GamePanel gamePanel;
	private GameHandle gameHandle;
	
	public GameFrame(GamePanel panel,GameHandle handle){
		this.gamePanel = panel;
		this.gameHandle = handle;
		
		this.createComponent();
		
		this.initWindow();
	}
	public void initWindow() {
		// 设置窗口标题
		this.setTitle(StringConfig.getStringGameName());
		// 设置窗口图标
		this.setIconImage(new ImageIcon(Path.WINDOW_ICON_PATH).getImage());
		// 背景设置为白色
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().setForeground(Color.red);
		// 设置关闭按钮事件
		this.setDefaultCloseOperation(GameFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		
		// 设置窗口不可改变大小
		this.setResizable(false);
		// 设置窗口可见
		this.setVisible(true);
		// 设置窗口居中
		this.setLocation(367, 293);
		
		this.gameHandle.setWindowSize(this,menu.getHeight());
		this.add(this.gamePanel);
	}
	
	JMenuBar menu;
	JMenu gameMenu;
	JMenu helpMenu;
	JMenuItem start;
	JMenuItem  easy;
	JMenuItem  normal;
	JMenuItem  hard;
	JMenuItem  custom;
	JMenuItem mark;
	JMenuItem color;
	JMenuItem sound;
	JMenuItem record;
	JMenuItem exit;
	//
	/**
	 * 创建窗口菜单
	 */
	private void createComponent() {

		this.menu = new JMenuBar();
		this.gameMenu = new JMenu(StringConfig.getStringMenuGame() + "(G)");
		this.gameMenu.setMnemonic(KeyEvent.VK_G);
		this.helpMenu = new JMenu("帮助(H)");
		this.helpMenu.setMnemonic(KeyEvent.VK_H);

		this.start = new JMenuItem("开局(N)");
		KeyStroke startKey = KeyStroke.getKeyStroke("F2");
		this.start.setMnemonic(KeyEvent.VK_N);
		//菜单后面显示快捷键，并添加为全局快捷键
		this.start.setAccelerator(startKey);

		this.easy = new JCheckBoxMenuItem ("初级(B)");
		this.easy.setMnemonic(KeyEvent.VK_B);

		this.normal = new JCheckBoxMenuItem ("中级(I)");
		this.normal.setMnemonic(KeyEvent.VK_I);

		this.hard = new JCheckBoxMenuItem ("高级(E)");
		this.hard.setMnemonic(KeyEvent.VK_E);

		
		this.custom = new JCheckBoxMenuItem ("自定义(C)...");
		this.custom.setMnemonic(KeyEvent.VK_C);
		//根据系统配置设置窗口菜单难度选中状态
		switch(GameConfig.getSystemConfig().getLevel().getLevelId()){
		case 0:
			this.easy.setSelected(true);
			break;
		case 1:
			this.normal.setSelected(true);
			break;
		case 2:
			this.hard.setSelected(true);
			break;
		case 3:
			this.custom.setSelected(true);
			break;
		}

		this.mark = new JCheckBoxMenuItem("标记(?)(M)");
		this.mark.setMnemonic(KeyEvent.VK_M);
		
		// 设置窗口菜单是否显示标记(?)状态
		this.mark.setSelected(GameConfig.getSystemConfig().isShowQuestionMark());

		this.color = new JCheckBoxMenuItem("颜色(L)");
		this.color.setMnemonic(KeyEvent.VK_L);
		// 设置窗口皮肤及菜单“颜色”状态
		this.color.setSelected(GameConfig.getSystemConfig().isShowColor());

		this.sound = new JCheckBoxMenuItem("声音(S)");
		this.sound.setMnemonic(KeyEvent.VK_S);
		
		// 设置窗口菜单是否打开声音状态
		this.sound.setSelected(GameConfig.getSystemConfig().isPlaySound());

		this.record = new JMenuItem("扫雷英雄榜(T)...");
		this.record.setMnemonic(KeyEvent.VK_T);

		this.exit = new JMenuItem("退出(X)");
		this.exit.setMnemonic(KeyEvent.VK_X);
		
		ActionListener action = new MenuActionListener();
		
		this.start.addActionListener(action);
		this.easy.addActionListener(action);
		this.normal.addActionListener(action);
		this.hard.addActionListener(action);
		this.custom.addActionListener(action);
		this.mark.addActionListener(action);
		this.color.addActionListener(action);
		this.sound.addActionListener(action);
		this.record.addActionListener(action);
		this.exit.addActionListener(action);

		ButtonGroup levelGroup = new ButtonGroup();
		levelGroup.add(this.easy);
		levelGroup.add(this.normal);
		levelGroup.add(this.hard);
		levelGroup.add(this.custom);
		
		this.gameMenu.add(this.start);
		this.gameMenu.addSeparator();
		this.gameMenu.add(this.easy);
		this.gameMenu.add(this.normal);
		this.gameMenu.add(this.hard);
		this.gameMenu.add(this.custom);
		this.gameMenu.addSeparator();
		this.gameMenu.add(this.mark);
		this.gameMenu.add(this.color);
		this.gameMenu.add(this.sound);
		this.gameMenu.addSeparator();
		this.gameMenu.add(this.record);
		this.gameMenu.addSeparator();
		this.gameMenu.add(this.exit);
		this.menu.add(this.gameMenu);
		menu.add(this.helpMenu);

		this.setJMenuBar(menu);
	}
	
	public void resetSize(){
		this.gameHandle.setWindowSize(this,this.menu.getHeight());
	}
	private class MenuActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Object obj = e.getSource();
			if(obj==start){
				gameHandle.reStart();
			}else if(obj==easy){
				gameHandle.setLevel(Level.EASY);
			}else if(obj==normal){
				gameHandle.setLevel(Level.NORMAL);
			}else if(obj==hard){
				gameHandle.setLevel(Level.HARD);
			}else if(obj==custom){
				gameHandle.showCustomWindow();
			}else if(obj==mark){
				gameHandle.changeQuestionMark(mark.isSelected());
			}else if(obj==color){
				gameHandle.changeSkin(color.isSelected());
			}else if(obj==sound){
				gameHandle.changeSound(sound.isSelected());
			}else if(obj==record){
				gameHandle.showLeaderboardFrame();
			}else if(obj==exit){
				gameHandle.exit();
			}
		}
	}
}
