package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Players;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.List;

public class LoadTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static String teamName;
    private static ILeague leagueDb;
    private static String stateName;
    private static String nextStateName;

    public LoadTeamState(IUserInput input, IUserOutput output, ILeague leagueDb) {
        this.input = input;
        this.output = output;
        this.leagueDb = leagueDb;
        this.stateName = "Load Team";
    }

    public void nextState(StateContext context){
        this.nextStateName = "Simulate";
        context.setState(new SimulateState(input, output, teamName));
    }

    public void doProcessing(){
        output.setOutput("Welcome to Load Team state :-) ");
        output.sendOutput();
        output.setOutput("Please enter the team name and we will load the complete League for you.");
        output.sendOutput();
        input.setInput();
        teamName = input.getInput();
        List<Conferences> conferencesList = null;
        List<Players> freeAgents = null;
        boolean result = false;
        String finalLeagueName = "";
        Leagues league = new Leagues("test", conferencesList, freeAgents);

        List<String> leagues = league.getLeagueNames(teamName, leagueDb);

        if(leagues.size() == 1) {
            result = league.loadLeagueData(leagues.get(0));
        }
        else if(leagues.size() > 1) {
            output.setOutput("We have found multiple leagues for the given team. Which one do you like to load?");
            output.sendOutput();

            for(int i=0; i < leagues.size(); i++){
                output.setOutput(i+1 + ": " + leagues.get(i));
                output.sendOutput();
            }

            input.setInput();
            finalLeagueName = input.getInput();

            if(!finalLeagueName.isEmpty()) {
                result = league.loadLeagueData(finalLeagueName);
            }
        }
        else {
            output.setOutput("No League found for the team.");
            output.sendOutput();
            doProcessing();
        }

        if(result){
            output.setOutput("League has been initialized for the team: "+ teamName);
            output.sendOutput();
        }
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
