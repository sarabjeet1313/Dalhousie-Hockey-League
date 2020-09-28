package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class ParsingState implements IState {

    private static IUserOutput output;
    private static IUserInput input;
    private static String filePath;

    public ParsingState(IUserInput input, IUserOutput output, String filePath) {
        this.input = input;
        this.output = output;
        this.filePath = filePath;
        doProcessing();
    }

    public void nextState(StateContext context){
        context.setState(new CreateTeamState(input, output));
    }

    public void doProcessing(){

        output.setOutput("Welcome to the Parsing State. It's time to parse the JSON and initialize the models");
        output.sendOutput();

        InitializeLeagues leagueModel = new InitializeLeagues(filePath);
    }
}
