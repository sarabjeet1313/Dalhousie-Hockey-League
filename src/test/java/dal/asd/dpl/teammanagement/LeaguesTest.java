package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class LeaguesTest {
	
	@Test
	public void getLeagueNameTest() {
		ArrayList<Conferences> conferenceList = null;
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList);
		Assert.assertEquals("Dalhousie Hockey League", league.getLeagueName());
	}
	
	@Test
	public void setLeagueNameTest() {
		ArrayList<Conferences> conferenceList = null;
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList);
		league.setLeagueName("Test League");
		Assert.assertEquals("Test League", league.getLeagueName());
	}
	
	@Test
	public void getDivisionListTest() {
		ArrayList<Divisions> divisionList = null;
		Conferences conference = new Conferences("Atlantic", divisionList);
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		conferenceList.add(conference);
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList);
		Assert.assertEquals(1, league.getConferenceList().size());
	}
	
	@Test
	public void setDivisionListTest() {
		ArrayList<Divisions> divisionList = null;
		ArrayList<Divisions> divisionList1 = null;
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		Conferences conference1 = new Conferences("Western Conference", divisionList1);
		conferenceList.add(conference);
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList);
		conferenceList.add(conference1);
		league.setConferenceList(conferenceList);
		Assert.assertEquals(2, league.getConferenceList().size());
	}
	
}
