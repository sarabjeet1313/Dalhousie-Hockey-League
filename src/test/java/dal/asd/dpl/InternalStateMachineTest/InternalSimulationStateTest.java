package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.InternalSimulationState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.StandingsTest.StandingsMock;
import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.Trading.ITradePersistance;
import dal.asd.dpl.TradingTest.TradeObjectTestMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InternalSimulationStateTest {
    private InternalSimulationState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private LeagueMockData leagueMock;
    private SeasonCalendar utlity;
    private IStandingsPersistance standingMock;
    private ITradePersistance tradeMock;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueMock = new LeagueMockData();
        utlity = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        standingMock = new StandingsMockDb(0);
        tradeMock = new TradeObjectTestMockData();
        state = new InternalSimulationState(input, output,1,"testTeam", leagueMock.getTestData(), context, tradeMock, standingMock);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("End", context.currentStateName);
        context.setState(state);
        context.nextState();
        assertEquals("End", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("InternalSimulation", state.getStateName());
        assertNotEquals("End", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("InternalEndState", state.getNextStateName());
        state.nextState(context);
        assertEquals("InternalEndState", state.getNextStateName());
    }

}