package board;

import static java.lang.System.out;
import playingCards.StdCard;

/**
 * Key mapping for a move.
 * @author groovyLlama devteam
 * @version 0.1
 */
public class KeyMap {

	private static boolean debug = false;
	
	private Key src;
	private Key dest;
	private StdCard srcCard;
	private Board board;
	
	public KeyMap(Key s, Key d, Board b) {
		
		board = b;
		src = s;
		dest = d;
		genCard();
	}
	
	/**
	 * Constructs a key map for a move.
	 * @param s source position
	 * @param d destination position
	 * @param b current board
	 */
	public KeyMap(String s, String d, Board b) {
		
		board = b;
		genKeys(s, d);
		genCard();
	}
	
	/**
	 * Generates source and destination keys if
	 * strings are used to create the mapping..
	 * @param s source position
	 * @param d destination position
	 */
	private void genKeys(String s, String d) {
		
		if (debug) out.println("\n---board.KeyMap.genKeys--- ");
		
		for (Key k : Key.values()) {
			if (s.matches(k.getKey())) src = k;
			if (d.matches(k.getKey())) dest = k;
		}
		
		if (debug) out.println(src + "\n" + dest);
	}
	
	/**
	 * Generates the card in the source position.
	 */
	private void genCard() {
		
		if (debug) out.println("\n---board.KeyMap.genCard--- ");
		srcCard = board.getCardAt(src);
	}
	
	void invertKey() {
		
		Key temp = src;
		src = dest;
		dest = temp;
		genCard();
	}
	
	/**
	 * Returns source card.
	 * @return source card
	 */
	public StdCard getSourceCard() {
		return srcCard;
	}
	
	/**
	 * Returns source position key.
	 * @return source key
	 */
	public Key getSrcKey() {
		return src;
	}
	
	/**
	 * Returns destination position key.
	 * @return destination key
	 */
	public Key getDestKey() {
		return dest;
	}
	
	void setDestKey(Key d) {
		dest = d;
	}
	
	/**
	 * Tests keymap for move validity.
	 * @return true if move is valid
	 */
	public boolean isValid() {
		
		if (debug) out.println("\n---board.KeyMap.isValid---");
		if (debug) out.println(this);
		
		// no card
		if (srcCard == null) return false;
		// same card
		if (src.equals(dest)) return false;
		// homecell remove
		if (src.isHomecell()) return false;
		
		
		switch(dest.getRegion()) {
		// into freecell
		case 1	:
			FreeCell[] fAry = board.getFreecells();
			for (FreeCell f : fAry) {
				if (f.isValid()) return true;
			}
			break;
		// into homecell
		case 2 	:
			HomeCell[] hAry = board.getHomecells();
			for (HomeCell h : hAry) {
				if (h.isValid(srcCard)) return true;
			}
			break;
		// into respective playing pile
		case 3	:
			PlayingPile p = board.getPile(dest.getPosition());
			if (p.isValid(srcCard)) return true;
			break;
		default	:
			if (debug) out.println("ERROR - Unknown destination in board.KeyMap.isValid");
			break;
		}
		return false;
	}
	
	/**
	 * Returns keymap string.
	 */
	public String toString() {
		
		return "s - " + src + "\nd - " + dest;
	}
	
	/**
	 * Returns true if keymaps are the same.
	 * @param k keymap to compare to
	 * @return true if keymaps are the same
	 */
	public boolean equals(KeyMap k) {
		
		return
			src.equals(k.getSrcKey()) &&
			dest.equals(k.getDestKey()) &&
			srcCard.equals(k.getSourceCard());			 
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
		//TODO feed me!
	}
}
