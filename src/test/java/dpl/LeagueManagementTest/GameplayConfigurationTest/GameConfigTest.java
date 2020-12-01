package dpl.LeagueManagementTest.GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class GameConfigTest {
	private League leagueToSimulate;
	private GameplayConfig config;
	private IGameplayConfigPersistance configMock;
	private GameplayConfig tempConfig;
	private IUserOutput output;

	@Before
	public void setUp() throws Exception {
		leagueToSimulate = new LeagueMockData().getTestData();
		config = leagueToSimulate.getGameConfig();
		configMock = new GameplayConfigMockData();
		output = new CmdUserOutput();
		tempConfig = new GameplayConfig(config.getAging(), config.getInjury(), config.getTraining(),
				config.getTrading(), configMock);
	}

	@Test
	public void parameterizedConstructorTest() {
		assertEquals(35, config.getAging().getAverageRetirementAge());
		assertEquals(50, config.getAging().getMaximumAge());
		assertEquals(0.05, config.getAging().getStatDecayChance(), 1);
		assertEquals(0.05, config.getInjury().getRandomInjuryChance(), 1);
		assertEquals(1, config.getInjury().getInjuryDaysLow());
		assertEquals(260, config.getInjury().getInjuryDaysHigh());
		assertEquals(8, config.getTrading().getLossPoint());
		assertEquals(0.05, config.getTrading().getRandomTradeOfferChance(), 1);
		assertEquals(2, config.getTrading().getMaxPlayersPerTrade());
		assertEquals(0.05, config.getTrading().getRandomAcceptanceChance(), 1);
		assertEquals(100, config.getTraining().getDaysUntilStatIncreaseCheck());
		assertEquals(100, config.getTraining().getTrackDays());
	}

	@Test
	public void getAgingTest() {
		assertEquals(35, config.getAging().getAverageRetirementAge());
		assertEquals(50, config.getAging().getMaximumAge());
		assertEquals(0.05, config.getAging().getStatDecayChance(), 1);
	}

	@Test
	public void setAgingTest() {
		config.getAging().setAverageRetirementAge(30);
		assertEquals(30, config.getAging().getAverageRetirementAge());
		config.getAging().setMaximumAge(45);
		assertEquals(45, config.getAging().getMaximumAge());
		config.getAging().setStatDecayChance(0.5);
		assertEquals(0.5, config.getAging().getStatDecayChance(), 1);
	}

	@Test
	public void getInjuryTest() {
		assertEquals(0.05, config.getInjury().getRandomInjuryChance(), 1);
		assertEquals(1, config.getInjury().getInjuryDaysLow());
		assertEquals(260, config.getInjury().getInjuryDaysHigh());
	}

	@Test
	public void setInjuryTest() {
		config.getInjury().setRandomInjuryChance(0.5);
		assertEquals(0.5, config.getInjury().getRandomInjuryChance(), 1);
		config.getInjury().setInjuryDaysLow(10);
		assertEquals(10, config.getInjury().getInjuryDaysLow());
		config.getInjury().setInjuryDaysHigh(200);
		assertEquals(200, config.getInjury().getInjuryDaysHigh());
	}

	@Test
	public void getTradingTest() {
		assertEquals(8, config.getTrading().getLossPoint());
		assertEquals(0.05, config.getTrading().getRandomTradeOfferChance(), 1);
		assertEquals(2, config.getTrading().getMaxPlayersPerTrade());
		assertEquals(0.05, config.getTrading().getRandomAcceptanceChance(), 1);
	}

	@Test
	public void setTradingTest() {
		config.getTrading().setLossPoint(10);
		assertEquals(10, config.getTrading().getLossPoint());
		config.getTrading().setRandomTradeOfferChance(0.5);
		assertEquals(0.5, config.getTrading().getRandomTradeOfferChance(), 1);
		config.getTrading().setMaxPlayersPerTrade(1);
		assertEquals(1, config.getTrading().getMaxPlayersPerTrade());
		config.getTrading().setRandomAcceptanceChance(0.5);
		assertEquals(0.5, config.getTrading().getRandomAcceptanceChance(), 1);
	}

	@Test
	public void getTrainingTest() {
		assertEquals(100, config.getTraining().getDaysUntilStatIncreaseCheck());
		assertEquals(100, config.getTraining().getTrackDays());
	}

	@Test
	public void setTrainingTest() {
		config.getTraining().setDaysUntilStatIncreaseCheck(150);
		assertEquals(150, config.getTraining().getDaysUntilStatIncreaseCheck());
		config.getTraining().setTrackDays(150);
		assertEquals(150, config.getTraining().getTrackDays());
	}

	@Test
	public void getPenaltyChanceTest() {
		assertEquals(0.45, config.getPenaltyChance(), 1);
		assertNotEquals(0.5, config.getPenaltyChance());
	}

	@Test
	public void getShootingValueTest() {
		assertEquals(4.9, config.getShootingValue(), 1);
		assertNotEquals(0.5, config.getShootingValue());
	}

	@Test
	public void getCheckingValueTest() {
		assertEquals(10, config.getCheckingValue());
		assertNotEquals(100, config.getCheckingValue());
	}

	@Test
	public void saveGameplayConfigTest() {
		leagueToSimulate.setGameConfig(tempConfig);
		try {
			assertTrue(tempConfig.saveGameplayConfig(leagueToSimulate));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}
	
	@Test
	public void loadGameplayConfigTest() {
		leagueToSimulate.setGameConfig(tempConfig);
		try {
			GameplayConfig temp = tempConfig.loadGameplayConfig(leagueToSimulate);
			assertTrue(temp instanceof GameplayConfig);
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}
