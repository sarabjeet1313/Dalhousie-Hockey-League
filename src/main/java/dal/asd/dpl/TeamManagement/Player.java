package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String playerName;
	private String position;
	private boolean captain;
	private int age;
	private int skating;
	private int shooting;
	private int checking;
	private int saving;
	
	public Player(String playerName, String position, boolean captain, int age, int skating, int shooting,
			int checking, int saving) {
		this.playerName = playerName;
		this.position = position;
		this.captain = captain;
		this.age = age;
		this.skating = skating;
		this.shooting = shooting;
		this.checking = checking;
		this.saving = saving;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String name) {
		playerName = name;
	}
	
	public String getPlayerPosition() {
		return position;
	}
	
	public void setPlayerPosition(String p) {
		position = p;
	}
	
	public boolean getCaptain() {
		return captain;
	}
	
	public void setCaptain(boolean c) {
		captain = c;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	
}
