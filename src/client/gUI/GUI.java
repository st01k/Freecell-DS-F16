package client.gUI;

import freecell.GameBoardInterface;

public interface GUI 
{
	public void paint(GameBoardInterface gb);
	
	public void initialize(GameBoardInterface gb);
}
