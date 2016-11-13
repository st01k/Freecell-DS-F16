package solver;

public class Solver {

	private static boolean debug = false;
	
	private boolean movePossible;
	// alert user that there are no moves
	// gray out hint button
	private Solution solution;
	
	public Solver() {
		
		init();
		solution = new Solution();
	}
	
	public boolean moveIsPossible() {
		
		return movePossible;
	}
	
	public Solution getSolution() {
		return solution;
	}
	
	private void setMovePossible(boolean in) {
		
		movePossible = in;
	}
	
	private void init() {
		
		findMoves();
		//TODO create threads based on possible moves
	}
	
	private void findMoves() {
		
		//TODO find all possible moves
		
		// if moves possible
		setMovePossible(true);
	}
	
	public static void toggleDebug() {
		debug = !debug;
	}
}
