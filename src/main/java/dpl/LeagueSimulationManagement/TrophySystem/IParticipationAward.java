package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public interface IParticipationAward {
    void updateTrophy(TrophyState trophy);
}
