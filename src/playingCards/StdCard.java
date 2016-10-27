package playingCards;

/**
 *
 * @author groovyLlama devteam
 * @version 1.0
 */
public class StdCard {
    // class constants
    static final Character[] uniSymAry = {'♦', '♣', '♥', '♠'};
    static final Character[] defSymAry = {'d', 'c', 'h', 's'};
    static final String[] rankAry = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    static final String[] suitAry = { "Diamonds", "Clubs", "Hearts", "Spades" };

    // instance variables
    private boolean useUni = false;
    private int rank;
    private int suit;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Default card is the two of diamonds.  Remaining instance variables
     * are dynamically generated.
     */
    public StdCard() {
        setRank(0);
        setSuit(0);
    }

    /**
     * Specific card Constructor<br>
     * Creates a playing card based on rank and suit as integers.  Remaining
     * instance variables are dynamically generated.
     * @param r
     * @param s
     */
    public StdCard(int r, int s) {
        setRank(r);
        setSuit(s);
    }
    
// Mutators ------------------------------------------------------------------------------------------------------------
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

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns rank as an integer.
     * @return rank integer
     */
    public int getRankInt() {
        return rank;
    }

    /**
     * Returns suit as an integer.
     * @return suit integer
     */
    public int getSuitInt() {
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
     * Returns spelled out suit as a sting (i.e. diamonds)
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
     * Returns unicode suit symbol
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
     * Returns string with proper rank and suit as a unicode symbol.  If show
     * is false, returns '###' to signify card is face down.
     * @param show true for face up, false for face down
     * @return name and symbol string
     */
    @Override
    public String toString() {
    	if (useUni) return rankAry[rank] + uniSymAry[suit];
        return rankAry[rank] + defSymAry[suit];
    }
    
    public void toggleUnicode() {
    	useUni = !useUni;
    }

    public static void unitTest() {
    	
    }
}
