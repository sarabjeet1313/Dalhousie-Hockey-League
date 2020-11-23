package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.TrophySystem.data.TrophyState;

public interface IParticipationAward {
    void updateTrophy(TrophyState trophy);
}
