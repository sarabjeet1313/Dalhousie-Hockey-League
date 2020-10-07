package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class InternalStartState implements ISimulationState {

    public static IUserInput input;
    public static IUserOutput output;

    public InternalStartState(IUserInput input, IUserOutput output){
        this.input = input;
        this.output = output;
    }

    public void nextState(InternalStateContext context){

    }

    public void doProcessing(){

    }

    public String getStateName(){

        return "";
    }

    public String getNextStateName(){

        return "";
    }
}
