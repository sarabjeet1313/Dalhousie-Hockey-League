package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.Database.InvokeStoredProcedure;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Teams;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Standings {

    private Leagues leagueToSimulate;
    private int season;
    private InvokeStoredProcedure isp;
    private Map<String, Integer> teamWinMap;
    private Map<String, Integer> teamLoseMap;

    public Standings(Leagues leagueToSimulate, int season) {
        this.leagueToSimulate = leagueToSimulate;
        teamWinMap = new HashMap<>();
        teamLoseMap = new HashMap<>();
        this.season = season;
    }

    public void updateTeamWinMap(String teamName, int wins){
        teamWinMap.put(teamName, wins);
    }

    public void updateTeamLoseMap(String teamName, int losses){
        teamLoseMap.put(teamName, losses);
    }

    public boolean initializeStandings() {

        boolean result = false;
        String leagueName = leagueToSimulate.getLeagueName();
        List<Conferences> conferenceList =  leagueToSimulate.getConferenceList();

        for (Conferences conferences : conferenceList) {
            List<Divisions> divisionList = conferences.getDivisionList();
            String conferenceName = conferences.getConferenceName();

            for (Divisions divisions : divisionList) {
                List<Teams> teamList = divisions.getTeamList();
                String divisionName = divisions.getDivisionName();

                for (Teams teams : teamList) {
                    String teamName = teams.getTeamName();
                    result = insertToStandings(leagueName, conferenceName, divisionName, teamName);
                    if (result) {
                        continue;
                    } else {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public void updateStandings() {
        for (Map.Entry<String, Integer> entry : this.teamWinMap.entrySet()) {
            String teamWon = entry.getKey();
            int noOfMatchesWon = entry.getValue();

            for(int i=0; i < noOfMatchesWon; i++){
                updateStandingsWin(teamWon);
            }
        }
        for (Map.Entry<String, Integer> entry : this.teamLoseMap.entrySet()) {
            String teamLose = entry.getKey();
            int noOfMatchesLose = entry.getValue();

            for(int i=0; i < noOfMatchesLose; i++){
                updateStandingsLosses(teamLose);
            }
        }
    }

    private void updateStandingsWin(String teamName) {
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

    private void updateStandingsLosses(String teamName) {
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

    private boolean insertToStandings(String leagueName, String conferenceName, String divisionName, String teamName) {

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

    public List<String> getTopDivisionTeams(String divisionName) {
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
