package BS;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ship {

	private String name;		// nom du bateau
	private int size;			// taille
	private int reach;			// Portée
	private boolean isSunk;		// passe à true si le bateau est coulé
	private int life;			// Chaque bateau commence avec une vie de 2
	private Point from;				// Première case du bateau
	private boolean horizontal;			// true si le bateau est positionné horizontalement
	private ArrayList<Point> allCases;		// Dernière case du bateau
	
	
	public Ship(String name, int size, int reach) {
        this.name = name;
        this.size = size;
        this.isSunk = false;
        this.life = 2;
        this.reach = reach;
        this.allCases = new ArrayList<Point>();
    }
	
	public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
    
    public int getLife() {
		return life;
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
    
    public boolean isHorizontal() {
    	return horizontal;
    }


    
    public Point getPositionFrom() {
        return from;
    }

    public void updatePosition(Point ptFrom, boolean horizontal) {
        int oriX = 0, oriY = 0;
        if (horizontal)
        {
        	oriX = 1;
        } else
        {
        	oriY = 1;
        }
    	
    	if (ptFrom.x + oriX * this.size > 10 || ptFrom.y + oriY * this.size > 10)
    	{
    		System.out.printf("Nouvelle position invalide : %d; %d", ptFrom.y, ptFrom.x);
    	} else
    	{
    		from = ptFrom;
    		this.allCases.clear();
    		Point newPoint;
    		for (int i = 0; i < this.size; i++)
    		{
    			newPoint = new Point(ptFrom.x + i*oriX, ptFrom.y + i*oriY);
    			pushPointShip(newPoint);
    		}
    	}
    }
    
    
    public void pushPointShip(Point push) {
    	allCases.add(push);
    }
    
    public void setPointShip(int index, Point element) {
    	allCases.add(index, element);
    }
    
    public Point getPointShip(int index) {
    	return allCases.get(index);
    }
    
    public ArrayList<Point> getAllPointShip() {
    	return allCases;
    }
    
    ////////////////////////
    
    
    public void shipWasHit(Point touche) {
		int iTouch = allCases.indexOf(touche);
		allCases.remove(iTouch);		// Pour ne pas toucher 2 fois de suite au même endroit
		
        life--;
        
        System.out.print("Vous avez touché un " + name);
        if(life <= 0) {
            isSunk = true;
            System.out.print("... et l'avez coulé ! \n\n");
            return;
        }
        System.out.println();
    }
	
}
