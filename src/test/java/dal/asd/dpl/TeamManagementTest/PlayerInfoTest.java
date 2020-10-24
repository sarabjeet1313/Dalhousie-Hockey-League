package dal.asd.dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.IPlayerInfo;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.PlayerInfo;

public class PlayerInfoTest {
	
	IPlayerInfo playerInfo = new PlayerInfo();
	private static final double DELTA = 1e-15;
	
	@Test
	public void getPlayerStrengthTest() {
		Player player = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(2.5, strength, DELTA);
	}
	
	@Test
	public void getPlayerStrengthTwoTest() {
		Player player = new Player("Player1", "Forward", false, 1, 2, 1, 1, 1, false);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertNotEquals(2.5, strength, DELTA);
	}
	
	@Test
	public void getPlayerStrengthThreeTest() {
		Player player = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, true);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(1.25, strength, DELTA);
	}
	
	@Test
	public void getPlayerStrengthFourTest() {
		Player player = new Player("Player1", "Defense", false, 1, 1, 2, 1, 1, true);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(1.5, strength, DELTA);
	}

}
