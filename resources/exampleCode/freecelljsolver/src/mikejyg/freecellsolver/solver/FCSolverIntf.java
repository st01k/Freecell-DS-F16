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

public interface FCSolverIntf {

	// solver result
	public static final int ERROR = 0;
	public static final int SOLVED = 1;
	public static final int UNSOLVABLE = 2;
	public static final int STOPPED = 3;
	public static final int BOARDS_LIMIT_EXCEEDED = 4;
	public static final int OUT_OF_MEMORY_ERROR = 5;

	public int solve(FreecellBoard hand);

	public int getSolverResult();
	
	/**
	 * return report in a string
	 * @return
	 */
	public String toReport();

}
