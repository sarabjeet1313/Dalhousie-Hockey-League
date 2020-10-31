package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public class RetirementPublisher {
	private List<IRetirementInfo> subscribers;
	private static RetirementPublisher instance;

	private RetirementPublisher(){
		subscribers = new ArrayList<IRetirementInfo>();
	}

	public static RetirementPublisher getInstance(){
		if(instance == null){
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
		for(IRetirementInfo subscriber : this.subscribers) {
			subscriber.updateRetirement(player, age);
		}
	}
}
