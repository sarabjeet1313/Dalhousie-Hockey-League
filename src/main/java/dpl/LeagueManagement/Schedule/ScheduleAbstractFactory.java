package dpl.LeagueManagement.Schedule;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.util.Calendar;

public class ScheduleAbstractFactory implements IScheduleAbstractFactory {

	@Override
	public PlayoffSchedule PlayoffSchedule(IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings,  int season) {
		return new PlayoffSchedule(output, standingsDb, standings, season);
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
