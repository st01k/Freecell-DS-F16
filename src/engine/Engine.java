package engine;

import static java.lang.System.out;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import client.cli.CLI;
import client.gui.FreeGUI;
import board.Board;
import board.KeyMap;

/**
 * Drives freecell game.
 * @author groovyLlama devteam
 * @version 0.5
 */
public class Engine 
{
	// static variables
	private static boolean isGui = false;
	private static boolean gameOver = false;
	private static boolean autoStack = false;
	private static boolean debug = false;
	private static int moveNum;
	private static String src, dest;
	private static Board curBoard;
	private static Stack<Turn> history;
	private static Stack<Turn> rvrsHistory;
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
		history = new Stack<Turn>();
		rvrsHistory = new Stack<Turn>();
		isGui = _isGui;
		gameOver = false;
				
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
		snapshot(new Turn(curBoard));
		
		gui = checkUiMode();
		
		if (autoStack) autoStack();
		while(!gameOver) {
			
			if (debug) out.println("\n---loop begin---");
			if (debug && isGui) printSnapshot();
						
			//TODO don't forget i'm here
			curBoard.calcMoveableCards();
			
			clearMapStrings();
			if (isGui) gui.Paint(curBoard);
			else out.println(curBoard);
			
			if (isGui) {
				
				//TODO filler moves
				src = "i";
				dest = "a";
				Scanner scan = new Scanner(System.in);
				out.println("Press Enter for filler move.");
				scan.nextLine();
//				guiWait();
			}
			else {
				src = getSourceCLI();
				dest = getDestCLI();
			}
			
			KeyMap keymap = new KeyMap(src, dest, curBoard);
			
			
			if (!rvrsHistory.isEmpty()) {
				// if new path is chosen, clear redo history
				if (!keymap.equals(rvrsHistory.peek())) rvrsHistory.clear();
			}
			
			if (keymap.isValid()) {
				
				Turn turn = new Turn(++moveNum, curBoard, keymap);
				curBoard.updateBoardStats(turn);
				snapshot(turn);
			}		
			else { out.println("\nIllegal Move\n"); }
			
			clearMapStrings();
			if (autoStack) autoStack();
			gameOver = checkGameOver();
		}
		
		out.println(curBoard);
		out.println("\nYou won the game!!!");
	}
	
	// Move Input -------------------------------------------------------------
	/**
	 * Calls in game cli prompt, returns source string.
	 * @return source string
	 */
	private static String getSourceCLI() {
		return CLI.inGame("source");
	}
	
	/**
	 * Calls in game cli prompt, returns destination string.
	 * @return destination string
	 */
	private static String getDestCLI() {
		return CLI.inGame("dest");
	}
	
	private static void guiWait() {
		
		clearMapStrings();		
		while (src.isEmpty() && dest.isEmpty()) {}
		
	}
	
	public static void setGuiMapping(String s, String d) {
		
		src = s;
		dest = d;
	}
	
	// In-game Action Handlers ------------------------------------------------
	/**
	 * Deals a new game, resets game stats.
	 */
	public static void newDeal() {
		
		if (debug) out.println("event: New Deal");
		
		curBoard = new Board();
		history = new Stack<Turn>();
		moveNum = 0;
		snapshot(new Turn(curBoard));
		if (autoStack) autoStack();
		
		if (isGui) gui.Paint(curBoard); 
		else out.println(curBoard);
	}
	
	/**
	 * Reverts game state to previous turn.
	 */
	public static void undo() {
		
		if (debug) out.println("event: Undo");
		
		if (history.size() <= 1) out.println("nothing to undo");
		else {
			
			Turn curTurn = history.pop();
			rvrsHistory.push(curTurn);

			curBoard.forceUpdate(rvrsHistory.peek());
			if (debug) out.println("undoing:\n" + curTurn);
			
			Turn prevTurn = history.peek();
			if (debug) out.println("restoring:\n" + prevTurn);
			moveNum = prevTurn.getMoveNum();
			curBoard.updateBoardStats(prevTurn);
			
			if (isGui) gui.Paint(curBoard); 
			else out.println(curBoard);
		}
	}
	
	/**
	 * Reapplies the most recent turn if it exists.
	 */
	public static void redo() {
		
		if (debug) out.println("event: Redo");

		if (rvrsHistory.isEmpty()) 
			out.println("nothing to redo");
		else {
			
			Turn nextTurn = rvrsHistory.pop();
			history.push(nextTurn);
			curBoard.forceUpdate(history.peek());
			if (debug) out.println("redoing:\n" + nextTurn);
			
			moveNum = nextTurn.getMoveNum();
			curBoard.updateBoardStats(nextTurn);
			
			if (isGui) gui.Paint(curBoard); 
			else out.println(curBoard);
		}
	}
	
	/**
	 * Shows next move in the solution from current turn.
	 */
	public static void hint() {
		
		if (debug) out.println("event: Hint");
		if (debug) out.println("currently unavailable");
	}
	
	/**
	 * Initiates turn movement without user interaction towards the current solution.
	 */
	public static void solve() {
		
		if (debug) out.println("event: Solve");
		if (debug) out.println("currently unavailable");
	}
	
	/**
	 * Double click confirmation.
	 * @param src source clicked (card position)
	 */
	public static void doubleClick(String src) {
		
		if (debug) out.println("event: Double-Click (" + src + ")");
	}
	
	/**
	 * Drag and drop confirmation.
	 * @param src source card clicked (card position) 
	 * @param dest destination position of card clicked
	 */
	public static void dragDrop(String src, String dest) {
		
		if (debug) out.println("event: Drag and Drop (" + src + ", " + dest + ")");
	}
	
	// Utilities --------------------------------------------------------------
	/**
	 * Processes all possible insertions into a homecell.
	 * Currently only processes all freecells and cards on the last element
	 * of their pile, not cards that open up as a result of the stacking.
	 */
	public static void autoStack() {
		
		Queue<KeyMap> autoStack = curBoard.autoStack();
		while (!autoStack.isEmpty()) {
			
			KeyMap k = autoStack.remove();
			Turn turn = new Turn(++moveNum, curBoard, k);
			curBoard.updateBoardStats(turn);
			snapshot(turn);
		}
	}
	
	/**
	 * Clears source and destingation strings.
	 */
	static void clearMapStrings() {
		
		src = "";
		dest = "";
	}
	
	/**
	 * Saves turn to history.
	 */
	public static void snapshot(Turn t) {
		history.push(t);
	}
	
	/**
	 * Prints most recent snapshot.
	 */
	public static void printSnapshot() {
		
		if (debug) out.println("\n---engine.Engine.printSnapshot---\n");
		out.println();
		out.print("******************** Begin Snapshot ********************");
		out.println(history.peek().getBoard());
		
		if (debug) {
			String client = (isGui)? "GUI" : "CLI";
			out.println			 
				("\nstate [client: " + client + " | history size: " + history.size() + "]");
		}
		
		out.println("********************* End Snapshot *********************");
		out.println();
	}
	
	/**
	 * Determines if game has been won.
	 * @return true if game has been won
	 */
	public static boolean checkGameOver() {
		
		boolean status = curBoard.winCheck();
		if (debug) out.println(status);
		return status;
	}
	
	public static void toggleAutoStack() {
		autoStack = !autoStack;
		
		String s;
		s = (autoStack)? "on" : "off";
		if (debug) out.println("auto-stack " + s);
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
