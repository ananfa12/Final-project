package ccrm.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class panelBig {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					panelBig window = new panelBig();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public panelBig() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 262);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(345, 239, 89, 23);
		panel.add(btnLogOut);
		
		JLabel lblChooseOneOf = new JLabel("Choose  one of the available operations");
		lblChooseOneOf.setBounds(28, 43, 277, 14);
		panel.add(lblChooseOneOf);
		
		JComboBox opList = new JComboBox();
		opList.setBounds(28, 68, 193, 20);
		panel.add(opList);
		
		JLabel lblChooseOneOf_1 = new JLabel("Choose one of the clients:");
		lblChooseOneOf_1.setBounds(28, 127, 193, 14);
		panel.add(lblChooseOneOf_1);
		
		JComboBox costList = new JComboBox();
		costList.setBounds(28, 152, 193, 20);
		panel.add(costList);
		
		JButton btnNewButton = new JButton("Call The Client");
		btnNewButton.setBounds(0, 239, 141, 23);
		panel.add(btnNewButton);
	}
}
