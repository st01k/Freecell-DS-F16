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
 * designate a free cell move
 * 
 *
 */
public class FCMove {

	static final int MOVE_TYPE_STANDARD = 0;
	static final int MOVE_TYPE_FCS_META = 1;
	
	public static final String MOVE_TYPE_STRINGS[] = {"standard", "fcs-meta"};
	
	static final int LOCATION_TYPE_HOME = 0;
	static final int LOCATION_TYPE_FREE = 1;
	static final int LOCATION_TYPE_COLUMN = 2;
	
	public static final String MOVE_LOCATION_STRINGS[] = {"home", "free", "column"};
	
	// 2 bit move type
	// 4 bit move length
	// 2 bit for location, 3 bit for id
	// 2 bit for location, 3 bit for id
	// 16 bit total

	public static int makeFCMove(int moveType, int moveLength, 
			int sourceLocation, int sourceId,
			int destLocation, int destId) {
		int move = moveType;
		
		move <<= 4;
		move += moveLength;
		
		move <<=2;
		move += sourceLocation;

		move <<=3;
		move += sourceId;

		move <<=2;
		move += destLocation;

		move <<=3;
		move += destId;
		
		return move;
	}
	
	public static int getMoveType(int move) {
		return move >> 14;
	}
	
	public static int getMoveLength(int move) {
		return (move >> 10) & 0x0f;
	}
	
	public static int getSourceLocation(int move) {
		return (move >> 8) & 0x03;
	}

	public static int getSourceId(int move) {
		return ( move >> 5 ) & 0x07;
	}

	public static int getDestLocation(int move) {
		return ( move >> 3 ) & 0x03;
	}

	public static int getDestId(int move) {
		return move & 0x07;
	}

	public static String toString(int move) {
		String str;
		str = MOVE_TYPE_STRINGS[getMoveType(move)];
		str += " (" + getMoveLength(move) + ")";
		str += " " + MOVE_LOCATION_STRINGS[getSourceLocation(move)];
		str += "(" + (getSourceId(move)+1) + ") -> ";
		str += " " + MOVE_LOCATION_STRINGS[getDestLocation(move)];
		str += "(" + (getDestId(move)+1) + ")";
		                            
		return str;
	}
	
	public static String getLocationStr(int location, int id) {
		String str;
		switch (location) {
		case LOCATION_TYPE_HOME :
			str = "h";
			break;
		case LOCATION_TYPE_FREE :
			str = Character.toString((char) ('a' + id));
			break;
		case LOCATION_TYPE_COLUMN:
			str = Character.toString((char) ('1' + id));
			break;
		default:
			throw new Error("unknown location : " + location);
		}
		return str;
	}
	
	public static String toStdString(int move) {
		if (getMoveType(move) != MOVE_TYPE_STANDARD) {
			throw new Error("move type is not standard");
		}
		String str = getLocationStr(getSourceLocation(move), getSourceId(move));
		str += getLocationStr(getDestLocation(move), getDestId(move));
		return str;
	}
}
