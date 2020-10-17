package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class Players {
	
	private String playerName;
	private String position;
	private boolean captain;
	private int age;
	private int skating;
	private int shooting;
	private int checking;
	private int saving;
	
	public Players(String playerName, String position, boolean captain, int age, int skating, int shooting,
			int checking, int saving) {
		super();
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
	
	public List<List<Players>> getAvailablePlayersList(Leagues league){
		List<Players> goalieList = new ArrayList<Players>();
		List<Players> forwordList = new ArrayList<Players>();
		List<Players> defenseList = new ArrayList<Players>();
		List<List<Players>> list = new ArrayList<List<Players>>(); 
		List<Players> playerList = league.getFreeAgents();
		for(int index = 0; index < playerList.size(); index++) {
			if(playerList.get(index).getPlayerPosition().equals("goalie")) {
				goalieList.add(playerList.get(index));
			}
			else if(playerList.get(index).getPlayerPosition().equals("forward")) {
				forwordList.add(playerList.get(index));
			}
			else {
				defenseList.add(playerList.get(index));
			}
		}
		
		list.add(goalieList);
		list.add(forwordList);
		list.add(defenseList);
		return list;
	}
	
//	public List<Players> sortList(String playerType, List<Players> list){
//		List<Players> tempList = list;
//		for(int index = 0; index < tempList.size(); index++) {
//			if(tempList.get(index).getPlayerPosition().equals("goalie")) {
//				
//			}
//		}
//		return list;
//	}
	
//	@Override
//	public int compareTo(Players player) {
//		int value = 0;
//		if(player.getPlayerPosition().equals("goalie")) {
//			value = player.getSaving();
//		}
//		else if(player.getPlayerPosition().equals("goalie")) {
//			value = player.get;
//		}
//		else {
//			value = player.getSaving();
//		}
//		return this.saving - value;
//	}
	
//	@Override
//	public String toString() {
//		return "Players"
//	}
	

}
