package engine;

import static java.lang.System.out;

import java.util.Stack;
import client.gui.FreeGUI;
import board.Board;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Engine 
{
	
	private static boolean gui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	private static Stack<Board> history = new Stack<Board>();
	
	static Board curBoard;
	
	/**
	 * Saves current board to history.
	 */
	public static void snapshot()
	{
		history.push(curBoard);
	}
	
	/**
	 * Creates board and solver.
	 * Starts game in preferred user interface.
	 * @param isGui true if UI is gui
	 */
	public static void start(boolean isGui)
	{
		curBoard = new Board();
		gui = isGui;
		if (gui) FreeGUI.start();
		gameLoop();
	}

	/**
	 * Main game loop.
	 */
	private static void gameLoop() 
	{
		int moveNum = 0;
		snapshot();
		
		while(!gameOver)
		{
			if (debug) out.println("\n---engine.Engine.gameLoop---");
			if (debug) printSnapshot();
			
			if (gui) FreeGUI.Paint(curBoard);
			else out.println(curBoard.toString());
			
			//TODO auto stacks
			Turn turn = new Turn(gui, ++moveNum, curBoard);
			curBoard.updateBoardStats(turn);
			snapshot();
		}
	}
	
	private static void newDeal() {
		
	}
	
	private static void undo() {
		
	}
	
	private static void redo() {
		
	}
	
	public static void printSnapshot() {
		
		out.println("\n---engine.Engine.printSnapshot---\n");
		out.print("*************** Begin Snapshot ***************");
		out.println(history.peek());
		out.println("\nstate: gui - " + gui + " | history size: " + history.size());
		out.println("**************** End Snapshot ****************");
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
