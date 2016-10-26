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
 * depth-first-scan tree
 * with priority scoring
 * 
 * properties: 
 * 	only the leaf nodes of the max depth are accessible
 * 	at each depth, only one node can have branches
 * 
 */
public interface DFSTreeIntf {
	
	// the current depth of the tree, initially 0
	public int getDepth();
	
	// there will be 0 node initialy at the new depth
	public void incDepth();
	
	// this will remove all nodes, if exist, at the current depth
	public void decDepth();
	
	// get the node with the HIGHEST score
	// return null if there is no node at current depth
	public Integer popBestNode();
	
	// add a node to the current depth
	public void addNode(int id, double score);
}
