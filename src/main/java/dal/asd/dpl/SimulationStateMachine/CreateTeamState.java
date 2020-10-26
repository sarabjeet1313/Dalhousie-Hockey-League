package dal.asd.dpl.SimulationStateMachine;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamState implements IState {

    private static IUserInput input;
    private static IUserOutput output;
    private static League initializedLeague;
    private static ILeague leagueDb;
    private static Conference conferences;
    private static Division divisions;
    private static Team teams;
    private Player player;
    private static String conferenceName = "";
    private static String divisionName = "";
    private static String teamName = "";
    private static String genManager = "";
    private static Coach headCoach;
    private static String stateName;
    private static String nextStateName;
    private List<List<Player>> list;
    private List<Player> tempList1 =new ArrayList<Player>();
    private List<Integer> indexList = new ArrayList<Integer>();
    private List<Player> playersList = new ArrayList<Player>();
    List<Player> tempList2 =new ArrayList<Player>();
    
    

    public CreateTeamState(IUserInput input, IUserOutput output, League league, ILeague leagueDb) {
        CreateTeamState.input = input;
        CreateTeamState.output = output;
        CreateTeamState.leagueDb = leagueDb;
        CreateTeamState.initializedLeague = league;
        CreateTeamState.conferences = new Conference("",null);
        CreateTeamState.divisions = new Division("",null);
        CreateTeamState.teams = new Team("","", headCoach,null);
        CreateTeamState.stateName = "Create Team";
    }

    public void nextState(StateContext context){
        CreateTeamState.nextStateName = "Simulate";
        context.setState(new SimulateState(input, output, teamName, initializedLeague));
    }
    
    private void displayManagerList() {
    	boolean validManager = false;
    	String inputValue = "";
    	int managerId = 0;
    	do {
            output.setOutput("Please select the General manager for "+ teamName);
            output.sendOutput();
            output.setOutput("ManagerID | MANAGER NAME");
            output.sendOutput();
            List<String> gmList = initializedLeague.getGeneralManager();
            for(int index = 0; index < gmList.size() ; index++) {
            	output.setOutput(index+1 +"	| "+ gmList.get(index));
            	output.sendOutput();
            }
            output.setOutput("Please enter the selected Manager ID");
            output.sendOutput();
            input.setInput();
            inputValue = input.getInput();
            if(inputValue == null) {
            	validManager = false;
            	output.setOutput("Manager ID cannot be null");
                output.sendOutput();
            }
            else {
	            managerId = Integer.parseInt(inputValue)-1;
            }
            if(managerId <= -1 || (managerId > gmList.size())) {
            	validManager = false;
            	output.setOutput("Please enter a valid Manager ID");
            	output.sendOutput();
            }
            else {
            	genManager = gmList.get(managerId);
            	gmList.remove(managerId);
            	initializedLeague.setGeneralManager(gmList);
            	validManager = true;
            }
        } while(validManager == false);
    }
    
    private void displayCoachesList() {
        boolean validCoach = false;
        String inputValue = "";
        int headCoachNumber = 0;
    	do {
            output.setOutput("Please select the Head Coach for "+ teamName);
            output.sendOutput();
            List<Coach> cList = initializedLeague.getCoaches();
    		output.setOutput("Coach ID |	COACH NAME     	| SKATING | SHOOTING | CHECKING | SAVING");
            output.sendOutput();
            for(int index = 0; index < cList.size() ; index++) {
            	output.setOutput(index+1 +"  | "+ cList.get(index).getCoachName()+"    	| "+cList.get(index).getSkating()
    					+" |  "+cList.get(index).getShooting()+" |  "+cList.get(index).getChecking()+" |  "+cList.get(index).getSaving());
            	output.sendOutput();
            }
            output.setOutput("Please enter the selected coach ID");
            output.sendOutput();
            input.setInput();
            inputValue = input.getInput();
            if(inputValue == null) {
            	validCoach = false;
            	output.setOutput("Coach ID cannot be null");
                output.sendOutput();
                headCoachNumber = -1;
            }
            else {
	            headCoachNumber = Integer.parseInt(inputValue)-1;
            }
	        if(headCoachNumber <= -1 || (headCoachNumber > cList.size())) {
	        	output.setOutput("Please enter a valid Coach ID");
	        	output.sendOutput();
	        	validCoach = false;
	        }
	        else {
	        	headCoach = cList.get(headCoachNumber);
	        	cList.remove(headCoachNumber);
	        	initializedLeague.setCoaches(cList);
	        	validCoach = true;
	        }
        } while(validCoach == false);
    }
    
    private void diaplayGoalieList(List<Player> pList) {
    	String inputValue = "";
    	int temp = 0;
    	output.setOutput("PlayerID | PLAYER NAME    | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
        output.sendOutput();
		for(int index = 0; index < pList.size(); index++) {
			output.setOutput(index+1 +"  	 | "+ pList.get(index).getPlayerName()+"    	| 	"+pList.get(index).getAge()+" | 	"+pList.get(index).getSkating()
					+" | 	"+pList.get(index).getShooting()+" | 	"+pList.get(index).getChecking()+" | 	"+pList.get(index).getSaving());
            output.sendOutput();
		}
		for(int index = 0; index < 2; index++) {
			output.setOutput("Please enter selected Goalie ID");
            output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
            if(inputValue == null) {
            	output.setOutput("Goalie ID cannot be null");
                output.sendOutput();
                temp = -1;
            }
            else {
            	temp = Integer.parseInt(inputValue)-1;
            }
			if(temp > pList.size()-1 || temp <= -1) {
				if(index == 0) {
					index = -1;
				}
				else {
					index = index-1;
				}
				output.setOutput("Please enter a valid Goalie ID");
	            output.sendOutput();
			}
			else if(indexList.contains(temp+1)) {
				if(index == 0) {
					index = -1;
				}
				else {
					index = index - 1;
				}
				output.setOutput("Player Already added");
	            output.sendOutput();
			}
			else {
				playersList.add(pList.get(temp));
				tempList1.add(pList.get(temp));
				indexList.add(temp+1);
			}
		}
    }
    
    private void displayPlayersList(List<Player> dList, String playerType, int addIndex) {
    	for(int index = 0; index < dList.size(); index++) {
			output.setOutput((index+1+addIndex) +"  	 | "+playerType+" |"+ dList.get(index).getPlayerName()+"   		 	 | "+dList.get(index).getAge()+" | 	 "+dList.get(index).getSkating()
					+" | 	 "+dList.get(index).getShooting()+" |  	"+dList.get(index).getChecking()+" | 	 "+dList.get(index).getSaving());
            output.sendOutput();
		}
    }
    
    private void displaySelectedPlayers(List<Player> fdList) {
    	int temp = 0;
    	String inputValue = "";
    	for(int index = 0; index < 18; index++) {
			output.setOutput("Please enter selected Player ID (Number of players selected: "+(index)+")");
            output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if(inputValue == null) {
            	output.setOutput("Goalie ID cannot be null");
                output.sendOutput();
                temp = -1;
            }
            else {
            	temp = Integer.parseInt(inputValue)-1;
            }
			if(temp > (fdList.size()-1) || temp <= -1) {
				if(index == 0) {
					index = -1;
				}
				else {
					index = index - 1;
				}
				output.setOutput("Please enter a valid Player ID");
	            output.sendOutput();
			}
			else if(indexList.contains(temp+1)) {
				if(index == 0) {
					index = -1;
				}
				else {
					index = index - 1;
				}
				output.setOutput("Player Already added");
	            output.sendOutput();
			}
			else {
				playersList.add(fdList.get(temp));
				tempList2.add(fdList.get(temp));
				indexList.add(temp+1);
			}
		}
    }
    
    private void displayCaptain() {
    	boolean validId = true;
    	int temp = 0;
    	String inputValue = "";
    	output.setOutput("Please select captain for the team");
        output.sendOutput();
        output.setOutput("PLAYER ID | PLAYER NAME | POSITION");
        output.sendOutput();
        for(int index = 0; index < playersList.size(); index++) {
        	output.setOutput((index+1)+" 	| "+ playersList.get(index).getPlayerName()+" 	| "+playersList.get(index).getPosition());
            output.sendOutput();
        }
        while(validId) {
            output.setOutput("Please enter the selected captain Player ID");
            output.sendOutput();
            input.setInput();
            inputValue = input.getInput();
			if(inputValue == null) {
            	output.setOutput("Player ID cannot be null");
                output.sendOutput();
                temp = -1;
            }
            else {
            	temp = Integer.parseInt(inputValue)-1;
            }
			if(temp > (playersList.size()-1) || temp <= -1) {
				output.setOutput("Please enter a valid Player ID");
	            output.sendOutput();
			}
			else {
				playersList.get(temp).setCaptain(true);
				validId = false;
			}
        }
    }

    public void doProcessing(){
        boolean validConference = false;
        boolean validDivision = false;
        boolean validTeam = true;
        List<Player> pList;
        List<Player> fList;
        List<Player> dList;
        List<Player> fdList;

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
        
        displayManagerList();
        displayCoachesList();
        
        output.setOutput("Please select 20 Roster Players for "+ teamName);
        output.sendOutput();
        output.setOutput("Please select 2 Golies");
        output.sendOutput();
        list = teams.getAvailablePlayersList(initializedLeague);
        pList = list.get(0);
        diaplayGoalieList(pList);
        indexList.clear();
        pList.removeAll(tempList1);
        output.setOutput("Please select 18 Skaters (forward and defense)");
        output.sendOutput();
        fList = list.get(1);
        dList = list.get(2);
        output.setOutput("PlayerNo. | PLAYER TYPE |	PLAYER NAME	 | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
        output.sendOutput();
        displayPlayersList(fList, "FORWORD", 0);
        displayPlayersList(dList, "DEFENCE", fList.size());
        fdList =new ArrayList<Player>();
        fdList.addAll(fList);
        fdList.addAll(dList);
        displaySelectedPlayers(fdList);
        fdList.removeAll(tempList2);
        fdList.addAll(pList);
        displayCaptain();
        initializedLeague.setFreeAgents(fdList);
        createTeamInLeague(conferenceName, divisionName, teamName, genManager, headCoach, playersList, initializedLeague);
    }
    
    public boolean createTeamInLeague(String conferenceName, String divisionName, String teamName, String genManager, Coach headCoach,List<Player> playerList, League initializedLeague) {
        List<Conference> conferenceList =  initializedLeague.getConferenceList();
        for(int index = 0; index < conferenceList.size(); index++) {
            if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
                List<Division> divisionList = conferenceList.get(index).getDivisionList();
                for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                    if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
                        List<Team> teamList = divisionList.get(dIndex).getTeamList();
                        Team newTeam = new Team(teamName, genManager, headCoach, playerList);
                        teamList.add(newTeam);
                        initializedLeague.getConferenceList().get(index).getDivisionList().get(dIndex).setTeamList(teamList);
                        break;
                    }
                }
            }
        }
        return initializedLeague.createTeam(initializedLeague, leagueDb);
    }

    public String getStateName(){
        return CreateTeamState.stateName;
    }

    public String getNextStateName(){
        return CreateTeamState.nextStateName;
    }
}
