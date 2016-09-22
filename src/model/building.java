package model;

import java.io.Serializable;

public class building implements Serializable{
	private int x;
	private int y;
	private String type;
	
	public building(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public String getType(){
		return type;
	}
}
