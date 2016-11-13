package board;

import static java.lang.System.out;

import java.util.ArrayList;
import playingCards.StdCard;
import playingCards.StdDeck;

/**
 * Freecell playing pile
 * @author groovyLlama devteam
 * @version 0.4
 */
public class PlayingPile {
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private ArrayList<StdCard> pile;
	private Key key;

	/**
	 * Creates new empty playing pile.
	 */
	public PlayingPile() {
		pile = new ArrayList<StdCard>();
	}
	
	// List Manipulation ------------------------------------------------------
	/**
	 * Places a card in pile on the deal (without validation).
	 * @param c card to be placed
	 */
	void placeCardOnDeal(StdCard c) {
		pile.add(c);
	}
	
	/**
	 * Places a card in the pile if it is a valid placement.
	 * @param c card being placed
	 * @return true if card was added
	 */
	boolean placeCard(StdCard c) {
	
		if (debug) out.println("\n---board.PlayingPile.placeCard---");
		
		if (!isValid(c)) return false;
		
		if (debug) out.println("added: " + c);
		pile.add(c);
		return true;
	}
	
	/**
	 * Removes top card from pile and returns it.
	 * @return top card on pile, null if empty
	 */
	StdCard removeCard() {
		
		if (debug) out.println("\n---board.PlayingPile.removeCard---");
		
		if (pile.isEmpty()) {
			
			if (debug) out.println("Playing pile is empty.");
			return null;
		}
		else {
			
			if (debug) out.println("removed card: " + peekLastCard());
			return pile.remove(pile.size() - 1);
		}
		
	}
	
	/**
	 * Sets the board key on this pile.
	 * @param k key
	 */
	public void setKey(Key k) {
		
		key = k;
	}

	// List Information -------------------------------------------------------
	/**
	 * Returns card at index position.
	 * Does not remove the card from the pile.
	 * @param index index of card
	 * @return card at index
	 */
	public StdCard getCardAt(int index) {
		
		//TODO throw exception?
		if (index > size() - 1 || index < 0) return null;
		return pile.get(index);
	}
	
	/**
	 * Returns but does not remove the last card (top) in the pile.
	 * Returns null if the pile is empty.
	 * @return last card in pile (top), null if empty
	 */
	StdCard peekLastCard() {
		
		//TODO check for null somewhere
		return getCardAt(size() - 1);
	}
	
	public boolean check(StdCard c) {
		return isValid(c);
	}
	
	/**
	 * Returns the size of the pile.
	 * @return number of cards in pile
	 */
	public int size() {
		return pile.size();
	}
	
	/**
	 * Returns the key of this board element.
	 * @return
	 */
	public Key getKey() {
		return key;
	}
	
	// Checks -----------------------------------------------------------------
	/**
	 * Returns true if pile is empty.
	 * @return true if pile is empty
	 */
	boolean isEmpty() {
		return pile.isEmpty();
	}
	
	/**
	 * Returns true if the card passed in is one face value less
	 * than the last card on the pile or if the pile is empty.
	 * @param c card being compared
	 * @return true if card is descending by one, or cell empty
	 */
	private boolean isDsc(StdCard c) {
		
		if (isEmpty()) return true;
		return c.getValue() + 1 == peekLastCard().getValue();
	}
	
	/**
	 * Returns true if the card  passed in is the opposite color
	 * of the last card in the pile or if the pile is empty.
	 * @param c card being compared
	 * @return true if card is alternate color, or cell empty
	 */
	private boolean isAltColor(StdCard c) {
		
		if (isEmpty()) return true;
		return c.isBlack() != peekLastCard().isBlack();
	}
	
	/**
	 * Returns true is card being passed in passes all other checks.
	 * @param c card being passed in
	 * @return true if the card has been validated for placement
	 */
	private boolean isValid(StdCard c) {
		return isDsc(c) && isAltColor(c);
	}
	
	// Utilities --------------------------------------------------------------
	/**
	 * Returns the contents of the pile as a string.
	 */
	@Override
	public String toString() {
		return pile.toString();
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
		out.println("-------------------- Testing PlayingPile Class:\n");
		
		StdDeck d = new StdDeck();
		PlayingPile p = new PlayingPile();
		
		
		for (int i = 0; i < 5; i++) {
		
			StdCard c = d.getCard();
			p.placeCardOnDeal(c);
			out.println("Placed: " + c);
			out.println("Cards in pile: " + p.size());
			out.println();
		}
		
		out.println("Removing card: " + p.removeCard());
		out.println("Cards in pile: " + p.size());
		out.println();
		out.println("Removing card: " + p.removeCard());
		out.println("Cards in pile: " + p.size());
		
		out.println();
		
		StdCard b = new StdCard(1, 1);
		out.println("New card: " + b);
		out.println("is descending: " + p.isDsc(b));
		out.println("is alt color: " + p.isAltColor(b));
		out.print(b + "stacks onto pile: " + p.placeCard(b));
		
		out.println();
		out.println("-------------------- PlayingPile Unit Test Complete.\n");
	}
}
