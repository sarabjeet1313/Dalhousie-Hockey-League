package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.Trading.ITradePersistence;

public class TradeDataDB implements ITradePersistence {
    InvokeStoredProcedure isp = null;

    @Override
    public List<String> getEligibleTeamName(int lossPoint) {
        List<String> eligibleTeamName = new ArrayList<>();
        ResultSet result;
        try {
            isp = new InvokeStoredProcedure("spLoadEligibleTeamName(?)");
            isp.setParameter(1, lossPoint);
            result = isp.executeQueryWithResults();
            while (result.next()) {
                eligibleTeamName.add(result.getString("teamName"));
            }
            result.close();
        } catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return eligibleTeamName;
    }

    @Override
    public boolean resetTradeLossPoint(String teamName) {
        boolean isPersisted = Boolean.FALSE;
        try {
            isp = new InvokeStoredProcedure("spResetTradeLossPoint(?)");
            isp.setParameter(1, teamName);
            isp.executeQueryWithResults();
            isPersisted = Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return isPersisted;
    }

}
