import java.awt.Graphics;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;
import java.awt.*;

public class GameOfLife extends JPanel implements MouseListener, ActionListener, ChangeListener {

	public static void main(String[] args) {
		createWindow();
		timer = new Timer(speed, life);
		timer.start();
	}

	public static void createWindow() {
		frame = new JFrame("Conway's game of life");
		frame.getContentPane().add(life);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		JPanel knoppenbalk = new JPanel();
// stapknop
		stepknop = new JButton("Step");
		knoppenbalk.add(stepknop);
		stepknop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
// start/stopknop
		startknop = new JButton("Start");
		knoppenbalk.add(startknop);
		startknop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!start) {
					startknop.setText("Stop");
				} else {
					startknop.setText("Start");
				}
				start = !start;
			}
		});
// snelheidsschuif
		speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 250, speed);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				timer.stop();
				speed = speedSlider.getValue();
				timer.setDelay(speed);
				timer.start();
			}
		});
		knoppenbalk.add(speedSlider);
// grid aan/uit 
		gridknop = new JButton("Grid");
		knoppenbalk.add(gridknop);
		gridknop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid = !grid;
				life.repaint();
			}
		});
// generatieteller afbeelden
		gen = new JLabel("generation: " + generation);
		knoppenbalk.add(gen);
		frame.add(knoppenbalk, BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.addMouseListener(life);
	}

	public void paint(Graphics g) {
		super.paint(g);
		int gridwidth = getWidth();
		if (gridwidth > getHeight())
			gridwidth = getHeight();
		elementwidth = gridwidth / todisplay[1].length;
		for (int i = 0; i < todisplay.length; i++) {
			for (int j = 0; j < todisplay[i].length; j++) {
				if (todisplay[i][j] == 1) {
					g.fillRect(j * elementwidth, i * elementwidth, elementwidth, elementwidth);
				}
			}
		}
		if (grid) {
			for (int i = 0; i <= todisplay.length; i++) {
				g.drawLine(i * elementwidth, 0, i * elementwidth, (todisplay.length) * elementwidth);
			}
			for (int i = 0; i <= todisplay[0].length; i++) {
				g.drawLine(0, i * elementwidth, (todisplay[0].length) * elementwidth, i * elementwidth);
			}
		}
	}

	public static void step() {
		applyRules();
		generation++;
		gen.setText("Generation: " + generation);
		life.repaint();
	}

	public static void applyRules() {
		int nextgeneration[][] = new int[todisplay.length][todisplay[1].length];
		for (int i = 0; i < todisplay.length; i++) {
			for (int j = 0; j < todisplay[i].length; j++) {
				int neighbours = 0;
				neighbours += ((i == 0) ? 0 : ((j == 0) ? 0 : todisplay[i - 1][j - 1]));
				neighbours += ((i == 0) ? 0 : todisplay[i - 1][j]);
				neighbours += ((i == 0) ? 0 : (j == todisplay[i].length - 1) ? 0 : todisplay[i - 1][j + 1]);
				neighbours += ((j == 0) ? 0 : todisplay[i][j - 1]);
				neighbours += ((j == todisplay[i].length - 1) ? 0 : todisplay[i][j + 1]);
				neighbours += ((i == todisplay.length - 1) ? 0 : (j == 0) ? 0 : todisplay[i + 1][j - 1]);
				neighbours += ((i == todisplay.length - 1) ? 0 : todisplay[i + 1][j]);
				neighbours += ((i == todisplay.length - 1) ? 0
						: (j == todisplay[i].length - 1) ? 0 : todisplay[i + 1][j + 1]);
				switch (neighbours) {
				case 2:
					nextgeneration[i][j] = (todisplay[i][j] == 1) ? 1 : 0;
					break;
				case 3:
					nextgeneration[i][j] = 1;
					break;
				default:
					nextgeneration[i][j] = 0;
				}
			}
		}
		todisplay = nextgeneration;
	}

	static GameOfLife life = new GameOfLife();
	static boolean start = false;
	static JFrame frame;
	static JButton stepknop, gridknop, startknop;
	static JSlider speedSlider;
	static JLabel gen;
	static int generation = 0;
	static int speed = 100;
	static int elementwidth;
	static boolean grid = false;
	static Timer timer;
//	Dit is het array dat standaard weergegeven wordt met een paar zichzelf herhalende vormen
	static int todisplay[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
//	Een leeg array om zelf in te vullen; kan verwisseld worden met todisplay
	static int blanc[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

// muisprocedure voor tekenenen/weghalen van vakjes in het grid
	@Override
	public void mouseClicked(MouseEvent e) {
		int offset = frame.getHeight() - this.getRootPane().getHeight();
		int indexY = e.getX() / elementwidth;
		int indexX = (e.getY() - offset) / elementwidth;
		if (indexX < todisplay.length && indexY < todisplay[0].length) {
			if (todisplay[indexX][indexY] == 0) {
				todisplay[indexX][indexY] = 1;
			} else if (todisplay[indexX][indexY] == 1) {
				todisplay[indexX][indexY] = 0;
			}
			life.repaint();
		}
	}

// dit is de actie die bij de timer hoort
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!start)
			return;
		step();
		return;
	}

// ongebruikte procedures voor de eventlisteners
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}
