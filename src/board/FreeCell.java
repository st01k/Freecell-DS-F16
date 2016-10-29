package board;

import static java.lang.System.out;

import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.2
 */
public class FreeCell implements CellInterface {
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	StdCard card;
	
	public FreeCell() {
		card = null;
	}
	
	@Override
	public boolean placeCard(StdCard c) {
		
		if (card != null) return false;
		
		card = c;
		return true;
	}

	@Override
	public boolean removeCard() {
		
		card = null;
		return true;
	}

	public String toString() {
		
		if (card == null) return "[   ]";
		return "[" + card.toString() + "]";
	}
	
	public static void toggleDebug() {
		debug = !debug;
	}
	
	/**
	 * Unit test.
	 */
	public static void unitTest() {
		out.println("-------------------- Testing FreeCell Class:\n");
		
		out.println();
		out.println("-------------------- FreeCell Unit Test Complete.\n");
	}
}
