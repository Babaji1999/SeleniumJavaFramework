package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {
	
	public static void main(String[] args) throws SQLException {
		String host = "localhost";
		String port = "3306";
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/QADBT", "root", "Aayush@123");
		
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("select * from LoginInfo LIMIT 1");
		while(rs.next()) {
			System.out.println(rs.getString("email"));
			System.out.println(rs.getString("password"));	
		}
		

	}

}
