package dpl.UserInputOutput.Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dpl.SystemConfig;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutputAbstractFactory;

public class CmdParseJSON implements IParser {
	private String filePath;
	private IUserOutput output;
	private IUserOutputAbstractFactory outputAbstractFactory;

	public CmdParseJSON(String filePath) {
		this.filePath = filePath;
		this.outputAbstractFactory = SystemConfig.getSingleInstance().getUserOutputAbstractFactory();
		this.output = this.outputAbstractFactory.CmdUserOutput();
	}

	public String parse(String field) {
		JsonParser parser = new JsonParser();

		try {
			Object obj = parser.parse(new FileReader(filePath));
			JsonObject jsonObject = (JsonObject) obj;
			return jsonObject.get(field).toString();
		} catch (FileNotFoundException e) {
			output.setOutput("Input JSON file not found");
			output.sendOutput();
			return "Error";
		} catch (JsonSyntaxException e) {
			output.setOutput("Error in parsing Json file. Please check the syntax of the file");
			output.sendOutput();
			return "Error";
		} catch (NullPointerException e) {
			output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
			output.sendOutput();
			return "Error";
		} catch (IOException e) {
			output.setOutput("Found error in reading the file");
			output.sendOutput();
			return "Error";
		} catch (Exception e) {
			output.setOutput("Exception found in json file parsing.");
			output.sendOutput();
			return "Error";
		}
	}

	public JsonArray parseList(String field) {
		JsonArray items = null;
		JsonParser parser = new JsonParser();
		try {
			Object obj = parser.parse(new FileReader(filePath));
			JsonObject jsonObject = (JsonObject) obj;
			items = (JsonArray) jsonObject.get(field);
			return items;
		} catch (FileNotFoundException e) {
			output.setOutput("Input JSON file not found");
			output.sendOutput();
		} catch (JsonSyntaxException e) {
			output.setOutput("Error in parsing Json file. Please check the syntax of the file");
			output.sendOutput();
		} catch (NullPointerException e) {
			output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
			output.sendOutput();
		} catch (IOException e) {
			output.setOutput("Found error in reading the file");
			output.sendOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public JsonObject parseConfig(String field) {
		JsonParser parser = new JsonParser();

		try {
			Object obj = parser.parse(new FileReader(filePath));
			JsonObject jsonObject = (JsonObject) obj;
			return (JsonObject) jsonObject.get(field);
		} catch (FileNotFoundException e) {
			output.setOutput("Input JSON file not found");
			output.sendOutput();
			return null;
		} catch (JsonSyntaxException e) {
			output.setOutput("Error in parsing Json file. Please check the syntax of the file");
			output.sendOutput();
			return null;
		} catch (NullPointerException e) {
			output.setOutput("Error in parsing Json file. Please verify the " + field + " name in file");
			output.sendOutput();
			return null;
		} catch (IOException e) {
			output.setOutput("Found error in reading the file");
			output.sendOutput();
			return null;
		} catch (Exception e) {
			output.setOutput("Exception found in json file parsing.");
			output.sendOutput();
			return null;
		}
	}
}
