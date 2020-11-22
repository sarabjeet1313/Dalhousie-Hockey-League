package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public class GameplayConfig {
	private double penaltyChance = 0.45;
	private int checkingValueToPenalty = 10;
	private double shootingValueToGoal = 4.9;
	@Expose (serialize = true, deserialize = true) private Aging aging;
	@Expose (serialize = true, deserialize = true) private GameResolver gameResolver;
	@Expose (serialize = true, deserialize = true) private Injury injury;
	@Expose (serialize = true, deserialize = true) private Training training;
	@Expose (serialize = true, deserialize = true) private Trading trading;
	private IGameplayConfigPersistance configDb;

	public GameplayConfig(IGameplayConfigPersistance configDb) {
		this.configDb = configDb;
	}

	public GameplayConfig(Aging aging, GameResolver gameResolver, Injury injury, Training training, Trading trading,
			IGameplayConfigPersistance configDb) {
		this.aging = aging;
		this.gameResolver = gameResolver;
		this.injury = injury;
		this.training = training;
		this.trading = trading;
		this.configDb = configDb;
	}

	public GameplayConfig(Aging aging, GameResolver gameResolver, Injury injury, Training training, Trading trading) {
		this.aging = aging;
		this.gameResolver = gameResolver;
		this.injury = injury;
		this.training = training;
		this.trading = trading;
	}

	public Aging getAging() {
		return aging;
	}

	public void setAging(Aging aging) {
		this.aging = aging;
	}

	public GameResolver getGameResolver() {
		return gameResolver;
	}

	public void setGameResolver(GameResolver gameResolver) {
		this.gameResolver = gameResolver;
	}

	public Injury getInjury() {
		return injury;
	}

	public void setInjury(Injury injury) {
		this.injury = injury;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Trading getTrading() {
		return trading;
	}

	public void setTrading(Trading trading) {
		this.trading = trading;
	}

	public double getPenaltyChance() {
		return penaltyChance;
	}

	public double getShootingValue() {
		return shootingValueToGoal;
	}

	public int getCheckingValue() {
		return checkingValueToPenalty;
	}

	public boolean saveGameplayConfig(League league) throws SQLException, IOException {
		boolean isValid = Boolean.FALSE;
		try {
			isValid = configDb.persistGameConfig(league.getGameConfig(), league.getLeagueName());
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return isValid;
	}

	public GameplayConfig loadGameplayConfig(League league) throws SQLException, IOException {
		GameplayConfig config = null;
		try {
			config = configDb.loadGameplayConfigData(league.getLeagueName());
		} catch (SQLException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return config;
	}
}
