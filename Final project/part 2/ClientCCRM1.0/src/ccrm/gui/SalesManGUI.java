package ccrm.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

import ccrm.client.CCRMClientControl;
import ccrm.client.CCRMClientUI;
import common.CostumerDetails;
import common.ImageFile;
import common.ProductDetails;

public class SalesManGUI extends JPanel implements ClientGUIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel panelSALESMAN, panelOPENLIST, panelMAKECALL, panelLISTWITHCLIENTS;
	CCRMClientControl control;
	public ArrayList<Integer> listOfIds, opId;
	public ArrayList<String> listOfNames;
	private JComboBox<String> listList, costList;;
	private ArrayList<CostumerDetails> costumerList;
	private ProductDetails product;
	private CostumerDetails costumer;

	private boolean yesNoFlag;
	private JTextField clientComm;
	private String messageToClients;

	private int callLength;
	private JTextField length;
	private Timer timer;
	private int secondsPassed;
	private int minutesPassed;

	JTextField textFieldNAME, textFieldFAMILY, textFieldAGE, textFieldPHONE, textFieldPNAME, textFieldPDET, textFieldPPRICE;

	private JFrame addPermissionFrame, finishCall, imagePreview;

	private int selectedOpId;
	private JTextField callTime;

	private JComboBox<String> fieldList;
	private ArrayList<String> fieldNames;
	private ArrayList<Integer> fieldIds;
	private JTextField msgText;

	public SalesManGUI(CCRMClientControl control) {
		this.control = control;
		initialize();
		add(panelLISTWITHCLIENTS);
	}

	private void initialize() {
		setBounds(CCRMClientUI.defaultBigScreen);
		setLayout(null);

		initListWithClients();
		initFinishCall();
		initMakePermissionFrame();
		initMakeCall();
	}

	public void setList(ArrayList<String> listName, ArrayList<Integer> listId, ArrayList<Integer> opId) {
		listList.removeAllItems();
		costList.removeAllItems();

		listOfNames = listName;
		listOfIds = listId;
		this.opId = opId;

		for (String x : listOfNames)
			listList.addItem(x);
	}

	public void setCostumerList(ArrayList<CostumerDetails> list, ProductDetails pd, String msg) {
		messageToClients = msg;
		msgText.setText(messageToClients);

		costList.removeAllItems();

		costumerList = list;
		product = pd;

		for (CostumerDetails x : list)
			costList.addItem(x.name + " " + x.lastname);

		textFieldPNAME.setText(pd.name);
		textFieldPDET.setText(pd.desc);
		textFieldPPRICE.setText("" + pd.price);

		ImageFile.saveImageFile(pd.image, "ProductImages", "recieved");
	}

	private void updateClientDetails(String name) {
		for (int i = 0; i < costumerList.size(); i++) {
			String fullName = costumerList.get(i).name + " " + costumerList.get(i).lastname;
			if (fullName.equals(name)) {
				costumer = costumerList.get(i);
				textFieldNAME.setText(costumerList.get(i).name);
				textFieldFAMILY.setText(costumerList.get(i).lastname);
				textFieldAGE.setText(costumerList.get(i).age + "");
				textFieldPHONE.setText(costumerList.get(i).phone);
			}
		}
	}

	private void startCountingTime() {
		secondsPassed = 0;
		minutesPassed = 0;

		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (++secondsPassed == 60) {
					secondsPassed = 0;
					minutesPassed++;
				}
				callTime.setText(minutesPassed + ":" + secondsPassed);
			}
		});

		timer.start();
	}

	private void stopCountingTime() {
		timer.stop();
		callLength = minutesPassed * 60 + secondsPassed;
	}

	private void restartCountingTime() {

	}

	private void initListWithClients() {
		panelLISTWITHCLIENTS = new JPanel();
		panelLISTWITHCLIENTS.setBounds(CCRMClientUI.defaultBigScreen);
		add(panelLISTWITHCLIENTS);
		panelLISTWITHCLIENTS.setLayout(null);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(getWidth() - 156, getHeight() - 51, 150, 23);
		panelLISTWITHCLIENTS.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.logOutFromServer();
			}
		});

		JLabel lblChooseOneOf = new JLabel("Choose  one of the available operations");
		lblChooseOneOf.setBounds(28, 43, 277, 14);
		panelLISTWITHCLIENTS.add(lblChooseOneOf);

		listList = new JComboBox<String>();
		listList.setBounds(28, 68, 193, 20);
		panelLISTWITHCLIENTS.add(listList);
		listList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				control.askForCostumerList(listOfIds.get(listList.getSelectedIndex()));
				selectedOpId = opId.get(listList.getSelectedIndex());
				costList.setEnabled(true);
			}
		});

		JLabel lblChooseOneOf_1 = new JLabel("Choose one of the clients:");
		lblChooseOneOf_1.setBounds(28, 127, 193, 14);
		panelLISTWITHCLIENTS.add(lblChooseOneOf_1);

		costList = new JComboBox<String>();
		costList.setBounds(28, 152, 193, 20);
		costList.setEnabled(false);
		panelLISTWITHCLIENTS.add(costList);

		JButton btnNewButton = new JButton("Call The Client");
		btnNewButton.setBounds(0, getHeight() - 51, 150, 23);
		panelLISTWITHCLIENTS.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateClientDetails(costList.getSelectedItem().toString());
				startCountingTime();
				changePanel(panelMAKECALL);
			}
		});
	}

	private void initMakeCall() {
		panelMAKECALL = new JPanel();
		panelMAKECALL.setBounds(CCRMClientUI.defaultBigScreen);
		panelMAKECALL.setLayout(null);

		JButton btnNewButton = new JButton("Finish");
		btnNewButton.setBounds(0, getHeight() - 51, 89, 23);
		panelMAKECALL.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopCountingTime();
				finishCall.setVisible(true);
			}
		});

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setBounds(getWidth() - 95, getHeight() - 51, 89, 23);
		panelMAKECALL.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(panelLISTWITHCLIENTS);
			}
		});

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(224, 31, 46, 14);
		panelMAKECALL.add(lblName);

		JLabel lblFamilyName = new JLabel("Family Name:");
		lblFamilyName.setBounds(224, 56, 69, 14);
		panelMAKECALL.add(lblFamilyName);

		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(224, 81, 46, 14);
		panelMAKECALL.add(lblAge);

		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setBounds(224, 106, 86, 14);
		panelMAKECALL.add(lblPhoneNumber);

		textFieldNAME = new JTextField();
		textFieldNAME.setBounds(318, 28, 86, 20);
		panelMAKECALL.add(textFieldNAME);
		textFieldNAME.setColumns(10);
		textFieldNAME.setEditable(false);

		textFieldFAMILY = new JTextField();
		textFieldFAMILY.setBounds(318, 53, 86, 20);
		panelMAKECALL.add(textFieldFAMILY);
		textFieldFAMILY.setColumns(10);
		textFieldFAMILY.setEditable(false);

		textFieldAGE = new JTextField();
		textFieldAGE.setText("");
		textFieldAGE.setBounds(318, 78, 86, 20);
		panelMAKECALL.add(textFieldAGE);
		textFieldAGE.setColumns(10);
		textFieldAGE.setEditable(false);

		textFieldPHONE = new JTextField();
		textFieldPHONE.setText("");
		textFieldPHONE.setBounds(318, 103, 86, 20);
		panelMAKECALL.add(textFieldPHONE);
		textFieldPHONE.setColumns(10);
		textFieldPHONE.setEditable(false);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 31, 46, 14);
		panelMAKECALL.add(lblNewLabel);

		JButton btnAddPermission = new JButton("Add New Permission");
		btnAddPermission.setBounds(89, getHeight() - 51, 200, 23);
		panelMAKECALL.add(btnAddPermission);
		btnAddPermission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPermissionFrame.setVisible(true);
			}
		});

		JLabel lblDetails = new JLabel("Details:");
		lblDetails.setBounds(10, 56, 46, 14);
		panelMAKECALL.add(lblDetails);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(10, 81, 46, 14);
		panelMAKECALL.add(lblPrice);

		JLabel lblImage = new JLabel("Image:");
		lblImage.setBounds(10, 106, 46, 14);
		panelMAKECALL.add(lblImage);

		JLabel lblCostumerDetails = new JLabel("Costumer Details");
		lblCostumerDetails.setBounds(264, 6, 81, 14);
		panelMAKECALL.add(lblCostumerDetails);

		JLabel lblProductDetails = new JLabel("Product Details");
		lblProductDetails.setBounds(49, 6, 86, 14);
		panelMAKECALL.add(lblProductDetails);

		textFieldPNAME = new JTextField();
		textFieldPNAME.setBounds(66, 28, 86, 20);
		panelMAKECALL.add(textFieldPNAME);
		textFieldPNAME.setColumns(10);
		textFieldPNAME.setEditable(false);

		textFieldPDET = new JTextField();
		textFieldPDET.setBounds(66, 53, 86, 20);
		panelMAKECALL.add(textFieldPDET);
		textFieldPDET.setColumns(10);
		textFieldPDET.setEditable(false);

		textFieldPPRICE = new JTextField();
		textFieldPPRICE.setBounds(66, 78, 86, 20);
		panelMAKECALL.add(textFieldPPRICE);
		textFieldPPRICE.setColumns(10);
		textFieldPPRICE.setEditable(false);

		JButton btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initImagePreview();
			}
		});
		btnPreview.setBounds(66, 102, 86, 23);
		panelMAKECALL.add(btnPreview);

		JLabel lblTimeOfCall = new JLabel("Time of call:");
		lblTimeOfCall.setBounds(106, 144, 81, 14);
		panelMAKECALL.add(lblTimeOfCall);

		callTime = new JTextField("0:0");
		callTime.setBounds(175, 141, 86, 20);
		panelMAKECALL.add(callTime);
		callTime.setColumns(10);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(32, 189, 69, 14);
		panelMAKECALL.add(lblMessage);

		msgText = new JTextField();
		msgText.setBounds(101, 186, 300, 20);
		msgText.setEditable(false);
		panelMAKECALL.add(msgText);
		msgText.setColumns(10);
	}

	private void initMakePermissionFrame() {
		addPermissionFrame = new JFrame("Add Permission");
		addPermissionFrame.getContentPane().setLayout(new FlowLayout());

		fieldList = new JComboBox<String>();
		fieldList.setPreferredSize(new Dimension(100, 30));
		addPermissionFrame.getContentPane().add(fieldList);

		JButton addPermissionBtn = new JButton("Add the field");
		addPermissionBtn.setPreferredSize(new Dimension(100, 30));
		addPermissionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.addPermissionToClient(fieldNames.get(fieldList.getSelectedIndex()), costumer.name);
				addPermissionFrame.dispose();
			}
		});
		addPermissionFrame.getContentPane().add(addPermissionBtn);
		addPermissionFrame.pack();
	}

	private void initFinishCall() {
		finishCall = new JFrame();
		finishCall.setBounds(100, 100, 300, 150);
		finishCall.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finishCall.getContentPane().setLayout(null);
		JPanel panel = new JPanel();

		yesNoFlag = true;

		JTextField textField = null;

		JLabel lblDidClientDecided = new JLabel("Did client decided to buy the product?");
		lblDidClientDecided.setBounds(10, 11, 264, 14);
		finishCall.getContentPane().add(lblDidClientDecided);

		JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBounds(50, 30, 50, 23);
		finishCall.getContentPane().add(rdbtnYes);
		rdbtnYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				yesNoFlag = true;
				panel.setVisible(false);
			}
		});

		JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBounds(110, 30, 109, 23);
		finishCall.getContentPane().add(rdbtnNo);
		rdbtnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				yesNoFlag = false;
				panel.setVisible(true);
			}
		});

		ButtonGroup clientChoice = new ButtonGroup();
		clientChoice.add(rdbtnYes);
		clientChoice.add(rdbtnNo);

		panel.setBounds(10, 36, 264, 41);
		finishCall.getContentPane().add(panel);
		panel.setLayout(null);
		panel.setVisible(false);

		JLabel lblWhyNot = new JLabel("Why not?");
		lblWhyNot.setBounds(10, 20, 46, 14);
		panel.add(lblWhyNot);

		clientComm = new JTextField();
		clientComm.setBounds(67, 12, 187, 20);
		panel.add(clientComm);
		clientComm.setColumns(10);

		JButton btnFinishYes = new JButton("Finish");
		btnFinishYes.setBounds(0, 88, 89, 23);
		finishCall.getContentPane().add(btnFinishYes);
		btnFinishYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String comm = "Purchased";
				if (!yesNoFlag)
					comm = clientComm.getText();

				control.addComment(costumer, product, comm, callLength, selectedOpId, Integer.parseInt(product.price));
				changePanel(panelLISTWITHCLIENTS);
				finishCall.dispose();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(195, 88, 89, 23);
		finishCall.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finishCall.dispose();
			}
		});
	}

	private void initImagePreview() {
		imagePreview = new JFrame();
		imagePreview.getContentPane().setLayout(null);
		imagePreview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon image = new ImageIcon("ProductImages/recieved.jpg");
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image);
		imageLabel.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		imagePreview.getContentPane().add(imageLabel);

		JButton closeWindow = new JButton("Close");
		closeWindow.setBounds(image.getIconWidth() / 2 - 50, image.getIconHeight() + 20, 100, 20);
		closeWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				imagePreview.dispose();
			}
		});
		imagePreview.getContentPane().add(closeWindow);

		imagePreview.setSize(image.getIconWidth() + 20, image.getIconHeight() + 100);
		imagePreview.setVisible(true);
	}

	public void changePanel(JPanel panel) {
		removeAll();
		add(panel);
		invalidate();
		repaint();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateFields(Object... field) {
		fieldNames = (ArrayList<String>) field[0];
		fieldIds = (ArrayList<Integer>) field[1];

		for (String name : fieldNames)
			fieldList.addItem(name);
	}

	@Override
	public boolean testAllFields(JTextField... field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearFields(JTextField... field) {
		for (JTextField f : field)
			f.setText("");
	}
}
