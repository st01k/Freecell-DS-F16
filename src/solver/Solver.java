package solver;

import java.util.Queue;
import board.Board;
import board.KeyMap;
import engine.Turn;

/**
 * Freecell solver.
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Solver {

	private static boolean debug = false;
	
	private Queue<KeyMap> possibleMoves;
	private Turn rootTurn;
	private Board boardClone;
	private Solution solution;
	
	/**
	 * Constructs a new solver.
	 * @param t turn
	 */
	public Solver(Turn t) {
		
		rootTurn = t;
		boardClone = t.getBoard().clone();
		possibleMoves = t.getPossibleMoves();
		init();
	}
	
	/**
	 * Returns whether a move is possible on current board.
	 * @return true if move is possible
	 */
	public boolean movePossible() {
		return !possibleMoves.isEmpty();
	}
	
	/**
	 * Returns all possible moves.
	 * @return all possible moves as KeyMaps
	 */
	public Queue<KeyMap> getPossibleMoves() {
		return possibleMoves;
	}
	
	/**
	 * Returns solution to current board.
	 * @return solution
	 */
	public Solution getSolution() {
		return solution;
	}
	
	/**
	 * Returns the root turn that this solver is based on.
	 * @return root turn
	 */
	public Turn getRootTurn() {
		return rootTurn;
	}
	
	/**
	 * Returns the cloned board for use in the solver.
	 * @return cloned board
	 */
	public Board getBoardClone() {
		return boardClone;
	}
	
	/**
	 * Initializes solver.  
	 * Runs multiple threads to check for solution.
	 */
	private void init() {
		
		solution = new Solution();
		//TODO sort moves in order of priority OR compare as they enter pattern/search?
		//TODO create threads based on possible moves
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
