package pt.iul.poo.firefight.objects;


import abstracts.Terrain;
import pt.iul.ista.poo.utils.Point2D;


	public class grass extends Terrain {
		
		int hp = 3;

		public grass(Point2D position) {
			super(position, 3, 0.15);
		}
		public String getName() {
			if (hp > 0) {
				return "grass";
			}
			else {
				return "burntgrass";
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