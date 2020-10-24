package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class TradePublisher {

    private List<ITrade> subscribers;

    public TradePublisher(){
        subscribers = new ArrayList<ITrade>();
    }

    public void subscribe(ITrade subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(ITrade subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String fromTeam, String toTeam, String[][] playersTraded) {
        for(ITrade subscriber : this.subscribers) {
            subscriber.updateTrade(fromTeam, toTeam, playersTraded);
        }
    }
}
