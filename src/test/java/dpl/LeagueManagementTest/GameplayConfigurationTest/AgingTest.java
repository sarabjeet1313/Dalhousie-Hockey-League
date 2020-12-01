package dpl.LeagueManagementTest.GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class AgingTest {

	private IGameplayConfigurationAbstractFactory gameConfig = SystemConfig.getSingleInstance()
			.getGameplayConfigurationAbstractFactory();
	private League leagueToSimulate;
	private Aging aging;

	@Before
	public void setUp() throws Exception {
		leagueToSimulate = new LeagueMockData().getTestData();
		aging = gameConfig.Aging(35, 45, 0.5);
	}

	@Test
	public void parameterizedConstructorTest() {
		assertEquals(35, aging.getAverageRetirementAge());
		assertEquals(45, aging.getMaximumAge());
		assertEquals(0.5, aging.getStatDecayChance(), 0.5);
	}

	@Test
	public void getAverageRetirementAgeTest() {
		assertEquals(35, aging.getAverageRetirementAge());
		assertNotEquals(50, aging.getAverageRetirementAge());
	}

	@Test
	public void setAverageRetirementAgeTest() {
		assertEquals(35, aging.getAverageRetirementAge());
		aging.setAverageRetirementAge(38);
		assertEquals(38, aging.getAverageRetirementAge());
	}

	@Test
	public void getMaximumRetirementAgeTest() {
		assertEquals(45, aging.getMaximumAge());
		assertNotEquals(55, aging.getMaximumAge());
	}

	@Test
	public void setMaximumRetirementAgeTest() {
		assertEquals(45, aging.getMaximumAge());
		aging.setMaximumAge(40);
		assertEquals(40, aging.getMaximumAge());
	}

	@Test
	public void getStatDecayChanceTest() {

		assertEquals(0.5, aging.getStatDecayChance(), 1);
		assertNotEquals(50, aging.getStatDecayChance());
	}

	@Test
	public void setStatDecayChanceTest() {
		assertEquals(0.5, aging.getStatDecayChance(), 1);
		aging.setStatDecayChance(0.7);
		assertEquals(0.7, aging.getStatDecayChance(), 1);
	}

}
