package model;

import java.io.Serializable;

public class mainsail extends item implements Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "mainsail";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 200;
	}

}
