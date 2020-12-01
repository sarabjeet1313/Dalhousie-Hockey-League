package dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class InjuryPublisher {
	private final List<IInjuryInfo> subscribers;
	private static InjuryPublisher instance;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

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
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
			output.sendOutput();

		}
	}
}
