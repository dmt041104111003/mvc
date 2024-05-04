package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMySQL {
	private static String DB_URL = "jdbc:mysql://localhost:3306/data_user";
	private static String USER_NAME = "root";
	private static String PASSWORD = "";
	
	public static Connection getConnection() throws SQLException {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
