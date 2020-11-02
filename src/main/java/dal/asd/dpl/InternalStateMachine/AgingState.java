package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.IInjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.StateConstants;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private IInjuryManagement injury;
    private InternalStateContext context;
    private String currentDate;
    private SeasonCalendar seasonCalendar;
    private IUserOutput output;

    public AgingState (League leagueToSimulate, IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
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
        leagueToSimulate = injury.updatePlayerInjuryStatus(1, leagueToSimulate);
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
