package ccrm.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ocsf.client.AbstractClient;
import ccrm.gui.ClientGUIInterface;
import common.CostumerDetails;
import common.ImageFile;
import common.ListCombo;
import common.MessageFromClient;
import common.MessageFromServer;
import common.ProductDetails;
import common.Task;

public class CCRMClientControl extends AbstractClient {

	/**
	 * the user interface of the client
	 */
	private CCRMClientUI clientUI;

	/**
	 * the username of the connected username on this client
	 */
	protected String username;

	/**
	 * 
	 * @param clientUI
	 *            getting the clientUI iinstance
	 */
	public void setParams(CCRMClientUI clientUI) {
		this.clientUI = clientUI;
	}

	/**
	 * connecting to server
	 * 
	 * @param host
	 *            ip of the server
	 * @param port
	 *            port the server is listening to
	 */
	public void connectToServer(String host, int port) {
		setHost(host);
		setPort(port);
		try {
			openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * take user login details
	 * 
	 * @param user
	 *            user name
	 * @param pass
	 *            user password
	 */
	public void loginToServer(String user, String pass) {
		sendLongMsg(Task.LOGIN, user, pass);
	}

	/**
	 * loging connected user name currently connected
	 */
	public void logOutFromServer() {
		sendLongMsg(Task.LOGOUT, username);
	}

	/**
	 * close completely the client window
	 */
	public void exitClient() {
		clientUI.dispose();
		System.exit(0);
	}

	/**
	 * 
	 * Costumer Relations
	 */

	/**
	 * add client to system
	 * 
	 * @param name
	 *            name of client
	 * @param family
	 *            family name of client
	 * @param age
	 *            client's age
	 * @param city
	 *            city where client live
	 * @param phone
	 *            client's phone number
	 */
	public void addClientToDB(String name, String family, int age, int city, String phone) {
		sendLongMsg(Task.ADDUSER, name, family, age, city, phone);
	}

	/**
	 * add product to system
	 * 
	 * @param name
	 *            name of new product
	 * @param desc
	 *            description of the product
	 * @param imageF
	 *            the product image
	 * @param price
	 *            the product price
	 */
	public void addProductToDB(String name, String desc, File imageF, int price) {
		ImageFile imf = ImageFile.makeImageFile(imageF);
		sendLongMsg(Task.ADDPROD, name, desc, imf, price);
	}

	/**
	 * add slice to system
	 * 
	 * @param name
	 *            slice name
	 * @param minAge
	 *            lower slice age
	 * @param maxAge
	 *            highest slice age
	 * @param areaId
	 *            the area of the slice
	 */
	public void addSliceToDB(String name, int minAge, int maxAge, int areaId) {
		sendLongMsg(Task.ADDSLICE, name, minAge, maxAge, areaId);
	}

	/**
	 * add pattern to the system
	 * 
	 * @param patternName
	 *            new pattern name
	 * @param productName
	 *            product for the new pattern
	 * @param sliceName
	 *            slice for the new pattern
	 * @param patternMsg
	 *            a message for the clients
	 */
	public void addPatternToDB(String patternName, String productName, String sliceName, String patternMsg) {
		sendLongMsg(Task.ADDPATTERN, productName, sliceName, patternMsg, patternName);
	}

	/**
	 * create new marketing operation
	 * 
	 * @param operationName
	 *            new operation name
	 * @param patternName
	 *            pattern to be used with the operation
	 */
	public void addOperationToDB(String operationName, String patternName) {
		sendLongMsg(Task.ADDOP, operationName, patternName);
	}

	/**
	 * add type to the system
	 * 
	 * @param typeName
	 *            new type name
	 */
	public void addTypeToDB(String typeName) {
		sendLongMsg(Task.ADDTYPE, typeName);
	}

	/**
	 * add field to the system
	 * 
	 * @param fieldName
	 *            new field name
	 */
	public void addFieldToDB(String fieldName) {
		sendLongMsg(Task.ADDFIELD, fieldName);
	}

	/**
	 * define an existing product with a field
	 * 
	 * @param prodId
	 *            the product id
	 * @param fieldId
	 *            the field for the chosen product
	 */
	public void defineTypeAndField(int prodId, int fieldId) {
		sendLongMsg(Task.DEFTYPE, prodId, fieldId);
	}

	/**
	 * allows sales man to ask for an updated list of lists
	 */
	public void askForSalesList() {
		sendLongMsg(Task.GETLISTS, username);
	}

	/**
	 * allows sales man to ask clients list of a list
	 * 
	 * @param listId
	 *            the list id
	 */
	public void askForCostumerList(int listId) {
		sendLongMsg(Task.GETCOSTLIST, listId);
	}

	/**
	 * add permission on a specific field to a client
	 * 
	 * @param fieldName
	 *            the new field
	 * @param userName
	 *            the client user name
	 */
	public void addPermissionToClient(String fieldName, String userName) {
		sendLongMsg(Task.ADDPERMISSON, fieldName, userName);
	}

	/**
	 * add comment about a call that was finished
	 * 
	 * @param costumer
	 *            the costumer id
	 * @param product
	 *            the product id
	 * @param comm
	 *            the comment in case of decline
	 * @param length
	 *            the total length of the call
	 * @param opid
	 *            the operation id of the sale
	 * @param price
	 *            the price of the product on sale
	 */
	public void addComment(CostumerDetails costumer, ProductDetails product, String comm, int length, int opid, int price) {
		sendLongMsg(Task.SENDCOMM, costumer, product, comm, length, opid, price);
	}

	/**
	 * allows the marketing employee to start system analyse
	 */
	public void startSystemAnalyse() {
		sendLongMsg(Task.ANALYSESYSTEM);
	}

	/**
	 * allows the marketing manager to ask for an operation report
	 * 
	 * @param opid
	 */
	public void askForReport(int opid) {
		sendLongMsg(Task.ASKREPORTOP, opid);
	}

	/**
	 * allows the marketing manager to ask for an client report
	 */
	public void askForUserReport() {
		sendLongMsg(Task.ASKREPORTUSER);
	}

	/**
	 * asking for specific field - product
	 * 
	 * @param fieldId
	 *            the field id
	 */

	public void askForProductUpdate(int fieldId) {
		sendLongMsg(Task.ASKFORPRODUCTS, fieldId);
	}

	/**
	 * asking for specific field - product - for an employee
	 * 
	 * * @param fieldId 
	 * 				the field id
	 */

	public void askForProductUpdateEmp(int fieldId) {
		sendLongMsg(Task.ASKFORPRODUCTSEMP, fieldId);
	}

	/**
	 * send message to the server
	 * 
	 * @param mfc
	 *            the message to be sent
	 */

	private void sendMSG(MessageFromClient mfc) {
		try {
			sendToServer(mfc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * allow stacking several variable to be sent to the server
	 * 
	 * @param type
	 *            the type of the message
	 * @param val
	 *            the values to stack together
	 */

	private void sendLongMsg(Task type, Object... val) {
		MessageFromClient mfc = new MessageFromClient();
		mfc.type = type;
		mfc.addLongData(val);
		sendMSG(mfc);
	}

	/**
	 * Analyze received message from server
	 * 
	 * @param msg
	 *            the message from the server
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getMsg(MessageFromServer msg) {
		MessageFromServer mfs = (MessageFromServer) msg;
		switch (mfs.task) {
		case CHNGSCRN:
			username = (String) mfs.other;
			switch (mfs.screen) {
			case LOGINSCRN:
				clientUI.setTitle("CCRM login screen");
				clientUI.setContentPane(clientUI.loginGUI);
				break;
			case MRKTEMP:
				clientUI.setTitle("CCRM marketing employee");
				clientUI.setContentPane(clientUI.marketingEmployeeGUI);
				break;
			case MRKTMGR:
				clientUI.setTitle("CCRM marketing manager");
				clientUI.setContentPane(clientUI.marketingManagerGUI);
				break;
			case SALEMAN:
				askForSalesList();
				clientUI.setTitle("CCRM sales man");
				clientUI.setContentPane(clientUI.salesManGUI);
				break;
			case COSTREL:
				clientUI.setTitle("CCRM costumer relationships");
				clientUI.setContentPane(clientUI.costumerRelationshipsGUI);
				break;
			default:
				break;
			}
			break;

		case LOGOUT:
			clientUI.setTitle("CCRM login screen");
			clientUI.setContentPane(clientUI.loginGUI);
			break;
		case ERROR:
			infoBox((String) mfs.other, "", JOptionPane.ERROR_MESSAGE);
			break;
		case CONFIRM:
			infoBox((String) mfs.other, "", JOptionPane.INFORMATION_MESSAGE);
			break;
		case GETLIST:
			clientUI.setList((ArrayList) mfs.get(0), (ArrayList) mfs.get(1), (ArrayList) mfs.get(2));
			break;
		case GETCOSTLIST:
			clientUI.setCostList((ArrayList) mfs.get(0), (ProductDetails) mfs.get(1), (String) mfs.get(2));
			break;
		case GETCITYLIST:
			((ClientGUIInterface) clientUI.costumerRelationshipsGUI).updateFields(mfs.get(0));
			break;
		case MARKETINGMGRGUI:
			((ClientGUIInterface) clientUI.marketingManagerGUI).updateFields(mfs.get(0), mfs.get(1), mfs.get(2), mfs.get(3), mfs.get(4), mfs.get(5));
			break;
		case MARKETINGEMPLOYEEGUI:
			((ClientGUIInterface) clientUI.marketingEmployeeGUI).updateFields(mfs.get(0), mfs.get(1), mfs.get(2), mfs.get(3));
			break;
		case SALESMANGUI:
			((ClientGUIInterface) clientUI.salesManGUI).updateFields(mfs.get(0), mfs.get(1));
			break;
		case REPORTOP:
			clientUI.printReportOp((ArrayList<String>) mfs.get(0));
			break;
		case REPORTUSER:
			clientUI.printReportOp((ArrayList<String>) mfs.get(0));
			break;
		case PRODUCTUPDATE:
			clientUI.updateProducts((ListCombo) mfs.get(0));
			break;
		case PRODUCTUPDATEEMP:
			clientUI.updateProductsEmp((ListCombo) mfs.get(0));
			break;
		// case FIELDUPDATE:
		// clientUI.updateFields((ListCombo) mfs.get(0));
		// break;
		// case SLICEUPDATE:
		// clientUI.updateSlices((ListCombo) mfs.get(0));
		// break;
		default:
			break;
		}
	}

	/**
	 * implementation of OCSF method for handling the message from the server
	 */

	public void handleMessageFromServer(Object msg) {
		getMsg((MessageFromServer) msg);
	}

	/**
	 * quit the system while closing the connection
	 */

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * allowing methods to pop up message to the client
	 * 
	 * @param infoMessage
	 *            the message
	 * @param titleBar
	 *            the title to the pop up message
	 * @param type
	 *            the type of the message {error, confirmation}
	 */

	public static void infoBox(String infoMessage, String titleBar, int type) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, type);
	}

	public static void main(String[] args) throws IOException {
		CCRMClientControl clientControl = new CCRMClientControl();
		CCRMClientUI clientui = new CCRMClientUI(clientControl);
		// CCRMClient client = new CCRMClient(clientControl);

		clientControl.setParams(clientui);

		clientui.setVisible(true);
	}
}
