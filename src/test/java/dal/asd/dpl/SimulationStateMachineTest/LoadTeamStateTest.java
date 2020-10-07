package dal.asd.dpl.SimulationStateMachineTest;

import dal.asd.dpl.SimulationStateMachine.InitialState;
import dal.asd.dpl.SimulationStateMachine.LoadTeamState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.LeagueMockData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class LoadTeamStateTest {

    private static LoadTeamState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static ILeague leagueMock;

    @BeforeAll
    public static void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueMock = new LeagueMockData();
        state = new LoadTeamState(input, output, leagueMock);
        context = new StateContext(input, output);
    }

  //  @Test
//    public void nextStateTest() {
//        context.nextState();
//        assertEquals("Simulate", state.getNextStateName());
//        context.setState(state);
//    }

    @Test
    public void doProcessingTest() {
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Load Team", state.getStateName());
    }

 //   @Test
//    public void getNextStateNameTest() {
//        context.nextState();
//        assertEquals("Simulate", state.getNextStateName());
//        context.setState(state);
//    }
}