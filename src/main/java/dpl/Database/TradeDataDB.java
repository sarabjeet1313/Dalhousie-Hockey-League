package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.DplConstants.StoredProcedureConstants;
import dpl.Trading.ITradePersistence;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class TradeDataDB implements ITradePersistence {

    InvokeStoredProcedure invoke = null;
    IUserOutput output = new CmdUserOutput();

    @Override
    public List<String> getEligibleTeamName(int lossPoint) {
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
        return eligibleTeamName;
    }

    @Override
    public int getLossPoint() {
        int lossPoint = -1;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_LOSS.getSpString());
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                lossPoint = result.getInt(StoredProcedureConstants.LOSS_POINT.getSpString());
            }
            result.close();
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
        return lossPoint;
    }

    @Override
    public int getMaxPlayersPerTrade() {
        int maxPlayersPT = -1;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_MAX_PLAYER.getSpString());
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                maxPlayersPT = result.getInt(StoredProcedureConstants.MAX_PLAYER.getSpString());
            }
            result.close();
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
        return maxPlayersPT;
    }

    @Override
    public double getRandomTradeOfferChance() {
        double randTradeOfferChance = -1;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_RANDOM.getSpString());
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                randTradeOfferChance = result.getDouble(StoredProcedureConstants.RANDOM_TRADE.getSpString());
            }
            result.close();
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
        return randTradeOfferChance;
    }

    @Override
    public double getRandomTradeAcceptChance() {
        double randTradeAcceptChance = -1;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_TRADE_ACCEPT.getSpString());
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                randTradeAcceptChance = result.getDouble(StoredProcedureConstants.TRADE_ACCEPT.getSpString());
            }
            result.close();
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
        return randTradeAcceptChance;
    }

    @Override
    public String getUserteamName() {
        return null;
    }
}