package board;

import static java.lang.System.out;

import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.3
 */
public class FreeCell implements CellInterface {
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private StdCard cell;
	
	
	// cell manipulation ------------------------------------------------------
	/**
	 * Places a card in the freecell
	 * if there is not one already present.
	 */
	@Override
	public boolean placeCard(StdCard c) {
		
		if (debug) out.println("\n---board.FreeCell.placeCard---");
		
		if (!isEmpty()) return false;
		
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
		cell = null;
		return temp;
	}
	
	// cell information -------------------------------------------------------
	/**
	 * Returns card currently in cell.
	 * Does not remove the card.
	 * @return card currently in cell
	 */
	public StdCard peekCard() {
		return cell;
	}
	
	// cell checks ------------------------------------------------------------	
	private boolean isEmpty() {
		return cell == null;
	}

	// utilities --------------------------------------------------------------
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
