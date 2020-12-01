package dpl.LeagueManagementTest.StandingsTest;

public class StandingsMock {

    private static StandingsMock instance;
    private int season;
    private String leagueName;
    private String conferenceName;
    private String divisionName;
    private String teamName;
    private int wins;
    private int loss;
    private int points;
    private int gameLoss;

    public static StandingsMock getInstance(int season, String leagueName, String conferenceName, String divisionName, String teamName) {
        if (instance == null) {
            instance = new StandingsMock(season, leagueName, conferenceName, divisionName, teamName);
        }
        return instance;
    }

    public StandingsMock(int season, String leagueName, String conferenceName, String divisionName, String teamName) {
        this.season = season;
        this.leagueName = leagueName;
        this.conferenceName = conferenceName;
        this.divisionName = divisionName;
        this.teamName = teamName;
    }

    public String getDivisionName(){
        return  this.divisionName;
    }

    public String getTeamName() {
        return this.teamName;
    }
}
