package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueManagement.Trading.Trade;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public interface IInternalStateMachineAbstractFactory {

	public ISimulationState AdvanceTimeState(League leagueToSimulate, ISchedule schedule, SeasonCalendar utility,
			IStandingsPersistance standingsDb, StandingInfo standings, String startDate, String endDate, IUserOutput output,
			InternalStateContext context, int season);

	public ISimulationState AdvanceToNextSeasonState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, StandingInfo standings, IInjuryManagement injury, IRetirementManagement retirement,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output);

	public ISimulationState AgingState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate,
			String endDate, int season, IUserOutput output);

	public ISimulationState GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
			IStandingsPersistance standingsDb,  StandingInfo standings, IUserOutput output, InternalStateContext context, int season,
			String currentDate, String endDate);

	public ISimulationState GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
															   InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, SeasonCalendar utility);

	public ISimulationState InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings);

	public ISimulationState InternalEndState(IUserOutput output);

	public ISimulationState InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName,
			League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
			IStandingsPersistance standingsDb);

	public ISimulationState InternalStartState(IUserInput input, IUserOutput output, String teamName,
			League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
			IStandingsPersistance standingsDb);

	public InternalStateContext InternalStateContext(IUserInput input, IUserOutput output);

	public ISimulationState PersistState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,  StandingInfo standings,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output);

	public ISimulationState SimulateGameState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, StandingInfo standings, InternalStateContext context, SeasonCalendar utility, String currentDate,
			String endDate, int season, IUserOutput output);

	public ISimulationState TradingState(League leagueToSimulate, Trade trade, InternalStateContext context,
			IUserOutput output, SeasonCalendar utility, String currentDate, String endDate, int season,
			IStandingsPersistance standingsDb, StandingInfo standings, ISchedule schedule);

	public ISimulationState TrainingState(League leagueToSimulate, Training training, ISchedule schedule,
			SeasonCalendar utility, String currentDate, String endDate, IUserOutput output,
			InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, int season);

	public ISimulationState EndOfSeasonState(IUserOutput output);

	public ISimulationState AllStarGameState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility, String currentDate, String endDate, IUserOutput output, InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, int season);

	public ISimulationState TrophyState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output);

	public ISimulationState PlayerDraftState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output);

	public ISimulateMatch SimulateRegularSeasonMatch(String currentDate, ISchedule schedule, IUserOutput output, League leagueToSimulate, StandingInfo standings);

	public ISimulateMatch SimulatePlayoffSeasonMatch(String currentDate, ISchedule schedule, IUserOutput output, League leagueToSimulate, StandingInfo standings, SeasonCalendar utility);

	public GameContext GameContext(ISimulateMatch match);


}
