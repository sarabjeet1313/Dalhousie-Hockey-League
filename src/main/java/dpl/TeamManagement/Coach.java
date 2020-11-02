package dpl.TeamManagement;

import java.util.List;

import dpl.DplConstants.TeamManagementConstants;

public class Coach {

	private String coachName;
	private double skating;
	private double shooting;
	private double checking;
	private double saving;
	private ICoachPersistance coachDb;

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

	public boolean saveTeamCoaches(Coach coach, String teamName, String leagueName) {
		boolean isValid = Boolean.FALSE;
		isValid = coachDb.persistCoaches(coach, teamName, leagueName);
		return isValid;
	}

	public boolean saveLeagueCoaches(League league) {
		List<Coach> coachList = league.getCoaches();
		String leagueName = league.getLeagueName();
		boolean isValid = Boolean.FALSE, flag = Boolean.FALSE;
		String teamName = TeamManagementConstants.EMPTY.toString();
		for (int index = 0; index < coachList.size(); index++) {
			Coach coach = coachList.get(index);
			isValid = coachDb.persistCoaches(coach, teamName, leagueName);
			if (isValid == Boolean.FALSE) {
				flag = Boolean.FALSE;
			}
		}
		return (isValid && flag);
	}

}
