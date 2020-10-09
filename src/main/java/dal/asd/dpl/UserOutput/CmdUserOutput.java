package dal.asd.dpl.UserOutput;

public class CmdUserOutput implements IUserOutput{

    private String outputResponse;

    public CmdUserOutput() {
        setInitialValues();
    }

    public void setInitialValues() {
        outputResponse = "";
    }

    public void setOutput(String outputResponse) {
        this.outputResponse = outputResponse;
    }

    public String sendOutput() {
        System.out.println(this.outputResponse);
        return this.outputResponse;
    }

}
