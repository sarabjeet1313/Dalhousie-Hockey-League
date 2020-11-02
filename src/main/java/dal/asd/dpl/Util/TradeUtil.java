package dal.asd.dpl.Util;

public enum TradeUtil {

    EMPTY("Empty");

    private final String constantString;

    private TradeUtil(String constantString) {
        this.constantString = constantString;
    }

    public String toString() {
        return constantString;
    }

}
