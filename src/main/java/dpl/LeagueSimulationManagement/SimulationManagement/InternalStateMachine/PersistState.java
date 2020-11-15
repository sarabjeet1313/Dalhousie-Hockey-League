package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.sql.SQLException;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class PersistState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private ISchedule schedule;
	private StandingInfo standings;
	private IStandingsPersistance standingsDb;
	private InternalStateContext context;
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private int season;
	private String lastDate;
	private IUserOutput output;

	public PersistState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output) {
		this.stateName = StateConstants.PERSIST_STATE;
		this.leagueToSimulate = leagueToSimulate;
		this.schedule = schedule;
		this.standingsDb = standingsDb;
		this.standings = new StandingInfo(leagueToSimulate, season, standingsDb);
		this.context = context;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.lastDate = utility.getRegularSeasonLastDay();
		this.output = output;
	}

	public ISimulationState nextState(InternalStateContext context) {
		if (utility.getSeasonOverStatus()) {
			this.nextStateName = "SeasonEndState";
			return new EndOfSeasonState(output);
		} else {
			this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
			return new AdvanceTimeState(this.leagueToSimulate, this.schedule, this.utility, this.standingsDb,
					this.currentDate, this.endDate, output, context, this.season);
		}
	}

	public void doProcessing() {
		output.setOutput("Inside persist state");
		output.sendOutput();
		try {
			standings.updateStandings();
			leagueToSimulate.UpdateLeague(leagueToSimulate);
			// TODO Breej, replace it with the logic from leagueToSimulate itself.
//            if (tradeReset instanceof TradeReset) {
//                tradeReset.UpdateTrade();
//            }
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
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
