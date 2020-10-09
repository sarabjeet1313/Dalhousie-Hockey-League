package dal.asd.dpl.Database;

import java.sql.Connection;

public interface IDatabaseConnection {

	public Connection getConnection();
	public void disconnect();
}
