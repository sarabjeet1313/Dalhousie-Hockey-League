package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.NewsSystem.GamePlayedPublisher;
import dal.asd.dpl.NewsSystem.NewsSubscriber;
import dal.asd.dpl.TeamManagement.ITeamInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Team;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulateGameState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private StandingInfo standings;
    private ISchedule schedule;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private IUserOutput output;
    private GamePlayedPublisher gamePublisher;
    private ITeamInfo teamInfo;
    private double randomWinChance;


    public SimulateGameState(League leagueToSimulate, ISchedule schedule, StandingInfo standings, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "SimulateGame";
        this.leagueToSimulate = leagueToSimulate;
        this.standings = standings;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.randomWinChance = leagueToSimulate.getGameConfig().getGameResolver().getRandomWinChance();
        this.output = output;
        this.teamInfo = new Team();
        GamePlayedPublisher.getInstance().subscribe(new NewsSubscriber());
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "InjuryCheck";
    }

    public void doProcessing() {

        output.setOutput("Inside Match Simulation state");
        output.sendOutput();

        Map<String, List<Map<String, String>>> currentSchedule = schedule.getFinalSchedule();

        if(schedule.getSeasonType() == 0) {
            if (currentSchedule.containsKey(this.currentDate)) {
                List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);
                if(teamsCompetingList.size()>0) {
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

                        if(teamInfo.shouldReverseResult(randomWinChance)){
                            if(team1Strength <= team2Strength){
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                            }
                            else {
                                winningTeam = secondTeam;
                                losingTeam = firstTeam;
                            }
                        }
                        else {
                            if(team1Strength >= team2Strength){
                                winningTeam = firstTeam;
                                losingTeam = secondTeam;
                            }
                            else {
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
        else if(schedule.getSeasonType() == 1) {
            if (currentSchedule.containsKey(this.currentDate)) {
                List<Map<String, String>> teamsCompetingList = currentSchedule.get(this.currentDate);

                if(teamsCompetingList.size() > 0) {
                    Map<String, String> teamsCompeting = teamsCompetingList.get(0);
                    for (Map.Entry<String, String> entry : teamsCompeting.entrySet()) {
                        String firstTeam = entry.getKey();
                        String secondTeam = entry.getValue();

                        int team1Win = 1;
                        int team2Win = 0;
                        // best of seven series
                        for (int i = 0; i < 7; i++) {
                            if(team1Win >= 4 || team2Win >=4) {
                                break;
                            }
                            output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                            output.sendOutput();

                            String winningTeam = "";
                            String losingTeam = "";
                            double team1Strength = teamInfo.getTeamStrength(firstTeam, leagueToSimulate);
                            double team2Strength = teamInfo.getTeamStrength(secondTeam, leagueToSimulate);
                            if(teamInfo.shouldReverseResult(randomWinChance)){
                                if(team1Strength <= team2Strength){
                                    winningTeam = firstTeam;
                                    losingTeam = secondTeam;
                                    team1Win++;
                                }
                                else {
                                    winningTeam = secondTeam;
                                    losingTeam = firstTeam;
                                    team2Win++;
                                }
                            }
                            else {
                                if(team1Strength >= team2Strength){
                                    winningTeam = firstTeam;
                                    losingTeam = secondTeam;
                                    team1Win++;
                                }
                                else {
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
                            List<String> teams = schedule.getTeamsToBeScheduled();
                            teams.add(firstTeam);
                            schedule.setTeamsToBeScheduled(teams);

                            List<String> teamsScheduled = schedule.getTeamsScheduled();
                            teamsScheduled.remove(firstTeam);
                            teamsScheduled.remove(secondTeam);
                            schedule.setTeamsScheduled(teamsScheduled);

                            if (schedule.getTeamsScheduled().isEmpty()) {
                                if (schedule.getTeamsToBeScheduled().size() < 2) {
                                    utility.setSeasonOverStatus(true);
                                    utility.setSeasonWinner(schedule.getTeamsToBeScheduled().get(0));
                                    output.setOutput("Winner is : " + utility.getSeasonWinner());
                                    output.sendOutput();
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

                        if (team2Win > team1Win) {
                            List<String> teams = schedule.getTeamsToBeScheduled();
                            teams.add(secondTeam);
                            schedule.setTeamsToBeScheduled(teams);

                            List<String> teamsScheduled = schedule.getTeamsScheduled();
                            teamsScheduled.remove(firstTeam);
                            teamsScheduled.remove(secondTeam);
                            schedule.setTeamsScheduled(teamsScheduled);

                            if (schedule.getTeamsScheduled().isEmpty()) {
                                if (schedule.getTeamsToBeScheduled().size() < 2) {
                                    utility.setSeasonOverStatus(true);
                                    utility.setSeasonWinner(schedule.getTeamsToBeScheduled().get(0));
                                    output.setOutput("Winner is : " + utility.getSeasonWinner());
                                    output.sendOutput();
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
                    }
                    currentSchedule.remove(this.currentDate);
                    schedule.setFinalSchedule(currentSchedule);
                }
            }
        }
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
