package dal.asd.dpl.TeamManagement;

import java.util.List;
import java.util.Random;
import dal.asd.dpl.NewsSystem.InjuryPublisher;
import dal.asd.dpl.NewsSystem.NewsSubscriber;
import dal.asd.dpl.util.ConstantsUtil;

public class Player implements IPlayerInfo, IInjuryCalculator, IAgingCalculator {

	private String playerName;
	private String position;
	private boolean captain;
	private int age;
	private int skating;
	private int shooting;
	private int checking;
	private int saving;
	private boolean isInjured;
	private boolean retireStatus;
	private int daysInjured;

	public Player(String playerName, String position, boolean captain, int age, int skating, int shooting, int checking,
			int saving, boolean isInjured, boolean retireStatus, int daysInjured) {
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
		InjuryPublisher.getInstance().subscribe(new NewsSubscriber());

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

		if ((randomValue <= randomInjuryChance) && (player.isInjured() == Boolean.FALSE)) {
			player.setInjured(Boolean.TRUE);
			int injuryDays = random.nextInt(injuryDaysHigh - injuryDaysLow) + injuryDaysLow;
			player.setDaysInjured(injuryDays);
		}
		InjuryPublisher.getInstance().notify(player.getPlayerName(), player.getDaysInjured());
		return player;
	}

	@Override
	public League increaseAge(int days, League league) {
		List<Conference> conferenceList = league.getConferenceList();
		int maximumRetirementAge = league.getGameConfig().getAging().getMaximumAge();
		List<Player> freeAgentsList = league.getFreeAgents();

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
					for (Player player : playersByTeam) {
						int years = player.getAge();
						player.setAge(years + (int) (days / 365));

						if (player.getAge() > maximumRetirementAge) {
							player.setRetireStatus(true);
						}
					}
					league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList().get(tIndex).setPlayerList(playersByTeam);
				}
			}
		}
		
		for (Player freeplayer : freeAgentsList) {
			int years = freeplayer.getAge();
			freeplayer.setAge(years + (int) (days / 365));

			if (freeplayer.getAge() > maximumRetirementAge) {
				freeplayer.setRetireStatus(true);
			}
		}
		
		league.setFreeAgents(freeAgentsList);
		IRetirementManager retirementManager = new RetirementManagement();
		return retirementManager.replaceRetiredPlayers(league);
	}

}
