package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

public interface IRosterManagement {

    public boolean checkRoster(String teamName, League league);
    public boolean updateActiveStatus(String teamName, League league);
    public League updateLeagueActiveStatus(League league);
    public boolean balanceOutRoster(League league);
}
