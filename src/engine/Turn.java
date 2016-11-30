package engine;

import static java.lang.System.out;

import java.util.Queue;

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
	private Queue<KeyMap> possibleMoves;
	
	/**
	 * Creates an empty freecell turn.
	 * For use with move 0 at start of game.
	 * @param b new board
	 */
	public Turn(Board b) {
		
		if (debug) out.println("\n---engine.Turn.constructor---");
		moveNum = 0;
		board = b;
		winnable = true;
	}
	
	/**
	 * Creates a freecell turn with statistics.
	 * @param move move number
	 * @param b current board
	 * @param km valid keymap
	 */
	public Turn(int move, Board b, KeyMap km) {
		
		if (debug) out.println("\n---engine.Turn.constructor---");
		moveNum = move;
		board = b;
		keymap = km;
		
		board.makeMove(keymap);
		
		//TODO hard set until solver is in place
		// then should call isWinnable to run solver
		winnable = true;;
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
	
	/**
	 * Returns all possible moves from current turn.
	 * @return all possible moves
	 */
	public Queue<KeyMap> getPossibleMoves() {
		
		possibleMoves = board.getAllMoves();
		return possibleMoves;
	}
	
	/**
	 * Returns true if a move is possible.
	 * @return true if a move is possible
	 */
	public boolean movePossible() {
		
		possibleMoves = getPossibleMoves();
		return 
				possibleMoves == null ||
				!possibleMoves.isEmpty();
	}
	
	// Business ---------------------------------------------------------------
	/**
	 * Runs solver and sets winnable status on the turn.
	 * @return true if game from this turn is winnable
	 */
	private boolean isWinnable() {
		
		if (debug) out.println("\n---engine.Turn.isWinnable---");
		
		if (!movePossible()) {
			
			if (debug) out.println("false");
			return false;
		}
		
		if (debug) out.println("true");
		Solver solver = new Solver(this);
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
}
