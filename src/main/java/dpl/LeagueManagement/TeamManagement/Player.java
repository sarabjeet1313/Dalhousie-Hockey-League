package dpl.LeagueManagement.TeamManagement;

import com.google.gson.annotations.Expose;

public class Player implements IPlayerInfo {

	@Expose(serialize = true, deserialize = true)
	private String playerName;
	@Expose(serialize = true, deserialize = true)
	private String position;
	@Expose(serialize = true, deserialize = true)
	private boolean captain;
	@Expose(serialize = true, deserialize = true)
	private int age;
	@Expose(serialize = true, deserialize = true)
	private int skating;
	@Expose(serialize = true, deserialize = true)
	private int shooting;
	@Expose(serialize = true, deserialize = true)
	private int checking;
	@Expose(serialize = true, deserialize = true)
	private int saving;
	@Expose(serialize = true, deserialize = true)
	private int goals;
	@Expose(serialize = true, deserialize = true)
	private int penalties;
	@Expose(serialize = true, deserialize = true)
	private int saves;
	@Expose(serialize = true, deserialize = true)
	private boolean isInjured;
	@Expose(serialize = true, deserialize = true)
	private boolean retireStatus;
	@Expose(serialize = true, deserialize = true)
	private int daysInjured;
	@Expose(serialize = true, deserialize = true)
	private boolean isActive;
	@Expose(serialize = true, deserialize = true)
	private int birthDay;
	@Expose(serialize = true, deserialize = true)
	private int birthMonth;
	@Expose(serialize = true, deserialize = true)
	private int birthYear;
	@Expose(serialize = true, deserialize = true)
	private boolean isDraftPlayer;

	public Player() {
		super();
	}

	public Player(String playerName, String position, boolean captain, int age, int skating, int shooting, int checking,
			int saving, boolean isInjured, boolean retireStatus, int daysInjured, boolean isActive, int birthDay,
			int birthMonth, int birthYear, boolean isDraftPlayer) {
		super();
		this.playerName = playerName;
		this.position = position;
		this.captain = captain;
		this.age = age;
		this.skating = skating;
		this.shooting = shooting;
		this.checking = checking;
		this.saving = saving;
		this.isInjured = isInjured;
		this.retireStatus = retireStatus;
		this.daysInjured = daysInjured;
		this.isActive = isActive;
		this.goals = 0;
		this.saves = 0;
		this.penalties = 0;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.isDraftPlayer = isDraftPlayer;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getSaves() {
		return saves;
	}

	public void setSaves(int save) {
		this.saves = save;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isCaptain() {
		return captain;
	}

	public void setCaptain(boolean captain) {
		this.captain = captain;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSkating() {
		return skating;
	}

	public void setSkating(int skating) {
		this.skating = skating;
	}

	public int getShooting() {
		return shooting;
	}

	public void setShooting(int shooting) {
		this.shooting = shooting;
	}

	public int getChecking() {
		return checking;
	}

	public void setChecking(int checking) {
		this.checking = checking;
	}

	public int getSaving() {
		return saving;
	}

	public void setSaving(int saving) {
		this.saving = saving;
	}

	public boolean isInjured() {
		return isInjured;
	}

	public void setInjured(boolean isInjured) {
		this.isInjured = isInjured;
	}

	public boolean isRetireStatus() {
		return retireStatus;
	}

	public void setRetireStatus(boolean retireStatus) {
		this.retireStatus = retireStatus;
	}

	public int getDaysInjured() {
		return daysInjured;
	}

	public void setDaysInjured(int daysInjured) {
		this.daysInjured = daysInjured;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public boolean isDraftPlayer() {
		return isDraftPlayer;
	}

	public void setDraftPlayer(boolean isDraftPlayer) {
		this.isDraftPlayer = isDraftPlayer;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public double getPlayerStrength(Player player) {

		double strength = 0.0;
		String position = player.getPosition();
		int skating = player.getSkating();
		int shooting = player.getShooting();
		int checking = player.getChecking();
		int saving = player.getSaving();
		boolean isInjured = player.isInjured();

		if (position.equalsIgnoreCase(GeneralConstants.FORWARD.toString())) {
			strength = skating + shooting + (checking / 2.0);
		} else if (position.equalsIgnoreCase(GeneralConstants.DEFENSE.toString())) {
			strength = skating + checking + (shooting / 2.0);
		} else if (position.equalsIgnoreCase(GeneralConstants.GOALIE.toString())) {
			strength = skating + saving;
		}

		if (isInjured) {
			return strength / 2;
		} else {
			return strength;
		}
	}

}
