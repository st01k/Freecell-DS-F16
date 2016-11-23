package solver;

import static java.lang.System.out;

import java.util.Queue;
import board.Board;
import board.KeyMap;

/**
 * Freecell solver.
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Solver {

	private static boolean debug = false;
	
	private Queue<KeyMap> possibleMoves;
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
	public boolean getMovePossible() {
		
		return movePossible;
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
	 * Initializes solver.  
	 * Runs multiple threads to check for solution.
	 */
	private void init() {
		
		findMoves();
		//TODO sort moves in order of priority OR compare as they enter pattern/search?
		//TODO create threads based on possible moves
	}
	
	/**
	 * Finds possible moves.
	 */
	private void findMoves() {
		
		out.println("\n---solver.Solver.findMoves---");
		possibleMoves = board.getAllMoves();
		movePossible = (possibleMoves.isEmpty())? false : true;
		
		if (debug) {
			
			for (KeyMap k : possibleMoves) {
				out.println(k + "\n");
			}
		}
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
