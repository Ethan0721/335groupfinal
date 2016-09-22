package model;

import java.io.Serializable;

public class Spade extends item implements Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Spade";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 50;
	}

}
