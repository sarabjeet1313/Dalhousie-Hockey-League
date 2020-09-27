package dal.asd.dpl.ParserTest;

import dal.asd.dpl.Parser.CmdParseJSON;
import dal.asd.dpl.UserInput.CmdUserInput;
import org.junit.Test;

import static org.junit.Assert.*;

public class CmdParseJSONTest {

    @Test
    public void parseTest() {
        CmdParseJSON parser = new CmdParseJSON("/Users/sarabjeetsingh/AdvSDC/dummy.json");
        assertEquals("\"testingData\"",parser.parse("testing"));
    }

    @Test
    public void parseListTest() {
        CmdParseJSON parser = new CmdParseJSON("/Users/sarabjeetsingh/AdvSDC/dummy.json");
        assertEquals("\"testingListData[0]\"",parser.parseList("testingList").get(0).toString());
    }
}