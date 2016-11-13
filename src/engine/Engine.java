package engine;

import static java.lang.System.out;

import java.util.Scanner;
import java.util.Stack;
import client.cli.CLI;
import client.gui.FreeGUI;
import board.Board;
import board.KeyMap;

/**
 * Drives freecell game.
 * @author groovyLlama devteam
 * @version 0.3
 */
public class Engine 
{
	// static variables
	private static boolean isGui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	private static int moveNum;
	private static String src, dest;
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
		
		if (debug) out.println("\n---engine.Engine.gameLoop---");
		
		moveNum = 0;
		snapshot();
		
		gui = checkUiMode();
		
		while(!gameOver) {
			
			if (debug) out.println("\n---loop begin---");
			if (debug && isGui) printSnapshot();
			
			if (isGui) gui.Paint(curBoard);
			else out.println(curBoard);
			
			//TODO auto stacks
			
			if (isGui) {
				
				//TODO filler moves
				src = "i";
				dest = "a";
				Scanner scan = new Scanner(System.in);
				out.println("Press Enter for filler move.");
				scan.nextLine();
				// open a scanner and wait for input in method
				// in gui send strings to the method
				// scanner catches them on input and returns here
			}
			else {
				src = getSourceCLI();
				dest = getDestCLI();
			}
			
			// generate keymap here
			KeyMap keymap = new KeyMap(src, dest, curBoard);
			// check keymap
			if (keymap.isValid()) {
				
				Turn turn = new Turn(++moveNum, curBoard, keymap);
				curBoard.updateBoardStats(turn);
				snapshot();
			}		
			else { if (debug) out.println("\nIllegal Move\n"); }
		}
	}
	
	private static String getSourceCLI() {
		return CLI.inGame("source");
	}
	
	private static String getDestCLI() {
		return CLI.inGame("dest");
	}
	
	public static void getMappingGUI() {
		
		
	}
	
	// In-game Action Handlers ------------------------------------------------
	public static void newDeal() {
		
		if (debug) out.println("event: New Deal");
		
		curBoard = new Board();
		history = new Stack<Board>();
		moveNum = 0;
		snapshot();
		
		if (!isGui) out.println(curBoard);
		else gui.Paint(curBoard);
	}
	
	public static void undo() {
		
		if (debug) out.println("event: Undo");
	}
	
	public static void redo() {
		
		if (debug) out.println("event: Redo");
	}
	
	public static void hint() {
		
		if (debug) out.println("event: Hint");
	}
	
	public static void solve() {
		
		if (debug) out.println("event: Solve");
	}
	
	public static void doubleClick(String src) {
		
		if (debug) out.println("event: Double-Click (" + src + ")");
	}
	
	public static void dragDrop(String src, String dest) {
		
		if (debug) out.println("event: Drag and Drop (" + src + ", " + dest + ")");
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
		
		if (debug) out.println("\n---engine.Engine.printSnapshot---\n");
		out.println();
		out.print("******************** Begin Snapshot ********************");
		out.println(history.peek());
		
		if (debug) {
			String client = (isGui)? "GUI" : "CLI";
			out.println			 
				("\nstate [client: " + client + " | history size: " + history.size() + "]");
		}
		
		out.println("********************* End Snapshot *********************");
		out.println();
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
