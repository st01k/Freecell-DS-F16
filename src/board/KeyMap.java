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
	
	Key src;
	Key dest;
	StdCard srcCard;
	Board board;
	
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
	 * Generates source and destination keys.
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
				if (f.check()) return true;
			}
			//FreeCell f = fAry[dest.getPosition()];
			break;
		// into homecell
		case 2 	:
			HomeCell[] hAry = board.getHomecells();
			for (HomeCell h : hAry) {
				if (h.check(srcCard)) return true;
			}
			//HomeCell h = hAry[dest.getPosition()];
			
			break;
		// into respective playing pile
		case 3	:
			PlayingPile p = board.getPile(dest.getPosition());
			if (p.check(srcCard)) return true;
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
		
		String s = "";
		s += "source: " + src.getKey() + ", ";
		s += src.getRegion() + ", " + src.getPosition();
		s += " (" + srcCard + ") | ";
		s += "dest: " + dest.getKey() + ", ";
		s += dest.getRegion() + ", " + dest.getPosition();
		return s;
	}
	
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}
}
