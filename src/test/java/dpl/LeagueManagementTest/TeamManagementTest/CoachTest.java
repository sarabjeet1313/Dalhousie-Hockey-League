package dpl.LeagueManagementTest.TeamManagementTest;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class CoachTest {
	
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private League leagueToSimulate = new LeagueMockData().getTestData();
	private ICoachPersistance coachMock = new CoachMockData();
	private IUserOutput output = new CmdUserOutput();
	private Coach coach = teamManagement.CoachWithDbParameters("Coach1", 0.1, 0.1, 0.1, 0.1, coachMock);
	private static final double DELTA = 1e-15;

	@Test
	public void parameterizedConstructorTest() {
		assertEquals("Coach1", coach.getCoachName());
		assertEquals(0.1, coach.getSkating(), DELTA);
		assertEquals(0.1, coach.getShooting(), DELTA);
		assertEquals(0.1, coach.getChecking(), DELTA);
		assertEquals(0.1, coach.getSaving(), DELTA);
	}
	
	@Test
	public void getCoachNameTest() {
		assertEquals("Coach1", coach.getCoachName());
	} 
	
	@Test
	public void setCoachNameTest() {
		coach.setCoachName("Coach2");
		assertEquals("Coach2", coach.getCoachName());
	}
	
	@Test
	public void getSkatingTest() {
		assertEquals(0.1, coach.getSkating(), DELTA);
	} 
	
	@Test
	public void setSkatingTest() {
		coach.setSkating(0.1);
		assertEquals(0.1, coach.getSkating(), DELTA);
	}
	
	@Test
	public void getCheckingTest() {
		assertEquals(0.1, coach.getChecking(), DELTA);
	} 
	
	@Test
	public void setCheckingTest() {
		coach.setChecking(0.1);
		assertEquals(0.1, coach.getChecking(), DELTA);
	}
	
	@Test
	public void getSavingTest() {
		assertEquals(0.1, coach.getSaving(), DELTA);
	} 
	
	@Test
	public void setSavingTest() {
		coach.setSaving(0.1);
		assertEquals(0.1, coach.getSaving(), DELTA);
	}
	
	@Test
	public void setSavingTwoTest() {
		coach.setSaving(0.1);
		assertNotEquals(0.2, coach.getSaving());
	}
	
	@Test
	public void saveTeamCoachesTest() {
		String teamName = "Boston";
		try {
			assertFalse(coach.saveTeamCoaches(coach, teamName, leagueToSimulate.getLeagueName()));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}
	
	@Test
	public void saveLeagueCoaches() {
		try {
			assertFalse(coach.saveLeagueCoaches(leagueToSimulate));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}
}
