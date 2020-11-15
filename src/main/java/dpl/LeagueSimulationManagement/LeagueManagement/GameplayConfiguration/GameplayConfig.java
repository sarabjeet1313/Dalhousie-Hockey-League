package dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration;

import java.sql.SQLException;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public class GameplayConfig {
	
	private Aging aging;
	private GameResolver gameResolver;
	private Injury injury;
	private Training training;
	private Trading trading;
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

	public boolean saveGameplayConfig(League league) throws SQLException {
		boolean isValid = Boolean.FALSE;
		try {
			isValid = configDb.persistGameConfig(league.getGameConfig(), league.getLeagueName());
		} catch (SQLException e) {
			throw e;
		}
		return isValid;
	}

	public GameplayConfig loadGameplayConfig(League league) throws SQLException {
		GameplayConfig config = null;
		try {
			config = configDb.loadGameplayConfigData(league.getLeagueName());
		} catch (SQLException e) {
			throw e;
		}
		return config;
	}
	
}
