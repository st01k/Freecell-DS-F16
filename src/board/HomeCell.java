package board;

import static java.lang.System.out;
import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.3
 */
public class HomeCell implements CellInterface {

	// static variables
	private static boolean debug = false;
	
	// class variables
	private StdCard cell;
	
	// cell manipulation ------------------------------------------------------
	/**
	 * Places a card in the home cell
	 * if it is a valid placement.
	 */
	@Override
	public boolean placeCard(StdCard c) {

		if (!isValid(c)) return false;
		
		cell = c;
		return true;
	}

	/**
	 * Returns null.
	 * Cannot remove a card from a home cell.
	 */
	@Override
	public StdCard removeCard() {
		
		if (debug) out.println("---board.HomeCell.removeCard---\n" +
								"Cannot remove card from homecell.");
		return null;
	}

	// cell information -------------------------------------------------------
	/**
	 * Returns the card currently in cell.
	 * Does not remove the card.
	 * @return card currently in cell
	 */
	public StdCard peekCard() {
		return cell;
	}
	
	// cell checks ------------------------------------------------------------
	/**
	 * Returns true if the homecell is empty.
	 * @return true if cell is empty.
	 */
	private boolean isEmpty() {
		
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
		
		if (isEmpty()) return c.getValue() == 1;
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
	
	// utilities --------------------------------------------------------------
	/**
	 * Dynamically generates cell string.
	 * For use in CLI.
	 */
	@Override
	public String toString() {
		
		if (cell == null) return "[   ]";
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
