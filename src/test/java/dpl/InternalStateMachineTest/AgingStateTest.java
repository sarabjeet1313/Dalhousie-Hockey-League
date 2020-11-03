package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgingStateTest {

    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserOutput output;
    private IUserInput input;
    private AgingState state;
    private League leagueToSimulate;
    private InjuryManagement injury;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        injury = new InjuryManagement();
        leagueToSimulate = new LeagueMockData().getTestData();
        state = new AgingState(leagueToSimulate, injury, context, utility, "13-11-2020", output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Persist", state.getNextStateName());
        assertNotEquals("Aging", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeague() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Aging", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertNotEquals("NextSeason", state.getNextStateName());
        assertEquals("Persist", state.getNextStateName());

        utility.setSeasonOverStatus(true);
        state.nextState(context);
        assertNotEquals("Persist", state.getNextStateName());
        assertEquals("NextSeason", state.getNextStateName());
    }
}