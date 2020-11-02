package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.GameConfigConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.GameplayConfiguration.Aging;
import dpl.GameplayConfiguration.GameResolver;
import dpl.GameplayConfiguration.GameplayConfig;
import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.GameplayConfiguration.Injury;
import dpl.GameplayConfiguration.Trading;
import dpl.GameplayConfiguration.Training;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class GameConfigDB implements IGameplayConfigPersistance {
    InvokeStoredProcedure invoke = null;
    IUserOutput output = new CmdUserOutput();

    @Override
    public GameplayConfig loadGameplayConfigData(String leagueName) {
        GameplayConfig config = null;
        Aging aging = null;
        GameResolver gameResolver = null;
        Injury injury = null;
        Training training = null;
        Trading trading = null;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.LOAD_GAMECONFIG.getSpString());
            invoke.setParameter(1, leagueName);
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                aging = new Aging(result.getInt(GameConfigConstants.AVG_RETIREMENT_AGE.toString()),
                        result.getInt(GameConfigConstants.MAX_RETIREMENT_AGE.toString()));
                gameResolver = new GameResolver(result.getDouble(GameConfigConstants.RANDOM_WIN_CHANCE.toString()));
                injury = new Injury(result.getDouble(GameConfigConstants.RANDOM_INJURY_CHANCE.toString()),
                        result.getInt(GameConfigConstants.INJURY_DAYS_LOW.toString()),
                        result.getInt(GameConfigConstants.INJURY_DAYS_HIGH.toString()));
                training = new Training(result.getInt(GameConfigConstants.STAT_INCREASE_CHECK.toString()),
                        result.getInt(GameConfigConstants.STAT_INCREASE_CHECK.toString()));
                trading = new Trading(result.getInt(GameConfigConstants.LOSS_POINT.toString()),
                        result.getDouble(GameConfigConstants.RANDOM_TRADE_OFFER_CHANCE.toString()),
                        result.getInt(GameConfigConstants.MAX_PLAYERS_PER_TRADE.toString()),
                        result.getDouble(GameConfigConstants.RANDOM_ACCEPTANCE_CHANCE.toString()));
            }
            config = new GameplayConfig(aging, gameResolver, injury, training, trading);
        } catch (SQLException e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
        return config;
    }

    @Override
    public boolean persistGameConfig(GameplayConfig config, String leagueName) {
        boolean isPersisted = Boolean.FALSE;
        ResultSet result;
        try {
            invoke = new InvokeStoredProcedure(StoredProcedureConstants.PERSIST_GAMECONFIG.getSpString());
            invoke.setParameter(1, leagueName);
            invoke.setParameter(2, config.getAging().getAverageRetirementAge());
            invoke.setParameter(3, config.getAging().getMaximumAge());
            invoke.setParameter(4, config.getGameResolver().getRandomWinChance());
            invoke.setParameter(5, config.getInjury().getRandomInjuryChance());
            invoke.setParameter(6, config.getInjury().getInjuryDaysLow());
            invoke.setParameter(7, config.getInjury().getInjuryDaysHigh());
            invoke.setParameter(8, config.getTraining().getDaysUntilStatIncreaseCheck());
            invoke.setParameter(9, config.getTrading().getLossPoint());
            invoke.setParameter(10, config.getTrading().getRandomTradeOfferChance());
            invoke.setParameter(11, config.getTrading().getMaxPlayersPerTrade());
            invoke.setParameter(12, config.getTrading().getRandomAcceptanceChance());
            result = invoke.executeQueryWithResults();
            while (result.next()) {
                isPersisted = result.getBoolean(GameConfigConstants.SUCCESS.toString());
            }
        } catch (Exception e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        } finally {
            try {
                invoke.closeConnection();
            } catch (SQLException e) {
                output.setOutput(e.getMessage());
                output.sendOutput();
            }
        }
        return isPersisted;
    }
}