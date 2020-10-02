package dal.asd.dpl.teammanagement;

import org.junit.Test;

import org.junit.Assert;

public class PlayersTest {
	Players player = new Players("Player1", "Forword", false);
	
	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
		Assert.assertEquals("Forword", player.getPlayerPosition());
		Assert.assertTrue(player.getCaptain());
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
		Assert.assertTrue(player.getCaptain());
	} 
	
	@Test
	public void setCaptainTest() {
		player.setCaptain(true);
		Assert.assertTrue(player.getCaptain());
	}
}
