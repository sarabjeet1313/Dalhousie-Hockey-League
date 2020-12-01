package dpl.LeagueManagement.Schedule;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.util.Calendar;

public interface IScheduleAbstractFactory {

	PlayoffSchedule PlayoffSchedule(IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings, int season);
	
	RegularSeasonSchedule RegularSeasonSchedule(Calendar calendar, IUserOutput output);

	SeasonCalendar SeasonCalendar(int season, IUserOutput output);
}
