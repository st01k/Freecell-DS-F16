package client.gui;

import javax.swing.Icon;

public class Hand 
{

	private static Icon II;
	
	public static void add(Icon icon)
	{
		II = icon;
	}
	
	public static Icon getIcon()
	{
		return II;
	}
	
	public void remove()
	{
		II = null;
	}
}
