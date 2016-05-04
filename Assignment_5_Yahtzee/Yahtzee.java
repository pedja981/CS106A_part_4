/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.awt.event.*;
import java.util.*;


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
			if (checkForCategory(dice, category)) {
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
	
	/*private boolean checkForCategory() {
		if (YahtzeeMagicStub.checkCategory(dice, category)) {
			return true;
		} else {return false;}
	}*/
	
	
	
	
	

	/* Pre-condition: The player has finished rolling the dice and selects a category. 
	 * This method returns true if the selected category matches 
	 * to the actual category correctly, and false if it does not match. */
	private boolean checkForCategory(int[] dice, int category) {
		boolean categoryMatch = false;
		if(category >= ONES && category <= SIXES || category == CHANCE) {
			categoryMatch = true;
		}
		else {
			
			//creates an array for each possible dice value (1-6)
			ArrayList <Integer> ones = new ArrayList<Integer>();  
			ArrayList <Integer> twos = new ArrayList<Integer>(); 
			ArrayList <Integer> threes = new ArrayList<Integer>(); 
			ArrayList <Integer> fours = new ArrayList<Integer>(); 
			ArrayList <Integer> fives = new ArrayList<Integer>(); 
			ArrayList <Integer> sixes = new ArrayList<Integer>();
			
			/*goes through each rolled die and puts 1 as a place-holder into the appropriate ArrayList
			* e.g. if the first die value is 1, then 1 is added to the ones ArrayList or
			* if the second die value is 5, then 1 is added to the fives ArrayList*/
			for(int i = 0; i < N_DICE; i++) {
				if(dice[i] == 1) {
					ones.add(1);
				}
				else if(dice[i] == 2) {
					twos.add(1);
				}
				else if(dice[i] == 3) {
					threes.add(1);
				}
				else if(dice[i] == 4) {
					fours.add(1);
				}
				else if(dice[i] == 5) {
					fives.add(1);
				}
				else if(dice[i] == 6) {
					sixes.add(1);
				}
			}
			if(category == THREE_OF_A_KIND) {
				if(ones.size() >= 3 || twos.size() >= 3 || threes.size() >= 3 || fours.size() >= 3 || fives.size() >= 3 || sixes.size() >= 3) {
					categoryMatch = true;
				}
			}	
			else if(category == FOUR_OF_A_KIND) { 
				if(ones.size() >= 4 || twos.size() >= 4 || threes.size() >= 4 || fours.size() >= 4 || fives.size() >= 4 || sixes.size() >= 4) {
					categoryMatch = true;
				}
			}
			else if(category == YAHTZEE) {
				if(ones.size() == 5 || twos.size() == 5 || threes.size() == 5 || fours.size() == 5 || fives.size() == 5 || sixes.size() == 5) {
					categoryMatch = true;
				}
			}
			else if(category == FULL_HOUSE) {
				if(ones.size() == 3 || twos.size() == 3 || threes.size() == 3 || fours.size() == 3 || fives.size() == 3 || sixes.size() == 3) {
					if(ones.size() == 2 || twos.size() == 2 || threes.size() == 2 || fours.size() == 2 || fives.size() == 2 || sixes.size() == 2) {
						categoryMatch = true;
					}
				}
			}	
			else if(category == LARGE_STRAIGHT) { 
				if(ones.size() == 1 && twos.size() == 1 && threes.size() == 1 && fours.size() == 1 && fives.size() == 1){
					categoryMatch = true;
				}
				else if(twos.size() == 1 && threes.size() == 1 && fours.size() == 1 && fives.size() == 1 && sixes.size() == 1) {
					categoryMatch = true;
				}
			}
			else if(category == SMALL_STRAIGHT) { 
				if(ones.size() >= 1 && twos.size() >= 1 && threes.size() >= 1 && fours.size() >= 1) {
					categoryMatch = true;
				}
				else if(twos.size() >= 1 && threes.size() >= 1 && fours.size() >= 1 && fives.size() >= 1) {
					categoryMatch = true;
				}
				else if(threes.size() >= 1 && fours.size() >= 1 && fives.size() >= 1 && sixes.size() >= 1) {
					categoryMatch = true;
				}
			}
		}
		return categoryMatch;
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
	private int[][] score; // score array for every player
	private int turnScore; // score per turn - calculated or 0
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
