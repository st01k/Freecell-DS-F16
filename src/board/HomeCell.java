package board;

import static java.lang.System.out;
import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.2
 */
public class HomeCell implements CellInterface {

	// static variables
	private static boolean debug = false;
	
	// class variables
	StdCard holder;
		
	/**
	 * Creates empty homecell.
	 */
	public HomeCell() {
		holder = null;
	}
	
	/**
	 * Places a card in the home cell
	 * if it is a valid placement.
	 */
	@Override
	public boolean placeCard(StdCard c) {
		
		// cell is empty
		if (holder == null && c.getValue() == 1) {
			holder = c;
			return true;
		}
		// cell is not empty 
		if (c.getValue() == holder.getValue() + 1) {
			holder = c;
			return true;
		}
		// card is not the next ascending
		//TODO make isAscending method
		return false;
	}

	/**
	 * Returns null.
	 * Cannot remove a card from a home cell.
	 */
	@Override
	public StdCard removeCard() {
		return null;
	}

	/**
	 * Returns the card currently in cell.
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
		out.println("-------------------- Testing HomeCell Class:\n");
		
		out.println();
		out.println("-------------------- HomeCell Unit Test Complete.\n");
	}
}
