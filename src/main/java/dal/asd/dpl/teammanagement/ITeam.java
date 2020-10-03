package dal.asd.dpl.teammanagement;

public interface ITeam {
	
	public boolean isValidTeamName (String teamName, String LeagueName);
	
	public boolean isValidGeneralManager (String generalManager, String LeagueName);
	
	public boolean isValidHeadCoach (String headCoach, String LeagueName);
	
}
