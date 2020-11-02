package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.*;
import dal.asd.dpl.util.ScheduleConstants;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdvanceToNextSeasonState implements ISimulationState {
    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private IInjuryManagement injury;
    private IRetirementManagement retirement;
    private InternalStateContext context;
    private SeasonCalendar seasonCalendar;
    private String currentDate;
    private IUserOutput output;

    public AdvanceToNextSeasonState (League leagueToSimulate, IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.NEXT_SEASON_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.injury = injury;
        this.retirement = retirement;
        this.context = context;
        this.seasonCalendar = seasonCalendar;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.PERSIST_STATE;
    }

    public void doProcessing() {
        // no of days between the very first day of next season and the day when cup winner declared.
        int days = (int)daysLapsed();
        leagueToSimulate = retirement.increaseAge(days, leagueToSimulate);
        leagueToSimulate = injury.updatePlayerInjuryStatus(days, leagueToSimulate);
    }

    public League getUpdatedLeague() {
        return leagueToSimulate;
    }

    private long daysLapsed() {
        SimpleDateFormat myFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);
        String startDate = seasonCalendar.getLastSeasonDay();
        String endDate = seasonCalendar.getNextRegularSeasonStartDay();
        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long difference = date2.getTime() - date1.getTime();
            long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            return days;
        } catch (ParseException e) {
            output.setOutput("Exception finding days lapsed during advancing to next season");
            output.sendOutput();
        }
        return 0;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
