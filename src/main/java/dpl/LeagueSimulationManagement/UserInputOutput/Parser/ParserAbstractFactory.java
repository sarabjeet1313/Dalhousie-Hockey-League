package dpl.LeagueSimulationManagement.UserInputOutput.Parser;

public class ParserAbstractFactory implements IParserAbstractFactory {

	@Override
	public IParser CmdParseJSON(String filePath) {
		return new CmdParseJSON(filePath);
	}

}
