package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.util.List;

public interface ITeamInfo {

    public boolean isValidTeamName(String conferenceName, String divisionName, String teamName, League league);

    public double getTeamStrength(String teamName, League league);

    public boolean shouldReverseResult(double randomChance);

    public List<Player> getPlayersByTeam(String teamName, League league);

    public List<String> getAllTeamName(League league);

    public String getUserTeamName(League league);
}
