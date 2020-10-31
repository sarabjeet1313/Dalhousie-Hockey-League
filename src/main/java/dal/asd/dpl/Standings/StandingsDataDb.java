package dal.asd.dpl.Standings;

import dal.asd.dpl.Database.InvokeStoredProcedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StandingsDataDb implements IStandingsDb {

    private InvokeStoredProcedure isp;
    private int season;

    public StandingsDataDb(int season) {
        this.season = season;
    }

    public void updateStandingsWin(String teamName) {
        try {
            isp = new InvokeStoredProcedure("spUpdateStandingsWin(?, ?, ?)");
            isp.setParameter(1, this.season);
            isp.setParameter(2, teamName);
            isp.executeQueryWithResults();
        }
        catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        }
        finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
    }

    public void updateStandingsLosses(String teamName) {
        try {
            isp = new InvokeStoredProcedure("spUpdateStandingsLose(?, ?, ?)");
            isp.setParameter(1, this.season);
            isp.setParameter(2, teamName);
            isp.executeQueryWithResults();
        }
        catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        }
        finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
    }

    public boolean insertToStandings(String leagueName, String conferenceName, String divisionName, String teamName) {

        ResultSet result;
        boolean isInserted = false;
        try {
            isp = new InvokeStoredProcedure("spInsertToStandings(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            isp.setParameter(1, this.season);
            isp.setParameter(2, leagueName);
            isp.setParameter(3, conferenceName);
            isp.setParameter(4, divisionName);
            isp.setParameter(5, teamName);
            isp.setParameter(6, 0);
            isp.setParameter(7, 0);
            isp.setParameter(8, 0);
            isp.setParameter(9, 0);
            isp.setParameter(10, 0);
            result = isp.executeQueryWithResults();
            while(result.next()) {
                isInserted = result.getBoolean("success");
            }
        }
        catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        }
        finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return isInserted;
    }

    public List<String> getTop4TeamsFromStandings(String divisionName) {
        ResultSet result;
        List<Integer> teamIds = getTopSeededTeamIds(divisionName);
        List<String> teamList = new ArrayList<>();

        for(int id : teamIds) {
            try {
                isp = new InvokeStoredProcedure("spGetTeamName(?)");
                isp.setParameter(1, id);
                result = isp.executeQueryWithResults();
                while(result.next()) {
                    teamList.add(result.getString("teamName"));
                }
            }
            catch (Exception e) {
                System.out.println("Database Error:" + e.getMessage());
            }
            finally {
                try {
                    isp.closeConnection();
                } catch (SQLException e) {
                    System.out.println("Database Error:" + e.getMessage());
                }
            }
        }
        return teamList;

    }

    private List<Integer> getTopSeededTeamIds(String divisionName) {
        ResultSet result;
        List<Integer> teamIdList = new ArrayList<>();
        try {
            isp = new InvokeStoredProcedure("spGetTopSeededTeams(?, ?)");
            isp.setParameter(1, this.season);
            isp.setParameter(2, divisionName);
            result = isp.executeQueryWithResults();
            while(result.next()) {
                teamIdList.add(result.getInt("teamIdStanding"));
            }
            return teamIdList;
        }
        catch (Exception e) {
            System.out.println("Database Error:" + e.getMessage());
        }
        finally {
            try {
                isp.closeConnection();
            } catch (SQLException e) {
                System.out.println("Database Error:" + e.getMessage());
            }
        }
        return teamIdList;
    }
}
