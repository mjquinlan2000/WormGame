package com.quinlan;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

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
    private Cell[][] board = new Cell[CELLSX][CELLSY];
    private static double cellHeight;
    private static double cellWidth;
    private Color borderColor=Color.BLACK;
    private Color boardColor=Color.BLUE;
    private Color pieceColor = Color.GREEN;
    private Color foodColor = Color.RED;
    private Cell wormHead;
    private Cell wormTail;
    private Timer timer;
    private static int CELLSX = 20;
    private static int CELLSY = 15;
    private static ScorePanel scorePanel;
    public static int score = 0;
    private static final int DELAY = 150;
    private static int delayOffset = 0;
    private static Button resetButton;
    
    public WormGame() {
    	initBoard();
    	initWorm();
    	makeFood();
    	
        timer = new Timer(DELAY, this);
        // initial delay while window gets set up
        timer.setInitialDelay(1000);
        animStartTime = 1000 + System.nanoTime() / 1000000;
        timer.start();
    }
    
    private void initBoard()
    {
    	for(int i = 0; i < CELLSX; i++)
    	{
    		for(int j = 0; j < CELLSY; j++)
    		{
    			Cell tmpCell = new Cell(i, j);
    			tmpCell.setPieceVisible(false);
    			tmpCell.setFood(false);
    			if(i == 0 || i == CELLSX - 1 || j == 0 || j == CELLSY-1)
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
    	int start = CELLSX/2;
    	for(int i = start; i > start - 5 ; i--)
    	{
    		board[i][CELLSY/2].setPieceVisible(true);
    		if(i != start)
    		{
    			board[i][CELLSY/2].setNext(board[i+1][CELLSY/2]);
    		}
    		
    		if(i != start - 4)
    		{
    			board[i][CELLSY/2].setPrev(board[i-1][CELLSY/2]);
    		}
    	}
    	wormHead = board[start][CELLSY/2];
    	wormTail = board[start - 4][CELLSY/2];
    }

    public void paintComponent(Graphics g) {
    	cellHeight = (double)getHeight()/(double)CELLSY;
    	cellWidth = (double)getWidth()/(double)CELLSX;
        g.setColor(borderColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(boardColor);
        g.fillRect((int)cellHeight, (int)cellWidth, getWidth() - (int)(2*cellWidth), getHeight() - (int)(2*cellHeight));
        g.setColor(pieceColor);
        for(int i = 0; i < CELLSX; i++)
        {
        	for(int j = 0; j < CELLSY; j++)
        	{
        		if(board[i][j].isPieceVisible())
        			g.fillOval((int)(cellWidth*i), (int)(cellHeight*j), (int)cellWidth, (int)cellHeight);
        		
        		if(board[i][j].isFood())
        		{
        			g.setColor(foodColor);
        			g.fillOval((int)(cellWidth*i), (int)(cellHeight*j), (int)cellWidth, (int)cellHeight);
        			g.setColor(pieceColor);
        		}
        	}
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        int x = wormHead.getX();
    	int y = wormHead.getY();
        
        switch (currentDirection)
        {
        case RIGHT:
        	wormHead.setNext(board[x + 1][y]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x+1][y];
        	break;
        case LEFT:
        	wormHead.setNext(board[x - 1][y]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x-1][y];
        	break;
        case UP:
        	wormHead.setNext(board[x][y - 1]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x][y-1];
        	break;
        case DOWN:
        	wormHead.setNext(board[x][y+1]);
        	wormHead.getNext().setPrev(wormHead);
        	wormHead = board[x][y+1];
        	break;
        }
        
        if(!wormHead.isFood())
    	{
    		wormTail.setPieceVisible(false);
    		wormTail = wormTail.getNext();
    		wormTail.getPrev().setNext(null);
    		wormTail.setPrev(null);
    	}else{
    		score++;
    		delayOffset++;
    		int newDelay = DELAY - 2*delayOffset;
    		if(newDelay > 70)
    			timer.setDelay(newDelay);
    		scorePanel.getScore().setText(String.valueOf(score));
    		scorePanel.repaint();
    		wormHead.setFood(false);
    		makeFood();
    	}

        if(wormHead.isPieceVisible())
        {
        	scorePanel.getInfoLabel().setText("You ate yourself!! Score:  ");
        	scorePanel.repaint();
        	timer.stop();
        }
        
        if(wormHead.isBorder())
        {
        	scorePanel.getInfoLabel().setText("You hit a wall!! Score:  ");
        	scorePanel.repaint();
        	timer.stop();
        }
        
        wormHead.setPieceVisible(true);
        // force a repaint to display our oval with its new color
        repaint();
    }
    
    private static void createAndShowGUI() {    
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setTitle("Worm Game");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = (int)screen.getWidth()/2 - 400;
		int h = (int)screen.getHeight()/2 - 300;
		f.setSize(800, 630);
		f.setLocation(w, h);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scorePanel = new ScorePanel();
        
        f.add(scorePanel, BorderLayout.PAGE_END);
        
        final WormGame wg = new WormGame();
        
        f.add(wg, BorderLayout.CENTER);
        
        resetButton = scorePanel.getRestartButton();
        resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				wg.resetGame();
			}
        	
        });
        
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
    	Cell current;
    	
    	do{
    		Random rand1 = new Random();
    		int randX = rand1.nextInt(CELLSX-2) + 1;
    		int randY = rand1.nextInt(CELLSY-2) + 1;
    		current = board[randX][randY];
    		if(!current.isPieceVisible())
    		{
    			current.setFood(true);
    		}
    	}while(current.isPieceVisible());
    	
    	
    }
    
    public void resetGame()
    {
    	currentDirection = Direction.RIGHT;
    	score = 0;
    	scorePanel.getScore().setText("0");
    	initBoard();
    	initWorm();
    	makeFood();
    	timer.stop();
    	timer.setDelay(DELAY);
    	delayOffset = 0;
    	timer.start();
    	scorePanel.getInfoLabel().setText("Score:  ");
    	this.repaint();
    	scorePanel.repaint();
    }
}