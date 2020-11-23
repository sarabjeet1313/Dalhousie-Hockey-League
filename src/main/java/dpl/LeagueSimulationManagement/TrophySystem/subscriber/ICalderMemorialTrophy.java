package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface ICalderMemorialTrophy {
    void updateTrophy(TrophyState trophy);
}
