package dal.asd.dpl.Standings;

import java.util.List;

public interface IStandingsDb {

    boolean insertToStandings(String leagueName, String conferenceName, String divisionName, String teamName);
    void updateStandingsLosses(String teamName);
    void updateStandingsWin(String teamName);
    public List<String> getTop4TeamsFromStandings(String divisionName);
}
