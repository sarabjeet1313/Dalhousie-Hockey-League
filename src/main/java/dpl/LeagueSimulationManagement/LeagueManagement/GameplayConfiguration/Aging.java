package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import com.google.gson.annotations.Expose;

public class Aging {
    
	@Expose (serialize = true, deserialize = true) int averageRetirementAge;
	@Expose (serialize = true, deserialize = true) int maximumAge;

    public Aging(int averageRetirementAge, int maximumAge) {
        this.averageRetirementAge = averageRetirementAge;
        this.maximumAge = maximumAge;
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

}
