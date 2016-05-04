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
		display.waitForPlayerToClickRoll(nPlayers);
		rollDiceThreeTimes();
		
	}
	
	private void rollDiceThreeTimes(){
		
		int[] dice = new int[N_DICE];
		firstRollDice(dice, N_DICE);
		display.displayDice(dice);

		checkUserChoice(dice, N_DICE);
		display.displayDice(dice);
		
		checkUserChoice(dice, N_DICE);
		display.displayDice(dice);
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
	
	
	

	
	
	
	
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}


































































