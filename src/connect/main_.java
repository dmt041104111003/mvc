package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main_ {
	private static String DB_URL = "jdbc:mysql://localhost:3306/data_user";
	private static String USER_NAME = "root";
	private static String PASSWORD = "";
	public static void main(String[] args) throws SQLException {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		String sql = "select * from user";
		PreparedStatement pr = conn.prepareStatement(sql);
		ResultSet r1 = pr.executeQuery();
		while(r1.next()) {
			System.out.print(r1.getInt("id_user") + "\n");
			System.out.print(r1.getString("name_user") + "\n");
			System.out.print(r1.getInt("score_user") + "\n\n");
		}
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		}
	}
}
