package board;

import static java.lang.System.out;
import playingCards.StdCard;

/**
 * Freecell home cell.  Top-right section of board.
 * Virutal stack of suit ordered (asc) cards.  Not an actual stack.
 * Only holds one card, but card must be validated for placement.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class HomeCell implements CellInterface {

	// static variables
	private static boolean debug = false;
	
	// class variables
	private StdCard cell;
	private Key key;
	
	// Cell Manipulation ------------------------------------------------------
	/**
	 * Places a card in the home cell if it is a valid placement.
	 */
	@Override
	public boolean placeCard(StdCard c) {
		
		if (debug) out.println("\n---board.HomeCell.placeCard---");

		if (!isValid(c)) return false;
		
		if (debug) out.println("placed card: " + c);
		cell = c;
		return true;
	}

	/**
	 * Returns null.  Cannot remove a card from a home cell.
	 */
	@Override
	public StdCard removeCard() {
		
		if (debug) out.println("---board.HomeCell.removeCard---\n" +
								"Cannot remove card from homecell.");
		return null;
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
	 * Returns the card currently in cell.
	 * Does not remove the card.
	 * @return card currently in cell
	 */
	public StdCard peekCard() {
		return cell;
	}
	
	/**
	 * Checks if card can be moved into homecell.
	 * @param c card to check
	 * @return true if move is valid
	 */
	public boolean check(StdCard c) {
		return isValid(c);
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
	 * Returns true if the homecell is empty.
	 * @return true if cell is empty.
	 */
	boolean isEmpty() {
		
		return cell == null;
	}
	
	/**
	 * Returns true if the card passed in is of the same suit as the
	 * previous card in the cell. Returns true if cell is empty.
	 * @param c card to be passed in
	 * @return true if card is same suit, or cell empty
	 */
	private boolean isSameSuit(StdCard c) {
		
		if (isEmpty()) return true;
		return c.getSuit() == cell.getSuit();
	}
	
	/**
	 * Returns true if the card passed in is one face value more
	 * than the last card on the pile or if the pile is empty.
	 * @param c card being passed in
	 * @return true if card passed in is ascending by one, or cell empty
	 */
	private boolean isAsc(StdCard c) {
		
		// ace to empty home cell
		if (isEmpty()) return c.getValue() == 1;
		
		// next ascending value
		return c.getValue() == cell.getValue() + 1;
	}
	
	/**
	 * Returns true is card being passed in passes all other checks.
	 * @param c card being passed in
	 * @return true if the card has been validated for placement
	 */
	private boolean isValid(StdCard c) {
		
		return isSameSuit(c) && isAsc(c);
	}
	
	// Utilities --------------------------------------------------------------
	/**
	 * Dynamically generates cell string.
	 * For use in CLI.
	 */
	@Override
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
		out.println("-------------------- Testing HomeCell Class:\n");
		
		out.println();
		out.println("-------------------- HomeCell Unit Test Complete.\n");
	}
}
