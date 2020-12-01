package dpl.LeagueManagement.GameplayConfiguration;

import com.google.gson.annotations.Expose;

public class Aging {

	@Expose(serialize = true, deserialize = true)
	int averageRetirementAge;
	@Expose(serialize = true, deserialize = true)
	int maximumAge;
	@Expose(serialize = true, deserialize = true)
	double statDecayChance;

	public Aging(int averageRetirementAge, int maximumAge, double statDecayChance) {
		this.averageRetirementAge = averageRetirementAge;
		this.maximumAge = maximumAge;
		this.statDecayChance = statDecayChance;
	}

	public int getAverageRetirementAge() {
		return averageRetirementAge;
	}

	public void setAverageRetirementAge(int averageRetirementAge) {
		this.averageRetirementAge = averageRetirementAge;
	}

	public int getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(int maximumAge) {
		this.maximumAge = maximumAge;
	}

	public double getStatDecayChance() {
		return statDecayChance;
	}

	public void setStatDecayChance(double statDecayChance) {
		this.statDecayChance = statDecayChance;
	}

}
