package engine;

import static java.lang.System.out;

import java.util.Scanner;

import utils.SysUtils;
import board.Board;


/**
 * 
 * @author groovyLlama devteam
 * @version 0.3
 */
public class Turn {
	
	private static Scanner scan = new Scanner(System.in);
	private static boolean debug = false;

	private boolean winnable;
	private int moveNum;
	private Board board;
	
	// TODO needs some kind of board mapping/graph
	// going with CLI board model for now
	// with alphabetic mapping.
	// see cli board by running 'test' in cli
	// if used, will need a getCardAt(position in map)
	public Turn(boolean isGui, int move, Board b) {
		
		moveNum = move;
		board = b;
		if (isGui) guiTurn();
		else cliTurn();
		winnable = isWinnable();
	}
	
	public boolean getWinnable() {
		return winnable;
	}
	
	public int getMoveNum() {
		return moveNum;
	}
	
	/**
	 * Prompts user for turn input.
	 * Returns true if the turn successfully changes the board.
	 * @return true if the move is successful
	 */
	public void cliTurn() {
		
		if (debug) out.println("\n---engine.Turn.cliTurn---");
		//TODO add validation
		out.print("\nEnter source position: ");
		String inSrc = scan.nextLine();
		if (inSrc.matches("exit")) SysUtils.exitDoor("Exit from cliTurn @ input src");
		inSrc = inSrc.substring(0, 1).toLowerCase();
		
		String src = turnKey(inSrc);
		out.print("Enter destination position: ");
		String inDest = scan.nextLine();
		if (inDest.matches("exit")) SysUtils.exitDoor("Exit from cliTurn @ input dest");
		inDest = inDest.substring(0, 1).toLowerCase();
		
		String dest = turnKey(inDest);
		out.println();
		
		if (debug) out.println("src key: " + src + " | dest key: " + dest);
		
		board.makeMove(src, dest);		
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
	}
	
	private String turnKey(String in) {
		
		if (debug) out.println("\n---engine.Turn.turnKey---");
		
		String key = "";
		
		if (in.matches("[a-p]")) key = in;
		else {
			if (debug) out.println("ERROR: invalid input in engine.Turn.turnKey");
			key = "invalid";
		}
		if (debug) out.println("key: " + key);
		return key;
	}
	
	/**
	 * Runs solver and sets winnable status on the turn.
	 * @return true if game from this turn is winnable
	 */
	private boolean isWinnable() {
		
		//TODO run solver = winnable
		return false;
	}
	
	// Utilities --------------------------------------------------------------
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
