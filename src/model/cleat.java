package model;

import java.io.Serializable;

public class cleat extends item implements Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "cleat";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 300;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 0;
	}

}
