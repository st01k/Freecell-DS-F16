package board;

import static java.lang.System.out;

import playingCards.StdCard;

/**
 * Free cell free cell.  Top-left section of board.
 * Holds one card at a time.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class FreeCell implements CellInterface {
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private StdCard cell;
	private Key key;
	
	// Cell Manipulation ------------------------------------------------------
	/**
	 * Places a card in the freecell if there is not one already present.
	 */
	@Override
	public boolean placeCard(StdCard c) {
		
		if (debug) out.println("\n---board.FreeCell.placeCard---");
		
		if (!isEmpty()) return false;
		
		if (debug) out.println("placed card: " + c + "\ninto " + key);
		cell = c;
		return true;
	}
	
	/**
	 * Removes card from freecell.
	 * Returns card that was removed.
	 */
	@Override
	public StdCard removeCard() {
		
		if (debug) out.println("\n---board.FreeCell.removeCard---");
		
		StdCard temp = cell;
		if (debug) out.println("removed card: " + temp + "\nfrom " + key);
		
		cell = null;
		return temp;
	}
	
	/**
	 * Sets the board key on this cell.
	 * @param k key
	 */
	public void setKey(Key k) {
		
		key = k;
	}
	
	// Cell Information -------------------------------------------------------
	/**
	 * Returns card currently in cell.
	 * Does not remove the card.
	 * @return card currently in cell
	 */
	public StdCard peekCard() {
		return cell;
	}
	
	/**
	 * Checks if card can be moved into freecell.
	 * @param c card to check
	 * @return true if move is valid
	 */
	public boolean check() {
		return isEmpty();
	}
	
	/**
	 * Returns the key of this board element.
	 * @return
	 */
	public Key getKey() {
		return key;
	}
	
	// Checks -----------------------------------------------------------------	
	/**
	 * Returns true if freecell is empty.
	 * @return true if cell is empty
	 */
	private boolean isEmpty() {
		return cell == null;
	}

	// Utilities --------------------------------------------------------------
	/**
	 * Dynamically generates cell string.
	 * For use in CLI.
	 */
	public String toString() {
		
		if (cell == null) return "[    ]";
		return "[" + cell.toString() + "]";
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
