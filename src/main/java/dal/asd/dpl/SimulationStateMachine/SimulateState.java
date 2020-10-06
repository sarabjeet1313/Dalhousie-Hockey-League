package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class SimulateState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static String teamName;

    public SimulateState(IUserInput input, IUserOutput output, String teamName) {
        this.input = input;
        this.output = output;
        this.teamName = teamName;
        doProcessing();
    }

    public void nextState(StateContext context){
        return;
    }

    public void doProcessing(){
        output.setOutput("Welcome to Simulate state :-) ");
        output.sendOutput();

        output.setOutput("How many seasons you want to simulate for " + teamName + " ?");
        output.sendOutput();

        input.setInput();
        int numOfSeasons = Integer.parseInt(input.getInput());

        for(int i=1; i <= numOfSeasons; i++){
            output.setOutput("Season " + i + " simulated for " + teamName + " ..." );
            output.sendOutput();
        }

    }
}
