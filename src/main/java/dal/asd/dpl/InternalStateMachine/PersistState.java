package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.StandingInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.ScheduleConstants;

public class PersistState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private StandingInfo standings;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private String lastDate;
    private IUserOutput output;

    public PersistState (League leagueToSimulate, ISchedule schedule, StandingInfo standings, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.PERSIST_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.standings = standings;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.lastDate = utility.getRegularSeasonLastDay();
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if(utility.getSeasonOverStatus()) {
            this.nextStateName = StateConstants.GENERATE_REGULAR_SEASON_SCHEDULE_STATE;
            return;
        }
        else {
            this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
        }
    }

    public void doProcessing() {

        output.setOutput("Inside persist state");
        output.sendOutput();
        if(schedule.getSeasonType() == ScheduleConstants.REGULAR_SEASON && currentDate.equals(schedule.getFirstDay())) {
            standings.initializeStandings();
        }
        else {
            standings.updateStandings();
            leagueToSimulate.UpdateLeague(leagueToSimulate);
        }
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
