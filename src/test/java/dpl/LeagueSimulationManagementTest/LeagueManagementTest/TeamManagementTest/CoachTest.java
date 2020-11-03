package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Coach;

public class CoachTest {
	
	Coach coach = new Coach("Coach1", 0.1, 0.1, 0.1, 0.1);
	private static final double DELTA = 1e-15;

	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Coach1", coach.getCoachName());
		Assert.assertEquals(0.1, coach.getSkating(), DELTA);
		Assert.assertEquals(0.1, coach.getShooting(), DELTA);
		Assert.assertEquals(0.1, coach.getChecking(), DELTA);
		Assert.assertEquals(0.1, coach.getSaving(), DELTA);
	}
	
	@Test
	public void getCoachNameTest() {
		Assert.assertEquals("Coach1", coach.getCoachName());
	} 
	
	@Test
	public void setCoachNameTest() {
		coach.setCoachName("Coach2");
		Assert.assertEquals("Coach2", coach.getCoachName());
	}
	
	@Test
	public void getSkatingTest() {
		Assert.assertEquals(0.1, coach.getSkating(), DELTA);
	} 
	
	@Test
	public void setSkatingTest() {
		coach.setSkating(0.1);
		Assert.assertEquals(0.1, coach.getSkating(), DELTA);
	}
	
	@Test
	public void getCheckingTest() {
		Assert.assertEquals(0.1, coach.getChecking(), DELTA);
	} 
	
	@Test
	public void setCheckingTest() {
		coach.setChecking(0.1);
		Assert.assertEquals(0.1, coach.getChecking(), DELTA);
	}
	
	@Test
	public void getSavingTest() {
		Assert.assertEquals(0.1, coach.getSaving(), DELTA);
	} 
	
	@Test
	public void setSavingTest() {
		coach.setSaving(0.1);
		Assert.assertEquals(0.1, coach.getSaving(), DELTA);
	}
	
	@Test
	public void setSavingTwoTest() {
		coach.setSaving(0.1);
		Assert.assertNotEquals(0.2, coach.getSaving(), DELTA);
	}
}
