package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.SimulationManagement.InternalStateMachine.AdvanceToNextSeasonState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvanceToNextSeasonStateTest {

    private IUserInput input;
    private IUserOutput output;
    private SeasonCalendar utility;
    private InternalStateContext context;
    private AdvanceToNextSeasonState state;
    private League leagueToSimulate;
    private IInjuryManagement injury;
    private IRetirementManagement retirement;
    private ILeaguePersistance leagueMock;
    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

    @Before
    public void setUp() throws Exception {
        leagueMock = LeagueMockData.getInstance();
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        injury = teamManagement.InjuryManagement();
        retirement = teamManagement.RetirementManagement();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        leagueToSimulate.setLeagueDb(leagueMock);
        utility.setLastSeasonDay("20-11-2020");
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        state = (AdvanceToNextSeasonState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().AdvanceToNextSeasonState(leagueToSimulate, null, null, null, injury, retirement, context, utility, "13-11-2020", "", 0, output);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        leagueToSimulate.setLeagueDb(leagueMock);
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeagueTest() {
        leagueToSimulate.setLeagueDb(leagueMock);
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("NextSeason", state.getStateName());
        assertNotEquals("Training", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}