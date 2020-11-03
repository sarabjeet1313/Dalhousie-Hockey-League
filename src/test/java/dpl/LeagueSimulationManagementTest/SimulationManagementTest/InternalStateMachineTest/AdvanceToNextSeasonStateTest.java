package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;

import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.AdvanceToNextSeasonState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.RetirementManagement;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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
    private InjuryManagement injury;
    private RetirementManagement retirement;
    private ILeaguePersistance leagueMock;

    @Before
    public void setUp() throws Exception {
        leagueMock = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        injury = new InjuryManagement();
        retirement = new RetirementManagement();
        leagueToSimulate = new LeagueMockData().getTestData();
        utility = new SeasonCalendar(0, output);
        leagueToSimulate.setLeagueDb(leagueMock);
        utility.setLastSeasonDay("20-11-2020");
        context = new InternalStateContext(input, output);
        state = new AdvanceToNextSeasonState(leagueToSimulate, injury, retirement, context, utility, "13-11-2020", output);
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
}