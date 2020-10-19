package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.Calendar;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private static String startDate;
    private static String endDate;
    private static Calendar seasonCalendar;
    private static Leagues leagueToSimulate;
    private static int currentYear;
    private static IUserInput input;
    private static IUserOutput output;

    public GenerateRegularSeasonScheduleState(Leagues leagueToSimulate, IUserInput input, IUserOutput output) {
        this.stateName = "GenerateRegularSeasonSchedule";
        this.leagueToSimulate = leagueToSimulate;
        this.seasonCalendar = Calendar.getInstance();
        this.input = input;
        this.output = output;

        // getting current year
        this.currentYear = this.seasonCalendar.get(Calendar.YEAR);
        String currentYear = String.valueOf(this.currentYear);

        // setting simulation start date to 30th September every year.
        this.startDate = "30-09-" + currentYear;

        // setting final day of regular season
        this.endDate = getFinalDayOfSeason() + "-04-" + String.valueOf(this.currentYear+1);

    }

    @Override
    public void nextState(InternalStateContext context) {
        context.currentDate = this.startDate;
        this.nextStateName = "AdvanceTime";
        context.setState(new AdvanceTimeState(seasonCalendar, this.startDate, this.endDate, this.input, this.output));

    }

    public void doProcessing() {
        // TODO - once we finalize the scheduling algo
    }

    public String getFinalDayOfSeason() {
        seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
        seasonCalendar.set(Calendar.YEAR, this.currentYear + 1);
        return String.valueOf(seasonCalendar.get(Calendar.DATE));
    }

    public String getRegularSeasonEndDate() {
        return this.endDate;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
