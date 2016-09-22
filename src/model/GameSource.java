package model;

import java.io.Serializable;

public abstract class GameSource implements Serializable{

	private int value;
	
	
	public GameSource(int values){
		this.value = values;
	}
	
	public int getValues(){
		return value;
	}
	public void minus(){
		value = value - 1;
	}
	public abstract String getName();
	public abstract char getChar();
	
}