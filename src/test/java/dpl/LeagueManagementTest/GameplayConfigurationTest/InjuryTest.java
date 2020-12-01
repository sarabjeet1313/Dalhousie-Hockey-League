package dpl.LeagueManagementTest.GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class InjuryTest {

	private IGameplayConfigurationAbstractFactory gameConfig = SystemConfig.getSingleInstance()
			.getGameplayConfigurationAbstractFactory();
	private League leagueToSimulate;
	private Injury injury;

	@Before
	public void setUp() throws Exception {
		leagueToSimulate = new LeagueMockData().getTestData();
		injury = gameConfig.Injury(0.4, 10, 260);
	}

	@Test
	public void parameterizedConstructorTest() {
		assertEquals(0.4, injury.getRandomInjuryChance(), 1);
		assertEquals(10, injury.getInjuryDaysLow());
		assertEquals(260, injury.getInjuryDaysHigh());
	}

	@Test
	public void getRandomInjuryChanceTest() {
		assertEquals(0.4, injury.getRandomInjuryChance(), 1);
		assertNotEquals(0.7, injury.getRandomInjuryChance());
	}

	@Test
	public void setRandomInjuryChanceTest() {
		assertEquals(0.4, injury.getRandomInjuryChance(), 1);
		injury.setRandomInjuryChance(0.7);
		assertEquals(0.7, injury.getRandomInjuryChance(), 1);
	}
	
	@Test
	public void getInjuryDaysLowTest() {
		assertEquals(10, injury.getInjuryDaysLow());
		assertNotEquals(100, injury.getInjuryDaysLow());
	}

	@Test
	public void setInjuryDaysLowTest() {
		assertEquals(10, injury.getInjuryDaysLow());
		injury.setInjuryDaysLow(20);
		assertEquals(20, injury.getInjuryDaysLow());
	}
	
	@Test
	public void getInjuryDaysHighTest() {
		assertEquals(260, injury.getInjuryDaysHigh());
		assertNotEquals(300, injury.getInjuryDaysHigh());
	}

	@Test
	public void setInjuryDaysHighTest() {
		assertEquals(260, injury.getInjuryDaysHigh());
		injury.setInjuryDaysHigh(300);
		assertEquals(300, injury.getInjuryDaysHigh());
	}
}
