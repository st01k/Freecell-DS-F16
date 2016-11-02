package engine;

import java.util.Stack;

import board.Board;
import engine.gUI.FreeGUI;

public class Engine 
{

	/**
	 * 
	 * @author groovyLlama devteam
	 * @version 0.3
	 */
	
	private static Stack<Board> BoardHistory = new Stack<Board>();
	static Board curBoard;
	private static boolean flag = true;
	
	public static void snapshot()
	{
		BoardHistory.push(curBoard);
	}
	
	public static void start()
	{
		curBoard = new Board();
		FreeGUI.start();
		gameLoop();
	}

	private static void gameLoop() 
	{
		while(flag)
		{
			FreeGUI.Paint(curBoard);
		}
	}
}
