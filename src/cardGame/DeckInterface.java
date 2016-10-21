package cardGame;

/**
 * Interface for any kind of a card-based deck.
 * @author Casey J. Murphy
 * @version 1.0
 */
public interface DeckInterface {
	
	/**
	 * Shuffles deck.
	 */
	public void shuffle();
	
	/**
	 * Returns a card from deck without removal from deck.
	 * @return card
	 */
	public StandardCard getCard();
	
	/**
	 * Sorts deck.
	 * Optional method.
	 */
	public void sort();
	
	/**
	 * Prints deck to console.
	 * Used for testing in text-based environment.
	 * Optional method.
	 */
	public void print();
}
