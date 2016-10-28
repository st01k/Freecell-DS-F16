package client;

import static java.lang.System.out;
import playingCards.StdCard;
import playingCards.StdDeck;

public class Tester {

	/**
	 * Runs unit tests.
	 */
	public static void enter() {
		
		testAll();
	}
	
	private static void testAll() {
		
		out.println("\nBegin Individualized Unit Testing\n");
		
		StdCard.unitTest();
		StdDeck.unitTest();
		
		out.println("Individualized Unit Testing Complete\n");
	}
}
