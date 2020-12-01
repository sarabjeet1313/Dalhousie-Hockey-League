package dpl.LeagueManagement.GameplayConfiguration;

import java.util.HashMap;

import com.google.gson.annotations.Expose;

public class Trading {
	@Expose(serialize = true, deserialize = true)
	int lossPoint;
	@Expose(serialize = true, deserialize = true)
	double randomTradeOfferChance;
	@Expose(serialize = true, deserialize = true)
	int maxPlayersPerTrade;
	@Expose(serialize = true, deserialize = true)
	double randomAcceptanceChance;
	@Expose(serialize = true, deserialize = true)
	HashMap<String, Double> gmTable;

	public Trading() {
		super();
	}

	public Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade, double randomAcceptanceChance,
			HashMap<String, Double> gmtable) {
		this.lossPoint = lossPoint;
		this.randomTradeOfferChance = randomTradeOfferChance;
		this.maxPlayersPerTrade = maxPlayersPerTrade;
		this.randomAcceptanceChance = randomAcceptanceChance;
		this.gmTable = gmtable;
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

	public HashMap<String, Double> getGmTable() {
		return gmTable;
	}

	public void setGmTable(HashMap<String, Double> gmTable) {
		this.gmTable = gmTable;
	}
}
