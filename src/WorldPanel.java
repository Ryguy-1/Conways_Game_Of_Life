import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.omg.Messaging.SyncScopeHelper;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private int cellsPerRow;
	private int cellSize;
	private Cell[][] cells;
	private Timer timer;
	
	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;
		//calculate the cellSize
		cellSize=ConwaysGameOfLife.WIDTH/cpr;
		System.out.println(cellSize);
		//initialize the cells array
		cells = new Cell[cpr][cpr];
		
		//initialize each cell in the array
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j]=new Cell(i,j,cellSize);
			}
		}
	}
	
	public void randomizeCells() {
		System.out.println("randomize");
		// make each cell alive or dead randomly
		Random randy = new Random();
		int random = 0;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				random = randy.nextInt(2);
				if(random==1) {
					cells[i][j].isAlive=true;
				}else {
				cells[i][j].isAlive=false;
				}
			}
		}
		int T = 0;
		int F = 0;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if(cells[i][j].isAlive == true) {
					T+=1;
				}else if(cells[i][j].isAlive == false) {
					F+=1;
				}
			}
		}
		System.out.println("F = " + F);
		System.out.println("T = " + T);
		System.out.println(350*350);
		repaint();
	}
	
	public void clearCells() {
		// set isAlive to false for all cells
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].isAlive=false;
			}
		}
		repaint();
	}
	
	public void startAnimation() {
		timer.start();
	}
	
	public void stopAnimation() {
		timer.stop();
	}
	
	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}
	


	public void paintComponent(Graphics g) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j].draw(g);
			}
		}
	}
	
	//advances world one step
	public void step() {
		//initialize the numLivingNbors variable to be the same size as the cells
		int[][] numLivingNbors;
		numLivingNbors=new int[cellsPerRow][cellsPerRow];
		//iterate through the cells and populate the numLivingNbors array with their neighbors
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				numLivingNbors[i][j]=getLivingNeighbors(i,j);
			}
		}
		
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].liveOrDie(numLivingNbors[i][j]);
			}
		}
		
		repaint();
	}
	
	//returns an array list of the  8 or less neighbors of the 
	//cell identified by x and y
	public int getLivingNeighbors(int x, int y){
		int livingNeighbors = 0;
		
		if(x!=0) {
			if(cells[x-1][y].isAlive) livingNeighbors+=1;
		}
		if(y!=0) {
			if(cells[x][y-1].isAlive) livingNeighbors+=1;
		}
		if(x!=cellsPerRow-1) {
			if(cells[x+1][y].isAlive) livingNeighbors+=1;
		}
		if(y!=cellsPerRow-1) {
			if(cells[x][y+1].isAlive) livingNeighbors+=1;
		}
		if(x!=0 && y!=0) {
			if(cells[x-1][y-1].isAlive) livingNeighbors+=1;
		}
		if(x!=cellsPerRow-1&&y!=0) {
			if(cells[x+1][y-1].isAlive) livingNeighbors+=1;
		}
		if(x!=cellsPerRow-1&&y!=cellsPerRow-1) {
			if(cells[x+1][y+1].isAlive) livingNeighbors+=1;
		}
		if(x!=0&&y!=cellsPerRow-1) {
			if(cells[x-1][y+1].isAlive) livingNeighbors+=1;
		}
		
		
//		if(x==0) {
//			if(cells[x][y].isAlive==true) {
//				livingNeighbors+=1;
//			}
//			else if(cells[x][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}
//		}else if(y==0) {
//			if(cells[x][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}
//		}else if(x==ConwaysGameOfLife.WIDTH) {
//			if(cells[x][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x][y+1].isAlive==true) {
//				livingNeighbors+=1;
//			}
//		}else if(y==ConwaysGameOfLife.HEIGHT) {
//			if(cells[x][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x-1][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y-1].isAlive==true) {
//				livingNeighbors+=1;
//			}else if(cells[x+1][y].isAlive==true) {
//				livingNeighbors+=1;
//			}
//		}else {
//		if(cells[x-1][y-1].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x][y-1].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x+1][y-1].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x-1][y].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x][y].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x+1][y].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x-1][y+1].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x][y+1].isAlive==true) {
//			livingNeighbors+=1;
//		}else if(cells[x+1][y+1].isAlive==true) {
//			livingNeighbors+=1;
//		}
//		}
		
		
		//add 1 to livingNeighbors for each
		//neighboring cell that is alive
		
		return livingNeighbors;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// IGNORE
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// IGNORE
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//get the location of the mouse
		int x = e.getX()/(ConwaysGameOfLife.WIDTH/cellsPerRow);
		int y = e.getY()/(ConwaysGameOfLife.HEIGHT/cellsPerRow);
		
		//toggle the cell at that location to either alive or dead
		if(cells[x][y].isAlive) cells[x][y].isAlive=false;
		else cells[x][y].isAlive=true;
		//based on its current state
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// IGNORE
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action Performed");
		step();		
		
	}
}
