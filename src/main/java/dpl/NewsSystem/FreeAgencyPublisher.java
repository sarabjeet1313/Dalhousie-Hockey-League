package dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class FreeAgencyPublisher {
	private final List<IFreeAgencyInfo> subscribers;
	private static FreeAgencyPublisher instance;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

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
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(e + NewsSystemConstants.ARGUMENT_MESSAGE.toString());
			output.sendOutput();
		}
	}
}