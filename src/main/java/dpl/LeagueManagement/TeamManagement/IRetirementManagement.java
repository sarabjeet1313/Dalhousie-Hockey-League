package dpl.LeagueManagement.TeamManagement;

import java.text.ParseException;

public interface IRetirementManagement {

	public int getLikelihoodOfRetirement(League league, Player player);

	public boolean shouldPlayerRetire(League league, Player player);

	public League replaceRetiredPlayers(League league) throws IndexOutOfBoundsException;

	public League increaseAge(String currentDate, League league) throws IndexOutOfBoundsException, ParseException;

}
