package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.CoachConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;

public class CoachDataDB implements ICoachPersistance {
	InvokeStoredProcedure invoke = null;

	@Override
	public boolean persistCoaches(Coach coach, String teamName, String leagueName) throws SQLException {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.PERSIST_COACH.getSpString());
			invoke.setParameter(1, coach.getCoachName());
			invoke.setParameter(2, coach.getSkating());
			invoke.setParameter(3, coach.getShooting());
			invoke.setParameter(4, coach.getChecking());
			invoke.setParameter(5, coach.getSaving());
			invoke.setParameter(6, teamName);
			invoke.setParameter(7, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(CoachConstants.SUCCESS.toString());
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
		return isPersisted;
	}
}
