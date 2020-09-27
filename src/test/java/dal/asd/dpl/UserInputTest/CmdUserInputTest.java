package dal.asd.dpl.UserInputTest;

import dal.asd.dpl.UserInput.CmdUserInput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

import static junit.framework.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

class CmdUserInputTest {

    private static CmdUserInput cmdInput;

    @BeforeAll
    static void setUpClass() throws Exception {
        cmdInput = new CmdUserInput();
    }

    @Test
    void setInitialValuesTest() {
        cmdInput.setInitialValues();
        assertEquals("", cmdInput.getInput());
    }

    @Test
    void getInputTest() {
        String input = "Testing Get Input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cmdInput.setInput();
        assertEquals("Testing Get Input", cmdInput.getInput());
    }

    @Test
    void setInputTest() {
        String input = "Testing Set Input";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cmdInput.setInput();
        assertEquals("Testing Set Input", cmdInput.getInput());
    }
}