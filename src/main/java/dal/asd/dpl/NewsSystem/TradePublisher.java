package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class TradePublisher {

    private List<ITradeInfo> subscribers;

    public TradePublisher(){
        subscribers = new ArrayList<ITradeInfo>();
    }

    public void subscribe(ITradeInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(ITradeInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String fromTeam, String toTeam, String[][] playersTraded) {
        for(ITradeInfo subscriber : this.subscribers) {
            subscriber.updateTrade(fromTeam, toTeam, playersTraded);
        }
    }
}
