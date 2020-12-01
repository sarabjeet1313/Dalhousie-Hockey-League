package dpl.LeagueManagementTest.TeamManagementTest;

import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.IPlayerInfo;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.Player;

import org.junit.Assert;

public class PlayerTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 23, 3, 1999, Boolean.FALSE);
	IPlayerInfo playerInfo = new PlayerInfoMock();
	private static final double DELTA = 1e-15;

	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
		Assert.assertEquals("Forward", player.getPosition());
		Assert.assertEquals(1, player.getSkating());
		Assert.assertEquals(1, player.getShooting());
		Assert.assertEquals(1, player.getChecking());
		Assert.assertEquals(1, player.getSaving());
		Assert.assertFalse(player.isCaptain());
		Assert.assertFalse(player.isInjured());
	}

	@Test
	public void getPlayerNameTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
	}

	@Test
	public void setPlayerNameTest() {
		player.setPlayerName("Player2");
		Assert.assertEquals("Player2", player.getPlayerName());
	}

	@Test
	public void getPlayerPositionTest() {
		Assert.assertEquals("Forward", player.getPosition());
	}

	@Test
	public void setPlayerPositionTest() {
		player.setPosition("Defender");
		Assert.assertEquals("Defender", player.getPosition());
	}

	@Test
	public void getCaptainTest() {
		Assert.assertFalse(player.isCaptain());
	}

	@Test
	public void setCaptainTest() {
		player.setCaptain(true);
		Assert.assertTrue(player.isCaptain());
	}

	@Test
	public void getAgeTest() {
		Assert.assertEquals(1, player.getAge());
	}

	@Test
	public void setAgeTest() {
		player.setAge(1);
		Assert.assertEquals(1, player.getAge());
	}

	@Test
	public void getSkatingTest() {
		Assert.assertEquals(1, player.getSkating());
	}

	@Test
	public void setSkatingTest() {
		player.setSkating(1);
		Assert.assertEquals(1, player.getSkating());
	}

	@Test
	public void getShootingTest() {
		Assert.assertEquals(1, player.getShooting());
	}

	@Test
	public void setShootingTest() {
		player.setShooting(1);
		Assert.assertEquals(1, player.getShooting());
	}

	@Test
	public void getCheckingTest() {
		Assert.assertEquals(1, player.getChecking());
	}

	@Test
	public void setCheckingTest() {
		player.setChecking(1);
		Assert.assertEquals(1, player.getChecking());
	}

	@Test
	public void getSavingTest() {
		Assert.assertEquals(1, player.getSaving());
	}

	@Test
	public void setSavingTest() {
		player.setSaving(1);
		Assert.assertEquals(1, player.getSaving());
	}

	@Test
	public void getInjuredTest() {
		Assert.assertFalse(player.isInjured());
	}

	@Test
	public void setInjuredTest() {
		player.setInjured(true);
		Assert.assertTrue(player.isInjured());
	}

	@Test
	public void getPlayerStrengthTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false,
				0, false, 23, 3, 1999, Boolean.FALSE);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(2.5, strength, DELTA);
	}

	@Test
	public void getPlayerStrengthTwoTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 2, 1, 1, 1, false, false,
				0, false, 23, 3, 1999, Boolean.FALSE);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertNotEquals(2.5, strength, DELTA);
	}

	@Test
	public void getPlayerStrengthThreeTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, true, false, 0, false, 23, 3, 1999, Boolean.FALSE);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(1.25, strength, DELTA);
	}

	@Test
	public void getPlayerStrengthFourTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Defense", false, 1, 1, 2, 1, 1, true, false, 0, false, 23, 3, 1999, Boolean.FALSE);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertEquals(1.5, strength, DELTA);
	}

	@Test
	public void getPlayerStrengthFiveTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 2, 1, 1, 1, false, false,
				0, false, 23, 3, 1999, Boolean.FALSE);
		double strength = playerInfo.getPlayerStrength(player);
		Assert.assertNotEquals(4.5, strength, DELTA);
	}

	@Test
	public void getGoalsTest() {
		Assert.assertNotEquals(10, player.getGoals());
		player.setGoals(10);
		Assert.assertEquals(10, player.getGoals());
	}

	@Test
	public void setGoalsTest() {
		Assert.assertNotEquals(10, player.getGoals());
		player.setGoals(10);
		Assert.assertEquals(10, player.getGoals());
	}

	@Test
	public void getPenaltiesTest() {
		Assert.assertNotEquals(20, player.getPenalties());
		player.setPenalties(20);
		Assert.assertEquals(20, player.getPenalties());
	}

	@Test
	public void setPenaltiesTest() {
		Assert.assertNotEquals(20, player.getPenalties());
		player.setPenalties(20);
		Assert.assertEquals(20, player.getPenalties());
	}

	@Test
	public void getSavesTest() {
		Assert.assertNotEquals(30, player.getSaves());
		player.setSaves(30);
		Assert.assertEquals(30, player.getSaves());
	}

	@Test
	public void setSavesTest() {
		Assert.assertNotEquals(30, player.getSaves());
		player.setSaves(30);
		Assert.assertEquals(30, player.getSaves());
	}

}
