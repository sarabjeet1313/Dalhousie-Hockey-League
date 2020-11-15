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
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.TradeReset;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class InternalStateMachineAbstractFactory implements IInternalStateMachineAbstractFactory {

	@Override
	public ISimulationState AdvanceTimeState(String startDate, String endDate, IUserOutput output,
			InternalStateContext context) {
		return new AdvanceTimeState(startDate, endDate, output, context);
	}

	@Override
	public ISimulationState AdvanceToNextSeasonState(League leagueToSimulate, IInjuryManagement injury,
			IRetirementManagement retirement, InternalStateContext context, SeasonCalendar seasonCalendar,
			String currentDate, IUserOutput output) {
		return new AdvanceToNextSeasonState(leagueToSimulate, injury, retirement, context, seasonCalendar, currentDate,
				output);
	}

	@Override
	public ISimulationState AgingState(League leagueToSimulate, IInjuryManagement injury, InternalStateContext context,
			SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
		return new AgingState(leagueToSimulate, injury, context, seasonCalendar, currentDate, output);
	}

	@Override
	public ISimulationState GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
			IStandingsPersistance standings, IUserOutput output, InternalStateContext context, int season) {
		return new GeneratePlayoffScheduleState(leagueToSimulate, seasonCalendar, standings, output, context, season);
	}

	@Override
	public ISimulationState GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
			InternalStateContext context, IStandingsPersistance standingsDb) {
		return new GenerateRegularSeasonScheduleState(leagueToSimulate, output, season, context, standingsDb);
	}

	@Override
	public ISimulationState InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
		return new InjuryCheckState(leagueToSimulate, injury, schedule, context, seasonCalendar, currentDate, output);
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
	public ISimulationState PersistState(League leagueToSimulate, ISchedule schedule, StandingInfo standings,
			TradeReset tradeReset, InternalStateContext context, SeasonCalendar utility, String currentDate,
			IUserOutput output) {
		return new PersistState(leagueToSimulate, schedule, standings, tradeReset, context, utility, currentDate,
				output);
	}

	@Override
	public ISimulationState SimulateGameState(League leagueToSimulate, ISchedule schedule, StandingInfo standings,
			InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
		return new SimulateGameState(leagueToSimulate, schedule, standings, context, utility, currentDate, output);
	}

	@Override
	public ISimulationState TradingState(League leagueToSimulate, Trade trade, InternalStateContext context,
			IUserOutput output) {
		return new TradingState(leagueToSimulate, trade, context, output);
	}

	@Override
	public ISimulationState TrainingState(League leagueToSimulate, Training training, ISchedule schedule,
			SeasonCalendar utility, String currentDate, IUserOutput output, InternalStateContext context) {
		return new TrainingState(leagueToSimulate, training, schedule, utility, currentDate, output, context);
	}

}
