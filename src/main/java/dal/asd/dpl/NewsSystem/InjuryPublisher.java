package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class InjuryPublisher {

	private List<IInjury> subscribers;

	public InjuryPublisher(){
		subscribers = new ArrayList<IInjury>();
	}

	public void subscribe(IInjury subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IInjury subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String player, int daysInjured) {
		for(IInjury subscriber : this.subscribers) {
			subscriber.updateInjuries(player, daysInjured);
		}
	}
}
