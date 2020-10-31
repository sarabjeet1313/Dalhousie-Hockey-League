package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.List;
import java.util.Map;

public class InjuryCheckState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private IUserOutput output;

    public InjuryCheckState (League leagueToSimulate, ISchedule schedule, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;
        this.stateName = "Injury";
    }

    public void nextState(InternalStateContext context) {
        if(anyUnplayedGames()) {
            this.nextStateName = "SimulateGame";
        }
        else {
            if (utility.isTradeDeadlinePending(this.currentDate)) {
                this.nextStateName = "Trading";
            }
            else {
                this.nextStateName = "Aging";
            }
        }
    }

    public void doProcessing() {
        // TODO injury check ...
        output.setOutput("Inside Injury Check state");
        output.sendOutput();
    }

    private boolean anyUnplayedGames() {
        Map< String, List<Map<String, String>>> finalSchedule = schedule.getFinalSchedule();
        if(finalSchedule.containsKey(this.currentDate)) {
            if (finalSchedule.get(this.currentDate).size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
