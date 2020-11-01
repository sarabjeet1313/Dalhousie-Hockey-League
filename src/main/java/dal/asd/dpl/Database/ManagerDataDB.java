package dal.asd.dpl.Database;

import java.awt.image.DataBufferUShort;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.Util.DatabaseUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;

public class ManagerDataDB implements IManagerPersistance{
	InvokeStoredProcedure invoke = null;
	
	@Override
	public boolean persisitManagerInfo(String managerName, String teamName, String leagueName) {
		boolean isPersisted = false;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.PERSIST_MANAGER.getSpString());
			invoke.setParameter(1, managerName);
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean("success");
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return isPersisted;
	}
}
