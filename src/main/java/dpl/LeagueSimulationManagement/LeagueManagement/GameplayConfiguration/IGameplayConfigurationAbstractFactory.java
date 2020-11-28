package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

public interface IGameplayConfigurationAbstractFactory {

	public Aging Aging(int averageRetirementAge, int maximumAge, double statDecayChance);

	public GameplayConfig GameplayConfigWithDbParameters(IGameplayConfigPersistance configDb);

	public GameplayConfig GameplayConfigWithAllParameters(Aging aging, GameResolver gameResolver, Injury injury,
			Training training, Trading trading, IGameplayConfigPersistance configDb);

	public GameplayConfig GameplayConfigWithParameters(Aging aging, GameResolver gameResolver, Injury injury,
			Training training, Trading trading);

	public GameResolver GameResolver(double randomWinChance);

	public Injury Injury(double randomInjuryChance, int injuryDaysLow, int injuryDaysHigh);

	public Trading Trading(int lossPoint, double randomTradeOfferChance, int maxPlayersPerTrade,
			double randomAcceptanceChance);
	
	public Training Training(int daysUntilStatIncreaseCheck, int trackDays);
}
