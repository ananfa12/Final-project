import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import common.CostumerDetails;
import common.ImageFile;
import common.ListCombo;
import common.MessageFromClient;
import common.MessageFromServer;
import common.ProductDetails;
import common.Screens;
import common.Task;

public class ServerIMP extends AbstractServer {

	/**
	 * the server IP
	 */
	private InetAddress myIP;
	/**
	 * the server user interface - allows using ui methods
	 */
	static ServerUI serverui;

	/**
	 * the directory in which all the images of product are stored
	 */
	private final String imagesDirectory = "ProductsImages";

	/**
	 * a role - code as presented in the database
	 */
	private final int marketingManagerRoleCode = 1;
	private final int marketingEmployeeRoleCode = 2;
	private final int salesmanRoleCode = 3;
	private final int costumerRelRoleCode = 4;

	/**
	 * ServerIMP Constructor
	 */
	public ServerIMP() {
		super();
	}

	/**
	 * taking care of client's request to login to the system
	 * 
	 * @param mfc
	 *            the package of variable the client sent
	 * @param client
	 *            the connection to the specific client
	 */
	private void takeUserLogIn(MessageFromClient mfc, ConnectionToClient client) {
		int userId = getIntFromSelect("SELECT user_id FROM user_details WHERE user_name = '" + (String) mfc.get(0) + "' AND user_pass = '" + (String) mfc.get(1) + "'");
		int userRole = getIntFromSelect("SELECT user_role FROM user_details WHERE user_name = '" + (String) mfc.get(0) + "' AND user_pass = '" + (String) mfc.get(1) + "'");
		int connectedNow = getIntFromSelect("SELECT connected_now FROM user_details WHERE user_name = '" + (String) mfc.get(0) + "' AND user_pass = '" + (String) mfc.get(1) + "'");

		if (userId == 0) {
			sendErrorMessage("The details are incorrect.", client);
		} else if (connectedNow == 1) {
			sendErrorMessage("You are already connected to the system!", client);
		} else {
			MessageFromServer mfs = new MessageFromServer();
			mfs.task = Task.CHNGSCRN;
			mfs.screen = Screens.values()[userRole];
			mfs.value = userRole;
			mfs.other = (String) mfc.get(0);
			updateUserGUI(userId, userRole, client);

			sendMSG(mfs, client);
			insertSQL("UPDATE user_details SET connected_now = 1 WHERE user_id = " + userId);
			writeToConsole("new connection: " + (String) mfc.get(0));
			// sendConfirmMessage("Welcome!", client);
		}
	}

	/**
	 * update each user's GUI according to it's role
	 * 
	 * @param id
	 *            the user's id
	 * @param role
	 *            the user's role
	 * @param client
	 *            the connection the client
	 */

	private void updateUserGUI(int id, int role, ConnectionToClient client) {
		ArrayList<String> cityNames, sliceNames, productNames, areaNames, typeNames, fieldNames, opNames, patternNames;
		ArrayList<Integer> cityIds, sliceIds, productIds, areaIds, typeIds, fieldIds, opIds, patternIds;
		ListCombo city, slice, product, area, pattern, fields, operation;

		switch (role) {
		case costumerRelRoleCode:
			cityNames = getStrListFromSQL("SELECT city_name FROM city_details");
			cityIds = getListFromSQL("SELECT city_id FROM city_details");
			city = new ListCombo(cityNames, cityIds);

			sendLongMsg(Task.GETCITYLIST, client, city);
			break;
		case marketingManagerRoleCode:
			sliceNames = getStrListFromSQL("SELECT slice_name FROM slice_details");
			sliceIds = getListFromSQL("SELECT slice_id FROM slice_details");
			slice = new ListCombo(sliceNames, sliceIds);

			productNames = getStrListFromSQL("SELECT product_name FROM product_details");
			productIds = getListFromSQL("SELECT product_id FROM product_details");
			product = new ListCombo(productNames, productIds);

			areaNames = getStrListFromSQL("SELECT area_name FROM area_details");
			areaIds = getListFromSQL("SELECT area_id FROM area_details");
			area = new ListCombo(areaNames, areaIds);

			// typeNames =
			// getStrListFromSQL("SELECT type_name FROM type_details");
			// typeIds = getListFromSQL("SELECT type_id FROM type_details");
			// typeNames.add(0, " ");
			// typeIds.add(0, 0);

			fieldNames = getStrListFromSQL("SELECT field_name FROM field_details");
			fieldIds = getListFromSQL("SELECT field_id FROM field_details");
			fields = new ListCombo(fieldNames, fieldIds);

			opNames = getStrListFromSQL("SELECT op_name FROM operation_details");
			opIds = getListFromSQL("SELECT op_id FROM operation_details");
			operation = new ListCombo(opNames, opIds);

			patternNames = getStrListFromSQL("SELECT pattern_name FROM pattern_details");
			patternIds = getListFromSQL("SELECT pattern_id FROM pattern_details");
			pattern = new ListCombo(patternNames, patternIds);

			sendLongMsg(Task.MARKETINGMGRGUI, client, slice, product, area, fields, operation, pattern);
			break;
		case marketingEmployeeRoleCode:
			areaNames = getStrListFromSQL("SELECT area_name FROM area_details");
			areaIds = getListFromSQL("SELECT area_id FROM area_details");
			area = new ListCombo(areaNames, areaIds);

			productNames = getStrListFromSQL("SELECT product_name FROM product_details");
			productIds = getListFromSQL("SELECT product_id FROM product_details");
			product = new ListCombo(productNames, productIds);

			sliceNames = getStrListFromSQL("SELECT slice_name FROM slice_details");
			sliceIds = getListFromSQL("SELECT slice_id FROM slice_details");
			slice = new ListCombo(sliceNames, sliceIds);

			fieldNames = getStrListFromSQL("SELECT field_name FROM field_details");
			fieldIds = getListFromSQL("SELECT field_id FROM field_details");
			fields = new ListCombo(fieldNames, fieldIds);

			sendLongMsg(Task.MARKETINGEMPLOYEEGUI, client, area, product, slice, fields);
			break;
		case salesmanRoleCode:
			fieldNames = getStrListFromSQL("SELECT field_name FROM field_details");
			fieldIds = getListFromSQL("SELECT field_id FROM field_details");
			fieldNames.add(0, " ");
			fieldIds.add(0, 0);

			sendLongMsg(Task.SALESMANGUI, client, fieldNames, fieldIds);
			break;
		default:
			break;
		}
	}

	/**
	 * handle client's request to log out off the system
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void logUserOut(MessageFromClient mfc, ConnectionToClient client) {
		insertSQL("UPDATE user_details SET connected_now = 0 WHERE user_name = '" + (String) mfc.get(0) + "'");
		MessageFromServer mfs = new MessageFromServer();
		mfs.task = Task.LOGOUT;
		sendMSG(mfs, client);
		writeToConsole("user " + (String) mfc.get(0) + " disconnected");
	}

	/**
	 * handle add client request adding client to database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addClientToDB(MessageFromClient mfc, ConnectionToClient client) {
		insertSQL("INSERT INTO client_details (client_name, client_family, client_age, client_city_id, client_phone) VALUES ('" + (String) mfc.get(0) + "', '" + (String) mfc.get(1) + "', " + (int) mfc.get(2) + ", " + (int) mfc.get(3) + ", '"
				+ (String) mfc.get(4) + "')");
		sendConfirmMessage("client added", client);
	}

	/**
	 * handle client request to add product to database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addProductToDB(MessageFromClient mfc, ConnectionToClient client) {
		String imageFileName = (String) mfc.get(0);
		ImageFile imf = (ImageFile) mfc.get(2);

		ImageFile.saveImageFile(imf, imagesDirectory, imageFileName);

		if (!isValueInDB("SELECT * FROM product_details WHERE product_name = '" + (String) mfc.get(0) + "'")) {
			insertSQL("INSERT INTO product_details (product_name, product_desc, product_img, product_price) VALUES ('" + (String) mfc.get(0) + "', '" + (String) mfc.get(1) + "', '" + imageFileName + "', " + (int) mfc.get(3) + ")");
			sendConfirmMessage("product added", client);
		} else
			sendErrorMessage("product already in database", client);
	}

	/**
	 * handle client request to add new slice to database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addSliceToDB(MessageFromClient mfc, ConnectionToClient client) {
		if (!isValueInDB("SELECT * FROM slice_details WHERE slice_name = '" + (String) mfc.get(0) + "'")) {
			insertSQL("INSERT INTO slice_details (slice_name, slice_min_age, slice_max_age, slice_area_id) VALUES ('" + (String) mfc.get(0) + "', " + (int) mfc.get(1) + ", " + (int) mfc.get(2) + ", " + (int) mfc.get(3) + ")");
			updateUserGUI(1, marketingManagerRoleCode, client);
			updateUserGUI(1, marketingEmployeeRoleCode, client);
			sendConfirmMessage("new slice created", client);
		} else
			sendErrorMessage("slice with this name is already in database", client);
	}

	/**
	 * handle client's request to add pattern to database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addPatternToDB(MessageFromClient mfc, ConnectionToClient client) {
		println((String) mfc.get(0));
		int productId = getIntFromSelect("SELECT product_id FROM product_details WHERE product_name = '" + (String) mfc.get(0) + "'");
		int sliceId = getIntFromSelect("SELECT slice_id FROM slice_details WHERE slice_name = '" + (String) mfc.get(1) + "'");
		if (isValueInDB("SELECT * FROM pattern_details WHERE pattern_name = '" + (String) mfc.get(3) + "'"))
			sendErrorMessage("a pattern with this name already exists", client);
		else {
			insertSQL("INSERT INTO pattern_details (pattern_product_id, pattern_slice_id, pattern_message, pattern_name) VALUES (" + productId + ", " + sliceId + ", '" + (String) mfc.get(2) + "', '" + (String) mfc.get(3) + "')");
			updateUserGUI(1, marketingManagerRoleCode, client);
			updateUserGUI(1, marketingEmployeeRoleCode, client);
			sendConfirmMessage("New marketing pattern created", client);
		}
	}

	/**
	 * handle client's request to add new field to database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addFieldToDB(MessageFromClient mfc, ConnectionToClient client) {
		if (!isValueInDB("SELECT * FROM field_details WHERE field_name = '" + (String) mfc.get(0) + "'")) {
			insertSQL("INSERT INTO field_details (field_name) VALUES ('" + (String) mfc.get(0) + "')");
			updateUserGUI(1, marketingManagerRoleCode, client);
			updateUserGUI(1, marketingEmployeeRoleCode, client);
			// makeFieldUpdate(mfc, client);
			sendConfirmMessage("new field of products created", client);
		} else
			sendErrorMessage("a field with this name already exists", client);
	}

	/**
	 * handle client's request to create new operation also spread the client
	 * among all the sales man equally
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addOpToDB(MessageFromClient mfc, ConnectionToClient client) {
		if (isValueInDB("SELECT * FROM operation_details WHERE op_name = '" + (String) mfc.get(0) + "'"))
			sendErrorMessage("Operation with that name already exists, please choose another one", client);
		else {
			
			int patternId = getIntFromSelect("SELECT pattern_id FROM pattern_details WHERE pattern_name = '" + (String) mfc.get(1) + "'");
			println((String) mfc.get(1));
			insertSQL("INSERT INTO operation_details (op_name, op_pattern_id) VALUES ('" + (String) mfc.get(0) + "', " + patternId + ")");

			// distribution of operation client to sales men

			int sliceId = getIntFromSelect("SELECT pattern_slice_id FROM pattern_details WHERE pattern_id = " + patternId);

			int minAge = getIntFromSelect("SELECT slice_min_age FROM slice_details WHERE slice_id = " + sliceId);
			int maxAge = getIntFromSelect("SELECT slice_max_age FROM slice_details WHERE slice_id = " + sliceId);
			int areaId = getIntFromSelect("SELECT slice_area_id FROM slice_details WHERE slice_id = " + sliceId);

			// exporting needed field permission from product
			int productId = getIntFromSelect("SELECT pattern_product_id FROM pattern_details WHERE pattern_id = " + patternId);

			ArrayList<Integer> productFieldList = getListFromSQL("SELECT field_id FROM product_in_field WHERE product_id = " + productId);
			// filtering age-wise
			ArrayList<Integer> clientListAged = getListFromSQL("SELECT client_id FROM client_details WHERE client_age > " + minAge + " AND client_age < " + maxAge);
			ArrayList<Integer> clientListFinal = new ArrayList<Integer>();

			for (int i = 0; i < clientListAged.size(); i++) {
				int clientId = clientListAged.get(i);
				int city = getIntFromSelect("SELECT client_city_id FROM client_details WHERE client_id = " + clientId);
				int area = getIntFromSelect("SELECT area_id FROM city_in_area WHERE city_id = " + city);

				if (areaId == area) { // filtering area-wise
					for (int j = 0; j < productFieldList.size(); j++) {

						// filtering permission-wise
						if (isValueInDB("SELECT * FROM permission_user_field WHERE client_id = " + clientId + " AND field_id = " + productFieldList.get(j))) {

							// filtering date-wise
							Timestamp ts = getTimestampSQL("SELECT last_approach FROM client_details WHERE client_id = " + clientId);
							DateTime dtNow = new DateTime(new Timestamp(System.currentTimeMillis()));
							DateTime dtClient = new DateTime(ts);

							if (DateTime.diffMoreThanXDays(dtNow, dtClient, 7))
								clientListFinal.add(clientId);
							break;
						}

					}
				}
			}

			// making list of sales men id's
			ArrayList<Integer> salesManList = new ArrayList<Integer>();
			salesManList = getListFromSQL("SELECT user_id FROM user_details WHERE user_role = " + salesmanRoleCode);

			int salesManCount = salesManList.size();
			int clientListCount = clientListFinal.size();

			// get the newly created operation
			int newOpId = getIntFromSelect("SELECT op_id FROM operation_details WHERE op_name = '" + (String) mfc.get(0) + "'");
			// assigning each sales man a new list to the new operation
			for (int i = 0; i < salesManCount; i++)
				insertSQL("INSERT INTO list_details (operation_id, salesman_id) VALUES (" + newOpId + "," + salesManList.get(i) + ")");

			// get id of all relevant lists (that were just created
			ArrayList<Integer> listOfLists = new ArrayList<Integer>();
			listOfLists = getListFromSQL("SELECT list_id FROM list_details WHERE operation_id = " + newOpId);

			// giving each sales man an equal amount of clients
			int i = 0, j = 0;
			while (i < clientListCount && j < salesManCount) {
				insertSQL("INSERT INTO list_to_client (list_id, client_id) VALUES (" + listOfLists.get(j) + "," + clientListFinal.get(i++) + ")");
				if (++j == salesManCount)
					j = 0;
			}

			sendConfirmMessage("operation was initiated", client);
		}
	}

	/**
	 * handle client's request to define a field to an existing product in the
	 * database
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void defTypeAndField(MessageFromClient mfc, ConnectionToClient client) {
		int productId = (int) mfc.get(0);
		int fieldId = (int) mfc.get(1);

		// if (typeId != 0)
		// insertSQL("INSERT INTO product_in_type VALUES (" + productId + "," +
		// typeId + ")");

		if (fieldId != 0)
			insertSQL("INSERT INTO product_in_field VALUES (" + productId + "," + fieldId + ")");

		sendConfirmMessage("product was assigned to the given type and field", client);
	}

	/**
	 * sending list of all the user's lists of client each list related to one
	 * operation
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void sendListToSalesman(MessageFromClient mfc, ConnectionToClient client) {
		// clear any list with no costumers
		ArrayList<Integer> listList = getListFromSQL("SELECT list_id FROM list_details");
		for (int i = 0; i < listList.size(); i++) {
			if (!isValueInDB("SELECT * FROM list_to_client WHERE list_id = " + listList.get(i)))
				insertSQL("DELETE FROM list_details WHERE list_id = " + listList.get(i));
		}

		int salesmanId = getIntFromSelect("SELECT user_id FROM user_details WHERE user_name = '" + (String) mfc.get(0) + "'");
		ArrayList<Integer> salesmanLists = getListFromSQL("SELECT list_id FROM list_details WHERE salesman_id = " + salesmanId);
		ArrayList<Integer> salesmanOperationsId = getListFromSQL("SELECT operation_id FROM list_details WHERE salesman_id = " + salesmanId);
		ArrayList<String> salesmanOperationsName = new ArrayList<String>();
		for (int i = 0; i < salesmanOperationsId.size(); i++) {
			String name = getStringFromSelect("SELECT op_name FROM operation_details WHERE op_id = " + salesmanOperationsId.get(i));
			salesmanOperationsName.add(name);
		}
		sendLongMsg(Task.GETLIST, client, salesmanOperationsName, salesmanLists, salesmanOperationsId);
	}

	/**
	 * sending a sales man a list of clients which are listed in a list the user
	 * chose and sent
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void sendListOfCostumer(MessageFromClient mfc, ConnectionToClient client) {
		ArrayList<Integer> list = getListFromSQL("SELECT client_id FROM list_to_client WHERE list_id = " + (int) mfc.get(0));
		ArrayList<CostumerDetails> costList = new ArrayList<CostumerDetails>();

		for (int i = 0; i < list.size(); i++) {

			// testing if client was recently called
			Timestamp ts = getTimestampSQL("SELECT last_approach FROM client_details WHERE client_id = " + list.get(i));
			DateTime dtNow = new DateTime(new Timestamp(System.currentTimeMillis()));
			DateTime dtClient = new DateTime(ts);

			if (DateTime.diffMoreThanXDays(dtNow, dtClient, 7)) {
				CostumerDetails temp = new CostumerDetails();
				temp.id = getIntFromSelect("SELECT client_id FROM client_details WHERE client_id = " + list.get(i));
				temp.name = getStringFromSelect("SELECT client_name FROM client_details WHERE client_id = " + list.get(i));
				temp.lastname = getStringFromSelect("SELECT client_family FROM client_details WHERE client_id = " + list.get(i));
				temp.age = getIntFromSelect("SELECT client_age FROM client_details WHERE client_id = " + list.get(i));
				temp.phone = getStringFromSelect("SELECT client_phone FROM client_details WHERE client_id = " + list.get(i));

				costList.add(temp);
			} else {
				insertSQL("DELETE FROM list_to_client WHERE client_id = " + list.get(i));
			}
		}

		// get product details
		int opid = getIntFromSelect("SELECT operation_id FROM list_details WHERE list_id = " + (int) mfc.get(0));
		int patid = getIntFromSelect("SELECT op_pattern_id FROM operation_details WHERE op_id = " + opid);
		int prodid = getIntFromSelect("SELECT pattern_product_id FROM pattern_details WHERE pattern_id = " + patid);

		String message = getStringFromSelect("SELECT pattern_message FROM pattern_details WHERE pattern_id = " + patid);

		ProductDetails pd = new ProductDetails();

		pd.id = prodid;
		pd.name = getStringFromSelect("SELECT product_name FROM product_details WHERE product_id = " + prodid);
		pd.desc = getStringFromSelect("SELECT product_desc FROM product_details WHERE product_id = " + prodid);
		pd.price = getStringFromSelect("SELECT product_price FROM product_details WHERE product_id = " + prodid);
		String imageName = getStringFromSelect("SELECT product_img FROM product_details WHERE product_id = " + prodid);

		println(pd.id, pd.name, imageName);
		
		File file = new File(imagesDirectory + "/" + imageName + ".jpg");
		pd.image = ImageFile.makeImageFile(file);

		sendLongMsg(Task.GETCOSTLIST, client, costList, pd, message);
	}

	/**
	 * add a specific field permission to a chosen client
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void addPermissionToCostumer(MessageFromClient mfc, ConnectionToClient client) {
		int userid = getIntFromSelect("SELECT client_id FROM client_details WHERE client_name = '" + mfc.get(1) + "'");
		int fieldid = getIntFromSelect("SELECT field_id FROM field_details WHERE field_name = '" + mfc.get(0) + "'");

		insertSQL("INSERT INTO permission_user_field VALUES (" + userid + ", " + fieldid + ")");
		sendConfirmMessage("User was given the new permission", client);
	}

	/**
	 * handling client's request to add a comment to to a successful or
	 * unsuccessful sale
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void sendComm(MessageFromClient mfc, ConnectionToClient client) {
		CostumerDetails costumer = (CostumerDetails) mfc.get(0);
		ProductDetails product = (ProductDetails) mfc.get(1);
		String comment = (String) mfc.get(2);
		int callLength = (int) mfc.get(3);
		int opId = (int) mfc.get(4);
		int price = (int) mfc.get(5);

		// retrieve current time and date
		Timestamp sqlDate = new Timestamp(System.currentTimeMillis());
		insertSQL("UPDATE client_details SET last_approach = '" + sqlDate + "' WHERE client_id = " + costumer.id);

		// delete costumer from any list - since he was just approached
		insertSQL("DELETE FROM list_to_client WHERE client_id = " + costumer.id);

		// clear any list with no costumers
		ArrayList<Integer> listList = getListFromSQL("SELECT list_id FROM list_details");
		for (int i = 0; i < listList.size(); i++) {
			if (!isValueInDB("SELECT * FROM list_to_client WHERE list_id = " + listList.get(i)))
				insertSQL("DELETE FROM list_details WHERE list_id = " + listList.get(i));
		}

		int type = 0;
		if (comment.equals("Purchased"))
			type = 1;

		insertSQL("INSERT INTO client_comment (comm_type, product_id, client_id, comm_text, call_len, op_id, buy_price) VALUES (" + type + ", " + product.id + ", " + costumer.id + ", '" + comment + "', " + callLength + ", " + opId + ", " + price
				+ ")");
		sendConfirmMessage("Sale details submitted", client);
	}

	/**
	 * update the client's list of product according to a chosen field
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void makeProductUpdate(MessageFromClient mfc, ConnectionToClient client) {
		int fieldId = (int) mfc.get(0);

		ArrayList<Integer> ids = getListFromSQL("SELECT product_id FROM product_in_field WHERE field_id = " + fieldId);
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < ids.size(); i++) {
			String name = getStringFromSelect("SELECT product_name FROM product_details WHERE product_id = " + ids.get(i));
			names.add(name);
		}

		ListCombo list = new ListCombo(names, ids);

		sendLongMsg(Task.PRODUCTUPDATE, client, list);
	}

	private void makeProductUpdateEmp(MessageFromClient mfc, ConnectionToClient client) {

		int fieldId = (int) mfc.get(0);

		ArrayList<Integer> ids = getListFromSQL("SELECT product_id FROM product_in_field WHERE field_id = " + fieldId);
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < ids.size(); i++) {
			String name = getStringFromSelect("SELECT product_name FROM product_details WHERE product_id = " + ids.get(i));
			names.add(name);
		}

		ListCombo list = new ListCombo(names, ids);
		sendLongMsg(Task.PRODUCTUPDATEEMP, client, list);
	}

	/**
	 * update the fields list on the client's gui called when there was an
	 * update
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void makeFieldUpdate(MessageFromClient mfc, ConnectionToClient client) {
		ArrayList<Integer> ids = getListFromSQL("SELECT field_id FROM field_details");
		ArrayList<String> names = getStrListFromSQL("SELECT field_name FROM field_details");

		ListCombo list = new ListCombo(names, ids);

		sendLongMsg(Task.FIELDUPDATE, client, list);
	}

	/**
	 * update the slice list on the client's gui called where the was an update
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void makeSliceUpdate(MessageFromClient mfc, ConnectionToClient client) {
		ArrayList<Integer> ids = getListFromSQL("SELECT slice_id FROM slice_details");
		ArrayList<String> names = getStrListFromSQL("SELECT slice_name FROM slice_details");

		ListCombo list = new ListCombo(names, ids);

		sendLongMsg(Task.SLICEUPDATE, client, list);
	}

	/**
	 * produce an operation report called when a marketing manager send an
	 * operation id
	 * 
	 * @param mfc
	 *            package of the variables client sent
	 * @param client
	 *            connection to the client
	 */

	private void makeOperationReport(MessageFromClient mfc, ConnectionToClient client) {
		int opId = (Integer) mfc.get(0);
		int clientsCount = getSumOfRowsSQL("SELECT * FROM client_comment WHERE op_id = " + opId);
		String opName = getStringFromSelect("SELECT op_name FROM operation_details WHERE op_id = " + opId);
		int totalTime = getSumOfRowsSQL("SELECT call_len FROM client_comment WHERE op_id = " + opId);
		int totalSales = getRowCountSQL("SELECT * FROM client_comment WHERE op_id = " + opId + " AND comm_text = 'Purchased'");
		ArrayList<String> commentsTotal = getStrListFromSQL("SELECT comm_text FROM client_comment WHERE op_id = " + opId + " AND comm_text != 'Purchased'");
		ArrayList<String> textAll = new ArrayList<String>();

		textAll.add("Operation Name: " + opName + " ID: " + opId);
		DateTime dt = new DateTime(new Timestamp(System.currentTimeMillis()));
		textAll.add("Current Date: " + dt.toString());
		textAll.add("Total time operation took: " + totalTime);
		textAll.add("Total successful sales: " + totalSales);
		textAll.add("Out of: " + clientsCount);
		textAll.add("Comments on operation:");

		for (int i = 0; i < commentsTotal.size(); i++)
			textAll.add(commentsTotal.get(i));

		sendLongMsg(Task.REPORTOP, client, textAll);
	}

	/**
	 * make a users - report for marketing manager
	 * 
	 * @param client
	 *            connection to the client
	 */
	@SuppressWarnings("unchecked")
	private void makeUsersReport(ConnectionToClient client) {
		ArrayList<Integer> usersList = getListFromSQL("SELECT client_id FROM client_details");
		ArrayList<Integer> rankList = getListFromSQL("SELECT client_rank FROM client_details");
		ArrayList<Integer> interestList = getListFromSQL("SELECT client_interest FROM client_details");

		ArrayList<Integer> rankOrginized;
		ArrayList<Integer> interesetOrginized;

		for (int i = 0; i < rankList.size(); i++) {
			for (int j = i + 1; j < rankList.size(); j++) {
				if (rankList.get(i) < rankList.get(j)) {
					arrayListSwap(rankList, i, j);
					arrayListSwap(usersList, i, j);
				}
			}
		}

		rankOrginized = (ArrayList<Integer>) usersList.clone();

		for (int i = 0; i < interestList.size(); i++) {
			for (int j = i + 1; j < interestList.size(); j++) {
				if (rankList.get(i) < rankList.get(j)) {
					arrayListSwap(interestList, i, j);
					arrayListSwap(usersList, i, j);
				}
			}
		}

		interesetOrginized = (ArrayList<Integer>) usersList.clone();

		// making the report
		ArrayList<String> textAll = new ArrayList<String>();

		textAll.add("User's Activity Report");

		DateTime dt = new DateTime(new Timestamp(System.currentTimeMillis()));
		textAll.add("Current Date: " + dt.toString());
		textAll.add("Total user count: " + usersList.size());

		textAll.add("Costumers Ranks: " + usersList.size());
		for (int i = 0; i < rankOrginized.size(); i++)
			textAll.add("User ID: " + rankOrginized.get(i));

		textAll.add("Costumer Interest: " + usersList.size());
		for (int i = 0; i < interesetOrginized.size(); i++)
			textAll.add("User ID: " + interesetOrginized.get(i));

		sendLongMsg(Task.REPORTUSER, client, textAll);
	}

	/**
	 * swapping between two positions in an arraylist
	 * 
	 * @param list
	 *            list to be changed
	 * @param i
	 *            first position
	 * @param j
	 *            second position
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void arrayListSwap(ArrayList list, int i, int j) {
		Object temp = list.get(i);
		list.add(i, list.get(j));
		list.add(j, temp);
	}

	/**
	 * allowing marketing employee to start system analyse
	 * 
	 * @param client
	 *            connection to the client
	 */

	private void startAnalyseSystem(ConnectionToClient client) {
		ArrayList<Integer> clientIds = getListFromSQL("SELECT client_id FROM client_details");
		int offers = 0, sales = 0, money = 0;

		for (int i = 0; i < clientIds.size(); i++) {
			offers = getRowCountSQL("SELECT * FROM client_comment WHERE client_id = " + clientIds.get(i));
			sales = getRowCountSQL("SELECT * FROM client_comment WHERE client_id = " + clientIds.get(i) + " AND comment_text = 'Purchased'");
			money = getSumOfRowsSQL("SELECT buy_price FROM client_comment WHERE client_id = " + clientIds.get(i));

			int newRank = makeRankToClient(sales, money);
			int newInterest = makeIntrestToClientField(offers, sales);

			insertSQL("UPDATE client_details SET client_rank = " + newRank + " WHERE client_id = " + clientIds.get(i));
			insertSQL("UPDATE client_details SET client_interest = " + newInterest + " WHERE client_id = " + clientIds.get(i));
		}
		sendConfirmMessage("System was analaysed, thank you", client);
	}

	/**
	 * generate rank for each costumer
	 * 
	 * @param purchaseCount
	 *            how many purchases the costumer made so far
	 * @param totalMoney
	 *            how much money did the costumer spent so far
	 * @return rank
	 */

	private int makeRankToClient(int purchaseCount, int totalMoney) {
		if (purchaseCount == 0)
			return 1;

		int avgSpend = totalMoney / purchaseCount;

		for (int i = 1; i <= 10; i++) {
			int n = 10 * i;
			if (avgSpend <= n)
				return i;
		}

		return 1;
	}

	/**
	 * generates interest rank for each costumer
	 * 
	 * @param offers
	 *            how many offers were made to the costumer
	 * @param purchases
	 *            how many times the costumer purchased the product
	 * @return interest
	 */

	private int makeIntrestToClientField(int offers, int purchases) {
		if (purchases == 0)
			return 1;

		int avgSpend = offers / purchases;

		for (int i = 1; i <= 10; i++) {
			int n = offers / i / 10;
			if (avgSpend < n)
				return 11 - i;
		}

		return 1;
	}

	/**
	 * handling the message received from client Analyzing the message to know
	 * exactly which method will handle the task
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		MessageFromClient mfc = (MessageFromClient) msg;
		switch (mfc.type) {
		case LOGIN:
			takeUserLogIn(mfc, client);
			break;
		case LOGOUT:
			logUserOut(mfc, client);
			break;
		case ADDUSER:
			addClientToDB(mfc, client);
			break;
		case ADDPROD:
			addProductToDB(mfc, client);
			break;
		case ADDSLICE:
			addSliceToDB(mfc, client);
			break;
		case ADDPATTERN:
			addPatternToDB(mfc, client);
			break;
		case ADDOP:
			addOpToDB(mfc, client);
			break;
		// case ADDTYPE:
		// addTypeToDB(mfc, client);
		// break;
		case ADDFIELD:
			addFieldToDB(mfc, client);
			break;
		case DEFTYPE:
			defTypeAndField(mfc, client);
			break;
		case GETLISTS:
			sendListToSalesman(mfc, client);
			break;
		case GETCOSTLIST:
			sendListOfCostumer(mfc, client);
			break;
		case ADDPERMISSON:
			addPermissionToCostumer(mfc, client);
			break;
		case SENDCOMM:
			sendComm(mfc, client);
			break;
		case ANALYSESYSTEM:
			startAnalyseSystem(client);
			break;
		case ASKREPORTOP:
			makeOperationReport(mfc, client);
			break;
		case ASKREPORTUSER:
			makeUsersReport(client);
			break;
		case ASKFORPRODUCTS:
			makeProductUpdate(mfc, client);
			break;
		case ASKFORPRODUCTSEMP:
			makeProductUpdateEmp(mfc, client);
			break;
		default:
			break;
		}
	}

	/**
	 * overriding serverStarted from OCSF framework
	 */
	protected void serverStarted() {
		try {
			myIP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		writeToConsole("Server is running. ip: " + myIP + " port: " + getPort());
	}

	/**
	 * getting Timestamp from database
	 * 
	 * @param s
	 *            the sql query
	 * @return Timstamp ts
	 */
	private Timestamp getTimestampSQL(String s) {
		Timestamp ts = null;
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			while (rs.next())
				ts = rs.getTimestamp(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * sending confirmation message to client
	 * 
	 * @param s
	 *            the text to be sent
	 * @param client
	 *            connection to the client
	 */

	private void sendConfirmMessage(String s, ConnectionToClient client) {
		MessageFromServer mfs = new MessageFromServer();
		mfs.task = Task.CONFIRM;
		mfs.other = s;
		sendMSG(mfs, client);
	}

	/**
	 * sending error message to client
	 * 
	 * @param s
	 *            the text to be sent
	 * @param client
	 *            connection to the client
	 */

	private void sendErrorMessage(String s, ConnectionToClient client) {
		MessageFromServer mfs = new MessageFromServer();
		mfs.task = Task.ERROR;
		mfs.value = MessageFromServer.MSG;
		mfs.other = s;
		sendMSG(mfs, client);
	}

	/**
	 * sums up all the values in the result of an SQL query
	 * 
	 * @param s
	 *            the SQL query
	 * @return sum
	 */

	private int getSumOfRowsSQL(String s) {
		int sum = 0;
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			while (rs.next())
				sum += rs.getInt(1);
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return sum;
	}

	/**
	 * sums up how many rows are in the result set
	 * 
	 * @param s
	 *            the SQL query
	 * @return counter
	 */

	private int getRowCountSQL(String s) {
		int counter = 0;
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			while (rs.next())
				counter++;
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return counter;
	}

	/**
	 * convert a result set from database to an ArrayList
	 * 
	 * @param s
	 *            the SQL query
	 * @return list
	 */

	private ArrayList<Integer> getListFromSQL(String s) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			while (rs.next())
				list.add(rs.getInt(1));
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return list;
	}

	/**
	 * convert a result set from database to an ArrayList
	 * 
	 * @param s
	 *            the SQL query
	 * @return list
	 */

	private ArrayList<String> getStrListFromSQL(String s) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			while (rs.next())
				list.add(rs.getString(1));
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		return list;
	}

	/**
	 * applying insert or update SQL query on database
	 * 
	 * @param s
	 *            the SQL query
	 */

	private void insertSQL(String s) {
		try {
			// println(s); // used for debugging SQL error
			ConnectDB.updateStat(s);
		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	/**
	 * returns integer value from database
	 * 
	 * @param s
	 *            the SQL query
	 * @return rs.getInt(1)
	 */

	private int getIntFromSelect(String s) {
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// e.printStackTrace();
			return 0;
		}
	}

	/**
	 * return String from database
	 * @param s the SQL query
	 * @return rs.getString(1)
	 */

	private String getStringFromSelect(String s) {
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			// e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * testing if a given SQL query has any results
	 * @param s the SQL query
	 * @return true or false
	 */

	private boolean isValueInDB(String s) {
		try {
			// println(s); // used for debugging SQL error
			ResultSet rs = ConnectDB.getSelectQuery(s);
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			// e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * sending message from the server to the client
	 * @param mfs MesssageFromServer message
	 * @param client connect to client
	 */

	private void sendMSG(MessageFromServer mfs, ConnectionToClient client) {
		try {
			client.sendToClient(mfs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sending message to the client
	 * while packaging variable
	 * @param type type of the message
	 * @param client connection to client
	 * @param val extra values
	 */

	private void sendLongMsg(Task type, ConnectionToClient client, Object... val) {
		MessageFromServer mfs = new MessageFromServer();
		mfs.addLongData(val);
		mfs.task = type;
		sendMSG(mfs, client);
	}

	/**
	 * implementation of OCSF framework method
	 */
	
	protected void serverStopped() {
		writeToConsole("Server has shut down");
	}

	/**
	 * implementation of OCSF framework method
	 */
	
	public void startServer(int port, String user, String pass) {
		setPort(port);
		ConnectDB.setUserPass(user, pass);
		ConnectDB.getInstance();
		insertSQL("UPDATE user_details SET connected_now = 0");
		try {
			listen();
		} catch (Exception ex) {
			writeToConsole("error - a server is already running on port " + getPort());
		}
	}
	
	/**
	 * allows ordering the server to stop
	 */

	public void stopServer() {
		stopListening();
	}

	/**
	 * allows printing message to the server's graphical console
	 * @param s the message
	 */
	
	public void writeToConsole(String s) {
		serverui.writeToConsole(s);
	}
	
	/**
	 * allows printing many values without the need to 
	 * use System.out.println()
	 * for each
	 * @param o the values
	 */

	@SuppressWarnings("unused")
	private static void println(Object... o) {
		System.out.println();
		for (int i = 0; i < o.length; i++)
			System.out.print(o[i] + ", ");
	}

	public static void main(String[] args) {

		ServerIMP sp = new ServerIMP();
		serverui = new ServerUI(sp);

		serverui.setVisible(true);
	}

}