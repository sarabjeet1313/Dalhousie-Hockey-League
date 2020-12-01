package dpl.LeagueManagement.TrophySystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Subject {
	private Map<String, Object> keyValuePairs;
	private List<IObserver> observers;

	public Subject() {
		observers = new ArrayList<>();
		keyValuePairs = new HashMap<>();
	}

	public void attach(IObserver observer) {
		this.observers.add(observer);
	}

	public void detach(IObserver observer) {
		this.observers.remove(observer);
	}

	public void notifyAllObservers() {
		for (IObserver observer : observers) {
			observer.update(this);
		}
		keyValuePairs.clear();
	}

	public void setValue(String key, Object o) {
		keyValuePairs.put(key, o);
	}

	public Object getValue(String key) {
		return keyValuePairs.get(key);
	}
}
