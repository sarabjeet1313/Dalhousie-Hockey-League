package dpl.LeagueManagement.Trading;

import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.TeamManagement.IPlayerInfo;
import dpl.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutputAbstractFactory;
import dpl.SystemConfig;

import java.util.HashMap;

public class AiAcceptReject {

	private IUserInputAbstractFactory inputFactory;
	private IUserOutputAbstractFactory outputFactory;
	private IGameplayConfigurationAbstractFactory gameConfigFactory;
	private ITeamManagementAbstractFactory teamManagementFactory;

	public AiAcceptReject() {
		super();
		this.inputFactory = SystemConfig.getSingleInstance().getUserInputAbstractFactory();
		this.outputFactory = SystemConfig.getSingleInstance().getUserOutputAbstractFactory();
		this.gameConfigFactory = SystemConfig.getSingleInstance().getGameplayConfigurationAbstractFactory();
		this.teamManagementFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
	}

	public boolean isAcceptOrReject(Trade trade, League league, double randomAcceptanceChance, boolean isUserTeam
			, IPlayerInfo iPInfoObject, ITeamInfo iTInfoObject) {
		IUserOutput output = this.outputFactory.CmdUserOutput();
		IUserInput Input = this.inputFactory.CmdUserInput();
		Manager manager = this.teamManagementFactory.Manager();
		HashMap<String, Double> gmTable = league.getGameConfig().getTrading().getGmTable();
		String personalityType = manager.getMangerPersonalityByTeam(trade.getTradeRequestedTeam(), league);

		double personalityValue = gmTable.get(personalityType);
		double playerOfferedStrength = 0;
		double playerRequestedStrength = 0;
		double totalTeamStrength = 0;

		if (isUserTeam) {
			output.setOutput(TradeConstants.PARTITION.toString());
			output.sendOutput();
			output.setOutput(TradeConstants.REQUESTED_PLAYER_FROM_TEAM.toString());
			output.sendOutput();
			output.setOutput(TradeConstants.PARTITION.toString());
			output.sendOutput();
			output.setOutput(TradeConstants.PLAYERTABLE_HEADER.toString());
			output.sendOutput();
			for (Player p : trade.getPlayerListRequestedTeam()) {
				output.setOutput("| " + p.getPlayerName() + " | " + p.getPosition() + "	 | " + p.getAge() + "	 | " + p.getSkating() + "	 | " + p.getShooting() + "	| " + p.getChecking() + "	 | " + p.getSaving() + "	|");
				output.sendOutput();
			}
			output.setOutput(TradeConstants.PARTITION.toString());
			output.sendOutput();
			output.setOutput("|						 Offered Players from " + trade.getTradeOfferTeam() + "						  |");
			output.sendOutput();
			output.setOutput(TradeConstants.PARTITION.toString());
			output.sendOutput();
			output.setOutput(TradeConstants.PLAYERTABLE_HEADER.toString());
			output.sendOutput();
			for (Player p : trade.getPlayerListOfferTeam()) {
				output.setOutput("| " + p.getPlayerName() + " | " + p.getPosition() + "	 | " + p.getAge() + "	 | " + p.getSkating() + "	 | " + p.getShooting() + "	| " + p.getChecking() + "	 | " + p.getSaving() + "	|");
				output.sendOutput();
			}
			output.setOutput("							   ");
			output.sendOutput();
			output.setOutput(TradeConstants.ACCEPT_REJECT.toString());
			output.sendOutput();
			Input.setInput();
			String response = Input.getInput();
			return response.equals(TradeConstants.YES_SMALL.toString()) || response.equals(TradeConstants.YES_BIG.toString());
		} else if (Math.random() < randomAcceptanceChance + personalityValue) {
			return true;
		} else {
			for (Player p1 : trade.getPlayerListOfferTeam()) {
				playerOfferedStrength = playerOfferedStrength + iPInfoObject.getPlayerStrength(p1);
			}
			for (Player p2 : trade.getPlayerListRequestedTeam()) {
				playerRequestedStrength = playerRequestedStrength + iPInfoObject.getPlayerStrength(p2);
			}
			totalTeamStrength = iTInfoObject.getTeamStrength(trade.getTradeRequestedTeam(), league);

			return (totalTeamStrength - playerRequestedStrength + playerOfferedStrength) > totalTeamStrength;
		}
	}

}
