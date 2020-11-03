package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.ManagerConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IManagerPersistance;

public class ManagerDataDB implements IManagerPersistance {
    InvokeStoredProcedure invoke = null;

    @Override
    public boolean persistManagerInfo(String managerName, String teamName, String leagueName) throws SQLException {
        boolean isPersisted = Boolean.FALSE;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.PERSIST_MANAGER.getSpString());
            invoke.setParameter(1, managerName);
            invoke.setParameter(2, teamName);
            invoke.setParameter(3, leagueName);
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                isPersisted = result.getBoolean(ManagerConstants.SUCCESS.toString());
            }
        } catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
        return isPersisted;
    }
}