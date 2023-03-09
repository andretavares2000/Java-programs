package abstracts;

import Interfaces.Burnable;
import pt.iul.ista.poo.utils.Point2D;

public 	abstract class Terrain extends GameObject implements Burnable {

	private double percentagem;
	private int hp;

	public Terrain(Point2D position, int hp, double percentagem) {
		super(position);
		this.percentagem = percentagem;
		this.hp = hp;

	}

	public double getPercentagem() {
		return percentagem;
	}

	public int getHp() {
		return hp;

	}

	public int getLayer() {
		return 0;
	}

	public void updateHp() {
		hp--;
	}


}

