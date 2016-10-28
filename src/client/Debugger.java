package client;

import playingCards.*;

public class Debugger {

	private static boolean debug = false;
	
	/**
	 * Toggles debug mode.
	 */
	public static void masterToggleDebug() {
		
		debug = !debug;
		StdCard.toggleDebug();
		StdDeck.toggleDebug();
		engine.gUI.FreeGUI.toogleDebug();
	}
}
