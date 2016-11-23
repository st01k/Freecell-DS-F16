package client.gui;

import javax.swing.JLabel;

import engine.Engine;
import playingCards.StdCard;
import utils.SysUtils;
import static java.lang.System.out;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import board.*;

	/**
	 * @author Ryan Whytsell
	 */
	public class FreeGUI extends javax.swing.JFrame implements MouseMotionListener, MouseListener {

		private static final long serialVersionUID = -2499184546285035594L;

		private static final int NUMCELLS = 4;
		private static final int NUMPILES = 8;
		private static final int MAX_PILE_SIZE = 19;
		private static final String SEP = SysUtils.getSeparator();
		private static final String IMAGESDIR = SysUtils.getPath() + "resources" + SEP + "images" + SEP;
		private static final String CARDIMAGESDIR = IMAGESDIR + "cards" + SEP;
		private static Board ShownBoard;

		

		private static boolean debug = false;

		/**
	     * Creates new form FreeGUI
	     */
	    public FreeGUI()
	    {
	        initComponents();
	    }

	    public void Paint(Board curboard)
	    {
	    	try 
	    	{
				ShownBoard = curboard.clone();
			} 
	    	catch (Exception e)
	    	//TODO had to comment this out to remove error
	    	//catch (CloneNotSupportedException e) 
	    	{
				out.println(e.getMessage());
			}
	    	
	    	if (debug)
	    	{
	    		out.println("\n---engine.FreeGUI.paint---");
	    	}
	    	
	    	// clear consoleout on each turn
	    	consoleOut("");
	    	
	    	//-----------Free Cells------------
	    	
	    	FreeCell[] fcArray = curboard.getFreecells().clone();
	    	for(int i = 0, len = fcArray.length; i < len; i++)
	    	{
	    		StdCard temp = fcArray[i].peekCard();
	    		
	    		if(temp != null)
	    		{
	    			String filename = temp.getValue() + temp.getDefSym() + ".jpg";
	    			fcAry[i].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + filename));
	    		}
	    		else
	    		{
	    			fcAry[i].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "FreeCell.png"));
	    		}
	    		
	    	}
	    	
	    	//----------Home Cells---------------------

	    	HomeCell[] hcArray = curboard.getHomecells().clone();
	    	for(int i = 0, len = hcArray.length; i < len; i++)
	    	{
	    		StdCard temp = hcArray[i].peekCard();
	    		
	    		if(temp != null)
	    		{
	    			String filename = temp.getValue() + temp.getDefSym() + ".jpg";
	    			hcAry[i].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + filename));
	    		}
	    		else
	    		{
	    			hcAry[i].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "HomeCell.png"));
	    		}
	    		
	    	}
	    	
	    	//-------------Playing Piles--------------
	    	
	    	PlayingPile[] pPile = curboard.getPiles().clone();
	    	for(int i = 0, len = pPile.length; i < len; i++)
	    	{
	    		for(int j = 0, len2 = MAX_PILE_SIZE - 1; j < len2; j++)
	    		{
	    			StdCard temp = pPile[i].getCardAt(j);
	    			if(temp != null)
	    			{
		    			String filename = temp.getValue() + temp.getDefSym() + ".jpg";
		    			PlayPile[i][j].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + filename));
	    			}
	    			else
	    			{
	    				PlayPile[i][j].setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "blank.png"));
	    			}
	    		}
	    	}
	    }

	    /**
	     * Acts as a console
	     * @param s String to show
	     */
	    public static void consoleOut(String s)
	    {
	    	Output.setText(s);
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     */
	    private void initComponents() {

	    	// path test logged to console
	    	if (debug) 
	    	{
	    		out.println("\n---engine.FreeGUI.initComponents--- ");
	    	}
	    	
	        BackgroundPan = new javax.swing.JLayeredPane();
	        jLayeredPane1 = new javax.swing.JLayeredPane();
	        TurnLabel = new javax.swing.JLabel();
	        Turn = new javax.swing.JLabel();
	        TimeLabel = new javax.swing.JLabel();
	        Time = new javax.swing.JLabel();
	        SolvableLable = new javax.swing.JLabel();
	        Solvable = new javax.swing.JLabel();
	        Output = new javax.swing.JLabel();
	        InfoBackground = new javax.swing.JLabel();
	        Background = new javax.swing.JLabel();
	        R1C18 = new javax.swing.JLabel();
	        R1C17 = new javax.swing.JLabel();
	        R1C16 = new javax.swing.JLabel();
	        R1C15 = new javax.swing.JLabel();
	        R1C14 = new javax.swing.JLabel();
	        R1C13 = new javax.swing.JLabel();
	        R1C12 = new javax.swing.JLabel();
	        R1C11 = new javax.swing.JLabel();
	        R1C10 = new javax.swing.JLabel();
	        R1C9 = new javax.swing.JLabel();
	        R1C8 = new javax.swing.JLabel();
	        R1C7 = new javax.swing.JLabel();
	        R1C6 = new javax.swing.JLabel();
	        R1C5 = new javax.swing.JLabel();
	        R1C4 = new javax.swing.JLabel();
	        R1C3 = new javax.swing.JLabel();
	        R1C2 = new javax.swing.JLabel();
	        R1C1 = new javax.swing.JLabel();
	        R1C0 = new javax.swing.JLabel();
	        R2C18 = new javax.swing.JLabel();
	        R2C17 = new javax.swing.JLabel();
	        R2C16 = new javax.swing.JLabel();
	        R2C15 = new javax.swing.JLabel();
	        R2C14 = new javax.swing.JLabel();
	        R2C13 = new javax.swing.JLabel();
	        R2C12 = new javax.swing.JLabel();
	        R2C11 = new javax.swing.JLabel();
	        R2C10 = new javax.swing.JLabel();
	        R2C9 = new javax.swing.JLabel();
	        R2C8 = new javax.swing.JLabel();
	        R2C7 = new javax.swing.JLabel();
	        R2C6 = new javax.swing.JLabel();
	        R2C5 = new javax.swing.JLabel();
	        R2C4 = new javax.swing.JLabel();
	        R2C3 = new javax.swing.JLabel();
	        R2C2 = new javax.swing.JLabel();
	        R2C1 = new javax.swing.JLabel();
	        R2C0 = new javax.swing.JLabel();
	        R3C18 = new javax.swing.JLabel();
	        R3C17 = new javax.swing.JLabel();
	        R3C16 = new javax.swing.JLabel();
	        R3C15 = new javax.swing.JLabel();
	        R3C14 = new javax.swing.JLabel();
	        R3C13 = new javax.swing.JLabel();
	        R3C12 = new javax.swing.JLabel();
	        R3C11 = new javax.swing.JLabel();
	        R3C10 = new javax.swing.JLabel();
	        R3C9 = new javax.swing.JLabel();
	        R3C8 = new javax.swing.JLabel();
	        R3C7 = new javax.swing.JLabel();
	        R3C6 = new javax.swing.JLabel();
	        R3C5 = new javax.swing.JLabel();
	        R3C4 = new javax.swing.JLabel();
	        R3C3 = new javax.swing.JLabel();
	        R3C2 = new javax.swing.JLabel();
	        R3C1 = new javax.swing.JLabel();
	        R3C0 = new javax.swing.JLabel();
	        R4C18 = new javax.swing.JLabel();
	        R4C17 = new javax.swing.JLabel();
	        R4C16 = new javax.swing.JLabel();
	        R4C15 = new javax.swing.JLabel();
	        R4C14 = new javax.swing.JLabel();
	        R4C13 = new javax.swing.JLabel();
	        R4C12 = new javax.swing.JLabel();
	        R4C11 = new javax.swing.JLabel();
	        R4C10 = new javax.swing.JLabel();
	        R4C9 = new javax.swing.JLabel();
	        R4C8 = new javax.swing.JLabel();
	        R4C7 = new javax.swing.JLabel();
	        R4C6 = new javax.swing.JLabel();
	        R4C5 = new javax.swing.JLabel();
	        R4C4 = new javax.swing.JLabel();
	        R4C3 = new javax.swing.JLabel();
	        R4C2 = new javax.swing.JLabel();
	        R4C1 = new javax.swing.JLabel();
	        R4C0 = new javax.swing.JLabel();
	        R5C18 = new javax.swing.JLabel();
	        R5C17 = new javax.swing.JLabel();
	        R5C16 = new javax.swing.JLabel();
	        R5C15 = new javax.swing.JLabel();
	        R5C14 = new javax.swing.JLabel();
	        R5C13 = new javax.swing.JLabel();
	        R5C12 = new javax.swing.JLabel();
	        R5C11 = new javax.swing.JLabel();
	        R5C10 = new javax.swing.JLabel();
	        R5C9 = new javax.swing.JLabel();
	        R5C8 = new javax.swing.JLabel();
	        R5C7 = new javax.swing.JLabel();
	        R5C6 = new javax.swing.JLabel();
	        R5C5 = new javax.swing.JLabel();
	        R5C4 = new javax.swing.JLabel();
	        R5C3 = new javax.swing.JLabel();
	        R5C2 = new javax.swing.JLabel();
	        R5C1 = new javax.swing.JLabel();
	        R5C0 = new javax.swing.JLabel();
	        R6C18 = new javax.swing.JLabel();
	        R6C17 = new javax.swing.JLabel();
	        R6C16 = new javax.swing.JLabel();
	        R6C15 = new javax.swing.JLabel();
	        R6C14 = new javax.swing.JLabel();
	        R6C13 = new javax.swing.JLabel();
	        R6C12 = new javax.swing.JLabel();
	        R6C11 = new javax.swing.JLabel();
	        R6C10 = new javax.swing.JLabel();
	        R6C9 = new javax.swing.JLabel();
	        R6C8 = new javax.swing.JLabel();
	        R6C7 = new javax.swing.JLabel();
	        R6C6 = new javax.swing.JLabel();
	        R6C5 = new javax.swing.JLabel();
	        R6C4 = new javax.swing.JLabel();
	        R6C3 = new javax.swing.JLabel();
	        R6C2 = new javax.swing.JLabel();
	        R6C1 = new javax.swing.JLabel();
	        R6C0 = new javax.swing.JLabel();
	        R7C18 = new javax.swing.JLabel();
	        R7C17 = new javax.swing.JLabel();
	        R7C16 = new javax.swing.JLabel();
	        R7C15 = new javax.swing.JLabel();
	        R7C14 = new javax.swing.JLabel();
	        R7C13 = new javax.swing.JLabel();
	        R7C12 = new javax.swing.JLabel();
	        R7C11 = new javax.swing.JLabel();
	        R7C10 = new javax.swing.JLabel();
	        R7C9 = new javax.swing.JLabel();
	        R7C8 = new javax.swing.JLabel();
	        R7C7 = new javax.swing.JLabel();
	        R7C6 = new javax.swing.JLabel();
	        R7C5 = new javax.swing.JLabel();
	        R7C4 = new javax.swing.JLabel();
	        R7C3 = new javax.swing.JLabel();
	        R7C2 = new javax.swing.JLabel();
	        R7C1 = new javax.swing.JLabel();
	        R7C0 = new javax.swing.JLabel();
	        R8C18 = new javax.swing.JLabel();
	        R8C17 = new javax.swing.JLabel();
	        R8C16 = new javax.swing.JLabel();
	        R8C15 = new javax.swing.JLabel();
	        R8C14 = new javax.swing.JLabel();
	        R8C13 = new javax.swing.JLabel();
	        R8C12 = new javax.swing.JLabel();
	        R8C11 = new javax.swing.JLabel();
	        R8C10 = new javax.swing.JLabel();
	        R8C9 = new javax.swing.JLabel();
	        R8C8 = new javax.swing.JLabel();
	        R8C7 = new javax.swing.JLabel();
	        R8C6 = new javax.swing.JLabel();
	        R8C5 = new javax.swing.JLabel();
	        R8C4 = new javax.swing.JLabel();
	        R8C3 = new javax.swing.JLabel();
	        R8C2 = new javax.swing.JLabel();
	        R8C1 = new javax.swing.JLabel();
	        R8C0 = new javax.swing.JLabel();
	        free1 = new javax.swing.JLabel();
	        free2 = new javax.swing.JLabel();
	        free3 = new javax.swing.JLabel();
	        free4 = new javax.swing.JLabel();
	        home1 = new javax.swing.JLabel();
	        home2 = new javax.swing.JLabel();
	        home3 = new javax.swing.JLabel();
	        home4 = new javax.swing.JLabel();
	        MenuPanel = new javax.swing.JPanel();
	        UndoBtn = new javax.swing.JButton();
	        RedoBtn = new javax.swing.JButton();
	        HintBtn = new javax.swing.JButton();
	        SolveBtn = new javax.swing.JButton();
	        NewDealBtn = new javax.swing.JButton();

	        BackgroundPan.addMouseMotionListener(this);
	        BackgroundPan.addMouseListener(this);
	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Freecell by GroovyLlamas");
	        setBackground(new java.awt.Color(255, 255, 255));
	        setMaximumSize(new java.awt.Dimension(1280, 720));
	        setMinimumSize(new java.awt.Dimension(1280, 720));
	        setName("MainFrame");
	        setPreferredSize(new java.awt.Dimension(1280, 720));
	        setResizable(false);
	        setSize(new java.awt.Dimension(1280, 720));
	        getContentPane().setLayout(null);

	        BackgroundPan.setOpaque(true);

	        TurnLabel.setText("Turn:");
	        jLayeredPane1.add(TurnLabel);
	        TurnLabel.setBounds(20, 20, 34, 14);
	        jLayeredPane1.add(Turn);
	        Turn.setBounds(50, 20, 0, 0);

	        TimeLabel.setText("Time: ");
	        jLayeredPane1.add(TimeLabel);
	        TimeLabel.setBounds(240, 20, 35, 14);
	        jLayeredPane1.add(Time);
	        Time.setBounds(270, 20, 90, 0);

	        SolvableLable.setText("Solvable: ");
	        jLayeredPane1.add(SolvableLable);
	        SolvableLable.setBounds(20, 60, 55, 14);
	        jLayeredPane1.add(Solvable);
	        Solvable.setBounds(70, 60, 0, 0);

	        Output.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLayeredPane1.add(Output);
	        Output.setBounds(20, 90, 340, 30);

	        InfoBackground.setBackground(new java.awt.Color(204, 204, 204));
	        InfoBackground.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(20, 20, 20), 5));
	        InfoBackground.setOpaque(true);
	        jLayeredPane1.add(InfoBackground);
	        InfoBackground.setBounds(0, 0, 380, 130);

	        BackgroundPan.add(jLayeredPane1);
	        jLayeredPane1.setBounds(440, 10, 380, 130);

	        Background.setBackground(new java.awt.Color(100, 100, 100));
	        Background.setIcon(new javax.swing.ImageIcon(IMAGESDIR + "felt.png"));
	        Background.setToolTipText(null);
	        Background.setOpaque(true);
	        BackgroundPan.add(Background);
	        Background.setBounds(0, 0, 1280, 690);

	        R1C18.setToolTipText(null);
	        R1C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C18);
	        R1C18.setBounds(60, 510, 86, 125);

	        R1C17.setToolTipText(null);
	        R1C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C17);
	        R1C17.setBounds(60, 490, 86, 125);

	        R1C16.setToolTipText(null);
	        R1C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C16);
	        R1C16.setBounds(60, 470, 86, 125);

	        R1C15.setToolTipText(null);
	        R1C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C15);
	        R1C15.setBounds(60, 450, 86, 125);

	        R1C14.setToolTipText(null);
	        R1C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C14);
	        R1C14.setBounds(60, 430, 86, 125);

	        R1C13.setToolTipText(null);
	        R1C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C13);
	        R1C13.setBounds(60, 410, 86, 125);

	        R1C12.setToolTipText(null);
	        R1C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C12);
	        R1C12.setBounds(60, 390, 86, 125);

	        R1C11.setToolTipText(null);
	        R1C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C11);
	        R1C11.setBounds(60, 370, 86, 125);

	        R1C10.setToolTipText(null);
	        R1C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C10);
	        R1C10.setBounds(60, 350, 86, 125);

	        R1C9.setToolTipText(null);
	        R1C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C9);
	        R1C9.setBounds(60, 330, 86, 125);

	        R1C8.setToolTipText(null);
	        R1C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C8);
	        R1C8.setBounds(60, 310, 86, 125);

	        R1C7.setToolTipText(null);
	        R1C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C7);
	        R1C7.setBounds(60, 290, 86, 125);

	        R1C6.setToolTipText(null);
	        R1C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C6);
	        R1C6.setBounds(60, 270, 86, 125);

	        R1C5.setToolTipText(null);
	        R1C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C5);
	        R1C5.setBounds(60, 250, 86, 125);

	        R1C4.setToolTipText(null);
	        R1C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C4);
	        R1C4.setBounds(60, 230, 86, 125);

	        R1C3.setToolTipText(null);
	        R1C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C3);
	        R1C3.setBounds(60, 210, 86, 125);

	        R1C2.setToolTipText(null);
	        R1C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C2);
	        R1C2.setBounds(60, 190, 86, 125);

	        R1C1.setToolTipText(null);
	        R1C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C1);
	        R1C1.setBounds(60, 170, 86, 125);

	        R1C0.setToolTipText(null);
	        R1C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R1C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R1C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R1C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R1C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R1C0);
	        R1C0.setBounds(60, 150, 86, 125);

	        R2C18.setToolTipText(null);
	        R2C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C18);
	        R2C18.setBounds(220, 510, 86, 125);

	        R2C17.setToolTipText(null);
	        R2C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C17);
	        R2C17.setBounds(220, 490, 86, 125);

	        R2C16.setToolTipText(null);
	        R2C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C16);
	        R2C16.setBounds(220, 470, 86, 125);

	        R2C15.setToolTipText(null);
	        R2C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C15);
	        R2C15.setBounds(220, 450, 86, 125);

	        R2C14.setToolTipText(null);
	        R2C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C14);
	        R2C14.setBounds(220, 430, 86, 125);

	        R2C13.setToolTipText(null);
	        R2C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C13);
	        R2C13.setBounds(220, 410, 86, 125);

	        R2C12.setToolTipText(null);
	        R2C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C12);
	        R2C12.setBounds(220, 390, 86, 125);

	        R2C11.setToolTipText(null);
	        R2C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C11);
	        R2C11.setBounds(220, 370, 86, 125);

	        R2C10.setToolTipText(null);
	        R2C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C10);
	        R2C10.setBounds(220, 350, 86, 125);

	        R2C9.setToolTipText(null);
	        R2C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C9);
	        R2C9.setBounds(220, 330, 86, 125);

	        R2C8.setToolTipText(null);
	        R2C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C8);
	        R2C8.setBounds(220, 310, 86, 125);

	        R2C7.setToolTipText(null);
	        R2C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C7);
	        R2C7.setBounds(220, 290, 86, 125);

	        R2C6.setToolTipText(null);
	        R2C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C6);
	        R2C6.setBounds(220, 270, 86, 125);

	        R2C5.setToolTipText(null);
	        R2C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C5);
	        R2C5.setBounds(220, 250, 86, 125);

	        R2C4.setToolTipText(null);
	        R2C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C4);
	        R2C4.setBounds(220, 230, 86, 125);

	        R2C3.setToolTipText(null);
	        R2C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C3);
	        R2C3.setBounds(220, 210, 86, 125);

	        R2C2.setToolTipText(null);
	        R2C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C2);
	        R2C2.setBounds(220, 190, 86, 125);

	        R2C1.setToolTipText(null);
	        R2C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C1);
	        R2C1.setBounds(220, 170, 86, 125);

	        R2C0.setToolTipText(null);
	        R2C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R2C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R2C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R2C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R2C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R2C0);
	        R2C0.setBounds(220, 150, 86, 125);

	        R3C18.setToolTipText(null);
	        R3C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C18);
	        R3C18.setBounds(370, 510, 86, 125);

	        R3C17.setToolTipText(null);
	        R3C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C17);
	        R3C17.setBounds(370, 490, 86, 125);

	        R3C16.setToolTipText(null);
	        R3C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C16);
	        R3C16.setBounds(370, 470, 86, 125);

	        R3C15.setToolTipText(null);
	        R3C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C15);
	        R3C15.setBounds(370, 450, 86, 125);

	        R3C14.setToolTipText(null);
	        R3C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C14);
	        R3C14.setBounds(370, 430, 86, 125);

	        R3C13.setToolTipText(null);
	        R3C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C13);
	        R3C13.setBounds(370, 410, 86, 125);

	        R3C12.setToolTipText(null);
	        R3C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C12);
	        R3C12.setBounds(370, 390, 86, 125);

	        R3C11.setToolTipText(null);
	        R3C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C11);
	        R3C11.setBounds(370, 370, 86, 125);

	        R3C10.setToolTipText(null);
	        R3C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C10);
	        R3C10.setBounds(370, 350, 86, 125);

	        R3C9.setToolTipText(null);
	        R3C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C9);
	        R3C9.setBounds(370, 330, 86, 125);

	        R3C8.setToolTipText(null);
	        R3C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C8);
	        R3C8.setBounds(370, 310, 86, 125);

	        R3C7.setToolTipText(null);
	        R3C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C7);
	        R3C7.setBounds(370, 290, 86, 125);

	        R3C6.setToolTipText(null);
	        R3C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C6);
	        R3C6.setBounds(370, 270, 86, 125);

	        R3C5.setToolTipText(null);
	        R3C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C5);
	        R3C5.setBounds(370, 250, 86, 125);

	        R3C4.setToolTipText(null);
	        R3C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C4);
	        R3C4.setBounds(370, 230, 86, 125);

	        R3C3.setToolTipText(null);
	        R3C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C3);
	        R3C3.setBounds(370, 210, 86, 125);

	        R3C2.setToolTipText(null);
	        R3C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C2);
	        R3C2.setBounds(370, 190, 86, 125);

	        R3C1.setToolTipText(null);
	        R3C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C1);
	        R3C1.setBounds(370, 170, 86, 125);

	        R3C0.setToolTipText(null);
	        R3C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R3C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R3C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R3C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R3C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R3C0);
	        R3C0.setBounds(370, 150, 86, 125);

	        R4C18.setToolTipText(null);
	        R4C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C18);
	        R4C18.setBounds(520, 510, 86, 125);

	        R4C17.setToolTipText(null);
	        R4C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C17);
	        R4C17.setBounds(520, 490, 86, 125);

	        R4C16.setToolTipText(null);
	        R4C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C16);
	        R4C16.setBounds(520, 470, 86, 125);

	        R4C15.setToolTipText(null);
	        R4C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C15);
	        R4C15.setBounds(520, 450, 86, 125);

	        R4C14.setToolTipText(null);
	        R4C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C14);
	        R4C14.setBounds(520, 430, 86, 125);

	        R4C13.setToolTipText(null);
	        R4C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C13);
	        R4C13.setBounds(520, 410, 86, 125);

	        R4C12.setToolTipText(null);
	        R4C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C12);
	        R4C12.setBounds(520, 390, 86, 125);

	        R4C11.setToolTipText(null);
	        R4C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C11);
	        R4C11.setBounds(520, 370, 86, 125);

	        R4C10.setToolTipText(null);
	        R4C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C10);
	        R4C10.setBounds(520, 350, 86, 125);

	        R4C9.setToolTipText(null);
	        R4C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C9);
	        R4C9.setBounds(520, 330, 86, 125);

	        R4C8.setToolTipText(null);
	        R4C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C8);
	        R4C8.setBounds(520, 310, 86, 125);

	        R4C7.setToolTipText(null);
	        R4C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C7);
	        R4C7.setBounds(520, 290, 86, 125);

	        R4C6.setToolTipText(null);
	        R4C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C6);
	        R4C6.setBounds(520, 270, 86, 125);

	        R4C5.setToolTipText(null);
	        R4C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C5);
	        R4C5.setBounds(520, 250, 86, 125);

	        R4C4.setToolTipText(null);
	        R4C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C4);
	        R4C4.setBounds(520, 230, 86, 125);

	        R4C3.setToolTipText(null);
	        R4C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C3);
	        R4C3.setBounds(520, 210, 86, 125);

	        R4C2.setToolTipText(null);
	        R4C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C2);
	        R4C2.setBounds(520, 190, 86, 125);

	        R4C1.setToolTipText(null);
	        R4C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C1);
	        R4C1.setBounds(520, 170, 86, 125);

	        R4C0.setToolTipText(null);
	        R4C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R4C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R4C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R4C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R4C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R4C0);
	        R4C0.setBounds(520, 150, 86, 125);

	        R5C18.setToolTipText(null);
	        R5C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C18);
	        R5C18.setBounds(670, 510, 86, 125);

	        R5C17.setToolTipText(null);
	        R5C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C17);
	        R5C17.setBounds(670, 490, 86, 125);

	        R5C16.setToolTipText(null);
	        R5C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C16);
	        R5C16.setBounds(670, 470, 86, 125);

	        R5C15.setToolTipText(null);
	        R5C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C15);
	        R5C15.setBounds(670, 450, 86, 125);

	        R5C14.setToolTipText(null);
	        R5C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C14);
	        R5C14.setBounds(670, 430, 86, 125);

	        R5C13.setToolTipText(null);
	        R5C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C13);
	        R5C13.setBounds(670, 410, 86, 125);

	        R5C12.setToolTipText(null);
	        R5C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C12);
	        R5C12.setBounds(670, 390, 86, 125);

	        R5C11.setToolTipText(null);
	        R5C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C11);
	        R5C11.setBounds(670, 370, 86, 125);

	        R5C10.setToolTipText(null);
	        R5C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C10);
	        R5C10.setBounds(670, 350, 86, 125);

	        R5C9.setToolTipText(null);
	        R5C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C9);
	        R5C9.setBounds(670, 330, 86, 125);

	        R5C8.setToolTipText(null);
	        R5C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C8);
	        R5C8.setBounds(670, 310, 86, 125);

	        R5C7.setToolTipText(null);
	        R5C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C7);
	        R5C7.setBounds(670, 290, 86, 125);

	        R5C6.setToolTipText(null);
	        R5C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C6);
	        R5C6.setBounds(670, 270, 86, 125);

	        R5C5.setToolTipText(null);
	        R5C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C5);
	        R5C5.setBounds(670, 250, 86, 125);

	        R5C4.setToolTipText(null);
	        R5C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C4);
	        R5C4.setBounds(670, 230, 86, 125);

	        R5C3.setToolTipText(null);
	        R5C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C3);
	        R5C3.setBounds(670, 210, 86, 125);

	        R5C2.setToolTipText(null);
	        R5C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C2);
	        R5C2.setBounds(670, 190, 86, 125);

	        R5C1.setToolTipText(null);
	        R5C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C1);
	        R5C1.setBounds(670, 170, 86, 125);

	        R5C0.setToolTipText(null);
	        R5C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R5C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R5C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R5C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R5C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R5C0);
	        R5C0.setBounds(670, 150, 86, 125);

	        R6C18.setToolTipText(null);
	        R6C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C18);
	        R6C18.setBounds(820, 510, 86, 125);

	        R6C17.setToolTipText(null);
	        R6C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C17);
	        R6C17.setBounds(820, 490, 86, 125);

	        R6C16.setToolTipText(null);
	        R6C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C16);
	        R6C16.setBounds(820, 470, 86, 125);

	        R6C15.setToolTipText(null);
	        R6C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C15);
	        R6C15.setBounds(820, 450, 86, 125);

	        R6C14.setToolTipText(null);
	        R6C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C14);
	        R6C14.setBounds(820, 430, 86, 125);

	        R6C13.setToolTipText(null);
	        R6C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C13);
	        R6C13.setBounds(820, 410, 86, 125);

	        R6C12.setToolTipText(null);
	        R6C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C12);
	        R6C12.setBounds(820, 390, 86, 125);

	        R6C11.setToolTipText(null);
	        R6C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C11);
	        R6C11.setBounds(820, 370, 86, 125);

	        R6C10.setToolTipText(null);
	        R6C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C10);
	        R6C10.setBounds(820, 350, 86, 125);

	        R6C9.setToolTipText(null);
	        R6C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C9);
	        R6C9.setBounds(820, 330, 86, 125);

	        R6C8.setToolTipText(null);
	        R6C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C8);
	        R6C8.setBounds(820, 310, 86, 125);

	        R6C7.setToolTipText(null);
	        R6C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C7);
	        R6C7.setBounds(820, 290, 86, 125);

	        R6C6.setToolTipText(null);
	        R6C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C6);
	        R6C6.setBounds(820, 270, 86, 125);

	        R6C5.setToolTipText(null);
	        R6C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C5);
	        R6C5.setBounds(820, 250, 86, 125);

	        R6C4.setToolTipText(null);
	        R6C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C4);
	        R6C4.setBounds(820, 230, 86, 125);

	        R6C3.setToolTipText(null);
	        R6C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C3);
	        R6C3.setBounds(820, 210, 86, 125);

	        R6C2.setToolTipText(null);
	        R6C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C2);
	        R6C2.setBounds(820, 190, 86, 125);

	        R6C1.setToolTipText(null);
	        R6C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C1);
	        R6C1.setBounds(820, 170, 86, 125);

	        R6C0.setToolTipText(null);
	        R6C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R6C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R6C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R6C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R6C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R6C0);
	        R6C0.setBounds(820, 150, 86, 125);

	        R7C18.setToolTipText(null);
	        R7C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C18);
	        R7C18.setBounds(970, 510, 86, 125);

	        R7C17.setToolTipText(null);
	        R7C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C17);
	        R7C17.setBounds(970, 490, 86, 125);

	        R7C16.setToolTipText(null);
	        R7C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C16);
	        R7C16.setBounds(970, 470, 86, 125);

	        R7C15.setToolTipText(null);
	        R7C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C15);
	        R7C15.setBounds(970, 450, 86, 125);

	        R7C14.setToolTipText(null);
	        R7C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C14);
	        R7C14.setBounds(970, 430, 86, 125);

	        R7C13.setToolTipText(null);
	        R7C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C13);
	        R7C13.setBounds(970, 410, 86, 125);

	        R7C12.setToolTipText(null);
	        R7C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C12);
	        R7C12.setBounds(970, 390, 86, 125);

	        R7C11.setToolTipText(null);
	        R7C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C11);
	        R7C11.setBounds(970, 370, 86, 125);

	        R7C10.setToolTipText(null);
	        R7C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C10);
	        R7C10.setBounds(970, 350, 86, 125);

	        R7C9.setToolTipText(null);
	        R7C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C9);
	        R7C9.setBounds(970, 330, 86, 125);

	        R7C8.setToolTipText(null);
	        R7C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C8);
	        R7C8.setBounds(970, 310, 86, 125);

	        R7C7.setToolTipText(null);
	        R7C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C7);
	        R7C7.setBounds(970, 290, 86, 125);

	        R7C6.setToolTipText(null);
	        R7C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C6);
	        R7C6.setBounds(970, 270, 86, 125);

	        R7C5.setToolTipText(null);
	        R7C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C5);
	        R7C5.setBounds(970, 250, 86, 125);

	        R7C4.setToolTipText(null);
	        R7C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C4);
	        R7C4.setBounds(970, 230, 86, 125);

	        R7C3.setToolTipText(null);
	        R7C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C3);
	        R7C3.setBounds(970, 210, 86, 125);

	        R7C2.setToolTipText(null);
	        R7C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C2);
	        R7C2.setBounds(970, 190, 86, 125);

	        R7C1.setToolTipText(null);
	        R7C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C1);
	        R7C1.setBounds(970, 170, 86, 125);

	        R7C0.setToolTipText(null);
	        R7C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R7C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R7C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R7C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R7C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R7C0);
	        R7C0.setBounds(970, 150, 86, 125);

	        R8C18.setToolTipText(null);
	        R8C18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C18.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C18.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C18.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C18, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C18);
	        R8C18.setBounds(1120, 510, 86, 125);

	        R8C17.setToolTipText(null);
	        R8C17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C17.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C17.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C17.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C17, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C17);
	        R8C17.setBounds(1120, 490, 86, 125);

	        R8C16.setToolTipText(null);
	        R8C16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C16.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C16.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C16.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C16, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C16);
	        R8C16.setBounds(1120, 470, 86, 125);

	        R8C15.setToolTipText(null);
	        R8C15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C15.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C15.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C15.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C15, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C15);
	        R8C15.setBounds(1120, 450, 86, 125);

	        R8C14.setToolTipText(null);
	        R8C14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C14.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C14.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C14.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C14, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C14);
	        R8C14.setBounds(1120, 430, 86, 125);

	        R8C13.setToolTipText(null);
	        R8C13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C13.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C13.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C13.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C13, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C13);
	        R8C13.setBounds(1120, 410, 86, 125);

	        R8C12.setToolTipText(null);
	        R8C12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C12.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C12.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C12.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C12, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C12);
	        R8C12.setBounds(1120, 390, 86, 125);

	        R8C11.setToolTipText(null);
	        R8C11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C11.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C11.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C11.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C11, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C11);
	        R8C11.setBounds(1120, 370, 86, 125);

	        R8C10.setToolTipText(null);
	        R8C10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C10.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C10.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C10.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C10, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C10);
	        R8C10.setBounds(1120, 350, 86, 125);

	        R8C9.setToolTipText(null);
	        R8C9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C9.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C9.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C9.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C9, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C9);
	        R8C9.setBounds(1120, 330, 86, 125);

	        R8C8.setToolTipText(null);
	        R8C8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C8.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C8.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C8.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C8, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C8);
	        R8C8.setBounds(1120, 310, 86, 125);

	        R8C7.setToolTipText(null);
	        R8C7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C7.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C7.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C7.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C7, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C7);
	        R8C7.setBounds(1120, 290, 86, 125);

	        R8C6.setToolTipText(null);
	        R8C6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C6.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C6.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C6.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C6, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C6);
	        R8C6.setBounds(1120, 270, 86, 125);

	        R8C5.setToolTipText(null);
	        R8C5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C5.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C5.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C5.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C5, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C5);
	        R8C5.setBounds(1120, 250, 86, 125);

	        R8C4.setToolTipText(null);
	        R8C4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C4.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C4.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C4);
	        R8C4.setBounds(1120, 230, 86, 125);

	        R8C3.setToolTipText(null);
	        R8C3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C3.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C3.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C3);
	        R8C3.setBounds(1120, 210, 86, 125);

	        R8C2.setToolTipText(null);
	        R8C2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C2.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C2.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C2);
	        R8C2.setBounds(1120, 190, 86, 125);

	        R8C1.setToolTipText(null);
	        R8C1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C1.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C1.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C1);
	        R8C1.setBounds(1120, 170, 86, 125);

	        R8C0.setToolTipText(null);
	        R8C0.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
	        R8C0.setMaximumSize(new java.awt.Dimension(86, 125));
	        R8C0.setMinimumSize(new java.awt.Dimension(86, 125));
	        R8C0.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(R8C0, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(R8C0);
	        R8C0.setBounds(1120, 150, 86, 125);

	        free1.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "FreeCell.png"));
	        free1.setToolTipText(null);
	        free1.setMaximumSize(new java.awt.Dimension(86, 125));
	        free1.setMinimumSize(new java.awt.Dimension(86, 125));
	        free1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(free1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(free1);
	        free1.setBounds(20, 10, 86, 125);

	        free2.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "FreeCell.png"));
	        free2.setToolTipText(null);
	        free2.setMaximumSize(new java.awt.Dimension(86, 125));
	        free2.setMinimumSize(new java.awt.Dimension(86, 125));
	        free2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(free2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(free2);
	        free2.setBounds(120, 10, 86, 125);

	        free3.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "FreeCell.png"));
	        free3.setToolTipText(null);
	        free3.setMaximumSize(new java.awt.Dimension(86, 125));
	        free3.setMinimumSize(new java.awt.Dimension(86, 125));
	        free3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(free3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(free3);
	        free3.setBounds(220, 10, 86, 125);

	        free4.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "FreeCell.png")); 
	        free4.setToolTipText(null);
	        free4.setMaximumSize(new java.awt.Dimension(86, 125));
	        free4.setMinimumSize(new java.awt.Dimension(86, 125));
	        free4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(free4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(free4);
	        free4.setBounds(320, 10, 86, 125);

	        home1.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "HomeCell.png"));
	        home1.setToolTipText(null);
	        home1.setMaximumSize(new java.awt.Dimension(86, 125));
	        home1.setMinimumSize(new java.awt.Dimension(86, 125));
	        home1.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(home1, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(home1);
	        home1.setBounds(850, 10, 86, 125);

	        home2.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "HomeCell.png"));
	        home2.setToolTipText(null);
	        home2.setMaximumSize(new java.awt.Dimension(86, 125));
	        home2.setMinimumSize(new java.awt.Dimension(86, 125));
	        home2.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(home2, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(home2);
	        home2.setBounds(950, 10, 86, 125);

	        home3.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "HomeCell.png"));
	        home3.setToolTipText(null);
	        home3.setMaximumSize(new java.awt.Dimension(86, 125));
	        home3.setMinimumSize(new java.awt.Dimension(86, 125));
	        home3.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(home3, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(home3);
	        home3.setBounds(1050, 10, 86, 125);

	        home4.setIcon(new javax.swing.ImageIcon(CARDIMAGESDIR + "HomeCell.png"));
	        home4.setToolTipText(null);
	        home4.setMaximumSize(new java.awt.Dimension(86, 125));
	        home4.setMinimumSize(new java.awt.Dimension(86, 125));
	        home4.setPreferredSize(new java.awt.Dimension(86, 125));
	        BackgroundPan.setLayer(home4, javax.swing.JLayeredPane.DRAG_LAYER);
	        BackgroundPan.add(home4);
	        home4.setBounds(1150, 10, 86, 125);

	        getContentPane().add(BackgroundPan);
	        BackgroundPan.setBounds(0, 30, 1287, 730);

	        MenuPanel.setBackground(new java.awt.Color(255, 255, 255));
	        MenuPanel.setMaximumSize(new java.awt.Dimension(1280, 55));
	        MenuPanel.setMinimumSize(new java.awt.Dimension(1280, 55));

	        UndoBtn.setText("Undo");
	        UndoBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                UndoBtnActionPerformed(evt);
	            }
	        });

	        RedoBtn.setText("Redo");
	        RedoBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                RedoBtnActionPerformed(evt);
	            }
	        });

	        HintBtn.setText("Hint");
	        HintBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                HintBtnActionPerformed(evt);
	            }
	        });

	        SolveBtn.setText("Solve");
	        SolveBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                SolveBtnActionPerformed(evt);
	            }

	        });

	        NewDealBtn.setText("New Deal");
	        NewDealBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                NewDealBtnActionPerformed(evt);
	            }


	        });

	        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
	        MenuPanel.setLayout(MenuPanelLayout);
	        MenuPanelLayout.setHorizontalGroup(
	            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(MenuPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(NewDealBtn)
	                .addGap(134, 134, 134)
	                .addComponent(UndoBtn)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(RedoBtn)
	                .addGap(127, 127, 127)
	                .addComponent(HintBtn)
	                .addGap(18, 18, 18)
	                .addComponent(SolveBtn)
	                .addContainerGap(691, Short.MAX_VALUE))
	        );
	        MenuPanelLayout.setVerticalGroup(
	            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(MenuPanelLayout.createSequentialGroup()
	                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(NewDealBtn)
	                    .addComponent(UndoBtn)
	                    .addComponent(RedoBtn)
	                    .addComponent(HintBtn)
	                    .addComponent(SolveBtn))
	                .addGap(0, 32, Short.MAX_VALUE))
	        );

	        getContentPane().add(MenuPanel);
	        MenuPanel.setBounds(0, 0, 1287, 30);
	        setLocationRelativeTo(null);
	        setAlwaysOnTop(true);
	        pack();
	        
	       
	       for(int i = 0; i < NUMCELLS; i++)
	       {
	    	   switch(i)
	    	   {
		    	   case(0):
		    	   {
		    		   fcAry[i] = free1;
		    		   hcAry[i] = home1;
		    		   break;
		    	   }
		    	   case(1):
		    	   {
		    		   fcAry[i] = free2;
		    		   hcAry[i] = home2;
		    		   break;
		    	   }
		    	   case(2):
		    	   {
		    		   fcAry[i] = free3;
		    		   hcAry[i] = home3;
		    		   break;
		    	   }
		    	   case(3):
		    	   {
		    		   fcAry[i] = free4;
		    		   hcAry[i] = home4;
		    		   break;
		    	   }
	    	   }
	       }
	       
	       for(int i = 0; i < NUMPILES; i++)
	       {
	    	   switch(i)
	    	   {
		    	   case(0):
		    	   {
		    		   PlayPile[i][0] = R1C0;
		    		   PlayPile[i][1] = R1C1;
		    		   PlayPile[i][2] = R1C2;
		    		   PlayPile[i][3] = R1C3;
		    		   PlayPile[i][4] = R1C4;
		    		   PlayPile[i][5] = R1C5;
		    		   PlayPile[i][6] = R1C6;
		    		   PlayPile[i][7] = R1C7;
		    		   PlayPile[i][8] = R1C8;
		    		   PlayPile[i][9] = R1C9;
		    		   PlayPile[i][10] = R1C10;
		    		   PlayPile[i][11] = R1C11;
		    		   PlayPile[i][12] = R1C12;
		    		   PlayPile[i][13] = R1C13;
		    		   PlayPile[i][14] = R1C14;
		    		   PlayPile[i][15] = R1C15;
		    		   PlayPile[i][16] = R1C16;
		    		   PlayPile[i][17] = R1C17;
		    		   break;
		    	   }
		    	   case(1):
		    	   {
		    		   PlayPile[i][0] = R2C0;
		    		   PlayPile[i][1] = R2C1;
		    		   PlayPile[i][2] = R2C2;
		    		   PlayPile[i][3] = R2C3;
		    		   PlayPile[i][4] = R2C4;
		    		   PlayPile[i][5] = R2C5;
		    		   PlayPile[i][6] = R2C6;
		    		   PlayPile[i][7] = R2C7;
		    		   PlayPile[i][8] = R2C8;
		    		   PlayPile[i][9] = R2C9;
		    		   PlayPile[i][10] = R2C10;
		    		   PlayPile[i][11] = R2C11;
		    		   PlayPile[i][12] = R2C12;
		    		   PlayPile[i][13] = R2C13;
		    		   PlayPile[i][14] = R2C14;
		    		   PlayPile[i][15] = R2C15;
		    		   PlayPile[i][16] = R2C16;
		    		   PlayPile[i][17] = R2C17;
		    		   break;
		    	   }
		    	   case(2):
		    	   {
		    		   PlayPile[i][0] = R3C0;
		    		   PlayPile[i][1] = R3C1;
		    		   PlayPile[i][2] = R3C2;
		    		   PlayPile[i][3] = R3C3;
		    		   PlayPile[i][4] = R3C4;
		    		   PlayPile[i][5] = R3C5;
		    		   PlayPile[i][6] = R3C6;
		    		   PlayPile[i][7] = R3C7;
		    		   PlayPile[i][8] = R3C8;
		    		   PlayPile[i][9] = R3C9;
		    		   PlayPile[i][10] = R3C10;
		    		   PlayPile[i][11] = R3C11;
		    		   PlayPile[i][12] = R3C12;
		    		   PlayPile[i][13] = R3C13;
		    		   PlayPile[i][14] = R3C14;
		    		   PlayPile[i][15] = R3C15;
		    		   PlayPile[i][16] = R3C16;
		    		   PlayPile[i][17] = R3C17;
		    		   break;
		    	   }
		    	   case(3):
		    	   {
		    		   PlayPile[i][0] = R4C0;
		    		   PlayPile[i][1] = R4C1;
		    		   PlayPile[i][2] = R4C2;
		    		   PlayPile[i][3] = R4C3;
		    		   PlayPile[i][4] = R4C4;
		    		   PlayPile[i][5] = R4C5;
		    		   PlayPile[i][6] = R4C6;
		    		   PlayPile[i][7] = R4C7;
		    		   PlayPile[i][8] = R4C8;
		    		   PlayPile[i][9] = R4C9;
		    		   PlayPile[i][10] = R4C10;
		    		   PlayPile[i][11] = R4C11;
		    		   PlayPile[i][12] = R4C12;
		    		   PlayPile[i][13] = R4C13;
		    		   PlayPile[i][14] = R4C14;
		    		   PlayPile[i][15] = R4C15;
		    		   PlayPile[i][16] = R4C16;
		    		   PlayPile[i][17] = R4C17;
		    		   break;
		    	   }
		    	   case(4):
		    	   {
		    		   PlayPile[i][0] = R5C0;
		    		   PlayPile[i][1] = R5C1;
		    		   PlayPile[i][2] = R5C2;
		    		   PlayPile[i][3] = R5C3;
		    		   PlayPile[i][4] = R5C4;
		    		   PlayPile[i][5] = R5C5;
		    		   PlayPile[i][6] = R5C6;
		    		   PlayPile[i][7] = R5C7;
		    		   PlayPile[i][8] = R5C8;
		    		   PlayPile[i][9] = R5C9;
		    		   PlayPile[i][10] = R5C10;
		    		   PlayPile[i][11] = R5C11;
		    		   PlayPile[i][12] = R5C12;
		    		   PlayPile[i][13] = R5C13;
		    		   PlayPile[i][14] = R5C14;
		    		   PlayPile[i][15] = R5C15;
		    		   PlayPile[i][16] = R5C16;
		    		   PlayPile[i][17] = R5C17;
		    		   break;
		    	   }
		    	   case(5):
		    	   {
		    		   PlayPile[i][0] = R6C0;
		    		   PlayPile[i][1] = R6C1;
		    		   PlayPile[i][2] = R6C2;
		    		   PlayPile[i][3] = R6C3;
		    		   PlayPile[i][4] = R6C4;
		    		   PlayPile[i][5] = R6C5;
		    		   PlayPile[i][6] = R6C6;
		    		   PlayPile[i][7] = R6C7;
		    		   PlayPile[i][8] = R6C8;
		    		   PlayPile[i][9] = R6C9;
		    		   PlayPile[i][10] = R6C10;
		    		   PlayPile[i][11] = R6C11;
		    		   PlayPile[i][12] = R6C12;
		    		   PlayPile[i][13] = R6C13;
		    		   PlayPile[i][14] = R6C14;
		    		   PlayPile[i][15] = R6C15;
		    		   PlayPile[i][16] = R6C16;
		    		   PlayPile[i][17] = R6C17;
		    		   break;
		    	   }
		    	   case(6):
		    	   {
		    		   PlayPile[i][0] = R7C0;
		    		   PlayPile[i][1] = R7C1;
		    		   PlayPile[i][2] = R7C2;
		    		   PlayPile[i][3] = R7C3;
		    		   PlayPile[i][4] = R7C4;
		    		   PlayPile[i][5] = R7C5;
		    		   PlayPile[i][6] = R7C6;
		    		   PlayPile[i][7] = R7C7;
		    		   PlayPile[i][8] = R7C8;
		    		   PlayPile[i][9] = R7C9;
		    		   PlayPile[i][10] = R7C10;
		    		   PlayPile[i][11] = R7C11;
		    		   PlayPile[i][12] = R7C12;
		    		   PlayPile[i][13] = R7C13;
		    		   PlayPile[i][14] = R7C14;
		    		   PlayPile[i][15] = R7C15;
		    		   PlayPile[i][16] = R7C16;
		    		   PlayPile[i][17] = R7C17;
		    		   break;
		    	   }
		    	   case(7):
		    	   {
		    		   PlayPile[i][0] = R8C0;
		    		   PlayPile[i][1] = R8C1;
		    		   PlayPile[i][2] = R8C2;
		    		   PlayPile[i][3] = R8C3;
		    		   PlayPile[i][4] = R8C4;
		    		   PlayPile[i][5] = R8C5;
		    		   PlayPile[i][6] = R8C6;
		    		   PlayPile[i][7] = R8C7;
		    		   PlayPile[i][8] = R8C8;
		    		   PlayPile[i][9] = R8C9;
		    		   PlayPile[i][10] = R8C10;
		    		   PlayPile[i][11] = R8C11;
		    		   PlayPile[i][12] = R8C12;
		    		   PlayPile[i][13] = R8C13;
		    		   PlayPile[i][14] = R8C14;
		    		   PlayPile[i][15] = R8C15;
		    		   PlayPile[i][16] = R8C16;
		    		   PlayPile[i][17] = R8C17;
		    		   break;
		    	   }
		    	   default:
		    	   {
		    		   
		    	   }
	    	   }
	       }
	       
	    }


	    //TODO set button actions
	    private void UndoBtnActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	Engine.undo();
	    }

	    private void RedoBtnActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	Engine.redo();
	    }

		private void HintBtnActionPerformed(java.awt.event.ActionEvent evt)
		{
			Engine.hint();
		}

		private void SolveBtnActionPerformed(java.awt.event.ActionEvent evt)
		{
			Engine.solve();
		}

	    private void NewDealBtnActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	Engine.newDeal();
		}

	    /**
	     * Toggles debug state.
	     * Allows for debug statements to logger.
	     * Prints to console.
	     */
	    public static void toogleDebug() {
	    	debug = !debug;
	    }

	    /**
	     * @param args the command line arguments
	     */
	    public void start() {

	    	if(debug)
	    	{
	    	out.println("\n---client.gui.FreeGUI.start---");
	    	}

	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(FreeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(FreeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(FreeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(FreeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new FreeGUI().setVisible(true);
	            }
	        });
	    }

	    private javax.swing.JLabel Background;
	    private javax.swing.JLayeredPane BackgroundPan;
	    private javax.swing.JButton HintBtn;
	    private javax.swing.JLabel InfoBackground;
	    private javax.swing.JPanel MenuPanel;
	    private javax.swing.JButton NewDealBtn;
	    private static javax.swing.JLabel Output;
	    private static javax.swing.JLabel R1C0;
	    private static javax.swing.JLabel R1C1;
	    private static javax.swing.JLabel R1C10;
	    private static javax.swing.JLabel R1C11;
	    private static javax.swing.JLabel R1C12;
	    private static javax.swing.JLabel R1C13;
	    private static javax.swing.JLabel R1C14;
	    private static javax.swing.JLabel R1C15;
	    private static javax.swing.JLabel R1C16;
	    private static javax.swing.JLabel R1C17;
	    private static javax.swing.JLabel R1C18;
	    private static javax.swing.JLabel R1C2;
	    private static javax.swing.JLabel R1C3;
	    private static javax.swing.JLabel R1C4;
	    private static javax.swing.JLabel R1C5;
	    private static javax.swing.JLabel R1C6;
	    private static javax.swing.JLabel R1C7;
	    private static javax.swing.JLabel R1C8;
	    private static javax.swing.JLabel R1C9;
	    private static javax.swing.JLabel R2C0;
	    private static javax.swing.JLabel R2C1;
	    private static javax.swing.JLabel R2C10;
	    private static javax.swing.JLabel R2C11;
	    private static javax.swing.JLabel R2C12;
	    private static javax.swing.JLabel R2C13;
	    private static javax.swing.JLabel R2C14;
	    private static javax.swing.JLabel R2C15;
	    private static javax.swing.JLabel R2C16;
	    private static javax.swing.JLabel R2C17;
	    private static javax.swing.JLabel R2C18;
	    private static javax.swing.JLabel R2C2;
	    private static javax.swing.JLabel R2C3;
	    private static javax.swing.JLabel R2C4;
	    private static javax.swing.JLabel R2C5;
	    private static javax.swing.JLabel R2C6;
	    private static javax.swing.JLabel R2C7;
	    private static javax.swing.JLabel R2C8;
	    private static javax.swing.JLabel R2C9;
	    private static javax.swing.JLabel R3C0;
	    private static javax.swing.JLabel R3C1;
	    private static javax.swing.JLabel R3C10;
	    private static javax.swing.JLabel R3C11;
	    private static javax.swing.JLabel R3C12;
	    private static javax.swing.JLabel R3C13;
	    private static javax.swing.JLabel R3C14;
	    private static javax.swing.JLabel R3C15;
	    private static javax.swing.JLabel R3C16;
	    private static javax.swing.JLabel R3C17;
	    private static javax.swing.JLabel R3C18;
	    private static javax.swing.JLabel R3C2;
	    private static javax.swing.JLabel R3C3;
	    private static javax.swing.JLabel R3C4;
	    private static javax.swing.JLabel R3C5;
	    private static javax.swing.JLabel R3C6;
	    private static javax.swing.JLabel R3C7;
	    private static javax.swing.JLabel R3C8;
	    private static javax.swing.JLabel R3C9;
	    private static javax.swing.JLabel R4C0;
	    private static javax.swing.JLabel R4C1;
	    private static javax.swing.JLabel R4C10;
	    private static javax.swing.JLabel R4C11;
	    private static javax.swing.JLabel R4C12;
	    private static javax.swing.JLabel R4C13;
	    private static javax.swing.JLabel R4C14;
	    private static javax.swing.JLabel R4C15;
	    private static javax.swing.JLabel R4C16;
	    private static javax.swing.JLabel R4C17;
	    private static javax.swing.JLabel R4C18;
	    private static javax.swing.JLabel R4C2;
	    private static javax.swing.JLabel R4C3;
	    private static javax.swing.JLabel R4C4;
	    private static javax.swing.JLabel R4C5;
	    private static javax.swing.JLabel R4C6;
	    private static javax.swing.JLabel R4C7;
	    private static javax.swing.JLabel R4C8;
	    private static javax.swing.JLabel R4C9;
	    private static javax.swing.JLabel R5C0;
	    private static javax.swing.JLabel R5C1;
	    private static javax.swing.JLabel R5C10;
	    private static javax.swing.JLabel R5C11;
	    private static javax.swing.JLabel R5C12;
	    private static javax.swing.JLabel R5C13;
	    private static javax.swing.JLabel R5C14;
	    private static javax.swing.JLabel R5C15;
	    private static javax.swing.JLabel R5C16;
	    private static javax.swing.JLabel R5C17;
	    private static javax.swing.JLabel R5C18;
	    private static javax.swing.JLabel R5C2;
	    private static javax.swing.JLabel R5C3;
	    private static javax.swing.JLabel R5C4;
	    private static javax.swing.JLabel R5C5;
	    private static javax.swing.JLabel R5C6;
	    private static javax.swing.JLabel R5C7;
	    private static javax.swing.JLabel R5C8;
	    private static javax.swing.JLabel R5C9;
	    private static javax.swing.JLabel R6C0;
	    private static javax.swing.JLabel R6C1;
	    private static javax.swing.JLabel R6C10;
	    private static javax.swing.JLabel R6C11;
	    private static javax.swing.JLabel R6C12;
	    private static javax.swing.JLabel R6C13;
	    private static javax.swing.JLabel R6C14;
	    private static javax.swing.JLabel R6C15;
	    private static javax.swing.JLabel R6C16;
	    private static javax.swing.JLabel R6C17;
	    private static javax.swing.JLabel R6C18;
	    private static javax.swing.JLabel R6C2;
	    private static javax.swing.JLabel R6C3;
	    private static javax.swing.JLabel R6C4;
	    private static javax.swing.JLabel R6C5;
	    private static javax.swing.JLabel R6C6;
	    private static javax.swing.JLabel R6C7;
	    private static javax.swing.JLabel R6C8;
	    private static javax.swing.JLabel R6C9;
	    private static javax.swing.JLabel R7C0;
	    private static javax.swing.JLabel R7C1;
	    private static javax.swing.JLabel R7C10;
	    private static javax.swing.JLabel R7C11;
	    private static javax.swing.JLabel R7C12;
	    private static javax.swing.JLabel R7C13;
	    private static javax.swing.JLabel R7C14;
	    private static javax.swing.JLabel R7C15;
	    private static javax.swing.JLabel R7C16;
	    private static javax.swing.JLabel R7C17;
	    private static javax.swing.JLabel R7C18;
	    private static javax.swing.JLabel R7C2;
	    private static javax.swing.JLabel R7C3;
	    private static javax.swing.JLabel R7C4;
	    private static javax.swing.JLabel R7C5;
	    private static javax.swing.JLabel R7C6;
	    private static javax.swing.JLabel R7C7;
	    private static javax.swing.JLabel R7C8;
	    private static javax.swing.JLabel R7C9;
	    private static javax.swing.JLabel R8C0;
	    private static javax.swing.JLabel R8C1;
	    private static javax.swing.JLabel R8C10;
	    private static javax.swing.JLabel R8C11;
	    private static javax.swing.JLabel R8C12;
	    private static javax.swing.JLabel R8C13;
	    private static javax.swing.JLabel R8C14;
	    private static javax.swing.JLabel R8C15;
	    private static javax.swing.JLabel R8C16;
	    private static javax.swing.JLabel R8C17;
	    private static javax.swing.JLabel R8C18;
	    private static javax.swing.JLabel R8C2;
	    private static javax.swing.JLabel R8C3;
	    private static javax.swing.JLabel R8C4;
	    private static javax.swing.JLabel R8C5;
	    private static javax.swing.JLabel R8C6;
	    private static javax.swing.JLabel R8C7;
	    private static javax.swing.JLabel R8C8;
	    private static javax.swing.JLabel R8C9;
	    private static javax.swing.JButton RedoBtn;
	    private static javax.swing.JLabel Solvable;
	    private static javax.swing.JLabel SolvableLable;
	    private static javax.swing.JButton SolveBtn;
	    private static javax.swing.JLabel Time;
	    private static javax.swing.JLabel TimeLabel;
	    private static javax.swing.JLabel Turn;
	    private static javax.swing.JLabel TurnLabel;
	    private static javax.swing.JButton UndoBtn;
	    private static javax.swing.JLabel free1;
	    private static javax.swing.JLabel free2;
	    private static javax.swing.JLabel free3;
	    private static javax.swing.JLabel free4;
	    private static javax.swing.JLabel home1;
	    private static javax.swing.JLabel home2;
	    private static javax.swing.JLabel home3;
	    private static javax.swing.JLabel home4;
	    private javax.swing.JLayeredPane jLayeredPane1;
	    
	    private static JLabel fcAry[] = new JLabel[NUMCELLS];
	    private static JLabel hcAry[] = new JLabel[NUMCELLS];
	    private static JLabel PlayPile[][] = new JLabel[NUMPILES][MAX_PILE_SIZE];
	    
	    private static final int colX[] = {103,263,413,563,713,863,1013,1163};
	    private static final int colY[] = {212,232,252,272,292,312,332,352,372,392,412,432,452,472,492,512,532,552,572};
	    private static final int FreeX[] = {63,163,263,363};
	    private static final int HomeX[] = {912,1012,1112,1212};
	    private static final int FH_Y_CONST = 72;

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if(debug)
			{
				out.println("Mouse dragged at: (" + e.getX() + "," + e.getY() + ")");
			}
			//TODO show any cards in the hand at the mouse pointer
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			// Not Used
		}

		@Override
		public void mouseClicked(MouseEvent e) 
		{

		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			if(debug)
			{
				out.println("Mouse pressed at: (" + e.getX() + "," + e.getY() + ")");
			}
			
			int x = e.getX();
			int y = e.getY();
			String key = "";
			
			if(y >= FH_Y_CONST - 63 && y <= FH_Y_CONST + 63)
			{
				for(int i = 0,len = FreeX.length;i < len;i++)
				{
					if(x >= (FreeX[i] - 43) && x <= (FreeX[i] + 43))
					{
						FreeCell[] fcells = ShownBoard.getFreecells();
						if(!fcells[i].isValid())
						{
							if(debug)
							{
								out.println("Filled Freecell number " + i);
							}
							//TODO add it to the players hand
						}
						else
						{
							if(debug) out.println("Empty freecell number " + i);
						}
						key = fcells[i].getKey().getKeyString();
					}
				}
			}
			else
			{
				for(int i = 0,len = colX.length;i < len; i++)
				{
					if(x <= colX[i] + 43 && x >= colX[i] - 43)
					{
						for(int j = colY.length-1; j >= 0; j--)
						{
							if(y <= colY[j] + 63 && y >= colY[j] - 63)
							{
								PlayingPile column = ShownBoard.getPile(i);
								if(debug)
								{
									if(column.size() > j)
									{
									out.println("Column :" + i + " Card :" + j);
									break;
									}
								}
								key = column.getKey().getKeyString();
								//TODO Check card and cards on top and check to see if they can be picked up.
							}
						}
					}
				}
			}
			
			if (e.getClickCount() == 2) Engine.doubleClick(key);
			else Engine.setSource(key);
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			if(debug)
			{
				out.println("Mouse released at: (" + e.getX() + "," + e.getY() + ")");
			}
			
			int x = e.getX();
			int y = e.getY();
			String key = "";
			
			if(y >= FH_Y_CONST - 63 && y <= FH_Y_CONST + 63)
			{
				for(int i = 0,len = FreeX.length;i < len;i++)
				{
					if(x >= (FreeX[i] - 43) && x <= (FreeX[i] + 43))
					{
						FreeCell[] fcells = ShownBoard.getFreecells();
						if(!fcells[i].isValid())
						{
							if(debug)
							{
								out.println("Filled Freecell number " + i);
							}
							//TODO add it to the players hand
						}
						else
						{
							if(debug) out.println("Empty freecell number " + i);
						}
						key = fcells[i].getKey().getKeyString();
					}
				}
			}
			else
			{
				for(int i = 0,len = colX.length;i < len; i++)
				{
					if(x <= colX[i] + 43 && x >= colX[i] - 43)
					{
						for(int j = colY.length-1; j >= 0; j--)
						{
							if(y <= colY[j] + 63 && y >= colY[j] - 63)
							{
								PlayingPile column = ShownBoard.getPile(i);
								if(debug)
								{
									if(column.size() > j)
									{
									out.println("Column :" + i + " Card :" + j);
									break;
									}
								}
								key = column.getKey().getKeyString();
								//TODO Check card and cards on top and check to see if they can be picked up.
							}
						}
					}
				}
			}
			
			Engine.setDest(key);
			//TODO if the hand has cards, check to see if the cards can go where the hand was released, if they can not, return them to where they were.
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			// Not Used
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			// Not Used
		}
		
		
}
