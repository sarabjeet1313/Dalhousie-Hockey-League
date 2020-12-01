package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.*;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InternalSimulationState implements ISimulationState {

	private IUserOutput output;
	private int season;
	private int totalSeasons;
	private String teamName;
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private InternalStateContext context;
	private ISimulationState initialState;
	private SeasonCalendar utility;
	private String currentDate;
	private ISchedule schedule;
	private StandingInfo standingsInfo;
	private IStandingsPersistance standingsDb;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private IScheduleAbstractFactory scheduleAbstractFactory;
	private IStandingsAbstractFactory standingsAbstractFactory;
	private static final Logger log = Logger.getLogger(InternalSimulationState.class.getName());

	public InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName,
								   League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
								   IStandingsPersistance standingsDb) {

		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.standingsAbstractFactory = SystemConfig.getSingleInstance().getStandingsAbstractFactory();
		this.scheduleAbstractFactory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
		this.output = output;
		this.totalSeasons = seasons;
		this.teamName = teamName;
		this.leagueToSimulate = leagueToSimulate;
		this.stateName = StateConstants.INTERNAL_SIMULATION_STATE;
		this.season = 0;
		this.context = context;
		this.currentDate = "";
		this.standingsDb = standingsDb;
	}



	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.INTERNAL_END_STATE;
		ISimulationState endState = this.internalStateMachineFactory.InternalEndState(output);
		context.setState(endState);
		return endState;
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.GAME_SIMULATION);
		for (int index = 1; index <= totalSeasons; index++) {
			this.season = index - 1;
			output.setOutput("Season " + index + " is simulating.");
			output.sendOutput();

			standingsDb.setSeason(index);
			standingsInfo = this.standingsAbstractFactory.StandingInfo(leagueToSimulate, season, standingsDb, output);
			utility = this.scheduleAbstractFactory.SeasonCalendar(season, output);
			initialState = this.internalStateMachineFactory.GenerateRegularSeasonScheduleState(leagueToSimulate, this.output, this.season, this.context, standingsDb, standingsInfo, utility);
			context.setState(initialState);

			while (context.shouldContinue()) {
				context.doProcessing();
				ISimulationState state = context.nextState();
				context.setState(state);
			}
			output.setOutput("Season " + index + " winner is : " + utility.getSeasonWinner());
			output.sendOutput();
			output.setOutput("\nSeason stats after Playoffs : \n");
			output.sendOutput();
			standingsInfo.showStats();
		}
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
