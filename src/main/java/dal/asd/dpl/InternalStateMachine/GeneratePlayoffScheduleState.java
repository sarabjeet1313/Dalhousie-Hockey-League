package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

public class GeneratePlayoffScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private String startDate;
    private String endDate;
    private String currentDate;
    private League leagueToSimulate;
    private IUserOutput output;
    private ScheduleUtlity utility;
    private StandingInfo standings;
    private InternalStateContext context;
    private ISchedule schedule;

    public GeneratePlayoffScheduleState(League leagueToSimulate, ScheduleUtlity utility, StandingInfo standings, String currentDate, IUserOutput output, InternalStateContext context) {
        this.stateName = "GeneratePlayoffSchedule";
        this.nextStateName = "Training";
        this.output = output;
        this.utility = utility;
        this.standings = standings;
        this.currentDate = currentDate;
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = new PlayoffScheduleState(output, standings);
        this.context = context;
        this.startDate = this.utility.getPlayoffFirstDay();
        schedule.setFirstDay(startDate);
        schedule.setCurrentDay(startDate);
        this.endDate = this.utility.getPlayoffLastDay();
        schedule.setLastDay(endDate);
        utility.setLastSeasonDay(this.endDate);
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "Training";
    }

    public void doProcessing() {

        output.setOutput("Scheduling the playoff season round-1 for simulation.");
        output.sendOutput();

        if(leagueToSimulate == null) {
            output.setOutput("Error scheduling season, passed league object is null. Please check");
            output.sendOutput();
            return;
        }

        schedule.generateSchedule(leagueToSimulate);
        output.setOutput("Playoff season has been scheduled successfully.");
        output.sendOutput();

        schedule.setCurrentDay(this.startDate);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

    public ISchedule getSchedule() {
        return this.schedule;
    }
}
