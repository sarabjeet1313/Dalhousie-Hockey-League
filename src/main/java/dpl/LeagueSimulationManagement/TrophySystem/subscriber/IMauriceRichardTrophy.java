package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IMauriceRichardTrophy {
    void updateTrophy(TrophyState trophy);
}
