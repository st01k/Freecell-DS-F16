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

/**
 * class for routines of a playing card
 * 
 * only defines operations on card represented by a integer
 * rank starts with 1: A-1, K-13; 
 * rank 0 means no card
 * suit is 0 - 3, color is 0 - 1
 * card coding: 
 * 	RRRRSS
 *
 */
public class PlayingCard {
	public static final int SUITS = 4;
	
	// notice the coding, so that bit 1 is the color
	public static final byte SPADE=3;	// 11
	public static final byte CLUB=2;	// 10
	public static final byte HEART=1;	// 01
	public static final byte DIAMOND=0;	// 00

	public static final byte BLACK=1;
	public static final byte RED=0;

	public static final byte HIGHEST_RANK = 13;
	
	public static final char cSuit[]={'D', 'H', 'C', 'S'};

	public static final char cRank[]=
	{
		'-', 'A', '2', '3', '4', '5', '6', '7', '8', '9',
		'T', 'J', 'Q', 'K'
	};

	public static final byte NULL_CARD = 0;

	/////////////////////////////////////////////////////////

	public static byte getRank(byte card)
	{
		return (byte) (card >> 2);
	}

	public static byte getSuit(byte card)
	{
		return (byte) ( card & 0x03 );
	}

	public static byte getColor(byte card)
	{
		return getColorOfSuit(PlayingCard.getSuit(card));
	}

	public static boolean isSameColor(byte c1, byte c2)
	{
		return getColor(c1) == getColor(c2);
	}

	public static byte makeCard(byte rank, byte suit)
	{
		if (rank==0)
			return(NULL_CARD);

		if (rank < 1 || rank > HIGHEST_RANK || suit < 0 || suit >= SUITS)
		{
			throw new Error("bad arguments rank : " + Byte.toString(rank) + ", suite : " + Byte.toString(suit) );
		}

		rank<<=2;

		return (byte) ( rank | suit );
	}

	public static byte changeToColorOnly(byte card) {
		// remove last bit
		return (byte) (card & 0xfe);
	}
	
	// return the 2 character representation of a card
	public static String toString(byte card)
	{
		String str = new String();
		if (card==0)
			str="__";
		else
		{
			str += cRank[ getRank(card) ];
			str += cSuit[ getSuit(card) ];
		}
		return str;
	}

	public static byte parse(String str) throws ParseException {
		if (str.equals("__") || str.equals("--"))
			return NULL_CARD;
		byte rank=1;
		while (rank <=HIGHEST_RANK) {
			if (str.charAt(0) == cRank[rank])
				break;
			rank++;
		}
		if (rank>HIGHEST_RANK)
			throw new ParseException("parse error");
		
		byte suit=0;
		while (suit < SUITS) {
			if ( str.charAt(1) == cSuit[suit] )
				break;
			suit++;
		}
		if (suit >= SUITS)
			throw new ParseException("parse error");
		
		return makeCard(rank, suit);
	}
	
	public static class ParseException extends Exception {
		private static final long serialVersionUID = -1451220728430826497L;
		public ParseException(String str) {
			super(str);
		}
	}
	
	// return the color of a suit
	public static byte getColorOfSuit(byte s) {
		return (byte) (s>>1);
	}

	public static byte getOneLower(byte card) {
		if (card==NULL_CARD)
			throw new Error("card is NULL");

		return (byte) (card - 0x04);
	}

	public static byte getOppositeColor(byte color) {
		return (byte) (color ^ 0x01);
	}
	
}
