package dpl.TeamManagement;

import java.util.List;

import dpl.DplConstants.TeamManagementConstants;

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
        boolean isSaved = Boolean.FALSE;
        isSaved = managerDb.persistManagerInfo(managerName, teamName, leagueName);
        return isSaved;
    }

    public boolean saveManagerList(League league) {
        boolean isSaved = Boolean.FALSE;
        String teamName = TeamManagementConstants.EMPTY.toString();
        List<Manager> list = league.getManagerList();
        for (int index = 0; index < list.size(); index++) {
            Manager manager = new Manager(list.get(index).getManagerName(), managerDb);
            isSaved = managerDb.persistManagerInfo(manager.getManagerName(), teamName, league.getLeagueName());
        }
        return isSaved;
    }

}
