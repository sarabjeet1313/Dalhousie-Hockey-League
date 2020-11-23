package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class ParticipationAwardPublisher {
    private final List<IParticipationAward> subscribers;
    private static ParticipationAwardPublisher instance;
    private IUserOutput output;

    private ParticipationAwardPublisher() {
        subscribers = new ArrayList<>();
    }

    public static ParticipationAwardPublisher getInstance() {
        if (instance == null) {
            instance = new ParticipationAwardPublisher();
        }
        return instance;
    }

    public void subscribe(IParticipationAward subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IParticipationAward subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IParticipationAward subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.PARTICIPATION_AWARD.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
