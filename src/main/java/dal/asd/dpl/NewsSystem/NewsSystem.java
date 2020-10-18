package dal.asd.dpl.NewsSystem;

import dal.asd.dpl.SimulationStateMachine.Trading;

public class NewsSystem implements INewsSubscriber {

    String eventName;
    private final Trading trades;

    public NewsSystem(String eventName, Trading trades){
        this.eventName=eventName;
        this.trades=trades;
    }

    @Override
    public void updateTrades() {
        System.out.println(" Trade between "+eventName + " and " + trades.newName);
    }

    @Override
    public void updateGamesPlayed() {

    }

    @Override
    public void updateInjuries() {

    }

    @Override
    public void updateFreeAgency() {

    }

    @Override
    public void updateRetirement() {

    }
}
