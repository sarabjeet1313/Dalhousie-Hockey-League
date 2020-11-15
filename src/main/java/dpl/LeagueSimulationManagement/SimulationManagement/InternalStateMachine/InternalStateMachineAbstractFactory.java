package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class InternalStateMachineAbstractFactory implements IInternalStateMachineAbstractFactory {

	@Override
	public ISimulationState AdvanceTimeState(League leagueToSimulate, ISchedule schedule, SeasonCalendar utility,
			IStandingsPersistance standingsDb, String startDate, String endDate, IUserOutput output,
			InternalStateContext context, int season) {
		return new AdvanceTimeState(leagueToSimulate, schedule, utility, standingsDb, startDate, endDate, output,
				context, season);
	}

	@Override
	public ISimulationState AdvanceToNextSeasonState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, IInjuryManagement injury, IRetirementManagement retirement,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output) {
		return new AdvanceToNextSeasonState(leagueToSimulate, schedule, standingsDb, injury, retirement, context,
				seasonCalendar, currentDate, endDate, season, output);
	}

	@Override
	public ISimulationState AgingState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
			IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate,
			String endDate, int season, IUserOutput output) {
		return new AgingState(leagueToSimulate, schedule, standingsDb, injury, context, seasonCalendar, currentDate,
				endDate, season, output);
	}

	@Override
	public ISimulationState GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
			IStandingsPersistance standings, IUserOutput output, InternalStateContext context, int season,
			String currentDate, String endDate) {
		return new GeneratePlayoffScheduleState(leagueToSimulate, seasonCalendar, standings, output, context, season,
				currentDate, endDate);
	}

	@Override
	public ISimulationState GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
			InternalStateContext context, IStandingsPersistance standingsDb, SeasonCalendar utility) {
		return new GenerateRegularSeasonScheduleState(leagueToSimulate, output, season, context, standingsDb, utility);
	}

	@Override
	public ISimulationState InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output, IStandingsPersistance standingsDb) {
		return new InjuryCheckState(leagueToSimulate, injury, schedule, context, seasonCalendar, currentDate, endDate,
				season, output, standingsDb);
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
	public ISimulationState PersistState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output) {
		return new PersistState(leagueToSimulate, schedule, standingsDb, context, utility, currentDate, endDate, season,
				output);
	}

	@Override
	public ISimulationState SimulateGameState(League leagueToSimulate, ISchedule schedule,
			IStandingsPersistance standingsDb, InternalStateContext context, SeasonCalendar utility, String currentDate,
			String endDate, int season, IUserOutput output) {
		return new SimulateGameState(leagueToSimulate, schedule, standingsDb, context, utility, currentDate, endDate,
				season, output);
	}

	@Override
	public ISimulationState TradingState(League leagueToSimulate, Trade trade, InternalStateContext context,
			IUserOutput output, SeasonCalendar utility, String currentDate, String endDate, int season,
			IStandingsPersistance standingsDb, ISchedule schedule) {
		return new TradingState(leagueToSimulate, trade, context, output, utility, currentDate, endDate, season,
				standingsDb, schedule);
	}

	@Override
	public ISimulationState TrainingState(League leagueToSimulate, Training training, ISchedule schedule,
			SeasonCalendar utility, String currentDate, String endDate, IUserOutput output,
			InternalStateContext context, IStandingsPersistance standings, int season) {
		return new TrainingState(leagueToSimulate, training, schedule, utility, currentDate, endDate, output, context,
				standings, season);
	}

}
