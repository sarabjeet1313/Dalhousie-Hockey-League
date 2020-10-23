package dal.asd.dpl.NewsSystem;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPublisher {
	protected List<Object> subscribers;
	public AbstractPublisher() {
		this.subscribers = new ArrayList<Object>();
	}
	
	public void subscribe(Object subscriber) {
		this.subscribers.add(subscriber);
	}
	
	public void unsubscribe(Object subscriber) {
		this.subscribers.remove(subscriber);
	}
}
