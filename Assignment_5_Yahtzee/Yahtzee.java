/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		
		playGame();
	}

	private void playGame() {

		score = initScore();
		while(true) {playOneTurn();}
		
		/* display.printMessage("Press \"Roll dice\" to roll the dice.");

		display.waitForPlayerToClickRoll(nPlayers);
		rollDiceThreeTimes();
		
		// trial  
		int category = display.waitForPlayerToSelectCategory();
		display.updateScorecard(category, 1, 10); */
	}
	
	private void playOneTurn() {
		for (int i=1; i<nPlayers+1; i++) {
			display.printMessage("Press \"Roll dice\" to roll the dice.");
			
			display.waitForPlayerToClickRoll(i);
			dice = new int[5];
			dice = rollDiceThreeTimes();
			
			// prevent user to overwrite existent result
			while (true) {
				category = display.waitForPlayerToSelectCategory();
				if (score[i-1][category-1] == 0) break;
			} 
			
			//checkForCategory();
			if (checkForCategory()) {
				turnScore = calculateScore(dice);
				score[i-1][category-1] = turnScore;
				score[i-1][16] += turnScore;
				int playerTotal = score[i-1][16];
				display.updateScorecard(category, i, turnScore);
				display.updateScorecard(17, i, playerTotal);
				if (1<=category && category<=6) {
					score[i-1][6] += turnScore;
					display.updateScorecard(7, i, score[i-1][6]);
					if (score[i-1][6] > 60) {
						score[i-1][7] = 35;
						display.updateScorecard(8, i, 35);
						score[i-1][7] += 35;
					}
				}				
			} else {
				display.updateScorecard(category, i, 0);
			}
		}
	}
	
	
	private /*void*/ int[] rollDiceThreeTimes(){
		int[] dice = new int[N_DICE];
		firstRollDice(dice, N_DICE);
		display.displayDice(dice);
		display.printMessage("Choose your dice to re-roll, and roll again.");

		checkUserChoice(dice, N_DICE);
		display.displayDice(dice);
		display.printMessage("Choose your dice to re-roll, and roll again.");
		
		checkUserChoice(dice, N_DICE);
		display.displayDice(dice);
		
		return dice;
	}
	
	// Method for for first rolling of the dice
	private int[] firstRollDice(int[] arr, int n) {
		for (int i=0; i<n; i++) {
			arr[i] = rgen.nextInt(1, 6);
		}
		return arr;
	}
	
	/*
	 * Method for second and third rolling of the dice
	 * Checking the user's choice, and roll 
	 * (random generate the values) for the dice
	 * 
	 */
	private int[] checkUserChoice(int[] arr, int n) {
		display.waitForPlayerToSelectDice();
		for (int i=0; i<n; i++) {
			if (display.isDieSelected(i)) {
				arr[i] = rgen.nextInt(1, 6);
			}
		}
		return arr;
	}
		
	/*private int[] rollOneDice(int[] arr, int i) {
		arr[i] = rgen.nextInt(1, 6);
		return arr;
	}*/
	
	private boolean checkForCategory() {
		if (YahtzeeMagicStub.checkCategory(dice, category)) {
			return true;
		} else {return false;}
	}
	
	
	private int calculateScore (int[] dice) {
				
		turnScore = 0;
		if (category == 1) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 1) {turnScore ++;}
			}
		} 
		
		else if (category == 2) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 2) {turnScore = turnScore+2;}
			}
		}
		
		else if (category == 3) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 3) {turnScore = turnScore+3;}
			}
		}
		
		else if (category == 4) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 4) {turnScore = turnScore+4;}
			}
		}
		
		else if (category == 5) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 5) {turnScore = turnScore+5;}
			}
		}
		
		else if (category == 6) {
			for (int i=0; i<5; i++) {
				if (dice[i] == 6) {turnScore = turnScore+6;}
			}
		}
		
		// three of a kind
		else if (category == 9) {
			for (int i=0; i<5; i++) {
				turnScore += dice[i];
			}
		}
		
		// four of a kind
		else if (category == 10) {
			for (int i=0; i<5; i++) {
				turnScore += dice[i];
			}
		}
		
		// full house
		else if (category ==11) {
			turnScore = 25;
		}
		
		// small straight
		else if (category ==12) {
			turnScore = 30;
		}
		
		// large straight
		else if (category ==13) {
			turnScore = 40;
		}
		
		// yahtzee!
		else if (category ==14) {
			turnScore = 50;
		}
		
		// chance
		else if (category == 15) {
			for (int i=0; i<5; i++) {
				turnScore += dice[i];
			}
		}
		
		// to see eventual mistake
		else turnScore = 77;
		
		return turnScore;

	}
	
	
	
	
	
	// initializing the 2-dimensional score array
	private int[][] initScore() {
		score = new int[nPlayers][17];
		for (int i=0; i<nPlayers; i++) {
			for (int j=0; j<17; j++) {
				score[i][j] = 0;
			}
		}
		return score;
	}
	
			
/* Private instance variables */
	private int category;
	private int[] dice;
	private int[][] score;
	private int turnScore; // score per turn - calculated or 0
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}


































































