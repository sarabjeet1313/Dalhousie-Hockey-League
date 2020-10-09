package dal.asd.dpl.TeamManagement;

public class Players {
	
	private String playerName;
	private String position;
	private boolean captain;
	
	public Players(String playerName, String position, boolean captain) {
		this.playerName = playerName;
		this.position = position;
		this.captain = captain;
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
	
}
