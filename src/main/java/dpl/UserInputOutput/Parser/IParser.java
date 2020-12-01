package dpl.UserInputOutput.Parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface IParser {
	public String parse(String field);

	public JsonArray parseList(String field);

	public JsonObject parseConfig(String field);
}
