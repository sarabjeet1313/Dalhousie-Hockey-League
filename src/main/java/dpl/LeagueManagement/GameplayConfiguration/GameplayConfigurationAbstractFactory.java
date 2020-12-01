package dpl.LeagueManagement.GameplayConfiguration;

import java.util.HashMap;

public class GameplayConfigurationAbstractFactory implements IGameplayConfigurationAbstractFactory {

	@Override
	public Aging Aging(int averageRetirementAge, int maximumAge, double statDecayChance) {
		return new Aging(averageRetirementAge, maximumAge, statDecayChance);
	}

	@Override
	public GameplayConfig GameplayConfigWithDbParameters(IGameplayConfigPersistance configDb) {
		return new GameplayConfig(configDb);
	}

	@Override
	public GameplayConfig GameplayConfigWithAllParameters(Aging aging, Injury injury, Training training,
			Trading trading, IGameplayConfigPersistance configDb) {
		return new GameplayConfig(aging, injury, training, trading, configDb);
	}

	@Override
	public GameplayConfig GameplayConfigWithParameters(Aging aging, Injury injury, Training training, Trading trading) {
		return new GameplayConfig(aging, injury, training, trading);
	}

	@Override
	public Injury Injury(double randomInjuryChance, int injuryDaysLow, int injuryDaysHigh) {
		return new Injury(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
	}

	@Override
	public Trading Trading() {
		return new Trading();
	}

	@Override
	public Trading Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade,
			double randomAcceptanceChance, HashMap<String, Double> gmTable) {
		return new Trading(lossPoint, randomTradeOfferChance, maxPlayersPerTrade, randomAcceptanceChance, gmTable);
	}

	@Override
	public Training Training(int daysUntilStatIncreaseCheck, int trackDays) {
		return new Training(daysUntilStatIncreaseCheck, trackDays);
	}

	@Override
	public dpl.LeagueManagement.GameplayConfiguration.Training Training() {
		return new Training();
	}
}
