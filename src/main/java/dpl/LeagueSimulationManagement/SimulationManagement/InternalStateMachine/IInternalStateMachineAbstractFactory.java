package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
}
