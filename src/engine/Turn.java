package engine;

import playingCards.StdCard;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Turn {

	private boolean winnable;
	// sent in by engine round num, maybe?
	// not static inc as to preserve states
	// on undo/redo
	private int moveNum;
	private StdCard srcCard;
	private Character destination;
	
	// TODO needs some kind of board mapping/graph
	// going with CLI board model for now
	// with alphabetic mapping.
	// see cli board by running 'test' in cli
	// if used, will need a getCardAt(position in map)
	public Turn(StdCard c, Character src, Character dest) {
		
	}
	
	/**
	 * Returns the turn's winnable status.
	 * @return true if game from this turn is winnable
	 */
	public boolean isWinnable() {
		
		//TODO runs solver = winnable
		return winnable;
	}
	
	/**
	 * Moves a card from its source to a destination.
	 * @param src source position
	 * @param dest destination position
	 * @return true if card was successfully moved
	 */
	public boolean moveCard(Character src, Character dest) {
		
		//TODO feed me!
		return false;
	}
}
