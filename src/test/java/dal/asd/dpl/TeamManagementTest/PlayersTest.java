package dal.asd.dpl.TeamManagementTest;

import org.junit.Test;
import dal.asd.dpl.TeamManagement.Players;
import org.junit.Assert;

public class PlayersTest {
	
	Players player = new Players("Player1", "Forword", false);
	
	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
		Assert.assertEquals("Forword", player.getPlayerPosition());
		Assert.assertFalse(player.getCaptain());
	}
	
	@Test
	public void getPlayerNameTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
	} 
	
	@Test
	public void setPlayerNameTest() {
		player.setPlayerName("Player2");
		Assert.assertEquals("Player2",player.getPlayerName());
	}
	
	@Test
	public void getPlayerPositionTest() {
		Assert.assertEquals("Forword", player.getPlayerPosition());
	} 
	
	@Test
	public void setPlayerPositionTest() {
		player.setPlayerPosition("Defender");
		Assert.assertEquals("Defender",player.getPlayerPosition());
	}
	
	@Test
	public void getCaptainTest() {
		Assert.assertFalse(player.getCaptain());
	} 
	
	@Test
	public void setCaptainTest() {
		player.setCaptain(true);
		Assert.assertTrue(player.getCaptain());
	}
	
}
