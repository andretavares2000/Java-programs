package pt.iul.poo.firefight.objects;

import java.util.ArrayList;
import Interfaces.InteractableGameObject;
import abstracts.GameObject;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;
import pt.iul.poo.firefight.starterpack.GameEngine;


public class Plane extends GameObject implements InteractableGameObject {

	public Plane(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 4;
	}


	@Override 
	public String getName() {
		return "plane";
	}




	public void move(ArrayList<GameObject> arrayListOfObjectsToCheckCollisions) {

		Vector2D v = new Vector2D (0,-2);
		Vector2D v1 = new Vector2D(0,-1);


		Point2D nextP = getPosition().plus(v);	
		Point2D nextP1 = getPosition().plus(v1);


		GameObject collidingObj = GameEngine.getCollidingObject(getPosition(), arrayListOfObjectsToCheckCollisions);
		GameObject collidingObj2 = GameEngine.getCollidingObject(nextP, arrayListOfObjectsToCheckCollisions);
		GameObject collidingObj3 = GameEngine.getCollidingObject(nextP1, arrayListOfObjectsToCheckCollisions);



		if (collidingObj != null && collidingObj2 !=null || collidingObj3 !=null) {


			this.setPosition(getPosition().plus(v));

			doAction(collidingObj);
			doAction(collidingObj2);
			doAction(collidingObj3);


			if(this.getPosition().getY() <0) {
				GameEngine.getInstance().removeImage(this);
				GameEngine.getInstance().setPlane(null);



			}



		}

	}





	@Override
	public void doAction(GameObject object) {


		if(object instanceof Fire) {
			GameEngine.getInstance().removeImage(object);

		}


	}





}

