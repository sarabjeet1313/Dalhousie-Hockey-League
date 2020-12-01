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

public class InternalStateMachineAbstractFactory implements IInternalStateMachineAbstractFactory {

	@Override
	public ISimulationState AdvanceTimeState(League leagueToSimulate, ISchedule schedule, SeasonCalendar utility,
			IStandingsPersistance standingsDb, StandingInfo standings, String startDate, String endDate, IUserOutput output,
			InternalStateContext context, int season) {
		return new AdvanceTimeState(leagueToSimulate, schedule, utility, standingsDb, standings, startDate, endDate, output,
				context, season);
	}

	@Override
	public ISimulationState AdvanceToNextSeasonState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, StandingInfo standings, IInjuryManagement injury, IRetirementManagement retirement,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output) {
		return new AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context,
				seasonCalendar, currentDate, endDate, season, output);
	}

	@Override
	public ISimulationState AgingState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
									   StandingInfo standings, IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate,
			String endDate, int season, IUserOutput output) {
		return new AgingState(leagueToSimulate, schedule, standingsDb, standings, injury, context, seasonCalendar, currentDate,
				endDate, season, output);
	}

	@Override
	public ISimulationState GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
			IStandingsPersistance standingsDb, StandingInfo standings, IUserOutput output, InternalStateContext context, int season,
			String currentDate, String endDate) {
		return new GeneratePlayoffScheduleState(leagueToSimulate, seasonCalendar, standingsDb, standings, output, context, season,
				currentDate, endDate);
	}

	@Override
	public ISimulationState GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
															   InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, SeasonCalendar utility) {
		return new GenerateRegularSeasonScheduleState(leagueToSimulate, output, season, context, standingsDb, standings, utility);
	}

	@Override
	public ISimulationState InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings) {
		return new InjuryCheckState(leagueToSimulate, injury, schedule, context, seasonCalendar, currentDate, endDate,
				season, output, standingsDb, standings);
	}

	@Override
	public ISimulationState InternalEndState(IUserOutput output) {
		return new InternalEndState(output);
	}

	@Override
	public ISimulationState InternalSimulationState(IUserInput input, IUserOutput output, int seasons, String teamName,
			League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
			IStandingsPersistance standingsDb) {
		return new InternalSimulationState(input, output, seasons, teamName, leagueToSimulate, context, tradeDb,
				standingsDb);
	}

	@Override
	public ISimulationState InternalStartState(IUserInput input, IUserOutput output, String teamName,
			League leagueToSimulate, InternalStateContext context, ITradePersistence tradeDb,
			IStandingsPersistance standingsDb) {
		return new InternalStartState(input, output, teamName, leagueToSimulate, context, tradeDb, standingsDb);
	}

	@Override
	public InternalStateContext InternalStateContext(IUserInput input, IUserOutput output) {
		return new InternalStateContext(input, output);
	}

	@Override
	public ISimulationState PersistState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output) {
		return new PersistState(leagueToSimulate, schedule, standingsDb, standings, context, utility, currentDate, endDate, season,
				output);
	}

	@Override
	public ISimulationState SimulateGameState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, StandingInfo standings, InternalStateContext context, SeasonCalendar utility, String currentDate,
			String endDate, int season, IUserOutput output) {
		return new SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, utility, currentDate, endDate,
				season, output);
	}

	@Override
	public ISimulationState TradingState(League leagueToSimulate, Trade trade, InternalStateContext context,
			IUserOutput output, SeasonCalendar utility, String currentDate, String endDate, int season,
			IStandingsPersistance standingsDb, StandingInfo standings, ISchedule schedule) {
		return new TradingState(leagueToSimulate, trade, context, output, utility, currentDate, endDate, season,
				standingsDb, standings, schedule);
	}

	@Override
	public ISimulationState TrainingState(League leagueToSimulate, Training training, ISchedule schedule,
			SeasonCalendar utility, String currentDate, String endDate, IUserOutput output,
			InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		return new TrainingState(leagueToSimulate, training, schedule, utility, currentDate, endDate, output, context,
				standingsDb, standings, season);
	}

	@Override
	public ISimulationState EndOfSeasonState(IUserOutput output) {
		return new EndOfSeasonState(output);
	}

	@Override
	public ISimulationState AllStarGameState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility, String currentDate, String endDate, IUserOutput output, InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		return new AllStarGameState(leagueToSimulate, training, schedule, utility, currentDate, endDate, output, context, standingsDb, standings, season) ;
	}

	@Override
	public ISimulationState TrophyState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output) {
		return new TrophySystemState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context, seasonCalendar, currentDate, endDate, season, output);
	}

	@Override
	public ISimulationState PlayerDraftState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output) {
		return new PlayerDraftState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context, seasonCalendar, currentDate, endDate, season, output);
	}

	@Override
	public ISimulateMatch SimulateRegularSeasonMatch(String currentDate, ISchedule schedule, IUserOutput output, League leagueToSimulate, StandingInfo standings) {
		return new SimulateRegularSeasonMatch(currentDate, schedule, output, leagueToSimulate, standings);
	}

	@Override
	public ISimulateMatch SimulatePlayoffSeasonMatch(String currentDate, ISchedule schedule, IUserOutput output, League leagueToSimulate, StandingInfo standings, SeasonCalendar utility) {
		return new SimulatePlayoffSeasonMatch(currentDate, schedule, output, leagueToSimulate, standings, utility);
	}

	@Override
	public GameContext GameContext(ISimulateMatch match) {
		return new GameContext(match);
	}
}
