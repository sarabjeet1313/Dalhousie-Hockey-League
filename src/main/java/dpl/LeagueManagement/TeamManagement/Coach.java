package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Coach {

	@Expose(serialize = true, deserialize = true)
	private String coachName;
	@Expose(serialize = true, deserialize = true)
	private double skating;
	@Expose(serialize = true, deserialize = true)
	private double shooting;
	@Expose(serialize = true, deserialize = true)
	private double checking;
	@Expose(serialize = true, deserialize = true)
	private double saving;
	private ICoachPersistance coachDb;

	public Coach() {
		super();
	}

	public Coach(String coachName, double skating, double shooting, double checking, double saving,
			ICoachPersistance coachDb) {
		this.coachName = coachName;
		this.skating = skating;
		this.shooting = shooting;
		this.checking = checking;
		this.saving = saving;
		this.coachDb = coachDb;
	}

	public Coach(String coachName, double skating, double shooting, double checking, double saving) {
		this.coachName = coachName;
		this.skating = skating;
		this.shooting = shooting;
		this.checking = checking;
		this.saving = saving;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public double getSkating() {
		return skating;
	}

	public void setSkating(double skating) {
		this.skating = skating;
	}

	public double getShooting() {
		return shooting;
	}

	public void setShooting(double shooting) {
		this.shooting = shooting;
	}

	public double getChecking() {
		return checking;
	}

	public void setChecking(double checking) {
		this.checking = checking;
	}

	public double getSaving() {
		return saving;
	}

	public void setSaving(double saving) {
		this.saving = saving;
	}

	public boolean saveTeamCoaches(Coach coach, String teamName, String leagueName) throws IOException {
		boolean isValid = Boolean.FALSE;
		Coach tempCoach = new Coach(coach.getCoachName(), coach.getSkating(), coach.getShooting(), coach.getChecking(),
				coach.getSaving());
		try {
			isValid = coachDb.persistCoaches(tempCoach, teamName, leagueName);
		} catch (IOException e) {
			throw e;
		}

		return isValid;
	}

	public boolean saveLeagueCoaches(League league) throws IOException {
		boolean isValid = Boolean.FALSE;
		boolean flag = Boolean.FALSE;
		try {
			List<Coach> coachList = league.getCoaches();
			String leagueName = league.getLeagueName();
			String teamName = TeamManagementConstants.EMPTY.toString();
			for (int index = 0; index < coachList.size(); index++) {
				Coach coach = coachList.get(index);
				Coach tempCoach = new Coach(coach.getCoachName(), coach.getSkating(), coach.getShooting(),
						coach.getChecking(), coach.getSaving());
				isValid = coachDb.persistCoaches(tempCoach, teamName, leagueName);
				if (isValid == Boolean.FALSE) {
					flag = Boolean.FALSE;
				}
			}
		} catch (IOException e) {
			throw e;
		}
		return (isValid && flag);
	}

}
