package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.StateConstants;

public class InternalEndState implements ISimulationState{
    private static IUserOutput output;
    private static String stateName;
    private static String nextStateName;

    public InternalEndState(IUserOutput output){
        this.output = output;
        this.stateName = StateConstants.END_STATE;
    }

    public void nextState(InternalStateContext context){
        this.nextStateName = StateConstants.NO_STATE;
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
