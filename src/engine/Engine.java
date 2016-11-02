package engine;

import java.util.Stack;
import client.gui.FreeGUI;
import board.Board;
import solver.Solver;

public class Engine 
{

	/**
	 * 
	 * @author groovyLlama devteam
	 * @version 0.1
	 */
	
	private static Stack<Board> history = new Stack<Board>();
	static Board curBoard;
	static Solver solver;
	private static boolean isGui = false;
	private static boolean gameOver = false;
	private static boolean debug = false;
	
	public static void snapshot()
	{
		history.push(curBoard);
	}
	
	public static void start(boolean gui)
	{
		curBoard = new Board();
		solver = new Solver();
		isGui = gui;
		if (isGui) FreeGUI.start();
		gameLoop();
	}

	private static void gameLoop() 
	{
		while(!gameOver)
		{
			//TODO turn controls
			if (isGui) FreeGUI.Paint(curBoard);
		}
	}
	
	private static void toggleDebug() {
		debug = !debug;
	}
}
