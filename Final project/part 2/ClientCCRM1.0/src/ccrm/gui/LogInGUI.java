package ccrm.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ccrm.client.CCRMClientControl;

public class LogInGUI extends JPanel implements ClientGUIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String knowPort = "" + 3333;
	public String knowHost = "127.0.0.1";
	public String knowUser = "b";
	public String knowPass = "2";

	CCRMClientControl control;

	public LogInGUI(CCRMClientControl c) {
		control = c;
		initialize();
	}

	private void initialize() {
		setBounds(0, 0, 450, 300);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Port:");
		lblNewLabel.setBounds(22, 93, 78, 14);
		add(lblNewLabel);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(22, 118, 46, 14);
		add(lblIp);

		JLabel lblUserName = new JLabel("User name:");
		lblUserName.setBounds(22, 145, 78, 14);
		add(lblUserName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(22, 170, 70, 14);
		add(lblPassword);

		JTextField textFieldPORT = new JTextField(knowPort);
		textFieldPORT.setBounds(126, 87, 153, 20);
		add(textFieldPORT);
		textFieldPORT.setColumns(10);

		JTextField textFieldIP = new JTextField(knowHost);
		textFieldIP.setBounds(126, 112, 153, 20);
		add(textFieldIP);
		textFieldIP.setColumns(10);

		JTextField textFieldUNAME = new JTextField(knowUser);
		textFieldUNAME.setBounds(126, 139, 153, 20);
		add(textFieldUNAME);
		textFieldUNAME.setColumns(10);

		JTextField textFieldUPASS = new JTextField(knowPass);
		textFieldUPASS.setBounds(126, 164, 153, 20);
		add(textFieldUPASS);
		textFieldUPASS.setColumns(10);

		MyButton btnLogin = new MyButton("Log In", 0, getHeight() - 51, 150, 23);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(textFieldPORT, textFieldIP, textFieldUNAME, textFieldUPASS)) {
					control.connectToServer(textFieldIP.getText(), Integer.parseInt(textFieldPORT.getText()));
					control.loginToServer(textFieldUNAME.getText().toString(), textFieldUPASS.getText().toString());
				}
			}
		});
		add(btnLogin);

		// JButton btnExit = new JButton("Exit");
		// btnExit.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		MyButton btnExit = new MyButton("Exit", getWidth() - 156, getHeight() - 51, 150, 23);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.exitClient();
			}
		});
		add(btnExit);

		JLabel lblPleaseLogIn = new JLabel("please log in to continue..");
		lblPleaseLogIn.setBounds(66, 62, 159, 14);
		add(lblPleaseLogIn);

		JLabel logo = new JLabel();
		ImageIcon logoImage = new ImageIcon("logo.png");

		logo.setIcon(logoImage);
		logo.setBounds(300, 90, logoImage.getIconWidth(), logoImage.getIconHeight());
		add(logo);

		JLabel lblCcrmEmployeePortal = new JLabel("CCRM employee portal");
		lblCcrmEmployeePortal.setBounds(149, 23, 153, 14);
		add(lblCcrmEmployeePortal);
	}

	public void changePanel(JPanel panel) {
		removeAll();
		add(panel);
		invalidate();
		repaint();
	}

	@Override
	public void updateFields(Object... field) {

	}

	@Override
	public boolean testAllFields(JTextField... field) {
		for (int i = 0; i < field.length; i++) {
			if (field[i].getText().equals("")) {
				CCRMClientControl.infoBox("Please fill all the fields", "", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	@Override
	public void clearFields(JTextField... field) {
		for (JTextField f : field)
			f.setText("");
	}
}
