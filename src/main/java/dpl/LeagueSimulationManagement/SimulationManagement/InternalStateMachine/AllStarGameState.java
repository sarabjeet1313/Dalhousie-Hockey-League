package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.util.Calendar;
import java.util.List;

import dpl.SystemConfig;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.NewsSystem.GamePlayedPublisher;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
		String winningTeam = "", losingTeam = "";

		output.setOutput(StateConstants.ALL_STAR_GAME_STATE);
		output.sendOutput();
		List<Team> teamList = teamManagementAbstractFactory.AllStarGameManagement()
				.performAllStarGame(leagueToSimulate);
		Team firstTeam = teamList.get(0);
		Team secondTeam = teamList.get(1);
		output.setOutput("Match is going on between " + firstTeam.getTeamName() + " vs " + secondTeam.getTeamName());
		output.sendOutput();

		this.leagueToSimulate.getConferenceList().get(0).getDivisionList().get(0).getTeamList().add(firstTeam);
		this.leagueToSimulate.getConferenceList().get(0).getDivisionList().get(0).getTeamList().add(secondTeam);

		SimulateGameState SimulateGameState = new SimulateGameState(leagueToSimulate, standings);

		SimulateGameState.simulateMatch(firstTeam.getTeamName(), secondTeam.getTeamName());
		
		if(null == SimulateGameState.getTeamGoals().get(firstTeam.getTeamName()) || null == SimulateGameState.getTeamGoals().get(firstTeam.getTeamName())) {
			output.setOutput("Match is drawn");
			output.sendOutput();
			return;
		}

		if (SimulateGameState.getTeamGoals().get(firstTeam.getTeamName()) >= SimulateGameState.getTeamGoals()
				.get(secondTeam.getTeamName())) {
			winningTeam = firstTeam.getTeamName();
			losingTeam = secondTeam.getTeamName();
		} else {
			winningTeam = secondTeam.getTeamName();
			losingTeam = firstTeam.getTeamName();
		}

		GamePlayedPublisher.getInstance().notify(winningTeam, losingTeam, "");
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
