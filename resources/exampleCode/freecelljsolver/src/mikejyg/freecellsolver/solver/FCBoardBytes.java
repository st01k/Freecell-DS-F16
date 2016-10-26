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

import java.io.Serializable;

/**
 * a board backed by a byte array
 *
 */
public class FCBoardBytes implements Comparable<FCBoardBytes>, Serializable {

	private static final long serialVersionUID = -2792144519306934728L;

	// the bytes are arranged in the following order:
	// dest cells, 0 if none
	// free cells, 0 if none
	// tiles cells, column by column, one 0 at the end of each column
	public static final int ARRAY_SIZE = 
		FreecellBoard.numberOfFreeCells + PlayingCard.SUITS + PlayingCardDeck.DECK_CARDS + FreecellBoard.numberOfColumns;
	
	byte bytes[];

///////////////////////////////////////////////////////
	
	public FCBoardBytes() {
		bytes = new byte[ARRAY_SIZE];
	}
	
	public FCBoardBytes(byte bytes[]) {
		setBytes(bytes);
	}
	
	@Override
	public int compareTo(FCBoardBytes o) {
		// compare dest cell first
		int i;
		for (i=0; i<PlayingCard.SUITS; i++) {
			if (bytes[i] < o.bytes[i])
				return -1;
			else if (bytes[i] > o.bytes[i])
				return 1;
		}
		int idx=PlayingCard.SUITS;
		for (i=0; i<FreecellBoard.numberOfFreeCells; i++) {
			if (bytes[idx] < o.bytes[idx])
				return -1;
			else if (bytes[idx] > o.bytes[idx])
				return 1;
			idx++;
		}
		
		for (i=0; i<FreecellBoard.numberOfColumns; i++) {
			while (bytes[idx] != 0 && o.bytes[idx] != 0) {
				if (bytes[idx] > o.bytes[idx])
					return 1;
				else if (bytes[idx] < o.bytes[idx])
					return -1;

				idx++;
			}
			// at least one of them is 0
			if (bytes[idx] != 0 )
				return 1;
			else if (o.bytes[idx] != 0)
				return -1;
			// both are 0, compare next column
			idx++;
		}
		return 0;
	}
	
	// return an int that represents the dest(home) cells
	public int getDestCellsId() {
//		if (PlayingCard.SUITS > 4) {
//			throw new Error("getDestCellsId() only works for deck that has 4 or less suits.");
//		}
		int id = 0;
		for (int i=0; i<PlayingCard.SUITS; i++) {
			id *=14;
			id += PlayingCard.getRank(bytes[i]);
		}
		return id;
	}
	
	@Override
	public String toString() {
		String str="";
		str = new FreecellBoard(getBytes()).toString();
		return str;
	}

	public byte [] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte bytes[]) {
		this.bytes=bytes;
	}

}
