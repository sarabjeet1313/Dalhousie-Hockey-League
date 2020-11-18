package dpl.LeagueSimulationManagement.LeagueManagement.Schedule;

import java.util.Calendar;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public interface IScheduleAbstractFactory {

	public PlayoffSchedule PlayoffSchedule(IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings, int season);
	
	public RegularSeasonSchedule RegularSeasonSchedule(Calendar calendar, IUserOutput output);

	public SeasonCalendar SeasonCalendar(int season, IUserOutput output);
}
