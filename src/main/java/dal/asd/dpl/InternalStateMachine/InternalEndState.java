package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalEndState implements ISimulationState{
    private static IUserOutput output;
    private static String stateName;
    private static String nextStateName;


    public InternalEndState(IUserInput input, IUserOutput output){
        this.output = output;
        this.stateName = "End";
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = "None";
        return;
    }

    public void doProcessing(){
        output.setOutput("Thanks for using the DHL's Dynasty mode. Please come back soon.");
        output.sendOutput();
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
