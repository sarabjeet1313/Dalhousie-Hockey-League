package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllStarGameState implements ISimulationState {
	
	private String stateName;
	private String nextStateName;
	private String currentDate;
	private IUserOutput output;
	private InternalStateContext context;
	private String endDate;
	private Calendar calendar;
	private SeasonCalendar utility;
	private League leagueToSimulate;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private ISchedule schedule;
	private Training training;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private static final Logger log = Logger.getLogger(AllStarGameState.class.getName());
	private ITeamManagementAbstractFactory teamManagementAbstractFactory;

	public AllStarGameState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility,
			String currentDate, String endDate, IUserOutput output, InternalStateContext context,
			IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.training = training;
		this.schedule = schedule;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.output = output;
		this.context = context;
		this.standings = standings;
		this.standingsDb = standingsDb;
		this.season = season;
		this.stateName = StateConstants.ALL_STAR_GAME_STATE;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.TRAINING_STATE;
		return this.internalStateMachineFactory.TrainingState(leagueToSimulate, training, schedule, utility,
				currentDate, endDate, output, context, standingsDb, standings, season);
	}

	public void doProcessing() {
		output.setOutput(StateConstants.ALL_STAR_GAME_STATE);
		output.sendOutput();
		List<Team> teamList = teamManagementAbstractFactory.AllStarGameManagement()
				.performAllStarGame(leagueToSimulate);
		Team firstTeam = teamList.get(0);
		Team secondTeam = teamList.get(1);
		output.setOutput("Match is going on between " + firstTeam.getTeamName() + " vs " + secondTeam.getTeamName());
		output.sendOutput();
		List<Map<Player, String>> playersByTeam = teamManagementAbstractFactory.AllStarGameManagement()
				.getPlayersBytTeam();
		output.setOutput(firstTeam.getTeamName() + " won the match");		
		output.sendOutput();
		for (Map.Entry<Player, String> entry : playersByTeam.get(0).entrySet()) {
			output.setOutput(entry.getKey().getPlayerName() + " -- " + entry.getValue());	
			output.sendOutput();
		}
		log.log(Level.INFO, StateConstants.ALL_STAR_GAME_STATE);
	}

	public boolean shouldContinue() {
		return true;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
	
}
