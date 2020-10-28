package dal.asd.dpl.NewsSystem;

import dal.asd.dpl.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class InjuryPublisher {

	private List<IInjuryInfo> subscribers;

	public InjuryPublisher(){
		subscribers = new ArrayList<IInjuryInfo>();
	}

	public void subscribe(IInjuryInfo subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IInjuryInfo subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String player, int daysInjured) {
		for(IInjuryInfo subscriber : this.subscribers) {
			subscriber.updateInjuries(player, daysInjured);
		}
	}
}
