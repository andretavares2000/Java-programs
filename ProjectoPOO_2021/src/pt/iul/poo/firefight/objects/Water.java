package pt.iul.poo.firefight.objects;

import java.awt.event.KeyEvent;

import abstracts.GameObject;
import pt.iul.ista.poo.gui.ImageMatrixGUI;

import pt.iul.ista.poo.utils.Point2D;


public class Water extends GameObject {	

	public Water(Point2D position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 3;
	}
	
	public Point2D getPosition() {
		return super.position;
	}
	
	
	
	public String getName()  {
		 int keyPressed = ImageMatrixGUI.getInstance().keyPressed();
		 String water = null;

	        if (keyPressed == KeyEvent.VK_LEFT) {
	        water = "water_left";
	        }
	        else if (keyPressed == KeyEvent.VK_RIGHT) {
	           water = "water_right";
	        }
	        else if (keyPressed == KeyEvent.VK_UP) {

	           water = "water_up";
	    }
	        else if (keyPressed == KeyEvent.VK_DOWN) 
	           water = "water_down";



	    return water;


	}
	  
	}

