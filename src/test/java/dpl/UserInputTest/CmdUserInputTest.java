package dpl.UserInputTest;

import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class CmdUserInputTest {
    private static CmdUserInput cmdInput;

    @Before
    public void setUpClass() throws Exception {
        cmdInput = new CmdUserInput();
    }

    @Test
    public void setInitialValuesTest() {
        cmdInput.setInitialValues();
        assertEquals("", cmdInput.getInput());

    }

    @Test
    public void getInputTest() {
        String input = "Testing Get Input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cmdInput.setInput();
        assertEquals("Testing Get Input", cmdInput.getInput());
    }

    @Test
    public void setInputTest() {
        String input = "Testing Set Input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cmdInput.setInput();
        assertEquals("Testing Set Input", cmdInput.getInput());

    }
}