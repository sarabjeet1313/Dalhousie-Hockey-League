package dal.asd.dpl.database;

import java.sql.*;

public class DatabaseConnection {

	Connection connect= null;
	Statement statement=null;
	ResultSet resultSet=null;
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect=DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC", "praneethn", "B00832226");
//			statement = connect.createStatement();
//			statement.execute("use csci3901;");	
			return connect;
		} catch (Exception e) {
			System.out.println("Failed to establish connection");
			return connect;
		}
	}
	
	public void disconnect() {
		try {
			resultSet.close();
			statement.close();
			connect.close();
		} catch (Exception e) {
			System.out.println("Failed to disconnect connection");
		}	
	}
}
