package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.text.ParseException;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player);

    public League replaceRetiredPlayers(League league) throws IOException;

    public League increaseAge(String currentDate, League league) throws IOException, ParseException;

}
