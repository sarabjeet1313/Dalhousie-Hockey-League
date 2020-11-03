package dpl.InternalStateMachine;

import java.sql.SQLException;

import dpl.DplConstants.StateConstants;
import dpl.Schedule.ISchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.Standings.StandingInfo;
import dpl.TeamManagement.League;
import dpl.Trading.TradeReset;
import dpl.UserOutput.IUserOutput;

public class PersistState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private StandingInfo standings;
    private TradeReset tradeReset;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private String lastDate;
    private IUserOutput output;

    public PersistState(League leagueToSimulate, ISchedule schedule, StandingInfo standings, TradeReset tradeReset, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.PERSIST_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.standings = standings;
        this.tradeReset = tradeReset;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.lastDate = utility.getRegularSeasonLastDay();
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if (utility.getSeasonOverStatus()) {
            this.nextStateName = StateConstants.GENERATE_REGULAR_SEASON_SCHEDULE_STATE;
            return;
        } else {
            this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
        }
    }

    public void doProcessing() {
        output.setOutput("Inside persist state");
        output.sendOutput();
        try {
        	standings.updateStandings();
            leagueToSimulate.UpdateLeague(leagueToSimulate);
            if (tradeReset instanceof TradeReset) {
                tradeReset.UpdateTrade();
            }
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
	        output.sendOutput();
		}
        
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
