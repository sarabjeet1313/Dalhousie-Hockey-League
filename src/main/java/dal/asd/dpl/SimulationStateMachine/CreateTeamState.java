package dal.asd.dpl.SimulationStateMachine;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Players;
import dal.asd.dpl.TeamManagement.Teams;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static Leagues initializedLeague;
    private static ILeague leagueDb;
    private static Conferences conferences;
    private static Divisions divisions;
    private static Teams teams;
    private static String conferenceName = "";
    private static String divisionName = "";
    private static String teamName = "";
    private static String genManager = "";
    private static String headCoach = "";
    private static String stateName;
    private static String nextStateName;

    public CreateTeamState(IUserInput input, IUserOutput output, Leagues league, ILeague leagueDb) {
        this.input = input;
        this.output = output;
        this.leagueDb = leagueDb;
        this.initializedLeague = league;
        this.conferences = new Conferences("",null);
        this.divisions = new Divisions("",null);
        this.teams = new Teams("","","",null);
        this.stateName = "Create Team";
    }

    public void nextState(StateContext context){
        this.nextStateName = "Simulate";
        context.setState(new SimulateState(input, output, teamName));
    }

    public void doProcessing(){
        boolean validConference = false;
        boolean validDivision = false;
        boolean validTeam = true;

        output.setOutput("Welcome to the Create Team State. It's time to create and store the team.\n");
        output.sendOutput();

        do {
            output.setOutput("Please enter existing Conference name for the team");
            output.sendOutput();
            input.setInput();
            conferenceName = input.getInput();
            validConference = conferences.isValidConferenceName(conferenceName, initializedLeague);
        } while(validConference == false);

        do {
            output.setOutput("Please enter existing Division name for the team");
            output.sendOutput();
            input.setInput();
            divisionName = input.getInput();
            validDivision = divisions.isValidDivisionName(conferenceName, divisionName, initializedLeague);
        } while(validDivision == false);

        do {
            output.setOutput("Please enter Team name that does not exist in League already.");
            output.sendOutput();
            input.setInput();
            teamName = input.getInput();
            if(teamName == "") continue;
            validTeam = teams.isValidTeamName(conferenceName, divisionName, teamName, initializedLeague);
        } while(validTeam);

        output.setOutput("Please enter General Manager name for the team");
        output.sendOutput();
        input.setInput();
        genManager = input.getInput();
        output.setOutput("Please enter Head Coach name for the team");
        output.sendOutput();
        input.setInput();
        headCoach = input.getInput();
        createTeamInLeague(conferenceName, divisionName, teamName, genManager, headCoach, initializedLeague);
    }

    public boolean createTeamInLeague(String conferenceName, String divisionName, String teamName, String genManager, String headCoach, Leagues initializedLeague) {
        List<Conferences> conferenceList =  initializedLeague.getConferenceList();
        for(int index = 0; index < conferenceList.size(); index++) {
            if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
                List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
                for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                    if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
                        List<Teams> teamList = divisionList.get(dIndex).getTeamList();
                        List<Players> playersList = new ArrayList<Players>();
                        Teams newTeam = new Teams(teamName, genManager, headCoach, playersList);
                        teamList.add(newTeam);
                        break;
                    }
                }
                break;
            }
        }
        return initializedLeague.createTeam(initializedLeague, leagueDb);
    }

    public String getStateName(){
        return this.stateName;
    }

    public String getNextStateName(){
        return this.nextStateName;
    }
}
