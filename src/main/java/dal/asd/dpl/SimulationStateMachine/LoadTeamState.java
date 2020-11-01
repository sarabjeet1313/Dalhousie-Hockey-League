package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class LoadTeamState implements IState {
    private IUserInput input;
    private IUserOutput output;
    private String teamName;
    private ILeaguePersistance leagueDb;
    private String stateName;
    private String nextStateName;

    public LoadTeamState(IUserInput input, IUserOutput output, ILeaguePersistance leagueDb) {
        this.input = input;
        this.output = output;
        this.leagueDb = leagueDb;
        this.stateName = "Load Team";
    }

    public void nextState(StateContext context){
    	this.nextStateName = "Simulate";
        context.setState(new SimulateState(input, output, teamName, null));
    }

    public void doProcessing(){
        output.setOutput("Welcome to Load Team state :-) ");
        output.sendOutput();
        output.setOutput("Please enter the team name and we will load the complete League for you.");
        output.sendOutput();
        input.setInput();
        teamName = input.getInput();
        List<Conference> conferencesList = null;
        List<Player> freeAgents = null;
        List<Coach> coaches = null;
        List<Manager> managers = new ArrayList<Manager>(); 
        GameplayConfig config = null;
        boolean result = false;
        String finalLeagueName = "";
        League league = new League("test", conferencesList, freeAgents, coaches, managers, config, leagueDb);
        league = league.loadLeague(teamName);

        if(!league.getLeagueName().equals("test")){
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
