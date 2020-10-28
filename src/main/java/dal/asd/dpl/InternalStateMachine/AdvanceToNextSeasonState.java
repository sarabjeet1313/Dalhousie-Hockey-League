package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdvanceToNextSeasonState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private boolean cupWinnerDeclared;
    private Leagues leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private IUserOutput output;

    public AdvanceToNextSeasonState (Leagues leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "NextSeason";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "Persist";
    }

    public void doProcessing() {

        int days = (int)daysLapsed();
        // TODO call methods to perform aging.
    }

    private long daysLapsed() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String startDate = utility.getRegularSeasonStartDay();
        String endDate = utility.getLastSeasonDay();
        try {
            Date date1 = myFormat.parse(startDate);
            Date date2 = myFormat.parse(endDate);
            long difference = date2.getTime() - date1.getTime();
            long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
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
