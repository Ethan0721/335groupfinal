package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class SettlerCollection implements Serializable{
	private ArrayList<Settler> SettlerCollection;
	private int endX;
	private int endY;
	private NaturalSourceCollection nature;
	private GameSourceCollection game;
	private HouseCollection houses;

	private ItemCollection item;
	private pathFinding path;
	private ArrayList<String> forFind;

	
	public SettlerCollection(NaturalSourceCollection nature,
			GameSourceCollection game, ItemCollection item,
			HouseCollection houses) {
		this.nature = nature;
		this.game = game;
		this.item = item;
		this.houses = houses;
	
		path = new pathFinding(this.nature, this.game, this.item, this.houses);
		setup();
	}

	public void setup() {
		SettlerCollection = new ArrayList<Settler>();
		forFind = new ArrayList<String>();
		generateNewSettler();
		
	}
	private void generateNewSettler() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(houses.gethouseCollection()[i][j] == House.sleepingRoom){
					if(game.getGameSourceCollection()[i-1][j]==null&&(nature.getNaturalSourceCollection()[i-1][j]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i-1][j]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300,100, i-1, j, 0, 0, null, 0, item, false));
						
					} 
					if(game.getGameSourceCollection()[i+1][j]==null&&(nature.getNaturalSourceCollection()[i+1][j]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i+1][j]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300,100, i+1, j, 0, 0, null, 0, item, false));
						
					}
					if(game.getGameSourceCollection()[i][j-1]==null&&(nature.getNaturalSourceCollection()[i][j-1]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i][j-1]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300,100, i, j-1, 0, 0, null, 0, item, false));
						
					}
					if(game.getGameSourceCollection()[i][j+1]==null&&(nature.getNaturalSourceCollection()[i][j+1]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i][j+1]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300,100, i, j+1, 0, 0, null, 0, item, false));
						
					}
				}
			}
		}
	}
	public void createANewSettler() {

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(houses.gethouseCollection()[i][j] == House.sleepingRoom){
					if(game.getGameSourceCollection()[i-1][j]==null&&(nature.getNaturalSourceCollection()[i-1][j]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i-1][j]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300, 100, i-1, j, 0, 0, null, 0, item, false));
						break;
					}
					if(game.getGameSourceCollection()[i+1][j]==null&&(nature.getNaturalSourceCollection()[i+1][j]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i+1][j]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300, 100, i-1, j, 0, 0, null, 0, item, false));
						break;
					}
					if(game.getGameSourceCollection()[i][j-1]==null&&(nature.getNaturalSourceCollection()[i][j-1]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i][j-1]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300, 100, i-1, j, 0, 0, null, 0, item, false));
						break;
					}
					if(game.getGameSourceCollection()[i][j+1]==null&&(nature.getNaturalSourceCollection()[i][j+1]==NaturalSource.Ground||nature.getNaturalSourceCollection()[i][j+1]==NaturalSource.Sand)){
						SettlerCollection.add(new Settler(300, 300, 100, i-1, j, 0, 0, null, 0, item, false));
						break;
					}
				}
			}
		}
		
	}

	public ArrayList<Settler> getSettlerCollection() {
		return SettlerCollection;
	}
	public void forFindHelper(int i, int j){
		forFind.add(i+" "+j);
	}
	
	
	public void getNewCommand(int index, String careerString, boolean Leave) {

		if (SettlerCollection.get(index).getHunger() <= 200) {
			SearchForWood(index);
			Stack<String> temp;
			while (!path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY)){
//				forFind.add(endX + " " + endY);
				forFindHelper(endX, endY);
				SearchForWood(index);
				System.out.print("abc");
			}
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("tree");
			forFind.clear();
		}

		else if (SettlerCollection.get(index).getThirsty() <= 200) {
			SearchForWater(index);
			Stack<String> temp;
			while (!path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY)){
				forFindHelper(endX, endY);
				SearchForWater(index);
				System.out.print("abc");
			}
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("water");
			forFind.clear();
		}
		else if(Leave){
			SearchForSea(index);
			Stack<String> temp;
			while (!path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY)){
				forFindHelper(endX, endY);
				SearchForSea(index);
				System.out.print("abc");
			}
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("Leave");
			forFind.clear();
		}
		else if (!houses.getWaitingList().isEmpty()) {
			building temp1 = houses.getWaitingList().poll();
			Stack<String> temp;
			endX = temp1.getX();
			endY = temp1.getY();
			path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY);
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			if (temp1.getType().equals("Storage"))
				SettlerCollection.get(index).add("storage");
			if (temp1.getType().equals("SleepingHouse"))
				SettlerCollection.get(index).add("sleepingHouse");
			SettlerCollection.get(index).add("..");
		}

		else if (SettlerCollection.get(index).getContainer() >= 10
				&& !houses.anyStorage()) {
			SettlerCollection.get(index).add("..");
		} 
		else if (SettlerCollection.get(index).getContainer() >= 10) {
			SearchForHouse(index);
			Stack<String> temp;
			while (!path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY)){
				forFind.add(endX + " " + endY);
				SearchForHouse(index);
				System.out.print("abc");
			}
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("findStorage");
			forFind.clear();
		} else {
			
			if(careerString == null)
				careerString = "Mutou";
			SearchFor(careerString,index);
			Stack<String> temp;
			while (!path.getPath((int)Math.round(SettlerCollection.get(index).getX()),
					(int)Math.round(SettlerCollection.get(index).getY()), endX, endY)) {
				forFind.add(endX + " " + endY);
				SearchFor(careerString, index);
				System.out.print("abc");
			}
			temp = path.getTheArray();
			while (temp.size() > 1) {
				SettlerCollection.get(index).add(temp.pop());
			}
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add("..");
			SettlerCollection.get(index).add(careerString);
			forFind.clear();
		}

	}

	public void SearchForHouse(int index) {
		Settler settler = SettlerCollection.get(index);
		int X = (int)settler.getX();
		int Y = (int)settler.getY();
		int distence = 20000;
		int temp = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (houses.gethouseCollection()[i][j] != null
						&& houses.gethouseCollection()[i][j] == House.storage) {
					temp = Math.abs(X - i) + Math.abs(Y - j);
					if (temp < distence) {
						distence = temp;
						endX = i;
						endY = j;
					}
				}
			}
		}
	}

	public void SearchFor(String type, int index) {
		Settler settler = SettlerCollection.get(index);
		int X = (int)settler.getX();
		int Y = (int)settler.getY();
		int distence = 20000;
		int temp = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (game.getGameSourceCollection()[i][j] != null
						&& game.getGameSourceCollection()[i][j].getName()
								.equals(type)
						&& !forFind.contains(i + " " + j)) {
					temp = Math.abs(X - i) + Math.abs(Y - j);
					if (temp < distence) {
						distence = temp;
						endX = i;
						endY = j;
					}
				}
			}
		}

	}

	public void SearchForWood(int index) {
		Settler settler = SettlerCollection.get(index);
		int X = (int)settler.getX();
		int Y = (int)settler.getY();
		int distence = 20000;
		int temp = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (game.getGameSourceCollection()[i][j] != null
						&& game.getGameSourceCollection()[i][j].getName()
								.equals("Tree")
						&& !forFind.contains(i + " " + j)) {
					temp = Math.abs(X - i) + Math.abs(Y - j);
					
					if (temp < distence) {
						distence = temp;
						endX = i;
						endY = j;
					}
				}
			}
		}

	}

	public void SearchForWater(int index) {
		Settler settler = SettlerCollection.get(index);
		int X = (int)settler.getX();
		int Y = (int)settler.getY();
		int distence = 20000;
		int temp = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (nature.getNaturalSourceCollection()[i][j] == NaturalSource.Water
						&& !forFind.contains(i + " " + j)) {
					temp = Math.abs(X - i) + Math.abs(Y - j);
					if (temp < distence) {
						distence = temp;
						endX = i;
						endY = j;
					}
				}
			}
		}
	}
	public void SearchForSea(int index) {
		Settler settler = SettlerCollection.get(index);
		int X = (int)settler.getX();
		int Y = (int)settler.getY();
		int distence = 20000;
		int temp = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (nature.getNaturalSourceCollection()[i][j] == NaturalSource.Sea
						&& !forFind.contains(i + " " + j)) {
					temp = Math.abs(X - i) + Math.abs(Y - j);
					if (temp < distence) {
						distence = temp;
						endX = i;
						endY = j;
					}
				}
			}
		}
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
	public void TestHunger1(int index){
		SettlerCollection.get(index).setHunger1();
	}
	public void TestHunger2(int index){
		SettlerCollection.get(index).setHunger2();
	}
	public void TestThirsty1(int index){
		SettlerCollection.get(index).setThirsty1();
	}
	public void TestThirsty2(int index){
		SettlerCollection.get(index).setThirsty2();
	}
	public void TestContainer1(int index){
		SettlerCollection.get(index).setContainer1();
	}
	public void TestContainer2(int index){
		SettlerCollection.get(index).setContainer2();
	}
	

}
