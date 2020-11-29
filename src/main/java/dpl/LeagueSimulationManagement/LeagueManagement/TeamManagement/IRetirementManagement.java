package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player) throws SQLException;

    public League replaceRetiredPlayers(League league) throws SQLException, IOException;

    public League increaseAge(String currentDate, League league) throws SQLException, IOException, ParseException;

}
