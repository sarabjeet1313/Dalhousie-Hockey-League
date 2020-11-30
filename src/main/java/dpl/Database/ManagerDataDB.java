package dpl.Database;

import java.sql.ResultSet;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ManagerConstants;

public class ManagerDataDB implements IManagerPersistance {
	InvokeStoredProcedure invoke = null;

	@Override
	public boolean persistManagerInfo(Manager manager, String teamName, String leagueName) {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.PERSIST_MANAGER.getSpString());
			invoke.setParameter(1, manager.getManagerName());
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(ManagerConstants.SUCCESS.toString());
			}
		} catch (Exception e) {
		}
		return isPersisted;
	}
}