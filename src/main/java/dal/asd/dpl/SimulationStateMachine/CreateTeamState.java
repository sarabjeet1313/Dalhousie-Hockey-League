package dal.asd.dpl.SimulationStateMachine;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Player;
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
    private  Player player;
    private static String conferenceName = "";
    private static String divisionName = "";
    private static String teamName = "";
    private static String genManager = "";
    private static Coach headCoach;
    private static String stateName;
    private static String nextStateName;
    

    public CreateTeamState(IUserInput input, IUserOutput output, Leagues league, ILeague leagueDb) {
        CreateTeamState.input = input;
        CreateTeamState.output = output;
        CreateTeamState.leagueDb = leagueDb;
        CreateTeamState.initializedLeague = league;
        CreateTeamState.conferences = new Conferences("",null);
        CreateTeamState.divisions = new Divisions("",null);
        CreateTeamState.teams = new Teams("","", headCoach,null);
        CreateTeamState.stateName = "Create Team";
    }

    public void nextState(StateContext context){
        CreateTeamState.nextStateName = "Simulate";
        context.setState(new SimulateState(input, output, teamName, initializedLeague));
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
        int headCoachNumber = 0;
        int managerId = 0;
        
        List<Integer> indexList = new ArrayList<Integer>();
        List<Player> playersList = new ArrayList<Player>();

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
            output.setOutput("ManagerID | MANAGER NAME");
            output.sendOutput();
            List<String> gmList = initializedLeague.getGeneralManager();
            for(int index = 0; index < gmList.size() ; index++) {
            	output.setOutput(index+1 +"	| "+ gmList.get(index));
            	output.sendOutput();
            }
            output.setOutput("Please enter the selected general manager ID");
            output.sendOutput();
            input.setInput();
            managerId = Integer.parseInt(input.getInput())-1;
            if(managerId <= -1 || (managerId > gmList.size())) {
            	validManager = false;
            }
            else {
            	genManager = gmList.get(managerId);
            	gmList.remove(managerId);
            	initializedLeague.setGeneralManager(gmList);
            	validManager = true;
            }
        } while(validManager == false);
        
        
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
            headCoachNumber = Integer.parseInt(input.getInput())-1;
            if(headCoachNumber <= -1 || (headCoachNumber > cList.size())) {
            	validCoach = false;
            }
            else {
            	headCoach = cList.get(headCoachNumber);
            	cList.remove(headCoachNumber);
            	initializedLeague.setCoaches(cList);
            	validCoach = true;
            }
        } while(validCoach == false);
        
        output.setOutput("Please select 20 Roster Players for "+ teamName);
        output.sendOutput();
        do {
        	output.setOutput("Please select 2 Golies");
            output.sendOutput();
            List<List<Player>> list = teams.getAvailablePlayersList(initializedLeague);
            List<Player> tempList1 =new ArrayList<Player>();
    		List<Player> pList = list.get(0);
    		output.setOutput("PlayerID | PLAYER NAME    | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
            output.sendOutput();
    		for(int index = 0; index < pList.size(); index++) {
    			output.setOutput(index+1 +"  	 | "+ pList.get(index).getPlayerName()+"    	| 	"+pList.get(index).getAge()+" | 	"+pList.get(index).getSkating()
    					+" | 	"+pList.get(index).getShooting()+" | 	"+pList.get(index).getChecking()+" | 	"+pList.get(index).getSaving());
                output.sendOutput();
    		}
    		for(int i = 0; i < 2; i++) {
    			output.setOutput("Please enter selected Goalie ID");
                output.sendOutput();
    			input.setInput();
    			int temp = Integer.parseInt(input.getInput())-1;
    			if(temp > pList.size()-1 || temp <= -1) {
    				i = (i==0) ? -1 :(i-1);
    				output.setOutput("Please enter a valid Goalie ID");
    	            output.sendOutput();
    			}
    			else if(indexList.contains(temp+1)) {
    				i = (i==0) ? -1 :(i-1);
    				output.setOutput("Player Already added");
    	            output.sendOutput();
    			}
    			else {
    				playersList.add(pList.get(temp));
    				tempList1.add(pList.get(temp));
    			}
    		}
    		
    		pList.removeAll(tempList1);
    		output.setOutput("Please select 18 Skaters (forward and defense)");
            output.sendOutput();
    		List<Player> fList = list.get(1);
    		List<Player> dList = list.get(2);
    		output.setOutput("PlayerNo. | PLAYER TYPE |	PLAYER NAME	 | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
            output.sendOutput();
    		for(int index = 0; index < fList.size(); index++) {
    			output.setOutput(index+1 +"  	 | FORWARD |"+ fList.get(index).getPlayerName()+"   		 	 | "+fList.get(index).getAge()+" | 	 "+fList.get(index).getSkating()
    					+" | 	 "+fList.get(index).getShooting()+" |  	"+fList.get(index).getChecking()+" | 	 "+fList.get(index).getSaving());
                output.sendOutput();
    		}
    		for(int index = 0; index < dList.size(); index++) {
    			output.setOutput((index+1+fList.size()) +"  	 | DEFENSE |"+ dList.get(index).getPlayerName()+"   		 	 | "+dList.get(index).getAge()+" | 	 "+dList.get(index).getSkating()
    					+" | 	 "+dList.get(index).getShooting()+" |  	"+dList.get(index).getChecking()+" | 	 "+dList.get(index).getSaving());
                output.sendOutput();
    		}
    		List<Player> fdList =new ArrayList<Player>();
    		List<Player> tempList2 =new ArrayList<Player>();
    		fdList.addAll(fList);
    		fdList.addAll(dList);
    		for(int i = 0; i < 18; i++) {
    			output.setOutput("Please enter selected Player ID (Number of players selected: "+(i)+")");
                output.sendOutput();
    			input.setInput();
    			int temp = Integer.parseInt(input.getInput())-1;
    			if(temp > (fdList.size()-1) || temp <= -1) {
    				i = (i==0) ? -1 :(i-1);
    				output.setOutput("Please enter a valid Player ID");
    	            output.sendOutput();
    			}
    			else if(indexList.contains(temp+1)) {
    				i = (i==0) ? -1 :(i-1);
    				output.setOutput("Player Already added");
    	            output.sendOutput();
    			}
    			else {
    				playersList.add(fdList.get(temp));
    				tempList2.add(fdList.get(temp));
    			}
    		}
    		fdList.removeAll(tempList2);
    		fdList.addAll(pList);
    		initializedLeague.setFreeAgents(fdList);
    		output.setOutput("Please select captain for the team");
            output.sendOutput();
            output.setOutput("PLAYER ID | PLAYER NAME | POSITION");
            output.sendOutput();
            for(int i = 0; i < playersList.size(); i++) {
            	output.setOutput((i+1)+" 	| "+ playersList.get(i).getPlayerName()+" 	| "+playersList.get(i).getPlayerPosition());
                output.sendOutput();
            }
            boolean validId = true;
            while(validId) {
	            output.setOutput("Please enter the selected captain Player ID");
	            output.sendOutput();
	            input.setInput();
				int temp = Integer.parseInt(input.getInput())-1;
				if(temp > (playersList.size()-1) || temp <= -1) {
					output.setOutput("Please enter a valid Player ID");
		            output.sendOutput();
				}
				else {
					playersList.get(temp).setCaptain(true);
					validId = false;
				}
            }
		} while (playersList.size() != 20);
        
        createTeamInLeague(conferenceName, divisionName, teamName, genManager, headCoach, playersList, initializedLeague);
    }

    public boolean createTeamInLeague(String conferenceName, String divisionName, String teamName, String genManager, Coach headCoach,List<Player> playerList, Leagues initializedLeague) {
        List<Conferences> conferenceList =  initializedLeague.getConferenceList();
        for(int index = 0; index < conferenceList.size(); index++) {
            if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
                List<Divisions> divisionList = conferenceList.get(index).getDivisionList();
                for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                    if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
                        List<Teams> teamList = divisionList.get(dIndex).getTeamList();
                        Teams newTeam = new Teams(teamName, genManager, headCoach, playerList);
                        teamList.add(newTeam);
                        initializedLeague.getConferenceList().get(index).getDivisionList().get(dIndex).setTeamList(teamList);
                        break;
                    }
                }
                break;
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
