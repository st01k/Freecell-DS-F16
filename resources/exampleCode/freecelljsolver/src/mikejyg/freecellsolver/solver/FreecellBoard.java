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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * a class of a freecell board and its methods
 * 
 */
public class FreecellBoard implements Comparable<FreecellBoard> {
	
	public static int numberOfFreeCells = 4;
	public static int numberOfColumns = 8;
	
	byte freeCells[];

	// index is suit
	byte homeCells[];

	public class Column extends ArrayList<Byte> implements Comparable<Column> {

		private static final long serialVersionUID = -1209696337017916336L;

		@Override
		public int compareTo(Column o) {
			int lSize = size();
			int rSize = o.size();
			int idx = 0;
			while ( idx < lSize && idx < rSize) {
				if ( get(idx) < o.get(idx) )
					return -1;
				else if ( get(idx) > o.get(idx) )
					return 1;
				idx++;
			}
			if (lSize < rSize)
				return -1;
			else if (lSize > rSize)
				return 1;
			else
				return 0;
		}

		public byte getLast() {
			return get(size() - 1);
		}
		
	}
	
	Column columns [];

	////////////////////////
	// working variables
	
	byte homeCellRanks[];
	
////////////////////////////////////////////////////
	
	// default constructor
	public FreecellBoard() {
		freeCells = new byte[numberOfFreeCells];
		homeCells = new byte[PlayingCard.SUITS];
		columns = new Column [numberOfColumns];
		homeCellRanks = new byte[PlayingCard.SUITS];

		initColumns();
	}

	// unpack from bytes
	FreecellBoard(byte [] bytes) {
		this();
		set(bytes);
	}

	/**
	 * copy constructor
	 * @param src
	 */
	public FreecellBoard(FreecellBoard src) {
		this();
		copy(src);
	}

	void set(byte bytes[]) {
		int idx = 0;
		int i;
		// unpack home cell first
		for (i=0; i<PlayingCard.SUITS; i++) {
			homeCells[i] = bytes[idx++];
		}
		for (i=0; i<numberOfFreeCells; i++) {
			freeCells[i] = bytes[idx++];
		}
		for (i=0; i<numberOfColumns; i++) {
			columns[i].clear();
			while (bytes[idx] !=PlayingCard.NULL_CARD) {
				columns[i].add(bytes[idx++]);
			}
			idx++;
		}
//		System.out.println(toString());
	}
	
	/**
	 *  make a deep copy
	 * @param src
	 */
	void copy(FreecellBoard src) {
		int i;
		
		for (i=0; i<numberOfFreeCells; i++)
			freeCells[i] = src.freeCells[i];
		
		for (i=0; i<PlayingCard.SUITS; i++)
			homeCells[i] = src.homeCells[i];
		
		for (int col=0; col<numberOfColumns; col++)
		{
			columns[col].clear();
			for (i=0; i<src.columns[col].size(); i++)
				columns[col].add( src.columns[col].get(i) );
		}			
	}
	
	private void initColumns() {
		for (int i=0; i<numberOfColumns; i++) {
			Column column = new Column();
			columns[i] = column;
		}
	}

	// deal a new deck
	public void deal( PlayingCardDeck deck ) {
		int i, col;

		// clear free and home
		for (col=0; col<PlayingCard.SUITS; col++)
		{
			homeCells[col]=PlayingCard.NULL_CARD;
		}
		
		for (col=0; col<numberOfFreeCells; col++)
		{
			freeCells[col]=PlayingCard.NULL_CARD;
		}
		
		// deal to columns
		for (i=0; i<PlayingCardDeck.DECK_CARDS; i++)
		{
			col=i%numberOfColumns;
			columns[col].add( (Byte) deck.getCard(i) );
		}
	}
	
	// turn it into a form for compare
	public void normalize() {
		Arrays.sort(freeCells);
		Arrays.sort( columns );
	}
	
	public String toString()
	{
		String str = new String();
		
		// print free cell
		int col, row;
		for (col=0; col<numberOfFreeCells; col++) {
			str += PlayingCard.toString(freeCells[col]) + " ";
		}
		str += " ";
		
		// print home cell
		for (col=0; col<PlayingCard.SUITS; col++) {
			str += PlayingCard.toString( homeCells[col] ) + " ";
		}
		str += "\n\n";
		
		// print column cell
		row=0;
		boolean end;
		do
		{
			end=true;
			for (col=0; col<numberOfColumns; col++)
			{
				if ( row < columns[col].size() )
				{
					str += PlayingCard.toString( columns[col].get(row) ) + " ";
					if (row != columns[col].size()-1)
						end=false;
				}
				else
					str += "__ ";
			}
			
			if (!end)
				str += '\n';
			
			row++;
		} while (!end);
		
		return str;
	}				

	public boolean isDone()
	{
		for (int i=0; i<PlayingCard.SUITS; i++) {
			if ( PlayingCard.getRank( homeCells[i] ) != PlayingCard.HIGHEST_RANK )
				return false;
		}
		return true;
	}

	// return an index to a free freecell
	public int getFreeFreecellIdx() {
		int freecellIdx = 0;
		while (freecellIdx != numberOfFreeCells) {
			if (freeCells[freecellIdx]==PlayingCard.NULL_CARD)
				return freecellIdx;
			freecellIdx++;
		}
		return -1;
	}

	// return an column index to a empty column, -1 if none 
	public int getEmptyColumn() {
		for (int i=0; i<numberOfColumns; i++) {
			if (columns[i].size() == 0)
				return i;
		}
		return -1; 
	}
	
	public int getNumberOfEmptyFreeCells() {
		int cnt = 0;
		for (int i=0; i<numberOfFreeCells; i++)
			if (freeCells[i] == PlayingCard.NULL_CARD)
				cnt++;
		return cnt;
	}
	
	public int getNumberOfEmptyColumns() {
		int cnt = 0;
		for (int i=0; i<numberOfColumns; i++)
			if (columns[i].size() == 0)
				cnt++;
		return cnt;
	}
	
	// remove and return the last card of a column
	public byte popColumn(int col) {
		int idx = columns[col].size()-1;
		byte card = columns[col].get(idx);
		columns[col].remove(idx);
		return card;
	}

	// return the last card on the column
	public byte getColumnCard(int col) {
		int idx = columns[col].size()-1;
		return columns[col].get(idx);
	}
	
	public static class IllegalBoardException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7062297000862909241L;
		public IllegalBoardException(String msg) {
			super(msg);
		}
	}
	
	// sanity check the board
	public void sanityCheck() throws IllegalBoardException {
		// count cards to make sure no duplidate or missing
		boolean cards[] = new boolean[PlayingCardDeck.DECK_CARDS ];
		int i;
		// init marks
		for (i=0; i<PlayingCardDeck.DECK_CARDS; i++) {
			cards[i] = false;
		}
		for (i=0; i<numberOfFreeCells; i++) {
			if (i!=0)
				if (freeCells[i-1] > freeCells[i])
					throw new IllegalBoardException("freecell not sorted");
			markCard(cards, freeCells[i]);
		}
		for (i=0; i<PlayingCard.SUITS; i++) {
			if (PlayingCard.getRank(homeCells[i]) == 0) {
				if ( homeCells[i] != PlayingCard.NULL_CARD )
					throw new IllegalBoardException("home rank is 0 but : " + Integer.toString(homeCells[i]));
				continue;
			}
			for (int j=1; j< PlayingCard.getRank(homeCells[i]); j++) {
				markCard( cards, PlayingCard.makeCard((byte)j, (byte)i) );
			}
			markCard(cards, homeCells[i]);
		}
		for (int col = 0; col < numberOfColumns; col++) {
			if (col!=0)
				if ( columns[col-1].compareTo(columns[col]) > 0 )
					throw new IllegalBoardException("columns not sorted.");
			Column column = columns[col];
			for (i=0; i<column.size(); i++) {
				if ( column.get(i) == PlayingCard.NULL_CARD )
					throw new IllegalBoardException("column " + col + " card " + i + " is null");
				markCard(cards, column.get(i));
			}
		}

		// verify for missing card
		for (i=0; i<PlayingCardDeck.DECK_CARDS; i++) {
			if ( cards[i] == false ) {
				throw new IllegalBoardException("missing : " + 
						PlayingCard.toString( PlayingCard.makeCard((byte)(i%13+1), (byte)(i/13)) ) );
			}	
		}

	}
	
	private void markCard(boolean[] cards, byte card) throws IllegalBoardException {
		byte rank = PlayingCard.getRank(card);
		if (rank != 0) {
			int idx = PlayingCard.getSuit(card) * 13 + rank - 1;
			if (cards[idx])
				throw new IllegalBoardException("duplicate : " + PlayingCard.toString(card));
			else
				cards[idx] = true;
		}
	}

	public byte popHomeCell(int i) {
		if ( homeCells[i]== PlayingCard.NULL_CARD )
			throw new Error("no homecell to pop at :" + Integer.toString(i));
		byte card = homeCells[i];
		if ( PlayingCard.getRank(card) == 1 )
			homeCells[i] = PlayingCard.NULL_CARD;
		else
			homeCells[i] = PlayingCard.getOneLower(card);
		return card;
	}

	public int getSequenceLength(int col) {
	  int iLen=1;
	  Column column = columns[col];

	  int iPtr=column.size()-1;
	  int iPtr1=iPtr-1;
	  
	  byte rank=PlayingCard.getRank(column.get(iPtr));
	  rank++;
			
	  byte color=PlayingCard.getColor(column.get(iPtr));
	  color = PlayingCard.getOppositeColor(color);
			
	  while (iPtr1>=0) {
		  if ( PlayingCard.getRank(column.get(iPtr1)) == rank &&
				  PlayingCard.getColor(column.get(iPtr1)) == color ) {
			  rank++;
			  color = PlayingCard.getOppositeColor(color);
			  iLen++;
			  iPtr1--;
		  } else
			  break;
	  }
	  return(iLen);
	}

	public void moveColumnToColumn(int col, int dCol, int sequenceLen) {
		int i;
		Column srcColumn = columns[col];
		Column destColumn = columns[dCol];
		int srcIdx = srcColumn.size()-sequenceLen;
		for (i=0; i<sequenceLen; i++) {
			destColumn.add(srcColumn.get(srcIdx++));
		}
		srcIdx = srcColumn.size()-sequenceLen;
		for (i=0; i<sequenceLen; i++)
			srcColumn.remove(srcIdx);
	}
	
	// if movable, return suit, otherwise, -1
	public byte isMovableToHome(byte card) {
		byte rank = PlayingCard.getRank( card );
		byte suit = PlayingCard.getSuit( card );

		if ( PlayingCard.getRank(homeCells[suit]) == rank - 1)
			return suit;
		else
			return -1;
	}
	
	@Override
	public int compareTo(FreecellBoard o) {
		// assume it is already sorted
		int i;
		for (i=0; i<numberOfFreeCells; i++) {
			if (freeCells[i] < o.freeCells[i])
				return -1;
			else if (freeCells[i] > o.freeCells[i])
				return 1;
		}
		for (i=0; i<PlayingCard.SUITS; i++) {
			if (homeCells[i] < o.homeCells[i])
				return -1;
			else if (homeCells[i] > o.homeCells[i])
				return 1;
		}
		for (i=0; i<numberOfColumns; i++) {
			int k = columns[i].compareTo(o.columns[i]); 
			if ( k != 0 )
				return k;
		}
		return 0;
	}

	// if the home cell is good, do not move it!
	public boolean isGoodHomeCell(byte suit) {
		// A and 2 are always good
		if ( PlayingCard.getRank(homeCells[suit]) <= 2 )
			return true;
	
		for (byte s=0; s<PlayingCard.SUITS; s++) {
			if (suit==s)
				continue;
			
			// only consider opposite color
			if ( PlayingCard.getColorOfSuit( s ) == PlayingCard.getColorOfSuit( suit ) )
				continue;
			
			// the minus 1 card of opposite color, can it go on to the home cell?
			// if not, we may need to move this card down
			if ( PlayingCard.getRank(homeCells[suit]) - 1 > PlayingCard.getRank(homeCells[s]) + 1 )
				return false;
		}
//		System.out.println("good home : " + i);
//		System.out.println(toString());
		return true;
	}

	// if the column begins with k and has 4 or more cards all in sequence, then it is good
	public boolean isGoodColumnCells(int col) {
		Column column = columns[col];
		if (column.size()==0)
			return false;
		if ( PlayingCard.getRank(column.get(0))!=13 )
			return false;
		if ( column.size() < 4)
			return false;
		int i=1;
		byte prevColor = PlayingCard.getColor(column.get(0));
		while (i<column.size()) {
			byte color = PlayingCard.getColor(column.get(i));
			if ( PlayingCard.getRank(column.get(i)) != 13-i 
					|| color == prevColor )
				return false;
			prevColor = color;
			i++;
		}
		return true;
	}
	
	// pack into bytes
	public byte [] toBytes() {
		byte bytes[] = new byte[FCBoardBytes.ARRAY_SIZE];

		toBytes(bytes);
		
		return bytes;
	}

	public void toBytes(byte bytes[]) {
		int idx = 0;
		// assume it is already sorted
		int i, j;
		// pack home cell first
		for (i=0; i<PlayingCard.SUITS; i++) {
			bytes[idx++] = homeCells[i];
		}
		for (i=0; i<numberOfFreeCells; i++) {
			bytes[idx++] = freeCells[i];
		}
		for (i=0; i<numberOfColumns; i++) {
			Column column = columns[i];
			for (j=0; j<column.size(); j++)
				bytes[idx++] = column.get(j);
			bytes[idx++] = 0;
		}
		
		// need to clear the rest for berkeley DB
		while (idx < bytes.length) {
			bytes[idx++] = 0;
		}
	}
	
	// change suit to color for card that is not next to be put on a home cell
	// effective threshold range 0(even A is ignored)..13(k is not ignored)
	public void toEquivalent(int nonEquivalentThreshold) {
		int i;
		for (i=0; i<PlayingCard.SUITS; i++) {
			homeCellRanks[i] = (byte) (PlayingCard.getRank(homeCells[i]) + nonEquivalentThreshold);
		}
		
		for (i=0; i<numberOfFreeCells; i++) {
			byte card = freeCells[i];
			if (card==PlayingCard.NULL_CARD)
				continue;
			if ( PlayingCard.getRank(card) > homeCellRanks[PlayingCard.getSuit(card)] ) {
				freeCells[i] = PlayingCard.changeToColorOnly(card);
			}
		}
		
		for (i=0; i<numberOfColumns; i++) {
			Column column = columns[i];
			for (int j=0; j<column.size(); j++) {
				byte card = column.get(j);
				if ( PlayingCard.getRank(card) > homeCellRanks[PlayingCard.getSuit(card)] ) {
					column.set(j, PlayingCard.changeToColorOnly(card) );
				}
			}
		}
	}

	/**
	 * read in a free cell board from an input stream
	 * the format is the same as the output of the toString method
	 * 
	 * @param is InputStream
	 * @return
	 * @throws ParseException
	 */
	static public FreecellBoard parse(InputStream is) throws ParseException {
		FreecellBoard board = new FreecellBoard();
		
		Scanner scanner = new Scanner(is);
		int i;
		try {
			for (i=0; i<numberOfFreeCells; i++) {
				board.freeCells[i] = PlayingCard.parse(scanner.next());
			}

			for (i=0; i<PlayingCard.SUITS; i++) {
				board.homeCells[i] = PlayingCard.parse(scanner.next());
			}

			i = 0;
			while (scanner.hasNext()) {
				byte card = PlayingCard.parse(scanner.next());
				if (card != PlayingCard.NULL_CARD) {
					board.columns[i].add(card);
				}
				i++;
				if (i>=numberOfColumns)
					i=0;
			}

		} catch (PlayingCard.ParseException e) {
			throw new ParseException(e.getMessage());
		}

		try {
			board.sanityCheck();
		} catch (IllegalBoardException e) {
			throw new ParseException("sanity check exception : " + e.getMessage());
		}

		return board;
	}

	public static class ParseException extends Exception {
		private static final long serialVersionUID = -8546014451907953979L;

		public ParseException(String string) {
			super(string);
		}
	}	

	public void setHomeCell(int idx, byte card) {
		homeCells[idx] = card;
	}
	
	public void setFreeCell(int idx, byte card) {
		freeCells[idx] = card;
	}
	
	public void appendToColumn(int idx, byte card) {
		columns[idx].add(card);
	}
	
	public void setColumn(int idx, byte[] cards) {
		columns[idx].clear();
		for (int i=0; i<cards.length; i++) {
			columns[idx].add(cards[i]);
		}
	}
	
	class IllegalMoveException extends Exception {
		private static final long serialVersionUID = -3134683233706297040L;
		public IllegalMoveException(String string) {
			super(string);
		}
	}
	
	void verifyMoves(ArrayList<Integer> moves) throws IllegalMoveException {
		for (int i=0; i<moves.size(); i++) {
			int move = moves.get(i);
			
			switch (FCMove.getSourceLocation(move)) {
			case FCMove.LOCATION_TYPE_COLUMN :
				switch (FCMove.getDestLocation(move)) {
				case FCMove.LOCATION_TYPE_COLUMN :
					//TODO
					
					break;
				case FCMove.LOCATION_TYPE_FREE:
					//TODO
					
					break;
					
				case FCMove.LOCATION_TYPE_HOME:
					//TODO
					
					break;
					
				default:
					throw new IllegalMoveException("unknown destination location : " + FCMove.getDestLocation(move));
				}
				break;
			
			case FCMove.LOCATION_TYPE_FREE:
				switch (FCMove.getDestLocation(move)) {
				case FCMove.LOCATION_TYPE_COLUMN :
					//TODO
					
					break;
				case FCMove.LOCATION_TYPE_HOME:
					//TODO
					
					break;
				default:
					throw new IllegalMoveException("unknown destination location : " + FCMove.getDestLocation(move));
				}
				break;
			
			case FCMove.LOCATION_TYPE_HOME:
				throw new IllegalMoveException("not veryfing moving from home");
				
			default:
				throw new IllegalMoveException("unknown source location : " + FCMove.getSourceLocation(move));
			}
		}
	}

	public ArrayList<String> toStdString(int move) {
		if (FCMove.getMoveType(move) != FCMove.MOVE_TYPE_FCS_META)
			throw new Error("move type is not FCS_META");
		
		ArrayList<String> strs = new ArrayList<String>();
		int j=0;
		for (int i=0; i<FCMove.getMoveLength(move); i++) {
			String str = Character.toString( (char) ( '1' + FCMove.getSourceId(move) ) );
			while (j<numberOfFreeCells) {
				if (freeCells[j] == PlayingCard.NULL_CARD) {
					str += (char) ('a' + j);
					j++;
					break;
				}	
				j++;
			}
			strs.add(str);
		}
		String str = Character.toString( (char) ( '1' + FCMove.getSourceId(move) ) ) + "h";
		strs.add(str);
		
		return strs;
	}
	
}
