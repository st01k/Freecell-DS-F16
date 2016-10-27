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
    static final Character[] rankAry = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K' };
    static final String[] suitAry = { "Diamonds", "Clubs", "Hearts", "Spades" };

    // instance variables
    private int rank;
    private int suit;
    private String sRank;
    private String sSuit;
    private String name;
    private Character uniSym;
    private Character defSym;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Default card is the two of diamonds.  Remaining instance variables
     * are dynamically generated.
     */
    public StdCard() {
        setRank(0);
        setSuit(0);
        setName();
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
        setName();
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
    public String getRank() {
        return sRank;
    }

    /**
     * Returns spelled out suit as a sting (i.e. diamonds)
     * @return suit string
     */
    public String getSuit() {
        return sSuit;
    }
    
    /**
     * Returns default suit symbol.
     * @return suit default symbol
     */
    public String getDefSym() {
    	return defSym.toString();
    }

    /**
     * Returns unicode suit symbol
     * @return suit unicode symbol
     */
    public String getUniSym() {
        return uniSym.toString();
    }

    /**
     * Returns string with proper rank and suit in words.
     * @return name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns string with proper rank and suit as a unicode symbol.  If show
     * is false, returns '###' to signify card is face down.
     * @param show true for face up, false for face down
     * @return name and symbol string
     */
    @Override
    public String toString() {
        return sRank + uniSym;
    }

// Mutators ------------------------------------------------------------------------------------------------------------
    /**
     * Sets card rank.
     * @param newRank new card rank as integer
     */
    private void setRank(int newRank) {
        rank = newRank;
        sRank = rankAry[rank].toString();
    }

    /**
     * Sets card suit.
     * @param newSuit new card suit as integer
     */
    private void setSuit(int newSuit) {
        suit = newSuit;
        sSuit = suitAry[suit];
        uniSym = uniSymAry[suit];
        defSym = defSymAry[suit];
    }

    /**
     * Generates card name as text based on array definitions.
     */
    private void setName() {
        name = rankAry[rank] + " of " + suitAry[suit];
    }
}
