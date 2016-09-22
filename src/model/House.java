package model;

import java.io.Serializable;

public enum House implements Serializable{
	sleepingRoom(0,0,0),
	storage(0,0,0);

	private int Wood;
	private int Stone;
	private int Iron;
	
	private House (int Wood, int Stone, int Iron){
		this.Wood = Wood;
		this.Stone = Stone;
		this.Iron = Iron;
	}
	public void addWood(){
		if(Wood+10>=1000)
			Wood = 1000;
		else
		Wood = Wood + 10;
	}
	public void addStone(){
		if(Stone+10>=1000)
			Stone = 1000;
		else
		Stone = Stone + 10;
	}
	public void addIron(){
		if(Iron+10>=1000)
			Iron = 1000;
		else
		Iron = Iron + 10;
	}
	public int getWood(){
		return Wood;
	}
	public int getStone(){
		return Stone;
	}
	public int getIron(){
		return Iron;
	}
	public void minusIron(int temp){
		Iron = Iron - temp;
	}
	public void minusStone(int temp){
		Stone = Stone - temp;
	}
	public void minusWood(int temp){
		Wood = Wood - temp;
	}
}