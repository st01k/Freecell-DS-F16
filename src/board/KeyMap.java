package board;

import static java.lang.System.out;
import playingCards.StdCard;

public class KeyMap {

	private static boolean debug = false;
	
	Key src;
	Key dest;
	StdCard srcCard;
	Board board;
	
	public KeyMap(String s, String d, Board b) {
		
		board = b;
		genKeys(s, d);
		genCard();
	}
	
	private void genKeys(String s, String d) {
		
		if (debug) out.println("\n---board.KeyMap.genKeys--- ");
		
		for (Key k : Key.values()) {
			if (s.matches(k.getKey())) src = k;
			if (d.matches(k.getKey())) dest = k;
		}
		
		if (debug) out.println(src + "\n" + dest);
	}
	
	private void genCard() {
		
		if (debug) out.println("\n---board.KeyMap.genCard--- ");
		srcCard = board.getCardAt(src);
	}
	
	public StdCard getSourceCard() {
		return srcCard;
	}
	
	public Key getSrcKey() {
		return src;
	}
	
	public Key getDestKey() {
		return dest;
	}
	
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
	
	public String toString() {
		
		String s = "";
		s += "source: " + src.getKey() + ", ";
		s += src.getRegion() + ", " + src.getPosition();
		s += " (" + srcCard + ") | ";
		s += "dest: " + dest.getKey() + ", ";
		s += dest.getRegion() + ", " + dest.getPosition();
		return s;
	}
	
	public static void toggleDebug() {
		debug = !debug;
	}
}
