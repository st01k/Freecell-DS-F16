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

import mikejyg.utilities.PrimitiveWrappers.Bool;

/**
 * this interface takes a configured FreecellSolver and a board,
 * trying different configurations of the FreecellSolver to solve the board
 */
public interface FCMultiSolverIntf {
	// returns boolean result in solved, and the object that produced the result
	public FreecellSolver solve(FreecellSolver fcs, FreecellBoard board, Bool solved);
	
	public void shutdown();
	
}
