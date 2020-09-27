package dal.asd.dpl.teammanagement;

import org.junit.Test;

import java.util.ArrayList;

import org.junit.Assert;

public class FreeAgentTests {
	Players player1 = new Players("Player1", "Forword", false);
	Players player2 = new Players("Player2", "Forword", false);
	Players player3 = new Players("Player3", "Goalie", false);
	ArrayList<Players> playerList = new ArrayList<Players>();
	FreeAgents freePlayers = new FreeAgents(playerList);
	
	@Test
	public void parameterizedConstructorTest() {
		playerList.add(player1);
		freePlayers.setFreePlayerList(playerList);
		Assert.assertEquals(1, freePlayers.getFreePlayerList().size());
	}
	
	@Test
	public void getFreePlayerListTest() {
		playerList.add(player1);
		playerList.add(player2);
		freePlayers.setFreePlayerList(playerList);
		Assert.assertEquals(2, freePlayers.getFreePlayerList().size());
	}
	
	@Test
	public void setFreePlayerListTest() {
		playerList.add(player1);
		playerList.add(player2);
		FreeAgents freePlayers = new FreeAgents(playerList);
		playerList.add(player3);
		freePlayers.setFreePlayerList(playerList);
		Assert.assertEquals(3, freePlayers.getFreePlayerList().size());
	}
}
