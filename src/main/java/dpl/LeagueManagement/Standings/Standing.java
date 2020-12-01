package dpl.LeagueManagement.Standings;

import dpl.SystemConfig;

import java.util.ArrayList;
import java.util.List;

public class Standing {

	private int season;
	private List<TeamStanding> standings;

	public Standing() {
		standings = new ArrayList<>();
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public List<TeamStanding> getStandings() {
		return standings;
	}

	public void setStandings(List<TeamStanding> standings) {
		this.standings = standings;
	}

	private boolean checkTeamInStanding(String teamName) {
		if(standings.size() < 1) {
			return false;
		}

		for(TeamStanding teamStanding : standings) {
			if(teamStanding.getTeamName().equals(teamName)) {
				return true;
			}
		}
		return false;
	}

	public void updateStandingsWin(String teamName) {
		if(checkTeamInStanding(teamName)) {
			for(TeamStanding teamStanding : standings) {
				if (teamStanding.getTeamName().equals(teamName)) {
					teamStanding.setWins(teamStanding.getWins() + 1);
					teamStanding.setPoints(teamStanding.getPoints() + 2);
				}
			}
		}
		else {
			TeamStanding teamStanding = SystemConfig.getSingleInstance().getStandingsAbstractFactory().TeamStanding();
			teamStanding.setTeamName(teamName);
			teamStanding.setPoints(2);
			teamStanding.setWins(1);
			standings.add(teamStanding);
		}
	}

	public void updateStandingsLosses(String teamName) {
		if(checkTeamInStanding(teamName)) {
			for(TeamStanding teamStanding : standings) {
				if (teamStanding.getTeamName().equals(teamName)) {
					teamStanding.setLosses(teamStanding.getLosses() + 1);
					teamStanding.setTradeLossPoint(teamStanding.getTradeLossPoint() +1);
				}
			}
		}
		else {
			TeamStanding teamStanding = SystemConfig.getSingleInstance().getStandingsAbstractFactory().TeamStanding();
			teamStanding.setTeamName(teamName);
			teamStanding.setLosses(1);
			teamStanding.setTradeLossPoint(1);
			standings.add(teamStanding);
		}

	}

}

