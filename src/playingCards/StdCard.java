package playingCards;

import static java.lang.System.out;

/**
 * Standard playing card
 * @author groovyLlama devteam
 * @version 1.0
 */
public class StdCard implements CardInterface {
    
	// class constants
	static final String[] rankAry = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	static final String[] suitAry = { "Diamonds", "Clubs", "Hearts", "Spades" };
	static final Character[] defSymAry = { 'd', 'c', 'h', 's' };
	static final Character[] uniSymAry = { '♦', '♣', '♥', '♠' };

	// class variables
	private static boolean debug = false;
	
    // instance variables
    private boolean unicode = false;
    private int rank;
    private int suit;

// Constructors ---------------------------------------------------------------
    /**
     * Default card is the ace of diamonds.
     */
    public StdCard() {
        setRank(0);
        setSuit(0);
    }

    /**
     * Creates a playing card based on rank and suit as integers.
     * @param r rank from 0-12
     * @param s suit from 0-3
     */
    public StdCard(int r, int s) {
        setRank(r);
        setSuit(s);
    }
    
// Mutators -------------------------------------------------------------------
    /**
     * Sets card rank.
     * @param newRank new card rank as integer
     */
    private void setRank(int newRank) {
        rank = newRank;
    }

    /**
     * Sets card suit.
     * @param newSuit new card suit as integer
     */
    private void setSuit(int newSuit) {
        suit = newSuit;
    }
    
    /**
     * Toggles use of unicode suit symbol.
     */
    public void toggleUnicode() {
    	unicode = !unicode;
    }
    
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}

// Accessors ------------------------------------------------------------------
    /**
     * Returns rank as an integer.
     * @return rank integer
     */
	@Override
    public int getRank() {
        return rank;
    }
	
	/**
	 * Returns face value of card.
	 * @return face value
	 */
	public int getValue() {
		return rank + 1;
	}

    /**
     * Returns suit as an integer.
     * @return suit integer
     */
    @Override
    public int getSuit() {
        return suit;
    }

    /**
     * Returns rank as a proper playing card rank (2 - A)
     * @return rank string
     */
    public String getRankString() {
        return rankAry[rank].toString();
    }

    /**
     * Returns suit as a word.
     * @return suit string
     */
    public String getSuitString() {
        return suitAry[suit];
    }
    
    /**
     * Returns default suit symbol.
     * @return suit default symbol
     */
    public String getDefSym() {
    	return defSymAry[suit].toString();
    }

    /**
     * Returns unicode suit symbol.
     * @return suit unicode symbol
     */
    public String getUniSym() {
        return uniSymAry[suit].toString();
    }

    /**
     * Returns string with proper rank and suit in words.
     * @return name string
     */
    public String getName() {
        return rankAry[rank] + " of " + suitAry[suit];
    }

    /**
     * Returns string with proper rank and suit as a default symbol (d,c,h,s).
     * If unicode is toggled, returns suit symbol as unicode instead.
     * @return rank and suit symbol
     */
    @Override
    public String toString() {
    	
    	Character sym;
    	if (unicode) sym = uniSymAry[suit];
    	else sym = defSymAry[suit];
        String s = String.format("%2s%s ", rankAry[rank], sym);
        return s;
    }
    
    /**
     * Returns true if card is using unicode symbol.
     * @return true if using unicode
     */
    public boolean isUnicode() {
    	return unicode;
    }
    
    public boolean isBlack() {
    	return suit == 1 || suit == 3;
    }
    
 // Testing -------------------------------------------------------------------
    /**
     * Standard Card unit test.
     */
    public static void unitTest() {
    
    	out.println("-------------------- Testing StdCard Class:\n");
		
		StdCard[] testSequence = {
				
				new StdCard(),
				new StdCard(3,1),
				new StdCard(7,2),
				new StdCard(12,3)
		};
		
		for (StdCard c : testSequence) {
			
			String color = "Red";
			if (c.isBlack()) color = "Black";
			
			out.println("Full Name: " + c.getName());
			out.println("Color: " + color);
			out.println("Rank Int: " + c.getRank());
			out.println("Rank Value: " + c.getValue());
			out.println("Rank String: " + c.getRankString());
			out.println("Suit Int: " + c.getSuit());
			out.println("Suit String: " + c.getSuitString());
			out.println("Suit Default Symbol: " + c.getDefSym());
			out.println("Suit Unicode Symbol: " + c.getUniSym());
			out.println("Short Name (toString): " + c.toString());
			out.println(c + " using unicode? " + c.isUnicode());
			out.println("Toggling unicode...");
			c.toggleUnicode();
			out.println(c + " using unicode? " + c.isUnicode());
			out.println();
		}
		
    	out.println("-------------------- StdCard Unit Test Complete.\n");
    }
}
