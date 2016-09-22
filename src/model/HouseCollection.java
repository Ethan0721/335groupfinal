package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class HouseCollection implements Serializable {
	private House[][] housecollection;
	private NaturalSource[][] naturalSourceCollection;
	private GameSource[][] gameSourceCollection;
	private ArrayList<item> itemCollection;
	private NaturalSourceCollection nature;
	private GameSourceCollection game;
	private ItemCollection item;
	private int x1;
	private int y1;
	private AudioInputStream hammeraudioIn = null;
	private File hammerFile;
	private Clip clip = null;

	private Queue<building> waitingList = new LinkedList<building>();
	
	public HouseCollection(NaturalSourceCollection nature,
			GameSourceCollection game, ItemCollection item) {
		this.nature = nature;
		this.game = game;
		this.item = item;
		gameSourceCollection = game.getGameSourceCollection();
		naturalSourceCollection = nature.getNaturalSourceCollection();
		itemCollection = item.getItemCollection();
		hammerFile = new File("src/songfiles/hammer.wav");

		setup();
	}
	
	private void setup() {

		
		housecollection = new House[100][100];
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				housecollection[i][j] = null;
			}
		}
		Random rdn = new Random();
		int randX = rdn.nextInt(50);
		int randY = rdn.nextInt(50);
		while(nature.getNaturalSourceCollection()[randX][randY]!=NaturalSource.Ground||game.getGameSourceCollection()[randX][randY]!=null){
			randX = rdn.nextInt(50);
			randY = rdn.nextInt(50);
		}
		
		housecollection[randX][randY] = House.sleepingRoom;
		
		
	}
	public Queue<building> getWaitingList(){
		return waitingList;
	}
	public void addToWaitingList(int x, int y, String type){
		waitingList.add(new building(x, y, type));
	}
	public void addASleepingRoom(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
		if (CanBuildIt(x1, y1)) {
			housecollection[x1][y1] = House.sleepingRoom;
			


		}

	}

	public void addAStorage(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
		if (CanBuildIt(x1, y1)) {
			housecollection[x1][y1] = House.storage;
		}

	}

	public boolean CanBuildIt(int i, int j) {
		if (naturalSourceCollection[i][j] == NaturalSource.Ground) {
			if(housecollection[i][j]!=House.storage && housecollection[i][j]!=House.sleepingRoom){
				if(gameSourceCollection[i][j]==null)
					return true;
			}
			 
		}

		return false;
	}

	public House[][] gethouseCollection() {
		return housecollection;
	}

	public boolean anyStorage() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(housecollection[i][j] == House.storage)
					return true;
			}
		}
		return false;
	}
	public boolean anySleepingHouse() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(housecollection[i][j] == House.sleepingRoom)
					return true;
			}
		}
		return false;
	}

	
}
