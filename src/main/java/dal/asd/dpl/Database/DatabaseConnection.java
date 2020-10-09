package dal.asd.dpl.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import dal.asd.dpl.util.ConstantsUtil;

public class DatabaseConnection implements IDatabaseConnection {

	Connection connect = null;
	private Properties properties;
	
	/*private static DatabaseConnection databaseConnection = null;

	private DatabaseConnection() {
		super();
	}

	public static DatabaseConnection getSingleInstance() {
		if (null == databaseConnection) {
			databaseConnection = new DatabaseConnection();
		}
		return databaseConnection;
	}*/

	public Connection getConnection() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(ConstantsUtil.PROP_FILE.toString());
		this.properties = new Properties();
		try {
			this.properties.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName(ConstantsUtil.DRIVER_NAME.toString());
			
			String dbURL = properties.getProperty(ConstantsUtil.DB_URL.toString());
			String dbUserName = properties.getProperty(ConstantsUtil.DB_USER_NAME.toString());
			String dbPassword = properties.getProperty(ConstantsUtil.DB_PASSWORD.toString());
			
//			String dbURL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_8_DEVINT_USER";
//			String dbUserName = "CSCI5308_8_DEVINT_USER";
//			String dbPassword = "cWhbaAs94F";
			
			connect = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connect;

	}

	public void disconnect() {
		try {
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
