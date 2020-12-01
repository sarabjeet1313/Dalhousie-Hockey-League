package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.ScheduleConstants;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.NewsSystem.GamePlayedPublisher;
import dpl.NewsSystem.INewsSystemAbstractFactory;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulateGameState implements ISimulationState {

	private int fixedShotsPerTeam = 30;
	private static String stateName;
	private static String nextStateName;
	private League leagueToSimulate;
	private StandingInfo standings;
	private IStandingsPersistance standingsDb;
	private ISchedule schedule;
	private InternalStateContext context;
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private String winningTeam;
	private String losingTeam;
	private int season;
	private IUserOutput output;
	private ITeamInfo teamInfo;
	private double penaltyChance;
	private int checkingValueToPenalty;
	private double shootingValueToGoal;
	private IInjuryManagement injury;
	private Map<String, List<Map<String, String>>> currentSchedule;
	private List<Player> firstTeamForwards;
	private List<Player> firstTeamForwardsOnIce;
	private List<Player> firstTeamDefenseMen;
	private List<Player> firstTeamDefenseMenOnIce;
	private List<Player> firstTeamGoalies;
	private List<Player> firstTeamGoaliesOnIce;
	private List<Player> secondTeamForwards;
	private List<Player> secondTeamForwardsOnIce;
	private List<Player> secondTeamDefenseMen;
	private List<Player> secondTeamDefenseMenOnIce;
	private List<Player> secondTeamGoalies;
	private List<Player> secondTeamGoaliesOnIce;
	private int firstTeamSkatingTotal;
	private int secondTeamSkatingTotal;
	private int firstTeamShotsCounter;
	private int secondTeamShotsCounter;
	private GameContext gameContext;
	private ISimulateMatch simulateMatch;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITeamManagementAbstractFactory teamManagementAbstractFactory;
	private INewsSystemAbstractFactory newsSystemAbstractFactory;
	private static final Logger log = Logger.getLogger(SimulateGameState.class.getName());

	public SimulateGameState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
			StandingInfo standings, InternalStateContext context, SeasonCalendar utility, String currentDate,
			String endDate, int season, IUserOutput output) {
		this.stateName = StateConstants.SIMULATE_GAME_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.schedule = schedule;
		this.context = context;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.currentSchedule = schedule.getFinalSchedule();
		this.injury = teamManagementAbstractFactory.InjuryManagement();
		this.penaltyChance = leagueToSimulate.getGameConfig().getPenaltyChance();
		this.checkingValueToPenalty = leagueToSimulate.getGameConfig().getCheckingValue();
		this.shootingValueToGoal = leagueToSimulate.getGameConfig().getShootingValue();
		this.output = output;
		this.teamInfo = teamManagementAbstractFactory.Team();
		this.firstTeamForwards = new ArrayList<>();
		this.firstTeamForwardsOnIce = new ArrayList<>();
		this.secondTeamForwards = new ArrayList<>();
		this.secondTeamForwardsOnIce = new ArrayList<>();
		this.firstTeamDefenseMen = new ArrayList<>();
		this.firstTeamDefenseMenOnIce = new ArrayList<>();
		this.secondTeamDefenseMen = new ArrayList<>();
		this.secondTeamDefenseMenOnIce = new ArrayList<>();
		this.firstTeamGoalies = new ArrayList<>();
		this.firstTeamGoaliesOnIce = new ArrayList<>();
		this.secondTeamGoalies = new ArrayList<>();
		this.secondTeamGoaliesOnIce = new ArrayList<>();
		this.firstTeamSkatingTotal = 0;
		this.secondTeamSkatingTotal = 0;
		this.firstTeamShotsCounter = 0;
		this.secondTeamShotsCounter = 0;
	}

	static {
		GamePlayedPublisher.getInstance().subscribe(SystemConfig.getSingleInstance().getNewsSystemAbstractFactory().NewsSubscriber());
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.INJURY_STATE;
		return this.internalStateMachineFactory.InjuryCheckState(leagueToSimulate, injury, schedule, context, utility,
				currentDate, endDate, season, output, standingsDb, standings);
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.GAME_SIMULATION_ENTRY);
		if (schedule.getSeasonType() == ScheduleConstants.REGULAR_SEASON) {
			this.simulateMatch = internalStateMachineFactory.SimulateRegularSeasonMatch(currentDate, schedule, output,
					leagueToSimulate, standings);
			this.gameContext = internalStateMachineFactory.GameContext(this.simulateMatch);
			this.gameContext.simulateMatch();
		} else if (schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON) {
			this.simulateMatch = internalStateMachineFactory.SimulatePlayoffSeasonMatch(currentDate, schedule, output,
					leagueToSimulate, standings, utility);
			this.gameContext = internalStateMachineFactory.GameContext(this.simulateMatch);
			this.gameContext.simulateMatch();
		}
		output.setOutput(StateConstants.GAME_SIMULATION_ENTRY);
		output.sendOutput();
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
