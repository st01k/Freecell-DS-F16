package playingCards;

import static java.lang.System.out;

import playingCards.StdCard;

/**
 * Simple deck class.
 * @author groovyLlama devteam
 *
 */
public class StdDeck implements DeckInterface {
    // class constants
    private static final int SUITS = StdCard.suitAry.length;
    private static final int RANKS = StdCard.rankAry.length;
    private static final int SIZE = SUITS * RANKS;

    // class variables
    private static int deckCount;

    // instance variables
    private StdCard[] deck;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Creates and initializes a new deck of 52 standard playing cards.
     */
    public StdDeck() {
    	
        deck = new StdCard[SIZE];
        init();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns deck size
     * @return deck size
     */
    public int getSize() {
    	
        return SIZE;
    }

    /**
     * Returns a card from the top of the deck.  If too few cards remain, reshuffles
     * deck, resets deckCount, and deals from the top.
     * @return top card
     */
    public StdCard getCard () throws DeckException {
    	
        if (deckCount >= SIZE) throw new DeckException("Empty Deck");

        StdCard temp = deck[deckCount];
        deckCount++;
        return temp;
    }

    /**
     * Returns current deck count based on the number of cards that have been pulled
     * from the top of the deck.
     * @return deck count
     */
    public int getDeckCount() {
    	
        return deckCount;
    }

// Functionality -------------------------------------------------------------------------------------------------------
    /**
     * Initializes a deck of cards with proper suits and ranks.
     */
    private void init() {
    	
    	deckCount = 0;
        for (int i = 0; i < SIZE; i++) {
            deck[i] = new StdCard(i % RANKS, i / RANKS);
        }
    }

    /**
     * Shuffles deck of cards.
     */
    public void shuffle() {
    	
        deckCount = 0;

        for (int i = 0; i < SIZE; i++) {
        	
            int rand = i + (int) (Math.random() * (SIZE - i));
            StdCard temp = deck[rand];
            deck[rand] = deck[i];
            deck[i] = temp;
        }
    }
    
    public void sort() {
    	init();
    }

    /**
     * Prints deck of cards in four rows.
     */
    public void print()
    {
        for (int i = 0; i < SIZE; i++) {
            out.print(deck[i].toString());
            if ((i + 1) % 4 == 0) System.out.println();
        }
    }
}
