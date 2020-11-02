package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.ManagerConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.TeamManagement.IManagerPersistance;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class ManagerDataDB implements IManagerPersistance {
    InvokeStoredProcedure invoke = null;
    IUserOutput output = new CmdUserOutput();

    @Override
    public boolean persistManagerInfo(String managerName, String teamName, String leagueName) {
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