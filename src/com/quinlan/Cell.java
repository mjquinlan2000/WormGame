package com.quinlan;

public class Cell {
	public static enum direction {UP, DOWN, LEFT, RIGHT};
	boolean pieceVisible = false;
	boolean border = true;
	boolean first;
	boolean last;

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

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}	
	
}