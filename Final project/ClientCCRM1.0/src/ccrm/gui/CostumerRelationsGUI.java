package ccrm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ccrm.client.CCRMClientControl;
import ccrm.client.CCRMClientUI;

import common.ListCombo;

public class CostumerRelationsGUI extends JPanel implements ClientGUIInterface {

	JTextField textFieldNAME, textFieldFAMILY, textFieldAGE, textFieldCITY, textFieldPHONE;
	ListCombo city;

	private static final long serialVersionUID = 1L;
	JPanel panelADDCLIENT, panelDETAILS;
	CCRMClientControl control;

	public CostumerRelationsGUI(CCRMClientControl control) {
		this.control = control;
		initialize();
		add(panelADDCLIENT);
	}

	private void initialize() {
		setBounds(CCRMClientUI.defaultBigScreen);
		setLayout(null);

		initCostumerRelations();
		initAddClient();
	}

	public void initCostumerRelations() {
		panelADDCLIENT = new JPanel();
		panelADDCLIENT.setBounds(CCRMClientUI.defaultBigScreen);
		panelADDCLIENT.setLayout(null);

		JButton btnNewButton = new JButton("Add Client");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(panelDETAILS);
			}
		});
		btnNewButton.setBounds(159, 86, 89, 23);
		panelADDCLIENT.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Log Out");
		btnNewButton_1.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelADDCLIENT.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.logOutFromServer();
			}
		});
	}

	public void initAddClient() {
		panelDETAILS = new JPanel();
		panelDETAILS.setBounds(CCRMClientUI.defaultBigScreen);
		panelDETAILS.setLayout(null);

		JLabel lblClientName = new JLabel("Name:");
		lblClientName.setBounds(10, 30, 71, 14);
		panelDETAILS.add(lblClientName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(10, 60, 80, 14);
		panelDETAILS.add(lblLastName);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(10, 89, 46, 14);
		panelDETAILS.add(lblAge);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(10, 117, 46, 14);
		panelDETAILS.add(lblCity);

		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setBounds(10, 148, 97, 14);
		panelDETAILS.add(lblPhoneNumber);

		textFieldNAME = new JTextField();
		textFieldNAME.setBounds(98, 30, 142, 20);
		panelDETAILS.add(textFieldNAME);
		textFieldNAME.setColumns(10);

		textFieldFAMILY = new JTextField();
		textFieldFAMILY.setBounds(98, 58, 142, 20);
		panelDETAILS.add(textFieldFAMILY);
		textFieldFAMILY.setColumns(10);

		textFieldAGE = new JTextField();
		textFieldAGE.setBounds(98, 86, 142, 20);
		panelDETAILS.add(textFieldAGE);
		textFieldAGE.setColumns(10);

		textFieldPHONE = new JTextField();
		textFieldPHONE.setBounds(98, 143, 142, 20);
		panelDETAILS.add(textFieldPHONE);
		textFieldPHONE.setColumns(10);

		JLabel lblNewClientDetails = new JLabel("New Client Details");
		lblNewClientDetails.setBounds(43, 5, 121, 14);
		panelDETAILS.add(lblNewClientDetails);

		JButton btnFinish = new JButton("Add New Costumer");
		btnFinish.setBounds(0, getHeight() - 51, 150, 23);
		panelDETAILS.add(btnFinish);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(testAllFields(textFieldNAME, textFieldFAMILY, textFieldAGE, textFieldPHONE)) {
					control.addClientToDB(textFieldNAME.getText(), textFieldFAMILY.getText(), Integer.parseInt(textFieldAGE.getText()), city.getIds().get(city.list.getSelectedIndex()), textFieldPHONE.getText());
					clearFields(textFieldNAME, textFieldFAMILY, textFieldAGE, textFieldPHONE);
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelDETAILS.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields(textFieldNAME, textFieldFAMILY, textFieldAGE, textFieldPHONE);
				changePanel(panelADDCLIENT);
			}
		});

	}

	public void changePanel(JPanel panel) {
		removeAll();
		add(panel);
		invalidate();
		repaint();
	}

	@Override
	public void updateFields(Object... field) {
		city = (ListCombo) field[0];
		city.list.setBounds(98, 115, 142, 20);
		panelDETAILS.add(city.list);
	}

	@Override
	public boolean testAllFields(JTextField... field) {
		for (int i = 0; i < field.length; i++) {
			if (field[i].getText().equals(""))
				return false;
		}
		return true;
	}

	@Override
	public void clearFields(JTextField... field) {
		for(JTextField f: field)
			f.setText("");
	}

}
