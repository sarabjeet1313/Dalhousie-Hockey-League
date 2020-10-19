package dal.asd.dpl.SimulationStateMachine;
import dal.asd.dpl.InternalStateMachine.InternalStartState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class SimulateState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static String teamName;
    private static String stateName;
    private static String nextStateName;
    private static Leagues leagueToSimulate;

    public SimulateState(IUserInput input, IUserOutput output, String teamName, Leagues leagueToSimulate) {
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        this.stateName = "Simulate";
        this.leagueToSimulate = leagueToSimulate;
    }

    public void nextState(StateContext context){
        this.nextStateName = "None";
        return;
    }

    public void doProcessing(){
        output.setOutput("Welcome to Simulation state :-) ");
        output.sendOutput();
        InternalStateContext stateContext = new InternalStateContext(input, output);
        stateContext.setState(new InternalStartState(input, output, teamName, leagueToSimulate));
        stateContext.doProcessing();
        stateContext.nextState(); // simulation state
        stateContext.doProcessing();
        stateContext.nextState(); // end state
        stateContext.doProcessing();
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
