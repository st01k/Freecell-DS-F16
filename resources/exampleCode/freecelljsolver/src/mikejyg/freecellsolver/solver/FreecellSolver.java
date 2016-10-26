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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * the freecell solver
 * 
 */
public class FreecellSolver implements FCSolverIntf
{
	public static final String VERSION_STRING="1.3.0";
	
	////////////////////
	// output settings

	// print out boards along with moves
	static boolean printBoards = false;

	///////////////////
	// debug settings 
	
	// trace the moves as it tries to solve
	static boolean traceMoves = false;
	
	static boolean printFailed = false;
	
	// whether to do sanity check
	static boolean sanityCheck = false;

	////////////////////////
	// optional game rules
	
	// this is not enabled in MS freecell
	boolean allowMoveFromHomeCell = false;
	
	/////////////////////
	// search settings
	
	// use meta move from FreecellSolver solver
	boolean fcsMetaMove = true;
	
	boolean homecellOptimization = true;

	// a board revision system to restrict unnecessary moves
	boolean useBoardRevisions = false;
	
	// distance between the card and the corresponding home cell
	// that would not be converted to color only
	// see toEquivalent()
	int nonEquivalentThreshold = 1;	// default
	
	public static final int HEURISTIC_AGGRESSIVE = 0;
	public static final int HEURISTIC_CAUTIOUS = 1;	// try to keep free cells empty
	public static final int HEURISTIC_RANDOM = 2;
	
	public static final String heuristicStrings[] = {
		"AGGRESSIVE", "CAUTIOUS", "RANDOM"
	};
	
	int heuristic = HEURISTIC_CAUTIOUS;		// default
	
	/////////////////
	// for back end
	
	public static final int BACKEND_MONOLITHIC = 0;

	public static final int BACKEND_BERKELEY_DB = 1;
	
	int backEnd = BACKEND_MONOLITHIC;	// default

	public static final String backEndStrings[] = {"MONOLITHIC", "BERKELEY_DB"};
	
	String propertiesFileName = "FreecellSolver.properties";
	
	///////////////////////
	// limit memory usage
	boolean limitBoards = true;
	
	int maxBoardsSize = 1500000;	// a good number for 512M heap 
	
	///////////////////////
	// mail box variables
	
	// for other thread to stop this thread
	boolean stop = false;		
	
	//////////////
	// variables
	
	FCBoardsIntf fcBoards;
	
	DFSTreeIntf dfsTree;
	
	// statistics
	int duplicateCandidateCnt;
	int failedCnt;
	int triedCnt;
	int candidateCnt;
	int maxDepth;
	int boardCnt;
	
	int solverResult; 

	int solvedMove;
	
	// working variables
	
	int tryMoveResult;
	
	int startBoardId;
	
	FCBoardInfo startBoardInfo;
	FreecellBoard startBoard;
	FreecellBoard nextBoard;
	FCBoardInfo nextBoardInfo;
	FCBoardBytes nextBoardBytes;
	FreecellBoard eqBoard;
	FCBoardBytes eqBoardBytes;
	int move;
	
	//////////////////
	// internal use 
	
	// try routine return codes
	static final int TC_NO_MOVE = 3;
	static final int TC_ADDED = 4;

	int lastBoardId;
	boolean forwardLinked;
	
	boolean dbCreated = false;

////////////////////////////
// exceptions
	
	public static class ParseException extends Exception {
		private static final long serialVersionUID = -7653369573404918430L;
		public ParseException(String str) {
			super(str);
		}
	}
	
	/**
	 * not really an exception, just to simply program control
	 */
	static class SolvedException extends Exception {
		private static final long serialVersionUID = 373630016414410577L;
	}

	public static class StoppedException extends Exception {
		private static final long serialVersionUID = 5873811787042206526L;
	}
	
	public static class BoardsLimitExceededException extends Exception {
		private static final long serialVersionUID = 7710117468202721779L;
	}
/////////////////////////////////////////////////////////////////	
	
	public FreecellSolver() {
	}
	
	/**
	 * copy constructor
	 */
	public FreecellSolver(FreecellSolver fcs) {
		if (fcs.backEnd != BACKEND_MONOLITHIC) {
			throw new Error("can not copy FreecellSolver of non-monolithic back end");
		}

		allowMoveFromHomeCell = fcs.allowMoveFromHomeCell;
		fcsMetaMove = fcs.fcsMetaMove;
		homecellOptimization = fcs.homecellOptimization;
		nonEquivalentThreshold = fcs.nonEquivalentThreshold;
		heuristic = fcs.heuristic;
		backEnd = fcs.backEnd;		
		limitBoards = fcs.limitBoards;
		maxBoardsSize = fcs.maxBoardsSize;
		useBoardRevisions = fcs.useBoardRevisions;
	}	

	public void clear() {
		if (backEnd==BACKEND_BERKELEY_DB)
			closeBackEnd();
		fcBoards = null;
		dfsTree = null;
	}
	
	@Override
	public int solve(FreecellBoard hand) {
		try {
			solveInternal(hand);
		} catch (StoppedException e) {
			solverResult = STOPPED;
		} catch (BoardsLimitExceededException e) {
			solverResult = BOARDS_LIMIT_EXCEEDED;
		}
		return solverResult;
	}
	
	/**
	 * possible outcome of the method
	 * return true - solved, false - unsolvable
	 * throw StoppedException - was stopped before a result is found
	 * throw BoardsLimitExceeded - limit of number of boards exceeded
	 * throw OutOfMemoryError - out of memory
	 * @param hand
	 * @return
	 * @throws StoppedException
	 * @throws BoardsLimitExceededException 
	 */
	public boolean solveInternal(FreecellBoard hand) throws StoppedException, BoardsLimitExceededException {
		// for testing purposes
//		storeProperties();
//		loadProperties();
		
		// init data structures
		
		solverResult = ERROR;	// if it is not set correctly, it is an error 
				
		switch(backEnd) {
			case BACKEND_MONOLITHIC:
				fcBoards = new FCBoards();
				break;
			case BACKEND_BERKELEY_DB:
				if (dbCreated) {
						((FCBoardsBDb) fcBoards).closeDB();
						((FCBoardsBDb) fcBoards).closeEnv();
				}
				fcBoards = new FCBoardsBDb(useBoardRevisions);
				dbCreated = true;
				break;
			default:
				throw new Error("unknown back end : " + backEnd);
		}
		
		if (heuristic == HEURISTIC_RANDOM)
			dfsTree = new DFSTreeRand();
		else
			dfsTree = new DFSTree();
		
		nextBoard = new FreecellBoard();
		
		if (useBoardRevisions)
			nextBoardInfo = new FCBoardInfoRevision();
		else
			nextBoardInfo = new FCBoardInfo();
		
		nextBoardBytes = new FCBoardBytes();
		eqBoard = new FreecellBoard();
		eqBoardBytes = new FCBoardBytes();
		
		// init variables
		candidateCnt = 0;
		duplicateCandidateCnt = 0;
		failedCnt = 0;
		triedCnt = 0;
		maxDepth = 0;
		move = 0;
		boardCnt = 0;
		solvedMove = -1;
		
		forwardLinked = false;
		
		// add hand to candidates
		startBoard = new FreecellBoard(hand);
		if (useBoardRevisions)
			startBoardInfo = new FCBoardInfoRevision();
		else
			startBoardInfo = new FCBoardInfo();
		
		nextBoard.copy(startBoard);
		startBoardId=-1;
		if (useBoardRevisions) 
			nextBoardInfo.MakeNext((FCBoardInfoRevision)startBoardInfo, startBoardId);
		else
			nextBoardInfo.MakeNext(startBoardInfo, startBoardId);

		try {
			addCandidate();
		} catch (SolvedException e) {
			return true;
		}
		
		// the main loop
		while (dfsTree.getDepth() >= 0) {
			
			Integer k = dfsTree.popBestNode();
			if (k==null) {
				dfsTree.decDepth();
				continue;
			}

			startBoardId = k; 

			if (dfsTree.getDepth()>maxDepth)
				maxDepth = dfsTree.getDepth();
			
			candidateCnt--;
			triedCnt++;

			if (traceMoves) {
				System.out.println("move : " + dfsTree.getDepth());
				System.out.println(getBoard(startBoardId).toString());
			}

			dfsTree.incDepth();

			// get and add new candidates
			try {
				populateDepth();

				if (tryMoveResult == TC_NO_MOVE) {
					failedCnt++;
					if (isPrintFailed()) {
						System.out.println("failed board :");
						System.out.println(getBoard(startBoardId).toString());
					}
					dfsTree.decDepth();
				}				
			} catch (SolvedException e1) {
				return true;
			}
		}
		// no more candidates, failed
		solverResult = UNSOLVABLE;
		return false;
	}

	private void closeBackEnd() {
		if (backEnd == BACKEND_BERKELEY_DB) {
			((FCBoardsBDb) fcBoards).closeDB();
			((FCBoardsBDb) fcBoards).closeEnv();
			dbCreated = false;
		}
	}

	/**
	 *  populate the current depth of the search tree with candidates
	 * @throws SolvedException
	 * @throws StoppedException 
	 * @throws BoardsLimitExceededException 
	 */
	public void populateDepth() throws SolvedException, StoppedException, BoardsLimitExceededException {
		startBoardInfo = fcBoards.getBoardInfo(startBoardId);
		startBoard.set(startBoardInfo.getBoardBytes().getBytes());

		tryMoveResult = TC_NO_MOVE;
		
		// make sure no non-mustMove candidate is added before a must move
		tryColumnToHome(true);
		if (tryMoveResult == TC_ADDED)
			return;

		tryFreeToHome(true);
		if (tryMoveResult==TC_ADDED)
			return;
		
		tryColumnToHome(false);
		
		tryFreeToHome(false);

		tryColumnToEmptyColumn();

		tryColumnToNoneEmptyColumn();
		
		tryColumnToFree();

		tryFreeToColumn();

		if (allowMoveFromHomeCell) {
			tryHomeToColumn();
		}
		
		// optimization? never put home cell back to free cell
		if ( allowMoveFromHomeCell && !homecellOptimization ) {
			tryHomeToFree();
		}
		
		if (fcsMetaMove) {
			tryColumnToHomeMeta();
		}
	}

	private FCBoardBytes getBoard(int boardId) {
		return fcBoards.getBoardInfo(boardId).getBoardBytes();
	}

	/**
	 * add a candidate to the current level of the tree
	 * @throws SolvedException
	 * @throws StoppedException 
	 * @throws BoardsLimitExceededException 
	 */
	void addCandidate() throws SolvedException, StoppedException, BoardsLimitExceededException {
		if (isStop()) {
			throw new StoppedException();
		}
		
//		nextBoard.normalize();
//		System.out.println(nextBoard.toString());
		nextBoard.toBytes(nextBoardBytes.getBytes());

		// verify to bytes and from bytes works correctly
		if (sanityCheck) {
			FreecellBoard verify = new FreecellBoard(nextBoardBytes.getBytes());
			if ( nextBoard.compareTo(verify) != 0)
				throw new Error("serdes mismatch");
		}

		nextBoardInfo.setBoardBytes(nextBoardBytes);
		nextBoardInfo.setMove(move);
		
		if (nextBoard.isDone()) {
			// solved
			
			lastBoardId = addBoard();
			
			solvedMove =  fcBoards.getBoardInfo(lastBoardId).getMoves();
//			System.out.println("solved at move : " + solvedMove);
			
			solverResult = SOLVED;
			throw new SolvedException();
		}

		// verify the board is correct
		if (sanityCheck) {
			try {
				nextBoard.sanityCheck();
			} catch (FreecellBoard.IllegalBoardException e) {
				System.out.println("addCandidate() - added - moves : " + fcBoards.getBoardInfo(startBoardId).getMoves());
				System.out.println(getBoard(startBoardId).toString());
				throw new Error("FreecellBoard.IllegalBoardException : " + e.getMessage());
			}
		}
		
		eqBoard.copy(nextBoard);
		eqBoard.toEquivalent(nonEquivalentThreshold);
		eqBoard.normalize();
//		System.out.println(eqBoard.toString());

		eqBoard.toBytes(eqBoardBytes.getBytes());
		
//		System.out.println("eq board : " + eqBoardBytes.toString());
		
		if (fcBoards.lookupEquivBoard(eqBoardBytes) != null) {
//			System.out.println("duplicate");
			duplicateCandidateCnt++;
			return;
		}
		
		// does not exist
			
		// add to equivalent boards
		fcBoards.addEquivBoardInfo(new FCEquivBoardInfo(eqBoardBytes));
		eqBoardBytes = new FCBoardBytes();

		Integer boardId = addBoard();
//		System.out.println("board added : " + boardId.toString() + " size : " + fcBoards.size());
				
		candidateCnt++;
		
		// do not limit size for other back end
		if (limitBoards && backEnd == BACKEND_MONOLITHIC) {
			if (fcBoards.size() > maxBoardsSize) {
				throw new BoardsLimitExceededException();
			}
		}
		
		double score = 0;
		if (heuristic != HEURISTIC_RANDOM)
			score = getScore(nextBoard);
		
//		System.out.println(info.getBoard().toString());
//		System.out.println("score : " + score);
		
		dfsTree.addNode(boardId, score);

		tryMoveResult = TC_ADDED;
	}

	private int addBoard() {
		if (useBoardRevisions)
			((FCBoardInfoRevision) nextBoardInfo).normalizeRevisions();
		
		int k = fcBoards.addBoardInfo(nextBoardInfo);
		nextBoardBytes = new FCBoardBytes();
		
		if (useBoardRevisions)
			nextBoardInfo = new FCBoardInfoRevision();
		else
			nextBoardInfo = new FCBoardInfo();
		
		boardCnt++;
		
		return k; 
	}

///////////////////////////////////////////////////////
// the moves
	
	// try moving from free cell to home cell
	// returns : 1 - solved, 2 - a must move
	void tryFreeToHome(boolean mustMove) throws SolvedException, StoppedException, BoardsLimitExceededException {
		for (int col=0; col<FreecellBoard.numberOfFreeCells; col++)
		{
			byte card = startBoard.freeCells[col];
			if ( card == PlayingCard.NULL_CARD)
				continue;

			byte suit = startBoard.isMovableToHome(card);
			
			if ( suit == -1 ) 
				continue;

			// check revision eligibility
			if (useBoardRevisions) {
				int srcRev = ((FCBoardInfoRevision) startBoardInfo) .getFreeCellRev(col);
				if (srcRev < 0) {
					if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getHomeCellRev(suit) ) <= - srcRev )
						continue;
				}
			}
			
			move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_FREE, col, FCMove.LOCATION_TYPE_HOME, suit);
			
			getNextBoard();

			nextBoard.homeCells[suit] = nextBoard.freeCells[col];
			nextBoard.freeCells[col] = PlayingCard.NULL_CARD;

			// update revision
			if (useBoardRevisions) {
				((FCBoardInfoRevision) nextBoardInfo).updateFreeCellRev(col, false);
				((FCBoardInfoRevision) nextBoardInfo).updateHomeCellRev(suit, true);
			}
			
			if (mustMove) {
				if (nextBoard.isGoodHomeCell(suit)) {
					addCandidate();
					tryMoveResult = TC_ADDED;	// terminate the current level if a must move is tried
					return;
				}
			} else {
				addCandidate();
			}
		}
	}

	private void getNextBoard() {
		nextBoard.copy(startBoard);
		if (useBoardRevisions) 
			nextBoardInfo.MakeNext(startBoardInfo, startBoardId);
		else
			nextBoardInfo.MakeNext(startBoardInfo, startBoardId);
	}

	// try moving from column cell to home cell
	void tryColumnToHome(boolean mustMove) throws SolvedException, StoppedException, BoardsLimitExceededException {
		for (int col=0; col<FreecellBoard.numberOfColumns; col++)
		{
			FreecellBoard.Column column = startBoard.columns[col];
			if (column.size()==0)	// no card in the column
				continue;

			byte suit = startBoard.isMovableToHome(column.getLast());
			
			if ( suit == -1 )
				continue;

			// check revision eligibility
			if (useBoardRevisions) {
				int srcRev = ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col);
				if (srcRev < 0) {
					if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getHomeCellRev(suit) ) <= - srcRev )
						continue;
				}
			}
			
			move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_COLUMN, col, FCMove.LOCATION_TYPE_HOME, suit);
			
			getNextBoard();
			nextBoard.homeCells[suit] = nextBoard.popColumn(col);

			// update revision
			if (useBoardRevisions) {
				((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, false);
				((FCBoardInfoRevision) nextBoardInfo).updateHomeCellRev(suit, true);
			}
			
			if (mustMove) {
				if (nextBoard.isGoodHomeCell(suit)) {
					addCandidate();
					tryMoveResult = TC_ADDED;	// terminate the current level if a must move is tried
					return;
				}
			} else {
				addCandidate();
			}
		} 
	}	

	// try moving from one column to another none empty column, may move multiple cards
	void tryColumnToNoneEmptyColumn() throws SolvedException, StoppedException, BoardsLimitExceededException {
		int maxSequenceLen = startBoard.getNumberOfEmptyFreeCells() + 1;
		maxSequenceLen += maxSequenceLen * startBoard.getNumberOfEmptyColumns();
		
		for (int col=0; col<FreecellBoard.numberOfColumns; col++)
		{
			FreecellBoard.Column column = startBoard.columns[col];
			int srcSize = column.size();
			if (srcSize== 0)
				continue;

			byte card = column.getLast();
			byte rank = PlayingCard.getRank(card);
			byte color = PlayingCard.getColor(card);
			
			int srcSeqLen = startBoard.getSequenceLength(col);
			
			// try to move to another column cell
			for (int dCol=0; dCol<FreecellBoard.numberOfColumns; dCol++)
			{
				if (dCol==col)
					continue;

				if (startBoard.columns[dCol].size()==0)
					continue;

				byte destCard = startBoard.columns[dCol].getLast();
				
				byte destRank=PlayingCard.getRank(destCard);

				if (destRank<=rank)
					continue;

				int sequenceLen=destRank-rank;		// the target length
				if (sequenceLen>maxSequenceLen)			// out of range
					continue;

				if (sequenceLen>srcSeqLen)
					continue;

				byte destColor=PlayingCard.getColor(destCard);
				// check color
				if ( (sequenceLen & 0x01) != 0) {
					// odd cards, opposite color
					if ( color == destColor)
						continue;
				}
				else {
					if ( color != destColor)
						continue;
				}

				// check revision eligibility
				if (useBoardRevisions) {
					int srcRev = ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col);
					if (srcRev < 0) {
						if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getColumnRev(dCol) ) <= - srcRev )
							continue;
					}
				}
				
				move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, sequenceLen, FCMove.LOCATION_TYPE_COLUMN, col, 
						FCMove.LOCATION_TYPE_COLUMN, dCol);
					
				
				getNextBoard();
				nextBoard.moveColumnToColumn(col, dCol, sequenceLen);

				// update revision
				if (useBoardRevisions) {
					((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, false);
					((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(dCol, true);
				}
				
				addCandidate();
			}
		}
	}

	// try moving one card from column cell to another empty column cell
	void tryColumnToEmptyColumn() throws SolvedException, StoppedException, BoardsLimitExceededException {
		int destCol = startBoard.getEmptyColumn();
		if (destCol == -1)
			return;

		int maxSequenceLen = startBoard.getNumberOfEmptyFreeCells() + 1;
		maxSequenceLen *= startBoard.getNumberOfEmptyColumns();

		for (int col=0; col<FreecellBoard.numberOfColumns; col++)
		{
			if (col == destCol)
				continue;
			
			int srcSize = startBoard.columns[col].size();
			if (srcSize== 0 || srcSize == 1)
				continue;

			int seqLen = startBoard.getSequenceLength(col);
			if (seqLen > maxSequenceLen)
				seqLen = maxSequenceLen;

			// check revision eligibility
			if (useBoardRevisions) {
				int srcRev = ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col);
				if (srcRev < 0) {
					if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getColumnRev(destCol) ) <= - srcRev )
						continue;
				}
			}
			
			// always move maximum number of cards
			// note, that together with useBoardRevisions, this effectively prevents the move of one card to an empty column, 
			// if more cards can be moved, since moving to free cell and then to an empty column is restricted by the revision system.
			// it seems (tried the first 1 million deals, also seconded by Michael Keller on his FAQ, that this restriction does not cause a solvable board to be unsolvable.
			move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, seqLen, FCMove.LOCATION_TYPE_COLUMN, col, 
					FCMove.LOCATION_TYPE_COLUMN, destCol);

			getNextBoard();
			nextBoard.moveColumnToColumn(col, destCol, seqLen);
			
			// update revision
			if (useBoardRevisions) {
				((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, false);
				((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(destCol, true);
			}
		
			addCandidate();
		}
	}
	
	// try moving from column cell to free cell
	void tryColumnToFree() throws SolvedException, StoppedException, BoardsLimitExceededException {
		int freecellIdx = startBoard.getFreeFreecellIdx();

		if (freecellIdx == -1)
			return;

		for (int col=0; col<FreecellBoard.numberOfColumns; col++)
		{
			if (startBoard.columns[col].size()==0)	// no card in the column
				continue;

			// if the card can be moved to home, do not move it to a free cell
			if (homecellOptimization) {
				if ( startBoard.isMovableToHome( startBoard.columns[col].getLast() ) != -1)
					continue;
			}
			
			// check revision eligibility
			if (useBoardRevisions) {
				int srcRev = ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col);
				if (srcRev < 0) {
					if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getFreeCellRev(freecellIdx) ) <= - srcRev )
						continue;
				}
			}
			
			move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_COLUMN, col, 
					FCMove.LOCATION_TYPE_FREE, freecellIdx);
			getNextBoard();
			nextBoard.freeCells[freecellIdx] = nextBoard.popColumn(col);
			
			// update revision
			if (useBoardRevisions) {
				((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, false);
				((FCBoardInfoRevision) nextBoardInfo).updateFreeCellRev(freecellIdx, true);
			}
			
			addCandidate();
		}
	}

	// try moving from free cell to non-empty column cell
	void tryFreeToColumn() throws SolvedException, StoppedException, BoardsLimitExceededException {
		for (int col=0; col<FreecellBoard.numberOfFreeCells; col++)
		{
			if (startBoard.freeCells[col] == PlayingCard.NULL_CARD)
				continue;

			byte card = startBoard.freeCells[col];
			byte rank = PlayingCard.getRank(card);
			byte color = PlayingCard.getColor(card); 

			// only need to move to empty once
			boolean triedEmptyColumn = false;

			for (int iDCol=0; iDCol<FreecellBoard.numberOfColumns; iDCol++)
			{
				FreecellBoard.Column column = startBoard.columns[iDCol];
				if ( column.size() == 0 ) {
					if (triedEmptyColumn)
						continue;

					triedEmptyColumn = true;

				} else if ( PlayingCard.getRank( column.getLast() ) != rank + 1 ||
						PlayingCard.getColor( column.getLast() ) == color )
				{	
					continue;
				}
				
				// check revision eligibility
				if (useBoardRevisions) {
					int srcRev = ((FCBoardInfoRevision) startBoardInfo).getFreeCellRev(col);
					if (srcRev < 0) {
						if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getColumnRev(iDCol) ) <= - srcRev )
							continue;
					}
				}
				
				move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_FREE, col, 
						FCMove.LOCATION_TYPE_COLUMN, iDCol);

				getNextBoard();
				nextBoard.columns[iDCol].add(card);
				nextBoard.freeCells[col] = PlayingCard.NULL_CARD;
				
				// update revision
				if (useBoardRevisions) {
					((FCBoardInfoRevision) nextBoardInfo).updateFreeCellRev(col, false);
					((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(iDCol, true);
				}
				
				addCandidate();
			}				
		}
	} 

	void tryHomeToFree() throws SolvedException, StoppedException, BoardsLimitExceededException {
		int freeIdx = startBoard.getFreeFreecellIdx();
		if (freeIdx==-1)
			return;

		for (byte i=0; i<PlayingCard.SUITS; i++) {

			if ( startBoard.isGoodHomeCell(i) )
				continue;
			
			if ( startBoard.homeCells[i] != PlayingCard.NULL_CARD ) {
				
				// check revision eligibility
				if (useBoardRevisions) {
					int srcRev = ((FCBoardInfoRevision) startBoardInfo).getHomeCellRev(i);
					if (srcRev < 0) {
						if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getFreeCellRev(freeIdx) ) <= - srcRev )
							continue;
					}
				}
				
				move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_HOME, i, 
						FCMove.LOCATION_TYPE_FREE, freeIdx);

				getNextBoard();
				nextBoard.freeCells[freeIdx] = nextBoard.popHomeCell(i);

				// update revision
				if (useBoardRevisions) {
					((FCBoardInfoRevision) nextBoardInfo).updateHomeCellRev(i, false);
					((FCBoardInfoRevision) nextBoardInfo).updateFreeCellRev(freeIdx, true);
				}

				addCandidate();
			}
		}
	}

	void tryHomeToColumn() throws SolvedException, StoppedException, BoardsLimitExceededException {
		for (byte i=0; i<PlayingCard.SUITS; i++) {

			if ( startBoard.isGoodHomeCell(i))
				continue;
			
			byte card = startBoard.homeCells[i];
			if ( card == PlayingCard.NULL_CARD)
				continue;

			byte rank = PlayingCard.getRank(card);
			byte color = PlayingCard.getColor(card);

			boolean triedEmptyColumn = false;

			for (int col=0; col<FreecellBoard.numberOfColumns; col++) {
				if ( startBoard.columns[col].isEmpty() ) {
					// optimization - never move from home to an empty column
					if (homecellOptimization)
						continue;
					
					if (triedEmptyColumn)
						continue;

					triedEmptyColumn = true;

				} else {
					byte columnCard = startBoard.getColumnCard(col);
					if ( PlayingCard.getRank(columnCard) != rank + 1 ||
							PlayingCard.getColor(columnCard) == color )
						continue;
				}

				// check revision eligibility
				if (useBoardRevisions) {
					int srcRev = ((FCBoardInfoRevision) startBoardInfo).getHomeCellRev(i);
					if (srcRev < 0) {
						if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col) ) <= - srcRev )
							continue;
					}
				}
				
				move = FCMove.makeFCMove(FCMove.MOVE_TYPE_STANDARD, 1, FCMove.LOCATION_TYPE_HOME, i, 
						FCMove.LOCATION_TYPE_COLUMN, col);

				getNextBoard();
				nextBoard.columns[col].add( nextBoard.popHomeCell(i) );

				// update revision
				if (useBoardRevisions) {
					((FCBoardInfoRevision) nextBoardInfo).updateHomeCellRev(i, false);
					((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, true);
				}

				addCandidate();
			}
		}
	}

	void tryColumnToHomeMeta() throws SolvedException, StoppedException, BoardsLimitExceededException {
		int freeCells = startBoard.getNumberOfEmptyFreeCells();

		if (freeCells == 0)
			return;
		
		// try moving from column cell to home cell
		for (int col=0; col<FreecellBoard.numberOfColumns; col++)
		{
			FreecellBoard.Column column = startBoard.columns[col];

			if (column.size()<=1)
				continue;
			
			int moveToFreeCells = 1;
			int srcIdx = column.size() - 2;
			
			while (moveToFreeCells <= freeCells && srcIdx >= 0) {
			
				byte rank= PlayingCard.getRank( column.get(srcIdx) ) ;
				byte suit= PlayingCard.getSuit( column.get(srcIdx) );

				if ( PlayingCard.getRank( startBoard.homeCells[suit] ) == rank - 1 ) {

					// check revision eligibility
					// of the first card to move
					if (useBoardRevisions) {
						int srcRev = ((FCBoardInfoRevision) startBoardInfo).getColumnRev(col);
						if (srcRev < 0) {
							int freeCellIdx = startBoard.getFreeFreecellIdx();
							if ( Math.abs( ((FCBoardInfoRevision) startBoardInfo).getFreeCellRev(freeCellIdx) ) <= - srcRev ) {

								srcIdx--;
								moveToFreeCells++;

								continue;
							}
						}
					}
					
					move = FCMove.makeFCMove(FCMove.MOVE_TYPE_FCS_META, moveToFreeCells, FCMove.LOCATION_TYPE_COLUMN, col, 
							FCMove.LOCATION_TYPE_HOME, suit);

					getNextBoard();

					// move blocking cards
					for (int k=0; k<moveToFreeCells; k++) {
						int freeCellIdx = nextBoard.getFreeFreecellIdx();
						nextBoard.freeCells[freeCellIdx] = nextBoard.popColumn(col);
						
						// update revision
						// these are intermediate moves, not true destinations
						if (useBoardRevisions)
							((FCBoardInfoRevision) nextBoardInfo).updateFreeCellRev(freeCellIdx, false);
					}
					
					nextBoard.homeCells[suit] = nextBoard.popColumn(col);
					
					// update revision
					if (useBoardRevisions) {
						((FCBoardInfoRevision) nextBoardInfo).updateColumnRev(col, false);
						// only this one is the true destination
						((FCBoardInfoRevision) nextBoardInfo).updateHomeCellRev(suit, true);	
					}
					
					addCandidate();

					break;
				}
			
				srcIdx--;
				moveToFreeCells++;
			}
		}
	}

	// compute huristic score
	public double getScore(FreecellBoard board) {
		int i;
		
		// number of free cells
		int freeCellCnt = 0;
		if (heuristic == HEURISTIC_CAUTIOUS) {
			for (i=0; i<FreecellBoard.numberOfFreeCells; i++)
				if (board.freeCells[i]==PlayingCard.NULL_CARD)
					freeCellCnt++;
		}
		
		// home cell score
		int homeCellCnt = 0;
		for (i=0; i<PlayingCard.SUITS; i++)
		{
			homeCellCnt += PlayingCard.getRank(board.homeCells[i]);
		}

		FreecellBoard.Column column;
		int j;

		int inSequenceScore = 0;
		if ( heuristic == HEURISTIC_CAUTIOUS ) {
			for (i=0; i<FreecellBoard.numberOfColumns; i++)
			{
				column = board.columns[i];

				// bonus for good column cell
				if (board.isGoodColumnCells(i)) {
					inSequenceScore += column.size() * 2;
				}

				// should count just one card
				if (column.size() == 1) {
					inSequenceScore++;
					continue;
				}

				for (j=1; j<column.size(); j++)
				{
					if ( PlayingCard.getRank(column.get(j-1)) == ( PlayingCard.getRank(column.get(j)) + 1 ) &&
							PlayingCard.getColor(column.get(j-1)) != PlayingCard.getColor(column.get(j))) {
						inSequenceScore++;
					}
				}
			}
		}
		
		// blocking score
		double blockingScore = 0;
		
		byte lowestHomeRank = 13;
		int k;
		for (i=0; i<PlayingCard.SUITS; i++) {
			k = PlayingCard.getRank( board.homeCells[i] );
			if (k<lowestHomeRank)
				lowestHomeRank = (byte) k;
		}
		
		int freeColumnCell = 0;
		for (i=0; i<FreecellBoard.numberOfColumns; i++)
		{
			column = board.columns[i];
			if (column.size() == 0) {
				freeColumnCell++;
				continue;
			}
			for (j=column.size()-1; j>=0; j--)
			{
				byte cardRank = PlayingCard.getRank(column.get(j));
				// degree of concern
				double concern = cardRank - lowestHomeRank;
				concern += cardRank - PlayingCard.getRank(board.homeCells[PlayingCard.getSuit(column.get(j))]);
				concern /= 2;
				
				// 1 is highest concern, larger number is lower concern
				
//				// do not look further than 3 cards
//				if (concern > 3)
//					continue;
				
				// cards blocking it
				double blockings = 0;
				for (k = j+1; k<column.size(); k++) {
					if (PlayingCard.getRank(column.get(k)) >= cardRank)
						blockings += 1;
				}
				// alternative, faster but not as accurate method,
//				blockings = column.size()-1 - j;
				
				// give priority for cards that can be moved to home cell
				if (heuristic == HEURISTIC_CAUTIOUS) {
					if (concern == 1 && blockings <= 2)
						blockingScore += ( 9 - (blockings+1)*(blockings+1) ) * 4;

					concern = Math.pow(concern, 1.8);
				}
				
				blockings -= concern-1;
				
				if (blockings <0)
					continue;
				
				// if too much blocking, let's worry about it less (giving up for now)
				if (heuristic == HEURISTIC_CAUTIOUS)
					blockings = Math.pow(blockings, .85);
				
				blockingScore -= blockings * 13 / concern;
			}
		}
		
		// the higher the rank, the closer to the top, the better
//		int columnShapeScore = 0;
//		for (i=0; i<COLUMNS; i++) {
//			column = columns[i];
//			for (j=0; j<column.size(); j++)
//			{
//				// position value
//				int pv = 13 - j;
//				if (pv<=0)
//					continue;
//				columnShapeScore += pv * PlayingCard.getRank( column.get(j) );
//			}
//		}
		
		// rational behind the coefficients :
		// one blocking card of A is 13 points
		// one free cell is worth less than 13 - so that we want to move the card that blocks A to free cell
		// card sequence is enforced by rule, so it is not that important;
		// we should encourage moving of free cell to in sequence, so the numbers.
		double score;
		if (heuristic == HEURISTIC_CAUTIOUS) {
			score = 
				freeCellCnt * 26. + 
				homeCellCnt * 39. +			// only must moves to home cell is encouraged 
				blockingScore * 5. + 
				freeColumnCell * 13. + 
				inSequenceScore * 8.
//				+ columnShapeScore / 13 / 13 / 6
				;
		} else {
			score = 
				homeCellCnt * 39.		// only must moves to home cell is encouraged 
				+ blockingScore * 5. 
//				+ inSequenceScore * 4.
				;
		}
		
		return score;
	}
	
///////////////////////////////////////////////////////
	
	@Override
	public String toReport() {
		String str;

		str = "solver result : ";
		switch (solverResult) {
		case ERROR:
			str += "error";
			break;
		case SOLVED:
			str += "solved at move : " + solvedMove;
			break;
		case UNSOLVABLE:
			str += "unsolvable";
			break;
		case STOPPED:
			str += "stopped";
			break;
		case BOARDS_LIMIT_EXCEEDED:
			str += "limit of number of boards exceeded";
			break;
		default:
			throw new Error("unknown result : " + solverResult);
		}
		
		str += ", total boards : " + Integer.toString(boardCnt); 
		str += ", outstanding candidates : " + candidateCnt;
		str += ", tried candidates : " + triedCnt;
		str += ", max depth : " + maxDepth;
//		str += ", equivBoards size : " + fcBoards.equivSize();
//		str += ", failed candidates : " + failedCnt;
//		str += ", duplicate cnt: " + duplicateCandidateCnt;
		return str;
	}

	public void printResult() {
		if (!forwardLinked)
			makeForwardLinks();
		
		int boardId = 0;
		int prevBoardId = 0;
		int colPos = 0;
		do {
//			System.out.println();
			FCBoardInfo info = fcBoards.getBoardInfo(boardId);
			
			if (printBoards)
				System.out.print("move : " + info.getMoves() + " - ");
			
			if (boardId != 0) {
				for (String str : getStdString(prevBoardId, info.getMove()) ) {
					System.out.print( str + " " );
					colPos++;
					if (!printBoards && colPos == 10) {
						System.out.println();
						colPos = 0;
					}
				}
			}
			
			if (printBoards) {
				System.out.println();
				System.out.println(getBoard(boardId).toString());
				System.out.println();
			}
			
			prevBoardId = boardId;
			boardId = info.getNext();
		} while (boardId != -1);

		if (!printBoards)
			System.out.println();
	}
	
	private ArrayList<String> getStdString(int prevBoardId, int move) {
		if (FCMove.getMoveType(move) == FCMove.MOVE_TYPE_STANDARD) {
			ArrayList<String> strs = new ArrayList<String>();
			strs.add( FCMove.toStdString(move));
			return strs;
		}
		// expand meta move
		FreecellBoard board = new FreecellBoard( fcBoards.getBoardInfo(prevBoardId).getBoardBytes().getBytes() );
		return board.toStdString(move);
	}

	private void makeForwardLinks() {
		int boardId = lastBoardId;
		// traverse to beginning
		int previous = fcBoards.getBoardInfo(boardId).getPrevious();
		FCBoardInfo info;
		while (previous != -1) {
			info = fcBoards.getBoardInfo(previous);
			info.setNext(boardId);
			fcBoards.updateBoardInfo(previous, info);
			boardId = previous;
			previous = info.getPrevious();
		}
		// should end up at 0
		if (boardId != 0)
			throw new Error("makeForwardLinks error : end at : " + boardId);
		
		forwardLinked = true;
	}

	ArrayList<Integer> getMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		int boardId = fcBoards.getBoardInfo(0).getNext();
		do {
			FCBoardInfo info = fcBoards.getBoardInfo(boardId);
			moves.add(info.getMove());
			boardId = info.getNext();
		} while (boardId != -1);
		return moves;
	}
	
	public void printOptions() {
		System.out.println("equivalentBoardOptimization threshold : " + nonEquivalentThreshold);

		System.out.println("heuristic : " + heuristicStrings[heuristic]);
		
		System.out.println("back end : " + backEndStrings[backEnd]);
	}

	private int parseBackEnd(String property) throws ParseException {
		for (int i=0; i<backEndStrings.length; i++)
			if (property.equals(backEndStrings[i]))
					return i;
		throw new ParseException("unknown back end : " + property);
	}

	public static int parseHeuristic(String property) throws ParseException {
		for (int i=0; i<heuristicStrings.length; i++)
			if (property.equals(heuristicStrings[i]))
				return i;
		throw new ParseException("unknown huristic : " + property);
	}	

////////////////////////////////////////////////
// getters and setters
	
	public boolean isPrintFailed() {
		return printFailed;
	}

	public static void setPrintFailed(boolean printFailed) {
		FreecellSolver.printFailed = printFailed;
	}

	public int getNonEquivalentThreshold() {
		return nonEquivalentThreshold;
	}

	public void setNonEquivalentThreshold(int nonEquivalentThreshold) {
		this.nonEquivalentThreshold = nonEquivalentThreshold;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	public boolean isHomecellOptimization() {
		return homecellOptimization;
	}

	public void setHomecellOptimization(boolean homecellOptimization) {
		this.homecellOptimization = homecellOptimization;
	}
	
//////////////////////////////
// properties store and load
	
	public void storeProperties(String propertiesFileName) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		////////////////////
		// output settings

		// print out boards along with moves
//		static boolean printBoards = false;
		properties.setProperty("PRINT_BOARDS", Boolean.toString(printBoards));

		///////////////////
		// debug settings 
		
		// trace the moves as it tries to solve
//		boolean traceMoves = false;
		properties.setProperty("TRACE_MOVES", Boolean.toString(traceMoves));
		
//		boolean printFailed = false;
		properties.setProperty("PRINT_FAILED_BOARDSS", Boolean.toString(printFailed));
		
		// whether to do sanity check
//		boolean sanityCheck = false;
		properties.setProperty("SANITY_CHECK", Boolean.toString(sanityCheck));
		
		////////////////////////
		// optional game rules
		
		// this is not enabled in MS freecell
//		boolean allowMoveFromHomeCell = false;
		properties.setProperty("ALLOW_MOVE_FROM_HOME_CELL", Boolean.toString(allowMoveFromHomeCell));
		
		/////////////////////
		// search settings
		
		// use meta move from FreecellSolver solver
//		boolean fcsMetaMove = true;
		properties.setProperty("FCS_META_MOVE", Boolean.toString(fcsMetaMove));
		
//		boolean homecellOptimization = true;
		properties.setProperty("HOME_CELL_OPTIMIZATION", Boolean.toString(homecellOptimization));
		
//		boolean useBoardRevisions = true;
		properties.setProperty("USE_BOARD_REVISIONS", Boolean.toString(useBoardRevisions));
		
		// this does not work for 821933, for all deals 1-1000,000
		// unless the threadhold is set to 3 and up, see FreecellBoard
//		int nonEquivalentThreshold = 1;	// default
		properties.setProperty("NON-EQUIVALENT_THRESHOLD", Integer.toString(nonEquivalentThreshold));
		
//		int heuristic = FreecellBoard.HEURISTIC_CAUTIOUS;
		properties.setProperty("HEURISTIC", heuristicStrings[heuristic]);
		
		/////////////////
		// for back end
		
//		int backEnd = BACKEND_MONOLITHIC;	// default
		properties.setProperty("BACK_END", backEndStrings[backEnd]);
		
		///////////////////////
		// limit memory usage
//		boolean limitBoards = true;
		properties.setProperty("LIMIT_BOARDS_SIZE", Boolean.toString(limitBoards) );

//		int maxBoardsSize = 2500000;	// a good number for 512M heap
		properties.setProperty("MAX_BOARDS_SIZE", Integer.toString(maxBoardsSize) );
		
		properties.store(new FileOutputStream(propertiesFileName), "FreecellSolver properties");
	}
	
	public void loadProperties() throws FileNotFoundException, IOException, ParseException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFileName));

		////////////////////
		// output settings

		// print out boards along with moves
//		static boolean printBoards = false;
		printBoards = Boolean.parseBoolean( properties.getProperty("PRINT_BOARDS") );

		///////////////////
		// debug settings 
		
		// trace the moves as it tries to solve
		traceMoves = Boolean.parseBoolean(properties.getProperty("TRACE_MOVES"));
		
		printFailed = Boolean.parseBoolean(properties.getProperty("PRINT_FAILED_BOARDS"));
		
		// whether to do sanity check
		sanityCheck = Boolean.parseBoolean(properties.getProperty("SANITY_CHECK"));
		
		////////////////////////
		// optional game rules
		
		// this is not enabled in MS freecell
		allowMoveFromHomeCell = Boolean.parseBoolean(properties.getProperty("ALLOW_MOVE_FROM_HOME_CELL"));
		
		/////////////////////
		// search settings
		
		// use meta move from FreecellSolver solver
		fcsMetaMove = Boolean.parseBoolean(properties.getProperty("FCS_META_MOVE"));
		
		homecellOptimization = Boolean.parseBoolean(properties.getProperty("HOME_CELL_OPTIMIZATION"));
		
		useBoardRevisions = Boolean.parseBoolean(properties.getProperty("USE_BOARD_REVISIONS"));
		
		// this does not work for 821933, for all deals 1-1000,000
		// unless the threadhold is set to 3 and up, see FreecellBoard
		nonEquivalentThreshold = Integer.parseInt(properties.getProperty("NON-EQUIVALENT_THRESHOLD"));
		
		///////////////////////
		// limit memory usage
//		boolean limitBoards = true;
		limitBoards = Boolean.parseBoolean(properties.getProperty("LIMIT_BOARDS_SIZE"));

//		int maxBoardsSize = 2500000;	// a good number for 512M heap
		maxBoardsSize = Integer.parseInt(properties.getProperty("MAX_BOARDS_SIZE"));
		
		try {
			heuristic = parseHeuristic(properties.getProperty("HEURISTIC"));
		} catch (ParseException e) {
			throw new ParseException("FreecellBoard.ParseExceptione : " + e.getMessage());
		}
		
		/////////////////
		// for back end
		
		backEnd = parseBackEnd(properties.getProperty("BACK_END"));
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public static boolean isPrintBoards() {
		return printBoards;
	}

	public static void setPrintBoards(boolean printBoards) {
		FreecellSolver.printBoards = printBoards;
	}

	public boolean isUseBoardRevisions() {
		return useBoardRevisions;
	}

	public void setUseBoardRevisions(boolean useBoardRevisions) {
		this.useBoardRevisions = useBoardRevisions;
	}

	@Override
	public int getSolverResult() {
		return solverResult;
	}

	public int getSolvedMove() {
		return solvedMove;
	}


}
