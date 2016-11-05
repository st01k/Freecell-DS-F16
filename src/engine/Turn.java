package engine;

import static java.lang.System.out;

import java.util.Scanner;
import utils.SysUtils;
import board.Board;


/**
 * Freecell turn.
 * Alters current board when move is made.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class Turn {
	
	// static variables
	private static Scanner scan = new Scanner(System.in);
	private static boolean debug = false;

	// class variables
	private boolean winnable;
	private int moveNum;
	private String turnString;
	private Board board;
	private String srcKey;
	private String destKey;
	
	/**
	 * Creates a freecell turn with statistics.
	 * @param isGui true if preferred UI is gui
	 * @param move move number
	 * @param b current board
	 */
	public Turn(boolean isGui, int move, Board b, String src, String dest) {
		
		moveNum = move;
		board = b;
		srcKey = src;
		destKey = dest;
		winnable = isWinnable();
		
		if (isGui) guiTurn();
		else cliTurn();
	}
	
	// Accessors --------------------------------------------------------------
	public boolean getWinnable() {
		return winnable;
	}
	
	public int getMoveNum() {
		return moveNum;
	}
	
	// Business ---------------------------------------------------------------
	/**
	 * Runs solver and sets winnable status on the turn.
	 * @return true if game from this turn is winnable
	 */
	private boolean isWinnable() {
		
		//TODO run solver
		// winnable = boolean solver result
		// attach solution to turn when applicable
		return true;
	}
	
	// Turn Actions -----------------------------------------------------------
	/**
	 * Prompts user for turn input.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	private void cliTurn() {
		
		if (debug) out.println("\n---engine.Turn.cliTurn---");
		if (debug) out.println(this);
		
		if (!board.makeMove(srcKey, destKey)) 
			out.println("invalid move detected in engine.Turn.cliTurn");
	}
	
	//TODO automate free and home cell entry with double click
	// so that cards go into next available slot
	/**
	 * Waits for user turn input from gui.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public void guiTurn() {
		
		if (debug) out.println("\n---engine.Turn.guiTurn---");		
		if (debug) out.println(this);
		
		if (!board.makeMove(srcKey, destKey)) 
			out.println("invalid move detected in engine.Turn.guiTurn");
		
		// added to stop infinite loop in engine
		if (debug) out.println("\nGUI turn is empty.  Press enter.");
		scan.nextLine();
	}
	
	// Utilities --------------------------------------------------------------	
	/**
	 * Turn to string.
	 */
	@Override
	public String toString() {
		
		return "src key: " + srcKey + " | dest key: " + destKey + 
				" | move number: " + moveNum + " | winnable: " + winnable;
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
	
	/**
	 * Unit test.
	 */
	public static void unitTest() {
		//TODO add me to tester
	}
}
