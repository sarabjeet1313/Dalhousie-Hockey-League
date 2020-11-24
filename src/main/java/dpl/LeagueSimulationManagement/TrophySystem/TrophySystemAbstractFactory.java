package dpl.LeagueSimulationManagement.TrophySystem;

public class TrophySystemAbstractFactory implements ITrophySystemAbstractFactory {

    @Override
    public TrophySubscriber TrophySubscriber() {
        return new TrophySubscriber();
    }
}
