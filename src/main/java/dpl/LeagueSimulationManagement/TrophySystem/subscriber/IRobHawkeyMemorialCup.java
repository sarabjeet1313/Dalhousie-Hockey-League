package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IRobHawkeyMemorialCup {
    void updateTrophy(TrophyState trophy);
}
