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
		int[] firstRoll = new int[N_DICE];
		rollDice(firstRoll, N_DICE);
		display.displayDice(firstRoll);
		
		// display.displayDice[N_DICE];
		// display.displayDice[5];
		/* You fill this in */
	}
	
	// Method for for rolling the dice
	private int[] rollDice(int[] arr, int n) {
		for (int i=0; i<n; i++) {
			arr[i] = rgen.nextInt(1, 6);
		}
		return arr;
	}
	
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
