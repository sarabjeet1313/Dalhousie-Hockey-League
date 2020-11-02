package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.DplConstants.StoredProcedureConstants;
import dpl.Standings.IStandingsPersistance;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class StandingsDataDb implements IStandingsPersistance {
    InvokeStoredProcedure invoke = null;
    private int season;
    IUserOutput output = new CmdUserOutput();

    public void setSeason(int currentSeason) {
        this.season = currentSeason;
    }

    public void updateStandingsWin(String teamName) {
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.UPDATE_TEAM_WIN.getSpString());
            invoke.setParameter(1, this.season);
            invoke.setParameter(2, teamName);
            invoke.executeQueryWithResults();
        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
    }

    public void updateStandingsLosses(String teamName) {
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.UPDATE_TEAM_LOSS.getSpString());
            invoke.setParameter(1, this.season);
            invoke.setParameter(2, teamName);
            invoke.executeQueryWithResults();
        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
    }

    public boolean insertToStandings(String leagueName, String conferenceName, String divisionName, String teamName) {

        ResultSet result;
        boolean isInserted = false;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.INSERT_TO_STANDINGS.getSpString());
            invoke.setParameter(1, this.season);
            invoke.setParameter(2, leagueName);
            invoke.setParameter(3, conferenceName);
            invoke.setParameter(4, divisionName);
            invoke.setParameter(5, teamName);
            invoke.setParameter(6, 0);
            invoke.setParameter(7, 0);
            invoke.setParameter(8, 0);
            invoke.setParameter(9, 0);
            invoke.setParameter(10, 0);
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                isInserted = result.getBoolean("success");
            }
        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
        return isInserted;
    }

    public List<String> getTop4TeamsFromStandings(String divisionName) {
        ResultSet result;
        List<Integer> teamIds = getTopSeededTeamIds(divisionName);
        List<String> teamList = new ArrayList<>();

        for (int id : teamIds) {
            try {
                invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_TEAM_NAME.getSpString());
                invoke.setParameter(1, id);
                result = invoke.executeQueryWithResults();
                while (result.next()) {
                    teamList.add(result.getString("teamName"));
                }
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            } finally {
                try {
                    invoke.closeConnection();
                } catch (SQLException e) {
                    output.setOutput(e.getMessage());
                    output.sendOutput();
                }
            }
        }
        return teamList;

    }

    private List<Integer> getTopSeededTeamIds(String divisionName) {
        ResultSet result;
        List<Integer> teamIdList = new ArrayList<>();
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.GET_TOP_TEAMS.getSpString());
            invoke.setParameter(1, this.season);
            invoke.setParameter(2, divisionName);
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                teamIdList.add(result.getInt("teamIdStanding"));
            }
            return teamIdList;
        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
        return teamIdList;
    }
}