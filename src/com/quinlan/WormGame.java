package com.quinlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class WormGame extends JComponent implements ActionListener {
    Color startColor = Color.GRAY;  // where we start
    Color endColor = Color.BLACK;         // where we end
    Color currentColor = startColor;
    int animationDuration = 2000;   // each animation will take 2 seconds
    long animStartTime;     // start time for each animation
    public static enum Direction {RIGHT, LEFT, DOWN, UP};
    private static Direction currentDirection = Direction.RIGHT;
    private Cell[][] board = new Cell[80][60];
    private static double cellHeight;
    private static double cellWidth;
    private Color borderColor=Color.BLACK;
    private Color boardColor=Color.BLUE;
    private Color pieceColor = Color.GREEN;
    private Color foodColor = Color.RED;
    private Cell wormHead;
    private Cell wormTail;
    private Cell food;
    private Timer timer;
    private static int wormSize;
    
    public WormGame() {
    	initBoard();
    	initWorm();
    	
        timer = new Timer(100, this);
        // initial delay while window gets set up
        timer.setInitialDelay(1000);
        animStartTime = 1000 + System.nanoTime() / 1000000;
        timer.start();
    }
    
    private void initBoard()
    {
    	for(int i = 0; i < 80; i++)
    	{
    		for(int j = 0; j < 60; j++)
    		{
    			Cell tmpCell = new Cell(i, j);
    			tmpCell.setPieceVisible(false);
    			tmpCell.setFood(false);
    			if(i == 0 || i == 79 || j == 0 || j == 59)
    			{
    				tmpCell.setBorder(true);
    			}else
    			{
    				tmpCell.setBorder(false);
    			}
    			board[i][j] = tmpCell;
    		}
    	}
    }
    
    public void initWorm()
    {
    	for(int i = 40; i > 35; i--)
    	{
    		board[i][30].setPieceVisible(true);
    		if(i != 40)
    		{
    			board[i][30].setNext(board[i+1][30]);
    		}
    		
    		if(i != 36)
    		{
    			board[i][30].setPrev(board[i-1][30]);
    		}
    	}
    	wormHead = board[40][30];
    	wormTail = board[36][30];
    	wormSize = 5;
    }

    public void paintComponent(Graphics g) {
    	cellHeight = (double)getHeight()/60.0;
    	cellWidth = (double)getWidth()/80.0;
        g.setColor(this.borderColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(boardColor);
        g.fillRect((int)cellHeight, (int)cellWidth, getWidth() - 2*(int)cellWidth, getHeight() - 2*(int)cellHeight);
        g.setColor(pieceColor);
        for(int i = 0; i < 80; i++)
        {
        	for(int j = 0; j < 60; j++)
        	{
        		if(board[i][j].isPieceVisible())
        		g.fillOval((int)(cellWidth*i), (int)(cellHeight*j), (int)cellWidth, (int)cellHeight);
        	}
        }
//        g.setColor(currentColor);
//        g.fillOval(0, 0, getWidth(), getHeight());
    }
    
    public void actionPerformed(ActionEvent ae) {
        // calculate elapsed fraction of animation
        long currentTime = System.nanoTime() / 1000000;
        long totalTime = currentTime - animStartTime;
        if (totalTime > animationDuration) {
            animStartTime = currentTime;
        }
        
        int x = wormHead.getX();
    	int y = wormHead.getY();
        
        switch (currentDirection)
        {
        case RIGHT:
        	wormHead.setNext(board[x + 1][y]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x+1][y];
        	wormHead.setPieceVisible(true);
        	if(!wormHead.isFood())
        	{
        		wormTail.setPieceVisible(false);
        		wormTail = wormTail.getNext();
        		wormTail.getPrev().setNext(null);
        		wormTail.setPrev(null);
        	}else{
        		wormSize++;
        	}
        	break;
        case LEFT:
        	wormHead.setNext(board[x - 1][y]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x-1][y];
        	wormHead.setPieceVisible(true);
        	if(!wormHead.isFood())
        	{
        		wormTail.setPieceVisible(false);
        		wormTail = wormTail.getNext();
        		wormTail.getPrev().setNext(null);
        		wormTail.setPrev(null);
        	}else{
        		wormSize++;
        	}
        	break;
        case UP:
        	wormHead.setNext(board[x][y - 1]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x][y-1];
        	wormHead.setPieceVisible(true);
        	if(!wormHead.isFood())
        	{
        		wormTail.setPieceVisible(false);
        		wormTail = wormTail.getNext();
        		wormTail.getPrev().setNext(null);
        		wormTail.setPrev(null);
        	}else{
        		wormSize++;
        	}
        	break;
        case DOWN:
        	wormHead.setNext(board[x][y+1]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x][y+1];
        	wormHead.setPieceVisible(true);
        	if(!wormHead.isFood())
        	{
        		wormTail.setPieceVisible(false);
        		wormTail = wormTail.getNext();
        		wormTail.getPrev().setNext(null);
        		wormTail.setPrev(null);
        	}else{
        		wormSize++;
        	}
        	break;
        }
        // force a repaint to display our oval with its new color
        repaint();
    }
    
    private static void createAndShowGUI() {    
        JFrame f = new JFrame();
        f.setTitle("Worm Game");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = (int)screen.getWidth()/2 - 400;
		int h = (int)screen.getHeight()/2 - 300;
		f.setSize(800, 600);
		f.setLocation(w, h);
		f.setResizable(false);
		
		f.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent key) {
				switch(key.getKeyCode())
				{
				case KeyEvent.VK_UP:
					if(currentDirection != Direction.DOWN)
						currentDirection = Direction.UP;
					break;
				case KeyEvent.VK_DOWN:
					if(currentDirection != Direction.UP)
						currentDirection = Direction.DOWN;
					break;
				case KeyEvent.VK_RIGHT:
					if(currentDirection != Direction.LEFT)
						currentDirection = Direction.RIGHT;
					break;
				case KeyEvent.VK_LEFT:
					if(currentDirection != Direction.RIGHT)
						currentDirection = Direction.LEFT;
					break;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {/*nop*/}

			@Override
			public void keyTyped(KeyEvent arg0) {/*nop*/}
        	
        });
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new WormGame());
        f.setVisible(true);
    }
    
    public static void main(String args[]) {
        Runnable doCreateAndShowGUI = new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
    
    public void makeFood()
    {
//    	int randX = 
    }
}