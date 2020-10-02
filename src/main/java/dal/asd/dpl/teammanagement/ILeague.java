package dal.asd.dpl.teammanagement;

public interface ILeague {
	
	public Leagues getLeagueData(String teamName);
	
	public int checkLeagueName(String leagueName);
	
}
