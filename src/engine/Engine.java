package engine;

import java.util.Stack;
import client.gui.FreeGUI;
import board.Board;
import solver.Solver;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Engine 
{
	
	private static Stack<Board> history = new Stack<Board>();
	static Board curBoard;
	static Solver solver;
	private static boolean gui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	
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
		solver = new Solver();
		gui = isGui;
		if (gui) FreeGUI.start();
		gameLoop();
	}

	/**
	 * Main game loop.
	 */
	private static void gameLoop() 
	{
		while(!gameOver)
		{
			//TODO turn controls
			if (gui) FreeGUI.Paint(curBoard);
		}
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
