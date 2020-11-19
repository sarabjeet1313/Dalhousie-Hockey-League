package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.*;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.ScheduleTest.MockSchedule;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InternalStateMachineAbstractFactoryTest {

    private InternalStateMachineAbstractFactory factory;
    private League leagueToSimulate;
    private ISchedule schedule;
    private SeasonCalendar utility;
    private IStandingsPersistance standingsDb;
    private StandingInfo standings;
    private IUserOutput output;
    private IUserInput input;
    private InternalStateContext context;

    @Before
    public void setUp() throws Exception {
        factory = new InternalStateMachineAbstractFactory();
        leagueToSimulate = new LeagueMockData().getTestData();
        output = new CmdUserOutput();
        input = new CmdUserInput();
        standingsDb = new StandingsMockDb(0);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        schedule = new MockSchedule();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
    }

    @Test
    public void advanceTimeStateTest() {
        ISimulationState state = factory.AdvanceTimeState(leagueToSimulate, schedule, utility, standingsDb, standings,"13-11-2020", "02-04-2021", output, context,0);
        assertTrue(state instanceof AdvanceTimeState);
    }

    @Test
    public void advanceToNextSeasonState() {
        ISimulationState state = factory.AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, standings,null, null , context, utility, "13-11-2020", "02-04-2021",0, output);
        assertTrue(state instanceof AdvanceToNextSeasonState);
    }

    @Test
    public void agingState() {
        ISimulationState state = factory.AgingState(leagueToSimulate, schedule, standingsDb, standings, null, context, utility, "13-11-2020",
                "02-04-2021", 0, output);
        assertTrue(state instanceof AgingState);
    }

    @Test
    public void generatePlayoffScheduleState() {
        ISimulationState state = factory.GeneratePlayoffScheduleState(leagueToSimulate, utility, standingsDb, standings, output, context, 0,
                "13-11-2020", "02-04-2021");
        assertTrue(state instanceof GeneratePlayoffScheduleState);
    }

    @Test
    public void generateRegularSeasonScheduleState() {
        ISimulationState state = factory.GenerateRegularSeasonScheduleState(leagueToSimulate, output, 0, context, standingsDb, standings, utility);
        assertTrue(state instanceof GenerateRegularSeasonScheduleState);
    }

    @Test
    public void injuryCheckState() {
        ISimulationState state = factory.InjuryCheckState(leagueToSimulate, null, schedule, context, utility, "13-11-2020", "02-04-2021",
                0, output, standingsDb, standings);
        assertTrue(state instanceof InjuryCheckState);
    }

    @Test
    public void internalEndState() {
        ISimulationState state = factory.InternalEndState(output);
        assertTrue(state instanceof InternalEndState);
    }

    @Test
    public void internalSimulationState() {
        ISimulationState state = factory.InternalSimulationState(input, output, 0, "", leagueToSimulate, context, null,
                standingsDb);
        assertTrue(state instanceof InternalSimulationState);
    }

    @Test
    public void internalStartState() {
        ISimulationState state = factory.InternalStartState(input, output, "", leagueToSimulate, context, null, standingsDb);
        assertTrue(state instanceof InternalStartState);
    }

    @Test
    public void internalStateContext() {
        InternalStateContext context = factory.InternalStateContext(input, output);
        assertTrue(context instanceof InternalStateContext);
    }

    @Test
    public void persistState() {
        ISimulationState state = factory.PersistState(leagueToSimulate, schedule, standingsDb, standings, context, utility, "13-11-2020", "02-04-2021", 0,
                output);
        assertTrue(state instanceof PersistState);
    }

    @Test
    public void simulateGameState() {
        ISimulationState state = factory.SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, utility, "13-11-2020", "02-04-2021",
                0, output);
        assertTrue(state instanceof SimulateGameState);
    }

    @Test
    public void tradingState() {
        ISimulationState state = factory.TradingState(leagueToSimulate, null, context, output, utility, "13-11-2020", "02-04-2021", 0,
                standingsDb, standings, schedule);
        assertTrue(state instanceof TradingState);
    }

    @Test
    public void trainingState() {
        ISimulationState state = factory.TrainingState(leagueToSimulate, null, schedule, utility, "13-11-2020", "02-04-2021", output, context,
                standingsDb, standings, 0);
        assertTrue(state instanceof TrainingState);
    }
}