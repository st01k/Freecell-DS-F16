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
	StdCard holder;
	
	/**
	 * Creates empty freecell.
	 */
	public FreeCell() {
		holder = null;
	}
	
	/**
	 * Places a card in the freecell
	 * if there is not one already present.
	 */
	@Override
	public boolean placeCard(StdCard c) {
		
		if (holder != null) return false;
		
		holder = c;
		return true;
	}
	
	/**
	 * Removes card from freecell.
	 * Returns card that was removed.
	 */
	@Override
	public StdCard removeCard() {
		
		StdCard temp = holder;
		holder = null;
		return temp;
	}
	
	/**
	 * Returns card currently in cell.
	 * Does not remove the card.
	 * @return card currently in cell
	 */
	public StdCard peekCard() {
		return holder;
	}

	/**
	 * Dynamically generates cell string.
	 * For use in CLI.
	 */
	public String toString() {
		
		if (holder == null) return "[   ]";
		return "[" + holder.toString() + "]";
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
		out.println("-------------------- Testing FreeCell Class:\n");
		
		out.println();
		out.println("-------------------- FreeCell Unit Test Complete.\n");
	}
}
