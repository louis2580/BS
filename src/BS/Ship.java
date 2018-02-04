package BS;

import java.awt.Point;
import java.util.ArrayList;

public class Ship {

	private String name;
	private int size;
	private boolean isSunk;
	private int life;
	private Point from;
	private Point to;
	
	private ArrayList<Point> allPointsOfTheShip;
	private int reach;
	
	public Ship(String name, int size, int reach) {
        this.name = name;
        this.size = size;
        this.isSunk = false;
        this.life = 2;
        this.reach = reach;
        this.allPointsOfTheShip = new ArrayList<Point>();
    }
	
	public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
    
    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }
    
    public int getReach() {
        return reach;
    }
    
    
    /////////////////////////
    /*public void setPosition(Point from2, Point to2) {
        from = from2;
        to = to2;
    }*/
    
    public Point getPositionFrom() {
        return from;
    }
    
    public Point getPositionTo() {
        return to;
    }
    
    
    public void pushPointShip(Point push) {
    	allPointsOfTheShip.add(push);
    }
    
    public void setPointShip(int index, Point element) {
    	allPointsOfTheShip.add(index, element);
    }
    
    public Point getPointShip(int index) {
    	return allPointsOfTheShip.get(index);
    }
    
    public ArrayList<Point> getAllPointShip() {
    	return allPointsOfTheShip;
    }
    
    ////////////////////////
    
    
    public void shipWasHit() {
        if(life == 0) {
            isSunk = true;
            System.out.println("You sunk the " + name);
            return;
        }
        life--;
    }
	
}
