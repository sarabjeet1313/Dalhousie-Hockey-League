package dal.asd.dpl.GameplayConfiguration;

public class GameplayConfig {
	private Aging aging;
	private GameResolver gameResolver;
	private Injury injury;
	private Training training;
	private Trading trading;
	public GameplayConfig(Aging aging, GameResolver gameResolver, Injury injury, Training training, Trading trading) {
		super();
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
	
}
