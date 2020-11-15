package dpl.LeagueSimulationManagement.LeagueManagement.Schedule;

import java.util.Calendar;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class ScheduleAbstractFactory implements IScheduleAbstractFactory {

	@Override
	public PlayoffSchedule PlayoffSchedule(IUserOutput output, IStandingsPersistance standings, int season) {
		return new PlayoffSchedule(output, standings, season);
	}

	@Override
	public RegularSeasonSchedule RegularSeasonSchedule(Calendar calendar, IUserOutput output) {
		return new RegularSeasonSchedule(calendar, output);
	}

	@Override
	public SeasonCalendar SeasonCalendar(int season, IUserOutput output) {
		return new SeasonCalendar(season, output);
	}

}
