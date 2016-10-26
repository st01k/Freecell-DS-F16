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
import java.nio.ByteBuffer;

/**
 * information about the board
 * this is a complete record of a board, including various information, in addition to the actual board
 * 
 */
public class FCBoardInfo implements Serializable {

	private static final long serialVersionUID = -2830109618095051804L;
	
	int previous = -1;
	int next = -1;
	int moves = -1;
//	boolean tried = false;
	int move = 0;
	
	FCBoardBytes boardBytes;
	
	public static final int TO_BYTE_LENGTH = 16 + FCBoardBytes.ARRAY_SIZE;
	
///////////////////////////////////////////////////
	
	public FCBoardInfo() {
	}
	
	// from bytes
	public FCBoardInfo(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		previous = bb.getInt();
		next = bb.getInt();
		moves = bb.getInt();
//		tried = bb.get() == 0 ? false : true;
		move = bb.getInt();
		boardBytes = new FCBoardBytes();
		bb.get(boardBytes.getBytes());
	}
	
	public byte[] toBytes() {
		ByteBuffer bb = ByteBuffer.allocate(TO_BYTE_LENGTH);
		bb.putInt(previous);
		bb.putInt(next);
		bb.putInt(moves);
//		bb.put( (byte) (tried==true?1:0) );
		bb.putInt(move);
		bb.put(boardBytes.getBytes());
		
		return bb.array();
	}

	// make the next node with some default values
	public FCBoardInfo MakeNext(int currentBoardId) {
		FCBoardInfo nextInfo = new FCBoardInfo();
		nextInfo.previous=currentBoardId;
		nextInfo.moves=moves+1;
		return nextInfo;
	}

	// make the next node by copying some value from previous one
	public void MakeNext(FCBoardInfo previousInfo, int previousBoardId) {
		previous = previousBoardId;
		moves = previousInfo.moves + 1;
	}

//////////////////////////////////////////
	
	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getPrevious() {
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public FCBoardBytes getBoardBytes() {
		return boardBytes;
	}

	public void setBoardBytes(FCBoardBytes boardBytes) {
		this.boardBytes = boardBytes;
	}

}
