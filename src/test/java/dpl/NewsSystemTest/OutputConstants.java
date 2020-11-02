package dpl.NewsSystemTest;

public class OutputConstants {
	public static final String GAMEPLAY = "{\n" +
											 "  \"winner\": \"Montreal Canadiens\",\n" + 
											 "  \"loser\": \"Toronto Maple Leafs\",\n" + 
											 "  \"date\": \"25-10-2020\"\n" + 
											 "}";
	
	public static final String TRADES = "{\n" + 
										"  \"from\": {\n" + 
										"    \"team\": \"Montreal Canadiens\",\n" + 
										"    \"players\": [\n" + 
										"      \"Mario Lemieux\",\n" + 
										"      \"Patrick Roy\"\n" + 
										"    ]\n" + 
										"  },\n" + 
										"  \"to\": {\n" + 
										"    \"team\": \"Winnipeg Jets\",\n" + 
										"    \"players\": [\n" + 
										"      \"Wayne Gretzky\"\n" + 
										"    ]\n" + 
										"  }\n" + 
										"}";
	
	public static final String INJURY = "{\n" + 
									    "  \"player\": \"Wayne Gretzky\",\n" + 
									    "  \"daysInjured\": 20\n" + 
									    "}";
	public static final String HIRED = "{\n" + 
										"  \"hired\": \"Wayne Gretzky\"\n" + 
										"}";
	public static final String RELEASED = "{\n" + 
										  "  \"released\": \"Wayne Gretzky\"\n" + 
										  "}";
	public static final String RETIRED = "{\n" + 
										  "  \"player\": \"Wayne Gretzky\",\n" + 
										  "  \"age\": 38\n" + 
										  "}";
}
