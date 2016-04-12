package ccrm.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPanel;

public class panelSmall {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					panelSmall window = new panelSmall();
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
	public panelSmall() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblDidClientDecided = new JLabel("Did client decided to buy the product?");
		lblDidClientDecided.setBounds(10, 11, 264, 14);
		frame.getContentPane().add(lblDidClientDecided);
		
		JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBounds(194, 7, 43, 23);
		frame.getContentPane().add(rdbtnYes);
		
		JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBounds(236, 7, 109, 23);
		frame.getContentPane().add(rdbtnNo);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 264, 41);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblWhyNot = new JLabel("Why not?");
		lblWhyNot.setBounds(10, 11, 46, 14);
		panel.add(lblWhyNot);
		
		textField = new JTextField();
		textField.setBounds(67, 8, 187, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnFinish = new JButton("Finish");
		btnFinish.setBounds(0, 88, 89, 23);
		frame.getContentPane().add(btnFinish);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(195, 88, 89, 23);
		frame.getContentPane().add(btnCancel);
	}
}
