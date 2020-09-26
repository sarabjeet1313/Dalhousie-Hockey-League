package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class ConferencesTest {

	@Test
	public void getConferenceNameTest() {
		ArrayList<Divisions> divisionList = null;
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		Assert.assertEquals("Eastern Conference", conference.getConferenceName());
	}
	
	@Test
	public void setConferenceNameTest() {
		ArrayList<Divisions> divisionList = null;
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		conference.setConferenceName("Western Conference");
		Assert.assertEquals("Western Conference", conference.getConferenceName());
	}
	
	@Test
	public void getDivisionListTest() {
		ArrayList<Teams> teamList = null;
		Divisions division = new Divisions("Atlantic", teamList);
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		divisionList.add(division);
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		Assert.assertEquals(1, conference.getDivisionList().size());
	}
	
	@Test
	public void setDivisionListTest() {
		ArrayList<Teams> teamList = null;
		ArrayList<Teams> teamList1 = null;
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		Divisions division = new Divisions("Atlantic", teamList);
		Divisions division1 = new Divisions("Florida", teamList1);
		divisionList.add(division);
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		divisionList.add(division1);
		conference.setDivisionList(divisionList);
		Assert.assertEquals(2, conference.getDivisionList().size());
	}

}
