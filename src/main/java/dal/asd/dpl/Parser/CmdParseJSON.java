package dal.asd.dpl.Parser;
import com.google.gson.*;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CmdParseJSON implements IParser {
    private static String filePath;
    private static CmdUserOutput output;

    public CmdParseJSON (String filePath) {
        this.filePath = filePath;
        this.output = new CmdUserOutput();
    }

    public String parse(String field) {
        JsonParser parser = new JsonParser();

        try{
            Object obj = parser.parse(new FileReader(filePath));
            JsonObject jsonObject = (JsonObject)obj;
            return jsonObject.get(field).toString();
        }
        catch(FileNotFoundException e) {
            output.setOutput("Input JSON file not found");
            output.sendOutput();
            return "Error";
        }
        catch (JsonSyntaxException e) {
            output.setOutput("Error in parsing Json file. Please check the syntax of the file");
            output.sendOutput();
            return "Error";
        }
        catch(NullPointerException e) {
            output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
            output.sendOutput();
            return "Error";
        }
        catch(IOException e) {
            output.setOutput("Found error in reading the file");
            output.sendOutput();
            return "Error";
        }
        catch (Exception e) {
            output.setOutput("Exception found in json file parsing.");
            output.sendOutput();
            return "Error";
        }
    }

    public JsonArray parseList(String field) {
        JsonArray items = null;
        JsonParser parser = new JsonParser();
        try{
            Object obj = parser.parse(new FileReader(filePath));
            JsonObject jsonObject = (JsonObject)obj;
            items = (JsonArray) jsonObject.get(field);
            return items;
        }
        catch(FileNotFoundException e) {
            output.setOutput("Input JSON file not found");
            output.sendOutput();
        }
        catch (JsonSyntaxException e) {
            output.setOutput("Error in parsing Json file. Please check the syntax of the file");
            output.sendOutput();
        }
        catch(NullPointerException e) {
            output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
            output.sendOutput();
        }
        catch(IOException e) {
            output.setOutput("Found error in reading the file");
            output.sendOutput();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    public JsonObject parseConfig(String field){
        JsonParser parser = new JsonParser();

        try{
            Object obj = parser.parse(new FileReader(filePath));
            JsonObject jsonObject = (JsonObject)obj;
            return (JsonObject) jsonObject.get(field);
        }
        catch(FileNotFoundException e) {
            output.setOutput("Input JSON file not found");
            output.sendOutput();
            return null;
        }
        catch (JsonSyntaxException e) {
            output.setOutput("Error in parsing Json file. Please check the syntax of the file");
            output.sendOutput();
            return null;
        }
        catch(NullPointerException e) {
            output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
            output.sendOutput();
            return null;
        }
        catch(IOException e) {
            output.setOutput("Found error in reading the file");
            output.sendOutput();
            return null;
        }
        catch (Exception e) {
            output.setOutput("Exception found in json file parsing.");
            output.sendOutput();
            return null;
        }
    }
}
