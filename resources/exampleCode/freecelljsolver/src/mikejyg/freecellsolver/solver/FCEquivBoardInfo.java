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
 * a complete record of an equivalent board
 * it is similar to FCBoardInfo, but for equivalent board. 
 */
package mikejyg.freecellsolver.solver;

import java.nio.ByteBuffer;

public class FCEquivBoardInfo {
	FCBoardBytes equivBoard;
//	boolean tried;

	public static final int TO_BYTES_LENGTH = FCBoardBytes.ARRAY_SIZE;
	
////////////////////////////////////////////////////////////
	
	public FCEquivBoardInfo(FCBoardBytes equivBoard) {
		this.equivBoard = equivBoard;
//		this.tried = tried;
	}
	
	public FCEquivBoardInfo(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		equivBoard = new FCBoardBytes();
		bb.get(equivBoard.getBytes());
//		tried = bb.get() == 0 ? false : true;
	}
	
	public byte[] toBytes() {
		ByteBuffer bb = ByteBuffer.allocate(TO_BYTES_LENGTH);
		bb.put(equivBoard.getBytes());
//		bb.put((byte) (tried ? 1 : 0));
		return bb.array();
	}
	
	public FCBoardBytes getEquivBoard() {
		return equivBoard;
	}
	public void setEquivBoard(FCBoardBytes equivBoard) {
		this.equivBoard = equivBoard;
	}

//	public boolean isTried() {
//		return tried;
//	}

//	public void setTried(boolean tried) {
//		this.tried = tried;
//	}

}
