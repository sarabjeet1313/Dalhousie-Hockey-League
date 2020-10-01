package dal.asd.dpl.teammanagement;

import org.junit.Test;

import junit.framework.Assert;



public class PlayersTest {
	
	@Test
	public void getPlayerNameTest() {
		Players player = new Players("Player1", "Forword", true);
		Assert.assertEquals("Player1", player.getPlayerName());
	} 
	
	@Test
	public void setPlayerNameTest() {
		Players player = new Players("Player1", "Forword", true);
		player.setPlayerName("Player2");
		Assert.assertEquals("Player2",player.getPlayerName());
	}
	
	@Test
	public void getPlayerPositionTest() {
		Players player = new Players("Player1", "Forword", true);
		Assert.assertEquals("Forword", player.getPlayerPosition());
	} 
	
	@Test
	public void setPlayerPositionTest() {
		Players player = new Players("Player1", "Forword", true);
		player.setPlayerPosition("Defender");
		Assert.assertEquals("Defender",player.getPlayerPosition());
	}
	
	@Test
	public void getCaptainTest() {
		Players player = new Players("Player1", "Forword", true);
		Assert.assertTrue(player.getCaptain());
	} 
	
	@Test
	public void setCaptainTest() {
		Players player = new Players("Player1", "Forword", false);
		player.setCaptain(false);
		Assert.assertTrue(player.getCaptain());
	}
}
