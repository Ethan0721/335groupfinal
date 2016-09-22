package model;

import java.io.Serializable;

public class Iron extends GameSource implements Serializable{

	
	public Iron(int values) {
		super(values);
	}

	@Override
	public char getChar() {
		return 'I';
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Iron";
	}
}
