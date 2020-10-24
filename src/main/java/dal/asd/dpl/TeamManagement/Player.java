package dal.asd.dpl.TeamManagement;

import java.util.Random;
import dal.asd.dpl.util.ConstantsUtil;

public class Player implements IPlayerInfo, IInjuryCalculator {

	private String playerName;
	private String position;
	private boolean captain;
	private int age;
	private int skating;
	private int shooting;
	private int checking;
	private int saving;
	private boolean isInjured;
	private int numberOfInjuryDays;

	public Player(String playerName, String position, boolean captain, int age, int skating, int shooting, int checking,
			int saving, boolean isInjured, int numberOfInjuryDays) {
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
		this.numberOfInjuryDays= numberOfInjuryDays;
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

	public int getNumberOfInjuryDays() {
		return numberOfInjuryDays;
	}

	public void setNumberOfInjuryDays(int numberOfInjuryDays) {
		this.numberOfInjuryDays = numberOfInjuryDays;
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

		if (position.equalsIgnoreCase(ConstantsUtil.FORWARD.toString())) {
			strength = skating + shooting + (checking / 2.0);
		} else if (position.equalsIgnoreCase(ConstantsUtil.DEFENSE.toString())) {
			strength = skating + checking + (shooting / 2.0);
		} else if (position.equalsIgnoreCase(ConstantsUtil.GOALIE.toString())) {
			strength = skating + saving;
		}

		if (isInjured) {
			return strength / 2;
		} else {
			return strength;
		}
	}

	@Override
	public Player getPlayerInjuryDays(Player player, League league) {
		Random random = new Random();
		
		double randomInjuryChance = league.getGameConfig().getInjury().getRandomInjuryChance() * 100;
		int injuryDaysLow = league.getGameConfig().getInjury().getInjuryDaysLow();
		int injuryDaysHigh = league.getGameConfig().getInjury().getInjuryDaysHigh();
		double randomValue = Math.random() * 100;
		
		if((randomValue <= randomInjuryChance) && (player.isInjured() == Boolean.FALSE)) {
			player.setInjured(Boolean.TRUE);
			int injuryDays = random.nextInt(injuryDaysHigh - injuryDaysLow) + injuryDaysLow;
			player.setNumberOfInjuryDays(injuryDays);
		}
		
		return player;
	}

}
