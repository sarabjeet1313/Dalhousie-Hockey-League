package dpl.UserInputOutputTest.UserOutputTest;

import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutputAbstractFactory;
import dpl.UserInputOutput.UserOutput.UserOutputAbstractFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserOutputAbstractFactoryTest {

    private IUserOutputAbstractFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new UserOutputAbstractFactory();
    }

    @Test
    public void cmdUserOutputTest() {
        IUserOutput output = factory.CmdUserOutput();
        assertTrue(output instanceof CmdUserOutput);
        assertFalse(output instanceof CmdUserInput);
    }
}