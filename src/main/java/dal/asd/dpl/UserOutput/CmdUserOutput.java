package dal.asd.dpl.UserOutput;

public class CmdUserOutput implements IUserOutput{

    private String OutputResponse;

    public CmdUserOutput() {
        setInitialValues();
    }

    public void setInitialValues() {
        OutputResponse = "";
    }

    public void setOutput(String outputResponse) {
        this.OutputResponse = outputResponse;
    }

    public String sendOutput() {
        System.out.println(this.OutputResponse);
        return this.OutputResponse;
    }

}
