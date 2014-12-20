


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author sumeersinha
 *
 */
public class Target implements Runnable {

	int x, y;
	String name;

	Rectangle destination;

	public Target(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		destination = new Rectangle(x, y, 10, 10);
	}

	public void draw(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.setColor(Color.WHITE);
		g.drawString(this.name, this.x, this.y);
		g.setColor(Color.RED);
		g.fillRect(destination.x, destination.y, destination.width, destination.height);
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(8);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
