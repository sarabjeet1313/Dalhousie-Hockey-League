package dpl.TeamManagementTest;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRosterManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.RosterManagement;
import dpl.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

public class RosterManagementTest {

    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
            .getTeamManagementAbstractFactory();
    League leagueData = new LeagueObjectTestData().getLeagueData();
    IRosterManagement irm = teamManagement.RosterManagement();
    @Test
    public void checkRosterTest(){
        Assert.assertEquals(Boolean.FALSE, irm.checkRoster("Boston",leagueData));
    }

    @Test
    public void updateActiveStatusTest(){

        Assert.assertEquals(Boolean.TRUE, irm.updateActiveStatus("Boston", leagueData));
    }

    @Test
    public void updateLeagueActiveStatusTest(){
        Assert.assertEquals("Dalhousie Hockey League", irm.updateLeagueActiveStatus(leagueData).getLeagueName());
    }
}

