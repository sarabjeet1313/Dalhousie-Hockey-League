package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.InternalStateMachine.AllStarGameState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import static org.junit.Assert.*;

public class AllStarGameStateTest {

	private AllStarGameState state;
	private League leagueToSimulate;
	private IUserInput input;
	private IUserOutput output;
	private ISchedule schedule;
	private Calendar calendar;
	private StandingInfo standings;
	private InternalStateContext context;
	private SeasonCalendar utility;
	private IStandingsPersistance standingsDb;
	private ILeaguePersistance league;

	@Before
	public void setUp() throws Exception {
		league = LeagueMockData.getInstance();
		leagueToSimulate = LeagueMockData.getInstance().getTestData();
		input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
		output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
		calendar = Calendar.getInstance();
		standingsDb = StandingsMockDb.getInstance();
		schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
		standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
		context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
		utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
		state = (AllStarGameState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().AllStarGameState(leagueToSimulate, null, schedule, utility, null, null, output, context,
				standingsDb, standings, 0);
	}

	@Test
	public void nextStateTest() {
		state.nextState(context);
		assertEquals("Training", state.getNextStateName());
		assertNotEquals("Negative", state.getNextStateName());
	}

	@Test
	public void doProcessingTest() {
		leagueToSimulate.setLeagueDb(league);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		state.doProcessing();
		String expected = "AllStartGameState";
		String gotOutput = out.toString().replaceAll("\n", "");
		gotOutput = gotOutput.replaceAll("\r", "");
		assertNotEquals("Inside Negative State", gotOutput);
	}

	@Test
	public void getStateNameTest() {
		assertEquals("AllStartGameState", state.getStateName());
		assertNotEquals("Negative", state.getStateName());
	}

	@Test
	public void getNextStateNameTest() {
		assertNotEquals("AdvanceTime", state.getNextStateName());
		state.nextState(context);
		assertEquals("Training", state.getNextStateName());
	}

	@Test
	public void shouldContinueTest() {
		assertTrue(state.shouldContinue());
		assertFalse(!state.shouldContinue());
	}
}