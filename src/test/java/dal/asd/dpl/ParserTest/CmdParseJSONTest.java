package dal.asd.dpl.ParserTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dal.asd.dpl.App;
import dal.asd.dpl.Parser.CmdParseJSON;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.junit.Assert.*;

public class CmdParseJSONTest {

    private static String filePath;

    @Before
    public void setUpClass() throws Exception {
        File file = new File("resources/json/input.json");
        filePath = file.getCanonicalPath();
    }

    @Test
    public void parseTest() {
        CmdParseJSON parser = new CmdParseJSON(this.filePath);
        assertEquals("\"Dalhousie Hockey League\"", parser.parse("leagueName"));
    }

    @Test
    public void parseListTest() {
        CmdParseJSON parser = new CmdParseJSON(this.filePath);
        JsonArray conferences = parser.parseList("conferences");
        JsonObject conference = conferences.get(0).getAsJsonObject();

        assertEquals("\"Eastern Conference\"",conference.get("conferenceName").toString());
    }
}