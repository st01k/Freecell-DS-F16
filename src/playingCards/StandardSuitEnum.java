package playingCards;

/**
 * Standard suits enumeration
 * @author Casey J. Murphy
 * @version 1.0
 */
public enum StandardSuitEnum {

	NOSUIT		('-', '-', 0, "No Suit"),
	HEARTS		('h', '♥', 1, "Hearts"),
	DIAMONDS	('d', '♦', 2, "Diamonds"),
	CLUBS		('c', '♣', 3, "Clubs"),
	SPADES		('s', '♠', 4, "Spades");
	
	private final char defSymbol;
	private final char uniSymbol;
	private final int value;
	private final String name;
	
	/**
	 * Sets enum variables.
	 * @param defSymbol default symbol (for no unicode)
	 * @param uniSymbol unicode symbol
	 * @param value suit value, based on order
	 * @param name suit name
	 */
	private StandardSuitEnum(char defSymbol, char uniSymbol, int value, String name) {
		
		this.defSymbol = defSymbol;
		this.uniSymbol = uniSymbol;
		this. value = value;
		this.name = name;
	}
	
	/**
	 * Returns default symbol as the first letter of the suit name.
	 * @return default symbol
	 */
	public char getDefSymbol() {
		
		return this.defSymbol;
	}
	
	/**
	 * Returns unicode symbol.
	 * @return unicode symbol
	 */
	public char getUniSymbol() {
		
		return this.uniSymbol;
	}
	
	/**
	 * Returns suit value based on order in enum.
	 * @return suit value
	 */
	public int getValue() {
		
		return this.value;
	}
	
	/**
	 * Returns suit name.
	 * @return suit name
	 */
	public String getName() {
		
		return this.name;
	}
}
