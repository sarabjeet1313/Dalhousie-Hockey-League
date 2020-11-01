package dal.asd.dpl.GameplayConfiguration;

public interface IGameplayConfigPersistance {
	public GameplayConfig loadGameplayConfigData(String leagueName);
	public boolean persistGameConfig(GameplayConfig config, String leagueName);
}
