package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;
import java.util.Calendar;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private String startDate;
    private String endDate;
    private String year;
    private Calendar seasonCalendar;
    private Leagues leagueToSimulate;
    private int currentYear;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private ISchedule schedule;
    private ScheduleUtlity utility;

    public GenerateRegularSeasonScheduleState(Leagues leagueToSimulate, IUserInput input, IUserOutput output, int season, InternalStateContext context) {
        this.stateName = "GenerateRegularSeasonSchedule";
        this.leagueToSimulate = leagueToSimulate;
        this.seasonCalendar = Calendar.getInstance();
        this.schedule = new RegularSeasonScheduleState(seasonCalendar, output);
        this.utility = new ScheduleUtlity(season);
        this.input = input;
        this.output = output;
        this.context = context;

        this.startDate = utility.getRegularSeasonStartDay();
        schedule.setCurrentDay(this.startDate);

        schedule.setFirstDay(utility.getRegularSeasonFirstDay());

        this.endDate = utility.getRegularSeasonLastDay();
        schedule.setLastDay(this.endDate);
        utility.setLastSeasonDay(this.endDate);
    }


    public void nextState(InternalStateContext context) {
        this.nextStateName = "AdvanceTime";
        context.setState(new AdvanceTimeState(this.schedule, this.leagueToSimulate, this.startDate, this.endDate, this.utility, this.output, context));

    }

    public void doProcessing() {
        output.setOutput("Scheduling the regular season for simulation.");
        output.sendOutput();

        if(leagueToSimulate == null) {
            output.setOutput("Error scheduling season, passed league object is null. Please check");
            output.sendOutput();
            return;
        }

        schedule.generateSchedule(leagueToSimulate);
        output.setOutput("Regular season has been scheduled successfully.");
        output.sendOutput();

        schedule.setCurrentDay(utility.getRegularSeasonStartDay());
       // nextState(this.context);
    }

    public String getRegularSeasonEndDate() {
        return this.endDate;
    }

    public String getRegularSeasonStartDate() {
        return this.startDate;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

    //=======
    public ISchedule getSchedule() {
        return schedule;
    }



}
