package graphicalUI;

import freecell.GameBoardInterface;

public interface GUI 
{
	public void paint(GameBoardInterface gb);
	
	public void init(GameBoardInterface gb);
}
