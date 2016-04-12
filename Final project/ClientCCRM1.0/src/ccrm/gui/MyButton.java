package ccrm.gui;

import javax.swing.JButton;

public class MyButton extends JButton {
	
	public MyButton(String title, int x, int y, int w, int h) {
		super();
		setBounds(x, y, w, h);
		setText(title);
	}
	
}
