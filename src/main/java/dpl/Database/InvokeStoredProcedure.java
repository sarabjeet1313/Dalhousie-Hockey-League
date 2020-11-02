package dpl.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class InvokeStoredProcedure {
    private Connection connection;
    private DatabaseConnection db = DatabaseConnection.getSingleInstance();
    private String procedureName;
    private CallableStatement statement;
    IUserOutput output = new CmdUserOutput();

    public InvokeStoredProcedure(String procedureName) throws SQLException {
        this.procedureName = procedureName;
        createConnection();
        createStatement();
    }

    public void createConnection() throws SQLException {
        connection = db.getConnection();
    }

    public void createStatement() throws SQLException {
        statement = connection.prepareCall("{call " + procedureName + " }");
    }

    public void setParameter(int paramIndex, String value) throws SQLException {
        statement.setString(paramIndex, value);
    }

    public void setParameter(int paramIndex, boolean value) throws SQLException {
        statement.setBoolean(paramIndex, value);
    }

    public void setParameter(int paramIndex, int value) throws SQLException {
        statement.setInt(paramIndex, value);
    }

    public void setParameter(int paramIndex, double value) throws SQLException {
        statement.setDouble(paramIndex, value);
    }

    public ResultSet executeQueryWithResults() throws SQLException {
        ResultSet result = null;
        result = statement.executeQuery();
        if (result != null) {
            return result;
        }
        return result;
    }

    public void closeConnection() throws SQLException {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
                db.disconnect();
            }

        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
            throw e;
        }
    }
}