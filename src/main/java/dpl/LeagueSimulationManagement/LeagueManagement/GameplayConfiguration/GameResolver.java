package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

public class GameResolver {
    double randomWinChance;

    public GameResolver(double randomWinChance) {
        super();
        this.randomWinChance = randomWinChance;
    }

    public double getRandomWinChance() {
        return randomWinChance;
    }
}
