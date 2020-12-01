package dpl.UserInputOutputTest.UserOutputTest;

import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CmdUserOutputTest {
    private IUserOutput cmdOutput;

    @Before
    public void setUpClass() throws Exception {
        cmdOutput = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
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