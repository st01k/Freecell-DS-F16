package board;

import static java.lang.System.out;

import java.util.LinkedList;
import java.util.Queue;
import engine.Turn;
import playingCards.*;

/**
 * Freecell game board.
 * @author groovyLlama devteam
 * @version 0.4
 */
public class Board {

	// static constants
	private static final int CELLS = 4;
	private static final int PILES = 8;
	
	// static variables
	private static boolean debug = false;
	private static boolean ezWin = false;
	
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
		
		//TODO winnable dynamically gen'd on move 0
		// instead of hard set to true.
		winnable = true;
		moveNum = 0;
		freeAry = new FreeCell[CELLS];
		homeAry = new HomeCell[CELLS];
		pileAry = new PlayingPile[PILES];
		init();
	}
	
	// Initialization ---------------------------------------------------------
	/**
	 * Initializes board with a full deck dealt.  If ezWin mode 
	 * is on, the deck is stacked for an easy win.
	 */
	private void init() {
		
		final int INITROWS = 7;
		
		if (ezWin) d = new StdDeck(true);
		else {
			
			d = new StdDeck();
			d.shuffle();
		}
		
		for (int i = 0; i < CELLS; i++) {
			freeAry[i] = new FreeCell();
			homeAry[i] = new HomeCell();
		}
		for (int i = 0; i < PILES; i++) pileAry[i] = new PlayingPile();
		
		for (int i = 0; i < INITROWS; i++) dealRow();
		genMap();
	}
	
	/**
	 * Generates key members on cells and piles for mapping.
	 */
	private void genMap() {
		
		if (debug) out.println("\n---board.Board.genMap---");
		
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
		
		if (debug) out.println("\n---board.Board.dealRow---");
		
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
	
	/**
	 * Gets card on the board at specified key.
	 * @param k key
	 * @return card in key position
	 */
	public StdCard getCardAt(Key k) {
		
		if (debug) out.println("\n---board.Board.getCardAt---");
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
	 * Moves source card to destination.
	 * Must be sent a valid keymap.
	 * @param k keymap
	 */
	public void makeMove(KeyMap k) {
		
		if (debug) out.println("\n---board.Board.makeMove---");
		
		StdCard c = k.srcCard;
		Key destKey = k.getDestKey();
		
		Key actualPlacement = place(c, destKey, false);
		remove(k.getSrcKey(), false);
		
		// if key had to be searched for
		if (!destKey.equals(actualPlacement)) {
			
			if (debug) out.print("mod to keymap:\ndest(" + destKey.getKey());
			k.setDestKey(actualPlacement);
			if (debug) out.println(" --> " + k.getDestKey().getKey() + ")");
			
		}
	}
	
	/**
	 * Places card at destination based on keymap.
	 * Will search region if destination is not valid.
	 * Does not search piles.
	 * @param c source card
	 * @param dest destination position
	 */
	Key place(StdCard c, Key dest, boolean force) {
		
		// if destination has to be searched for
		// this holds the actual placement, used
		// to update the keymap
		Key placedIn = null;
		
		int pos = dest.getPosition();
		switch(dest.getRegion()) {
		// into freecell
		case 1	:
			if (force) freeAry[pos].forcePlace(c);
			else placedIn = intoFreecell(c, pos);
			break;
		// into homecell
		case 2 :
			if (force) homeAry[pos].forcePlace(c);
			else placedIn = intoHomecell(c, pos);
			break;
		// into respective playing pile
		case 3	:
			if (force) pileAry[pos].forcePlace(c);
			else placedIn = intoPlayingPile(c, pileAry[pos]);
			break;
		default	:
			if (debug) out.println
				("ERROR - Unknown destination in board.Board.place");
			break;
		}
		
		return placedIn;
	}
	
	/**
	 * Removes card at source position, based on keymap.
	 * @param src source position
	 */
	void remove(Key src, boolean force) {
		
		switch(src.getRegion()) {
		case 1 	:
			freeAry[src.getPosition()].removeCard();
			break;
		case 2 	:
			if (force) homeAry[src.getPosition()].removeCard();
			if (debug && !force) out.println
				("ERROR: homecell remove in board.Board.remove");
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
		
		if (debug) out.println("\n---board.Board.updateBoardStats---");
		
		moveNum = t.getMoveNum();
		winnable = t.getWinnable();
	}
	
	/**
	 * Updates the board to a specific previous turn.
	 * @param t turn to update to
	 */
	public void forceUpdate(Turn t) {
		
		t.getKeymap().invertKey();
		Key src = t.getKeymap().getSrcKey();
		Key dest = t.getKeymap().getDestKey();
		StdCard card = t.getKeymap().getSourceCard();
		
		place(card, dest, true);
		remove(src, true);
	}
	
	/**
	 * Checks if the game is won based on all homecells containing a king.
	 * @return true if game is won
	 */
	public boolean winCheck() {
		
		if (debug) out.println("\n---board.Board.winCheck---");
		
		int king = StdCard.getMaxValue();
		if (debug) out.println("king value: " + king);
		
		for (HomeCell h : homeAry) {
			
			if (h.isEmpty()) return false;
			
			if (debug) out.println("card value: " + h.peekCard().getValue());
			if (h.peekCard().getValue() != king) return false;
		}
		return true;
	}
	
	// Placement --------------------------------------------------------------
	/**
	 * Auto-stacks pile and freecell cards into homecells.
	 */
	public Queue<KeyMap> autoStack() {
		//TODO add stacking from appropriate 
		// cards under auto-stacked cards
		if (debug) out.println("\n---board.Board.autoStack---");
		
		Queue<KeyMap> autos = new LinkedList<KeyMap>();
		
		for (PlayingPile p : pileAry) {
			
			Key src = p.getKey();
			Key dest = Key.E;
			
			KeyMap keymap = new KeyMap(src, dest, this);
			if (keymap.isValid()) autos.add(keymap);
		}
		
		for (FreeCell f : freeAry) {
			
			Key src = f.getKey();
			Key dest = Key.E;
			
			KeyMap keymap = new KeyMap(src, dest, this);
			if (keymap.isValid()) autos.add(keymap);
		}
		
		if (debug) out.println("\n---board.Board.autoStack END---");
		return autos;
	}
	
	/**
	 * Places card into next available freecell.
	 * @param c card to insert
	 * @return true if the card was successfully placed.
	 */
	Key intoFreecell(StdCard c, int index) {
		
		if (debug) out.println("\n---board.Board.intoFreeCell---");
		
		// tries user selected placement
		if (freeAry[index].placeCard(c)) return freeAry[index].getKey();
		
		if (debug) out.println("searching for open freecell...");
		
		// finds first available cell
		for (FreeCell f : freeAry) {
			if (f.placeCard(c)) return f.getKey();
		}
		return null;
	}
	
	/**
	 * Places card into next available homecell or into
	 * its respective (by matching suit) homecell.
	 * @param c card to be inserted
	 * @return true if the card was successfully placed
	 */
	Key intoHomecell(StdCard c, int index) {

		if (debug) out.println("\n---board.Board.intoHomeCell---");
		
		// tries user selected placement
		if (homeAry[index].placeCard(c)) return homeAry[index].getKey();
		
		if (debug) out.println("searching for homecell...");
		
		// finds first available or matching cell
		for (HomeCell h : homeAry) {
			if (h.placeCard(c)) return h.getKey();
		}
		return null;
	}
	
	/**
	 * Places card into associated playing pile.
	 * @param c card to be inserted
	 * @param p playing pile destination
	 * @return true if the card was successfully placed
	 */
	Key intoPlayingPile(StdCard c, PlayingPile p) {
		
		if (debug) out.println("\n---board.Board.intoPlayingPile---");
		
		if (p.placeCard(c)) return p.getKey();
		return null;
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
		s += (winnable)? "Winnable" : "Lost    ";
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
	 * Calculates the number of cards that can be moved in a turn.
	 * Count is based on the number of empty freecells and piles.
	 * @return number of moveable cards in a turn
	 */
	public int calcMoveableCards() {
		
		if (debug) out.println("\n---board.Board.calcMoveableCards---");
		
		int cntF = 0;
		int cntP = 0;
		
		for (FreeCell f : freeAry) {
			if (f.isEmpty()) ++cntF;
		}
		
		for (PlayingPile p : pileAry) {
			if (p.isEmpty()) ++cntP;
		}
		
		int cnt = (cntF + 1) + (2 * cntP);
		
		if (debug) out.println("open freecells: " + cntF);
		if (debug) out.println("open piles: " + cntP);
		if (debug) out.println("moveable cards in one pile: " + cnt);
		
		return cnt;
	}
	
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
	 * Clones this board.
	 */
	public Board clone() {
		return this;
	}
	
	public static void toggleEzWin() {
		ezWin = !ezWin;
		
		String s;
		s = (ezWin)? "on" : "off";
		if (debug) out.println("easy win deck " + s);
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
