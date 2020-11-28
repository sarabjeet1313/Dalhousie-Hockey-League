package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.annotations.Expose;

import dpl.DplConstants.ManagerConstants;
import dpl.SystemConfig;
import dpl.DplConstants.TeamManagementConstants;

public class Manager {

	@Expose (serialize = true, deserialize = true) private String managerName;
	@Expose (serialize = true, deserialize = true) private String personality;
	private IManagerPersistance managerDb;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	
	public Manager() {
		super();
	}

	public Manager(String managerName, String personality,IManagerPersistance managerDb) {
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


	public boolean saveTeamGeneralManager(String managerName, String teamName, String leagueName)
			throws SQLException, IOException {
		boolean isSaved = Boolean.FALSE;
		try {
			isSaved = managerDb.persistManagerInfo(managerName, teamName, leagueName);
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return isSaved;
	}

	public boolean saveManagerList(League league) throws SQLException, IOException {
		boolean isSaved = Boolean.FALSE;
		String teamName = TeamManagementConstants.EMPTY.toString();
		try {
			List<Manager> list = league.getManagerList();
			for (int index = 0; index < list.size(); index++) {
				Manager manager = new Manager(list.get(index).getManagerName(), ManagerConstants.PERSONALITY.toString(),managerDb);
				isSaved = managerDb.persistManagerInfo(manager.getManagerName(), teamName, league.getLeagueName());
			}
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return isSaved;
	}

}
