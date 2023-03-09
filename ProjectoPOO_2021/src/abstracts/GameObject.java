package abstracts;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {

	protected Point2D position;
	private int layer;

	public GameObject(Point2D position) {
		this.position = position;
		
	}
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public int getLayer() {
		return layer;
	}
	

	@Override
	public Point2D getPosition() {
		return position;
	}
	

	
	public void setPosition(Point2D newPosition) {
		this.position = newPosition; 
	}


	
}

