
//Name: Altun Hasanli

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Comp130_HW4_Fall19 extends GraphicsProgram {


	/**
	 * Initializer. Prints the welcome messages. Do <b>not</b> modify.
	 */
	public void init() {
		setTitle("MakeAMovie");
		println("Welcome to Make a Movie!");
		println("Start by either creating a new project or opening an existing one.");
	}
	
	/**
	 * Entry method for your implementation.
	 */
	public void run() {
		//your code starts here 
		userGrammar = new String[]{"scale","image","from","to","time"};
		ArrayList<String> commands = new ArrayList<String>();
		String[] listOfCommands = {"setback", "setgrammar", "addscene", "removescene", "listscenes", "play", "exit"};
		for(String com : listOfCommands) {
			commands.add(com);
		}
		String[] colorNames = {"white", "green", "blue", "magenta"};
		allowedColors = new ArrayList<String>();
		for(String color : colorNames) {
			allowedColors.add(color);
		}
		String[] lexemeNames = {"scale", "image", "from", "to", "time"};
		grammarLexemes = new ArrayList<String>();
		for(String lexeme : lexemeNames) {
			grammarLexemes.add(lexeme);
		}
		listOfScenes = new ArrayList<String[]>();
		println(CLI_INPUT_STR);
		String getInput = readLine();
		while(commands.contains(getInput)) {
			String[] testCheck = getInput.split(" ");
			if(testCheck.length > 1) {
				println("Error: Too many commands passed!");
				break;
			}
			if(getInput == "exit") {
				break;
			}
			processCommand(getInput);
			println(CLI_INPUT_STR);
			getInput = readLine();
			if(!commands.contains(getInput)) println("Error: Unknown command!");
		}
		println("End of Program.");
		// your code ends here
	}
	
	//ADDITIONAL HELPER METHODS//
	//your code starts here 
	
	private void processCommand(String command) {
		switch(command) {
			case "setback": commandSetback(); break; 
			case "setgrammar": commandSetgrammar(); break; 
			case "addscene": commandAddscene(); break; 
			case "removescene": commandRemovescene(); break; 
			case "listscenes": commandListscenes(); break; 
			case "play": commandPlay(); break;
			default: println("Error: Command does not exist!"); break;
		}
	}
	
	private void commandSetgrammar() {
		println("Define the grammar.");
		println("[scale, image, from, to, time]");
		String userInput = readLine();
		String[] customGrammar = userInput.split(" ");
		Boolean shouldDefineGrammar = true;
		for(String lexeme : customGrammar) {
			if(!grammarLexemes.contains(lexeme)) shouldDefineGrammar = false;
		}
		if(shouldDefineGrammar) userGrammar = customGrammar;
		else println("Error: Wrong definition of grammar!");
	}
	
	private void commandSetback() {
		println("Choose the color to be used.");
		println("[white, green, blue, magenta]");
		String userInput = readLine();
		if(allowedColors.contains(userInput)) setBackground(getColor(userInput));
		else println("Error: Unknown color!");
	}
	
	private void commandListscenes() {
		println("Here is the list of all scenes:");
		int counter = 1;
		for(String[] scene : listOfScenes) {
			println(counter + ": " + scene[6]);
			counter++;
		}
	}
	
	private void commandRemovescene() {
		println("Specify the scene you want to remove: ");
		int userInput = readInt();
		listOfScenes.remove(userInput-1);
		println("Scene removed!");
	}
	
	private void commandAddscene() {
		println("Specify the scene you want to add: ");
		String userInput = readLine();
		String[] sceneFromInput = userInput.split(" ");
		int inputLength = sceneFromInput.length;
		String scale, image, from, to, time1, time2;
		scale = image = from = to = time1 = time2 = "";
		if(inputLength != 9) println("Error: description does not match default length");
		else {
			int wordCounter = 0;
			for(String lexeme : userGrammar) {
				if(lexeme.equals("scale")) {
					scale = sceneFromInput[wordCounter];
					wordCounter += 1;
				} else if(lexeme.equals("image")) {
					image = sceneFromInput[wordCounter];
					wordCounter += 1;
				} else if(lexeme.equals("from")) {
					from = sceneFromInput[wordCounter+1];
					wordCounter += 2;
				} else if(lexeme.equals("to")) {
					to = sceneFromInput[wordCounter+1];
					wordCounter += 2;
				} else if(lexeme.equals("time")) {
					time1 = sceneFromInput[wordCounter+1];
					time2 = sceneFromInput[wordCounter+2];
					wordCounter += 3;
				}
			}
			String[] sceneArray = new String[]{scale, image, from, to, time1, time2, userInput};
			listOfScenes.add(sceneArray);
		}
	}
	
	private Color getColor(String name) {
		Color color = Color.WHITE;
		switch(name) {
			case "white": color = Color.WHITE; break;
			case "green": color = Color.GREEN; break;
			case "blue": color = Color.BLUE; break;
			case "magenta": color = Color.MAGENTA; break;
		}
		return color;
	}
	
	private void commandPlay() {
		for(String[] scene : listOfScenes) {
			animateScene(scene);
		}
	}
	
	private void animateScene(String[] scene) {
		GImage object = new GImage(LIBRARY_PATH+scene[1]+IMAGE_TYPE);
		double scale = Double.parseDouble(scene[0]);
		double[] locations = whereToPlace(scene[2], object.getWidth(), object.getHeight());
		object.setLocation(locations[0], locations[1]);
		double[] arrival = whereToPlace(scene[3], object.getWidth(), object.getHeight());
		object.scale(scale);
		add(object);
		int time = Integer.parseInt(scene[4]);
		if(scene[5].equals("seconds")) time *= 1000;
		double dx = arrival[0] - locations[0];
		double dy = arrival[1] - locations[1];
		double speedX = dx/time;
		double speedY = dy/time;
		while(time>0) {
			object.move(speedX,speedY);
			pause(1);
			time-=1;
		}
		remove(object);
	}
	
	private double[] whereToPlace(String direction, double width, double height) {
		double SCREEN_WIDTH = getWidth();
		double SCREEN_HEIGHT = getHeight();
		double[] locations = new double[]{0, SCREEN_HEIGHT/2-height/2};
		switch(direction) {
			case "left": locations = new double[]{0, SCREEN_HEIGHT/2-height/2}; break;
			case "right": locations = new double[]{SCREEN_WIDTH-width, SCREEN_HEIGHT/2-height/2}; break;
			case "top": locations = new double[]{SCREEN_WIDTH/2 - width/2, 0}; break;
			case "bottom": locations = new double[]{SCREEN_WIDTH/2 - width/2, SCREEN_HEIGHT-height}; break;
			case "center": locations = new double[]{SCREEN_WIDTH/2 - width/2, SCREEN_HEIGHT/2-height/2}; break;
		}
		return locations;
	}
	
	// your code ends here
	
	//ADDITIONAL INSTANCE AND CONSTANT VARIABLES//
	//your code starts here 
	String[] userGrammar;
	ArrayList<String> allowedColors;
	Color colorToUse;
	ArrayList<String[]> listOfScenes;
	ArrayList<String> grammarLexemes;
	// your code ends here
	
	
	// DO NOT REMOVE THIS SECTION//

	private String grammar = "";
	private List<String> scenes = new ArrayList<String>();
	private static int PAUSE_TIME = 1;
	
	// INSTANCE VARIABLES AND CONSTANTS
	/**
	 * Constant <code>String</code> used to prompt user for commands.
	 */
	public static String CLI_INPUT_STR = "MakeAMovie -> ";

	/**
	 * Path to the folder enclosing the images. Read images from this path.
	 */
	public static String LIBRARY_PATH = "../lib/";

	/**
	 * File extension of image files.
	 */
	public static String IMAGE_TYPE = ".png";

}
