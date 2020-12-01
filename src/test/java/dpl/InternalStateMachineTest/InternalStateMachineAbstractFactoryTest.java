package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.InternalStateMachine.*;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.ScheduleTest.MockSchedule;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InternalStateMachineAbstractFactoryTest {

    private IInternalStateMachineAbstractFactory factory;
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
        factory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        standingsDb = StandingsMockDb.getInstance();
        standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
        schedule = MockSchedule.getInstance();
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
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
    public void endOfSeasonStateTest() {
        ISimulationState state = factory.EndOfSeasonState(output);
        assertTrue(state instanceof EndOfSeasonState);
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

    @Test
    public void simulateRegularSeasonMatchTest() {
        ISimulateMatch match = factory.SimulateRegularSeasonMatch("13-11-2020", schedule, output, leagueToSimulate, standings);
        assertTrue(match instanceof SimulateRegularSeasonMatch);

    }

    @Test
    public void simulatePlayoffSeasonMatchTest() {
        ISimulateMatch match = factory.SimulatePlayoffSeasonMatch("13-11-2020", schedule, output, leagueToSimulate, standings, utility);
        assertTrue(match instanceof SimulatePlayoffSeasonMatch);
    }

    @Test
    public void gameContextTest() {
        ISimulateMatch match = factory.SimulatePlayoffSeasonMatch("13-11-2020", schedule, output, leagueToSimulate, standings, utility);
        GameContext context = factory.GameContext(match);
        assertTrue(context instanceof GameContext);
    }

}