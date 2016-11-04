package engine;

import static java.lang.System.out;

import java.util.Stack;
import client.gui.FreeGUI;
import board.Board;

/**
 * Drives freecell game.
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Engine 
{
	// static variables
	private static boolean isGui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	private static Board curBoard;
	private static Stack<Board> history;
	private static FreeGUI gui;
	
	// Initialization ---------------------------------------------------------
	/**
	 * Creates board and solver.
	 * Starts game in preferred user interface.
	 * @param isGui true if UI is gui
	 */
	public static void start(boolean _isGui) {
		
		curBoard = new Board();
		history = new Stack<Board>();
		isGui = _isGui;
		
		if (isGui) gui = new FreeGUI();
		
		gameLoop();
	}
	
	

	// Game loop --------------------------------------------------------------
	/**
	 * Main game loop.
	 */
	private static void gameLoop() {
		
		int moveNum = 0;
		snapshot();
		
		if (isGui) gui.start();		
		
		while(!gameOver) {
			
			if (debug) out.println("\n---engine.Engine.gameLoop---");
			if (debug) printSnapshot();
			
			if (isGui) gui.Paint(curBoard);
			else out.println(curBoard);
			
			//TODO auto stacks
			Turn turn = new Turn(isGui, ++moveNum, curBoard);
			curBoard.updateBoardStats(turn);
			snapshot();
		}
	}
	
	// In-game Action Handlers ------------------------------------------------
	private static void newDeal() {
		
	}
	
	private static void undo() {
		
	}
	
	private static void redo() {
		
	}
	
	private static void hint() {
		
	}
	
	private static void solve() {
		
	}
	
	// Utilities --------------------------------------------------------------
	/**
	 * Saves current board to history.
	 */
	public static void snapshot() {
		history.push(curBoard);
	}
	
	/**
	 * Prints most recent snapshot.
	 */
	public static void printSnapshot() {
		
		out.println("\n---engine.Engine.printSnapshot---\n");
		out.print("*************** Begin Snapshot ***************");
		out.println(history.peek());
		out.println("\nstate: gui - " + isGui + " | history size: " + history.size());
		out.println("**************** End Snapshot ****************");
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
