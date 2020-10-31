package dal.asd.dpl.Trading;

import dal.asd.dpl.Database.InvokeStoredProcedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeDB implements ITradeDB{

    InvokeStoredProcedure isp = null;

    @Override
    public List<String> getEligibleTeamName(int lossPoint) {
        List<String> eligibleTeamName = new ArrayList<>();
        ResultSet result;
        try{
            isp = new InvokeStoredProcedure("spLoadEligibleTeamName(?)");
            isp.setParameter(1, lossPoint);
            result = isp.executeQueryWithResults();
            while (result.next()){
                eligibleTeamName.add(result.getString("teamName"));
            }
        result.close();
        }catch (Exception e){
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
    public int getLossPoint() {
        int lossPoint = -1;
        ResultSet result;
        try{
            isp = new InvokeStoredProcedure("spGetLossPoint()");
            result = isp.executeQueryWithResults();
            while (result.next()){
                lossPoint = result.getInt("lossPoint");
            }
            result.close();
        }catch (Exception e){
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return lossPoint;
    }

    @Override
    public int getMaxPlayersPerTrade() {
        int maxPlayersPT = -1;
        ResultSet result;
        try{
            isp = new InvokeStoredProcedure("spGetMaxPlayersPerTrade()");
            result = isp.executeQueryWithResults();
            while (result.next()){
                maxPlayersPT = result.getInt("maxPlayersPerTrade");
            }
            result.close();
        }catch (Exception e){
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return maxPlayersPT;
    }

    @Override
    public double getRandomTradeOfferChance() {
        double randTradeOfferChance = -1;
        ResultSet result;
        try{
            isp = new InvokeStoredProcedure("spGetRandomTradeOfferChance()");
            result = isp.executeQueryWithResults();
            while (result.next()){
                randTradeOfferChance = result.getDouble("randomTradeOfferChance");
            }
            result.close();
        }catch (Exception e){
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return randTradeOfferChance;
    }

    @Override
    public double getRandomTradeAcceptChance() {
        double randTradeAcceptChance = -1;
        ResultSet result;
        try{
            isp = new InvokeStoredProcedure("spGetRandomTradeAcceptChance()");
            result = isp.executeQueryWithResults();
            while (result.next()){
                randTradeAcceptChance = result.getDouble("randomTradeAcceptChance");
            }
            result.close();
        }catch (Exception e){
            System.out.println("Database Error:" + e.getMessage());
        } finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return randTradeAcceptChance;
    }

    @Override
    public String getUserteamName() {
        // nope;
        return null;
    }
}
