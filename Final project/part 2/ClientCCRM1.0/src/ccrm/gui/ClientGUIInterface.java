package ccrm.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

public interface ClientGUIInterface {
	public void changePanel(JPanel panel);

	public void updateFields(Object... field);

	public boolean testAllFields(JTextField... field);
	
	public void clearFields(JTextField... field);
}
