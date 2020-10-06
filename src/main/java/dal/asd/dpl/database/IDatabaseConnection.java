package dal.asd.dpl.database;

import java.sql.Connection;

public interface IDatabaseConnection {

	public Connection getConnection();
	public void disconnect();
}
