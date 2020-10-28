package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.Calendar;

public class GeneratePlayoffScheduleState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private String startDate;
    private String endDate;
    private String currentDate;
    private Leagues leagueToSimulate;
    private IUserOutput output;
    private ScheduleUtlity utility;
    private Standings standings;
    private InternalStateContext context;
    private ISchedule schedule;

    public GeneratePlayoffScheduleState(Leagues leagueToSimulate, ScheduleUtlity utility, Standings standings, String currentDate, IUserOutput output, InternalStateContext context) {
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
        context.setState(new TrainingState(leagueToSimulate, schedule, utility, currentDate, output, context));
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
