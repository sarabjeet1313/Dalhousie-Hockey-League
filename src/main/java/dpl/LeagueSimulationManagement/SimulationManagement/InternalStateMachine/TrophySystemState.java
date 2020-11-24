package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.Calendar;

public class TrophySystemState implements ISimulationState {
    private String stateName;
    private String nextStateName;
    private String currentDate;
    private IUserOutput output;
    private InternalStateContext context;
    private String endDate;
    private Calendar calendar;
    private SeasonCalendar utility;
    private League leagueToSimulate;
    private IStandingsPersistance standingsDb;
    private StandingInfo standings;
    private ISchedule schedule;
    private IInjuryManagement injury;
    private IRetirementManagement retirement;
    private int season;
    private IInternalStateMachineAbstractFactory internalStateMachineFactory;

    public TrophySystemState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings, IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context,
                             SeasonCalendar utility, String currentDate, String endDate, int season, IUserOutput output) {
        this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
        this.leagueToSimulate = leagueToSimulate;
        this.injury = injury;
        this.retirement = retirement;
        this.schedule = schedule;
        this.utility = utility;
        this.currentDate = currentDate;
        this.endDate = endDate;
        this.output = output;
        this.context = context;
        this.standings = standings;
        this.standingsDb = standingsDb;
        this.season = season;
        this.stateName = StateConstants.TROPHY_STATE;
    }

    public ISimulationState nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.PLAYER_DRAFT;
        return this.internalStateMachineFactory.PlayerDraftState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context,
                utility, currentDate, endDate, season, output);
    }


    public void doProcessing() {
        output.setOutput(StateConstants.TROPHY_STATE);
        output.sendOutput();
        // TODO
    }

    public boolean shouldContinue() {
        return true;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
