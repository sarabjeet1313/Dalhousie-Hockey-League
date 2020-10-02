package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class LoadTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;

    public LoadTeamState(IUserInput input, IUserOutput output) {
        this.input = input;
        this.output = output;
        doProcessing();
    }

    public void nextState(StateContext context){
        context.setState(new SimulateState(input, output));
    }

    public void doProcessing(){
        output.setOutput("Welcome to Simulate state :-) ");
        output.sendOutput();
        output.setOutput("Please enter the team name and we will load the complete League for you.");
        output.sendOutput();
        input.setInput();
        String TeamName = input.getInput();

        // TODO call Praneeth's function to pass the team name
    }
}
