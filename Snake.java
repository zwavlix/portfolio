import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Snake extends JPanel implements KeyListener, ActionListener, ChangeListener {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake");
		frame.add(snake);
		frame.setSize(512, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		JPanel speedsliderbox = new JPanel(new GridLayout(0, 2));
		speedsliderbox.add(new JLabel(""));
		speedsliderbox.add(speedSlider);
		frame.add(speedsliderbox, BorderLayout.SOUTH);
		initialise();
		timer = new Timer(delay, snake);
		timer.start();
		frame.setVisible(true);
	}

	public Snake() {
		addKeyListener(this);
		setFocusable(true);
		speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 250, delay);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				timer.stop();
				delay = speedSlider.getValue();
				timer.setDelay(delay);
				timer.start();
				speedSlider.transferFocusBackward();
			}
		});
	}

	private static void initialise() {
		collision = false;
		headX = 15;
		headY = 11;
		counter = 0;
		lengthvalue = 10;
		direction = -1;
		slang = new ArrayList<>();
		fillArray();
		snake.repaint();
	}

	private static void fillArray() {
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				game[i][j] = 0;
			}
		}
		for (Point p : slang) {
			game[p.x][p.y] = 1;
		}
	}

	private static void move() {
		switch (direction) {
		case 0:
			if (headY == 0)
				headY = game[0].length - 1;
			else
				headY--;
			counter++;
			slang.add(new Point(headX, headY));
			break;
		case 6:
			if (headY == game[0].length - 1)
				headY = 0;
			else
				headY++;
			counter++;
			slang.add(new Point(headX, headY));
			break;
		case 3:
			if (headX == game.length - 1)
				headX = 0;
			else
				headX++;
			counter++;
			slang.add(new Point(headX, headY));
			break;
		case 9:
			if (headX == 0)
				headX = game.length - 1;
			else
				headX--;
			counter++;
			slang.add(new Point(headX, headY));
			break;
		}
		if (game[headX][headY] == 1)
			collision = true;
		if (counter > 10) {
			lengthvalue++;
			counter = 0;
		}
		if (slang.size() > lengthvalue)
			slang.remove(0);
		fillArray();
		snake.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, game.length * ELEMENT_SIZE, game[0].length * ELEMENT_SIZE);
		g.setColor(Color.GRAY);
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				if (game[i][j] == 1) {
					g.fillRect(i * ELEMENT_SIZE, j * ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
				}
			}
			g.setColor(Color.BLACK);
			g.fillOval(headX * ELEMENT_SIZE, headY * ELEMENT_SIZE, ELEMENT_SIZE, ELEMENT_SIZE);
			g.setColor(Color.GRAY);
		}
		g.setColor(Color.WHITE);
		for (int i = 0; i <= game.length; i++) {
			g.drawLine(i * ELEMENT_SIZE, 0, i * ELEMENT_SIZE, (game[0].length) * ELEMENT_SIZE);
		}
		for (int i = 0; i <= game[0].length; i++) {
			g.drawLine(0, i * ELEMENT_SIZE, (game.length) * ELEMENT_SIZE, i * ELEMENT_SIZE);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, ELEMENT_SIZE));
		g.drawString("Score: " + String.valueOf(lengthvalue), ELEMENT_SIZE, (game[0].length + 1) * ELEMENT_SIZE);
		if (collision) {
			g.setFont(new Font(Font.DIALOG, Font.ITALIC, 2 * ELEMENT_SIZE));
			g.drawString("Spacebar for new game", 4 * ELEMENT_SIZE, 11 * ELEMENT_SIZE);
		}
	}
	static Snake snake = new Snake();
	static ArrayList<Point> slang;
	static Timer timer;
	static JSlider speedSlider;
	static int[][] game = new int[32][22];
	static boolean collision;
	static int headX, headY, direction;
	static int counter, lengthvalue;
	static int delay = 50;
	static final int ELEMENT_SIZE = 16;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && !(direction == 6))
			direction = 0;
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !(direction == 0))
			direction = 6;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !(direction == 9))
			direction = 3;
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !(direction == 3))
			direction = 9;
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) && collision)
			initialise();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!collision) {
			if (direction >= 0) {
				move();
			} // actie voor de timer
		}
	}

//ongebruikte verplichte methodes
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return ("Point: x: " + x + ", y: " + y);
	}
}
