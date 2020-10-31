package dal.asd.dpl.TeamManagement;

import java.util.List;

public class Manager {
	private String managerName;
	private IManagerPersistance managerDb;

	public Manager(String managerName, IManagerPersistance managerDb) {
		this.managerName = managerName;
		this.managerDb = managerDb;
	}

	public Manager(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public boolean saveTeamGeneralManager(String managerName, String teamName, String leagueName) {
		boolean isSaved = false;
		isSaved = managerDb.persisitManagerInfo(managerName, teamName, leagueName);
		return isSaved;
	}

	public boolean saveManagerList(League league) {
		boolean isSaved = false;
		String teamName = "Empty";
		List<Manager> list = league.getManagerList();
		for (int index = 0; index < list.size(); index++) {
			Manager manager = new Manager(list.get(index).getManagerName(), managerDb);
			isSaved = managerDb.persisitManagerInfo(manager.getManagerName(), teamName, league.getLeagueName());
		}
		return isSaved;
	}

}
