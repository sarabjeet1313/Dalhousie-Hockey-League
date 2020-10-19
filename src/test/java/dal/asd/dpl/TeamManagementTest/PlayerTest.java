package dal.asd.dpl.TeamManagementTest;

import org.junit.Test;
import dal.asd.dpl.TeamManagement.Player;
import org.junit.Assert;

public class PlayerTest {
	
	Player player = new Player("Player1", "Forword", false, 1, 1, 1, 1, 1);
	
	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Player1", player.getPlayerName());
		Assert.assertEquals("Forword", player.getPlayerPosition());
		Assert.assertEquals(1, player.getSkating());
		Assert.assertEquals(1, player.getShooting());
		Assert.assertEquals(1, player.getChecking());
		Assert.assertEquals(1, player.getSaving());
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
	
}
