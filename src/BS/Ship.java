package BS;

import java.awt.Point;
import java.util.ArrayList;

public class Ship {

	private String name;		// nom du bateau
	private int size;			// taille
	private int reach;			// Port�e
	private boolean isSunk;		// passe � true si le bateau est coul�
	private int life;			// Chaque bateau commence avec une vie de 2
	private Point from;				// Premi�re case du bateau
	private boolean horizontal;			// true si le bateau est positionn� horizontalement
	private ArrayList<Point> allCases;		// toutes les cases du navire
	private ArrayList<Point> touchedCases;	// cases touch�es du navire
	
	
	public Ship(String name, int size, int reach) {
        this.name = name;
        this.size = size;
        this.isSunk = false;
        this.life = 2;
        this.reach = reach;
        this.allCases = new ArrayList<Point>();
        this.touchedCases = new ArrayList<Point>();
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
    		this.horizontal = horizontal;
    		from = ptFrom;
    		this.allCases.clear();
    		this.touchedCases.clear(); // Toutes les cases peuvent de nouveau �tre touch�es
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

    public ArrayList<Point> getTouchedPoint() {
    	return touchedCases;
    }
    
    ////////////////////////
    
    
    public void shipWasHit(Point touche) {

    	this.touchedCases.add(touche); // Pour ne pas toucher 2 fois de suite au m�me endroit
		
        life--;
        
        System.out.print("Vous avez touch� un " + name);
        if(life <= 0) {
            isSunk = true;
            System.out.print("... et l'avez coul� ! \n\n");
            return;
        }
        System.out.println();
    }
	
}
