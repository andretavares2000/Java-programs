package pt.iul.poo.firefight.objects;

import java.util.Random;
import Interfaces.Burnable;
import abstracts.GameObject;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.poo.firefight.starterpack.GameEngine;

public class Fire extends GameObject  {

	public Fire(Point2D position) {
		super(position);
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public String getName() {
		return "fire";
	}


	public void spreadFire() {




		Fireman man = (Fireman) GameEngine.getInstance().getObject(obj -> obj instanceof Fireman);
		Point2D p = GameEngine.getInstance().getWaterPosition();


		for (Point2D ladosVizinhos : position.getNeighbourhoodPoints()) {
			if(!GameEngine.getInstance().checkCondition(obj -> obj instanceof Fire && obj.getPosition().equals(ladosVizinhos))) {
				Burnable a = (Burnable) GameEngine.getInstance().getCertainObjectOnCertainPosition(ladosVizinhos, obj -> obj instanceof Burnable && 
						obj.getPosition().equals(ladosVizinhos));

				Random random = new Random();
				Double rand = random.nextDouble();
				if (a!=null) {
					if(a.getHP()>0) {
						if( rand < a.getPercentagem() && !ladosVizinhos.equals(p)) {
							if(man != null && !ladosVizinhos.equals(man.getPosition())) {
								Fire f = new Fire(ladosVizinhos);
								GameEngine.getInstance().addImage(f);

							} else if(man == null) {
								Fire f = new Fire(ladosVizinhos);
								GameEngine.getInstance().addImage(f);
							}
						}
					}
				}
			}



		}


	}
}
