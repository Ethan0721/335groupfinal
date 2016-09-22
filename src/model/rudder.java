package model;

import java.io.Serializable;

public class rudder extends item implements Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "rudder";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 300;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 200;
	}

}
