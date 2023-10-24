import acm.program.GraphicsProgram;
import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import acm.util.RandomGenerator;

//Ignore SuppressWarnings tag, it is irrelevant to the course.
@SuppressWarnings("serial")
public class Comp130_HW3_Fall19 extends GraphicsProgram {

	GOval vLight, hLight;

	static RandomGenerator rgen = new RandomGenerator();

	public void run(){

		// Define Locations of Cross Roads
		// CrossRoad X Component Start and End respectively
		double crossXstart = SCREEN_WIDTH/2+ROAD_THICKNESS/2;
		double crossXend = SCREEN_WIDTH/2-ROAD_THICKNESS;
		// CrossRoad Y Component Start and End respectively
		double crossYstart = SCREEN_HEIGHT/2-ROAD_THICKNESS/2;
		double crossYend = SCREEN_HEIGHT/2+ROAD_THICKNESS/2;

		// Initialize Variables to track fine data
		// Variable to store whether car 1 has been fined with speeding
		boolean getfine1 = false;
		// Variable to store whether car 2 has been fined with speeding
		boolean getfine2 = false;
		// Variable to store whether car 1 has been fined with red light
		boolean getlight1 = false;
		// Variable to store whether car 2 has been fined with red light
		boolean getlight2 = false;
		// Total fine for car 1
		int totalfine1 = 0;
		// Total fine for car 2
		int totalfine2 = 0;
		// Temporary variable to store momentary fine for car 1 p.s. it's too much work without these :)
		int fine1 = 0;
		// Temporary variable to store momentary fine for car 2
		int fine2 = 0;

		// Fine track label for the blue car
		GLabel bluelabel = new GLabel("", LABEL_X_MARGIN, LABEL_Y_MARGIN); // new instance
		bluelabel.setFont(LABEL_FONT); // font &c
		bluelabel.setColor(CAR_COLOR_BLUE); // color
		add(bluelabel);

		// Fine track label for the red car
		GLabel redlabel = new GLabel("", getWidth() - 200, LABEL_Y_MARGIN);
		redlabel.setFont(LABEL_FONT);
		redlabel.setColor(CAR_COLOR_RED);
		add(redlabel);

		// At these coordinates car 1 should start moving
		double car1InitialX = getWidth() - CAR_LENGHT;
		double car1InitialY = getHeight() / 2 - ROAD_THICKNESS/2 + (ROAD_THICKNESS/2 - CAR_WIDTH);

		// At these coordinates car 2 should start moving
		// center it perfectly within the lane !!!
		double car2InitialX = getWidth() / 2 - ROAD_THICKNESS/2 + (ROAD_THICKNESS/2 - CAR_WIDTH)/2;
		double car2InitialY = 0;

		// Initialize the car 1 as a rectangle
		GRect car1 = new GRect(car1InitialX, car1InitialY, CAR_LENGHT, CAR_WIDTH);
		car1.setFilled(true);
		// red car
		car1.setColor(CAR_COLOR_RED);
		add(car1);
		// Pick a random speed for the red car
		int speed1 = rgen.nextInt(MIN_SPEED, MAX_SPEED);

		// Initialize the car 2 as a rectangle
		GRect car2 = new GRect(car2InitialX, car2InitialY, CAR_WIDTH, CAR_LENGHT);
		car2.setFilled(true);
		// It's the blue color
		car2.setColor(CAR_COLOR_BLUE);
		add(car2);
		// Pick a random initial speed for the blue car
		int speed2 = rgen.nextInt(MIN_SPEED, MAX_SPEED);

		// These variables are used to keep track of the time passed, so that the program can change the color of the lights
		// high tech stuff
		int vLightTime = 0;
		// Pick a random time for the vertical light
		int vRandomTime = rgen.nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME);
		int hLightTime = 0;
		// pick a random time for the horizontal light
		int hRandomTime = rgen.nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME);

		// Should cars move? Set to false if cars crash
		boolean shouldCarsMove = true;

		/*
		 *  MAIN LOOP FOR HANDLING ANIMATIONS
		 */
		while(true) {
			// Time already passed for these lights
			vLightTime += PAUSE_TIME;
			hLightTime += PAUSE_TIME;

			// If the time passed is larger than the determined interval then change color
			if(vLightTime >= vRandomTime) {
				// If it's red then green and vice versa
				if(vLight.getFillColor() == Color.RED) {
					vLight.setFilled(true);
					vLight.setFillColor(Color.GREEN);
				} else {
					vLight.setFilled(true);
					vLight.setFillColor(Color.RED);
				}
				// Reset light time and get ready for the next iteration of the loop
				vLightTime = 0;
				// pick random time
				vRandomTime = rgen.nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME);
			}

			// If the time passed is larger than the determined interval then change color
			if(hLightTime >= hRandomTime) {
				// If it's red then green and vice versa
				if(hLight.getFillColor() == Color.RED) {
					hLight.setFilled(true);
					hLight.setFillColor(Color.GREEN);
				} else {
					hLight.setFilled(true);
					hLight.setFillColor(Color.RED);
				}
				// Reset light time and get ready for the next iteration of the loop
				hLightTime = 0;
				// pick random time
				hRandomTime = rgen.nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME);
			}

			// If there has been no collision continue ...
			if(shouldCarsMove) {

				// At this point cars move according to their respective speeds
				car1.move(-(speed1),0);
				car2.move(0, speed2);

				// If car1 reached the end, reset position and pick random speed
				if(car1.getX() < -CAR_LENGHT) {
					car1.setLocation(car1InitialX, car1InitialY);
					speed1 = rgen.nextInt(MIN_SPEED, MAX_SPEED);
				}

				// If car 2 reached the end, reset position and pick random speed
				if(car2.getY() > getHeight() + 5) {
					car2.setLocation(car2InitialX, car2InitialY);
					speed2 = rgen.nextInt(MIN_SPEED, MAX_SPEED);
				}

				// If car1 entered the intersection ...
				if(car1.getX() <= crossXstart && car1.getX() >= crossXend) {

					// detect red light violations
					if(!getlight1 && hLight.getFillColor() == Color.RED) {

						getlight1 = true; // we fined the car in this iteration of the loop, don't fine again

						fine1 += RED_LIGHT_FINE;

						// Increase total fine
						totalfine1 += RED_LIGHT_FINE;
					}

					// detect speed limit violations
					if(!getfine1 && speed1 > SPEED_LIMIT) {

						getfine1 = true; // we fined the car in this iteration of the loop, don't fine again

						fine1 += SPEED_FINE * (speed1 - SPEED_LIMIT);

						// Increase total fine
						totalfine1 += SPEED_FINE * (speed1 - SPEED_LIMIT);
					}

					// if no fine was cut to the car, then proceed, else, display the amount and the reason
					if(fine1 != 0) {
						// each conditional detects the violation type
						if(getlight1 && getfine1) {
							redlabel.setLabel("+" + fine1 + "$ Reason: Speeding & Red Light.");
						} else if(getlight1) {
							redlabel.setLabel("+" + fine1 + "$ Reason: Red Light.");
						} else if(getfine1) {
							redlabel.setLabel("+" + fine1 + "$ Reason: Speeding.");
						}
					} else redlabel.setLabel("");

					// POSITION THE RED LABEL to *right*
					redlabel.setLocation(getWidth() - LABEL_X_MARGIN - redlabel.getWidth(), LABEL_Y_MARGIN);
				} else {
					// reset the momentary fine variable for the next loop
					fine1 = 0;
					// Reset the fine booleans to false, since we're gonna fine them in the next iteration of the loop
					getfine1 = false;
					getlight1 = false;
				}

				// If car 2 entered the intersection
				if(car2.getY() >= crossYstart && car2.getY() <= crossYend) {

					// detect red light violations
					if(!getlight2 && vLight.getFillColor() == Color.RED) {

						getlight2 = true; // we fined the car in this iteration of the loop, don't fine again

						fine2 += RED_LIGHT_FINE;

						// Increase total fine
						totalfine2 += RED_LIGHT_FINE;
					}

					// detect speed limit violations
					if(speed2 > SPEED_LIMIT && !getfine2) {

						getfine2 = true; // we fined the car in this iteration of the loop, don't fine again

						fine2 += SPEED_FINE * (speed2 - SPEED_LIMIT);

						// Increase total fine
						totalfine2 += SPEED_FINE * (speed2 - SPEED_LIMIT);
					}

					// if no fine was cut to the car, then proceed, else, display the amount and the reason
					if(fine2 != 0) {
						// each conditional detects the violation type
						if(getlight2 && getfine2) {
							bluelabel.setLabel("+" + fine2 + "$ Reason: Speeding & Red Light.");
						} else if(getlight2) {
							bluelabel.setLabel("+" + fine2 + "$ Reason: Red Light.");
						} else if(getfine2) {
							bluelabel.setLabel("+" + fine2 + "$ Reason: Speeding.");
						}
					} else bluelabel.setLabel("");
				} else {
					// reset the momentary fine variable for the next loop
					fine2 = 0;
					// Reset the fine booleans to false, since we're gonna fine them in the next iteration of the loop
					getfine2 = false;
					getlight2 = false;
				}
			}

			// make it look like an animation
			pause(PAUSE_TIME);

			// If the cars are (still) moving
			if(shouldCarsMove) {

				/*
				 * At this part, the code detects collisions
				 */
				// Test if the vertices of car2 are within car1
				if(car1.contains(car2.getX(), car2.getY()) ||
				   car1.contains(car2.getX() + CAR_WIDTH, car2.getY()) ||
				   car1.contains(car2.getX(), car2.getY() + CAR_LENGHT) ||
				   car1.contains(car2.getX() + CAR_WIDTH, car2.getY() + CAR_LENGHT))
				{
					// stop their movement
					shouldCarsMove = false;
				}

				// Test if the vertices of car1 are within car2
				if(car2.contains(car1.getX(), car1.getY()) ||
				   car2.contains(car1.getX() + CAR_LENGHT, car1.getY()) ||
				   car2.contains(car1.getX(), car1.getY() + CAR_WIDTH) ||
				   car2.contains(car1.getX() + CAR_LENGHT, car1.getY() + CAR_WIDTH))
				{
					// stop their movement
					shouldCarsMove = false;
				}

				// SHOW FINAL FINES FOR EACH CAR
				if(!shouldCarsMove) {
					// Total fine for the red car
					redlabel.setLabel("Total fine: $" + totalfine1);
					// align the red label to right
					redlabel.setLocation(getWidth() - LABEL_X_MARGIN - redlabel.getWidth(), LABEL_Y_MARGIN);
					// Total fine for the blue car
					bluelabel.setLabel("Total fine: $" + totalfine2);
				}
			}
		}

	}

	/**
	 * Initialization method. This method is guaranteed to run before run().
	 */
	public void init(){

		//Initialize the screen size.
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		//Set the background color.
		this.setBackground(new Color(0,128,0));

		//Construct the roads.
		constructRoads();

		//Place the lights.
		placeLights();

	}

	/**
	 * This method constructs the lanes and the intersection.
	 */
	public void constructRoads(){

		//Road and crossing objects.
		GRect vRoad, hRoad, crossing;

		//Vertical and horizontal road creation.
		vRoad = createRoad(SCREEN_WIDTH/2, ROAD_THICKNESS, "v");
		hRoad = createRoad(SCREEN_HEIGHT/2, ROAD_THICKNESS, "h");
		add(vRoad);
		add(hRoad);

		//Square crossing section.
		crossing = new GRect(SCREEN_WIDTH/2-ROAD_THICKNESS/2, SCREEN_HEIGHT/2-ROAD_THICKNESS/2, ROAD_THICKNESS, ROAD_THICKNESS);
		crossing.setColor(new Color(255,255,255));
		add(crossing);

		//Lane separator lines.
		for(int i = 0; i<SCREEN_WIDTH/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i+15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_WIDTH; i>SCREEN_WIDTH/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i-15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = 0; i<SCREEN_HEIGHT/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i+15);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_HEIGHT; i>SCREEN_HEIGHT/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i-15);
			line.setColor(Color.WHITE);
			add(line);
		}

	}

	/**
	 * This method creates and places the light objects.
	 * @see See vLight and hLight for the light objects.
	 */
	public void placeLights(){

		//Create and place the vertical light.
		vLight = new GOval(25, 25);
		vLight.setFilled(true);
		vLight.setFillColor(Color.RED);
		add(vLight, SCREEN_WIDTH/2-75-3, SCREEN_HEIGHT/2-100);

		//Create and place the horizontal light.
		hLight = new GOval(25, 25);
		hLight.setFilled(true);
		hLight.setFillColor(Color.RED);
		add(hLight, SCREEN_WIDTH/2+75, SCREEN_HEIGHT/2-75-3);

	}

	/**
	 * @param center Center of the road to be built.
	 * @param width Thickness of the road.
	 * @param dir Direction of the road. Allowed values: "v", "h"
	 * @throws RuntimeException on invalid orientation.
	 * @return Created road as a GRect.
	 * @see constructRoads()
	 */
	public GRect createRoad(int center, int width, String dir){

		//Use dir to determine the road orientation.
		if(dir.equals("v")){

			//Create the road object.
			GRect road = new GRect(center-width/2, 0, width, SCREEN_HEIGHT);

			//Set border color.
			road.setColor(new Color(0,0,0,0));

			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));

			//Return the created road object.
			return road;

		}else if (dir.equals("h")){

			//Create the road object.
			GRect road = new GRect(0, center-width/2, SCREEN_WIDTH, width);

			//Set border color.
			road.setColor(new Color(0,0,0,0));

			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));

			//Return the created road object.
			return road;

		}else{

			//Ignore throw keyword, it is irrelevant to the course.
			throw new RuntimeException("Invalid argument 'orientation' = '" + dir + "' @ drawRoad.");

		}
	}


	/**
	 * Width of the screen.
	 */
	public static final int SCREEN_WIDTH = 800;

	/**
	 * Height of the screen.
	 */
	public static final int SCREEN_HEIGHT = 600;

	/**
	 * Thickness of the roads constructed on the screen.
	 */
	public static final int ROAD_THICKNESS = 100;

	/**
	 * Width of the cars.
	 */
	public static final int CAR_WIDTH = 30;

	/**
	 * Lenght of the cars.
	 */
	public static final int CAR_LENGHT = 70;

	/**
	 * Color for the blue car.
	 */
	public static final Color CAR_COLOR_BLUE = new Color(0,0,192);

	/**
	 * Color for the red car.
	 */
	public static final Color CAR_COLOR_RED = new Color(192,0,0);

	/**
	 * Minimum speed allowed for the cars.
	 */
	public static final int MIN_SPEED = 5;

	/**
	 * Maximum speed allowed for the cars.
	 */
	public static final int MAX_SPEED = 30;

	/**
	 * Speed limit for the cars
	 */
	public static final int SPEED_LIMIT = 20;

	/**
	 * Unit fine used to calculate speeding fines.
	 */
	public static final int SPEED_FINE = 10;

	/**
	 * Static fine for passing a red light.
	 */
	public static final int RED_LIGHT_FINE = 100;

	/**
	 * Pause time for the animation.
	 */
	public static final int PAUSE_TIME = 50;

	/**
	 * Minimum time for a traffic light to change.
	 */
	public static final int MIN_LIGHT_TIME = 1000;

	/**
	 * Maximum time for a traffic light to change.
	 */
	public static final int MAX_LIGHT_TIME = 5000;

	/**
	 * Font for use in labels.
	 */
	public static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 15);

	/**
	 * Margin of fine labels for the Y-axis.
	 */
	public static final int LABEL_Y_MARGIN = 20;

	/**
	 * Margin of fine labels for the X-axis.
	 */
	public static final int LABEL_X_MARGIN = 10;

}
