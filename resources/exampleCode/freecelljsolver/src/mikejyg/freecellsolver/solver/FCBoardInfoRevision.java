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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * this variation of FCBoardInfo contains revision information of a board,
 * to facilitate restrict unnecessary moves
 */
public class FCBoardInfoRevision extends FCBoardInfo {

	private static final long serialVersionUID = 5165076381152392155L;

	// revision of components
	// 4 for home cells; 1 for each free cells and 1 for each column
	public static final int REVISIONS_ARRAY_LENGTH = PlayingCard.SUITS + 
		FreecellBoard.numberOfFreeCells + FreecellBoard.numberOfColumns;
	byte revisions[];
	// if revision is negative, then it was a destination.
	// only when moving from a previous destination, revision compare will be carried out.
	
	// this one is higher then all existing revisions 
	static final byte newRevisionNumber = (byte) (REVISIONS_ARRAY_LENGTH + 1);
	
	public static final int TO_BYTE_LENGTH = 16 + FCBoardBytes.ARRAY_SIZE + REVISIONS_ARRAY_LENGTH;
	
///////////////////////////////////////////////////
	
	public FCBoardInfoRevision() {
		revisions = new byte[REVISIONS_ARRAY_LENGTH];
		Arrays.fill(revisions, (byte)0);
	}
	
	// this constructor does not init revisions
	public FCBoardInfoRevision(boolean initial) {
		if (initial)
			throw new Error("wrong constructor called");
	}

	// from bytes
	public FCBoardInfoRevision(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		previous = bb.getInt();
		next = bb.getInt();
		moves = bb.getInt();
//		tried = bb.get() == 0 ? false : true;
		move = bb.getInt();
		boardBytes = new FCBoardBytes();
		bb.get(boardBytes.getBytes());
		
		revisions = new byte[REVISIONS_ARRAY_LENGTH];
		bb.get(revisions);
	}
	
	@Override
	public byte[] toBytes() {
		ByteBuffer bb = ByteBuffer.allocate(TO_BYTE_LENGTH);
		bb.putInt(previous);
		bb.putInt(next);
		bb.putInt(moves);
//		bb.put( (byte) (tried==true?1:0) );
		bb.putInt(move);
		bb.put(boardBytes.getBytes());
		
		bb.put(revisions);
		
		return bb.array();
	}

	// make the next node with some default values
	@Override
	public FCBoardInfoRevision MakeNext(int currentBoardId) {
		FCBoardInfoRevision nextInfo = new FCBoardInfoRevision(false);
		nextInfo.previous=currentBoardId;
		nextInfo.moves=moves+1;
		nextInfo.revisions = Arrays.copyOf(revisions, REVISIONS_ARRAY_LENGTH);
		return nextInfo;
	}

	// make the next node by copying some value from previous one
	@Override
	public void MakeNext(FCBoardInfo previousInfo, int previousBoardId) {
		previous = previousBoardId;
		moves = previousInfo.moves + 1;
		for (int i = 0; i<REVISIONS_ARRAY_LENGTH; i++)
			revisions[i] = ((FCBoardInfoRevision) previousInfo).revisions[i];
	}

	public void updateHomeCellRev(byte suit, boolean dest) {
		if (dest)
			revisions[suit] = (byte) - newRevisionNumber;
		else
			revisions[suit] = newRevisionNumber;
	}
	
	public int getHomeCellRev(byte suit) {
		return revisions[suit];
	}
	
	public void updateFreeCellRev(int col, boolean dest) {
		if (dest)
			revisions[PlayingCard.SUITS + col] = (byte) - newRevisionNumber;
		else
			revisions[PlayingCard.SUITS + col] = newRevisionNumber;
	}
	
	public int getFreeCellRev(int col) {
		return revisions[PlayingCard.SUITS + col];
	}
	
	public void updateColumnRev(int col, boolean dest) {
		if (dest)
			revisions[PlayingCard.SUITS + FreecellBoard.numberOfFreeCells + col] = (byte) - newRevisionNumber;
		else
			revisions[PlayingCard.SUITS + FreecellBoard.numberOfFreeCells + col] = newRevisionNumber;
	}
	
	public int getColumnRev(int col) {
		return revisions[PlayingCard.SUITS+ FreecellBoard.numberOfFreeCells + col];
	}
	
	// the relative revision values are our concern, 
	// so compact the revision values from 1 to REVISIONS_ARRAY_LENGTH;
	public void normalizeRevisions() {
		SortedMap<Integer, Integer> compactMap = new TreeMap<Integer, Integer>();
		int i;
		
		// sort it
		for (i=0; i<REVISIONS_ARRAY_LENGTH; i++) {
			compactMap.put( Math.abs(revisions[i]), 0);
		}
		
		// associate with 1...REVISIONS_ARRAY_LENGTH-1
		i=1;
		Iterator<Integer> it = compactMap.keySet().iterator();
		while (it.hasNext()) {
			compactMap.put(it.next(), i++);
		}
		
		// update revisions
		for (i=0; i<REVISIONS_ARRAY_LENGTH; i++) {
			if (revisions[i] < 0)
				revisions[i] =  (byte) - compactMap.get(- revisions[i]);
			else 
				revisions[i] = (byte) compactMap.get((int)revisions[i]).intValue();
		}
		
		return;
	}
	
//////////////////////////////////////////
	

}
