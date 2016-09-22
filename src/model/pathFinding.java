package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class pathFinding implements Serializable {
	private room[][] rooms;
	private int sizeX;
	private int sizeY;
	private int initX;
	private int initY;
	private int endX;
	private int endY;
	private NaturalSourceCollection nature;
	private GameSourceCollection game;
	private ItemCollection item;
	private HouseCollection houses;
	
	public pathFinding(NaturalSourceCollection nature, GameSourceCollection game, ItemCollection item, HouseCollection houses) {
		sizeX = 100;
		sizeY = 100;
		this.nature = nature;
		this.game = game;
		this.item = item;
		this.houses = houses;
		
	}
	
	public boolean getPath(int initX, int initY, int endX, int endY){
		rooms = new room[sizeX][sizeY];
		this.initX = initX;
		this.initY = initY;
		this.endX = endX;
		this.endY = endY;
		
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if((nature.getNaturalSourceCollection()[i][j]!= NaturalSource.Brambles&&
						nature.getNaturalSourceCollection()[i][j]!= NaturalSource.Ground&&nature.getNaturalSourceCollection()[i][j]!= NaturalSource.Sand)
						||game.getGameSourceCollection()[i][j]!=null||houses.gethouseCollection()[i][j]!=null)
				rooms[i][j] = new room('B', 'b', 100000, i, j);
				else
				rooms[i][j] = new room(' ', ' ', Math.abs(i - endX)
						+ Math.abs(j - endY), i, j);
			}
		}
		
		
		
		rooms[initX][initY] = new room('S', 'b',  Math.abs(initX - endX)
				+ Math.abs(initY - endY), initX, initY);
		rooms[endX][endY] = new room('E', ' ', 0, endX, endY);
		
		return WayOut(rooms[initX][initY]);
	}

	public Stack<String> getTheArray() {
		Stack<String> result = new Stack<String>();
		int x = endX;
		int y = endY;
		while (x != initX || y != initY) {
			if (rooms[x][y].getDirection() == 'U') {
				result.push("DOWN");
				y--;
			} else if (rooms[x][y].getDirection() == 'D') {
				result.push("UP");
				y++;
			} else if (rooms[x][y].getDirection() == 'L') {
				result.push("RIGHT");
				x--;
			} else if (rooms[x][y].getDirection() == 'R') {
				result.push("LEFT");
				x++;
			}
		}
		return result;
	}

	public boolean WayOut(room present) {
		
		if (present.getStatus() == 'E')
			return true;
	
		else if (present.getStatus() == 'S') {
			if(allUsed(present))
				return false;
			room temp = findSmallest(present);
			if (temp.getX() > present.getX())
				rooms[temp.getX()][temp.getY()].setDirection('L');
			if (temp.getX() < present.getX())
				rooms[temp.getX()][temp.getY()].setDirection('R');
			if (temp.getY() < present.getY())
				rooms[temp.getX()][temp.getY()].setDirection('D');
			if (temp.getY() > present.getY())
				rooms[temp.getX()][temp.getY()].setDirection('U');
			return WayOut(temp);
		}
		else if(allUsed(present)){
			return WayOut(getLastOne(present));
		}
		else if (isSmallest(present)) {
			if (allUsed(getLastOne(present))) {

				room temp = findSmallest(present);
				if (temp.getX() > present.getX())
					rooms[temp.getX()][temp.getY()].setDirection('L');
				if (temp.getX() < present.getX())
					rooms[temp.getX()][temp.getY()].setDirection('R');
				if (temp.getY() < present.getY())
					rooms[temp.getX()][temp.getY()].setDirection('D');
				if (temp.getY() > present.getY())
					rooms[temp.getX()][temp.getY()].setDirection('U');
				return WayOut(temp);
			} else
				
				return WayOut(getLastOne(present));
		} else {
			
			room temp = findSmallest(present);
			
			if (temp.getX() > present.getX())
				rooms[temp.getX()][temp.getY()].setDirection('L');
			if (temp.getX() < present.getX())
				rooms[temp.getX()][temp.getY()].setDirection('R');
			if (temp.getY() < present.getY())
				rooms[temp.getX()][temp.getY()].setDirection('D');
			if (temp.getY() > present.getY())
				rooms[temp.getX()][temp.getY()].setDirection('U');
			return WayOut(temp);
			
			
		}
	}

	public room getLastOne(room present) {
		int x = present.getX();
		int y = present.getY();
		char dir = present.getDirection();
		if (dir == 'L')
			return rooms[x - 1][y];
		else if (dir == 'R')
			return rooms[x + 1][y];
		else if (dir == 'U')
			return rooms[x][y - 1];
		else if (dir == 'D')
			return rooms[x][y + 1];

		return rooms[1][2];
	}

	private room findSmallest(room present) {
		int x = present.getX();
		int y = present.getY();
		int temp = 1000000;
		int finalX = 0;
		int finalY = 0;

		if (x + 1 <= sizeX) {
			if (rooms[x + 1][y].getCost() <= temp
					&& rooms[x + 1][y].getDirection() == ' ') {
				temp = rooms[x + 1][y].getCost();
				finalX = x + 1;
				finalY = y;
			}
		}
		if (y + 1 <= sizeY) {
			if (rooms[x][y + 1].getCost() <= temp
					&& rooms[x][y + 1].getDirection() == ' ') {
				temp = rooms[x][y + 1].getCost();
				finalX = x;
				finalY = y + 1;
			}
		}
		if (y - 1 >= 0) {
			if (rooms[x][y - 1].getCost() <= temp
					&& rooms[x][y - 1].getDirection() == ' ') {
				temp = rooms[x][y - 1].getCost();
				finalX = x;
				finalY = y - 1;
			}
			if (x - 1 >= 0) {
				if (rooms[x - 1][y].getCost() <= temp
						&& rooms[x - 1][y].getDirection() == ' ') {
					temp = rooms[x - 1][y].getCost();
					finalX = x - 1;
					finalY = y;
				}
			}
		}
		return rooms[finalX][finalY];

	}

	private boolean allUsed(room lastOne2) {
		int x = lastOne2.getX();
		int y = lastOne2.getY();

		if (y - 1 >= 0) {
			if (rooms[x][y - 1].getDirection() == ' ')
				return false;
		}
		if (y + 1 <= sizeY) {
			if (rooms[x][y + 1].getDirection() == ' ')
				return false;
		}
		if (x - 1 >= 0) {
			if (rooms[x - 1][y].getDirection() == ' ')
				return false;
		}
		if (x + 1 <= sizeX) {
			if (rooms[x + 1][y].getDirection() == ' ')
				return false;
		}

		return true;
	}

	public boolean isSmallest(room present) {

		int x = present.getX();

		int y = present.getY();

		if (x - 1 >= 0) {

			if (rooms[x - 1][y].getCost() <= rooms[x][y].getCost()
					&& rooms[x - 1][y].getDirection() == ' ')
				return false;
		}
		if (x + 1 <= sizeX) {

			if (rooms[x + 1][y].getCost() <= rooms[x][y].getCost()
					&& rooms[x + 1][y].getDirection() == ' ')
				return false;

		}
		if (y + 1 <= sizeY) {
			if (rooms[x][y + 1].getCost() <= rooms[x][y].getCost()
					&& rooms[x][y + 1].getDirection() == ' ')
				return false;
		}
		if (y - 1 >= 0) {
			if (rooms[x][y - 1].getCost() <= rooms[x][y].getCost()
					&& rooms[x][y - 1].getDirection() == ' ')
				return false;
		}

		return true;
	}
}
