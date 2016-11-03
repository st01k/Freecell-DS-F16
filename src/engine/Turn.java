package engine;

import static java.lang.System.out;

import java.util.Scanner;
import utils.SysUtils;
import board.Board;


/**
 * Freecell turn.
 * Alters current board when move is made.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class Turn {
	
	// static variables
	private static Scanner scan = new Scanner(System.in);
	private static boolean debug = false;

	// class variables
	private boolean winnable;
	private int moveNum;
	private String turnString;
	private Board board;
	
	/**
	 * Creates a freecell turn with statistics.
	 * @param isGui true if preferred UI is gui
	 * @param move move number
	 * @param b current board
	 */
	public Turn(boolean isGui, int move, Board b) {
		
		moveNum = move;
		board = b;
		if (isGui) guiTurn();
		else cliTurn();
		winnable = isWinnable();
	}
	
	// Accessors --------------------------------------------------------------
	public boolean getWinnable() {
		return winnable;
	}
	
	public int getMoveNum() {
		return moveNum;
	}
	
	// Business ---------------------------------------------------------------
	/**
	 * Runs solver and sets winnable status on the turn.
	 * @return true if game from this turn is winnable
	 */
	private boolean isWinnable() {
		
		//TODO run solver
		// winnable = boolean solver result
		// attach solution to turn when applicable
		return true;
	}
	
	// Turn Actions -----------------------------------------------------------
	/**
	 * Prompts user for turn input.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public void cliTurn() {
		
		if (debug) out.println("\n---engine.Turn.cliTurn---");
		//FIXME new line throws an exception
		out.print("\nEnter position of the card to move: ");
		String from = scan.nextLine();
		String src = checkInput(from);
		
		out.print("Enter destination position: ");
		String to = scan.nextLine();
		String dest = checkInput(to);
		
		out.println();
		
		turnString = "src key: " + src + " | dest key: " + dest;
		if (debug) out.println(this);
		
		if (!board.makeMove(src, dest)) 
			out.println("invalid move detected in engine.Turn.cliTurn");
	}
	
	//TODO automate free and home cell entry with double click
	// so that cards go into next available slot
	/**
	 * Waits for user turn input from gui.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public void guiTurn() {
		
		// TODO feed me!
		// only need src and dest mapping to make move
		//board.makeMove(src, dest);
		scan.nextLine();	// added to stop infinite loop in engine
		turnString = "build gui turn string in Turn.guiTurn";
	}
	
	// Utilities --------------------------------------------------------------	
	/**
	 * Turn to string.
	 */
	@Override
	public String toString() {
		
		return turnString + " | move number: " + moveNum + " | winnable: " + winnable;
	}

	/**
	 * Checks for exit command and validates user input.
	 * Exits if exit command is detected.
	 * @param s user input
	 * @return validated input
	 */
	private static String checkInput(String s) {
		
		//FIXME new line throws an exception
		if (s.matches("exit")) SysUtils.exitDoor
			("Exit from cliTurn @ input move");
				
		s = s.substring(0, 1).toLowerCase();
		
		while (!s.matches("[a-p]")) {
			out.println("Invalid position.  Re-enter: ");
			s = scan.nextLine();
			s = s.substring(0, 1).toLowerCase();
		}
		
		return s;
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
	
	/**
	 * Unit test.
	 */
	public static void unitTest() {
		//TODO add me to tester
	}
}
