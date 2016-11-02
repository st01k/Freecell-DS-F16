package engine;

import static java.lang.System.out;

import java.util.Scanner;
import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.3
 */
public class Turn {
	
	private static Scanner scan = new Scanner(System.in);

	private boolean winnable;
	
	// not static inc as to preserve states
	// on undo/redo
	private int moveNum;
	
	// TODO needs some kind of board mapping/graph
	// going with CLI board model for now
	// with alphabetic mapping.
	// see cli board by running 'test' in cli
	// if used, will need a getCardAt(position in map)
	public Turn(int move, boolean isGui) {
		
		moveNum = move;
		if (isGui) guiTurn();
		else cliTurn();
	}
	
	/**
	 * Returns the turn's winnable status.
	 * @return true if game from this turn is winnable
	 */
	public boolean isWinnable() {
		
		//TODO runs solver = winnable
		return winnable;
	}
	
	public int getMoveNum() {
		return moveNum;
	}
	
	/**
	 * Moves a card from its source to a destination.
	 * @param src source position
	 * @param dest destination position
	 * @return true if card was successfully moved
	 */
	private boolean moveCard(Character src, Character dest) {
		
		//TODO feed me!
		return false;
	}
	
	/**
	 * Prompts user for turn input.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public boolean cliTurn() {
		
		//TODO add validation
		out.println("\nEnter source position: ");
		String src = scan.nextLine();
		out.println("Enter destination position: ");
		String dest = scan.nextLine();
		
		return false;
	}
	
	/**
	 * Waits for user turn input from gui.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public boolean guiTurn() {
		
		// TODO feed me!
		return false;
	}
}
