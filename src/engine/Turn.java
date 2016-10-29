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
	private StdCard targetCard;
	private Character destination;
	
	// TODO needs some kind of board map
	// going with CLI board model for now
	// with alphabetic mapping.
	// see cli board by running 'test' in cli
	// if used, will need a getCardAt(position in map)
	public Turn(Character target, Character dest) {
		
	}
	
	public boolean isWinnable() {
		
		//TODO runs solver = winnable
		return winnable;
	}
}
