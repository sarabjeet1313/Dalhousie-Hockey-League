package dal.asd.dpl.SimulationStateMachine;

import dal.asd.dpl.NewsSystem.INewsSubscriber;
import dal.asd.dpl.NewsSystem.INewsSubject;
import dal.asd.dpl.NewsSystem.NewsSystem;

import java.util.List;
import java.util.*;

public class Trading implements INewsSubject {

    private List <INewsSubscriber> subs = new ArrayList<>();
    public String newName;

    @Override
    public void subscribe(INewsSubscriber observer) {

        subs.add(observer);
    }

    @Override
    public void unSubscribe(INewsSubscriber observer) {
        subs.remove(observer);

    }

    @Override
    public void notifyNewsSystem() {
        for(INewsSubscriber ob  : subs)
        ob.updateTrades();

    }
    public void setName(String newName)
    {
        this.newName= newName;
        notifyNewsSystem();
    }
    public String getTemp(){
        return newName;
    }
}
