package dal.asd.dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.ManagerUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;

public class ManagerDataDB implements IManagerPersistance {
	
	InvokeStoredProcedure invoke = null;
	IUserOutput output = new CmdUserOutput();

	@Override
	public boolean persisitManagerInfo(String managerName, String teamName, String leagueName) {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.PERSIST_MANAGER.getSpString());
			invoke.setParameter(1, managerName);
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(ManagerUtil.SUCCESS.toString());
			}
		} catch (Exception e) {
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
