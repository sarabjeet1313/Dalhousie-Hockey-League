package dal.asd.dpl.NewsSystem;

import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.NewsSystemUtil;

import java.util.ArrayList;
import java.util.List;

public class InjuryPublisher {
    private final List<IInjuryInfo> subscribers;
    private static InjuryPublisher instance;
    private IUserOutput output;

    private InjuryPublisher() {
        subscribers = new ArrayList<>();
    }

    public static InjuryPublisher getInstance() {
        if (instance == null) {
            instance = new InjuryPublisher();
        }
        return instance;
    }

    public void subscribe(IInjuryInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IInjuryInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String player, int daysInjured) {
        try {
            if (null == player) {
                throw new IllegalArgumentException();
            }
            for (IInjuryInfo subscriber : this.subscribers) {
                subscriber.updateInjuries(player, daysInjured);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemUtil.ARGUMENT_MESSAGE.toString());
            output.sendOutput();

        }
    }
}
