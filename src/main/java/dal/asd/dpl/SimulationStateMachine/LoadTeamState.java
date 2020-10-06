package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;

public class LoadTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static String teamName;
    private static ILeague leagueDb;

    public LoadTeamState(IUserInput input, IUserOutput output, ILeague leagueDb) {
        this.input = input;
        this.output = output;
        this.leagueDb = leagueDb;
        doProcessing();
    }

    public void nextState(StateContext context){
        context.setState(new SimulateState(input, output, teamName));
    }

    public void doProcessing(){
        output.setOutput("Welcome to Simulate state :-) ");
        output.sendOutput();
        output.setOutput("Please enter the team name and we will simulate the complete League for you.");
        output.sendOutput();
        input.setInput();
        teamName = input.getInput();

        // TODO call Praneeth's function to pass the team name
        // team_name -> list<Strings>
    }
}
