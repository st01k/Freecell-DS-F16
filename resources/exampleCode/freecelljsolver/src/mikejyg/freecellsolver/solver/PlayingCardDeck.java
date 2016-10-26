/**
 * Copyright (C) 2010 Junyang Gu <mikejyg@gmail.com>
 * 
 * This file is part of FreecellJSolver.
 *
 * FreecellJSolver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FreecellJSolver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FreecellJSolver.  If not, see <http://www.gnu.org/licenses/>.
 */

package mikejyg.freecellsolver.solver;

import java.util.Random;

/**
 * a 52-card playing card deck
 * handles shuffling of the deck
 *  
 */
public class PlayingCardDeck
{
	public static final int DECK_CARDS = 52;
	
   	byte[] cards = new byte[DECK_CARDS];

   	Random random = new Random();
   	
/////////////////////////////////////////////////
   	
	PlayingCardDeck()
	{
		init();
	}

	// init 52 cards as single deck start from 'start'
	public void init()
	{
		byte i;
		for (i=0; i<52; i++)
		{
			cards[i] = PlayingCard.makeCard( (byte) ( (i % 13)+1 ), (byte) ( i / 13 ) );
		}
	}
	
	public byte[] getCards() {
		return cards;
	}
	
	public byte getCard(int index)
	{
		return cards[index];
	}

	// print the deck
	public String toString() {
		String str = new String();
		int i;
		for (i=0; i<DECK_CARDS; i++)
		{
			str += PlayingCard.toString( (byte) getCard(i) ) + " ";
			if (i % 8 == 7)
				str += '\n';
		}
		if (i % 8 != 0)
			str += '\n';

		return str;
	}

	// use the Math.random function to shuffle the deck
	// should be a perfect shuffle
	public void shuffle()
	{
		int i;
		int iPos;
		byte card;

		for (i=0; i<DECK_CARDS; i++)
		{
			// get the random position
			iPos=(int) ( random.nextInt(DECK_CARDS) ) ;
			// swap two cards
			card=cards[i];
			cards[i]=cards[iPos];
			cards[iPos]=card;
		}
	}
	
	public void setSeed(long seed) {
		random.setSeed(seed);
	}
	
}