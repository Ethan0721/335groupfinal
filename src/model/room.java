package model;

import java.io.Serializable;

public class room implements Serializable{
	private char direction;
	private int cost;
	private char status;
	private int x;
	private int y;

	public room(char status, char direction, int cost, int x, int y) {
		this.direction = direction;
		this.cost = cost;
		this.status = status;
		this.x = x;
		this.y = y;

	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char temp) {
		direction = temp;
	}

	public int getCost() {
		return cost;
	}

	public char getStatus() {
		return status;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
