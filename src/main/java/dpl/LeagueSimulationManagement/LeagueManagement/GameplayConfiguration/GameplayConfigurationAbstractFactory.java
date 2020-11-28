package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

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
	public GameplayConfig GameplayConfigWithAllParameters(Aging aging, GameResolver gameResolver, Injury injury,
			Training training, Trading trading, IGameplayConfigPersistance configDb) {
		return new GameplayConfig(aging, gameResolver, injury, training, trading, configDb);
	}

	@Override
	public GameplayConfig GameplayConfigWithParameters(Aging aging, GameResolver gameResolver, Injury injury,
			Training training, Trading trading) {
		return new GameplayConfig(aging, gameResolver, injury, training, trading);
	}

	@Override
	public GameResolver GameResolver(double randomWinChance) {
		return new GameResolver(randomWinChance);
	}

	@Override
	public Injury Injury(double randomInjuryChance, int injuryDaysLow, int injuryDaysHigh) {
		return new Injury(randomInjuryChance, injuryDaysLow, injuryDaysHigh);
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

}
