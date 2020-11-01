package dal.asd.dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.asd.dpl.GameplayConfiguration.Aging;
import dal.asd.dpl.GameplayConfiguration.GameResolver;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.GameplayConfiguration.Injury;
import dal.asd.dpl.GameplayConfiguration.Trading;
import dal.asd.dpl.GameplayConfiguration.Training;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.GameConfigUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;

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
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.LOAD_GAMECONFIG.getSpString());
			invoke.setParameter(1, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				aging = new Aging(result.getInt(GameConfigUtil.AVG_RETIREMENT_AGE.toString()),
						result.getInt(GameConfigUtil.MAX_RETIREMENT_AGE.toString()));
				gameResolver = new GameResolver(result.getDouble(GameConfigUtil.RANDOM_WIN_CHANCE.toString()));
				injury = new Injury(result.getDouble(GameConfigUtil.RANDOM_INGURY_CHANCE.toString()),
						result.getInt(GameConfigUtil.INJURY_DAYS_LOW.toString()),
						result.getInt(GameConfigUtil.INJURY_DAYS_HIGH.toString()));
				training = new Training(result.getInt(GameConfigUtil.STAT_INCREASE_CHECK.toString()),
						result.getInt(GameConfigUtil.STAT_INCREASE_CHECK.toString()));
				trading = new Trading(result.getInt(GameConfigUtil.LOSS_POINT.toString()),
						result.getDouble(GameConfigUtil.RANDOM_TRADE_OFFER_CHANCE.toString()),
						result.getInt(GameConfigUtil.MAX_PLAYERS_PER_TRADE.toString()),
						result.getDouble(GameConfigUtil.RANDOM_ACCEPTANCE_CHANCE.toString()));
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
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.PERSIST_GAMECONFIG.getSpString());
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
				isPersisted = result.getBoolean(GameConfigUtil.SUCCESS.toString());
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
