package dal.asd.dpl.NewsSystem;

public class NewsSystemMock {
	public static void newsMock() {
		System.out.println("Game Played");
		GamePlayedPublisher gamePlayedPublisher = new GamePlayedPublisher();
		gamePlayedPublisher.subscribe(new NewsSubscriber());
		gamePlayedPublisher.notify("TeamA", "TeamB", "10-01-2020");

		System.out.println();
		System.out.println("Trades");
		TradePublisher tradePublisher = new TradePublisher();
		tradePublisher.subscribe(new NewsSubscriber());
		tradePublisher.notify("TeamA", "TeamB", new String[][] {{"Player1", "Player3"}, {"Player2"}});

		System.out.println();
		System.out.println("Injury");
		InjuryPublisher injuryPublisher = new InjuryPublisher();
		injuryPublisher.subscribe(new NewsSubscriber());
		injuryPublisher.notify("Wayne Gretzky", 20);

		System.out.println();
		System.out.println("Player Hired From Free Agent List");
		FreeAgencyPublisher freeAgencyPublisher = new FreeAgencyPublisher();
		freeAgencyPublisher.subscribe(new NewsSubscriber());
		freeAgencyPublisher.notify("Wayne Gretzky", "hired");

		System.out.println();
		System.out.println("Player Release To Free Agent List");
		freeAgencyPublisher.notify("Wayne Gretzky", "released");

		System.out.println();
		System.out.println("Player Retired");
		RetirementPublisher retirementPublisher = new RetirementPublisher();
		retirementPublisher.subscribe(new NewsSubscriber());
		retirementPublisher.notify("Wayne Gretzky", 38);

	}
}	
