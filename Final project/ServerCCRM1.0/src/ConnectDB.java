import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

	private static Connection conn = null;
	private static ConnectDB instance = null;
	private String db_name = "ccrm";
	private static String db_user;
	private static String db_pass;

	private ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
		}

		
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name + "?zeroDateTimeBehavior=convertToNull", db_user, db_pass);

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static ConnectDB getInstance() {
		if (instance == null) {
			instance = new ConnectDB();
		}
		return instance;
	}

	public static void setUserPass(String u, String p) {
		db_user = u;
		db_pass = p;
	}

	public static void updateStat(String s) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(s);
	}

	public static ResultSet getSelectQuery(String q) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(q);
		return rs;
	}

	public static PreparedStatement updateQuery(String s) {
		try {
			return conn.prepareStatement(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
