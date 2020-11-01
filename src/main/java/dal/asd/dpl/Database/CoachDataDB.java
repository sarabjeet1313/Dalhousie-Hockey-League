package dal.asd.dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.CoachUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;

public class CoachDataDB implements ICoachPersistance {
	
	InvokeStoredProcedure invoke = null;
	IUserOutput output = new CmdUserOutput();
	
	@Override
	public boolean persisitCoaches(Coach coach, String teamName, String leagueName) {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.PERSIST_COACH.getSpString());
			invoke.setParameter(1, coach.getCoachName());
			invoke.setParameter(2, coach.getSkating());
			invoke.setParameter(3, coach.getShooting());
			invoke.setParameter(4, coach.getChecking());
			invoke.setParameter(5, coach.getSaving());
			invoke.setParameter(6, teamName);
			invoke.setParameter(7, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(CoachUtil.SUCCESS.toString());
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		return isPersisted;
	}
}
