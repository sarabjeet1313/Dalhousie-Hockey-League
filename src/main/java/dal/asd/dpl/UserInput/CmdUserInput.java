package dal.asd.dpl.UserInput;


public class CmdUserInput implements IUserInput{

    private String InputResponse;

    public CmdUserInput() {
        setInitialValues();
    }

    public void setInitialValues() {
        InputResponse = "";
    }

    public String getInput() {
        return this.InputResponse;
    }

    public void setInput(String inputResponse) {
        this.InputResponse = inputResponse;
    }
}
