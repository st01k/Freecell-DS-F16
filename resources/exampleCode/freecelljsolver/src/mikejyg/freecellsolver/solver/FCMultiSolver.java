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

public class FCMultiSolver implements FCSolverIntf {

	FreecellSolver fcs;
	
	public FCMultiSolver(FreecellSolver fcs) {
		this.fcs = fcs;
	}
	
	@Override
	public int solve(FreecellBoard board) {
		boolean outOfMem = false;

		// try the "better" options first
		fcs.setHeuristic(FreecellSolver.HEURISTIC_CAUTIOUS);
		fcs.setNonEquivalentThreshold(1);
		int k = ERROR;
		try {
			k = fcs.solve(board); 
			if (k==SOLVED)
				return k;
			else if (k==STOPPED) {
				throw new Error("FreecellSolver.StoppedException");
			}
		} catch (OutOfMemoryError e) {
			// release memory
			fcs.clear();
			System.out.println("OutOfMemoryError : " + e.getMessage());
			outOfMem = true;
		}

		// change configuration
		if (outOfMem || k == BOARDS_LIMIT_EXCEEDED) {
			System.out.println("attempt 1 (cautious) - out of memory");
			fcs.setHeuristic(FreecellSolver.HEURISTIC_AGGRESSIVE);
			System.out.println("trying again (aggressive) ...");
		} else {
			System.out.println("attempt 1 (nonEquivThres 1) - unsolvable");
			fcs.setNonEquivalentThreshold(3);
			System.out.println("trying again (nonEquivThres 3) ...");

			k = fcs.solve(board);
			if (k == SOLVED) {
				return k;
			} else if (k == STOPPED) {
				throw new Error("FreecellSolver.StoppedException");
			} else {
				System.out.println("attempt 2 (nonEquivThres 3) - unsolvable");
				fcs.setNonEquivalentThreshold(13);	// disable it
				System.out.println("trying again (equiv optimization off) ...");
			}
		}

		// try again, no further attempt after this one
		k = fcs.solve(board);
		if (k==STOPPED)
			throw new Error("FreecellSolver.StoppedException");
		return k;
	}

	@Override
	public int getSolverResult() {
		return fcs.getSolverResult();
	}

	@Override
	public String toReport() {
		return fcs.toReport();
	}

}
