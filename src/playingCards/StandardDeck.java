package playingCards;

import static java.lang.System.out;

/**
 * Simple deck class.
 * Based on a standard deck of 52 playing cards.
 * @author Casey J. Murphy
 *
 */
public class StandardDeck implements DeckInterface {
	
    // class constants
	private static final int SKIPPEDRANKS = 2;	// skips jokers and low ace
	private static final int SKIPPEDSUITS = 1;	// skips empty suit
    private static final int SUITS = StandardSuitEnum.values().length - SKIPPEDSUITS;
    private static final int RANKS = StandardRankEnum.values().length - SKIPPEDRANKS;
    private static final int SIZE = SUITS * RANKS;

    // class variables
    private static int deckCount;

    // instance variables
    private StandardCard[] deck;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Creates and initializes a new deck of 52 standard playing cards.
     */
    public StandardDeck() {
    	
    	deckCount = 0;
        deck = new StandardCard[SIZE];
        init();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns deck size.
     * @return deck size
     */
    public int getSize() {
    	
        return SIZE;
    }

    /**
     * Returns a card from the top of the deck.  
     * @return top card
     * @throws DeckException if deck is empty
     */
    public StandardCard getCard() throws DeckException {
    	
        if (deckCount > (SIZE - 1))
        {
            throw new DeckException("Deck is empty.");
        }
        
        StandardCard temp = deck[deckCount];
        deckCount++;
        return temp;
    }

    /**
     * Returns number of cards in deck that have been used.
     * @return number of used cards in deck
     */
    public int getDeckCount() {
    	
        return deckCount;
    }

// Operations ----------------------------------------------------------------------------------------------------------
    /**
     * Initializes a standard deck of cards with proper suits and ranks.
     * Excludes jokers and low aces.
     */
    private void init() {
    	
        for (int i = 0; i < SIZE; i++)
        {
        	StandardRankEnum r = StandardRankEnum.values()[(i % RANKS) + SKIPPEDRANKS];
        	StandardSuitEnum s = StandardSuitEnum.values()[(i / RANKS) + SKIPPEDSUITS];
            deck[i] = new StandardCard(r, s);
        }
    }

    /**
     * Shuffles deck of cards.
     */
    public void shuffle() {
    	
        deckCount = 0;

        for (int i = 0; i < SIZE; i++)
        {
            int rand = i + (int) (Math.random() * (SIZE - i));
            StandardCard temp = deck[rand];
            deck[rand] = deck[i];
            deck[i] = temp;
        }
    }
    
    /**
     * Sorts deck.  Not implemented.
     */
    public void sort() {}

    /**
     * Prints deck of cards with short names in four rows.
     */
    public void print() {
    	
        for (int i = 0; i < SIZE; i++) {
            out.print(deck[i].shortName());
            out.print("  ");
            if ((i + 1) % 4 == 0) out.println();
        }
    }
    
    /**
     * Unit test for StandardDeck
     */
    public static void unitTest() {
    	
		out.println("-------------------- Testing StandardDeck Class:\n");
		
		out.println("Initializing deck...");
    	StandardDeck d = new StandardDeck();
    	out.println("Deck Size: " + d.getSize());
    	out.println("Deck Count: " + d.getDeckCount() + "\n");
    	d.print();
    	out.println();
    	
    	out.println("Shuffling deck...");
    	d.shuffle();
    	d.print();
    	
    	out.println();
    	out.println("Pulling card from top of deck...");
    	out.println(d.getCard());
    	out.println("Deck Count: " + d.getDeckCount() + "\n");
    	
    	out.println("\n-------------------- StandardDeck Unit Test Complete.\n");
    }
}
