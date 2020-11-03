package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.Trading.ITradePersistence;

public class TradeDataDB implements ITradePersistence {
	InvokeStoredProcedure invoke = null;

    @Override
    public List<String> getEligibleTeamName(int lossPoint) throws SQLException{
        List<String> eligibleTeamName = new ArrayList<>();
        ResultSet result;
        try {
        	invoke = new InvokeStoredProcedure("spLoadEligibleTeamName(?)");
        	invoke.setParameter(1, lossPoint);
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                eligibleTeamName.add(result.getString("teamName"));
            }
            result.close();
        } catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
        return eligibleTeamName;
    }

    @Override
    public boolean resetTradeLossPoint(String teamName) throws SQLException{
        boolean isPersisted = Boolean.FALSE;
        try {
        	invoke = new InvokeStoredProcedure("spResetTradeLossPoint(?)");
        	invoke.setParameter(1, teamName);
        	invoke.executeQueryWithResults();
            isPersisted = Boolean.TRUE;
        } catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
        return isPersisted;
    }

}
