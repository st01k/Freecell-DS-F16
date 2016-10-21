package cardGame;
/**
 * Standard ranks enumeration
 * @author Casey J. Murphy
 * @version 1.0
 */
public enum StandardRank {

	JOKER		('j', 0, "Joker"),
	LOWACE		('a', 1, "Low Ace"),
	TWO			('2', 2, "Two"),
	THREE		('3', 3, "Three"),
	FOUR		('4', 4, "Four"),
	FIVE		('5', 5, "Five"),
	SIX			('6', 6, "Six"),
	SEVEN		('7', 7, "Seven"),
	EIGHT		('8', 8, "Eight"),
	NINE		('9', 9, "Nine"),
	TEN			('T', 10, "Ten"),
	JACK		('J', 11, "Jack"),
	QUEEN		('Q', 12, "Queen"),
	KING		('K', 13, "King"),
	HIGHACE		('A', 14, "High Ace");
	
	private final char symbol;
	private final int value;
	private final String name;
	
	/**
	 * Sets enum variables. 
	 * @param symbol symbol character
	 * @param value face value
	 * @param name card name
	 */
	private StandardRank(char symbol, int value, String name) {
		
		this.symbol = symbol;
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Returns symbol character.
	 * @return symbol char
	 */
	public char getSym() {
		
		return symbol;
	}
	
	/**
	 * Returns face value of card.
	 * @return face value
	 */
	public int getValue() {
		
		return value;
	}
	
	/**
	 * Returns card name.
	 * @return name
	 */
	public String getName() {
		
		return name;
	}
}
