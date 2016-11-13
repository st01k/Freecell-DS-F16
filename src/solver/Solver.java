package solver;

import board.Board;
import board.KeyMap;

/**
 * Freecell solver.
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Solver {

	private static boolean debug = false;
	
	private KeyMap[] possibleMoves;
	private Board board;
	private boolean movePossible;
	// alert user that there are no moves
	// gray out hint button
	private Solution solution;
	
	/**
	 * Constructs a new solver.
	 * @param b
	 */
	public Solver(Board b) {
		
		board = b;
		init();
		solution = new Solution();
	}
	
	/**
	 * Returns whether a move is possible on current board.
	 * @return true if move is possible
	 */
	public boolean moveIsPossible() {
		
		return movePossible;
	}
	
	/**
	 * Returns solution to current board.
	 * @return solution
	 */
	public Solution getSolution() {
		return solution;
	}
	
	/**
	 * Sets whether a move is possible on current board.
	 * @param in whether moves are available
	 */
	private void setMovePossible(boolean in) {
		
		movePossible = in;
	}
	
	/**
	 * Initializes solver.  
	 * Runs multiple threads to check for solution.
	 */
	private void init() {
		
		findMoves();
		//TODO create threads based on possible moves
	}
	
	/**
	 * Finds possible moves.
	 */
	private void findMoves() {
		
		//TODO find all possible moves
		// assign their keymap to possibleMoves array
		
		// if moves possible
		setMovePossible(true);
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
