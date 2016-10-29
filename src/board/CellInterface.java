package board;

import playingCards.StdCard;

/**
 * Interface for card placement into various types of cells.<br>
 * This interface is specific to the mechanics of freecell.
 * @author groovyLlama devteam
 * @version 1.0
 */
public interface CellInterface 
{
	/**
	 * Places a card in a cell.
	 */
	boolean placeCard(StdCard c);
	
	/**
	 * Removes a card from a cell.
	 */
	boolean removeCard();
}
