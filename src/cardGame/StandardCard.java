package cardGame;

import static java.lang.System.out;

/**
 * Simple playing card class.
 * Model based on cards used in a standard deck of playing cards.
 * @author Casey J. Murphy
 * @version 1.0
 */
public class StandardCard implements CardInterface {
	
    // class constants
    private static final StandardRank rDEFAULT = StandardRank.values()[0];
    private static final StandardSuit sDEFAULT = StandardSuit.values()[0];

    // instance variables
    private StandardRank rank;
    private StandardSuit suit;
    private String name;
    private String sRank;
    private String sSuit;
    private char symbol;

    // Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default card is the two of hearts.
     */
    public StandardCard() {
    	
        setRank(rDEFAULT);
        setSuit(sDEFAULT);
        setName();
    }

    /**
     * Specific card Constructor<br>
     * Creates a playing card based on rank and suit as integers.  
     * Remaining instance variables are dynamically generated.
     * @param r
     * @param s
     */
    public StandardCard(StandardRank r, StandardSuit s) {
    	
        setRank(r);
        setSuit(s);
        setName();
    }

    // Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns rank as an integer.
     * @return rank integer
     */
    @Override
    public int getRank() {
    	
        return rank.getValue();
    }

    /**
     * Returns suit as an integer.
     * @return suit integer
     */
    @Override
    public int getSuit() {
    	
        return suit.getValue();
    }

    /**
     * Returns rank as a proper playing card rank (2 - A).
     * @return rank string
     */
    public String getRankString() {
    	
        return sRank;
    }

    /**
     * Returns suit as a sting (i.e. diamonds).
     * @return suit string
     */
    public String getSuitString() {
    	
        return sSuit;
    }

    /**
     * Returns suit as a unicode symbol.
     * @return suit symbol
     */
    public char getSuitSym() {
    	
    	// optimally, check system encoding and return either
    	// default or unicode symbol based on encoding type
        return symbol;
    }

    // Mutators ------------------------------------------------------------------------------------------------------------
    /**
     * Sets card rank.
     * Sets rank string.
     * @param newRank new card rank as integer
     */
    private void setRank(StandardRank newRank) {
    	
         rank = newRank;
         sRank = newRank.getName();
    }

    /**
     * Sets card suit.
     * Sets suit string.
     * Sets suit symbol.
     * @param newSuit new card suit as integer
     */
    private void setSuit(StandardSuit newSuit) {
    	
        suit = newSuit;
        sSuit = newSuit.getName();
        symbol = newSuit.getUniSymbol();
    }

    /**
     * Generates card name as text based on array definitions.
     */
    private void setName() {
    	
        name = sRank + " of " + sSuit;
    }
    
    // Overrides -----------------------------------------------------------------------------------------------------------
    /**
     * Returns string with proper rank and suit in as text.
     * @return name string
     */
    public String toString() {
    	
        return name;
    }
    
    /**
     * Returns card's face up string.
     */
    public String shortName() {
    	
    	//TODO make a OS dependent switcher for unicode
    	// Uncomment following line for unicode suit characters.
    	// String s = String.format("%2s%s ", rank.getSym(), suit.getUniSymbol());
    	// Comment following line if using unicode suit characters.
    	
    	String s = String.format("%2s%s ", rank.getSym(), suit.getDefSymbol());
    	return s;
    }
    
    /**
     * Checks if two cards have the same suit and rank.
     * @param c card to check against
     * @return true if they are the same, false otherwise
     */
    public boolean equals(StandardCard c) {
    	
    	if (rank.getValue() == c.getRank() && 
    		suit.getValue() == c.getSuit()) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Compares two card face values.  Returns -1 if source is smaller,
     * 1 if source is bigger, and 0 if they are equal.
     * @param c target card
     * @return result of comparison
     */
    public int compareTo(StandardCard c) {
    	
    	if (rank.getValue() < c.getRank()) return -1;
    	if (rank.getValue() > c.getRank()) return 1;
    	return 0;
    }
    
    // Operations ----------------------------------------------------------------------------------------------------------
    /**
     * Returns value of card based on 100 point ranges for 
     * each suit plus rank (face value).
     */
    public int valueOf() {
    	
    	int value = 0;
    	
    	value = suit.getValue() * 100;
    	value += rank.getValue();
    	
    	return value;
    }
    
    public static void unitTest() {
    	
    	out.println("-------------------- Testing StandardCard Class:\n");
    	
    	StandardCard c1 = new StandardCard();
    	StandardRank r = StandardRank.values()[4];
    	StandardSuit s = StandardSuit.values()[4];
    	StandardCard c2 = new StandardCard(r, s);
    	
    	out.println("Rank Int: " + c2.getRank());
    	out.println("Suit Int: " + c2.getSuit());
    	out.println("Value of card: " + c2.valueOf());
    	out.println("Rank String: " + c2.getRankString());
    	out.println("Suit String: " + c2.getSuitString());
    	out.println("Suit Symbol: " + c2.getSuitSym());
    	out.println("Short Name: " + c2.shortName());
    	out.println("Full Name: " + c2);
    	
    	out.println("\nComparing Two Cards...");
    	out.println(c2 + " = " + c2 + "? " + c2.equals(c2));
    	out.println(c1 + " = " + c2 + "? " + c1.equals(c2));
    	out.println(c2 + " compared to " + c2 + ": " + c2.compareTo(c2));
    	out.println(c1 + " compared to " + c2 + ": " + c1.compareTo(c2));
    	
    	out.println("\n-------------------- StandardCard Unit Test Complete.\n");
    }
}
