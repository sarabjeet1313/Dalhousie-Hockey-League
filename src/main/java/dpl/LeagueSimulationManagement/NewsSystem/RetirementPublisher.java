package dpl.LeagueSimulationManagement.NewsSystem;

import java.util.ArrayList;
import java.util.List;

import dpl.DplConstants.NewsSystemConstants;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class RetirementPublisher {
    private final List<IRetirementInfo> subscribers;
    private static RetirementPublisher instance;
    private IUserOutput output;

    private RetirementPublisher() {
        subscribers = new ArrayList<>();
    }

    public static RetirementPublisher getInstance() {
        if (instance == null) {
            instance = new RetirementPublisher();
        }
        return instance;
    }


    public void subscribe(IRetirementInfo subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IRetirementInfo subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(String player, int age) {
        try {
            if (null == player) {
                throw new IllegalArgumentException();
            }
            for (IRetirementInfo subscriber : this.subscribers) {
                subscriber.updateRetirement(player, age);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
