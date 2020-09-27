package dal.asd.dpl;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;

public class App 
{
    public static void main( String[] args ) {

        CmdUserInput input = new CmdUserInput();
        CmdUserOutput output = new CmdUserOutput();

        output.setOutput("Please enter json file path");
        output.sendOutput();
        input.setInput();

        InitializeLeagues initialize = new InitializeLeagues(input.getInput());

//        CmdParseJSON jsonParser = new CmdParseJSON(input.getInput());
//        jsonParser.parseList("conferences");
//        jsonParser.parse("leagueName");
    }
}
