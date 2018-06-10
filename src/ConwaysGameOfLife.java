import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 *  1. Check out the Wikipedia page on Conway's Game of Life to familiarize yourself
 *     with the concept.
 *     
 *	https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 */

/*
 *  2. Run the ConwaysGOL.jar to see a demo of the final product.
 */

/* 
 *  3. Create the program on your own or fill in the code under the comments to complete the project.
 *
 */

public class ConwaysGameOfLife extends JPanel implements ActionListener{
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int CELLS_PER_ROW = 350;
	
	private boolean isRunning = false;

	
	private JFrame window;
	private JPanel inputPanel;
	private JButton startStopButton;
	private JButton randomizeButton;
	private JButton clearButton;
	private JLabel speedLabel;
	private JTextField speedField;
	
	private WorldPanel gamePanel;
	
	public static void main(String[] args) {
		new ConwaysGameOfLife().launchGame();
	}
	
	
	public void launchGame() {
		//build the window and start the simulation
		window = new JFrame();
		inputPanel = new JPanel();
		startStopButton = new JButton();
		startStopButton.addActionListener(this);
		randomizeButton = new JButton();
		randomizeButton.addActionListener(this);
		clearButton = new JButton();
		clearButton.addActionListener(this);
		speedLabel = new JLabel();
		speedLabel.setText("Delay: ");
		speedField = new JTextField();
		gamePanel = new WorldPanel(WIDTH, HEIGHT, CELLS_PER_ROW);
		window.add(inputPanel);
		inputPanel.add(gamePanel);
		gamePanel.setVisible(true);
		window.setVisible(true);
		inputPanel.setVisible(true);
		inputPanel.add(speedField);
		inputPanel.add(speedLabel);
		speedField.setText("60");
		speedLabel.setVisible(true);
		speedField.setVisible(true);
		inputPanel.add(startStopButton);
		startStopButton.setVisible(true);
		startStopButton.setText("Start/Stop");
		inputPanel.add(randomizeButton);
		randomizeButton.setVisible(true);
		randomizeButton.setText("Randomize");
		inputPanel.add(clearButton);
		clearButton.setVisible(true);
		clearButton.setText("Clear");
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if startStopButton is pressed, 
			// toggle isRunning to the opposite of its current state
			// start or stop the animation based on the state of isRunning
		if(e.getSource() == startStopButton){
			if(isRunning==true) {
				isRunning = false;
			}else {
				isRunning = true;
			}
		}else if(e.getSource() == randomizeButton) {
			gamePanel.randomizeCells();
		// if ranomizeButton is pressed
			// call randomizeCells
		}else if(e.getSource()==clearButton) {
			gamePanel.clearCells();
		// if clearButton is pressed
			//call clearCells
	}
	}
	}

