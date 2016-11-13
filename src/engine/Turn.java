package engine;

import static java.lang.System.out;

import board.Board;
import board.KeyMap;


/**
 * Freecell turn.
 * Alters current board when move is made.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class Turn {
	
	// static variables
	private static boolean debug = false;

	// class variables
	private boolean winnable;
	private int moveNum;
	private Board board;
	private KeyMap keymap;
	
	/**
	 * Creates a freecell turn with statistics.
	 * @param isGui true if preferred UI is gui
	 * @param move move number
	 * @param b current board
	 */
	public Turn(int move, Board b, KeyMap km) {
		
		moveNum = move;
		board = b;
		keymap = km;
		
		winnable = isWinnable();
		board.makeMove(keymap);
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
		
		if (debug) out.println("\n---engine.Turn.isWinnable---");
		//TODO run solver
		// winnable = boolean solver result
		// attach solution to turn when applicable
		return true;
	}
	
	// Utilities --------------------------------------------------------------	
	/**
	 * Turn to string.
	 */
	@Override
	public String toString() {
		
		return 	"src key: " + keymap.getSrcKey().getKey() + 
				" | dest key: " + keymap.getDestKey().getKey() + 
				" | move number: " + moveNum + 
				" | winnable: " + winnable;
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
		//TODO feed me!
	}
}
