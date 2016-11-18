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
	 * Creates an empty freecell turn.
	 * For use with move 0.
	 * @param b new board
	 */
	public Turn(Board b) {
		
		moveNum = 0;
		board = b;
	}
	
	/**
	 * Creates a freecell turn with statistics.
	 * @param move move number
	 * @param b current board
	 * @param km valid keymap
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
	
	/**
	 * Returns the board as it looks after the keymap has processed.
	 * @return this turn's board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the key mapping for this turn.
	 * @return key map for this turn
	 */
	public KeyMap getKeymap() {
		return keymap;
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
			"turn: " + moveNum + " | winnable: " + winnable + "\n" + keymap;
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
