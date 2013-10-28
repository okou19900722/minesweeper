package com.mine.ui.window;

import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mine.config.GameConfig;
import com.mine.config.StringConfig;
import com.mine.handle.GameHandle;
import com.mine.ui.BoxPanel;

/**
 * 新记录窗口
 * @author 百度java吧@okou19900722
 */
public class RecordFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839276659682978415L;
	JTextField unameField;
	JButton btnOk;
	JLabel content1;
	JLabel content2;
	private GameHandle gameHandle;
	public RecordFrame(GameHandle gameHandle,GameFrame gameFrame){
		super(gameFrame,true);
		this.gameHandle = gameHandle;
		this.setUndecorated(true);
		this.createPanel(this.getContentPane());
		this.setSize(156, 170);
		this.addWindowListener(new RecordFrameWindowClose());
	}
	private void createPanel(Container c){
		JPanel panel = (JPanel) c;
		BoxPanel box = new BoxPanel(BoxPanel.LEADERBOARD_BOX);
		box.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.content1 = new JLabel();
		this.content2 = new JLabel();
		this.content1.setBounds(38, 4, 84, 24);
		this.content2.setBounds(38, 16, 84, 24);
		
		this.unameField = new JTextField();
		this.unameField.setMargin(new Insets(0, 0, 0, 0));
		this.unameField.setBounds(21, 80, 117, 18);
		this.btnOk = new JButton();
		this.btnOk.setBounds(51, 111, 54, 21);
		this.btnOk.setMargin(new Insets(0, 0, 0, 0));
		this.getRootPane().setDefaultButton(this.btnOk);
		
		this.btnOk.addActionListener(new RecordFrameActionListener());
		KeyListener keyAdapter = new RecordFrameKeyAdapter();
		this.btnOk.addKeyListener(keyAdapter);
		box.addKeyListener(keyAdapter);
		this.unameField.addKeyListener(keyAdapter);
		
		box.add(content1);
		box.add(content2);
		box.add(unameField);
		box.add(btnOk);
		panel.add(box);
	}
	@Override
	public void setVisible(boolean b) {
		if(b){
			int levelId = GameConfig.getSystemConfig().getLevel().getLevelId();
			String contentStr1 = StringConfig.getStringContent1().replace("{$level}", StringConfig.getStringLevel(levelId));
			String contentStr2 = StringConfig.getStringContent2().replace("{$level}", StringConfig.getStringLevel(levelId));
			this.content1.setText(contentStr1);
			this.content2.setText(contentStr2);
			this.btnOk.setText(StringConfig.getStringOk());
			this.unameField.setText(StringConfig.getStringDefaultUName());
			this.unameField.selectAll();
		}
		super.setVisible(b);
	}
	private void onExit(){
		this.setVisible(false);
		this.gameHandle.saveRecord(this.unameField.getText());
	}
	private void save(){
		this.gameHandle.saveRecord(this.unameField.getText());
	}
	private class RecordFrameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOk){
				onExit();
			}
		}
		
	}
	private class RecordFrameKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				onExit();
			}
		}
	}
	private class RecordFrameWindowClose extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent e) {
			save();
		}
	}
}

