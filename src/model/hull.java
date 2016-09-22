package model;

import java.io.Serializable;

public class hull extends item implements Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "hull";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 0;
	}

}
