package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IPresidentsTrophy {
    void updateTrophy(String teamWin, String teamLose);
}
