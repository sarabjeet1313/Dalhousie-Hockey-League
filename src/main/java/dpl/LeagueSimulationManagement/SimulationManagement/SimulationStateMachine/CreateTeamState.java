package dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.NewsSystem.FreeAgencyPublisher;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.TeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class CreateTeamState implements IState {

	private IUserInput input;
	private IUserOutput output;
	private League initializedLeague;
	private ILeaguePersistance leagueDb;
	private IGameplayConfigPersistance configDb;
	private ICoachPersistance coachDb;
	private IManagerPersistance managerDb;
	private IStandingsPersistance standingDb;
	private ITradePersistence tradeDb;
	private Conference conferences;
	private Division divisions;
	private Team teams;
	private String conferenceName = "";
	private String divisionName = "";
	private String teamName = "";
	private Manager genManager;
	private Coach headCoach;
	private String stateName;
	private String nextStateName;
	private List<List<Player>> list;
	private List<Player> tempList1 = new ArrayList<Player>();
	private List<Integer> indexList = new ArrayList<Integer>();
	private List<Player> playersList = new ArrayList<Player>();
	List<Player> tempList2 = new ArrayList<Player>();
	private CustomValidation validate;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private ISimulationStateMachineAbstractFactory simulationStateMachineAbstractFactory;

	public CreateTeamState(IUserInput input, IUserOutput output, League league, ILeaguePersistance leagueDb,
			ICoachPersistance coachDb, IGameplayConfigPersistance configDb, IManagerPersistance managerDb,
			ITradePersistence tradeDb, IStandingsPersistance standingDb) {
		this.input = input;
		this.output = output;
		this.leagueDb = leagueDb;
		this.coachDb = coachDb;
		this.configDb = configDb;
		this.managerDb = managerDb;
		this.tradeDb = tradeDb;
		this.standingDb = standingDb;
		this.initializedLeague = league;
		this.stateName = "Create Team";
		this.conferences = teamManagement.ConferenceWithParameters("", null);
		this.divisions = teamManagement.DivisionWithParameters("", null);
		this.teams = teamManagement.TeamWithParameters("", genManager, headCoach, null, Boolean.FALSE);
		this.simulationStateMachineAbstractFactory = SystemConfig.getSingleInstance().getSimulationStateMachineAbstractFactory();
		this.validate = simulationStateMachineAbstractFactory.CustomValidation();
	}

	static {
		FreeAgencyPublisher.getInstance().subscribe(new NewsSubscriber());
	}

	public void nextState(StateContext context) {
		this.nextStateName = "Simulate";
		context.setState(this.simulationStateMachineAbstractFactory.SimulateState(input, output, teamName, initializedLeague, tradeDb, standingDb));
	}

	private void displayManagerList() {
		boolean validManager = false;
		String inputValue = "";
		List<Manager> tempManagerList = new ArrayList<Manager>();
		int managerId = -1;
		do {
			output.setOutput("Please select the General manager for " + teamName);
			output.sendOutput();
			output.setOutput("ManagerID | MANAGER NAME");
			output.sendOutput();
			List<Manager> gmList = initializedLeague.getManagerList();
			for (int index = 0; index < gmList.size(); index++) {
				output.setOutput(index + 1 + "	| " + gmList.get(index).getManagerName());
				output.sendOutput();
			}
			output.setOutput("Please enter the selected Manager ID");
			output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if (validate.isNumber(inputValue) == Boolean.FALSE) {
				validManager = false;
				output.setOutput("Manager ID cannot be string");
				output.sendOutput();
			} else {
				managerId = Integer.parseInt(inputValue) - 1;
			}
			if (managerId <= -1 || (managerId > gmList.size())) {
				validManager = false;
				output.setOutput("Please enter a valid Manager ID");
				output.sendOutput();
			} else {
				genManager = teamManagement.ManagerWithDbParameters(gmList.get(managerId).getManagerName(), managerDb);
				gmList.remove(managerId);
				for (int index = 0; index < gmList.size(); index++) {
					Manager manager = teamManagement.ManagerWithDbParameters(gmList.get(index).getManagerName(), managerDb);
					tempManagerList.add(manager);
				}
				initializedLeague.setManagerList(tempManagerList);
				validManager = true;
			}
		} while (validManager == false);
	}

	private void displayCoachesList() {
		boolean validCoach = false;
		String inputValue = "";
		List<Coach> tempCoachList = new ArrayList<Coach>();
		int headCoachNumber = -1;
		do {
			output.setOutput("Please select the Head Coach for " + teamName);
			output.sendOutput();
			List<Coach> cList = initializedLeague.getCoaches();
			output.setOutput("Coach ID |	COACH NAME     	| SKATING | SHOOTING | CHECKING | SAVING");
			output.sendOutput();
			for (int index = 0; index < cList.size(); index++) {
				output.setOutput(index + 1 + "  	| " + cList.get(index).getCoachName() + "    	| "
						+ cList.get(index).getSkating() + " |  " + cList.get(index).getShooting() + " |  "
						+ cList.get(index).getChecking() + " |  " + cList.get(index).getSaving());
				output.sendOutput();
			}
			output.setOutput("Please enter the selected coach ID");
			output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if (validate.isNumber(inputValue) == Boolean.FALSE) {
				validCoach = false;
				output.setOutput("Coach ID cannot be null");
				output.sendOutput();
				headCoachNumber = -1;
			} else {
				headCoachNumber = Integer.parseInt(inputValue) - 1;
			}
			if (headCoachNumber <= -1 || (headCoachNumber > cList.size())) {
				output.setOutput("Please enter a valid Coach ID");
				output.sendOutput();
				validCoach = false;
			} else {
				headCoach = cList.get(headCoachNumber);
				cList.remove(headCoachNumber);
				for (int index = 0; index < cList.size(); index++) {
					Coach coach = teamManagement.CoachWithDbParameters(cList.get(index).getCoachName(),
							cList.get(index).getSkating(), cList.get(index).getShooting(),
							cList.get(index).getChecking(), cList.get(index).getSaving(), coachDb);
				}
				initializedLeague.setCoaches(cList);
				validCoach = true;
			}
		} while (validCoach == false);
	}

	private void diaplayGoalieList(List<Player> pList) {
		String inputValue = "";
		int temp = -1;
		output.setOutput("PlayerID | PLAYER NAME    | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
		output.sendOutput();
		for (int index = 0; index < pList.size(); index++) {
			output.setOutput(
					index + 1 + "  	 | " + pList.get(index).getPlayerName() + "    	| 	" + pList.get(index).getAge()
							+ " | 	" + pList.get(index).getSkating() + " | 	" + pList.get(index).getShooting()
							+ " | 	" + pList.get(index).getChecking() + " | 	" + pList.get(index).getSaving());
			output.sendOutput();
		}
		for (int index = 0; index < 4; index++) {
			output.setOutput("Please enter selected Goalie ID");
			output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if (validate.isNumber(inputValue) == Boolean.FALSE) {
				output.setOutput("Goalie ID cannot be null");
				output.sendOutput();
				temp = -1;
			} else {
				temp = Integer.parseInt(inputValue) - 1;
			}
			if (temp > pList.size() - 1 || temp <= -1) {
				if (index == 0) {
					index = -1;
				} else {
					index = index - 1;
				}
				output.setOutput("Please enter a valid Goalie ID");
				output.sendOutput();
			} else if (indexList.contains(temp + 1)) {
				if (index == 0) {
					index = -1;
				} else {
					index = index - 1;
				}
				output.setOutput("Player Already added");
				output.sendOutput();
			} else {
				playersList.add(pList.get(temp));
				tempList1.add(pList.get(temp));
				indexList.add(temp + 1);
				FreeAgencyPublisher.getInstance().notify(pList.get(temp).getPlayerName(), "hired");
			}
		}
	}

	private void displayPlayersList(List<Player> dList, String playerType, int addIndex) {
		for (int index = 0; index < dList.size(); index++) {
			output.setOutput((index + 1 + addIndex) + "  	 | " + playerType + " |" + dList.get(index).getPlayerName()
					+ "   		 	 | " + dList.get(index).getAge() + " | 	 " + dList.get(index).getSkating()
					+ " | 	 " + dList.get(index).getShooting() + " |  	" + dList.get(index).getChecking() + " | 	 "
					+ dList.get(index).getSaving());
			output.sendOutput();
		}
	}

	private void displaySelectedPlayers(List<Player> fdList) {
		int temp = -1;
		int forwardPlayers = 16;
		int defencePlayers = 10;
		String inputValue = "";
		for (int index = 0; index < 26; index++) {
			output.setOutput("Please enter selected Player ID (Number of players selected: " + (index) + ")");
			output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if (validate.isNumber(inputValue) == Boolean.FALSE) {
				output.setOutput("Player ID cannot be null");
				output.sendOutput();
				temp = -1;
			} else {
				temp = Integer.parseInt(inputValue) - 1;
			}
			if (temp > (fdList.size() - 1) || temp <= -1) {
				if (index == 0) {
					index = -1;
				} else {
					index = index - 1;
				}
				output.setOutput("Please enter a valid Player ID");
				output.sendOutput();
			} else if (indexList.contains(temp + 1)) {
				if (index == 0) {
					index = -1;
				} else {
					index = index - 1;
				}
				output.setOutput("Player Already added");
				output.sendOutput();
			} else if( fdList.get(temp).getPosition().equals("forward") ){
				if(forwardPlayers < 1){
					if (index == 0) {
						index = -1;
					} else {
						index = index - 1;
					}
					output.setOutput("Max number of Forward Players (16) already Reached!! \n Choose Players with Type: Defence!!");
					output.sendOutput();
				}else {
					forwardPlayers--;
					playersList.add(fdList.get(temp));
					tempList2.add(fdList.get(temp));
					indexList.add(temp + 1);
					FreeAgencyPublisher.getInstance().notify(fdList.get(temp).getPlayerName(), "hired");
				}
			}else {
				if(defencePlayers < 1){
					if (index == 0) {
						index = -1;
					} else {
						index = index - 1;
					}
					output.setOutput("Max number of Defence Players (10) already Reached!! \n Choose Players with Type: Forward!!");
					output.sendOutput();
				}else {
					defencePlayers--;
					playersList.add(fdList.get(temp));
					tempList2.add(fdList.get(temp));
					indexList.add(temp + 1);
					FreeAgencyPublisher.getInstance().notify(fdList.get(temp).getPlayerName(), "hired");
				}
			}
		}

	}

	private void displayCaptain() {
		boolean validId = true;
		int temp = -1;
		String inputValue = "";
		output.setOutput("Please select captain for the team");
		output.sendOutput();
		output.setOutput("PLAYER ID | PLAYER NAME | POSITION");
		output.sendOutput();
		for (int index = 0; index < playersList.size(); index++) {
			output.setOutput((index + 1) + " 	| " + playersList.get(index).getPlayerName() + " 	| "
					+ playersList.get(index).getPosition());
			output.sendOutput();
		}
		while (validId) {
			output.setOutput("Please enter the selected captain Player ID");
			output.sendOutput();
			input.setInput();
			inputValue = input.getInput();
			if (validate.isNumber(inputValue) == Boolean.FALSE) {
				output.setOutput("Player ID cannot be null");
				output.sendOutput();
				temp = -1;
			} else {
				temp = Integer.parseInt(inputValue) - 1;
			}
			if (temp > (playersList.size() - 1) || temp <= -1) {
				output.setOutput("Please enter a valid Player ID");
				output.sendOutput();
			} else {
				playersList.get(temp).setCaptain(true);
				output.setOutput("Creating the Team ...");
				output.sendOutput();
				validId = false;
			}
		}
	}

	public void doProcessing() {
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
		} while (validConference == false);

		do {
			output.setOutput("Please enter existing Division name for the team");
			output.sendOutput();
			input.setInput();
			divisionName = input.getInput();
			validDivision = divisions.isValidDivisionName(conferenceName, divisionName, initializedLeague);
		} while (validDivision == false);

		do {
			output.setOutput("Please enter Team name that does not exist in League already.");
			output.sendOutput();
			input.setInput();
			teamName = input.getInput();
			if (teamName == "")
				continue;
			validTeam = teams.isValidTeamName(conferenceName, divisionName, teamName, initializedLeague);
		} while (validTeam);

		displayManagerList();
		displayCoachesList();

		output.setOutput("Please select 30 Roster Players for " + teamName);
		output.sendOutput();
		output.setOutput("Please select 4 Golies");
		output.sendOutput();
		list = initializedLeague.getAvailableLeaguePlayers(initializedLeague);
		pList = list.get(0);
		diaplayGoalieList(pList);
		indexList.clear();
		pList.removeAll(tempList1);
		output.setOutput("Please select 26 Skaters (16 forward and 10 defense)");
		output.sendOutput();
		fList = list.get(1);
		dList = list.get(2);
		output.setOutput("PlayerNo. | PLAYER TYPE |	PLAYER NAME	 | AGE  | SKATING | SHOOTING | CHECKING | SAVING");
		output.sendOutput();
		displayPlayersList(fList, "FORWORD", 0);
		displayPlayersList(dList, "DEFENCE", fList.size());
		fdList = new ArrayList<Player>();
		fdList.addAll(fList);
		fdList.addAll(dList);
		displaySelectedPlayers(fdList);
		fdList.removeAll(tempList2);
		fdList.addAll(pList);
		displayCaptain();
		initializedLeague.setFreeAgents(fdList);
		createTeamInLeague(conferenceName, divisionName, teamName, genManager, headCoach, playersList,
				initializedLeague);
	}

	public boolean createTeamInLeague(String conferenceName, String divisionName, String teamName, Manager genManager,
			Coach headCoach, List<Player> playerList, League initializedLeague) {
		boolean isCreated = Boolean.FALSE;
		try {
			List<Conference> conferenceList = initializedLeague.getConferenceList();
			for (int index = 0; index < conferenceList.size(); index++) {
				if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
					List<Division> divisionList = conferenceList.get(index).getDivisionList();
					for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
						if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
							List<Team> teamList = divisionList.get(dIndex).getTeamList();
							Team newTeam = teamManagement.TeamWithParameters(teamName, genManager, headCoach, playerList, Boolean.TRUE);
							teamList.add(newTeam);
							initializedLeague.getConferenceList().get(index).getDivisionList().get(dIndex)
									.setTeamList(teamList);
							break;
						}
					}
				}
			}
			isCreated = initializedLeague.createTeam(initializedLeague);
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} catch (IOException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
		return isCreated;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
