package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.IInjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.ScheduleConstants;
import dal.asd.dpl.Util.StateConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InjuryCheckState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private IInjuryManagement injury;
	private ISchedule schedule;
	private InternalStateContext context;
	private SeasonCalendar seasonCalendar;
	private String currentDate;
	private IUserOutput output;

	public InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
		this.leagueToSimulate = leagueToSimulate;
		this.injury = injury;
		this.schedule = schedule;
		this.context = context;
		this.seasonCalendar = seasonCalendar;
		this.currentDate = currentDate;
		this.output = output;
		this.stateName = StateConstants.INJURY_STATE;
	}

	public void nextState(InternalStateContext context) {
		if (schedule.anyUnplayedGame(this.currentDate)) {
			this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
		} else {
			if (seasonCalendar.isTradeDeadlinePending(this.currentDate)) {
				this.nextStateName = StateConstants.TRADING_STATE;
			} else {
				this.nextStateName = StateConstants.AGING_STATE;
			}
		}
	}

	public void doProcessing() {
		List<Map<String, String>> competingList = new ArrayList<Map<String,String>>();
		competingList = schedule.getFinalSchedule().get(currentDate);
		for (Map<String, String> teams : competingList) {
			for (Map.Entry<String, String> entry : teams.entrySet()) {
				leagueToSimulate = injury.getInjuryStatusByTeam(entry.getKey(), leagueToSimulate);
				leagueToSimulate = injury.getInjuryStatusByTeam(entry.getValue(), leagueToSimulate);
			}
		}
		if(schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON){
			Map<String, List<Map<String, String>>> currentSchedule = schedule.getFinalSchedule();
			currentSchedule.remove(this.currentDate);
			schedule.setFinalSchedule(currentSchedule);
		}
		output.setOutput("Inside Injury Check state");
		output.sendOutput();
	}

	public League getUpdatedLeague() {
		return leagueToSimulate;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}
