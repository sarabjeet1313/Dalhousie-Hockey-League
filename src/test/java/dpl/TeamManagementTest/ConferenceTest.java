package dpl.TeamManagementTest;

import java.util.ArrayList;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

import org.junit.Assert;

public class ConferenceTest {
	
	ArrayList<Division> divisionList = new ArrayList<Division>();
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	Conference conference = teamManagement.ConferenceWithParameters("Eastern Conference", divisionList);
	ArrayList<Team> teamList = new ArrayList<Team>();
	ArrayList<Team> teamList1 = new ArrayList<Team>();
	Division division = teamManagement.DivisionWithParameters("Atlantic", teamList);
	Division division1 = teamManagement.DivisionWithParameters("Florida", teamList1);
	
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
		Conference conference1 = teamManagement.ConferenceWithParameters("Westren Conference", divisionList);
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
