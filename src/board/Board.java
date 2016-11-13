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
		
		for (int i = 0; i < INITROWS; i++) dealRow();
		genMap();
	}
	
	private void genMap() {
		
		for (Key k : Key.values()) {
			
			if (k.isFreecell()) {
				
				int pos = k.getPosition();
				freeAry[pos].setKey(k);
			}
			
			if (k.isHomecell()) {
				
				int pos = k.getPosition();
				homeAry[pos].setKey(k);
			}
			
			if (k.isPlayingPile()) {
				
				int pos = k.getPosition();
				pileAry[pos].setKey(k);
			}
		}
	}
	
	/**
	 * Fills a row of cards by cycling through each pile.
	 * @param rowNum row number to fill
	 */
	private void dealRow() {	
		
		if (debug) out.println("\n---board.Board.dealRow--- ");
		
		for (int i = 0; i < PILES && !d.isEmpty(); i++) {
			
			StdCard c = d.getCard();
			pileAry[i].placeCardOnDeal(c);
			
			if (debug) out.println(i + " " + c);
		}
	}
	
	// Accessors --------------------------------------------------------------
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
	
	/**
	 * Returns a specific playing pile.
	 * @param index
	 * @return playing pile at index
	 */
	public PlayingPile getPile(int index) {
		return pileAry[index];
	}
	
	public StdCard getCardAt(Key k) {
		
		if (debug) out.println("\n---board.Board.getCardAt--- ");
		if (debug) out.println(k);
		
		StdCard c;
		int pos = k.getPosition();
		
		switch (k.getRegion()) {
		
		case 1	: c = freeAry[pos].peekCard();
			break;
		case 2 	: c = homeAry[pos].peekCard();
			break;
		case 3 	: c = pileAry[pos].peekLastCard();
			break;
		default	: 
			if (debug) out.println
				("ERROR: invalid input in board.Board.getCardAt");
			c = null;
		}
		
		if (debug) out.println("source card: " + c);
		return c;
	}
	


	// Update -----------------------------------------------------------------
	/**
	 * Tries to place a card, if it fails returns false.
	 * If successful returns true.
	 * -2 : card into next available free cell
	 * -1 : card into next available (or matching suit) home cell
	 * 0 - 7 : card into respective playing pile
	 * @param src source card mapped position
	 * @param dest mapped destination
	 * @return true on legal move; false otherwise
	 */
	public void makeMove(KeyMap k) {
		
		if (debug) out.println("\n---board.Board.makeMove---");
		
		StdCard c = k.srcCard;
		
		place(c, k.getDestKey());
		remove(k.getSrcKey());
	}
	
	void place(StdCard c, Key dest) {
		
		int pos = dest.getPosition();
		switch(dest.getRegion()) {
		// into freecell
		case 1	:
			intoFreecell(c);
			break;
		// into homecell
		case 2 :
			intoHomecell(c);
			break;
		// into respective playing pile
		case 3	:
			intoPlayingPile(c, pileAry[pos]);
			break;
		default	:
			if (debug) out.println
				("ERROR - Unknown destination in board.Board.place");
			break;
		}
	}
	
	void remove(Key src) {
		
		switch(src.getRegion()) {
		case 1 	:
			freeAry[src.getPosition()].removeCard();
			break;
		case 2 	:
			if (debug) out.println
				("ERROR: homecell remove in board.Board.makeMove");
			break;
		case 3 	:
			pileAry[src.getPosition()].removeCard();
			break;
		default	:
			if (debug) out.println
				("ERROR: Unknown source in board.Board.remove");
			break;
		}
	}
	
	/**
	 * Updates the statistics on the board for live status and board snapshot.
	 * @param t updated turn number
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
	boolean intoFreecell(StdCard c) {
		
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
	boolean intoHomecell(StdCard c) {

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
	boolean intoPlayingPile(StdCard c, PlayingPile p) {
		
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
