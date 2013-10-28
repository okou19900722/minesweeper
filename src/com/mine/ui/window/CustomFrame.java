package com.mine.ui.window;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import com.mine.config.GameConfig;
import com.mine.config.StringConfig;
import com.mine.dto.Level;
import com.mine.handle.GameHandle;
import com.mine.string.Path;

/**
 * 自定义难度设置窗口
 * @author 百度java吧@okou19900722
 * 
 */
public class CustomFrame extends JDialog {
	private static final long serialVersionUID = -6535709852319353684L;
	

	/**
	 * 确定按钮
	 */
	private JButton btnOk;

	/**
	 * 取消按钮
	 */
	private JButton btnCancel;
	/**
	 * 高度标签
	 */
	private JLabel lbHeight;

	/**
	 * 宽度标签
	 */
	private JLabel lbWidth;
	/**
	 * 雷数标签
	 */
	private JLabel lbMineNum;
	/**
	 * 高度输入框
	 */
	private JTextField jtfHeight;
	/**
	 * 宽度输入框
	 */
	private JTextField jtfWidth;
	/**
	 * 雷数输入框
	 */
	private JTextField jtfMineNum;

	// 获取系统默认的本机工具包
	private Toolkit tk = Toolkit.getDefaultToolkit(); 

	/**
	 * 游戏逻辑控制器
	 */
	private GameHandle gameHandle;
	
	public CustomFrame(JFrame gameFrame,GameHandle gameHandle) {
		super(gameFrame, true);
		this.gameHandle = gameHandle;
		// 设置窗口标题
		this.setTitle(StringConfig.getStringCustomFrameTitle());
		// 设置窗口主面板
		this.setContentPane(createPanel());
		// 设置窗口图标
		this.setIconImage(new ImageIcon(Path.WINDOW_ICON_PATH).getImage());
		//设置窗口大小
		this.setSize(200, 160);
	}

	/**
	 * 初始化窗口主面板
	 * 
	 * @param level
	 *            面板上显示的等级
	 * @return 将创建的主面板返回
	 */
	private JPanel createPanel() {
		// 创建一个大小为200x160，没有布局方式的面板
		JPanel panel = new JPanel(null);
		panel.setSize(200, 160);

		// 初始化窗口控件
		btnOk = new JButton(StringConfig.getStringOk());
		btnCancel = new JButton(StringConfig.getStringCancel());

		// 增加按钮监听
		CustomFrameActionListener actionListener = new CustomFrameActionListener();
		btnOk.addActionListener(actionListener);
		btnCancel.addActionListener(actionListener);

		lbHeight = new JLabel(StringConfig.getStringHeight() + "(H)");
		lbWidth = new JLabel(StringConfig.getStringWidth() + "(W)");
		lbMineNum = new JLabel(StringConfig.getStringMineNum() + "(M)");

		jtfHeight = new JTextField();
		jtfWidth = new JTextField();
		jtfMineNum = new JTextField();
		
		CustomFrameKeyAdapter keyAdapter = new CustomFrameKeyAdapter();
		jtfHeight.addKeyListener(keyAdapter);
		jtfWidth.addKeyListener(keyAdapter);
		jtfMineNum.addKeyListener(keyAdapter);
		this.addKeyListener(keyAdapter);
		btnOk.addKeyListener(keyAdapter);
		btnCancel.addKeyListener(keyAdapter);

		// 窗口绝对布局
		btnOk.setBounds(120, 30, 60, 24);
		btnCancel.setBounds(120, 69, 60, 24);

		lbHeight.setBounds(15, 26, 50, 24);
		lbHeight.setDisplayedMnemonic(KeyEvent.VK_H);
		lbWidth.setBounds(15, 49, 50, 24);
		lbWidth.setDisplayedMnemonic(KeyEvent.VK_W);
		lbMineNum.setBounds(15, 71, 50, 24);
		lbMineNum.setDisplayedMnemonic(KeyEvent.VK_M);

		jtfHeight.setBounds(69, 30, 38, 18);
		jtfWidth.setBounds(69, 53, 38, 18);
		jtfMineNum.setBounds(69, 75, 38, 18);

		// 添加到窗口
		panel.add(btnOk);
		panel.add(btnCancel);

		panel.add(lbHeight);
		panel.add(lbWidth);
		panel.add(lbMineNum);

		panel.add(jtfHeight);
		panel.add(jtfWidth);
		panel.add(jtfMineNum);

		return panel;
	}

	private Level getLevel() {
		int gameHeight = 9;
		int gameWidth = 9;
		int mineNum = 10;
		try {
			gameHeight = Integer.parseInt(jtfHeight.getText());
		} catch (NumberFormatException e) {
		}
		try {
			gameWidth = Integer.parseInt(jtfWidth.getText());
		} catch (NumberFormatException e) {
		}
		try {
			mineNum = Integer.parseInt(jtfMineNum.getText());
		} catch (NumberFormatException e) {
		}
		return new Level(3, gameWidth, gameHeight, mineNum);
	}

	private class CustomFrameKeyAdapter extends KeyAdapter {
		boolean isAltPress = false;
		boolean isShiftPress = false;
		boolean isHPress = false;
		boolean isWPress = false;
		boolean isMPress = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			boolean isHeightFocus = jtfHeight.isFocusOwner();
			boolean isWidthFocus = jtfWidth.isFocusOwner();
			boolean isMineNumFocus = jtfMineNum.isFocusOwner();
			boolean notNeedAlt = true;
			
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				isShiftPress = true;
				break;
			case KeyEvent.VK_ALT:
				isAltPress = true;
				break;
			case KeyEvent.VK_H:
				isHPress = true;
				break;
			case KeyEvent.VK_W:
				isWPress = true;
				break;
			case KeyEvent.VK_M:
				isMPress = true;
				break;
			case KeyEvent.VK_ESCAPE:
				onCancel();
				return;
			default:
				// 发出当前系统最简单的声音
				if((!(isHeightFocus || isWidthFocus || isMineNumFocus) || isAltPress ) && !e.isActionKey()){
					//输入框都没有焦点，或者按住alt键的时候，除开switch中判断的键，其他键都发声
					tk.beep();
				}
				break;
			}
			
			if(isHeightFocus || isWidthFocus || isMineNumFocus){
				notNeedAlt = false;
			}
			if(notNeedAlt || isAltPress){
				if(isHPress){
					jtfHeight.requestFocus();
				}else if(isWPress && (isShiftPress || notNeedAlt)){
					jtfWidth.requestFocus();
				}else if(isMPress){
					jtfMineNum.requestFocus();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT:
				isShiftPress = false;
				break;
			case KeyEvent.VK_ALT:
				isAltPress = false;
				break;
			case KeyEvent.VK_H:
				isHPress = false;
				break;
			case KeyEvent.VK_W:
				isWPress = false;
				break;
			case KeyEvent.VK_M:
				isMPress = false;
				break;
			default:
				break;
			}
		}

		
	}
	private void onOk(){
		GameConfig.getSystemConfig().setLevel(this.getLevel());
		GameConfig.save();
		this.setVisible(false);
	}
	/**
	 * 取消窗口时的操作
	 */
	private void onCancel() {
		this.setVisible(false);
	}

	@Override
	public void setVisible(boolean b) {
		if(b){
			//获取游戏等级
			Level level = GameConfig.getSystemConfig().getLevel();
			jtfHeight.setText(level.getGameHeight() + "");
			jtfWidth.setText(level.getGameWidth() + "");
			jtfMineNum.setText(level.getMineNum() + "");
			// 自定义获得光标的按钮
			JRootPane root = getRootPane();// 获得根面板
			root.setDefaultButton(btnOk);// 设置缺省按钮
			btnOk.requestFocus();
		}else{
			GameConfig.getSystemConfig().getLevel().setLevelId(3);
			this.gameHandle.reStart();
		}
		super.setVisible(b);
	}
	private class CustomFrameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == btnOk) {
				onOk();
			} else if (obj == btnCancel) {
				onCancel();
			}

		}

	}
}
