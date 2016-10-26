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
import java.util.Random;

/**
 * for random DFS scan
 */
public class DFSTreeRand implements DFSTreeIntf {

	int depth = -1;

	// data structure for candidates at each move level
	ArrayList< ArrayList<Integer> > candidates;
	// Integer is the ID of the board
	// array index is the move level, level 0 is the initial board

	Random random = new Random(); 

	public DFSTreeRand() {
		candidates = new ArrayList< ArrayList<Integer> >();
		incDepth();
	}

	public void setRandSeed(long seed) {
		random.setSeed(seed);
	}
	
	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void incDepth() {
		candidates.add( new ArrayList<Integer>() );
		depth++;
	}

	@Override
	public void decDepth() {
		candidates.remove(candidates.size() - 1);
		depth--;
	}

	@Override
	public Integer popBestNode() {
		ArrayList<Integer> array = candidates.get(depth);
		if (array.size() == 0)
			return null;
		else {
			return array.remove( random.nextInt(array.size()) );
		}
	}

	@Override
	public void addNode(int id, double score) {
		// score is ignored
		candidates.get(depth).add(id);
	}
}
