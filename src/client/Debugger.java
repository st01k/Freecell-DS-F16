package client;

import playingCards.StdCard;
import playingCards.StdDeck;

public class Debugger {

	private static boolean debug = false;
	
	/**
	 * Toggles debug mode.
	 */
	public static void masterToggleDebug() {
		
		debug = !debug;
		StdCard.toggleDebug();
		StdDeck.toggleDebug();
	}
}
