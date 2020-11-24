package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import com.google.gson.annotations.Expose;

public class GameResolver {
	@Expose (serialize = true, deserialize = true) double randomWinChance;

    public GameResolver(double randomWinChance) {
        super();
        this.randomWinChance = randomWinChance;
    }

    public double getRandomWinChance() {
        return randomWinChance;
    }
}
