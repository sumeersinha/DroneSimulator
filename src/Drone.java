/**
 * 
 */



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Drone implements Runnable {
    
    //Drone variables
    int x, y,z, xDirection, yDirection,id;
    Image droneImg;
    volatile Queue<String> targetQueue;
    
    //Drone State
    volatile boolean reached;
    volatile boolean reachedFinal;
    
    //Drone state
    volatile boolean resting;
    
    //Drone  Target-Destination
    volatile Target t1;
    
    Rectangle drone;
    
    public Drone(int x, int y, Target t, int id){
    	this.id=id;
        this.x = x;
        this.y = y;
        this.z=5;
        //Create 'drone'
        drone = new Rectangle(this.x, this.y, 5, 5);
        resting=false;
        reached=false;
        reachedFinal=false;
        droneImg = new ImageIcon("drone.png").getImage();
        //Create Destination
        t1=t;
    }
    
    public void setXDirection(int xdir){
        xDirection = xdir;
    }
    public void setYDirection(int ydir){
        yDirection = ydir;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        if(drone != null)
        	g.fillRect(drone.x, drone.y, drone.width, drone.height);
        g.drawImage(droneImg, drone.x, drone.y, null);
    }
    
  //Find a path to the target
    public void findPathToTarget(){
    	if(drone.x<t1.destination.x)
    		setXDirection(1);
    	if(drone.x>t1.destination.x)
    		setXDirection(-1);
    	if(drone.y<t1.destination.y)
    		setYDirection(1);
    	if(drone.y>t1.destination.y)
    		setYDirection(-1);
    }
    
    public void collision(){
    	
    		if(drone.intersects(t1.destination) && !reachedFinal){
            	reached=true;
            	if(id==1){
            		if(t1.name.equals(DroneSimMain.destination))
                		reachedFinal=true;
            	}
            	else if(id==2){
            		if(t1.name.equals(DroneSimMain.destination1))
                		reachedFinal=true;
            	}
            	
            }
    		else{
    			reached=false;
    		}
    }
    
    public void move(){
        collision();
        drone.x += xDirection;
        drone.y += yDirection;
        //Bounce the drone when edge is detected
        if(drone.x <= 0){
            setXDirection(+1);
        }
        if(drone.x >= 385){
            setXDirection(-1);
        }
        if(drone.y <= 15)
            setYDirection(+1);
        if(drone.y >= 285)
            setYDirection(-1);
    }
    
    @Override
    public void run(){
        try{
            while(true){
            	 if(!resting && !reached ){
                     long start = System.currentTimeMillis();
                     long end = start + 3*500;
                     while(System.currentTimeMillis() < end){
                     	findPathToTarget();
                         move();
                         collision();
                         Thread.sleep(10);
                     }
                     resting = true;
                 }
                 else{
                     Thread.sleep(2000);
                     resting = false;
                 }
            }
        }catch(Exception e){System.err.println(e.getMessage());}
    }
    
}