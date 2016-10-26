package client;

/**
 * Driver for Freecell card game.
 * @author GroovyLlama
 * @version 0.1
 */
import playingCards.StandardCard;
import playingCards.StandardDeck;
import static java.lang.System.out;

public class Driver {
	
	public static void main(String[] args) {
		
		test();
	}
	
	private static void test() {
		
		StandardCard.unitTest();
		StandardDeck.unitTest();
		
		out.println("Individualized Unit Testing Complete");
	}
}
