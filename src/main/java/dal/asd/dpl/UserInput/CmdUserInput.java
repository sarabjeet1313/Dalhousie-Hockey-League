package dal.asd.dpl.UserInput;


import java.util.Scanner;

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

    public void setInput() {
        Scanner scanInput = new Scanner(System.in);
        this.InputResponse = scanInput.nextLine();
    }
}
