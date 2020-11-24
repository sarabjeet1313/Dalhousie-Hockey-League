package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
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
    public void advanceToNextSeasonStateTest() {
        ISimulationState state = factory.AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, standings,null, null , context, utility, "13-11-2020", "02-04-2021",0, output);
        assertTrue(state instanceof AdvanceToNextSeasonState);
    }

    @Test
    public void agingStateTest() {
        ISimulationState state = factory.AgingState(leagueToSimulate, schedule, standingsDb, standings, null, context, utility, "13-11-2020",
                "02-04-2021", 0, output);
        assertTrue(state instanceof AgingState);
    }

    @Test
    public void generatePlayoffScheduleStateTest() {
        ISimulationState state = factory.GeneratePlayoffScheduleState(leagueToSimulate, utility, standingsDb, standings, output, context, 0,
                "13-11-2020", "02-04-2021");
        assertTrue(state instanceof GeneratePlayoffScheduleState);
    }

    @Test
    public void generateRegularSeasonScheduleStateTest() {
        ISimulationState state = factory.GenerateRegularSeasonScheduleState(leagueToSimulate, output, 0, context, standingsDb, standings, utility);
        assertTrue(state instanceof GenerateRegularSeasonScheduleState);
    }

    @Test
    public void injuryCheckStateTest() {
        ISimulationState state = factory.InjuryCheckState(leagueToSimulate, null, schedule, context, utility, "13-11-2020", "02-04-2021",
                0, output, standingsDb, standings);
        assertTrue(state instanceof InjuryCheckState);
    }

    @Test
    public void internalEndStateTest() {
        ISimulationState state = factory.InternalEndState(output);
        assertTrue(state instanceof InternalEndState);
    }

    @Test
    public void internalSimulationStateTest() {
        ISimulationState state = factory.InternalSimulationState(input, output, 0, "", leagueToSimulate, context, null,
                standingsDb);
        assertTrue(state instanceof InternalSimulationState);
    }

    @Test
    public void internalStartStateTest() {
        ISimulationState state = factory.InternalStartState(input, output, "", leagueToSimulate, context, null, standingsDb);
        assertTrue(state instanceof InternalStartState);
    }

    @Test
    public void internalStateContextTest() {
        InternalStateContext context = factory.InternalStateContext(input, output);
        assertTrue(context instanceof InternalStateContext);
    }

    @Test
    public void persistStateTest() {
        ISimulationState state = factory.PersistState(leagueToSimulate, schedule, standingsDb, standings, context, utility, "13-11-2020", "02-04-2021", 0,
                output);
        assertTrue(state instanceof PersistState);
    }

    @Test
    public void simulateGameStateTest() {
        ISimulationState state = factory.SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, utility, "13-11-2020", "02-04-2021",
                0, output);
        assertTrue(state instanceof SimulateGameState);
    }

    @Test
    public void tradingStateTest() {
        ISimulationState state = factory.TradingState(leagueToSimulate, null, context, output, utility, "13-11-2020", "02-04-2021", 0,
                standingsDb, standings, schedule);
        assertTrue(state instanceof TradingState);
    }

    @Test
    public void trainingStateTest() {
        ISimulationState state = factory.TrainingState(leagueToSimulate, null, schedule, utility, "13-11-2020", "02-04-2021", output, context,
                standingsDb, standings, 0);
        assertTrue(state instanceof TrainingState);
    }

    @Test
    public void allStarGameStateTest() {
        ISimulationState state = factory.AllStarGameState(leagueToSimulate, null, schedule, utility, "13-11-2020", "02-04-2021", output, context, standingsDb, standings, 0);
        assertTrue(state instanceof AllStarGameState);
    }

    @Test
    public void trophyStateTest() {
        ISimulationState state = factory.TrophyState(leagueToSimulate, schedule, standingsDb, standings, null, null, context, utility, "13-11-2020", "02-04-2021", 0, output);
        assertTrue(state instanceof TrophySystemState);
    }

    @Test
    public void playerDraftStateTest() {
        ISimulationState state = factory.PlayerDraftState(leagueToSimulate, schedule, standingsDb, standings, null, null, context, utility, "13-11-2020", "02-04-2021", 0, output);
        assertTrue(state instanceof PlayerDraftState);
    }

}