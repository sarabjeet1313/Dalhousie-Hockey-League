package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.Database.InvokeStoredProcedure;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandingInfo {

    private League leagueToSimulate;
    private int season;
    private InvokeStoredProcedure isp;
    private Map<String, Integer> teamWinMap;
    private Map<String, Integer> teamLoseMap;

    public StandingInfo(League leagueToSimulate, int season) {
        this.leagueToSimulate = leagueToSimulate;
        teamWinMap = new HashMap<>();
        teamLoseMap = new HashMap<>();
        this.season = season;
    }

    public void updateTeamWinMap(String teamName){
        if(teamWinMap.containsKey(teamName)){
            int wins = teamWinMap.get(teamName);
            teamWinMap.put(teamName, wins+1);
        }
        else {
            teamWinMap.put(teamName, 1);
        }
    }

    public void updateTeamLoseMap(String teamName){
        if(teamLoseMap.containsKey(teamName)){
            int loses = teamLoseMap.get(teamName);
            teamLoseMap.put(teamName, loses+1);
        }
        else {
            teamLoseMap.put(teamName, 1);
        }
    }

    public boolean initializeStandings() {

        boolean result = false;
        String leagueName = leagueToSimulate.getLeagueName();
        List<Conference> conferenceList =  leagueToSimulate.getConferenceList();

        for (Conference conferences : conferenceList) {
            List<Division> divisionList = conferences.getDivisionList();
            String conferenceName = conferences.getConferenceName();

            for (Division divisions : divisionList) {
                List<Team> teamList = divisions.getTeamList();
                String divisionName = divisions.getDivisionName();

                for (Team teams : teamList) {
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
            teamWinMap.put(teamWon, 0);
        }

        for (Map.Entry<String, Integer> entry : this.teamLoseMap.entrySet()) {
            String teamLose = entry.getKey();
            int noOfMatchesLose = entry.getValue();

            for(int i=0; i < noOfMatchesLose; i++){
                updateStandingsLosses(teamLose);
            }
            teamLoseMap.put(teamLose, 0);
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
