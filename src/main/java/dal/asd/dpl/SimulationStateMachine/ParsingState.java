package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.Leagues;

public class ParsingState implements IState {

    private static IUserOutput output;
    private static IUserInput input;
    private static String filePath;
    private static ILeague leagueDb;
    private static Leagues initializedLeague;

    public ParsingState(IUserInput input, IUserOutput output, String filePath, ILeague leagueDb) {
        this.input = input;
        this.output = output;
        this.filePath = filePath;
        this.leagueDb = leagueDb;

        doProcessing();
    }

    public void nextState(StateContext context){
        context.setState(new CreateTeamState(input, output, initializedLeague, leagueDb));
    }

    public void doProcessing(){
        output.setOutput("Welcome to the Parsing State. It's time to parse the JSON and initialize your league.");
        output.sendOutput();

        InitializeLeagues leagueModel = new InitializeLeagues(filePath, leagueDb, output, input);
        initializedLeague = leagueModel.parseAndInitializeModels();

        if(initializedLeague == null){
            System.exit(0);
        }
    }
}
