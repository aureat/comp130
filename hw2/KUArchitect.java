/** Student Name: Altun Hasanli
 * This is a console program designed to conduct an audition for an architecture
 * competition. Four contestants are competing against each other given their
 * education, experience and their conciousness about environment.
 *
**/

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

public class KUArchitect extends ConsoleProgram {

	public void run() {
		// your code starts here

		/*
		 *  ASK THE USER FOR PARAMETERS OF DIFFERENT ARHITECTS, ASK REPEATEDLY IF NO VALID INPUT WAS GIVEN
		 *  CALCULATE TOTAL POINTS AND PICK THE THREE FINALISTS
		*/

		for(int i = 1; i <= CONTESTANT_NUM; i++) {
			println("NEW CONTESTANT");
			int totalPoints = pointCalculator(askKnowledge(), askExperience(), askAwards(), hasRecycledMaterial());
			assignPoint(totalPoints);
			println("KUArchitect #" + i +" has reached " + totalPoints + " points.\n");
			currentKUArchitectID++;
		}
		comparator();
		println("KUArchitect #" + firstID + " becomes #1 with " + scoreFromPerson(firstID) + " points.");
		println("KUArchitect #" + secondID + " becomes #2 with " + scoreFromPerson(secondID) + " points.");
		println("KUArchitect #" + thirdID + " becomes #3 with " + scoreFromPerson(thirdID) + " points.");


		/*
		 * COMBAT BETWEEN ARCHITECTS
		 * DETERMINE THE FINAL WINNER
		 */
		int architect1 = 0;
		int architect2 = 0;
		int architect3 = 0;
		// Combat between A1 and A2
		int nwin1 = matchDuo(firstPoint, secondPoint);
		architect1 += nwin1;
		architect2 += NTIMES - nwin1;
		// Combat between A2 and A3
		int nwin2 = matchDuo(secondPoint, thirdPoint);
		architect2 += nwin2;
		architect3 += NTIMES - nwin2;
		// Combat between A1 and A3
		int nwin3 = matchDuo(firstPoint, thirdPoint);
		architect1 += nwin3;
		architect3 += NTIMES - nwin3;
		// Determine the final winner
		int winner;
		if(architect1 > architect2) {
			if(architect1 > architect3) winner = firstID;
			else winner = thirdID;
		} else {
			if(architect2 > architect3) winner = secondID;
			else winner = thirdID;
		}
		println("The game is now over. The scores are as below: \n");

		println("KUArchitect #" + firstID + " has won " + architect1 + " times in 300 games.");
		println("KUArchitect #" + secondID + " has won " + architect2 + " times in 300 games.");
		println("KUArchitect #" + thirdID + " has won " + architect3 + " times in 300 games.");

		println("Congratulations KUArchitect #" + winner + "! You are now the winner of KUArchitect.\\n");
		// your code ends here
	}

	////////////GIVEN HELPER METHODS /////////////////////
	// You need to implement the given helper methods ///
	// You ARE NOT ALLOWED to change the signature of the given methods.

	/**
	 * This methods asks the number of year of education and number of year of experience.
	 * It will keep on asking user input until a valid input is read.
	 * @return - returns the knowledge point
	 */
	private double askKnowledge() {
		// your code starts here
		print("Enter a value for years of education of KUArchitect #" + currentKUArchitectID + ": ");
		int numEducation = readInt();
		while(numEducation < 4 || numEducation > 6) {
			print("Enter a VALID value for years of education of KUArchitect #" + currentKUArchitectID + ": ");
			numEducation = readInt();
		}
		print("Enter a value for years of experience of KUArchitect #" + currentKUArchitectID + ": ");
		int numExperience = readInt();
		while(numExperience < 2 || numExperience > 10) {
			print("Enter a VALID value for years of experience of KUArchitect #" + currentKUArchitectID + ": ");
			numExperience = readInt();
		}
		return calculateKnowledge(numEducation, numExperience);
		// your code ends here


	}

	/**
	 * This method calculates the knowledge point given the number of year of education
	 * and the number of year of experience
	 * @param yearsEducation - the number of year of education
	 * @param yearsExperience - the number of year of experience
	 * @return - knowledge point
	 */
	private double calculateKnowledge(int yearsEducation, int yearsExperience) {
		// your code starts here
		double knowledgePoint = Math.pow((yearsEducation + yearsExperience) / 5, yearsExperience - 2) + fibonacci(yearsEducation);
		return knowledgePoint;
		// your code ends here
	}

	/**
	 * This methods asks the number of projects and number of different projects.
	 * It will keep on asking user input until a valid input is read.
	 * @return - returns the experience point
	 */
	private int askExperience() {
		// your code starts here
		print("Enter a value for number of projects of KUArchitect #" + currentKUArchitectID + ": ");
		int numProjects = readInt();
		while(numProjects < 4 || numProjects > 15) {
			print("Enter a VALID value for number of projects of KUArchitect #" + currentKUArchitectID + ": ");
			numProjects = readInt();
		}
		print("Enter a value for number of different projects of KUArchitect #" + currentKUArchitectID + ": ");
		int numDifProjects = readInt();
		while(numDifProjects < 2 || numDifProjects > 5) {
			print("Enter a VALID value for number of different projects of KUArchitect #" + currentKUArchitectID + ": ");
			numDifProjects = readInt();
		}
		return calculateExperience(numProjects, numDifProjects);
		// your code ends here

	}
	/**
	 * This method calculates the experience point given the number of projects
	 * and the different projects worked on.
	 * @param numProjects - number of projects
	 * @param numDifProjects - number of different projects
	 * @return - experience point
	 */
	private int calculateExperience(int numProjects, int numDifProjects) {
		// your code starts here
		return numProjects * factorial(numDifProjects);
		// your code ends here
	}


	/**
	 * This methods asks the number of awards received.
	 * It will keep on asking user input until a valid input is read.
	 * @return - number of awards
	 */
	private int askAwards() {

		// your code starts here
		print("Enter a value for number of awards: ");
		int numAwards = readInt();
		while(numAwards < 1 || numAwards > 5) {
			print("Enter a VALID value for number of awards: ");
			numAwards = readInt();
		}
		return numAwards;
		// your code ends here
	}

	/**
	 * This method decides which architect gets the recycled materials.
	 * @return - whether the architect received a recycled material or not.
	 */
	private boolean hasRecycledMaterial() {
		// your code starts here
		return rgen.nextBoolean(0.5);
		// your code ends here
	}


	/**
	 * This method calculates the total points of a KUArchitect
	 * @param knowledge - the knowledge point of a KUArchietct
	 * @param experience - the experience point of a KUArchietct
	 * @param awards - the number of awards of a KUArchietct
	 * @param hasRecycledMaterial - whether KUArchiects received recyled material or not.
	 * @return - the total point of a KUArchitect
	 */
	private int pointCalculator(double knowledge, int experience,  int awards, boolean hasRecycledMaterial) {

		// your code starts here
		// Calculate points based on all parameters
		double awardsAndExperience = Math.sqrt(awards * experience);
		if(hasRecycledMaterial) return rounded(awardsAndExperience + 1.3 * knowledge);
		else return rounded(awardsAndExperience + 0.9 * knowledge);
		// your code ends here
	}

	/**
	 * This method assigns the total point calculatef for a KUArchitects and assigns to the correct KUArchitect
	 * @param p - the total point of any given KUArchitect
	 */
	private void assignPoint(int p) {

		// your code starts here
		switch(currentKUArchitectID) {
			case 1: totalPointOfKUArchitect1 = p; break;
			case 2: totalPointOfKUArchitect2 = p; break;
			case 3: totalPointOfKUArchitect3 = p; break;
			case 4: totalPointOfKUArchitect4 = p; break;
		}
		// your code ends here
	}

	/**
	 * This method compares the total point of all contestants and prints
	 * the first, secong and third order of the KUArchitect according to their total points.
	 */
	private void comparator() {
		// your code starts here

//		int max1 = (totalPointOfKUArchitect1 > totalPointOfKUArchitect2) ? 1 : 2;
//		int max2 = (scoreFromPerson(max1) > totalPointOfKUArchitect3) ? max1 : 3;
//		int max3 = (scoreFromPerson(max2) > totalPointOfKUArchitect4) ? max2 : 4;
//		int firstPoint = max3;

		int temp;
		// Assign points to dummy variables
		int p1 = totalPointOfKUArchitect1;
		int p2 = totalPointOfKUArchitect2;
		int p3 = totalPointOfKUArchitect3;
		int p4 = totalPointOfKUArchitect4;

//		int max1 = (p1 > p2) ? 1 : 2;
//		int max2 = (scoreFromPerson(max1) > p3) ? max1 : 3;
//		int max3 = (scoreFromPerson(max2) > p4) ? max2 : 4;
//		p4 = max3;

		/*
			Compare and exchange values at each step
		*/
		// Divide and compare
		if (p1 > p2) {
			temp = p1;
			p1 = p2;
			p2 = temp;
		}
		// p1 > p2
		if (p3 > p4) {
			temp = p3;
			p3 = p4;
			p4 = temp;
		}
		// Shorthand exchange
		// Combine
		if (p1 > p3) {
			temp = p1;
			p1 = p3;
			p3 = temp;
		}
		// Eliminate whatever's left on the sides
		if (p2 > p4) {
			temp = p2;
			p2 = p4;
			p4 = temp;
		}
		// Deal with the middle ones
		if (p2 > p3) {
			temp = p2;
			p2 = p3;
			p3 = temp;
		}
		// Set points
		firstPoint = p4;
		secondPoint = p3;
		thirdPoint = p2;
		// Set people
		firstID = personFromScore(firstPoint);
		secondID = personFromScore(secondPoint);
		thirdID = personFromScore(thirdPoint);
		// your code ends here
	}

	////////////ADDITIONAL HELPER METHODS /////////////////////
	// Feel free to add additional helper methods
	// your code starts here

	// Returns nth fibonacci number
	private int fibonacci(int numFibonacci) {
        int first = 1;
        int second = 1;
        int result;
        for(int i=1; i < numFibonacci-1; i++) {
          result = first + second;
          first = second;
          second = result;
        }
        return second;
	}

	// Returns nth factorial numnber
	private int factorial(int numFactorial) {
		int result = 1;
		for(int i = 2; i <= numFactorial; i++) {
			result *= i;
		}
		return result;
	}

	// Rounds number to the closest integer value
	private int rounded(double number) {
		int decimal = (int) number;
		double fractional = number - decimal;
		if(fractional >= 0.5) return decimal + 1;
		else return decimal;
	}

	// Returns the index of an architect by score
	private int personFromScore(int score) {
			int person = 0;
			if(score == totalPointOfKUArchitect1) person = 1;
			if(score == totalPointOfKUArchitect2) person = 2;
			if(score == totalPointOfKUArchitect3) person = 3;
			if(score == totalPointOfKUArchitect4) person = 4;
			return person;
	}

	// Retturns the score of a particular architect
	private int scoreFromPerson(int person) {
		int score = 0;
		switch(person) {
			case 1: score = totalPointOfKUArchitect1; break;
			case 2: score = totalPointOfKUArchitect2; break;
			case 3: score = totalPointOfKUArchitect3; break;
			case 4: score = totalPointOfKUArchitect4; break;
		}
		return score;
	}

	// Returns the number of winning matches the first given player had; the second one is calculated afterwards
	private int matchDuo(int firstGiven, int secondGiven) {
		int winning = 0;
		for(int i = 0; i < NTIMES; i++) {
			double probability = (double) firstGiven / (firstGiven + secondGiven);
			boolean architectWon = rgen.nextBoolean(probability);
			if(architectWon) winning++;
		}
		return winning;
	}

	// your code ends here

	//////////// GIVEN VARIABLES and CONSTANTS /////////////////////
	int currentKUArchitectID = 1;

	int totalPointOfKUArchitect1;
	int totalPointOfKUArchitect2;
	int totalPointOfKUArchitect3;
	int totalPointOfKUArchitect4;

	int firstID;
	int secondID;
	int thirdID;

	int firstPoint;
	int secondPoint;
	int thirdPoint;

	private final int CONTESTANT_NUM = 4;
	private final int NTIMES = 100;
	static RandomGenerator rgen = new RandomGenerator();
	////////////ADDITIONAL VARIABLES and CONSTANTS  /////////////////////
	// Feel free to add additional variables and constants
	// your code starts here

	// your code ends here

}
