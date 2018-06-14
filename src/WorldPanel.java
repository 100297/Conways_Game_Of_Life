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

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	
	int cellsPerRow;
	private int cellSize;
	private Cell[][] cells;
	private Timer timer;

	public WorldPanel(int w, int h, int cpr) {

		// initialize the cells array
		
		this.cellsPerRow = cpr;
		cells = new Cell[cellsPerRow][cellsPerRow];
		cellSize = h / cpr;
		System.out.println(cellSize);
		// initialize each cell in the array
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(i*cellSize, j*cellSize, cellSize);
			}

		}

		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		
	}

	public void randomizeCells() {
		Random ran = new Random();
		System.out.println("tesdgcvb");
		// if(cells[0] == null) {
		// cells = new Cell[ConwaysGameOfLife.WIDTH][ConwaysGameOfLife.HEIGHT];
		//
		// //initialize each cell in the array
		// for (int i = 0; i < ConwaysGameOfLife.CELLS_PER_ROW; i++) {
		// for (int j = 0; j < ConwaysGameOfLife.CELLS_PER_ROW; j++) {
		// cells[i][j] = new Cell(i,j,cellSize);
		// }
		//
		// }
		// }

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {

				boolean s = ran.nextBoolean();
				if (s) {
					cells[i][j].isAlive = true;

				} else {
					cells[i][j].isAlive = false;
				}
			}

		}

		// make each cell alive or dead randomly
		repaint();
	}

	public void clearCells() {
		// set isAlive to false for all cells
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].isAlive = false;
			}

		}
		repaint();
	}

	public void startAnimation() {
		timer.start();
		System.out.println("Started");
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j] != null) {

					cells[i][j].draw(g);
				}
			}

		}
		// iterate through the cells and draw them
	}

	// advances world one step
	public void step() {
		// initialize the numLivingNbors variable to be the same size as the cells
		
		int[][] numLivingNbors;
		numLivingNbors = new int[cells.length][cells[0].length];

		// iterate through the cells and populate the numLivingNbors array with their
		// neighbors
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				numLivingNbors[i][j] = getLivingNeighbors(i, j);
			}

		}

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j].liveOrDie(numLivingNbors[i][j]);
			}

		}

		repaint();
	}

	// returns an array list of the 8 or less neighbors of the
	// cell identified by x and y
	public int getLivingNeighbors(int x, int y) {

		int livingNeighbors = 0;

		if (x > 0 && y > 0) {

			if (cells[x - 1][y - 1].isAlive == true) {
				livingNeighbors++;
			}
		}
		if (y > 0) {

			if (cells[x][y - 1].isAlive == true) {
				livingNeighbors++;
			}
		}
		if (x > 0) {

			if (cells[x - 1][y].isAlive == true) {
				livingNeighbors++;
			}
		}
		if (y > 0 && x < cells.length - 1) {
			if (cells[x + 1][y - 1].isAlive == true) {
				livingNeighbors++;
			}
		}
		if (x > 0 && y < cells.length - 1) {
			if (cells[x - 1][y + 1].isAlive == true) {
				livingNeighbors++;
			}
		}
		if (x < cells.length - 1 && y <cells.length - 1) {
			if (cells[x + 1][y + 1].isAlive == true) {
				livingNeighbors++;
			}
			if (y < cells.length - 1) {
				if (cells[x][y + 1].isAlive == true) {
					livingNeighbors++;
				}
			}
			if (x < cells.length - 1) {
				if (cells[x + 1][y].isAlive == true) {
					livingNeighbors++;
				}
			}
			// add 1 to livingNeighbors for each
			// neighboring cell that is alive

		}
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
		if (cells[e.getX()/cellSize][e.getY()/cellSize].isAlive) {
			cells[e.getX()/cellSize][e.getY()/cellSize].isAlive = false;
		} else {
			cells[e.getX()/cellSize][e.getY()/cellSize].isAlive = true;
		}

		// get the location of the mouse

		// toggle the cell at that location to either alive or dead
		// based on its current state
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// IGNORE

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
}
