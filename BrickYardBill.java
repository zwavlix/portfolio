import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickYardBill extends JPanel implements KeyListener, ActionListener {

	static int delay = 125;
	static int neededToPass = 75;

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				neededToPass = Integer.parseInt(args[0]);
				System.out.println("Moeilijkheid aangepast");
			} catch (NumberFormatException err) {
				System.out.println("Ongeldige parameter; moeilijkheidsgraad ongewijzigd");
			}
		}
		JFrame frame = new JFrame("Brickyard Bill");
		frame.add(bill);
		frame.setSize(512, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		initialise();
		timer = new Timer(delay, bill);
		timer.start();
		frame.setVisible(true);
	}

	public BrickYardBill() {
		addKeyListener(this);
		setFocusable(true);
	}

	private static void initialise() {
		nextlevel = false;
		ingesloten = false;
		billX = 15;
		billY = 11;
		counter = 0;
		surface = 600;
		score = 0;
		direction = -1;
		fillArray();
		bill.repaint();
	}

	private static void fillArray() {
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				game[i][j] = 1;
			}
		}
		for (int i = 1; i < game.length - 1; i++) {
			for (int j = 1; j < game[i].length - 1; j++) {
				game[i][j] = 0;
			}
		}
		setRandomBricks();
	}

	private static void setRandomBricks() {
		for (int i = 0; i < (15 + 5 * level);) {
			int rndx = (int) (Math.random() * game.length);
			int rndy = (int) (Math.random() * game[0].length);
			if (game[rndx][rndy] == 0) {
				game[rndx][rndy] = 1;
				i++;
				surface--;
			}
		}
	}

	private static void move() {
		game[billX][billY] = 1;
		switch (direction) {
		case 0:
			if (game[billX][billY - 1] == 0) {
				billY--;
				counter++;
			}
			break;
		case 6:
			if (game[billX][billY + 1] == 0) {
				billY++;
				counter++;
			}
			break;
		case 3:
			if (game[billX + 1][billY] == 0) {
				billX++;
				counter++;
			}
			break;
		case 9:
			if (game[billX - 1][billY] == 0) {
				billX--;
				counter++;
			}
			break;
		}
		if (game[billX][billY - 1] == 1 && game[billX + 1][billY] == 1 && game[billX][billY + 1] == 1
				&& game[billX - 1][billY] == 1) {
			ingesloten = true;
		}
		score = 100 * counter / surface;
		newtotalscore = totalscore + score;
		if (score > neededToPass)
			nextlevel = true;
		bill.repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, game.length * BRICK_SIZE, game[0].length * BRICK_SIZE);
		g.setColor(Color.RED);
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				if (game[i][j] == 1) {
					g.fillRect(i * BRICK_SIZE, j * BRICK_SIZE, BRICK_SIZE, BRICK_SIZE);
				}
			}
			g.setColor(Color.BLACK);
			g.fillOval(billX * BRICK_SIZE, billY * BRICK_SIZE, BRICK_SIZE, BRICK_SIZE);
			g.setColor(Color.RED);
		}
		g.setColor(Color.WHITE);
		for (int i = 0; i <= game.length; i++) {
			g.drawLine(i * BRICK_SIZE, 0, i * BRICK_SIZE, (game[0].length) * BRICK_SIZE);
		}
		for (int i = 0; i <= game[0].length; i++) {
			g.drawLine(0, i * BRICK_SIZE, (game.length) * BRICK_SIZE, i * BRICK_SIZE);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, BRICK_SIZE));
		g.drawString("Score: " + String.valueOf(score), BRICK_SIZE, (game[0].length + 1) * BRICK_SIZE);
		if (totalscore > 0) {
			g.drawString("Total: " + String.valueOf(newtotalscore), 15 * BRICK_SIZE, (game[0].length + 1) * BRICK_SIZE);
		}
		if (ingesloten && nextlevel) {
			g.setFont(new Font(Font.DIALOG, Font.ITALIC, 2 * BRICK_SIZE));
			g.drawString("Spacebar for next level", 4 * BRICK_SIZE, 11 * BRICK_SIZE);
		}
		if (ingesloten && !nextlevel) {
			g.setFont(new Font(Font.DIALOG, Font.ITALIC, 2 * BRICK_SIZE));
			g.drawString("Game Over", 10 * BRICK_SIZE, 11 * BRICK_SIZE);
			g.drawString("Spacebar for new game", 4 * BRICK_SIZE, 14 * BRICK_SIZE);
		}
	}

	static BrickYardBill bill = new BrickYardBill();
	static Timer timer;
	static int[][] game = new int[32][22];
	static boolean ingesloten, nextlevel;
	static int billX, billY, direction;
	static int counter, surface, score, totalscore, newtotalscore;
	static int level = 1;
	static final int BRICK_SIZE = 16;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (game[billX][billY - 1] == 0)
				direction = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (game[billX][billY + 1] == 0)
				direction = 6;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (game[billX + 1][billY] == 0)
				direction = 3;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (game[billX - 1][billY] == 0)
				direction = 9;
		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) && nextlevel && ingesloten) {
			level++;
			totalscore = newtotalscore;
			initialise();
		}
		if ((e.getKeyCode() == KeyEvent.VK_SPACE) && !nextlevel && ingesloten) {
			level = 1;
			totalscore = 0;
			initialise();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		move(); // actie voor de timer
	}

//ongebruikte verplichte methodes
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
