package com.quinlan;

public class Cell {
	public static enum direction {UP, DOWN, LEFT, RIGHT};
	boolean pieceVisible = false;
	boolean border = true;
	boolean food;
	private int x, y;
	private Cell next = null;
	private Cell prev = null;
	
	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public boolean isPieceVisible() {
		return pieceVisible;
	}

	public void setPieceVisible(boolean pieceVisible) {
		this.pieceVisible = pieceVisible;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Cell getNext() {
		return next;
	}

	public void setNext(Cell next) {
		this.next = next;
	}

	public Cell getPrev() {
		return prev;
	}

	public void setPrev(Cell prev) {
		this.prev = prev;
	}

	public boolean isFood() {
		return food;
	}

	public void setFood(boolean food) {
		this.food = food;
	}
	
}