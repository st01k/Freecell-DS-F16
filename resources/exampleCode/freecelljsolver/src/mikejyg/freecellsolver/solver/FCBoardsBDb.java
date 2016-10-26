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

import java.io.File;
import java.nio.ByteBuffer;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * Berkeley DB backed
 */
public class FCBoardsBDb implements FCBoardsIntf {

	Environment myDbEnvironment;
	
	Database boards;

	// for reverse lookup of board
//	Database boardLookup;	// board -> id
	
	Database equivBoards;
	
	// for reverse lookup of equivlent board
	Database equivBoardLookup;	// equivBoard -> id 
	
	int boardCnt = 0;
	
	int equivBoardCnt = 0;
	
	///////////////////////
	// working variables
	DatabaseEntry key = new DatabaseEntry();
	DatabaseEntry data = new DatabaseEntry();	
	
	boolean useBoardRevisions;
	
////////////////////////////////////////////
	
	public FCBoardsBDb() {
		this(false);
	}
	
	public FCBoardsBDb(boolean useBoardRevisions) {
		myDbEnvironment = null;
		
		this.useBoardRevisions = useBoardRevisions;

//		try {
		    EnvironmentConfig envConfig = new EnvironmentConfig();
		    envConfig.setAllowCreate(true);
		    myDbEnvironment = new Environment(new File("dbEnv"), envConfig);
//		} catch (DatabaseException dbe) {
//			// Exception handling goes here
//		} 

		    openDB();	
	}
	
	public void openDB() {
	    // Open the database. Create it if it does not already exist.
	    DatabaseConfig dbConfig = new DatabaseConfig();
	    dbConfig.setAllowCreate(true);
//	    dbConfig.setDeferredWrite(true);
	    dbConfig.setTemporary(true);
	    
	    boards = myDbEnvironment.openDatabase(null,	"boards", dbConfig); 
//	    boardLookup = myDbEnvironment.openDatabase(null, "boardLookup", dbConfig); 
	    equivBoards = myDbEnvironment.openDatabase(null,	"equivBoards", dbConfig); 
	    equivBoardLookup = myDbEnvironment.openDatabase(null, "equivBoardLookup", dbConfig); 
	}

	public void closeDB() {
//		try {
        if (boards != null) {
            boards.close();
//            boardLookup.close();
            equivBoards.close();
            equivBoardLookup.close();
        }
	}

	public void closeEnv() {
		if (myDbEnvironment != null) {
			myDbEnvironment.cleanLog(); // Clean the log before closing
			myDbEnvironment.close();
		} 
		//	} catch (DatabaseException dbe) {
		//	    // Exception handling goes here
		//	} 
	}
	
	@Override
	public int addBoardInfo(FCBoardInfo boardInfo) {
		key.setData(toBytes(boardCnt));
		data.setData(boardInfo.toBytes());
		boards.put(null, key, data);
		
//		boardLookup.put(null, dataBoard, key);
		
		return boardCnt++;
	}

	@Override
	public void updateBoardInfo(int boardId, FCBoardInfo boardInfo) {
		key.setData(toBytes(boardId));
		data.setData(boardInfo.toBytes());
		boards.put(null, key, data);
	}

	public static byte[] toBytes(int num) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(num);
		return bb.array();
	}

	@Override
	public FCBoardInfo getBoardInfo(int boardId) {
		key.setData(toBytes(boardId));
		boards.get(null, key, data, LockMode.DEFAULT);
		if (useBoardRevisions)
			return new FCBoardInfoRevision(data.getData());
		else
			return new FCBoardInfo(data.getData());
	}

	@Override
	public int addEquivBoardInfo(FCEquivBoardInfo boardInfo) {
		key.setData(toBytes(equivBoardCnt));
		data.setData(boardInfo.toBytes());
		equivBoards.put(null, key, data);
		
		key.setData(boardInfo.getEquivBoard().getBytes());
		data.setData(toBytes(equivBoardCnt));
		equivBoardLookup.put(null, key, data);
			
		return equivBoardCnt++;
	}

	@Override
	public FCEquivBoardInfo getEquivBoardInfo(int boardId) {
		key.setData(toBytes(boardId));
		OperationStatus k = equivBoards.get(null, key, data, LockMode.DEFAULT);
		if (k==OperationStatus.NOTFOUND)
			return null;
		else
			return new FCEquivBoardInfo(data.getData());
	}

	@Override
	public void updateEquivBoardInfo(int equivBoardId, FCEquivBoardInfo info) {
		key.setData(toBytes(equivBoardId));
		data.setData(info.toBytes());
		equivBoards.put(null, key, data);
//		OperationStatus k = equivBoards.put(null, key, data);
//		System.out.println(k.toString());
	}

//	@Override
//	public Integer lookUpId(FCBoardBytes board) {
//		DatabaseEntry key = new DatabaseEntry(board.getBytes());
//		DatabaseEntry data = new DatabaseEntry();
//		OperationStatus k = boardLookup.get(null, key, data, LockMode.DEFAULT);
//		if (k==OperationStatus.NOTFOUND)
//			return null;
//		else
//			return intFromBytes(data.getData()); 
//	}

	@Override
	public int size() {
		return boardCnt;
	}

	@Override
	public Integer lookupEquivBoard(FCBoardBytes board) {
		key.setData(board.getBytes());
		
		OperationStatus k = equivBoardLookup.get(null, key, data, LockMode.DEFAULT);
		if (k==OperationStatus.NOTFOUND)
			return null;
		else
			return intFromBytes(data.getData());
	}

	private Integer intFromBytes(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		return bb.getInt();
	}

	@Override
	public int equivSize() {
		return equivBoardCnt;
	}

}
