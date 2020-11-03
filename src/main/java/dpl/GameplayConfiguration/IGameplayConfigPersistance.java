package dpl.GameplayConfiguration;

import java.sql.SQLException;

public interface IGameplayConfigPersistance {

    GameplayConfig loadGameplayConfigData(String leagueName) throws SQLException ;

    boolean persistGameConfig(GameplayConfig config, String leagueName)throws SQLException ;
}
