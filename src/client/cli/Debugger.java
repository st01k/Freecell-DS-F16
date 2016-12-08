package client.cli;

import client.gui.*;
import board.*;
import playingCards.*;
import engine.*;
import solver.*;

/**
 * Debug switch.
 * @author groovyLlama devteam
 * @version 0.3
 */
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
		FreeGUI.toggleDebug();
		Engine.toggleDebug();
		Turn.toggleDebug();
		Solver.toggleDebug();
		KeyMap.toggleDebug();
	}
}
