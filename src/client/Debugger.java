package client;

import board.Board;
import board.FreeCell;
import board.HomeCell;
import board.PlayingPile;
import playingCards.*;
import engine.gUI.*;

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
