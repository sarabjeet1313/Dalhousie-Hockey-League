package dpl.LeagueSimulationManagement.LeagueManagement.Schedule;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.Calendar;

public interface IScheduleAbstractFactory {

	PlayoffSchedule PlayoffSchedule(IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings, int season);
	
	RegularSeasonSchedule RegularSeasonSchedule(Calendar calendar, IUserOutput output);

	SeasonCalendar SeasonCalendar(int season, IUserOutput output);
}
