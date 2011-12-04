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
    Direction currentDirection = Direction.RIGHT;
    private Cell[][] board = new Cell[50][50];
    
    /**
     * Set up and start the timer
     */
    public WormGame() {
        Timer timer = new Timer(30, this);
        // initial delay while window gets set up
        timer.setInitialDelay(1000);
        animStartTime = 1000 + System.nanoTime() / 1000000;
        timer.start();
        addKeyListener(new KeyListener(){

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
    }
    
    /**
     * Erase to the background color and fill an oval with the current
     * color (which is being animated elsewhere)
     */
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLUE);
        g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
        g.setColor(currentColor);
        g.fillOval(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Callback from the Swing Timer. Calculate the fraction elapsed of
     * our desired animation duration and interpolate between our start and
     * end colors accordingly.
     */
    public void actionPerformed(ActionEvent ae) {
        // calculate elapsed fraction of animation
        long currentTime = System.nanoTime() / 1000000;
        long totalTime = currentTime - animStartTime;
        if (totalTime > animationDuration) {
            animStartTime = currentTime;
        }
        float fraction = (float)totalTime / animationDuration;
        fraction = Math.min(1.0f, fraction);
        // interpolate between start and end colors with current fraction
        int red = (int)(fraction * endColor.getRed() + 
                (1 - fraction) * startColor.getRed());
        int green = (int)(fraction * endColor.getGreen() + 
                (1 - fraction) * startColor.getGreen());
        int blue = (int)(fraction * endColor.getBlue() + 
                (1 - fraction) * startColor.getBlue());
        // set our new color appropriately
        currentColor = new Color(red, green, blue);
        // force a repaint to display our oval with its new color
        repaint();
    }
    
    private static void createAndShowGUI() {    
        JFrame f = new JFrame();
        f.setTitle("Worm Game");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = (int)screen.getWidth()/2 - 250;
		int h = (int)screen.getHeight()/2 - 250;
		f.setSize(500, 500);
		f.setLocation(w, h);
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
}