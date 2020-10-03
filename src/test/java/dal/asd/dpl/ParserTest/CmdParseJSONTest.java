package dal.asd.dpl.ParserTest;

import dal.asd.dpl.App;
import dal.asd.dpl.Parser.CmdParseJSON;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CmdParseJSONTest {

    @Test
    public void parseTest() {
        CmdParseJSON parser = new CmdParseJSON("/Users/sarabjeetsingh/AdvSDC/dummy.json");
        assertEquals("\"testingData\"", parser.parse("testing"));
    }

    @Test
    public void parseListTest() {
        CmdParseJSON parser = new CmdParseJSON("/Users/sarabjeetsingh/AdvSDC/dummy.json");
        assertEquals("\"testingListData[0]\"",parser.parseList("testingList").get(0).toString());
    }
}