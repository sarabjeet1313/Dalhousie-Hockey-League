package dal.asd.dpl.TeamManagementTest;

import java.util.List;

import dal.asd.dpl.TeamManagement.IPlayerInfo;
import dal.asd.dpl.TeamManagement.ITeamInfo;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;

public class TeamInfoMock implements ITeamInfo {

	@Override
	public double getTeamStrength(String teamName, League league) {

		LeagueMockData leagueMock= new LeagueMockData();
		List<Player> players = leagueMock.getPlayersByTeam(teamName, league);
		IPlayerInfo playerInfo = new PlayerInfoMock();

		double teamStrength = 0.0;
		for (Player player : players) {
			teamStrength = teamStrength + playerInfo.getPlayerStrength(player);
		}

		return teamStrength;
	}

	@Override
	public boolean shouldReverseResult(double randomChance){
		double result = Math.random();

		if(result < randomChance) {
			return true;
		}
		else {
			return false;
		}
	}

}
