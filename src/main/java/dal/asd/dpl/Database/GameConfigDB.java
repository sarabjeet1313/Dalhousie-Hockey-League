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

public class GameConfigDB implements IGameplayConfigPersistance{
	InvokeStoredProcedure invoke = null;
	
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
			invoke = new InvokeStoredProcedure("spLoadGameConfig(?)");
			invoke.setParameter(1, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				aging = new Aging(result.getInt("avgRetirementAge"), result.getInt("maxRetirementAge"));
				gameResolver = new GameResolver(result.getDouble("randomWinChance"));
				injury = new Injury(result.getDouble("randomInjuryChance"), result.getInt("injuryDaysLow"), result.getInt("injuryDaysHigh"));
				training = new Training(result.getInt("daysUntilStatIncreaseCheck"));
				trading = new Trading(result.getInt("lossPoint"), result.getDouble("randomTradeOfferChance"), result.getInt("maxPlayersPerTrade"), result.getDouble("randomAcceptanceChance"));
			}
			config = new GameplayConfig(aging, gameResolver, injury, training, trading);
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();	
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return config;
	}
	
	@Override
	public boolean persistGameConfig(GameplayConfig config, String leagueName) {
		boolean isPersisted = false;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure("spPersistGameplayConfig(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
				isPersisted = result.getBoolean("success");
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return isPersisted;
	}
}
	

