package engine;

import static java.lang.System.out;

import java.util.Stack;
import client.cli.CLI;
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
	private static String src, dest;
	private static int moveNum;
	
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
	private static FreeGUI checkUiMode() {
		
		if (debug) out.println("\n---engine.Engine.checkUiMode---");
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
		
		moveNum = 0;
		snapshot();
		
		FreeGUI gui = checkUiMode();
		
		while(!gameOver) {
			
			if (debug) out.println("---inside game loop---");
			if (debug && isGui) printSnapshot();
			
			if (isGui) {
				
				gui.Paint(curBoard);
			}
			else out.println(curBoard);
			
			//TODO auto stacks
			
			if (isGui) {
				
				// needs to be attached to mouse events
				// and GUI setters
				//TODO filler moves
				src = "i";
				dest = "a";
			}
			else {
				src = getSourceCLI();
				dest = getDestCLI();
			}
			
			//TODO if illegal move, don't inc moveNum
			Turn turn = new Turn(isGui, ++moveNum, curBoard, src, dest);
			curBoard.updateBoardStats(turn);
			
			snapshot();
		}
	}
	
	private static String getSourceCLI() {
		return CLI.inGame("source");
	}
	
	private static String getDestCLI() {
		return CLI.inGame("dest");
	}
	
	public static void setSourceGUI(String in) {
		src = in;
	}
	
	public static void setDestGUI(String in) {
		dest = in;
	}
	
	// In-game Action Handlers ------------------------------------------------
	public static void newDeal() {
		if (debug) out.println("event: New Deal clicked");
		
		curBoard = new Board();
		history = new Stack<Board>();
		moveNum = 0;
		snapshot();
		if (!isGui) out.println(curBoard);
		// else gui.paint
	}
	
	public static void undo() {
		if (debug) out.println("event: Undo clicked");
	}
	
	public static void redo() {
		if (debug) out.println("event: Redo clicked");
	}
	
	public static void hint() {
		if (debug) out.println("event: Hint clicked");
	}
	
	public static void solve() {
		if (debug) out.println("event: Solve clicked");
	}
	
	//TODO automate free and home cell entry with double click
	
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
