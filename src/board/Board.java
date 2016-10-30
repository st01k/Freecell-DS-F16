package board;

import static java.lang.System.out;

import playingCards.*;

/**
 * Freecell game board.
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Board {

	// static constants
	private static final int CELLS = 4;
	private static final int PILES = 8;
	private static final int INITROWS = 7;
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private boolean solvable = false;
	//TODO num moves from each turn
	// belongs in turn, here for testing, for now
	private int numMoves = 0;
	private StdDeck d;
	private FreeCell[] freeAry;
	private HomeCell[] homeAry;
	// changed to arrays for easier access
	// brandon, you had the right idea from the start
	private PlayingPile[] pileAry;
	
	/**
	 * Creates, initializes, and deals a freecell board.
	 */
	public Board() {
		
		freeAry = new FreeCell[CELLS];
		homeAry = new HomeCell[CELLS];
		pileAry = new PlayingPile[PILES];
		init();
	}
	
	/**
	 * Returns true if the game is winnable.
	 * @return true if game is winnable
	 */
	public boolean getSolvable() {
		return solvable;
	}
	
	/**
	 * Returns the number of moves that have been played.
	 * @return number of moves played
	 */
	public int getNumMoves() {
		return numMoves;
	}
	
	/**
	 * Initializes board with a full deck dealt.
	 */
	private void init() {
		
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
	
	/**
	 * Board to string.
	 * For use in the CLI.
	 */
	@Override
	public String toString() {
		String s = "";
		s += ("  A    B    C    D    E    F    G    H\n");
		s += ("----------------------------------------\n");
		s += buildCellsCLI();
		s += ("\n");
		
		for (int i = 0; i < maxPileSize(); i++) s += buildRowCLI(i) + "\n";
		
		s += ("----------------------------------------\n");
		s += ("  I    J    K    L    M    N    O    P\n");
		s += ("----------------------------------------\n");
		if (solvable) s += ("Is winnable");
		else s += ("Game is lost");
		s += ("\t\t\tMoves: " + numMoves);
		
		
		return s;
	}
	
	/**
	 * Builds the string to print cells.
	 * For use in the CLI.
	 * @return all cells
	 */
	private String buildCellsCLI() {
		
		String s = "";
		
		for (FreeCell f : freeAry) s += f.toString();
		for (HomeCell h : homeAry) s += h.toString();
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
			if (c == null) s += "   ";
			else s += p.getCardAt(rowNum).toString() + " ";
		}
		return s;
	}
	
	/**
	 * Returns the number of cards contained 
	 * in the largest playing pile.
	 * @return
	 */
	private int maxPileSize() {
		
		int max = 0;
		for (PlayingPile p : pileAry) {
			if (p.size() > max) max = p.size();
		}
		
		return max;
	}
	
	/**
	 * Toggles whether or not game can be won. 
	 */
	void toggleSolvable() {
		solvable = !solvable;
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
