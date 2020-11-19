package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IPlayerInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.UserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutputAbstractFactory;
import dpl.SystemConfig;

public class AiAcceptReject {

    private IUserInputAbstractFactory inputFactory;
    private IUserOutputAbstractFactory outputFactory;

	public AiAcceptReject() {
		super();
		this.inputFactory = SystemConfig.getSingleInstance().getUserInputAbstractFactory();
		this.outputFactory = SystemConfig.getSingleInstance().getUserOutputAbstractFactory();
	}

    public boolean isAcceptOrReject(Trade trade, League league, double randomAcceptanceChance, boolean isUserTeam
            , IPlayerInfo iPInfoObject, ITeamInfo iTInfoObject) {
        IUserOutput output = this.outputFactory.CmdUserOutput();
        IUserInput Input = this.inputFactory.CmdUserInput();
        double playerOfferedStrength = 0;
        double playerRequestedStrength = 0;
        double totalTeamStrength = 0;

        if (isUserTeam) {
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|                         Requested Players from  Your team                         |");
            output.sendOutput();
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|      PlayerName     |   Position   | Age  | Skating | Shooting | Checking | Saving |");
            output.sendOutput();
            for (Player p : trade.getPlayerListRequestedTeam()) {
                output.setOutput("| " + p.getPlayerName() + " | " + p.getPosition() + " | " + p.getAge() + " | " + p.getSkating() + " | " + p.getShooting() + " | " + p.getChecking() + " | " + p.getSaving() + " |");
                output.sendOutput();
            }
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|                         Offered Players from " + trade.getTradeOfferTeam() + "                          |");
            output.sendOutput();
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|      PlayerName     |   Position   | Age  | Skating | Shooting | Checking | Saving |");
            output.sendOutput();
            for (Player p : trade.getPlayerListOfferTeam()) {
                output.setOutput("| " + p.getPlayerName() + " | " + p.getPosition() + " | " + p.getAge() + " | " + p.getSkating() + " | " + p.getShooting() + " | " + p.getChecking() + " | " + p.getSaving() + " |");
                output.sendOutput();
            }
            output.setOutput("                               ");
            output.sendOutput();
            output.setOutput("Do you Accept the trade offered? [y/n]");
            output.sendOutput();
            Input.setInput();
            String response = Input.getInput();
            return response.equals("y");
        } else if (Math.random() < randomAcceptanceChance) {
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
