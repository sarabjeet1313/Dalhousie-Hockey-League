package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import org.junit.Assert;

public class ConferencesTest {
	ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
	Conferences conference = new Conferences("Eastern Conference", divisionList);
	ArrayList<Teams> teamList = new ArrayList<Teams>();
	ArrayList<Teams> teamList1 = new ArrayList<Teams>();
	Divisions division = new Divisions("Atlantic", teamList);
	Divisions division1 = new Divisions("Florida", teamList1);
	
	@Test
	public void parameterizedConstructorTest() {
		divisionList.add(division);
		divisionList.add(division1);
		conference.setDivisionList(divisionList);
		Assert.assertEquals("Eastern Conference", conference.getConferenceName());
		Assert.assertEquals(2, conference.getDivisionList().size());
	}
	
	@Test
	public void getConferenceNameTest() {
		Assert.assertEquals("Eastern Conference", conference.getConferenceName());
	}
	
	@Test
	public void setConferenceNameTest() {
		conference.setConferenceName("Western Conference");
		Assert.assertEquals("Western Conference", conference.getConferenceName());
	}
	
	@Test
	public void getDivisionListTest() {
		divisionList.add(division);
		conference.setDivisionList(divisionList);
		Assert.assertEquals(1, conference.getDivisionList().size());
	}
	
	@Test
	public void setDivisionListTest() {
		divisionList.add(division);
		Conferences conference1 = new Conferences("Westren Conference", divisionList);
		divisionList.add(division1);
		conference1.setDivisionList(divisionList);
		Assert.assertEquals(2, conference1.getDivisionList().size());
	}
	
	@Test
	public void isValidConferenceNameTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		String conferenceName = "Eastern Conference";
		Assert.assertTrue(conference.isValidConferenceName(conferenceName, leagueData.getLeagueData()));
	}
}
