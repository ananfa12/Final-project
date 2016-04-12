package ccrm.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import ccrm.client.CCRMClientControl;
import ccrm.client.CCRMClientUI;
import common.ListCombo;

public class MarketingManagerGUI extends JPanel implements ClientGUIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelMAIN, panelCREATEPATTERN, panelCREATETYPE, panelDEFINETYPES, panelINITIATEOP, panelCREATENEWSLICE, panelNEWPRODUCT;
	JTextField newOpOperationName, newOpPatternName;
	private Rectangle defaultScreenSize = new Rectangle(0, 0, 450, 300);
	CCRMClientControl control;
	File imageFile;
	private Stack<JPanel> lastPanel = new Stack<JPanel>();

	JFrame makeReport, makeField;

	private ListCombo slice, product, product2, area, fields, operation, pattern;
	private JComboBox<String> fieldList, fieldList2, sliceList, patternList;

	public MarketingManagerGUI(CCRMClientControl c) {
		this.control = c;
		initialize();
		add(panelMAIN);
	}

	private void initialize() {
		setLayout(null);
		setBounds(defaultScreenSize);

		initPanelMain();
		initDefineNewPattern();
		initDefineProductTypeField();
		initCreateOperation();
		initNewSlice();
		initAddProduct();
	}

	public void updateProducts(ListCombo list) {
		product.names = list.names;
		product.ids = list.ids;
		product.updateComboBox();
	}

	public void initPanelMain() {
		panelMAIN = new JPanel();
		panelMAIN.setBounds(defaultScreenSize);
		panelMAIN.setLayout(null);
		add(panelMAIN);

		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelMAIN.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.logOutFromServer();
			}
		});

		JButton btnInitiateOperation = new JButton("Initiate Operation");
		btnInitiateOperation.setBounds(10, 51, 191, 23);
		panelMAIN.add(btnInitiateOperation);
		btnInitiateOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMAIN);
				changePanel(panelINITIATEOP);
			}
		});

		JButton btnDefineFields = new JButton("Define Fields");
		btnDefineFields.setBounds(10, 85, 191, 23);
		panelMAIN.add(btnDefineFields);
		btnDefineFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMAIN);
				changePanel(panelDEFINETYPES);
			}
		});

		JButton btnDefinePattern = new JButton("Define Pattern");
		btnDefinePattern.setBounds(233, 85, 191, 23);
		panelMAIN.add(btnDefinePattern);
		btnDefinePattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMAIN);
				changePanel(panelCREATEPATTERN);
			}
		});

		JButton btnCreateSlice = new JButton("Create Slice");
		btnCreateSlice.setBounds(233, 51, 191, 23);
		panelMAIN.add(btnCreateSlice);
		btnCreateSlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMAIN);
				changePanel(panelCREATENEWSLICE);
			}
		});

		JButton btnReports = new JButton("Reports");
		btnReports.setBounds(10, 119, 191, 23);
		panelMAIN.add(btnReports);
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initMakeReport();
			}
		});

		JButton btnCreateField = new JButton("Create Field");
		btnCreateField.setBounds(10, 153, 191, 23);
		panelMAIN.add(btnCreateField);
		btnCreateField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initCreateNewField();
			}
		});

		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.setBounds(233, 119, 191, 23);
		panelMAIN.add(btnAddProduct);
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelMAIN);
				changePanel(panelNEWPRODUCT);
			}
		});

		JLabel lblManagerPanel = new JLabel("Manager Panel");
		lblManagerPanel.setBounds(65, 26, 103, 14);
		panelMAIN.add(lblManagerPanel);

		JLabel lblEmployeePanel = new JLabel("Employee Panel");
		lblEmployeePanel.setBounds(283, 26, 114, 14);
		panelMAIN.add(lblEmployeePanel);
	}

	public void initDefineNewPattern() {
		JTextField newPatternName, newPatternMessage;
		panelCREATEPATTERN = new JPanel();
		panelCREATEPATTERN.setBounds(CCRMClientUI.defaultBigScreen);
		panelCREATEPATTERN.setLayout(null);

		JLabel lblNewPatternName = new JLabel("New pattern name:");
		lblNewPatternName.setBounds(10, 11, 92, 14);
		panelCREATEPATTERN.add(lblNewPatternName);

		newPatternName = new JTextField();
		newPatternName.setBounds(130, 8, 285, 20);
		panelCREATEPATTERN.add(newPatternName);
		newPatternName.setColumns(10);

		JLabel lblProductName = new JLabel("Choose product:");
		lblProductName.setBounds(10, 36, 92, 14);
		panelCREATEPATTERN.add(lblProductName);

		fieldList = new JComboBox<String>();
		fieldList.setBounds(130, 33, 140, 20);
		panelCREATEPATTERN.add(fieldList);
		fieldList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fieldList.getSelectedIndex() > 0)
					control.askForProductUpdate(fields.ids.get(fieldList.getSelectedIndex()));
			}
		});

		JLabel lblSliceName = new JLabel("Choose slice:");
		lblSliceName.setBounds(10, 61, 92, 14);
		panelCREATEPATTERN.add(lblSliceName);

		sliceList = new JComboBox<String>();
		sliceList.setBounds(130, 58, 144, 20);
		panelCREATEPATTERN.add(sliceList);

		JLabel lblMessageToClients = new JLabel("Message to clients:");
		lblMessageToClients.setBounds(10, 86, 110, 14);
		panelCREATEPATTERN.add(lblMessageToClients);

		newPatternMessage = new JTextField();
		newPatternMessage.setBounds(130, 83, 285, 20);
		panelCREATEPATTERN.add(newPatternMessage);
		newPatternMessage.setColumns(10);

		JButton btnCreatePattern = new JButton("Create Pattern");
		btnCreatePattern.setBounds(0, getHeight() - 51, 150, 23);
		panelCREATEPATTERN.add(btnCreatePattern);
		btnCreatePattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(newPatternName, newPatternMessage)) {
					System.out.println(product.list.getSelectedIndex());
					System.out.println(product.names.get(product.list.getSelectedIndex()));
					control.addPatternToDB(newPatternName.getText(), product.names.get(product.list.getSelectedIndex()), slice.names.get(sliceList.getSelectedIndex()), newPatternMessage.getText());
					clearFields(newPatternName, newPatternMessage);
				}
			}
		});

		JButton btnCancel = new JButton("Back");
		btnCancel.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelCREATEPATTERN.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields(newPatternName, newPatternMessage);
				prevPanel();
			}
		});

		JButton btnCreateNewSlice = new JButton("Create new slice");
		btnCreateNewSlice.setBounds(284, 57, 131, 23);
		panelCREATEPATTERN.add(btnCreateNewSlice);
		btnCreateNewSlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelCREATEPATTERN);
				changePanel(panelCREATENEWSLICE);
			}
		});
	}

	public void initCreateNewType() {
		panelCREATETYPE = new JPanel();
		panelCREATETYPE.setBounds(defaultScreenSize);
		panelCREATETYPE.setLayout(null);

		JLabel lblNewTypesName = new JLabel("new type's name:");
		lblNewTypesName.setBounds(10, 11, 149, 14);
		panelCREATETYPE.add(lblNewTypesName);

		JTextField textField = new JTextField();
		textField.setBounds(144, 8, 183, 20);
		panelCREATETYPE.add(textField);
		textField.setColumns(10);

		JButton btnCreateType = new JButton("create");
		btnCreateType.setBounds(55, 123, 89, 23);
		panelCREATETYPE.add(btnCreateType);
		btnCreateType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.addTypeToDB(textField.getText());
			}
		});

		JButton btnCancelType = new JButton("cancel");
		btnCancelType.setBounds(170, 123, 89, 23);
		panelCREATETYPE.add(btnCancelType);
		btnCancelType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePanel(panelMAIN);
			}
		});
	}

	public void initCreateNewField() {
		JTextField textField;

		makeField = new JFrame();
		makeField.setBounds(100, 100, 300, 150);
		makeField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeField.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(123, 37, 131, 20);
		makeField.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(195, 89, 89, 23);
		makeField.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				makeField.dispose();
			}
		});

		JButton btnAddField = new JButton("Add Field");
		btnAddField.setBounds(0, 89, 89, 23);
		makeField.getContentPane().add(btnAddField);
		btnAddField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.addFieldToDB(textField.getText());
				makeField.dispose();
			}
		});

		JLabel lblNewFieldName = new JLabel("New field name:");
		lblNewFieldName.setBounds(21, 40, 89, 14);
		makeField.getContentPane().add(lblNewFieldName);

		makeField.setVisible(true);
	}

	public void initDefineProductTypeField() {
		panelDEFINETYPES = new JPanel();
		panelDEFINETYPES.setBounds(defaultScreenSize);
		panelDEFINETYPES.setLayout(null);

		JLabel lblExistingProductName = new JLabel("Choose product:");
		lblExistingProductName.setBounds(40, 11, 120, 14);
		panelDEFINETYPES.add(lblExistingProductName);

		JLabel lblAddField = new JLabel("Choose field:");
		lblAddField.setBounds(10, 105, 80, 14);
		panelDEFINETYPES.add(lblAddField);

		fieldList2 = new JComboBox<String>();
		fieldList2.setBounds(101, 102, 130, 20);
		panelDEFINETYPES.add(fieldList2);

		JButton btnNewField = new JButton("Create new field");
		btnNewField.setBounds(260, 101, 140, 23);
		panelDEFINETYPES.add(btnNewField);
		btnNewField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initCreateNewField();
			}
		});

		JButton btnConfirm = new JButton("Confirm Changes");
		btnConfirm.setBounds(0, getHeight() - 51, 150, 23);
		panelDEFINETYPES.add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				control.defineTypeAndField(product2.ids.get(product2.list.getSelectedIndex()), fields.ids.get(fields.list.getSelectedIndex()));
			}
		});

		JButton btnCancel = new JButton("Back");
		btnCancel.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelDEFINETYPES.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prevPanel();
			}
		});
	}

	public void initCreateOperation() {
		panelINITIATEOP = new JPanel();
		panelINITIATEOP.setBounds(defaultScreenSize);
		panelINITIATEOP.setLayout(null);

		JLabel lblNewOperationName = new JLabel("New operation name:");
		lblNewOperationName.setBounds(30, 41, 121, 14);
		panelINITIATEOP.add(lblNewOperationName);

		newOpOperationName = new JTextField();
		newOpOperationName.setBounds(161, 38, 179, 20);
		panelINITIATEOP.add(newOpOperationName);
		newOpOperationName.setColumns(10);

		patternList = new JComboBox<String>();
		patternList.setBounds(161, 63, 179, 20);
		panelINITIATEOP.add(patternList);

		JLabel lblUseExistingPattern = new JLabel("Choose pattern:");
		lblUseExistingPattern.setBounds(30, 66, 121, 14);
		panelINITIATEOP.add(lblUseExistingPattern);

		JButton btnCreateNewPattern = new JButton("Create new pattern");
		btnCreateNewPattern.setBounds(122, 95, 141, 23);
		panelINITIATEOP.add(btnCreateNewPattern);
		btnCreateNewPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lastPanel.push(panelINITIATEOP);
				changePanel(panelCREATEPATTERN);
			}
		});

		JButton btnInitiate = new JButton("Create Operation");
		btnInitiate.setBounds(0, getHeight() - 51, 150, 23);
		panelINITIATEOP.add(btnInitiate);
		btnInitiate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testAllFields(newOpOperationName)) {
					control.addOperationToDB(newOpOperationName.getText(), pattern.names.get(patternList.getSelectedIndex()));
					clearFields(newOpOperationName);
				}
			}
		});

		JButton btnCancelInitiateOp = new JButton("Back");
		btnCancelInitiateOp.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelINITIATEOP.add(btnCancelInitiateOp);
		btnCancelInitiateOp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearFields(newOpOperationName);
				prevPanel();
			}
		});
	}

	public void initNewSlice() {

		JTextField sliceMaxAge, sliceMinAge, sliceName;

		panelCREATENEWSLICE = new JPanel();
		panelCREATENEWSLICE.setBounds(defaultScreenSize);

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
				clearFields(sliceName, sliceMinAge, sliceMaxAge);
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
						} else
							CCRMClientControl.infoBox("Minimum age can't be higher than maximum age", "", JOptionPane.ERROR_MESSAGE);
			}
		});
		panelCREATENEWSLICE.add(btnDone1);
	}

	public void initAddProduct() {
		final JFileChooser fc = new JFileChooser();
		imageFile = new File("ProductImages/default.jpg");

		JTextField productName, productDesc, productPrice;

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

		JButton btnCancel11 = new JButton("Cancel");
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
				fc.showOpenDialog(MarketingManagerGUI.this);
				imageFile = new File(fc.getSelectedFile().toString());
			}
		});
	}

	private void initMakeReport() {
		makeReport = new JFrame();
		makeReport.setBounds(100, 100, 300, 150);
		makeReport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeReport.getContentPane().setLayout(null);
		makeReport.setResizable(false);

		JButton btnNewButton = new JButton("Operation's report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.askForReport(operation.ids.get(operation.list.getSelectedIndex()));
			}
		});
		btnNewButton.setBounds(10, 36, 153, 23);
		makeReport.getContentPane().add(btnNewButton);

		JButton btnCancel = new JButton("Back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeReport.dispose();
			}
		});
		btnCancel.setBounds(205, 100, 89, 23);
		makeReport.getContentPane().add(btnCancel);

		JLabel lblToStartAnalysing = new JLabel("What type of report do you wish to produce?");
		lblToStartAnalysing.setBounds(10, 11, 264, 14);
		makeReport.getContentPane().add(lblToStartAnalysing);

		JButton btnNewButton_1 = new JButton("Costumer Summary");
		btnNewButton_1.setBounds(10, 59, 153, 23);
		makeReport.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.askForUserReport();
			}
		});

		operation.list.setBounds(173, 37, 101, 20);
		makeReport.getContentPane().add(operation.list);

		makeReport.setVisible(true);
	}

	public void printOp(ArrayList<String> text) {
		JFrame reportFrame = new JFrame();
		reportFrame.setBounds(100, 100, 285, 450);
		reportFrame.setLayout(null);
		reportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reportFrame.setResizable(false);

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 260, 340);
		textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		reportFrame.add(textArea);

		for (int i = 0; i < text.size(); i++)
			textArea.append(text.get(i) + "\n");

		MyButton close = new MyButton("Close Report", 60, 370, 153, 23);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reportFrame.dispose();
			}
		});
		reportFrame.add(close);

		reportFrame.setVisible(true);
	}

	public void changePanel(JPanel panel) {
		removeAll();
		add(panel);
		invalidate();
		repaint();
	}

	@Override
	public void updateFields(Object... field) {
		slice = (ListCombo) field[0];
		product = (ListCombo) field[1];
		product2 = new ListCombo(product.names, product.ids);
		area = (ListCombo) field[2];
		fields = (ListCombo) field[3];
		operation = (ListCombo) field[4];
		pattern = (ListCombo) field[5];

		product.list.setBounds(130 + 140, 33, 145, 20);
		panelCREATEPATTERN.add(product.list);

		product2.list.setBounds(140, 8, 160, 20);
		panelDEFINETYPES.add(product2.list);

		area.list.setBounds(118, 110, 150, 20);
		panelCREATENEWSLICE.add(area.list);

		if (fieldList.getItemCount() > 0)
			fieldList.removeAllItems();
		for (String name : fields.names) {
			fieldList.addItem(name);
		}

		if (fieldList2.getItemCount() > 0)
			fieldList2.removeAllItems();
		for (String name : fields.names) {
			fieldList2.addItem(name);
		}

		if (sliceList.getItemCount() > 0)
			sliceList.removeAllItems();
		for (String name : slice.names) {
			sliceList.addItem(name);
		}

		if (patternList.getItemCount() > 0)
			patternList.removeAllItems();
		for (String name : pattern.names) {
			patternList.addItem(name);
		}
	}

	public void prevPanel() {
		changePanel(lastPanel.pop());
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
					CCRMClientControl.infoBox("Entered illigal number", "", JOptionPane.ERROR_MESSAGE);
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
