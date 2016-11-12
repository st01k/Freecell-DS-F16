package solver;

public class Solver {

	private static boolean debug = false;
	
	private boolean movePossible;
	// alert user that there are no moves
	// gray out hint button
	
	public Solver() {
		
		init();
	}
	
	public boolean getPossibeMove() {
		
		return movePossible;
	}
	
	private void setPossibleMove(boolean in) {
		
		movePossible = in;
	}
	
	private void init() {
		
		
	}
	
	private void findMoves() {
		
		// find all possible moves
		
	}
	
	public static void toggleDebug() {
		debug = !debug;
	}
}
