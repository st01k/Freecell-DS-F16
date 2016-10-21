package client;

import cardGame.StandardCard;
import cardGame.StandardDeck;
import static java.lang.System.out;

public class FreecellDriver {
	
	public static void main(String[] args) {
		
		test();
	}
	
	private static void test() {
		
		StandardCard.unitTest();
		StandardDeck.unitTest();
		
		out.println("Individualized Unit Testing Complete");
	}
}
