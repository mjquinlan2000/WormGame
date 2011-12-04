package com.quinlan;

import java.awt.Color;

public class Cell {
	public static enum direction {UP, DOWN, LEFT, RIGHT};
	boolean pieceVisible = false;
	boolean border = true;

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
	
}