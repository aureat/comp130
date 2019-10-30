/* STUDENT NAME: Altun Hasanli
 * File: SuperKarelBro.java
 */

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import stanford.karel.SuperKarel;

public class SuperKarelBros extends SuperKarel {

	public void run() {

		playThemeSong(THEME_SONG);

		// Your code starts here.
		trimTree();
		reverseStairs();
		karelBreaksBricks();
		travelThroughPipes();
		climbTower();
		normalStairs();
		climbFlagpole();
		intoTheFortress();
		// Your code ends here.

		playVictorySong(VICTORY_SONG);
	}

	/** Helper Methods */
	// Your code starts here.
	
	private void findPinkTower() {
		while(!cornerColorIs(PINK)) move();
	}

	private void moveUnlessBlocked() {
		while (frontIsClear()) move();
	}
	
	private void trimTree() {
		moveUnlessBlocked();
		trimLeaves();
		doubleCheckAndTrim();
	}
	
	private void trimLeaves() {
		turnLeft();
		while (frontIsClear()) {
			if(cornerColorIs(RED)) paintCorner(CYAN);
			if(rightIsClear()) turnRight();
			move();
		}
	}
	
	private void doubleCheckAndTrim() {
		if(cornerColorIs(RED)) paintCorner(CYAN);
		turnLeft();
	}
	
	private void reverseStairs() {
		moveUnlessBlocked();
		flyToStairTop();
		reverseClimbStairs();
	}
	
	private void flyToStairTop() {
		turnLeft();
		while (!rightIsClear()) move();
		turnRight();
		move();
	}
	
	private void reverseClimbStairs() {
		move();
		while (rightIsClear()) {
			turnRight();
			move();
			turnLeft();
			move();
		}
	}
	
	private void karelBreaksBricks() {
		findBricks();
		breakBricks();
		exitBricks();
	}
	
	private void findBricks() {
		while(frontIsClear() && !rightIsClear()) move();
	}
	
	private void breakBricks() {
		while (true) {
			move();
			turnLeft();
			while(!cornerColorIs(YELLOW)) move();
			paintCorner(CYAN);
			turnAround();
			if(beepersPresent()) {
				pickBeeper();
				break;
			}
			moveUnlessBlocked();
			turnLeft();
		}
	}
	
	private void exitBricks() {
		moveUnlessBlocked();
		turnLeft();
	}
		
	private void travelThroughPipes() {
		moveUnlessBlocked();
		getUpPipes();
		coordinateThroughPipes();
		climbDownPipes();
	}
	
	private void climbDownPipes() {
		turnRight();
		move();
		turnRight();
		moveUnlessBlocked();
		turnLeft();
	}
	
	private void getUpPipes() {
		turnLeft();
		while(!rightIsClear()) move();
		turnRight();
		move();
		turnRight();
	}
	
	private void coordinateThroughPipes() {
		move();
		while(cornerColorIs(GREEN)) {
			while(beepersPresent()) pickBeeper();
			if(!frontIsClear() && leftIsClear()) turnLeft();
			if(!frontIsClear() && rightIsClear()) turnRight();
			move();
		}
	}
	
	private void climbTower() {
		findPinkTower();
		clearBottomHalf();
		clearTopHalf();
	}
	
	private void clearBottomHalf() {
		turnLeft();
		while (cornerColorIs(PINK) || beepersPresent()) { 
			if(beepersPresent() && !cornerColorIs(BLACK)) {
				pickBeeper();
				while(!cornerColorIs(BLACK)) {
					if(!facingNorth()) turnAround();
					move();
				}
			} else {
				if(!facingNorth()) turnAround();
				move();
				if(cornerColorIs(BLACK)) {
					moveUnlessBlocked();
					turnAround();
					break;
				}
			}
			if(cornerColorIs(BLACK)) {
				putBeeper();
				turnAround();
				moveUnlessBlocked();
			}
		}
	}
	
	private void clearTopHalf() {
		while (cornerColorIs(PINK) || beepersPresent()) { 
			if(beepersPresent() && !cornerColorIs(BLACK)) {
				pickBeeper();
				while(!cornerColorIs(BLACK)) {
					if(!facingSouth()) turnAround();
					move();
				}
			} else {
				if(!facingSouth()) turnAround();
				move();
				if(cornerColorIs(BLACK)) {
					break;
				}
			}
			if(cornerColorIs(BLACK)) {
				putBeeper();
				turnAround();
				moveUnlessBlocked();
			}
		}
		
		moveUnlessBlocked();
		turnLeft();
	}
	
	private void normalStairs() {
		moveUnlessBlocked();
		climbStairs();
		exitStairs();
	}
	
	private void climbStairs() {
		while(!frontIsClear()) {
			turnLeft();
			move();
			turnRight();
			move();
		}
	}
	
	private void exitStairs() {
		move();
		turnRight();
		moveUnlessBlocked();
		turnLeft();
	}
	
	private void climbFlagpole() {
		findFlagpole();
		findFlag();
		lowerFlag();
		exitFlagpole();
	}
	
	private void findFlagpole() {
		moveUnlessBlocked();
		turnLeft();
		move();
		turnRight();
		move();
		move();
		turnLeft();
	}
	
	private void findFlag() {
		while(!cornerColorIs(BLUE)) move();
		turnAround();
	}
	
	private void lowerFlag() {
		pickBeeper();
		paintCorner(CYAN);
		move();
		while(rightIsBlocked()) {
			paintCorner(BLUE);
			putBeeper();
			pickBeeper();
			paintCorner(CYAN);
			move();
		} 
	}
	
	private void exitFlagpole() {
		turnLeft();
		move();
		turnRight();
		move();
		turnLeft();
	}
	
	private void intoTheFortress() {
		while(!cornerColorIs(WHITE)) {
			move();
		}
	}
	
	// Your code ends here.

	/** ----- Do not change anything below here. ----- */

	private void playThemeSong(String fileLocation) {
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void playVictorySong(String fileLocation) {
		try {
			clip.close();
			inputStream.close();
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip.open(inputStream);
			clip.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	private static final String THEME_SONG = "theme.wav";
	private static final String VICTORY_SONG = "victory.wav";
	private Clip clip;
	private AudioInputStream inputStream;

}
