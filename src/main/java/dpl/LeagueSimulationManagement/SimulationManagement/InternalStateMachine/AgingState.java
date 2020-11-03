package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private IInjuryManagement injury;
    private InternalStateContext context;
    private String currentDate;
    private SeasonCalendar seasonCalendar;
    private IUserOutput output;

    public AgingState(League leagueToSimulate, IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.AGING_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.injury = injury;
        this.context = context;
        this.seasonCalendar = seasonCalendar;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if (seasonCalendar.getSeasonOverStatus()) {
            this.nextStateName = StateConstants.NEXT_SEASON_STATE;
        } else {
            this.nextStateName = StateConstants.PERSIST_STATE;
        }
    }

    public void doProcessing() {
        leagueToSimulate = injury.updatePlayerInjuryStatus(1, leagueToSimulate);
        output.setOutput(StateConstants.INSIDE_AGEING_STATE);
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