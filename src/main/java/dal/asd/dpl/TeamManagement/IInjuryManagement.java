package dal.asd.dpl.TeamManagement;

public interface IInjuryManagement {

	public League updatePlayerInjuryStatus(League league);

	public Player getPlayerInjuryDays(Player player, League league);

	public League getInjuryStatusByTeam(String teamName, League league);

}
