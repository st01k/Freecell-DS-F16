package client.cli;

import static java.lang.System.out;
import board.Board;
import board.FreeCell;
import board.HomeCell;
import board.PlayingPile;
import playingCards.StdCard;
import playingCards.StdDeck;

/**
 * Comprehensive, individualized, and cutomized testing controller.
 * @author Casey
 * @version 1.0
 */
public class Tester {

	/**
	 * Tester entry point.
	 */
	static void enter() {
		
		//TODO menu for individualized tests
		testAll();
	}
	
	/**
	 * Runs all class unit tests.
	 */
	private static void testAll() {
		
		out.println("\nBegin Comprehensive Unit Testing\n");
		
		StdCard.unitTest();
		StdDeck.unitTest();
		FreeCell.unitTest();
		HomeCell.unitTest();
		PlayingPile.unitTest();
		Board.unitTest();
		
		out.println("Comprehensive Unit Testing Complete\n");
	}
}
