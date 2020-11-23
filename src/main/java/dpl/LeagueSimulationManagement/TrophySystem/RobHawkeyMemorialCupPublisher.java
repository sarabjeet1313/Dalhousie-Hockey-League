package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import java.util.ArrayList;
import java.util.List;

public class RobHawkeyMemorialCupPublisher {
    private final List<IRobHawkeyMemorialCup> subscribers;
    private static RobHawkeyMemorialCupPublisher instance;
    private IUserOutput output;

    private RobHawkeyMemorialCupPublisher() {
        subscribers = new ArrayList<>();
    }

    public static RobHawkeyMemorialCupPublisher getInstance() {
        if (instance == null) {
            instance = new RobHawkeyMemorialCupPublisher();
        }
        return instance;
    }

    public void subscribe(IRobHawkeyMemorialCup subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(IRobHawkeyMemorialCup subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notify(TrophyState trophy) {
        try {
            if (null == trophy || null == trophy.getAwardedTeam()) {
                throw new IllegalArgumentException();
            }
            for (IRobHawkeyMemorialCup subscriber : this.subscribers) {
                trophy.setTrophyName(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP.toString());
                subscriber.updateTrophy(trophy);
            }

        } catch (IllegalArgumentException e) {
            output.setOutput(e + TrophySystemConstants.ARGUMENT_ERROR_MESSAGE.toString());
            output.sendOutput();
        }
    }
}
