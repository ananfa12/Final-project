package ccrm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ccrm.client.CCRMClientControl;
import ccrm.client.CCRMClientUI;
import common.ListCombo;

public class MarketingEmployeeGUI extends JPanel implements ClientGUIInterface {

	private static final long serialVersionUID = 1L;
	JTextField productName, productDesc, productPrice;
	JTextField sliceName, sliceMinAge, sliceMaxAge, sliceArea;
	JTextField patternProductName, patternSliceName, patternMessage;
	JPanel panelDEFINEPATERN, panelMARKETINGEMPLOYEE, panelCREATENEWSLICE, panelNEWPRODUCT, panelANALYSESYSTEM;
	File imageFile;
	BufferedImage image;
	private JFrame analyseSystem;
	private Stack<JPanel> lastPanel = new Stack<JPanel>();
	CCRMClientControl control;
	private ListCombo area, product, slice, fields;
	private JComboBox sliceList;

	public MarketingEmployeeGUI(CCRMClientControl control) {
		this.control = control;
		initialize();
		add(panelMARKETINGEMPLOYEE);
	}

	public void updateProducts(ListCombo list) {
		product.names = list.names;
		product.ids = list.ids;
		product.updateComboBox();
	}

	private void initialize() {
		setBounds(CCRMClientUI.defaultBigScreen);
		setLayout(null);

		initMarketingEmployee();
		initDefineNewPattern();
		initNewSlice();
		initAddProduct();
	}

	public void initMarketingEmployee() {
		panelMARKETINGEMPLOYEE = new JPanel();
		panelMARKETINGEMPLOYEE.setBounds(CCRMClientUI.defaultBigScreen);
		add(panelMARKETINGEMPLOYEE);
		panelMARKETINGEMPLOYEE.setLayout(null);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelMARKETINGEMPLOYEE.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.logOutFromServer();
			}
		});

		JButton btnDefinePattern = new JButton("Define Pattern");
		btnDefinePattern.setBounds(10, 96, 187, 23);
		panelMARKETINGEMPLOYEE.add(btnDefinePattern);
		btnDefinePattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMARKETINGEMPLOYEE);
				changePanel(panelDEFINEPATERN);
			}
		});

		JButton btnCreateSlice = new JButton("Create Slice");
		btnCreateSlice.setBounds(237, 96, 187, 23);
		panelMARKETINGEMPLOYEE.add(btnCreateSlice);
		btnCreateSlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMARKETINGEMPLOYEE);
				changePanel(panelCREATENEWSLICE);
			}
		});

		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.setBounds(10, 130, 187, 23);
		panelMARKETINGEMPLOYEE.add(btnAddProduct);
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMARKETINGEMPLOYEE);
				changePanel(panelNEWPRODUCT);
			}
		});

		JButton btnAnalyseSystem = new JButton("Analyse System");
		btnAnalyseSystem.setBounds(237, 130, 187, 23);
		panelMARKETINGEMPLOYEE.add(btnAnalyseSystem);
		btnAnalyseSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initAnalyseSystem();
			}
		});
	}

	public void initDefineNewPattern() {
		JTextField newPatternName, newPatternMessage;
		panelDEFINEPATERN = new JPanel();
		panelDEFINEPATERN.setBounds(CCRMClientUI.defaultBigScreen);
		panelDEFINEPATERN.setLayout(null);

		JLabel lblNewPatternName = new JLabel("New pattern name:");
		lblNewPatternName.setBounds(10, 11, 92, 14);
		panelDEFINEPATERN.add(lblNewPatternName);

		sliceList = new JComboBox<String>();
		sliceList.setBounds(130, 58, 144, 20);
		panelDEFINEPATERN.add(sliceList);

		newPatternName = new JTextField();
		newPatternName.setBounds(130, 8, 285, 20);
		panelDEFINEPATERN.add(newPatternName);
		newPatternName.setColumns(10);

		JLabel lblProductName = new JLabel("Choose product:");
		lblProductName.setBounds(10, 36, 92, 14);
		panelDEFINEPATERN.add(lblProductName);

		JLabel lblSliceName = new JLabel("Choose slice:");
		lblSliceName.setBounds(10, 61, 92, 14);
		panelDEFINEPATERN.add(lblSliceName);

		JLabel lblMessageToClients = new JLabel("Message to clients:");
		lblMessageToClients.setBounds(10, 86, 110, 14);
		panelDEFINEPATERN.add(lblMessageToClients);

		newPatternMessage = new JTextField();
		newPatternMessage.setBounds(130, 83, 285, 20);
		panelDEFINEPATERN.add(newPatternMessage);
		newPatternMessage.setColumns(10);

		JButton btnCreatePattern = new JButton("Create Pattern");
		btnCreatePattern.setBounds(0, getHeight() - 51, 150, 23);
		panelDEFINEPATERN.add(btnCreatePattern);
		btnCreatePattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(newPatternName, newPatternMessage))
					control.addPatternToDB(newPatternName.getText(), product.names.get(product.list.getSelectedIndex()), slice.names.get(slice.list.getSelectedIndex()), newPatternMessage.getText());
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelDEFINEPATERN.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prevPanel();
			}
		});

		JButton btnCreateNewSlice = new JButton("Create new slice");
		btnCreateNewSlice.setBounds(284, 57, 131, 23);
		panelDEFINEPATERN.add(btnCreateNewSlice);
		btnCreateNewSlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelDEFINEPATERN);
				changePanel(panelCREATENEWSLICE);
			}
		});
	}

	public void initNewSlice() {

		JTextField sliceMaxAge, sliceMinAge, sliceName;

		panelCREATENEWSLICE = new JPanel();
		panelCREATENEWSLICE.setBounds(CCRMClientUI.defaultBigScreen);

		panelCREATENEWSLICE.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(39, 38, 55, 14);
		panelCREATENEWSLICE.add(lblName);

		JLabel lblMinAge = new JLabel("Min Age:");
		lblMinAge.setBounds(39, 63, 55, 14);
		panelCREATENEWSLICE.add(lblMinAge);

		JLabel lblMaxAge = new JLabel("Max Age:");
		lblMaxAge.setBounds(39, 88, 55, 14);
		panelCREATENEWSLICE.add(lblMaxAge);

		JLabel lblArea = new JLabel("Area:");
		lblArea.setBounds(39, 113, 55, 14);
		panelCREATENEWSLICE.add(lblArea);

		sliceMaxAge = new JTextField();
		sliceMaxAge.setBounds(118, 85, 150, 20);
		panelCREATENEWSLICE.add(sliceMaxAge);
		sliceMaxAge.setColumns(10);

		sliceMinAge = new JTextField();
		sliceMinAge.setBounds(118, 60, 150, 20);
		panelCREATENEWSLICE.add(sliceMinAge);
		sliceMinAge.setColumns(10);

		sliceName = new JTextField();
		sliceName.setBounds(118, 35, 150, 20);
		panelCREATENEWSLICE.add(sliceName);
		sliceName.setColumns(10);

		JButton btnCancel1 = new JButton("Cancel");
		btnCancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prevPanel();
			}
		});
		btnCancel1.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelCREATENEWSLICE.add(btnCancel1);

		JButton btnDone1 = new JButton("Done");
		btnDone1.setBounds(0, getHeight() - 51, 150, 23);
		btnDone1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(sliceName, sliceMinAge, sliceMaxAge))
					if (isFieldNumber(10, 100, sliceMinAge, sliceMaxAge))
						if (Integer.parseInt(sliceMinAge.getText()) < Integer.parseInt(sliceMaxAge.getText())) {
							control.addSliceToDB(sliceName.getText(), Integer.parseInt(sliceMinAge.getText()), Integer.parseInt(sliceMaxAge.getText()), area.ids.get(area.list.getSelectedIndex()));
							clearFields(sliceName, sliceMinAge, sliceMaxAge);
							prevPanel();
						}
			}
		});
		panelCREATENEWSLICE.add(btnDone1);
	}

	public void initAddProduct() {
		final JFileChooser fc = new JFileChooser();
		imageFile = new File("ProductImages/default.jpg");

		panelNEWPRODUCT = new JPanel();
		panelNEWPRODUCT.setBounds(CCRMClientUI.defaultBigScreen);

		panelNEWPRODUCT.setLayout(null);

		JLabel lblName1 = new JLabel("Name:");
		lblName1.setBounds(35, 36, 55, 14);
		panelNEWPRODUCT.add(lblName1);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(35, 61, 90, 14);
		panelNEWPRODUCT.add(lblDescription);

		JLabel lblImage = new JLabel("Image:");
		lblImage.setBounds(35, 86, 55, 14);
		panelNEWPRODUCT.add(lblImage);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(35, 111, 55, 14);
		panelNEWPRODUCT.add(lblPrice);

		productName = new JTextField();
		productName.setBounds(110, 33, 150, 20);
		panelNEWPRODUCT.add(productName);
		productName.setColumns(10);

		productDesc = new JTextField();
		productDesc.setBounds(110, 58, 150, 20);
		panelNEWPRODUCT.add(productDesc);
		productDesc.setColumns(10);

		productPrice = new JTextField();
		productPrice.setBounds(110, 108, 150, 20);
		panelNEWPRODUCT.add(productPrice);
		productPrice.setColumns(10);

		JButton btnCancel11 = new JButton("Back");
		btnCancel11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields(productName, productDesc, productPrice);
				prevPanel();
			}
		});
		btnCancel11.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelNEWPRODUCT.add(btnCancel11);

		JButton btnDone11 = new JButton("Done");
		btnDone11.setBounds(0, getHeight() - 51, 150, 23);
		panelNEWPRODUCT.add(btnDone11);
		btnDone11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(productName, productDesc, productPrice))
					if (isFieldNumber(0, 99999, productPrice)) {
						control.addProductToDB(productName.getText(), productDesc.getText(), imageFile, Integer.parseInt(productPrice.getText()));
						clearFields(productName, productDesc, productPrice);
						prevPanel();
					}
			}
		});

		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(110, 82, 150, 23);
		panelNEWPRODUCT.add(btnUpload);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.showOpenDialog(MarketingEmployeeGUI.this);
				imageFile = new File(fc.getSelectedFile().toString());
			}
		});
	}

	public void initAnalyseSystem() {
		analyseSystem = new JFrame();
		analyseSystem.setBounds(100, 100, 300, 150);
		analyseSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		analyseSystem.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Analyse");
		btnNewButton.setBounds(10, 78, 166, 23);
		analyseSystem.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.startSystemAnalyse();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(185, 78, 89, 23);
		analyseSystem.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				analyseSystem.dispose();
			}
		});

		JLabel lblToStartAnalysing = new JLabel("To start Analysing the system -");
		lblToStartAnalysing.setBounds(10, 11, 264, 14);
		analyseSystem.getContentPane().add(lblToStartAnalysing);

		JLabel lblPressTheanalyse = new JLabel("press the 'Analyse' button below");
		lblPressTheanalyse.setBounds(10, 36, 197, 14);
		analyseSystem.getContentPane().add(lblPressTheanalyse);
		analyseSystem.setVisible(true);
	}

	public void changePanel(JPanel panel) {
		removeAll();
		add(panel);
		invalidate();
		repaint();
	}

	public void prevPanel() {
		changePanel(lastPanel.pop());
	}

	@Override
	public void updateFields(Object... field) {
		area = (ListCombo) field[0];
		product = (ListCombo) field[1];
		slice = (ListCombo) field[2];
		fields = (ListCombo) field[3];

		area.list.setBounds(118, 110, 150, 20);
		panelCREATENEWSLICE.add(area.list);

		// slice.list.setBounds(130, 58, 144, 20);
		// panelDEFINEPATERN.add(slice.list);

		product.list.setBounds(270, 33, 145, 20);
		panelDEFINEPATERN.add(product.list);

		fields.list.setBounds(130, 33, 140, 20);
		panelDEFINEPATERN.add(fields.list);

		fields.list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fields.list.getSelectedIndex() > 0)
					control.askForProductUpdateEmp(fields.ids.get(fields.list.getSelectedIndex()));
			}
		});

		if (sliceList.getItemCount() > 0)
			sliceList.removeAllItems();
		for (String name : slice.names) {
			sliceList.addItem(name);
		}
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

	public boolean isFieldNumber(int min, int max, JTextField... field) {
		for (int i = 0; i < field.length; i++) {
			try {
				int n = Integer.parseInt(field[i].getText());
				if (n < min || n > max) {
					CCRMClientControl.infoBox("Please fill all the fields", "", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (Exception e) {
				CCRMClientControl.infoBox("Entered illigal number", "", JOptionPane.ERROR_MESSAGE);
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
