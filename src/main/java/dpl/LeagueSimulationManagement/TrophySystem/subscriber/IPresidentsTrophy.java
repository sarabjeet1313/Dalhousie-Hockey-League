package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IPresidentsTrophy {
    void updateTrophy(TrophyState trophy);
}
