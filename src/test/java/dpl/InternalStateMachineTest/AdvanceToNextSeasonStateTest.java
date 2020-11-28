package dpl.InternalStateMachineTest;

import dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.AdvanceToNextSeasonState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
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
        leagueMock = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        injury = teamManagement.InjuryManagement();
        retirement = teamManagement.RetirementManagement();
        leagueToSimulate = new LeagueMockData().getTestData();
        utility = new SeasonCalendar(0, output);
        leagueToSimulate.setLeagueDb(leagueMock);
        utility.setLastSeasonDay("20-11-2020");
        context = new InternalStateContext(input, output);
        state = new AdvanceToNextSeasonState(leagueToSimulate, null, null, null, injury, retirement, context, utility, "13-11-2020", "", 0, output);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() throws RetirementManagementException {
        leagueToSimulate.setLeagueDb(leagueMock);
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeagueTest() throws RetirementManagementException {
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