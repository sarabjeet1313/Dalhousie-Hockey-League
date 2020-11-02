package dpl.StandingsTest;

public class StandingsMock {

    private int season;
    private String leagueName;
    private String conferenceName;
    private String divisionName;
    private String teamName;
    private int wins;
    private int loss;
    private int points;
    private int gameLoss;

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
