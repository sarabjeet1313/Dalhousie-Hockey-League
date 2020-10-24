package dal.asd.dpl.GameplayConfiguration;

public class GameResolver {
	double randomWinChance;

	public GameResolver(double randomWinChance) {
		super();
		this.randomWinChance = randomWinChance;
	}

	public double getRandomWinChance() {
		return randomWinChance;
	}

	public void setRandomWinChance(double randomWinChance) {
		this.randomWinChance = randomWinChance;
	}
	
}
