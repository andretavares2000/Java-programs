package pt.iul.poo.firefight.objects;

import java.util.ArrayList;
import Interfaces.InteractableGameObject;
import abstracts.GameObject;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Fireman extends GameObject implements InteractableGameObject{






	public Fireman(Point2D position) {

		super(position);
	}

	@Override
	public int getLayer() {
		return 4;
	}

	@Override
	public String getName() {
		return "fireman";
	}



	// MOVE O BOMBEIRO 



	public void move(Direction d, ArrayList<GameObject> arrayListOfObjectsToCheckCollisions) {

		Water water = (pt.iul.poo.firefight.objects.Water) GameEngine.getInstance().getObject(obj -> obj instanceof Water);

		Point2D nextP = getPosition().plus(d.asVector());
		GameObject collidingObj = GameEngine.getCollidingObject(nextP, arrayListOfObjectsToCheckCollisions);	
		if(water !=null) {
			GameEngine.getInstance().removeImage(water);


		}
		if (collidingObj != null && canMoveTo(nextP) && GameEngine.getInstance().getObject(obj -> obj instanceof Fireman) != null ) {

			if(water !=null) {
				GameEngine.getInstance().removeImage(water);


			}
			doAction(collidingObj);

		}

	}




	@Override
	public void doAction(GameObject object) {
//		Water agua = (pt.iul.poo.firefight.objects.Water) GameEngine.getInstance().getObject(obj -> obj instanceof Water);
//		if(agua!=null) {
//			GameEngine.getInstance().removeImage(agua);
//
//		}

		if (object instanceof Fire) {
			GameEngine.getInstance().removeImage(object);
			GameEngine.getInstance().addImage(new Water (object.getPosition()));


		}
		if(object.getLayer() == 0) {

			setPosition(object.getPosition());



		}
		if (object instanceof Bulldozer) {
			GameEngine.getInstance().removeImage(this);
		}





	}




	// VERIFICA SE A POSIÇÃO P ESTÁ DENTRO DA GRELHA DO JOGO 

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