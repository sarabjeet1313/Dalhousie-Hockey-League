package dpl.LeagueManagementTest.TeamManagementTest;

import dpl.LeagueManagement.TeamManagement.IRosterManagement;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

public class RosterManagementTest {

    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
            .getTeamManagementAbstractFactory();
    League leagueData = new LeagueObjectTestData().getLeagueData();
    IRosterManagement irm = teamManagement.RosterManagement();

    @Test
    public void checkRosterTest() {
        Assert.assertEquals(Boolean.FALSE, irm.checkRoster("Boston", leagueData));
    }

    @Test
    public void updateActiveStatusTest() {

        Assert.assertEquals(Boolean.TRUE, irm.updateActiveStatus("Boston", leagueData));
    }

    @Test
    public void updateLeagueActiveStatusTest() {
        Assert.assertEquals("Dalhousie Hockey League", irm.updateLeagueActiveStatus(leagueData).getLeagueName());
    }

    @Test
    public void balanceOutRoster() {

        Assert.assertEquals("Dalhousie Hockey League", irm.balanceOutRoster(leagueData).getLeagueName());
    }

    @Test
    public void balanceOutSingleRosterTest() {
        Assert.assertEquals(Boolean.TRUE, irm.balanceOutSingleRoster("Boston", leagueData));
    }

}

