package dal.asd.dpl.GameplayConfiguration;

public class Trading {
	int lossPoint;
	double randomTradeOfferChance;
	int maxPlayersPerTrade;
	double randomAcceptanceChance;

	public Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade,
			double randomAcceptanceChance) {
		this.lossPoint = lossPoint;
		this.randomTradeOfferChance = randomTradeOfferChance;
		this.maxPlayersPerTrade = maxPlayersPerTrade;
		this.randomAcceptanceChance = randomAcceptanceChance;
	}

	public int getLossPoint() {
		return lossPoint;
	}

	public void setLossPoint(int lossPoint) {
		this.lossPoint = lossPoint;
	}

	public double getRandomTradeOfferChance() {
		return randomTradeOfferChance;
	}

	public void setRandomTradeOfferChance(double randomTradeOfferChance) {
		this.randomTradeOfferChance = randomTradeOfferChance;
	}

	public int getMaxPlayersPerTrade() {
		return maxPlayersPerTrade;
	}

	public void setMaxPlayersPerTrade(int maxPlayersPerTrade) {
		this.maxPlayersPerTrade = maxPlayersPerTrade;
	}

	public double getRandomAcceptanceChance() {
		return randomAcceptanceChance;
	}

	public void setRandomAcceptanceChance(double randomAcceptanceChance) {
		this.randomAcceptanceChance = randomAcceptanceChance;
	}
}
