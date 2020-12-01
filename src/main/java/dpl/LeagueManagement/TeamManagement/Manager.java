package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Manager {

	@Expose(serialize = true, deserialize = true)
	private String managerName;
	@Expose(serialize = true, deserialize = true)
	private String personality;
	private IManagerPersistance managerDb;

	public Manager() {
		super();
	}

	public Manager(String managerName, String personality, IManagerPersistance managerDb) {
		super();
		this.managerName = managerName;
		this.personality = personality;
		this.managerDb = managerDb;
	}

	public Manager(String managerName, String personality) {
		super();
		this.managerName = managerName;
		this.personality = personality;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPersonality() {
		return personality;
	}

	public void setManagerPersonality(String personality) {
		this.personality = personality;
	}

	public boolean saveTeamGeneralManager(Manager manager, String teamName, String leagueName) throws IOException {
		boolean isSaved = Boolean.FALSE;
		try {
			isSaved = managerDb.persistManagerInfo(manager, teamName, leagueName);
		} catch (IOException e) {
			throw e;
		}
		return isSaved;
	}

	public String getMangerPersonalityByTeam(String teamName, League league) {
		String currentTeamName;
		String  personalityType = ManagerConstants.NORMAL.toString();
		List<Conference> conferenceList = league.getConferenceList();
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					currentTeamName = teamList.get(tIndex).getTeamName();
					if(currentTeamName.equals(teamName)){
						Manager teamManager = teamList.get(tIndex).getGeneralManager();
						personalityType = teamManager.getManagerPersonality();
					}
				}
			}
		}
		return personalityType;
	}

	public boolean saveManagerList(League league) throws IOException {
		boolean isSaved = Boolean.FALSE;
		String teamName = TeamManagementConstants.EMPTY.toString();
		try {
			List<Manager> list = league.getManagerList();
			for (int index = 0; index < list.size(); index++) {
				isSaved = managerDb.persistManagerInfo(list.get(index), teamName, league.getLeagueName());
			}
		} catch (IOException e) {
			throw e;
		}
		return isSaved;
	}

}
