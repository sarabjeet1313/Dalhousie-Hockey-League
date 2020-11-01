package dal.asd.dpl.NewsSystem;

import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.NewsSystemUtil;

import java.util.ArrayList;
import java.util.List;

public class FreeAgencyPublisher {
    private final List<IFreeAgencyInfo> subscribers;
    private static FreeAgencyPublisher instance;
    private IUserOutput output;

    private FreeAgencyPublisher() {
        subscribers = new ArrayList<>();
    }

    public static FreeAgencyPublisher getInstance() {
        if (instance == null) {
            instance = new FreeAgencyPublisher();
        }
        return instance;
    }

    public void subscribe(IFreeAgencyInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IFreeAgencyInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String player, String hiredOrReleased) {
        try {
            if (null == player || null == hiredOrReleased) {
                throw new IllegalArgumentException();
            }
            for (IFreeAgencyInfo subscriber : this.subscribers) {
                subscriber.updateFreeAgency(player, hiredOrReleased);
            }
        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemUtil.ARGUMENT_MESSAGE.toString());
            output.sendOutput();
        }
    }
}