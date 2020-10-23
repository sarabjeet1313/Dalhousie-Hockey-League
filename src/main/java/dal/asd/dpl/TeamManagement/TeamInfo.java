package dal.asd.dpl.TeamManagement;

import java.util.List;

public class TeamInfo implements ITeamInfo {
	
	PlayerInfo playerInfo = new PlayerInfo();

	@Override
	public double getTeamStrength(List<Player> players) {
		
		double teamStrength = 0.0;
		
		for(Player player: players) {
			
			teamStrength = teamStrength + playerInfo.getPlayerStrength(player);
			
		}
		
		return teamStrength;
	}

}
