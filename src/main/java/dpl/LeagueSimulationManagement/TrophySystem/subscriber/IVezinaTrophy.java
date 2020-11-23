package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IVezinaTrophy {
    void updateTrophy(TrophyState trophy);
}
