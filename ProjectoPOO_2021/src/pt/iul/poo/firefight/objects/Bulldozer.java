package pt.iul.poo.firefight.objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Interfaces.InteractableGameObject;
import abstracts.GameObject;
import pt.iul.ista.poo.gui.ImageMatrixGUI;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Bulldozer extends GameObject implements InteractableGameObject {



	public Bulldozer(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 3;
	}


	@Override 
	public String getName()  {
		int keyPressed = ImageMatrixGUI.getInstance().keyPressed();
		String bulldozer = "bulldozer";

		Fireman man = (Fireman) GameEngine.getInstance().getObject(obj -> obj instanceof Fireman);
		if (man == null) {

			if (keyPressed == KeyEvent.VK_LEFT) {
				bulldozer = "bulldozer_left";
			}
			else if (keyPressed == KeyEvent.VK_RIGHT) {
				bulldozer = "bulldozer_right";
			}
			else if (keyPressed == KeyEvent.VK_UP) {

				bulldozer = "bulldozer_up";
			}
			else if (keyPressed == KeyEvent.VK_DOWN) 
				bulldozer = "bulldozer_down";

		}

		return bulldozer;



	}





	public void move(Direction d, ArrayList<GameObject> arrayListOfObjectsToCheckCollisions) {
		Point2D nextP = getPosition().plus(d.asVector());
		int keyPressed = ImageMatrixGUI.getInstance().keyPressed();
		GameObject collidingObj = GameEngine.getCollidingObject(nextP, arrayListOfObjectsToCheckCollisions);

		if (GameEngine.getInstance().getObject( obj -> obj instanceof Fireman) == null ) {
			if (collidingObj != null && !(collidingObj instanceof Fire) && canMoveTo(nextP) ) {
				doAction(GameEngine.getCollidingObject(nextP, arrayListOfObjectsToCheckCollisions));
			}
			else if (collidingObj instanceof land && canMoveTo(nextP)) {
				setPosition(collidingObj.getPosition());
			}

		}
		if (keyPressed == KeyEvent.VK_ENTER && GameEngine.getInstance().getObject( obj -> obj instanceof Fireman) == null ) {
			Fireman man = new Fireman(getPosition());
			GameEngine.getInstance().addImage(man);
			GameEngine.getInstance().setFireman(man);



		}

	}

	@Override
	public void doAction(GameObject object) {

		land l = new land (object.getPosition());


		if(object.getLayer()== 0) {

			setPosition(object.getPosition());
			GameEngine.getInstance().MudaObjeto(l, object);

		}
		if (object instanceof Fire) {

		}
		else {
			setPosition(l.getPosition());

		}



	}

	public boolean canMoveTo(Point2D p) {

		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;

	}




}
