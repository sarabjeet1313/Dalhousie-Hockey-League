package dpl.TeamManagement;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player);

    public League replaceRetiredPlayers(League league);

    public League increaseAge(int days, League league);

}
