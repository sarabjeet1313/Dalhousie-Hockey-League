package dpl.LeagueManagement.GameplayConfiguration;

import java.util.HashMap;

public interface IGameplayConfigurationAbstractFactory {

	public Aging Aging(int averageRetirementAge, int maximumAge, double statDecayChance);

	public GameplayConfig GameplayConfigWithDbParameters(IGameplayConfigPersistance configDb);

	public GameplayConfig GameplayConfigWithAllParameters(Aging aging, Injury injury, Training training,
			Trading trading, IGameplayConfigPersistance configDb);

	public GameplayConfig GameplayConfigWithParameters(Aging aging, Injury injury, Training training, Trading trading);

	public Injury Injury(double randomInjuryChance, int injuryDaysLow, int injuryDaysHigh);

	public Trading Trading();

	public Trading Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade,
			double randomAcceptanceChance, HashMap<String, Double> gmTable);

	public Training Training(int daysUntilStatIncreaseCheck, int trackDays);

	public Training Training();
}
