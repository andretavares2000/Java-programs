package pt.iul.poo.firefight.objects;


import abstracts.Terrain;
import pt.iul.ista.poo.utils.Point2D;


public class pine extends Terrain {
	private int hp = 10;

	public pine(Point2D position) {
		super(position, 10, 0.05);
	}

	public String getName() {
		if (hp > 0) {
			return "pine";
		}
		else {
			return "burntpine";
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
		

		


