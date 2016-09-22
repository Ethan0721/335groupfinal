package model;

import java.io.Serializable;

public class Stone extends GameSource implements Serializable{

	
			
	public Stone(int values){
		super(values);
	}
	
	@Override
	public char getChar() {
		return 'S';
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Stone";
	}

	
}
