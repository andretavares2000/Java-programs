package pt.iul.poo.firefight.objects;

import abstracts.GameObject;
import pt.iul.ista.poo.utils.Point2D;

public class land extends GameObject {
	
		public land(Point2D position) {
			super(position);
			// TODO Auto-generated constructor stub
		}
		
		public int getLayer () {
			return 0;
		}
		
		public	String getName() {
			return "land";
		}
}
