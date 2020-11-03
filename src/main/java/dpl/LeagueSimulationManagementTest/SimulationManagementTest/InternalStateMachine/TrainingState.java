package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.ISchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

public class TrainingState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private String currentDate;
    private IUserOutput output;
    private InternalStateContext context;
    private ISchedule schedule;
    private SeasonCalendar utility;
    private Training training;

    public TrainingState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility, String currentDate, IUserOutput output, InternalStateContext context) {
        this.leagueToSimulate = leagueToSimulate;
        this.output = output;
        this.context = context;
        this.schedule = schedule;
        this.currentDate = currentDate;
        this.utility = utility;
        this.stateName = "Training";
        this.training = training;
    }

    public void nextState(InternalStateContext context) {
        if (schedule.anyUnplayedGame(currentDate)) {
            this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
        } else {
            if (utility.isTradeDeadlinePending(this.currentDate)) {
                this.nextStateName = StateConstants.TRADING_STATE;
            } else {
                this.nextStateName = StateConstants.AGING_STATE;
            }
        }
    }

    public void doProcessing() {
        leagueToSimulate = training.trackDaysForTraining(leagueToSimulate);
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
