package board;

import static java.lang.System.out;
import engine.Turn;

import playingCards.*;

/**
 * Freecell game board.
 * @author groovyLlama devteam
 * @version 0.3
 */
public class Board {

	// static constants
	private static final int CELLS = 4;
	private static final int PILES = 8;
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private boolean winnable;
	private int moveNum;
	private StdDeck d;
	private FreeCell[] freeAry;
	private HomeCell[] homeAry;
	private PlayingPile[] pileAry;
	
	/**
	 * Creates, initializes, and deals a freecell board.
	 */
	public Board() {
		
		winnable = true;
		moveNum = 0;
		freeAry = new FreeCell[CELLS];
		homeAry = new HomeCell[CELLS];
		pileAry = new PlayingPile[PILES];
		init();
	}
	
	// Accessors --------------------------------------------------------------
	//TODO will have to change these three accessors 
	// with encapsulation in mind down the road.
	/**
	 * Returns the freecell array.
	 * @return freecell array
	 */
	public FreeCell[] getFreecells() {
		return freeAry;
	}
	
	/**
	 * Returns the homecell array.
	 * @return homecell array
	 */
	public HomeCell[] getHomecells() {
		return homeAry;
	}
	
	/**
	 * Returns the playing piles array
	 * @return playing piles array
	 */
	public PlayingPile[] getPiles() {
		return pileAry;
	}
	
	//TODO check for empty, think it's already in place
	public StdCard getFreecellCard(int index) {
		return freeAry[index].removeCard();
	}
	
	//TODO pop instead of full access to pile
	public StdCard getPileCard(int pile) {
		return pileAry[pile].removeCard();
	}
	
	// Initialization ---------------------------------------------------------
	/**
	 * Initializes board with a full deck dealt.
	 */
	private void init() {
		
		final int INITROWS = 7;
		
		d = new StdDeck();
		d.shuffle();
		
		for (int i = 0; i < CELLS; i++) {
			freeAry[i] = new FreeCell();
			homeAry[i] = new HomeCell();
		}
		for (int i = 0; i < PILES; i++) pileAry[i] = new PlayingPile();
		
		for (int i = 0; i < INITROWS; i++) fillRow();
	}
	
	/**
	 * Fills a row of cards by cycling through each pile.
	 * @param rowNum row number to fill
	 */
	private void fillRow() {	
		
		if (debug) out.println("---board.Board.fillRow--- ");
		
		for (int i = 0; i < PILES && !d.isEmpty(); i++) {
			
			StdCard c = d.getCard();
			pileAry[i].placeCardOnDeal(c);
			
			if (debug) out.println(i + " " + c);
		}
	}

	// Update -----------------------------------------------------------------
	/**
	 * Moves a card from its source to a destination.
	 * -2 : card into next available free cell
	 * -1 : card into next available (or matching suit) home cell
	 * 0 - 7 : card into respective playing pile
	 * @param src source position
	 * @param dest destination position
	 * @return true if card was successfully moved
	 */
	public boolean makeMove(String src, String dest) {
		
		if (debug) out.println("\n---board.Board.makeMove---");
		
		StdCard sourceCard = sourceSwitch(src);
		int destination = destSwitch(dest);
		
		if (debug) out.println("source card: " + sourceCard);
		if (debug) out.println("dest pos: " + destination);
		
		switch(destination) {
		// into freecell
		case -2	:
			if (intoFreecell(sourceCard)) return true;
			break;
		// into homecell
		case -1 :
			if (intoHomecell(sourceCard)) return true;
			break;
		// into respective playing pile
		case 0	:
		case 1  :
		case 2  :
		case 3  :
		case 4  :
		case 5  :
		case 6  :
		case 7  :
			if (intoPlayingPile(sourceCard, pileAry[destination])) return true;
			break;
		default	:
			if (debug) out.println("ERROR - Unknown destination in board.Board.makeMove");
			break;
		}
		return false;
	}
	
	/**
	 * Updates the statistics on the board
	 * for live status and board snapshot.
	 * @param t turn to update to
	 */
	public void updateBoardStats(Turn t) {
		
		moveNum = t.getMoveNum();
		winnable = t.getWinnable();
	}
	
	// Placement --------------------------------------------------------------
	/**
	 * Places card into next available freecell.
	 * @param c card to insert
	 * @return true if the card was successfully placed.
	 */
	private boolean intoFreecell(StdCard c) {
		
		if (debug) out.println("\n---board.Board.intoFreeCell---");
		
		// finds first available cell
		for (FreeCell f : freeAry) {
			if (f.placeCard(c)) return true;
		}
		return false;
	}
	
	/**
	 * Places card into next available homecell or into
	 * its respective (by matching suit) homecell.
	 * @param c card to be inserted
	 * @return true if the card was successfully placed
	 */
	private boolean intoHomecell(StdCard c) {

		if (debug) out.println("\n---board.Board.intoHomeCell---");
		
		// finds first available or matching cell
		for (HomeCell h : homeAry) {
			if (h.placeCard(c)) return true;
		}
		return false;
	}
	
	/**
	 * Places card into associated playing pile.
	 * @param c card to be inserted
	 * @param p playing pile destination
	 * @return true if the card was successfully placed
	 */
	private boolean intoPlayingPile(StdCard c, PlayingPile p) {
		
		if (debug) out.println("\n---board.Board.intoPlayingPile---");
		
		if (p.placeCard(c)) return true;
		return false;
	}
	
	// CLI --------------------------------------------------------------------
	/**
	 * Board to string.
	 * For use in the CLI.
	 */
	@Override
	public String toString() {
		String s = "\n";
		s += ("--------------------------------------------------------\n");
		s += ("   A      B      C      D   |   E      F      G      H\n");
		s += ("--------------------------------------------------------\n");
		s += buildCellsCLI();
		s += ("\n");
		
		for (int i = 0; i < maxPileSize(); i++) s += " " + buildRowCLI(i) + "\n";
		
		s += ("--------------------------------------------------------\n");
		s += ("   I      J      K      L       M      N      O      P\n");
		s += ("--------------------------------------------------------\n");
		if (winnable) s += ("Winnable");
		else s += ("Lost    ");
		s += ("\t\t\t\t\tMove: " + moveNum + "\n");		
		
		return s;
	}
	
	/**
	 * Builds the string to print cells.
	 * For use in the CLI.
	 * @return all cells
	 */
	private String buildCellsCLI() {
		
		String s = "";
		
		for (FreeCell f : freeAry) s += " " + f.toString();
		for (HomeCell h : homeAry) s += " " + h.toString();
		return s += "\n";
	}
	
	/**
	 * Builds the string to print a row of playing piles.
	 * For use in the CLI.
	 * @param rowNum row number
	 * @return all cards on that row as string
	 */
	private String buildRowCLI(int rowNum) {
		
		String s = "";
		for (int i = 0; i < PILES; i++) {
			
			PlayingPile p = pileAry[i];
			StdCard c = p.getCardAt(rowNum);
			if (c == null) s += "       ";
			else s += " " + p.getCardAt(rowNum).toString() + "  ";
		}
		return s;
	}
	
	// Mapping ----------------------------------------------------------------
	/**
	 * Removes card from its current position on the board.
	 * @param src mapped source position of card
	 * @return card in the source position
	 */
	StdCard sourceSwitch(String src) {
		
		if (debug) out.println("\n---board.Board.sourceSwitch---");
		
		StdCard c = null;
		
		switch(src) {
		// freecells
		case "a"	:	c = freeAry[0].removeCard();	break;
		case "b"	:	c = freeAry[1].removeCard();	break;
		case "c"	:	c = freeAry[2].removeCard();	break;
		case "d"	:	c = freeAry[3].removeCard();	break;
		// homecells
		case "e"	:
		case "f"	:
		case "g"	:
		case "h"	:
			if (debug) out.println
				("ERROR: homecell remove in board.Board.sourceSwitch");
			break;
		// playing piles
		case "i"	:	c = pileAry[0].removeCard();	break;
		case "j"	:	c = pileAry[1].removeCard();	break;
		case "k"	:	c = pileAry[2].removeCard();	break;
		case "l"	:	c = pileAry[3].removeCard();	break;
		case "m"	:	c = pileAry[4].removeCard();	break;
		case "n"	:	c = pileAry[5].removeCard();	break;
		case "o"	:	c = pileAry[6].removeCard();	break;
		case "p"	:	c = pileAry[7].removeCard();	break;
			
		default		:
			if (debug) out.println
				("ERROR: invalid input in board.Board.sourceSwitch");
		}
		if (debug) out.println("removed: " + c);
		return c;
	}
	
	/**
	 * Translates alphabetic destination map location
	 * to a number-based region on the board.
	 * @param dest alphabetic position key
	 * @return numeric position key
	 */
	private int destSwitch(String dest) {
		
		int key = 999;
		
		switch(dest) {
		// freecells
		case "a"	:
		case "b"	:
		case "c"	:
		case "d"	:
			key = -2;
			break;
		// homecells
		case "e"	:
		case "f"	:
		case "g"	:
		case "h"	:
			key = -1;
			break;
		// playing piles
		case "i"	:	key = 0;	break;
		case "j"	:	key = 1;	break;
		case "k"	:	key = 2;	break;
		case "l"	:	key = 3;	break;
		case "m"	:	key = 4;	break;
		case "n"	:	key = 5;	break;
		case "o"	:	key = 6;	break;
		case "p"	:	key = 7;	break;
			
		default		:
			if (debug) out.println("ERROR: invalid input in board.Board.destSwitch");
		}
		
		return key;
	}

	// Utilities --------------------------------------------------------------
	/**
	 * Returns the number of cards contained 
	 * in the largest playing pile.
	 * @return size of the pile that is largest
	 */
	int maxPileSize() {
				
		int max = 0;
		for (PlayingPile p : pileAry) {
			if (p.size() > max) max = p.size();
		}
		return max;
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
		
		out.println("-------------------- Testing Board Class:\n");
		
		Board test = new Board();
		out.println(test);
		
		out.println();
		out.println("-------------------- Board Unit Test Complete.\n");
	}
}
