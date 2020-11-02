package dpl.GameplayConfiguration;

public interface IGameplayConfigPersistance {

    GameplayConfig loadGameplayConfigData(String leagueName);

    boolean persistGameConfig(GameplayConfig config, String leagueName);
}
