package dpl.LeagueManagement.Standings;

public class TeamStanding {
	private String teamName;
	private int wins;
	private int losses;
	private int points;
	private int tradeLossPoint;

	public TeamStanding() {
		this.wins = 0;
		this.losses = 0;
		this.points = 0;
		this.tradeLossPoint = 0;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getTradeLossPoint() {
		return tradeLossPoint;
	}

	public void setTradeLossPoint(int tradeLossPoint) {
		this.tradeLossPoint = tradeLossPoint;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
