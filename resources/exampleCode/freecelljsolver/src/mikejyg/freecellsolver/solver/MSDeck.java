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

/**
 *   Based on the code in freecell solver, by Shlomi Fish, 2000
 */

package mikejyg.freecellsolver.solver;

/**
 * create a shuffled deck the same way Microsoft Freecell does
 * 
 */
public class MSDeck extends PlayingCardDeck {

	public MSDeck() {
		super();
	}

	long seed;
	
	///\brief Create a random number in the same fashion that miccrosoft does
	///
	///\param Seed is the seed of the random number
	///\return A random number
	protected int MicrosoftRandom()
	{
		seed = seed * 214013 + 2531011;
		return (int) ( (seed >> 16) & 0x7fff ); 
	}
	
	public long getSeed() {
		return seed;
	}
	
	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}

	// the microsoft init
	@Override
	public void init() 
	{
		byte i, j;
		int pos = 0;
		for (i=1; i<=13; i++)
		{
			for (j=0; j<4; j++) {
				// MS codes suite as S(3), H(2), D(1), C(0)
				byte suit;
				if (j==0)
					suit = PlayingCard.CLUB;
				else if (j==1)
					suit = PlayingCard.DIAMOND;
				else if (j==2)
					suit = PlayingCard.HEART;
				else
					suit = PlayingCard.SPADE;
				
				cards[pos++] = PlayingCard.makeCard( i, suit);
			}
		}
	}
	
	// the microsoft shuffle
	@Override
	public void shuffle()
	{
		setSeed(seed);
		
		int i, j;
		int remain = DECK_CARDS;

		byte newCards[] = new byte[DECK_CARDS];
		
		for (i=0; i<DECK_CARDS; i++)
		{
			// get the random position
			j = MicrosoftRandom() % remain;
			remain--;
			newCards[i] = cards[j];
			cards[j] = cards[remain];
		}
		cards = newCards;
	}

}
