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
			
			category = display.waitForPlayerToSelectCategory();
			
			//checkForCategory();
			if (checkForCategory()) {
				turnScore = calculateScore(dice);
				display.updateScorecard(category, i, turnScore);
			} else {
				display.updateScorecard(category, i, 0);
			}
			//display.updateScorecard(category, i, 10);
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
		turnScore = 10;
		return turnScore;
	}
	
	
	/*for (int range = 0; range<=10; range++) {
		String label;
		switch (range) {
		case 0: label = "00-09"; break;
		case 10: label = "100"; break;
		default: label = (10*range)+"-"+(10*range+9); break;
		}
		String stars = printStars(arrayHistogram[range]);
		println(label + ": " + stars);
	}*/
	
	
	
	
	
	
	
	
	
	
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


































































