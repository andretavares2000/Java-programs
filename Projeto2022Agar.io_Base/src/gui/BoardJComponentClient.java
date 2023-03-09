package gui;

import environment.Coordinate;
import environment.Direction;
import game.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * Creates a JComponent to display the game state. At the same time, this is
 * also a KeyListener for itself: when a key is pressed, attribute
 * lastPressedDirection is updated accordingly. This feature is a demo to better
 * understand how to deal with keys pressed, useful for the remote client. This
 * feature is not helpful for the main application and should be ignored. This
 * class does not need to be edited.
 * 
 * @author luismota
 *
 */
public class BoardJComponentClient extends JComponent implements KeyListener {
	private GameClient game;
	private Image obstacleImage = new ImageIcon("obstacle.png").getImage();
	private Image humanPlayerImage = new ImageIcon("abstract-user-flat.png").getImage();
	private Direction lastPressedDirection = null;
	private final boolean alternativeKeys;

	public BoardJComponentClient(GameClient game, boolean alternativeKeys) {
		this.alternativeKeys = alternativeKeys;
		this.game = game;
		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double cellHeight = getHeight() / (double) message.y;
		double cellWidth = getWidth() / (double) message.x;

		for (int y = 1; y < Game.DIMY; y++) {
			g.drawLine(0, (int) (y * cellHeight), getWidth(), (int) (y * cellHeight));
		}
		for (int x = 1; x < Game.DIMX; x++) {
			g.drawLine((int) (x * cellWidth), 0, (int) (x * cellWidth), getHeight());
		}

		message msg = game.msg;

		if (msg != null) {

			for (int i = 0; i < msg.toClient.size(); i++) {

				Coordinate p = msg.toClient.get(i).coord;

				if (p != null) {
					// Fill yellow if there is a dead player
					if (msg.toClient.get(i).strength == 0) {
						g.setColor(Color.YELLOW);
						g.fillRect((int) (p.x * cellWidth), (int) (p.y * cellHeight), (int) (cellWidth),
								(int) (cellHeight));
						g.drawImage(obstacleImage, (int) (p.x * cellWidth), (int) (p.y * cellHeight), (int) (cellWidth),
								(int) (cellHeight), null);
						// if player is dead, don'd draw anything else?
						continue;
					}
					// Fill green if it is a human player
					if (msg.toClient.get(i).isHuman) {
						g.setColor(Color.GREEN);
						g.fillRect((int) (p.x * cellWidth), (int) (p.y * cellHeight), (int) (cellWidth),
								(int) (cellHeight));
						// Custom icon?
						g.drawImage(humanPlayerImage, (int) (p.x * cellWidth), (int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight), null);
					}
					g.setColor(new Color(msg.toClient.get(i).id * 1000));
					((Graphics2D) g).setStroke(new BasicStroke(5));
					Font font = g.getFont().deriveFont((float) cellHeight);
					g.setFont(font);
					String strengthMarking = (msg.toClient.get(i).strength >= 10 ? "X"
							: "" + msg.toClient.get(i).strength);
					g.drawString(strengthMarking, (int) ((p.x + .2) * cellWidth), (int) ((p.y + .9) * cellHeight));
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (alternativeKeys) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				lastPressedDirection = environment.Direction.LEFT;
				break;
			case KeyEvent.VK_D:
				lastPressedDirection = environment.Direction.RIGHT;
				break;
			case KeyEvent.VK_W:
				lastPressedDirection = environment.Direction.UP;
				break;
			case KeyEvent.VK_S:
				lastPressedDirection = environment.Direction.DOWN;
				break;
			}
		} else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				lastPressedDirection = environment.Direction.LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				lastPressedDirection = environment.Direction.RIGHT;
				break;
			case KeyEvent.VK_UP:
				lastPressedDirection = environment.Direction.UP;
				break;
			case KeyEvent.VK_DOWN:
				lastPressedDirection = environment.Direction.DOWN;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ignore
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignored...
	}

	public Direction getLastPressedDirection() {
		return lastPressedDirection;
	}

	public void clearLastPressedDirection() {
		lastPressedDirection = null;
	}

}