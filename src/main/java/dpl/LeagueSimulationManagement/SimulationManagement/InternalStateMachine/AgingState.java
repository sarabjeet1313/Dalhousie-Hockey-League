package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private IInjuryManagement injury;
    private InternalStateContext context;
    private String currentDate;
    private String endDate;
    private int season;
    private SeasonCalendar seasonCalendar;
    private IUserOutput output;
    private RetirementManagement retirement;
    private ISchedule schedule;
    private StandingInfo standings;
    private IStandingsPersistance standingsDb;

    public AgingState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output) {
        this.stateName = StateConstants.AGING_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.injury = injury;
        this.context = context;
        this.seasonCalendar = seasonCalendar;
        this.retirement = new RetirementManagement();
        this.currentDate = currentDate;
        this.endDate = endDate;
        this.season = season;
        this.output = output;
        this.schedule = schedule;
        this.standingsDb = standingsDb;
        this.standings = new StandingInfo(leagueToSimulate, season, standingsDb);
    }

    public ISimulationState nextState(InternalStateContext context) {
        if (seasonCalendar.getSeasonOverStatus() | seasonCalendar.isLastDayOfSeason(currentDate)) {
            this.nextStateName = StateConstants.NEXT_SEASON_STATE;
            return new AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, injury, retirement, context, seasonCalendar, currentDate, endDate, season, output);
        } else {
            this.nextStateName = StateConstants.PERSIST_STATE;
            return new PersistState(leagueToSimulate, schedule, standingsDb, context, seasonCalendar, currentDate, endDate, season, output);
        }
    }

    public void doProcessing() {
        leagueToSimulate = injury.updatePlayerInjuryStatus(1, leagueToSimulate);
        output.setOutput(StateConstants.INSIDE_AGEING_STATE);
        output.sendOutput();
    }

    public boolean shouldContinue() {
        return true;
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