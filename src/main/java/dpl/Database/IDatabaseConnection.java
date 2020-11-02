package dpl.Database;

import java.sql.Connection;

public interface IDatabaseConnection {
    Connection getConnection();

    void disconnect();
}