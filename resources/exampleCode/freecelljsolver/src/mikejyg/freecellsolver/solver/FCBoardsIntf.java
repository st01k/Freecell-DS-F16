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
 * this interface handles the storage, query of records of freecell boards and equivalent boards 
 */
public interface FCBoardsIntf {

	/////////////////////////////////////////
	// board records
	
	/**
	 * return ID of the newly added record
	 * @param boardInfo
	 * @return
	 */
	public int addBoardInfo(FCBoardInfo boardInfo);
	
	public FCBoardInfo getBoardInfo(int boardId);

	public void updateBoardInfo(int boardId, FCBoardInfo boardInfo);
	
	/**
	 * return size of the boardInfo
	 */
	public int size();

//	public Integer lookUpId(FCBoardBytes board);

	////////////////////////////////////
	// equivalent board records
	
	public int addEquivBoardInfo(FCEquivBoardInfo boardInfo);
	
	public FCEquivBoardInfo getEquivBoardInfo(int boardId);

	public int equivSize();
	
	/**
	 * return ID of the equivalent board, null if not found
	 */
	public Integer lookupEquivBoard(FCBoardBytes board);

	public void updateEquivBoardInfo(int equivBoardId, FCEquivBoardInfo info);

}
