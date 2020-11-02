package dal.asd.dpl.StandingsTest;
import dal.asd.dpl.Standings.IStandingsPersistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandingsMockDb implements IStandingsPersistance {
    private int season;
    private Map<String, Integer> standingsTeamWin;
    private Map<String, Integer> standingsTeamLoss;
    private List<StandingsMock> standings;

    public StandingsMockDb(int season) {
        this.season = season;
        this.standingsTeamWin = new HashMap<>();
        this.standingsTeamLoss = new HashMap<>();
        this.standings = new ArrayList<>();

        standingsTeamWin.put("Halifax", 2);
        standingsTeamWin.put("Boston", 0);
        standingsTeamLoss.put("Boston", 3);
        standingsTeamLoss.put("Halifax", 0);
        StandingsMock standing1 = new StandingsMock(season, "Dal Hockey League", "Eastern Conference", "Atlantic", "Boston");
        StandingsMock standing2 = new StandingsMock(season, "Dal Hockey League", "Eastern Conference", "Atlantic", "Halifax");
        StandingsMock standing3 = new StandingsMock(season, "Dal Hockey League", "Eastern Conference", "Pacific", "Toronto");
        standings.add(standing1);
        standings.add(standing2);
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

    public boolean insertToStandings(String leagueName, String conferenceName, String divisionName, String teamName) {
        StandingsMock  standing = new StandingsMock(season, leagueName, conferenceName, divisionName, teamName);
        standings.add(standing);
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
}
