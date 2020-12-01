package dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class RetirementPublisher {
	private final List<IRetirementInfo> subscribers;
	private static RetirementPublisher instance;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

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
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
			output.sendOutput();
		}
	}
}
