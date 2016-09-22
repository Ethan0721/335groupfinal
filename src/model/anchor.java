package model;

import java.io.Serializable;

public class anchor extends item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "anchor";
	}

	@Override
	public int CostInTree() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int CostInIron() {
		// TODO Auto-generated method stub
		return 300;
	}

	@Override
	public int CostInStone() {
		// TODO Auto-generated method stub
		return 300;
	}

}
