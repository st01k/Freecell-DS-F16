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

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * the monolithic boards data structure
 */
public class FCBoards implements FCBoardsIntf {
	// the array that holds the boards, the index is the ID
	ArrayList<FCBoardInfo> boards;

	// for reverse lookup of board
//	Map<FCBoardBytes, Integer> boardLookup;
	
	// for equivalent boards
	ArrayList<FCEquivBoardInfo> equivBoards;
	
	Map<FCBoardBytes, Integer> equivBoardLookup; 
	
	public FCBoards() {
		boards = new ArrayList<FCBoardInfo>();
//		boardLookup = new TreeMap<FCBoardBytes, Integer>();
		equivBoards = new ArrayList<FCEquivBoardInfo>();
		equivBoardLookup = new TreeMap<FCBoardBytes, Integer>();
	}
	
	// if previousBoard is -1, this should be the first board 
	// return the ID of the board added
	@Override
	public int addBoardInfo(FCBoardInfo boardInfo) {
		boards.add(boardInfo);
		return boards.size() - 1;
	}

	@Override
	public FCBoardInfo getBoardInfo(int boardId) {
		return boards.get(boardId);
	}

	@Override
	public void updateBoardInfo(int boardId, FCBoardInfo boardInfo) {
		// not needed, if the caller always modifies the reference returned by get
//		boards.set(boardId, boardInfo);
	}

	@Override
	public int size() {
		return boards.size();
	}

//	@Override
//	public Integer lookUpId(FCBoardBytes board) {
//		return boardLookup.get(board);
//	}

	@Override
	public int addEquivBoardInfo(FCEquivBoardInfo boardInfo) {
		int id = equivBoards.size();
		equivBoards.add(boardInfo);
		equivBoardLookup.put(boardInfo.getEquivBoard(), id);
		return id;
	}

	@Override
	public FCEquivBoardInfo getEquivBoardInfo(int boardId) {
		return equivBoards.get(boardId);
	}

	@Override
	public Integer lookupEquivBoard(FCBoardBytes board) {
		return equivBoardLookup.get(board);
	}

	@Override
	public void updateEquivBoardInfo(int equivBoardId, FCEquivBoardInfo info) {
		// not needed, if the caller always modifies the reference returned by get
//		equivBoards.set(equivBoardId, info);
	}

	@Override
	public int equivSize() {
		return equivBoards.size();
	}

}
