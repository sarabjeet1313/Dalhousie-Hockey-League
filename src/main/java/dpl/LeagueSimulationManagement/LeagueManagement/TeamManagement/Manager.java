package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.sql.SQLException;
import java.util.List;

import dpl.SystemConfig;
import dpl.DplConstants.TeamManagementConstants;

public class Manager {

	private String managerName;
	private IManagerPersistance managerDb;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	
	public Manager() {
		super();
	}

	public Manager(String managerName, IManagerPersistance managerDb) {
		super();
		this.managerName = managerName;
		this.managerDb = managerDb;
	}

	public Manager(String managerName) {
		super();
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public boolean saveTeamGeneralManager(String managerName, String teamName, String leagueName) throws SQLException {
		boolean isSaved = Boolean.FALSE;
		try {
			isSaved = managerDb.persistManagerInfo(managerName, teamName, leagueName);
		} catch (SQLException e) {
			throw e;
		}
		return isSaved;
	}

	public boolean saveManagerList(League league) throws SQLException {
		boolean isSaved = Boolean.FALSE;
		String teamName = TeamManagementConstants.EMPTY.toString();
		try {
			List<Manager> list = league.getManagerList();
			for (int index = 0; index < list.size(); index++) {
				Manager manager = teamManagement.ManagerWithDbParameters(list.get(index).getManagerName(), managerDb);
				isSaved = managerDb.persistManagerInfo(manager.getManagerName(), teamName, league.getLeagueName());
			}
		} catch (SQLException e) {
			throw e;
		}
		return isSaved;
	}

}
