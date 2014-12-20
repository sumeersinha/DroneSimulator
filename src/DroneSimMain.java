/**
 * @author sumeersinha
 *
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.*;

public class DroneSimMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Double buffering
	Image dbImage;
	Graphics dbg;

	// Simulator Variables
	static String src ;
	static String destination;
	static String src1 ;
	static String destination1;
	String places[]=LocationLookUpUtil.places();
	GraphEngine graphEngine;
	static String errorMsg="View Simulator Message here!";

	// Target by default
	static Target defaultT;
	static Target srcDefaultT;
	static Target defaultT1;
	static Target srcDefaultT1;

	// Drone objects
	static Drone drone;
	static Drone drone1;
	Thread droneThread;
	Thread droneThread1;
	Thread droneTargetThread;
	Thread droneTargetThread1;

	// To split the views
	boolean gameStarted = false;

	// Hover on button
	boolean startHover;
	boolean srcHover;  int srcPointer=0;
	boolean destHover; int destPointer=0;
	boolean quitHover;
	
	// Menu Buttons
	Rectangle startButton = new Rectangle(475, 100, 100, 25);
	Rectangle srcButton = new Rectangle(475, 150, 100, 25);
	Rectangle destButton = new Rectangle(475,200,100,25);
	
	//sim screen button
	Rectangle quitButton = new Rectangle(600,50,50,30);
	
	// Variables for screen size
	int GWIDTH = 1000, GHEIGHT = 700;
	// Dimension of GWIDTH*GHEIGHT
	Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);

	// Create constructor to spawn window
	public DroneSimMain() {
		this.setTitle("Drone Simulator");
		this.setSize(screenSize);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		this.graphEngine = new GraphEngine();
		srcPointer=0;
		destPointer=0;
		src=places[srcPointer];
		destination=places[destPointer];
	}

	public void startGame() {
		if(src.equals(destination))
			errorMsg="Please enter different SRC and DEST";
		else{
			src1=src;
			destination1=destination;
			srcDefaultT=graphEngine.getTarget(src);
			srcDefaultT1=graphEngine.getTarget(src1);
			
			defaultT=new Target(srcDefaultT.x,srcDefaultT.y,"Home");
			defaultT1=new Target(srcDefaultT1.x,srcDefaultT1.y,"Home1");
			
			drone = new Drone(srcDefaultT.x, srcDefaultT.y, srcDefaultT,1);
			drone1 = new Drone(srcDefaultT1.x, srcDefaultT1.y,srcDefaultT1,2);
			
			gameStarted = true;
		
				droneThread = new Thread(drone);
				droneThread.start();
				
				droneThread1=new Thread(drone1);
				droneThread1.start();

				droneTargetThread = new Thread(drone.t1);
				droneTargetThread.start();
				
				droneTargetThread1=new Thread(drone1.t1);
				droneTargetThread1.start();
				
				
			}
		}
	
	public void stopGame(){
		gameStarted=false;
	}

	public static void main(String[] args) {
		DroneSimMain m = new DroneSimMain();
	}

	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		try {
			draw(dbg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(dbImage, 0, 0, this);
	}

	public void draw(Graphics g) throws InterruptedException {
		if (!gameStarted) {
			// Menu
			g.setFont(new Font("Arial", Font.BOLD, 26));
			g.setColor(Color.WHITE);
			g.drawString("Drone Simulator!", 400, 75);
			
			//Start button
			if (!startHover)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GREEN);
			g.fillRect(startButton.x, startButton.y, startButton.width,
					startButton.height);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.setColor(Color.GRAY);
			g.drawString("Start Sim", startButton.x + 20, startButton.y + 17);
			
			//SRC button
			if (!srcHover)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GREEN);
			g.fillRect(srcButton.x, srcButton.y, srcButton.width,
					srcButton.height);
			g.setColor(Color.GRAY);
			g.drawString("Source:"+src, srcButton.x + 20, srcButton.y + 17);
			
			//DEST button
			if (!destHover)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GREEN);
			g.fillRect(destButton.x, destButton.y, destButton.width,
					destButton.height);
			g.setColor(Color.GRAY);
			g.drawString("Dest:"+destination, destButton.x + 20, destButton.y + 17);
			
			//Error
			g.setColor(Color.RED);
			g.drawString(errorMsg, 420, 380);

		} else {
			// Game drawings
			g.setColor(Color.MAGENTA);
			g.drawImage(new ImageIcon("map.png").getImage(), 100, 140, 800, 500, this);
			
			g.drawString(srcDefaultT.name, defaultT.x, defaultT.y);
			g.drawRect(defaultT.x, defaultT.y, 10, 10);
			
			Target t;
			for(String place: places){
				t=graphEngine.getTarget(place);
				g.drawString(place, t.x, t.y);
				g.drawRect(t.x, t.y, 10, 10);
			}
			
			if(drone.drone.intersects(drone1.drone)&&drone.z==drone1.z){
				drone1.z=drone.z+5;
				errorMsg="Collision Avoided!";
			}
				
			
			drone.draw(g);
			drone1.draw(g);
			
			if(!drone.t1.name.equals("Home"))
				src=drone.t1.name;
			if(!drone1.t1.name.equals("Home1"))
				src1=drone1.t1.name;
			
			if (drone.reached) {
				Target next;
				try {
					next = graphEngine.getNewTarget(src, destination);
					if (next != null)
						drone.t1 = next;
					else
						drone.t1 = defaultT;
				} catch (NoPathException e) {
					// TODO Auto-generated catch block
					errorMsg="No path between "+src+" and "+destination;
					gameStarted=false;
				}
				drone.reached=false;
			}
			
			if (drone1.reached) {
				Target next;
				try {
					next = graphEngine.getNewTarget(src1, destination1);
					if (next != null)
						drone1.t1 = next;
					else
						drone1.t1 = defaultT1;
				} catch (NoPathException e) {
					// TODO Auto-generated catch block
					errorMsg="No path between "+src1+" and "+destination1;
					gameStarted=false;
				}
				drone1.reached=false;
			}
			
			drone.t1.draw(g);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("" + "Drone Resting?:" + drone.resting, 15, 50);
			g.drawString("" + "Package Delivered?" + drone.reachedFinal, 370, 50);
			
			drone1.t1.draw(g);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("" + "Drone1 Resting?:" + drone1.resting, 15, 70);
			g.drawString("" + "Package1 Delivered?" + drone1.reachedFinal, 370, 70);
			g.drawString("" + "Drone CompassX " + drone.xDirection, 670, 70);
			g.drawString("" + "Drone CompassY " + drone.yDirection, 670, 80);
			g.drawString("" + "Drone1 CompassX" + drone1.xDirection, 670, 90);
			g.drawString("" + "Drone1 CompassY" + drone1.yDirection, 670, 100);
			
			//Quit button
			if (!quitHover)
				g.setColor(Color.CYAN);
			else
				g.setColor(Color.GREEN);
			g.fillRect(quitButton.x, quitButton.y, quitButton.width,
					quitButton.height);
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.setColor(Color.GRAY);
			g.drawString("Quit", quitButton.x + 10, quitButton.y + 20);

			//Error
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString(errorMsg+"  Drone Thrust: "+drone.z+" Drone Thrust1: "+drone1.z, 420, 680);
			//errorMsg="";
			drone1.z=drone.z=5;
		}

		repaint();
	}

	// //////EVENT LISTENER CLASSES/////////
	public class MouseHandler extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();

			if (mx > startButton.x && mx < startButton.x + startButton.width
					&& my > startButton.y
					&& my < startButton.y + startButton.height) {
				startHover = true;
			} else
				startHover = false;

			if (mx > srcButton.x && mx < srcButton.x + srcButton.width
					&& my > srcButton.y
					&& my < srcButton.y + srcButton.height) {
				srcHover = true;
			} else
				srcHover = false;
			
			
			if (mx > destButton.x && mx < destButton.x + destButton.width
					&& my > destButton.y
					&& my < destButton.y + destButton.height) {
				destHover = true;
			} else
				destHover = false;
			
			if (mx > quitButton.x && mx < quitButton.x + quitButton.width
					&& my > quitButton.y
					&& my < quitButton.y + quitButton.height) {
				quitHover = true;
			} else
				quitHover = false;
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();
			if (mx > startButton.x && mx < startButton.x + startButton.width
					&& my > startButton.y
					&& my < startButton.y + startButton.height) {
				System.out.println("Clicked");
				startGame();
			}
			
			if (mx > srcButton.x && mx < srcButton.x + srcButton.width
					&& my > srcButton.y
					&& my < srcButton.y + srcButton.height) {
				src=places[(srcPointer++)%places.length];
				src1="JFK";
			}
			
			if (mx > destButton.x && mx < destButton.x + destButton.width
					&& my > destButton.y
					&& my < destButton.y + destButton.height) {
				destination=places[(destPointer++)%places.length];
				destination1="LAS";
			}
			
			if (mx > quitButton.x && mx < quitButton.x + quitButton.width
					&& my > quitButton.y
					&& my < quitButton.y + destButton.height) {
				gameStarted=false;
				errorMsg="Select another source / destination";
				src="JFK";
				src1="JFK";
				destination1="LAX";
			}
			
		}
	}
	// /////END EVENT LISTENER CLASSES/////

}