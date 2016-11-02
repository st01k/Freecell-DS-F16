package engine;

import static java.lang.System.out;

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
	
	private static boolean gui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	private static Stack<Board> history = new Stack<Board>();
	
	static Board curBoard;
	static Solver solver;
	
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
		int moveNum = 0;
		snapshot();
		
		while(!gameOver)
		{
			if (gui) FreeGUI.Paint(curBoard);
			else out.println(curBoard.toString());
			
			//TODO turn controls
			// new turn prompts for turn actions
			Turn turn = new Turn(++moveNum, gui);
			curBoard.updateBoard(turn);
			snapshot();
		}
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
