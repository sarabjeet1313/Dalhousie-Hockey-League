package dpl.UserInputTest;

import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.UserInputAbstractFactory;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
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