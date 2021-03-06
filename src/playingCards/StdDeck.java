package playingCards;

import static java.lang.System.out;

import java.util.Arrays;

import playingCards.StdCard;

/**
 * Standard deck
 * @author groovyLlama devteam
 * @version 1.0
 */
public class StdDeck implements DeckInterface {

	// class constants
	private static final int SUITS = StdCard.suitAry.length;
    private static final int RANKS = StdCard.rankAry.length;
    private static final int SIZE = SUITS * RANKS;
    private static final String ERR1 = "ERROR: Deck Empty";

    // class variables
    private static int deckCount;
 	private static boolean debug = false;

    // instance variables
    private StdCard[] deck;

    // Constructors -----------------------------------------------------------
    /**
     * Creates and initializes a new deck of 52 standard playing cards.
     * Aces are low and no jokers are present.
     */
    public StdDeck() {
    	
    	deckCount = 0;
        deck = new StdCard[SIZE];
        init();
    }
    
    /**
     * Creates and initializes a new deck of 52 standard 
     * playing cards sequenced for an easy win in Freecell.
     * @param b value is irrelevant
     */
    public StdDeck(boolean b) {
    	
    	deckCount = 0;
    	deck = new StdCard[SIZE];
    	easyWin();
    }
    
    // Mutators ---------------------------------------------------------------    
	/**
	 * Toggles debug mode.
	 */
	public static void toggleDebug() {
		debug = !debug;
	}

	// Accessors --------------------------------------------------------------
    /**
     * Returns deck size.
     * @return deck size
     */
    public int size() {
        return SIZE;
    }
    
    /**
     * Returns number of cards that have been pulled from top of the deck.
     * @return deck count
     */
    public int getDeckCount() {
        return deckCount;
    }

    /**
     * Returns a card from the top of the deck.
     * @throws DeckException if deck is empty  
     * @return top card
     */
    public StdCard getCard () throws DeckException {
    	
        if (deckCount >= SIZE) throw new DeckException("Empty Deck");
        return deck[deckCount++];
    }
    
    public boolean isEmpty() {
    	return deckCount == SIZE;
    }

    // Functionality ----------------------------------------------------------
    /**
     * Initializes a deck of cards with proper suits and ranks.
     */
    private void init() {
    	
        for (int i = 0; i < SIZE; i++) {
            deck[i] = new StdCard(i % RANKS, i / RANKS);
        }
    }
    
    /**
     * Creates an easy win deck.
     */
    private void easyWin() {
    	
    	int[] seq = { 12,5,11,4,10,3,9,2,8,1,7,0,6 };
    	
    	int j = -1;
        for (int i = 0; i < SIZE; i++) {
            if (i % SUITS == 0) j++;
        	deck[i] = new StdCard(seq[j], i % SUITS);
        }
    }

    /**
     * Shuffles deck of cards.
     */
    @Override
    public void shuffle() {
    	
    	if (debug) out.println("\n---playingCards.StdDeck.shuffle--- ");
    	
        deckCount = 0;
        for (int i = 0; i < SIZE; i++) {
        	
            int rand = i + (int) (Math.random() * (SIZE - i));
            StdCard temp = deck[rand];
            deck[rand] = deck[i];
            deck[i] = temp;
            
            if (debug) out.println(temp + "--> pos " + i);
        }
    }

    /**
     * Prints deck of cards in four rows.
     */
    @Override
    public void print() {
        
    	for (int i = 0; i < SIZE; i++) {
            out.print(deck[i]);
            if ((i + 1) % 13 == 0) out.println();
        }
    }
    
    /**
     * Toggles unicode mode for each card in deck.
     */
    public void toggleUnicode() {
    	for (StdCard c : deck) c.toggleUnicode();
    }
    
    // Equality ---------------------------------------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(deck);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StdDeck other = (StdDeck) obj;
		if (!Arrays.equals(deck, other.deck)) {
			return false;
		}
		return true;
	}

    // Testing ----------------------------------------------------------------
    public static void unitTest() {
    	
    	out.println("-------------------- Testing StdDeck Class:\n");
    	
    	StdDeck d = new StdDeck();
		out.println("Deck initialized:");
		d.print();
		out.println();
				
		out.println("Shuffling deck...");
		d.shuffle();
		d.print();
		out.println();
		
		out.println("Testing deck empty exception...");
		// using up all cards
		for (int i = 0; i < d.size(); i++) d.getCard();
		// pulling one more
		try { d.getCard(); }
		catch (DeckException de) { out.println(ERR1); }
		
    	out.println("-------------------- StdDeck Unit Test Complete.\n");
    }
}
