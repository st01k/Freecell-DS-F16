package client;

import static java.lang.System.out;

import playingCards.StdCard;
import playingCards.StdDeck;

/**
 * Driver for Freecell card game.
 * @author GroovyLlama
 * @version 0.1
 */
public class Driver {
	
	public static void main(String[] args) {
		
		test();
	}
	
	private static void test() {
		
		StdCard.unitTest();
		StdDeck.unitTest();
		
		out.println("Individualized Unit Testing Complete");
	}
}
