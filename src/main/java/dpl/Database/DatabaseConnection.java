package dpl.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import dpl.DplConstants.GeneralConstants;

public class DatabaseConnection implements IDatabaseConnection {

    Connection connect = null;
    private Properties properties;

    private static DatabaseConnection databaseConnection = null;

    private DatabaseConnection() {
        super();
    }

    public static DatabaseConnection getSingleInstance() {
        if (null == databaseConnection) {
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }

    public Connection getConnection() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(GeneralConstants.PROP_FILE.toString());
        this.properties = new Properties();
        try {
            this.properties.load(is);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Class.forName(GeneralConstants.DRIVER_NAME.toString());
            String dbURL = properties.getProperty(GeneralConstants.DB_URL.toString());
            String dbUserName = properties.getProperty(GeneralConstants.DB_USER_NAME.toString());
            String dbPassword = properties.getProperty(GeneralConstants.DB_PASSWORD.toString());
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
