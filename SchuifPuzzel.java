import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class SchuifPuzzel extends JPanel implements MouseListener {

	public static void main(String[] args) {
		shuffle();
		createWindow();
	}

	public static void createWindow() {
		frame = new JFrame("Shuffle the day away!");
		frame.getContentPane().add(puzzel);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.addMouseListener(puzzel);
		frame.setVisible(true);
	}

	public static void shuffle() {
		ArrayList<Integer> bucket = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			bucket.add(i);
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = bucket.remove((int) (bucket.size() * (Math.random())));
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		size = (int) ((getWidth() < getHeight()) ? (getWidth() / 4) : (getHeight() / 4));
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, size - 10));

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0)
					continue;
				g.drawString(String.valueOf(grid[i][j]),
						(int) ((i) * size + .5 * size
								- .5 * g.getFontMetrics().stringWidth(String.valueOf(grid[i][j]))),
						(j + 1) * (size) - 10);
			}
		}
		for (int i = 0; i <= grid.length; i++) {
			g.drawLine(i * size, 0, i * size, (grid[0].length) * size);
		}
		for (int i = 0; i <= grid[0].length; i++) {
			g.drawLine(0, i * size, (grid.length) * size, i * size);
		}
	}

	static void applyRules() {
		if (indexX != 0 && grid[indexX - 1][indexY] == 0) {
			grid[indexX - 1][indexY] = grid[indexX][indexY];
			grid[indexX][indexY] = 0;
		}
		if (indexY != 0 && grid[indexX][indexY - 1] == 0) {
			grid[indexX][indexY - 1] = grid[indexX][indexY];
			grid[indexX][indexY] = 0;
		}
		if (indexX != (grid.length - 1) && grid[indexX + 1][indexY] == 0) {
			grid[indexX + 1][indexY] = grid[indexX][indexY];
			grid[indexX][indexY] = 0;
		}
		if (indexY != (grid[0].length - 1) && grid[indexX][indexY + 1] == 0) {
			grid[indexX][indexY + 1] = grid[indexX][indexY];
			grid[indexX][indexY] = 0;
		}
		// via deze arraylists wordt gecontroleerd of er een oplossing bereikt is
		ArrayList<Integer> check1 = new ArrayList<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				check1.add(grid[j][i]);
			}
		}
		int goodcounter = 0;
		// de nul er tussenuit halen
		for (int i = 0; i < check1.size(); i++) {
			if (check1.get(i) == 0) {
				check1.remove(i);
			}
		}
		for (int i = 0; i < check1.size() - 1; i++) {
			if (check1.get(i) < check1.get(i + 1)) {
				goodcounter++;
			} else {
				break;
			}
		}
		// second possible solution
		ArrayList<Integer> check2 = new ArrayList<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				check2.add(grid[i][j]);
			}
		}
		int goodcounter2 = 0;
		for (int i = 0; i < check2.size(); i++) {
			if (check2.get(i) == 0) {
				check2.remove(i);
			}
		}
		for (int i = 0; i < check1.size() - 1; i++) {
			if (check2.get(i) < check2.get(i + 1)) {
				goodcounter2++;
			} else {
				break;
			}
		}
		if (grid[0][0] == 0 || grid[grid.length - 1][grid[0].length - 1] == 0) {
			if (goodcounter == check1.size() - 1 || goodcounter2 == check2.size() - 1) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int result = JOptionPane.showConfirmDialog(null, "Meer?", "Lekker bezig!", dialogButton);
				if (result == JOptionPane.YES_OPTION) {
					shuffle();
				} else {
					System.exit(0);
				}
			}
		}
		puzzel.repaint();
	}

	static SchuifPuzzel puzzel = new SchuifPuzzel();
	static int[][] grid = new int[4][4];
	static JFrame frame;
	static int size, indexX, indexY;

	@Override
	public void mouseClicked(MouseEvent e) {
		int offset = frame.getHeight() - this.getRootPane().getHeight();
		indexX = e.getX() / size;
		indexY = (e.getY() - offset) / size;
		indexX = (indexX >= grid.length) ? grid.length - 1 : indexX;
		indexY = (indexY >= grid.length) ? grid.length - 1 : indexY;
		applyRules();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
