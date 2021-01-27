import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PegSolitaire extends JPanel implements MouseListener {

	public static void main(String[] args) {
		createWindow();
	}

	public static void createWindow() {
		frame = new JFrame("solitaire");
		frame.getContentPane().add(pegs);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addMouseListener(pegs);
	}

	public void paint(Graphics g) {
		super.paint(g);
		int gridwidth = (getWidth() < getHeight()) ? getWidth() : getHeight();
		elementsize = gridwidth / todisplay[1].length;
		for (int i = 0; i < todisplay.length; i++) {
			for (int j = 0; j < todisplay[i].length; j++) {
				if (todisplay[i][j] == 1) {
					g.fillOval(j * elementsize + elementsize / 8, i * elementsize + elementsize / 8,
							3 * elementsize / 4, 3 * elementsize / 4);
				} else if (todisplay[i][j] == 0) {
					g.drawOval(j * elementsize + 7 * elementsize / 16, i * elementsize + 7 * elementsize / 16,
							elementsize / 8, elementsize / 8);
				} else if (todisplay[i][j] == 2) {
					g.drawOval(j * elementsize + elementsize / 8, i * elementsize + elementsize / 8,
							3 * elementsize / 4, 3 * elementsize / 4);
				}
			}
		}
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, elementsize));
		if (counter > 1) {
			g.drawString(String.valueOf(counter), (int) (elementsize / 2), (int) (elementsize * 1.5));
		} else if (counter == 1) {
			g.drawString("you won!!", (int) (elementsize), (int) (elementsize * 1.75));
		}
	}

	static PegSolitaire pegs = new PegSolitaire();
	static int elementsize, counter;
	static JFrame frame;
	static int[][] todisplay = { { -1, -1, 1, 1, 1, -1, -1 }, { -1, -1, 1, 1, 1, -1, -1 }, { 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, 1, 1, 1, -1, -1 },
			{ -1, -1, 1, 1, 1, -1, -1 } };

	@Override
	public void mouseClicked(MouseEvent e) {
		int offset = frame.getHeight() - this.getRootPane().getHeight();
		int indexY = e.getX() / elementsize;
		indexY = (indexY >= todisplay.length) ? todisplay.length - 1 : indexY;
		int indexX = (e.getY() - offset) / elementsize;
		indexX = (indexX >= todisplay.length) ? todisplay.length - 1 : indexX;
		if (todisplay[indexX][indexY] == 1) {
			for (int i = 0; i < todisplay.length; i++) {
				for (int j = 0; j < todisplay[i].length; j++) {
					if (todisplay[i][j] == 2) {
						todisplay[i][j] = 1;
					}
				}
			}
			todisplay[indexX][indexY] = 2;
		} else if (todisplay[indexX][indexY] == 2) {
			todisplay[indexX][indexY] = 1;
		} else if (todisplay[indexX][indexY] == 0) {
			// applyrules
			if (indexX > 1 && todisplay[indexX - 2][indexY] == 2 && todisplay[indexX - 1][indexY] == 1) {
				todisplay[indexX - 2][indexY] = todisplay[indexX - 1][indexY] = 0;
				todisplay[indexX][indexY] = 1;
			} else if (indexY > 1 && todisplay[indexX][indexY - 2] == 2 && todisplay[indexX][indexY - 1] == 1) {
				todisplay[indexX][indexY - 2] = todisplay[indexX][indexY - 1] = 0;
				todisplay[indexX][indexY] = 1;
			} else if (indexX < (todisplay.length - 2) && todisplay[indexX + 2][indexY] == 2
					&& todisplay[indexX + 1][indexY] == 1) {
				todisplay[indexX + 2][indexY] = todisplay[indexX + 1][indexY] = 0;
				todisplay[indexX][indexY] = 1;
			} else if (indexY < (todisplay[indexX].length - 2) && todisplay[indexX][indexY + 2] == 2
					&& todisplay[indexX][indexY + 1] == 1) {
				todisplay[indexX][indexY + 2] = todisplay[indexX][indexY + 1] = 0;
				todisplay[indexX][indexY] = 1;
			}
		}
		counter = 0;
		for (int i = 0; i < todisplay.length; i++) {
			for (int j = 0; j < todisplay[i].length; j++) {
				if (todisplay[i][j] == 1 || todisplay[i][j] == 2) {
					counter++;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
