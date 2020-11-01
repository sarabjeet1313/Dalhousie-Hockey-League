package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.GameplayConfiguration.Injury;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.InjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private InjuryManagement injury;
    private InternalStateContext context;
    private String currentDate;
    private SeasonCalendar seasonCalendar;
    private IUserOutput output;

    public AgingState (League leagueToSimulate, InjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.AGING_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.injury = injury;
        this.context = context;
        this.seasonCalendar = seasonCalendar;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if(seasonCalendar.getSeasonOverStatus()) {
            this.nextStateName = StateConstants.NEXT_SEASON_STATE;
        }
        else {
            this.nextStateName = StateConstants.PERSIST_STATE;
        }
    }

    public void doProcessing() {
        leagueToSimulate = injury.updatePlayerInjuryStatus(leagueToSimulate);
        output.setOutput("Inside Aging state");
        output.sendOutput();
    }

    public League getUpdatedLeague() {
        return leagueToSimulate;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
