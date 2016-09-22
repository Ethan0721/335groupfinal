package model;

import java.io.Serializable;

public abstract class item implements Serializable {

	
	public item(){
	}
	
	
	public abstract String getName();
	public abstract int CostInTree();
	public abstract int CostInIron();
	public abstract int CostInStone();
}