package board;

import static java.lang.System.out;

import java.util.NoSuchElementException;
import java.util.ArrayList;

import playingCards.StdCard;
import playingCards.StdDeck;

/**
 * 
 * @author groovyLlama devteam
 * @version 0.2
 */
public class PlayingPile {
	
	// static variables
	private static boolean debug = false;
	
	// class variables
	private ArrayList<StdCard> pile;
	// changed to arraylist to allow indexing
	// and easier card placement in piles
	// during board initialization

	public PlayingPile() {
		pile = new ArrayList<StdCard>();
	}
	
	void placeCardOnDeal(StdCard c) {
		pile.add(c);
	}
	
	void placeCard(StdCard c) {
	
		//TODO make isDescending and isAltColor method to validate placement
	}
	
	StdCard removeCard() {
		if (!pile.isEmpty()) return pile.remove(pile.size() - 1);
		throw new NoSuchElementException("PlayingPile: pile stack is empty");
	}
	
	/**
	 * Returns card at index position.
	 * Does not remove the card from the pile.
	 * @param index index of card
	 * @return card at index
	 */
	StdCard getCardAt(int index) {
		
		//TODO throw exception?
		if (index > size() - 1) return null;
		return pile.get(index);
	}
	
	int size() {
		return pile.size();
	}
	
	@Override
	public String toString() {
		return pile.toString();
	}
	
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
			p.placeCardOnDeal(d.getCard());
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
		out.println("-------------------- PlayingPile Unit Test Complete.\n");
	}
}
