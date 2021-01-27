import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class Pong extends JPanel implements ActionListener, KeyListener{

	public static void main(String[] args) {
		Pong pong = new Pong();
//		initialize();
	}
	public static void initialize() {
		JFrame frame = new JFrame("Pong");
		frame.getContentPane().add(new Pong());
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	public Pong() {
		JFrame frame = new JFrame("Pong");
		frame.getContentPane().add(this);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		addKeyListener(this);
		setFocusable(true);
		paddle_upper = new Rectangle (300-PADDLE_WIDTH/2, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle_lower = new Rectangle (300-PADDLE_WIDTH/2,this.getHeight()-PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		ball = new Ellipse2D.Double(10,70,RADIUS,RADIUS);
		Timer timer = new Timer(10, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		paddle_lower.y = this.getHeight()-PADDLE_HEIGHT;
		g2.fill(paddle_upper);
		g2.fill(paddle_lower);
		g2.fill(ball);
	}
	
	public void move() {
		if (ball.getY() > this.getHeight() - RADIUS - PADDLE_HEIGHT) {
			diry = -diry;
			checkLost();
		}
		if (ball.getY() < PADDLE_HEIGHT) {
			diry = -diry;
		}
		if (ball.getX() < 1)
			dirx = -dirx;
		if (ball.getX() > this.getWidth() - RADIUS)
			dirx = -dirx;
		ball.setFrame(ball.getX() + dirx, ball.getY() + diry, RADIUS, RADIUS);
		if (ball.getX() + RADIUS / 2 > paddle_upper.getX() + PADDLE_WIDTH / 2)
			paddle_upper.x++;
		if (ball.getX() + RADIUS / 2 < paddle_upper.getX() + PADDLE_WIDTH / 2)
			paddle_upper.x--;
		repaint();
	}
	public void checkLost() {
		if ((ball.getX() + RADIUS / 2 > paddle_lower.getX()) && (ball.getX() + RADIUS / 2 < paddle_lower.getX() + PADDLE_WIDTH)) lost = false; else lost = true;
	}
	
	Rectangle paddle_upper, paddle_lower;
	Ellipse2D ball;
	int dirx = 1;
	int diry = 1;
	boolean lost = false;
	static final int RADIUS = 60;
	static final int PADDLE_HEIGHT = 30;
	static final int PADDLE_WIDTH = 90;
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!lost) move();
		else System.exit(0);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			paddle_lower.x+=15;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			paddle_lower.x-=15;
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
