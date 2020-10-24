package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class RetirementPublisher {
	private List<IRetirement> subscribers;

	public RetirementPublisher(){
		subscribers = new ArrayList<IRetirement>();
	}

	public void subscribe(IRetirement subscriber) {
		this.subscribers.add(subscriber);
	}

	public void unsubscribe(IRetirement subscriber) {
		this.subscribers.remove(subscriber);
	}

	public void notify(String player, int age) {
		for(IRetirement subscriber : this.subscribers) {
			subscriber.updateRetirement(player, age);
		}
	}
}
