package model;

import java.io.Serializable;

public class Tree extends GameSource implements Serializable{

	
	public Tree(int values) {
		super(values);
	}
	@Override
	public char getChar(){
		return 'T';
	}
	@Override
	public String getName() {
		
		return "Tree";
	}
	
}
