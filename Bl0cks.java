import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class Bl0cks {
	public static void main(String[] args) {
			GameFrame frame = new GameFrame();
			frame.setVisible(true);
	}
}
class GameFrame extends JFrame {
	public static final int HEIGHT = 600;
	public static final int WIDTH = 480;
	public static final int LOCATION_X = 50;
	public static final int LOCATION_Y = 100;
	
	public GameFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X,LOCATION_Y);
		setSize(WIDTH,HEIGHT);
		setResizable(true);
		GamePanel panel = new GamePanel();
		add(panel);
	}
}

class GamePanel extends JPanel {
	public static final int NUM_BRICK_ROWS = 10;
	public static final int NUM_BRICK_COLS = 30;
	private Ball ball = new Ball(Color.red);
	private ArrayList<Brick> bricks = new ArrayList<>();
	private Paddle paddle = new Paddle(Color.BLACK);
	private Player player = new Player();
	
	public GamePanel() {
		for (int row = 0; row < NUM_BRICK_ROWS; row++) {
			for (int col = 0; col < NUM_BRICK_COLS; col++) {
				bricks.add(new Brick(row, col, getRandomColor()));
			}
		}
	}
	
	public Color getRandomColor() {
		Color color = new Color((int) (Math.random() * 256), (int) (Math.random() *256), (int) (Math.random() * 256));
		if (getBackground().equals(color)) {
			return Color.RED;
		}
		return color;
	}
	
	public void showMessage(String s, Graphics2D g2) {
		Font myFont = new Font("SansSerif", Font.BOLD+Font.ITALIC,40);
		g2.setFont(myFont);
		g2.setColor(Color.RED);
		Rectangle2D textBox = myFont.getStringBounds(s, g2.getFontRenderContext());
		g2.drawString(s, (int)(getWidth() / 2 - textBox.getWidth() / 2),(int)(getHeight() / 2 - textBox.getHeight()));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (bricks.size() == 0) {
			showMessage("You Win!!", g2);
		} else if (!player.isAlive()) {
			showMessage("GAME OVER!!", g2);
		} else {
			ball.draw(g2);
			paddle.draw(g2);
			for (Brick brick : bricks) {
				brick.draw(g2);
			}
		}
		try {
			player.draw(g2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Ball {
	public static final int SIZE = 10;
	public static final int START_X = 200;
	public static final int START_Y = 400;
	private Color color;
	private int x, y;
	
	public Ball(Color color) {
		this.color = color;
		x = START_X;
		y = START_Y;
	}
	
	public void draw(Graphics2D g2) {
		g2.setPaint(color);
		Ellipse2D e = new Ellipse2D.Double(x,y, SIZE, SIZE);
		g2.fill(e);
	}
}

class Paddle {
	public static final int WIDTH = 50;
	public static final int HEIGHT = 10;
	public static final int START_X = 200;
	public static final int START_Y = 430;
	private Color color;
	private int x, y;
	
	public Paddle(Color color) {
		this.color = color;
		x = START_X;
		y = START_Y;
	}
	
	public void draw(Graphics2D g2) {
		g2.setPaint(color);
		Rectangle2D r = new Rectangle2D.Double(x,y, WIDTH, HEIGHT);
		g2.fill(r);
	}
}

class Player {
	public static int INITIAL_NUMLIVES = 3;
	public static int IMAGE_DISTANCE = 40;
	public static int IMAGE_Y = 450;
	private int numLives;
	
	public Player() {
		this.numLives = INITIAL_NUMLIVES;
	}
	
	public void killPlayer() {
		numLives--;
	}
	
	public boolean isAlive() {
		return (numLives > 0);
	}

	public void draw(Graphics2D g2) throws Exception {
		try {
			Image image = ImageIO.read(new File("player.gif"));
			for ( int x = 0; x < numLives; x++) {
				g2.drawImage(image, x * IMAGE_DISTANCE,  IMAGE_Y,  null);
			}
		} catch ( Exception myException) { }
	} 
}

class Brick {
	public static final int HEIGHT = 10;
	public static final int WIDTH = 30;
	public static final int BRICK_H_GAP = 2;
	public static final int BRICK_V_GAP = 2;
	private int x, y;
	private Color color;
	
	public Brick(int row, int col, Color color) {
		this.color = color;
		x = BRICK_H_GAP + row * (BRICK_H_GAP + Brick.WIDTH);
		y = BRICK_V_GAP + col * (BRICK_V_GAP + Brick.HEIGHT);
	}
	public void draw(Graphics2D g2) {
		g2.setPaint(color);
		Rectangle2D r = new Rectangle2D.Double(x,y,WIDTH,HEIGHT);
		g2.fill(r);
	}
}
