package dal.asd.dpl.Parser;

import com.google.gson.JsonArray;

public interface IParser {
    public String parse(String field);
    public JsonArray parseList(String field);
}
