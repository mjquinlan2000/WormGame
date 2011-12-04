package com.quinlan;

import java.awt.Color;

public class Cell {
	private int x;
	private int y;
	private int width;
	private int height;
	public static enum direction {UP, DOWN, LEFT, RIGHT};
	private Color backgroundColor;
	private Color pieceColor;
	boolean pieceVisible = false;
	
	
	public Cell(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(Color pieceColor) {
		this.pieceColor = pieceColor;
	}

	public boolean isPieceVisible() {
		return pieceVisible;
	}

	public void setPieceVisible(boolean pieceVisible) {
		this.pieceVisible = pieceVisible;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}	
	
}