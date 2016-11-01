package engine;

import java.util.Stack;

import board.Board;

public class Engine 
{

	/**
	 * 
	 * @author groovyLlama devteam
	 * @version 0.3
	 */
	
	private static Stack<Board> BoardHistory = new Stack<Board>();
	private static Board curBoard;
	
	public static void snapshot()
	{
		BoardHistory.push(curBoard);
	}
	
	
}
