package dal.asd.dpl.TeamManagement;

public interface ITeamInfo {

	public double getTeamStrength(String teamName, League league);
	public boolean shouldReverseResult(double randomChance);
	
}
