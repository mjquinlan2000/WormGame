package com.quinlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class WormGame extends JFrame implements Runnable{
	public static int rightLeft = 0;
	public static int upDown = 0;
	public Thread thisThread = new Thread(this);
	
	public WormGame()
	{
//		this.setSize(new Dimension(500, 500));
		this.setTitle("Worm Game");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int w = (int)screen.getWidth()/2 - 250;
		int h = (int)screen.getHeight()/2 - 250;
		this.setSize(500, 500);
		this.setLocation(w, h);
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch(arg0.getKeyCode())
				{
				case KeyEvent.VK_DOWN:
					System.out.println("Down Pressed");
					upDown -= 5;
					break;
				case KeyEvent.VK_UP:
					System.out.println("Up Pressed");
					upDown += 5;
					break;
				case KeyEvent.VK_LEFT:
					System.out.println("Left Pressed" );
					rightLeft -= 5;
					break;
				case KeyEvent.VK_RIGHT:
					System.out.println("Right Pressed");
					rightLeft += 5;
					break;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
		});
		thisThread.start();
	}
	
	public static void main(String[] args)
	{
		WormGame wg = new WormGame();
		wg.setVisible(true);
		wg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
//		g2.fillRect(0, 10, 100, 100);
		g2.drawRect(100, 100, 100, 100);
		
		g2.translate(upDown, rightLeft);
		
	}

	@Override
	public void run() {
		while(true)
		{
			this.paint(new Dot(0, 0, 0));
			System.out.println("HERE");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}