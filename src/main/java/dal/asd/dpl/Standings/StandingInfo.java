package dal.asd.dpl.Standings;

import dal.asd.dpl.Database.InvokeStoredProcedure;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandingInfo {

    private League leagueToSimulate;
    private int season;
    private InvokeStoredProcedure isp;
    private Map<String, Integer> teamWinMap;
    private Map<String, Integer> teamLoseMap;
    private IStandingsDb standingsDb;

    public StandingInfo(League leagueToSimulate, int season, IStandingsDb standingsDb) {
        this.leagueToSimulate = leagueToSimulate;
        this.standingsDb = standingsDb;
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

    public Map<String, Integer> getTeamWinMap() {
        return teamWinMap;
    }

    public Map<String, Integer> getTeamLoseMap() {
        return teamLoseMap;
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
                    result = standingsDb.insertToStandings(leagueName, conferenceName, divisionName, teamName);
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
                standingsDb.updateStandingsWin(teamWon);
            }
            teamWinMap.put(teamWon, 0);
        }

        for (Map.Entry<String, Integer> entry : this.teamLoseMap.entrySet()) {
            String teamLose = entry.getKey();
            int noOfMatchesLose = entry.getValue();

            for(int i=0; i < noOfMatchesLose; i++){
                standingsDb.updateStandingsLosses(teamLose);
            }
            teamLoseMap.put(teamLose, 0);
        }
    }
}
