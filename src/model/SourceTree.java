package model;

import java.io.Serializable;

public class SourceTree extends GameSource implements Serializable{

	
	public SourceTree(int values) {
		super(values);
	}
	@Override
	public char getChar(){
		return 'M';
	}
	@Override
	public String getName() {
		
		return "Mutou";
	}
	
}
