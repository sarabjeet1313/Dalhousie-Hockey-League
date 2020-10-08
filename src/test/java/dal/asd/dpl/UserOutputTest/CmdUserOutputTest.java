package dal.asd.dpl.UserOutputTest;

import dal.asd.dpl.UserOutput.CmdUserOutput;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class CmdUserOutputTest {

    private static CmdUserOutput cmdOutput;

    @Before
    public void setUpClass() throws Exception {
        cmdOutput = new CmdUserOutput();
    }

    @Test
    public void setInitialValuesTest() {
        cmdOutput.setInitialValues();
        assertEquals("", cmdOutput.sendOutput());
    }

    @Test
    public void setOutputTest() {
        cmdOutput.setOutput("Testing Output");
        assertEquals(cmdOutput.sendOutput(), "Testing Output");
    }

    @Test
    public void sendOutputTest() {

        //Base case
        cmdOutput.setInitialValues();
        assertEquals(cmdOutput.sendOutput(), "");

        cmdOutput.setOutput("Setting Dummy Output");
        assertEquals(cmdOutput.sendOutput(), "Setting Dummy Output");

    }

}