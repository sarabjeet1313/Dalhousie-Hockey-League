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
    private static Players players;
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
        boolean validManager = false;
        boolean validCoach = false;
        boolean validGoalie = false;
        boolean validDefender = false;
        boolean validAttacker = false;
        List<Integer> indexList = new ArrayList<Integer>();
        List<Players> playersList = new ArrayList<Players>();

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

        do {
            output.setOutput("Please select the General manager for "+ teamName);
            output.sendOutput();
            List<String> gmList = teams.getGeneralManagerList(initializedLeague);
            for(int index = 0; index < gmList.size() ; index++) {
            	output.setOutput(index+1 +"| "+ gmList.get(index));
            	output.sendOutput();
            }
            output.setOutput("Please enter the name of general manager");
            output.sendOutput();
            input.setInput();
            genManager = input.getInput();
            if(genManager == "") continue;
            validManager = teams.isValidGeneralManager(genManager, gmList);
        } while(validManager);
        
        do {
            output.setOutput("Please select the Head Coach for "+ teamName);
            output.sendOutput();
            List<String> cList = teams.getCoachList(initializedLeague);
            for(int index = 0; index < cList.size() ; index++) {
            	output.setOutput(index+1 +"| "+ cList.get(index));
            	output.sendOutput();
            }
            output.setOutput("Please enter the name of general manager");
            output.sendOutput();
            input.setInput();
            headCoach = input.getInput();
            if(headCoach == "") continue;
            validCoach = teams.isValidHeadCoach(headCoach, cList);
        } while(validCoach);
        
        output.setOutput("Please Roster Players for "+ teamName);
        output.sendOutput();
        List<List<Players>> list = players.getAvailablePlayersList(initializedLeague);
        do {
        	output.setOutput("Please select 2 Golies");
            output.sendOutput();
    		List<Players> pList = list.get(0);
    		output.setOutput("PlayerNo. |	PLAYER NAME     		| AGE  | SKATING | SHOOTING | CHECKING | SAVING");
            output.sendOutput();
    		for(int index = 0; index < pList.size(); index++) {
    			output.setOutput(index+1 +"  | "+ pList.get(index).getPlayerName()+"    	| "+pList.get(index).getAge()+" |  "+pList.get(index).getSkating()
    					+" |  "+pList.get(index).getShooting()+" |  "+pList.get(index).getChecking()+" |  "+pList.get(index).getSaving());
                output.sendOutput();
    		}
    		output.setOutput("Please enter selected player number");
            output.sendOutput();
    		for(int i = 0; i < 2; i++) {
    			input.setInput();
    			int temp = Integer.parseInt(input.getInput());
    			if(temp > pList.size()-1) {
    				i = (i==0) ? 0 :(i-1);
    			}
    			else {
    				playersList.add(pList.get(temp));
    				pList.remove(temp);
    			}
    		}
    		output.setOutput("Please select 18 Skaters (forward and defense)");
            output.sendOutput();
    		List<Players> fList = list.get(1);
    		List<Players> dList = list.get(2);
    		output.setOutput("PlayerNo. | PLAYER TYPE |	PLAYER NAME     		| AGE  | SKATING | SHOOTING | CHECKING | SAVING");
            output.sendOutput();
    		for(int index = 0; index < fList.size(); index++) {
    			output.setOutput(index+1 +"  | Forword "+ fList.get(index).getPlayerName()+"    	| "+fList.get(index).getAge()+" |  "+fList.get(index).getSkating()
    					+" |  "+fList.get(index).getShooting()+" |  "+fList.get(index).getChecking()+" |  "+fList.get(index).getSaving());
                output.sendOutput();
    		}
    		for(int index = 0; index < dList.size(); index++) {
    			output.setOutput(index+1 +"  | Defence "+ dList.get(index).getPlayerName()+"    	| "+dList.get(index).getAge()+" |  "+dList.get(index).getSkating()
    					+" |  "+dList.get(index).getShooting()+" |  "+dList.get(index).getChecking()+" |  "+dList.get(index).getSaving());
                output.sendOutput();
    		}
    		List<Players> fdList =new ArrayList<Players>();
    		fdList.addAll(fList);
    		fdList.addAll(dList);
    		for(int i = 0; i < 18; i++) {
    			input.setInput();
    			int temp = Integer.parseInt(input.getInput());
    			if(temp > (fdList.size()-2)) {
    				i = (i==0) ? 0 :(i-1);
    			}
    			else {
    				playersList.add(fdList.get(temp));
    				fdList.remove(temp);
    			}
    		}
		} while (playersList.size() != 20);
        
        createTeamInLeague(conferenceName, divisionName, teamName, genManager, headCoach, playersList, initializedLeague);
    }

    public boolean createTeamInLeague(String conferenceName, String divisionName, String teamName, String genManager, String headCoach,List<Players> playerList, Leagues initializedLeague) {
        List<Conferences> conferenceList =  initializedLeague.getConferenceList();
        for(int index = 0; index < conferenceList.size(); index++) {
            if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
                List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
                for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                    if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
                        List<Teams> teamList = divisionList.get(dIndex).getTeamList();
                        Teams newTeam = new Teams(teamName, genManager, headCoach, playerList);
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
