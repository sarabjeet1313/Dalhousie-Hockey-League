package dpl.LeagueSimulationManagement.TrophySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Publisher {
    protected Map<String, Object> dataMap;
    private List<ISubscriber> subscribers;

    protected Publisher(){
        subscribers = new ArrayList<>();
    }

    public void subscribe(ISubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(ISubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notifyAllObservers()
    {
        for(ISubscriber subscriber: subscribers){
            subscriber.update(dataMap);
        }
        dataMap.clear();
    }

    public Object getValue(String key){
        return dataMap.get(key);
    }

}
