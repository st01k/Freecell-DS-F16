package engine;

import static java.lang.System.out;

import solver.Solver;
import solver.Solution;
import board.Board;
import board.KeyMap;


/**
 * Freecell turn.
 * Alters current board when move is made.
 * @author groovyLlama devteam
 * @version 0.5
 */
public class Turn {
	
	// static variables
	private static boolean debug = false;

	// class variables
	private boolean winnable;
	private int moveNum;
	private Board board;
	private KeyMap keymap;
	private Solution solution;
	
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
	/**
	 * Returns winnable status.
	 * @return true if game is winnable from current turn
	 */
	public boolean getWinnable() {
		return winnable;
	}
	
	/**
	 * Returns move number.
	 * @return move number
	 */
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
		
		Solver solver = new Solver(board);
		solution = solver.getSolution();
		
		if (solution == null) return false;
		else return true;
	}
	
	// Utilities --------------------------------------------------------------	
	/**
	 * Turn to string.
	 */
	@Override
	public String toString() {
		
		return 	
			keymap + " | move number: " + moveNum + " | winnable: " + winnable;
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
