package com.quinlan;

import java.awt.Button;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	private JLabel score;
	private JLabel infoLabel;
	private Button restartButton;
	private Font generalFont;
	private GridLayout layout;
	
	public ScorePanel()
	{
		layout = new GridLayout(1, 0);
		generalFont = new Font(Font.SANS_SERIF, Font.BOLD, 23);
		infoLabel = new JLabel("Score:  ");
		score = new JLabel("0");
		infoLabel.setFont(generalFont);
		score.setFont(generalFont);
		restartButton = new Button("Restart Game");
		restartButton.setFocusable(false);
		infoLabel.setFocusable(false);
		score.setFocusable(false);
		this.setLayout(layout);
		this.add(restartButton);
		this.add(infoLabel);
		this.add(score);
		this.setFocusable(false);
	}

	public JLabel getScore() {
		return score;
	}

	public void setScore(JLabel score) {
		this.score = score;
	}

	public Button getRestartButton() {
		return restartButton;
	}

	public void setRestartButton(Button restartButton) {
		this.restartButton = restartButton;
	}

	public GridLayout getLayout() {
		return layout;
	}

	public void setLayout(GridLayout layout) {
		this.layout = layout;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}
	
	
}
