package cardGame;

/**
 * Interface for a card.
 * @author Casey J. Murphy
 * @version 1.0
 */
public interface CardInterface {

	/**
	 * Returns rank integer value.
	 * @return rank int
	 */
	public int getRank();
	
	/**
	 * Returns suit integer value.
	 * @return suit int
	 */
	public int getSuit();
}
