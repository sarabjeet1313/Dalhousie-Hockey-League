package dpl.UserInputOutputTest.ParserTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dpl.UserInputOutput.Parser.CmdParseJSON;

import org.junit.Test;
import org.junit.Before;
import java.net.URL;
import static org.junit.Assert.*;

public class CmdParseJSONTest {
    private static String filePath;

    @Before
    public void setUpClass() throws Exception {
    	URL i = getClass().getClassLoader().getResource("input.json");
    	filePath = i.getPath();
    }

    @Test
    public void parseTest() {
        CmdParseJSON parser = new CmdParseJSON(this.filePath);
        String temp = parser.parse("leagueName");
        assertEquals("\"test Hockey League\"", parser.parse("leagueName"));
    }

    @Test
    public void parseListTest() {
        CmdParseJSON parser = new CmdParseJSON(this.filePath);
        JsonArray conferences = parser.parseList("conferences");
        JsonObject conference = conferences.get(0).getAsJsonObject();
        assertEquals("\"Eastern Conference\"",conference.get("conferenceName").toString());
    }
}