package client.cli;

import client.gui.FreeGUI;
import board.*;
import playingCards.*;

public class Debugger {

	private static boolean debug = false;
	
	/**
	 * Toggles debug mode for all classes.
	 */
	public static void masterToggleDebug() {
		
		debug = !debug;
		StdCard.toggleDebug();
		StdDeck.toggleDebug();
		FreeCell.toggleDebug();
		HomeCell.toggleDebug();
		PlayingPile.toggleDebug();
		Board.toggleDebug();
		FreeGUI.toogleDebug();
	}
}
