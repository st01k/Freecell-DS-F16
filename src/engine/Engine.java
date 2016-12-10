package engine;

import static java.lang.System.out;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import client.cli.CLI;
import client.gui.FreeGUI;
import board.Board;
import board.Key;
import board.KeyMap;
import board.PlayingPile;

/**
 * Drives freecell game.
 * @author groovyLlama devteam
 * @version 1.0
 */
public class Engine {
	
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
	 * @param _isGui true if UI is gui
	 */
	public static void start(boolean _isGui) {
		
		if (debug) out.println("\n---engine.Engine.start---");
		
		curBoard = new Board();
		history = new Stack<Turn>();
		rvrsHistory = new Stack<Turn>();
		isGui = _isGui;
		gameOver = false;
		moveNum = 0;
		gui = checkUiMode();
		snapshot(new Turn(curBoard));
		
		gameLoop();
	}
	
	/**
	 * Re-initializes the board to turn 0 with a new deal.
	 */
	private static void reinitialize() {
	
		curBoard = new Board();
		history = new Stack<Turn>();
		rvrsHistory = new Stack<Turn>();
		gameOver = false;
		moveNum = 0;
		Turn turn = new Turn(curBoard);
		snapshot(turn);
		updateStats(turn);
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
		
		String winMsg = "You won the game!!!";
		
		if (isGui) FreeGUI.simClick();
		
		if (autoStack) autoStack();
		while (isGui || !gameOver) {
			
			if (debug) out.println("\n---loop begin---");
			
			clearMapStrings();
			refresh();
			
			if (isGui) guiWait();
			else {
				src = getSourceCLI();
				dest = getDestCLI();
			}
			
			KeyMap keymap = new KeyMap(src, dest, curBoard);
			
			// if redo history is not empty
			if (!rvrsHistory.isEmpty()) {
				// check for new path.  if it's new, clear redo history
				if (!keymap.equals(rvrsHistory.peek())) rvrsHistory.clear();
			}
			
			if (keymap.isValid()) {
				
				Turn turn = new Turn(++moveNum, curBoard, keymap);
				updateStats(turn);
				snapshot(turn);
				if (isGui) FreeGUI.consoleOut("");
			}		
			else {
				
				out.println("\nIllegal Move");
				if (isGui) FreeGUI.consoleOut("Illegal Move");
			}
			
			if (autoStack) autoStack();
			gameOver = checkGameOver();
			
			// if gui, stays in loop
			if (isGui && gameOver) {
				
				refresh();
				if (isGui) FreeGUI.consoleOut(winMsg);
			}
			
			System.gc();
		}
		
		// if cli, exits to main prompt
		out.println(curBoard);
		out.println("\n" + winMsg);
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
	
	// Loop Pauses and Controls -----------------------------------------------
	/**
	 * Clears src and dest strings and waits for
	 * gui event to fill those values to return.
	 */
	private static void guiWait() {
		
		if (debug) out.println("\n---engine.Engine.guiWait---");
		
		while (dest.matches("")) {}
		
		if (debug) {
			
			out.println("\n---guiWait---");
			out.println("gui done waiting");
			out.println("src: " + src + ", dest: " + dest);
		}
	}
	
	/**
	 * Sets source string for keymap
	 * creation and validation.
	 * @param key key value of source
	 */
	public static void setSource(String key) {
		if (!key.matches("")) src = key;
	}
	
	/**
	 * Sets destination string for keymap
	 * creation and validation.
	 * @param key key value of destination
	 */
	public static void setDest(String key) {
		dest = key;
	}
	
	// In-game Action Handlers ------------------------------------------------
	/**
	 * When 'New Deal' button is pressed this executes.
	 * Re-initializes the board with a new game and refreshes the output.
	 */
	public static void newDeal() {
		
		if (debug) out.println("event: New Deal");
		
		reinitialize();
		if (autoStack) autoStack();
		
		refresh();
		if (isGui) FreeGUI.consoleOut("");
	}
	
	/**
	 * When 'Undo' button is pressed this executes.
	 * Reverts game state to previous turn.
	 */
	public static void undo() {
		
		if (debug) out.println("event: Undo");
		
		if (history.size() <= 1) {
			
			if (isGui) FreeGUI.consoleOut("Nothing to Undo");
			if (!isGui || (isGui && debug)) out.println("Nothing to Undo");
		}
		else {
			
			Turn curTurn = history.pop();
			rvrsHistory.push(curTurn);

			curBoard.forceUpdate(rvrsHistory.peek());
			if (debug) out.println("undoing:\n" + curTurn);
			
			Turn prevTurn = history.peek();
			if (debug) out.println("restoring:\n" + prevTurn);
			
			moveNum = prevTurn.getMoveNum();
			updateStats(prevTurn);
			
			refresh();
		}
	}
	
	/**
	 * When 'Redo' button is pressed this executes.
	 * Reapplies the most recent turn if it exists.
	 */
	public static void redo() {
		
		if (debug) out.println("event: Redo");

		if (rvrsHistory.isEmpty()) {
			
			if (isGui) FreeGUI.consoleOut("Nothing to Redo");
			if (!isGui || (isGui && debug)) out.println("Nothing to Redo");
		}
		else {
			
			Turn nextTurn = rvrsHistory.pop();
			history.push(nextTurn);
			
			curBoard.forceUpdate(history.peek());
			if (debug) out.println("redoing:\n" + nextTurn);
			
			moveNum = nextTurn.getMoveNum();
			updateStats(nextTurn);
			
			refresh();
		}
	}
	
	/**
	 * When 'Hint' button is pressed this executes.
	 * Displays possible moves on current board.
	 */
	public static LinkedList<Key> hint() {
		
		if (debug) out.println("event: Hint");
		
		String sGui = "No Moves Possible";
		Queue<KeyMap> moves = history.peek().getPossibleMoves();
		LinkedList<Key> keyList = new LinkedList<Key>();
		
		if (moves.isEmpty()) {
			
			out.println(sGui);
			if (isGui) FreeGUI.consoleOut(sGui);
		}
		else {
			
			sGui = "";
			for (KeyMap k : moves) {
				
				Key s = k.getSrcKey();
				Key d = k.getDestKey();
				keyList.add(s);
				
				if (!isGui || (isGui && debug)) {
					
					sGui += (s.isFreecell())? "Freecell #" : "Pile #";
					sGui += (s.getPosition() + 1) + " --> ";
					
					if (d.isFreecell()) sGui += "Freecell";
					if (d.isHomecell()) sGui += "Homecell";
					if (d.isPlayingPile()) sGui += "Pile #" + (d.getPosition() + 1);
					
					sGui += "\n";
				}
			}
			out.println(sGui);
		}
		return keyList;
	}
	
	/**
	 * When 'Solve' button is pressed this executes.
	 * Initiates turn movement without user interaction towards the current solution.
	 */
	public static void solve() {
		
		if (debug) out.println("event: Solve");
		if (debug) out.println("Currently Unavailable");
		if (isGui) FreeGUI.consoleOut("Currently Unavailable");
	}
	
	/**
	 * Double click action and confirmation.
	 * Searches for valid homecell placement.  If none is
	 * found, searches for valid freecell placement.  Sets
	 * the source and destination appropriately.
	 * @param s source clicked (position key string)
	 */
	public static void doubleClick(String s) {
		
		src = s;
		String homecell = "e";
		String freecell = "a";
		
		KeyMap k = new KeyMap(s, homecell, curBoard);
		
		if (debug) out.println("event: Double-Click (" + k.getSourceCard() + ")");
		
		// if valid, destination to homecell
		if (k.isValid()) {
			dest = homecell;
		}
		// try destination to freecell
		else {
			k = new KeyMap(s, freecell, curBoard);
			if (k.isValid()) dest = freecell;
		}
	}
	
	/**
	 * Moves a sequence of cards from one pile to another pile.
	 * @param src source pile key string
	 * @param dest destination pile key string
	 * @param index index of the card clicked
	 * @return true if the cards from index to top of pile can be moved as a group
	 */
	public static boolean seqMove(String src, String dest, int index) {
		
		//TODO add functionality
		int cardsCanMove = curBoard.calcMoveableCards();
		PlayingPile p = curBoard.getPileByKey(src);
		int cardsTryMove = p.size() - index;
		
		if (cardsCanMove >= cardsTryMove) return true;
		
		return false;
	}
	
	// Accessors --------------------------------------------------------------
	/**
	 * Returns source string.
	 * @return source string
	 */
	public static String getSource() {
		return src;
	}
	
	/**
	 * Returns destination string.
	 * @return destination string
	 */
	public static String getDest() {
		return dest;
	}
	
	// Utilities --------------------------------------------------------------
	/**
	 * Update board stats, and if in gui mode, update gui stats.
	 * @param turn most recent turn
	 */
	static void updateStats(Turn turn) {
		
		curBoard.updateBoardStats(turn);
		if (isGui) {
			
			FreeGUI.setMoveNumber(turn.getMoveNum());
			FreeGUI.setWinnable(turn.getWinnable());
		}
	}
	
	/**
	 * Prints or paints current board according to global perferences.
	 */
	static void refresh() {
		
		if (isGui) gui.Paint(curBoard); 
		else out.println(curBoard);
		if (debug && isGui) printSnapshot();
	}
	
	/**
	 * Processes all possible insertions into a homecell.
	 */
	static void autoStack() {
		
		if (debug) out.println("\n---engine.Engine.autoStack--- BEGIN");
		
		clearMapStrings();
		Queue<KeyMap> autoStack = curBoard.toHome();
		while (!autoStack.isEmpty()) {
			
			KeyMap k = autoStack.remove();
			Turn turn = new Turn(++moveNum, curBoard, k);
			updateStats(turn);
			snapshot(turn);
			autoStack = curBoard.toHome();
		}
		gameOver = checkGameOver();
		
		if (debug) out.println("\n---engine.Engine.autoStack--- END");
	}
	
	/**
	 * Clears source and destination strings.
	 */
	static void clearMapStrings() {
		
		src = "";
		dest = "";
	}
	
	/**
	 * Saves turn to history.
	 */
	static void snapshot(Turn t) {
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
				("state [client: " + client + " | history size: " + history.size() + "]");
		}
		
		out.println("********************* End Snapshot *********************");
		out.println();
	}
	
	/**
	 * Determines if game has been won.
	 * @return true if game has been won
	 */
	static boolean checkGameOver() {
		
		boolean status = curBoard.winCheck();
		if (debug) out.println(status);
		return status;
	}
	
	// Toggles ----------------------------------------------------------------
	/**
	 * Toggles auto-stack mode.
	 */
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
