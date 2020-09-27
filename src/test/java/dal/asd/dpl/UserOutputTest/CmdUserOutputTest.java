package dal.asd.dpl.UserOutputTest;

import dal.asd.dpl.UserOutput.CmdUserOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdUserOutputTest {

    private static CmdUserOutput cmdOutput;

    @BeforeAll
    static void setUpClass() throws Exception {
        cmdOutput = new CmdUserOutput();
    }

    @Test
    void setInitialValuesTest() {
        cmdOutput.setInitialValues();
        assertEquals("", cmdOutput.sendOutput());
    }

    @Test
    void setOutputTest() {
        cmdOutput.setOutput("Testing Output");
        assertEquals(cmdOutput.sendOutput(), "Testing Output");
    }

    @Test
    void sendOutputTest() {

        //Base case
        cmdOutput.setInitialValues();
        assertEquals(cmdOutput.sendOutput(), "");

        cmdOutput.setOutput("Setting Dummy Output");
        assertEquals(cmdOutput.sendOutput(), "Setting Dummy Output");

    }
}