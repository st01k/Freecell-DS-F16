package client;

import static java.lang.System.out;
import playingCards.StdCard;
import playingCards.StdDeck;

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
		
		out.println("Comprehensive Unit Testing Complete\n");
	}
}
