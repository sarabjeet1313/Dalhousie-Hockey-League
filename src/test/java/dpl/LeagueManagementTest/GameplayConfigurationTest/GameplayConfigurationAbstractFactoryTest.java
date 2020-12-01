package dpl.LeagueManagementTest.GameplayConfigurationTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.GameplayConfiguration.Trading;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class GameplayConfigurationAbstractFactoryTest {

	private GameplayConfigurationAbstractFactory factory;
	private League leagueToSimulate;
	
	@Before
	public void setUp() {
		factory = new GameplayConfigurationAbstractFactory();
		leagueToSimulate = new LeagueMockData().getTestData();
	}
	
	@Test
	public void AgingTest() {
		Aging aging = factory.Aging(35, 50, 0.05);
		assertTrue(aging instanceof Aging);
	}
	
	@Test
	public void InjuryTest() {
		Injury injury = factory.Injury(0.05, 1, 260);
		assertTrue(injury instanceof Injury);
	}
	
	@Test
	public void TradingTest() {
		Trading trading = factory.Trading();
		assertTrue(trading instanceof Trading);
	}
	
	@Test
	public void TrainingTest() {
		Training training = factory.Training(100, 100);
		assertTrue(training instanceof Training);
	}
}
