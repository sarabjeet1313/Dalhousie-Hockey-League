package dal.asd.dpl.UserOutputTest;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Test;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CmdUserOutputTest {
    private IUserOutput cmdOutput;

    @Before
    public void setUpClass() throws Exception {
        cmdOutput = new CmdUserOutput();
    }

    @Test
    public void setInitialValuesTest() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");

        cmdOutput.setInitialValues();
        assertEquals("", cmdOutput.sendOutput());
    }

    @Test
    public void setOutputTest() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected = "Testing Output";

        cmdOutput.setOutput("Testing Output");
        cmdOutput.sendOutput();

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");


        assertEquals(expected, gotOutput);
    }

    @Test
    public void sendOutputTest() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected = "Setting Dummy Output";

        cmdOutput.setOutput("Setting Dummy Output");
        cmdOutput.sendOutput();

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");

        assertEquals(expected, gotOutput);
    }

}