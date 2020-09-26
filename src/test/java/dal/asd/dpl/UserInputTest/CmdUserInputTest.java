package dal.asd.dpl.UserInputTest;

import dal.asd.dpl.UserInput.CmdUserInput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CmdUserInputTest {

    private static CmdUserInput cmdInput;

    @BeforeAll
    static void setUpClass() throws Exception {
        cmdInput = new CmdUserInput();
    }

    @Test
    void setInitialValues() {
        cmdInput.setInitialValues();
        assertEquals("", cmdInput.getInput());
    }

    @Test
    void getInput() {
        cmdInput.setInput("Testing Get Input");
        assertEquals("Testing Get Input", cmdInput.getInput());
    }

    @Test
    void setInput() {
        cmdInput.setInput("Testing Set Input");
        assertEquals("Testing Set Input", cmdInput.getInput());
    }
}