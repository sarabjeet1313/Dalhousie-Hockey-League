package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulateGameState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private Leagues leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private IUserOutput output;


    public SimulateGameState(Leagues leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "SimulatePlayoffGame";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;

      //  doProcessing();
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "InjuryCheck";
        context.setState(new InjuryCheckState(leagueToSimulate, schedule, context, utility, currentDate, output));
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
                        // TODO Satya's method call.
                        // TODO update dB with the result.
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
                            output.setOutput("Match is going on between " + firstTeam + " vs " + secondTeam);
                            output.sendOutput();
                            // TODO Satya's method call.
                            // TODO handle the individual wins
                            // TODO update dB with the result.
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
//                teamsCompetingList.remove(0);
//                currentSchedule.put(this.currentDate, teamsCompetingList);
                    schedule.setFinalSchedule(currentSchedule);
                }
            }
        }
     //   nextState(this.context);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
