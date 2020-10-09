package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStartState implements ISimulationState {
    private static IUserInput input;
    private static IUserOutput output;
    private static String teamName;
    public int numOfSeasons;
    private static String stateName;
    private static String nextStateName;

    public InternalStartState(IUserInput input, IUserOutput output, String teamName){
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        this.stateName = "Start";
        this.numOfSeasons = 0;
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = "Simulate";
        context.setState(new InternalSimulationState(input, output, numOfSeasons, teamName));
    }

    public void doProcessing(){
        output.setOutput("How many seasons you want to simulate for " + teamName + " ?");
        output.sendOutput();
        input.setInput();
        this.numOfSeasons = Integer.parseInt(input.getInput());
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
