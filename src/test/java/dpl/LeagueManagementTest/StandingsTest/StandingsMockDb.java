package dpl.LeagueManagementTest.StandingsTest;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.Standing;
import dpl.LeagueManagement.Standings.TeamStanding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandingsMockDb implements IStandingsPersistance {

    private static StandingsMockDb instance;
    private int season;
    private Map<String, Integer> standingsTeamWin;
    private Map<String, Integer> standingsTeamLoss;
    private List<StandingsMock> standings;
    private List<TeamStanding> teamStandings;
    private Map<String, Integer> teamWinMap;

    public static StandingsMockDb getInstance() {
        if (instance == null) {
            instance = new StandingsMockDb(0);
        }
        return instance;
    }

    public StandingsMockDb(int season) {
        this.season = season;
        this.standingsTeamWin = new HashMap<>();
        this.standingsTeamLoss = new HashMap<>();
        this.teamStandings = new ArrayList<>();
        this.standings = new ArrayList<>();
        this.teamWinMap = standingsTeamWin;

        standingsTeamWin.put("Halifax", 2);
        standingsTeamWin.put("Boston", 0);
        standingsTeamLoss.put("Boston", 3);
        standingsTeamLoss.put("Halifax", 0);
        StandingsMock standing1 = StandingsMock.getInstance(season, "Dal Hockey League", "Eastern Conference", "Atlantic", "Boston");
        StandingsMock standing2 = StandingsMock.getInstance(season, "Dal Hockey League", "Eastern Conference", "Atlantic", "Halifax");
        StandingsMock standing3 = StandingsMock.getInstance(season, "Dal Hockey League", "Eastern Conference", "Pacific", "Toronto");
        standings.add(standing1);
        standings.add(standing2);

        TeamStanding teamStanding1 = new TeamStanding();
        teamStanding1.setTeamName("Boston");
        teamStanding1.setWins(13);
        teamStanding1.setPoints(26);
        teamStanding1.setLosses(12);

        TeamStanding teamStanding2 = new TeamStanding();
        teamStanding2.setTeamName("Toronto");
        teamStanding2.setWins(12);
        teamStanding2.setPoints(22);
        teamStanding2.setLosses(13);

        TeamStanding teamStanding3 = new TeamStanding();
        teamStanding3.setTeamName("Brampton");
        teamStanding3.setWins(33);
        teamStanding3.setPoints(66);
        teamStanding3.setLosses(2);

        teamStandings.add(teamStanding1);
        teamStandings.add(teamStanding2);
        teamStandings.add(teamStanding3);
    }

    public void setSeason(int season) {
        this.season = season;
    }


    public void updateStandingsWin(String teamName) {
        if(standingsTeamWin.containsKey(teamName)){
            int wins = standingsTeamWin.get(teamName);
            standingsTeamWin.put(teamName, wins+1);
        }
        else {
            standingsTeamWin.put(teamName, 1);
        }
    }

    public Map<String, Integer> getStandingsTeamWin() {
        return standingsTeamWin;
    }

    public void updateStandingsLosses(String teamName) {
        if(standingsTeamLoss.containsKey(teamName)){
            int wins = standingsTeamLoss.get(teamName);
            standingsTeamLoss.put(teamName, wins+1);
        }
        else {
            standingsTeamLoss.put(teamName, 1);
        }
    }

    public Map<String, Integer> getStandingsTeamLoss() {
        return standingsTeamLoss;
    }

    public boolean insertToStandings(Standing standing) {
        int season = 1;
        String leagueName = "Dal Hockey League";
        String divisionName = "Pacific";
        String conferenceName = "Eastern Conference";
        String teamName = "Test Team";
        StandingsMock  standingInfo = new StandingsMock(season, leagueName, conferenceName, divisionName, teamName);
        standings.add(standingInfo);
        return true;
    }

    public List<StandingsMock> getStandings() {
        return standings;
    }

    public List<String> getTop4TeamsFromStandings(String divisionName) {
        List<String> teams = new ArrayList<>();
        for(StandingsMock standing : standings) {
            if(standing.getDivisionName() == divisionName) {
                teams.add(standing.getTeamName());
            }
        }
        return teams;
    }

    public List<TeamStanding> getTeamStandings() {
        return teamStandings;
    }
}
