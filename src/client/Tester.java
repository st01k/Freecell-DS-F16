package client;

import static java.lang.System.out;
import playingCards.StdCard;
import playingCards.StdDeck;

public class Tester {

	/**
	 * Tester entry point.
	 */
	static void enter() {
		
		testAll();
	}
	
	/**
	 * Runs all class unit tests.
	 */
	private static void testAll() {
		
		out.println("\nBegin Individualized Unit Testing\n");
		
		StdCard.unitTest();
		StdDeck.unitTest();
		
		out.println("Individualized Unit Testing Complete\n");
	}
}
