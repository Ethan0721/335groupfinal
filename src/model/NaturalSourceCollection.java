 package model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

public class NaturalSourceCollection implements Serializable{
	private NaturalSource[][] naturalSourceCollection;

	public NaturalSourceCollection() {
		setup();
	}

	public void setup() {
		naturalSourceCollection = new NaturalSource[100][100];
		for (int i = 0; i < 40 * 2; i++)
			for (int j = 0; j < 40 * 2; j++) {
				int d = (int) Math.sqrt((i - 40) * (i - 40) + (j - 40) * (j - 40));
				if (d < 40)
					naturalSourceCollection[i + 10][j + 10] = NaturalSource.Sand;
			}
		for (int i = 0; i < 37 * 2; i++)
			for (int j = 0; j < 37 * 2; j++) {
				int d = (int) Math.sqrt((i - 37) * (i - 37) + (j - 37) * (j - 37));
				if (d < 37)
					naturalSourceCollection[i + 13][j + 13] = NaturalSource.Ground;
			}
		creatWater();
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (naturalSourceCollection[i][j]==null)
					naturalSourceCollection[i][j] = NaturalSource.Sea;
			}
		}
		Random rnd = new Random();
		for(int i=0; i<40;i++){
			int tempa = rnd.nextInt(100);
			int tempb = rnd.nextInt(100);
			while(naturalSourceCollection[tempa][tempb]!=NaturalSource.Ground){
				tempa = rnd.nextInt(100);
				tempb = rnd.nextInt(100);
			}
			naturalSourceCollection[tempa][tempb] = NaturalSource.Brambles;
		}
	
		
	}

	private void creatWater() {
		int arr[][] = new int [100][100];
		Random rnd = new Random();
		for(int q =0; q <4; q++){
		           int a = rnd.nextInt(40)+20;
		           int b = rnd.nextInt(40)+20;
		        for (int i = (-a); i < (100-a); i++) {
		            for (int j = (-b); j < (100-b); j++) {
		                if (i*i + j*j <= 4*4)
		                	arr[i+a][j+b] =1; 
		            }
		        }
		    }
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(arr[i][j]==1&&naturalSourceCollection[i][j] == NaturalSource.Ground)
					naturalSourceCollection[i][j] = NaturalSource.Water;
			}
		}
		
		    }   

	public NaturalSource[][] getNaturalSourceCollection() {
		return naturalSourceCollection;
	}

	public boolean IsGround(int x, int y) {
		if (naturalSourceCollection[x][y] == NaturalSource.Ground)
			return true;
		else
			return false;

	}



	public boolean IsSea(int x, int y) {
		if (naturalSourceCollection[x][y] == NaturalSource.Sea)
			return true;
		else
			return false;	}

}