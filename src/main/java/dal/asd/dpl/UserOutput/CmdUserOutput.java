package dal.asd.dpl.UserOutput;

public class CmdUserOutput {

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
        return this.OutputResponse;
    }

}
