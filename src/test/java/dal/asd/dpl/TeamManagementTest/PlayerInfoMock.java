package dal.asd.dpl.TeamManagementTest;

import dal.asd.dpl.TeamManagement.IPlayerInfo;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.util.ConstantsUtil;

public class PlayerInfoMock implements IPlayerInfo {

	@Override
	public double getPlayerStrength(Player player) {
		
		double strength = 0.0;
		String position = player.getPosition();
		int skating = player.getSkating();
		int shooting = player.getShooting();
		int checking = player.getChecking();
		int saving = player.getSaving();
		boolean isInjured = player.isInjured();
		
		if(position.equalsIgnoreCase(ConstantsUtil.FORWARD.toString())) {
			strength = skating + shooting + (checking / 2.0);			
		} else if(position.equalsIgnoreCase(ConstantsUtil.DEFENSE.toString())) {			
			strength = skating + checking + (shooting / 2.0);			
		} else if(position.equalsIgnoreCase(ConstantsUtil.GOALIE.toString())) {
			strength = skating + saving;			
		}
		
		if(isInjured) {
			return strength / 2;
		} else {
			return strength;
		}
	}
}
