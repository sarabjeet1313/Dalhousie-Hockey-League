package dal.asd.dpl.Trading;

import dal.asd.dpl.TeamManagement.IPlayerInfo;
import dal.asd.dpl.TeamManagement.ITeamInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;

public class AiAcceptReject {

    public boolean isAcceptOrReject(Trade trade, League league, double randomAcceptanceChance, boolean isUserTeam
                                    , IPlayerInfo iPInfoObject, ITeamInfo iTInfoObject){
        CmdUserOutput output = new CmdUserOutput();
        CmdUserInput Input = new CmdUserInput();
        double playerOfferedStrength=0;
        double playerRequestedStrength=0;
        double totalTeamStrength=0;

        if (isUserTeam){
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|                         Requested Players from  Your team                         |");
            output.sendOutput();
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|      PlayerName     |   Position   | Age  | Skating | Shooting | Checking | Saving |");
            output.sendOutput();
            for(Player p: trade.getPlayerListRequestedTeam()){
                output.setOutput("| "+p.getPlayerName()+" | "+p.getPosition()+" | "+p.getAge()+" | "+p.getSkating()+" | "+p.getShooting()+" | "+p.getChecking()+" | "+p.getSaving()+" |");
                output.sendOutput();
            }
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|                         Offered Players from " +trade.getTradeOfferTeam() +"                          |");
            output.sendOutput();
            output.setOutput("------------------------------------------ + ----------------------------------------");
            output.sendOutput();
            output.setOutput("|      PlayerName     |   Position   | Age  | Skating | Shooting | Checking | Saving |");
            output.sendOutput();
            for(Player p: trade.getPlayerListOfferTeam()){
                output.setOutput("| "+p.getPlayerName()+" | "+p.getPosition()+" | "+p.getAge()+" | "+p.getSkating()+" | "+p.getShooting()+" | "+p.getChecking()+" | "+p.getSaving()+" |");
                output.sendOutput();
            }
            output.setOutput("                               ");
            output.sendOutput();
            output.setOutput("Do you Accept the trade offered? [Y/n]");
            output.sendOutput();
            Input.setInput();

            return Input.getInput().equals("Y") || Input.getInput().equals("y");
        }
        else if(Math.random()< randomAcceptanceChance){
            return true;
        }
        else{
            for(Player p1: trade.getPlayerListOfferTeam()){
                playerOfferedStrength = playerOfferedStrength + iPInfoObject.getPlayerStrength(p1);
            }
            for(Player p2: trade.getPlayerListRequestedTeam()){
                playerRequestedStrength = playerRequestedStrength + iPInfoObject.getPlayerStrength(p2);
            }
            totalTeamStrength = iTInfoObject.getTeamStrength(trade.getTradeRequestedTeam(), league);

            return (totalTeamStrength - playerRequestedStrength + playerOfferedStrength) > totalTeamStrength;
        }
    }

}
