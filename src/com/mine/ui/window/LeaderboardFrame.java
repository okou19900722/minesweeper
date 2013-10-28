package com.mine.ui.window;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mine.config.GameConfig;
import com.mine.config.StringConfig;
import com.mine.dto.Player;
import com.mine.string.Path;

/**
 * 英雄窗口
 * @author 百度java吧@okou19900722
 */
public class LeaderboardFrame extends JDialog{
	private static final long serialVersionUID = -707536353632804209L;
	
	private JButton btnOk;
	private JButton btnReset;
	private JLabel[] levelTitle = new JLabel[3];
	private JLabel[] levelPoint = new JLabel[3];
	private JLabel[] levelUName = new JLabel[3];
	
	public LeaderboardFrame(GameFrame gameFrame){
		super(gameFrame, true);
		// 设置窗口标题
		this.setTitle(StringConfig.getStringLeaderboardFrameTitle());
		// 初始化窗口主面板
		createPanel(this.getContentPane());
		repaint();
		// 设置窗口图标
		this.setIconImage(new ImageIcon(Path.WINDOW_ICON_PATH).getImage());
		
		//设置窗口大小
		this.setSize(261, 145);
		this.setLocation(gameFrame.getX() + 3, gameFrame.getY() + 91);
		
	}
	
	private void createPanel(Container c){
		JPanel panel = (JPanel) c;
		panel.setLayout(null);

		int x = 15;
		int y = 21;
		int h = 15;
		
		int pointX = x + 42;
		int unameX = x + 123;
		Player[] p = GameConfig.getSystemConfig().getRecords();
		for(int i=0;i<3;i++){
			this.levelTitle[i] = new JLabel(StringConfig.getStringLevel(i) + ":");
			this.levelTitle[i].setBounds(x, y + h * i, 50, y);
			panel.add(this.levelTitle[i]);
			this.levelPoint[i] = new JLabel(p[i].getTime() + " " + StringConfig.getStringSecond());
			this.levelPoint[i].setBounds(pointX, y + h * i, 50, y);
			panel.add(this.levelPoint[i]);
			this.levelUName[i] = new JLabel(p[i].getuName());
			this.levelUName[i].setBounds(unameX, y + h * i, 50, y);
			panel.add(this.levelUName[i]);
		}
		
		this.btnReset = new JButton(StringConfig.getStringReSetPoint() + "(R)");
		this.btnReset.setMnemonic(KeyEvent.VK_R);
		this.btnOk = new JButton(StringConfig.getStringOk());
		
		this.btnReset.setMargin(new Insets(0, 0, 1, 0));
		this.btnOk.setMargin(new Insets(0, 0, 1, 0));
		this.btnReset.setBounds(38, 86, 75, 18);
		this.btnOk.setBounds(173, 86, 45, 18);
		
		LeaderboardFrameActionListener action = new LeaderboardFrameActionListener();
		this.btnOk.addActionListener(action);
		this.btnReset.addActionListener(action);
		panel.add(btnOk);
		panel.add(btnReset);
		
		LeaderboardFrameKeyAdapter keyAdapter = new LeaderboardFrameKeyAdapter();
		this.addKeyListener(keyAdapter);
		panel.addKeyListener(keyAdapter);
		this.btnOk.addKeyListener(keyAdapter);
		this.btnReset.addKeyListener(keyAdapter);
	}
	private void onReset(){
		Player[] p = new Player[3];
		for(int i=0;i<3;i++){
			p[i] = new Player();
		}
		GameConfig.getSystemConfig().setRecords(p);
		GameConfig.save();
		this.repaint();
	}
	private void onExit(){
		this.setVisible(false);
	}
	
	@Override
	public void paint(Graphics g) {
		Player[] p = GameConfig.getSystemConfig().getRecords();
		for (int i = 0; i < 3; i++) {
			this.levelTitle[i].setText(StringConfig.getStringLevel(i) + ":");
			this.levelPoint[i].setText(p[i].getTime() + " "
					+ StringConfig.getStringSecond());
			this.levelUName[i].setText(p[i].getuName());
		}

		this.getRootPane().setDefaultButton(this.btnOk);
		this.btnOk.requestFocus();
		super.paint(g);
	}
	private class LeaderboardFrameKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_R){
				onReset();
			}else if(keyCode == KeyEvent.VK_ESCAPE){
				onExit();
			}
		}
	}
	private class LeaderboardFrameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnOk){
				onExit();
			}else if(obj == btnReset){
				onReset();
			}
		}
		
	}
}
