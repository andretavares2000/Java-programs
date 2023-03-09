package pt.iul.poo.firefight.objects;


import abstracts.Terrain;
import pt.iul.ista.poo.utils.Point2D;

public class eucaliptus extends Terrain {
	private int hp = 5;

	public eucaliptus(Point2D position) {
		super(position, 5, 0.10);
	}

	public String getName() {
		if (hp > 0) {
			return "eucaliptus";
		}
		else {
			return "burnteucaliptus";
		}
	}

	@Override
	public int getHP() {
		return hp;
	}

	@Override
	public void updateHP() {
		hp--;


	}



	

}

		
		
	
	


