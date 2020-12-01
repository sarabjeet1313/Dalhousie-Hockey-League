package dpl.UserInputOutputTest.UserInputTest;

import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.UserInputOutput.UserInput.UserInputAbstractFactory;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserInputAbstractFactoryTest {

    private IUserInputAbstractFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new UserInputAbstractFactory();
    }

    @Test
    public void cmdUserInputTest() {
        IUserInput input = factory.CmdUserInput();
        assertTrue(input instanceof CmdUserInput);
        assertFalse(input instanceof CmdUserOutput);
    }
}