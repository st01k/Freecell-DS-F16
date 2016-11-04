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
	
	// Initialization ---------------------------------------------------------
	/**
	 * Creates board and solver.
	 * Starts game in preferred user interface.
	 * @param isGui true if UI is gui
	 */
	public static void start(boolean _isGui) {
		
		if (debug) out.println("\n---engine.Engine.start---");
		
		curBoard = new Board();
		history = new Stack<Board>();
		isGui = _isGui;
				
		gameLoop();
	}
	
	/**
	 * Initializes GUI if in GUI mode.
	 * @return gui, null if CLI mode
	 */
	private static FreeGUI startGUI() {
		
		if (debug) out.println("\n---engine.Engine.startGUI---");
		if (isGui) {
			
			FreeGUI gui = new FreeGUI();
			gui.start();
			return gui;
		}
		return null;
	}

	// Game loop --------------------------------------------------------------
	/**
	 * Main game loop.
	 */
	private static void gameLoop() {
		
		if (debug) out.println("---engine.Engine.gameLoop---");
		
		int moveNum = 0;
		snapshot();
		
		FreeGUI gui = startGUI();
		
		while(!gameOver) {
			
			if (debug) out.println("---inside game loop---");
			if (debug) printSnapshot();
			
			if (isGui) {
				
				gui.Paint(curBoard);
			}
			else out.println(curBoard);
			
			//TODO auto stacks
			Turn turn = new Turn(isGui, ++moveNum, curBoard);
			curBoard.updateBoardStats(turn);
			
			snapshot();
		}
	}
	
	// In-game Action Handlers ------------------------------------------------
	public static void newDeal() {
		
	}
	
	public static void undo() {
		
	}
	
	public static void redo() {
		
	}
	
	public static void hint() {
		
	}
	
	public static void solve() {
		
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
