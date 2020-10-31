package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class InjuryPublisher {
	private final List<IInjuryInfo> subscribers;
	private static InjuryPublisher instance;

	private InjuryPublisher(){
		subscribers = new ArrayList<>();
	}

	public List<IInjuryInfo> getSubscribers(){
		return subscribers;
	}

	public static InjuryPublisher getInstance(){
		if(instance == null){
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
		for(IInjuryInfo subscriber : this.subscribers) {
			subscriber.updateInjuries(player, daysInjured);
		}
	}
}
