package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.NewsSystem.GamePlayedPublisher;
import dpl.LeagueSimulationManagement.NewsSystem.NewsSubscriber;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class SimulateGameState implements ISimulationState {
    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private StandingInfo standings;
    private IStandingsPersistance standingsDb;
    private ISchedule schedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private String currentDate;
    private String endDate;
    private int season;
    private IUserOutput output;
    private ITeamInfo teamInfo;
    private double randomWinChance;
    private InjuryManagement injury;
    private Map<String, List<Map<String, String>>> currentSchedule;

    public SimulateGameState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb,
                             InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season, IUserOutput output) {
        this.stateName = StateConstants.SIMULATE_GAME_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.standingsDb = standingsDb;
        this.standings = new StandingInfo(leagueToSimulate, season, standingsDb);
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.endDate = endDate;
        this.season = season;
        this.currentSchedule = schedule.getFinalSchedule();
        this.injury = new InjuryManagement();
        this.randomWinChance = leagueToSimulate.getGameConfig().getGameResolver().getRandomWinChance();
        this.output = output;
        this.teamInfo = new Team();
    }

    static {
        GamePlayedPublisher.getInstance().subscribe(new NewsSubscriber());
    }

    public ISimulationState nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.INJURY_STATE;
        return new InjuryCheckState(leagueToSimulate, injury, schedule, context, utility, currentDate, endDate, season, output, standingsDb);
    }

    public void doProcessing() {
        output.setOutput("Inside Match Simulation state");
        output.sendOutput();

        if (schedule.getSeasonType() == ScheduleConstants.REGULAR_SEASON) {
            simulateRegularMatches();
        } else if (schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON) {
            simulatePlayoffMatches();
        }
    }

    private void simulateRegularMatches() {
        if (currentSchedule.containsKey(this.currentDate)) {
            List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
            if (teamsCompetingList.size() > 0) {
                Map<String, String> teamsCompeting = teamsCompetingList.get(0);
                for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
                    String firstTeam = entry.getKey();
                    String secondTeam = entry.getValue();

                    output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                    output.sendOutput();

                    String winningTeam = "";
                    String losingTeam = "";
                    double team1Strength = teamInfo.getTeamStrength(firstTeam, leagueToSimulate);
                    double team2Strength = teamInfo.getTeamStrength(secondTeam, leagueToSimulate);

                    if (teamInfo.shouldReverseResult(randomWinChance)) {
                        if (team1Strength <= team2Strength) {
                            winningTeam = firstTeam;
                            losingTeam = secondTeam;
                        } else {
                            winningTeam = secondTeam;
                            losingTeam = firstTeam;
                        }
                    } else {
                        if (team1Strength >= team2Strength) {
                            winningTeam = firstTeam;
                            losingTeam = secondTeam;
                        } else {
                            winningTeam = secondTeam;
                            losingTeam = firstTeam;
                        }
                    }

                    GamePlayedPublisher.getInstance().notify(winningTeam, losingTeam, currentDate);
                    standings.updateTeamWinMap(winningTeam);
                    standings.updateTeamLoseMap(losingTeam);
                }
                teamsCompetingList.remove(0);
                currentSchedule.put(this.currentDate, teamsCompetingList);
                schedule.setFinalSchedule(currentSchedule);
            }
        }
    }

    private void simulatePlayoffMatches() {
        if (currentSchedule.containsKey(this.currentDate)) {
            List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
            if (teamsCompetingList.size() > 0) {
                Map<String, String> teamsCompeting = teamsCompetingList.get(0);
                for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
                    String firstTeam = entry.getKey();
                    String secondTeam = entry.getValue();

                    int team1Win = 0;
                    int team2Win = 0;
                    for (int index = 0; index < 7; index++) {
                        if (team1Win >= 4 || team2Win >= 4) {
                            break;
                        }
                        output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                        output.sendOutput();

                        String winningTeam = "";
                        String losingTeam = "";
                        double team1Strength = teamInfo.getTeamStrength(firstTeam, leagueToSimulate);
                        double team2Strength = teamInfo.getTeamStrength(secondTeam, leagueToSimulate);
                        if (teamInfo.shouldReverseResult(randomWinChance)) {
                            if (team1Strength <= team2Strength) {
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                                team1Win++;
                            } else {
                                winningTeam = secondTeam;
                                losingTeam = firstTeam;
                                team2Win++;
                            }
                        } else {
                            if (team1Strength >= team2Strength) {
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                                team1Win++;
                            } else {
                                winningTeam = secondTeam;
                                losingTeam = firstTeam;
                                team2Win++;
                            }
                        }
                        GamePlayedPublisher.getInstance().notify(winningTeam, losingTeam, currentDate);
                        standings.updateTeamWinMap(winningTeam);
                        standings.updateTeamLoseMap(losingTeam);
                    }

                    if (team1Win > team2Win) {
                        generateNextRoundSchedule(firstTeam, secondTeam);
                    }

                    if (team2Win > team1Win) {
                        generateNextRoundSchedule(secondTeam, firstTeam);
                    }
                }
                schedule.setFinalSchedule(currentSchedule);
            }
        }
    }

    private void generateNextRoundSchedule(String winningTeam, String losingTeam) {
        List<String> teams = schedule.getTeamsToBeScheduled();
        teams.add(winningTeam);
        schedule.setTeamsToBeScheduled(teams);

        List<String> teamsScheduled = schedule.getTeamsScheduled();
        teamsScheduled.remove(winningTeam);
        teamsScheduled.remove(losingTeam);
        schedule.setTeamsScheduled(teamsScheduled);

        if (schedule.getTeamsScheduled().isEmpty()) {
            if (schedule.getTeamsToBeScheduled().size() < 2) {
                utility.setSeasonOverStatus(true);
                utility.setSeasonWinner(schedule.getTeamsToBeScheduled().get(0));
                utility.setLastSeasonDay(this.currentDate);
            } else {
                schedule.generateScheduleOnTheFly(schedule.getTeamsToBeScheduled(), this.currentDate);
                List<String> teamsAlreadyScheduled = new ArrayList<String>(schedule.getTeamsToBeScheduled());
                schedule.setTeamsScheduled(teamsAlreadyScheduled);
                List<String> clearTeams = new ArrayList<>();
                schedule.setTeamsToBeScheduled(clearTeams);
            }
        }
    }

    public boolean shouldContinue() {
        return true;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
