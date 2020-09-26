package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class DivisionsTest {
	
	@Test
	public void getDivisionNameTest() {
		ArrayList<Teams> teamList = null;
		Divisions division = new Divisions("Atlantic", teamList);
		Assert.assertEquals("Atlantic", division.getDivisionName());
	}
	
	@Test
	public void setDivisionNameTest() {
		ArrayList<Teams> teamList = null;
		Divisions division = new Divisions("Atlantic", teamList);
		division.setDivisionName("Adams");
		Assert.assertEquals("Adams", division.getDivisionName());
	}
	
	@Test
	public void getTeamTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		teamList.add(team);
		Divisions division = new Divisions("Atlantic", teamList);
		Assert.assertEquals(1, division.getTeamList().size());
	}
	
	@Test
	public void setTeamTest() {
		ArrayList<Players> playerList = null;
		ArrayList<Players> playerList1 = null;
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Teams team1 = new Teams("Florida", "Ashely", "Robert", playerList1);
		teamList.add(team);
		Divisions division = new Divisions("Atlantic", teamList);
		teamList.add(team1);
		division.setTeamList(teamList);
		Assert.assertEquals(2, division.getTeamList().size());
	}
}
