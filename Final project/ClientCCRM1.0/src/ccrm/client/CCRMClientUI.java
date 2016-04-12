package ccrm.client;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import ccrm.gui.CostumerRelationsGUI;
import ccrm.gui.LogInGUI;
import ccrm.gui.MarketingEmployeeGUI;
import ccrm.gui.MarketingManagerGUI;
import ccrm.gui.SalesManGUI;

import common.CostumerDetails;
import common.ListCombo;
import common.ProductDetails;

public class CCRMClientUI extends JFrame {
	private static final long serialVersionUID = 1L;

	CCRMClientControl clientControl;
	
	public static final Rectangle defaultBigScreen = new Rectangle(0, 0, 450, 300);
	public static final Rectangle defaultSmallScreen = new Rectangle(0, 0, 300, 150);

	protected JPanel loginGUI, marketingManagerGUI, salesManGUI, marketingEmployeeGUI, costumerRelationshipsGUI;

	public CCRMClientUI(CCRMClientControl control) {
		clientControl = control;

		setUIFont(new FontUIResource("Arial", Font.PLAIN, 11));

		loginGUI = new LogInGUI(clientControl);
		marketingManagerGUI = new MarketingManagerGUI(clientControl);
		salesManGUI = new SalesManGUI(clientControl);
		marketingEmployeeGUI = new MarketingEmployeeGUI(clientControl);
		costumerRelationshipsGUI = new CostumerRelationsGUI(clientControl);

		setContentPane(loginGUI);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
	}

	public void setList(ArrayList<String> listName, ArrayList<Integer> listId, ArrayList<Integer> opId) {
		((SalesManGUI) salesManGUI).setList(listName, listId, opId);
	}

	public void setCostList(ArrayList<CostumerDetails> list, ProductDetails pd, String msg) {
		((SalesManGUI) salesManGUI).setCostumerList(list, pd, msg);
	}

	public void printReportOp(ArrayList<String> text) {
		((MarketingManagerGUI)marketingManagerGUI).printOp(text);
	}

	public void updateProducts(ListCombo listCombo) {
		((MarketingManagerGUI)marketingManagerGUI).updateProducts(listCombo);
	}
	
	public void updateProductsEmp(ListCombo listCombo) {
		((MarketingEmployeeGUI)marketingEmployeeGUI).updateProducts(listCombo);
	}
	
//	public void updateFields(ListCombo listCombo) {
//		((MarketingManagerGUI)marketingManagerGUI).updateFields(listCombo);
//	}
	
//	public void updateSlices(ListCombo listCombo) {
//		((MarketingManagerGUI)marketingManagerGUI).updateSlices(listCombo);
//	}
	
	public static void setUIFont(FontUIResource f) {
		UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 10));
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 11));
	}


}
