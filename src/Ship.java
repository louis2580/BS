package battleshipCode;

import java.awt.Point;

public class Ship {

	private String name;
	private int size;
	private boolean isSunk;
	private int life;
	private Point from;
	private Point to;
	
	public Ship(String name, int size) {
        this.name = name;
        this.size = size;
        this.isSunk = false;
        this.life = 2;
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
    
    public void setPosition(Point from2, Point to2) {
        from = from2;
        to = to2;
    }
    
    public Point getPositionFrom() {
        return from;
    }
    
    public Point getPositionTo() {
        return to;
    }
    
    public void shipWasHit() {
        if(life == 0) {
            isSunk = true;
            System.out.println("You sunk the " + name);
            return;
        }
        life--;
    }
	
}
