package dpl.LeagueSimulationManagement.TrophySystem.subscriber;

import dpl.LeagueSimulationManagement.TrophySystem.cache.TrophyHistoryCache;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

public class TrophySubscriber implements IPresidentsTrophy, ICalderMemorialTrophy, IJackAdamsAward, IMauriceRichardTrophy, IParticipationAward, IRobHawkeyMemorialCup, IVezinaTrophy {

    @Override
    public void updateTrophy(TrophyState trophy) {
        TrophyHistoryCache.getInstance().add(trophy);
    }
}
