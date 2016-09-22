package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class GameSourceCollection implements Serializable{
	private  int[][] result;
	//private Random rnd;
	private GameSource[][] gameSourceCollection;
	private NaturalSource[][] nature;
	public GameSourceCollection(NaturalSourceCollection nature){
		this.nature = nature.getNaturalSourceCollection();
	setup();
	}
	public void setup(){
		creatTree();
	
		gameSourceCollection = new GameSource[100][100];
	    Random rnd = new Random();
		int arr[][] = new int [100][100];

		for(int i = 0; i<20; i++){
	    	int b = rnd.nextInt(50)+20;
			int c = rnd.nextInt(60)+20;
			arr[b][c] = 1;
			
		}

    	int stone[][] = new int [100][100];	
    	int q = 0;
			
    		
			while(q<15){
    			int a = rnd.nextInt(70)+10;
    			int b = rnd.nextInt(70)+10;
    			stone[a][b] =1;
    			int c = rnd.nextInt(2);
    			if(c==1)
    			stone[a+1][b]=1;
    			c = rnd.nextInt(2);
    			if(c==1)
    			stone[a-1][b]=1;
    			c = rnd.nextInt(2);
    			if(c==1)
    			stone[a][b+1]=1;
    			c = rnd.nextInt(2);
    			if(c==1)
    			stone[a][b-1]=1;
    			q++;
			}
			
		for(int i=0;i<100;i++){
			for(int j=0;j<100;j++){
				if(nature[i][j] == NaturalSource.Ground&&result[i][j]==1)
					gameSourceCollection[i][j] = new SourceTree(500);
				else if(nature[i][j] == NaturalSource.Ground&&result[i][j]==2)
					gameSourceCollection[i][j] = new Tree(500);
				else if(nature[i][j] == NaturalSource.Ground&&arr[i][j]==1)
					gameSourceCollection[i][j] = new Iron(500);
				else if(nature[i][j] == NaturalSource.Ground&&stone[i][j]==1)
					gameSourceCollection[i][j] = new Stone(500);
				
			}
			}


	
	}

public GameSource[][] getGameSourceCollection(){
	return gameSourceCollection;
}
public void SetForTest(GameSource[][] gameSourceCollection){
	this.gameSourceCollection  = gameSourceCollection;
}
public void creatTree(){

		Random rnd = new Random();
		int a1 = rnd.nextInt(4) + 15;
        result = new int[100][100];
        
		int[] arrX = new int[a1];
		int[] arrY = new int[a1];

		for (int i = 0; i < a1; i++) {
			arrX[i] = (int) (Math.random() * 100);
			arrY[i] = (int) (Math.random() * 100);
			
			for (int j = 0; j < i; j++) {
				if (arrX[i] == arrX[j] && arrY[i] == arrY[j]) {
					i--;
					break;
				}
			}
		}
		int c1 = rnd.nextInt(5) + 100;
		int count = 0;
		int[] newarrX = new int[c1 * a1];
		int[] newarrY = new int[c1 * a1];

		for (int j = 0; j < a1; j++) {
			newarrX[j] = arrX[j];
			newarrY[j] = arrY[j];
			add(newarrX[j], newarrY[j]);
		}

		for (int j = 1; j < c1; j++) {

			for (int i = 0; i < a1; i++) {

				int b1 = rnd.nextInt(3);
				int b2 = rnd.nextInt(3);

				if(arrX[i] < 0 )
					arrX[i] = 0;
				if(arrX[i] >99 )
					arrX[i] = 99;

				if(arrY[i] < 20 )
					arrY[i] = 15;
				if(arrY[i] >75 )
					arrY[i] = 70;
				
				if (b1 == 0) {
					arrX[i] = arrX[i] - 1;
				} else if (b1 == 1) {
					arrX[i] = arrX[i] + 1;

				} else if (b1 == 2) {
					arrX[i] = arrX[i];
				}

				if (b2 == 0) {
					arrY[i] = arrY[i] - 1;

				} else if (b2 == 1) {
					arrY[i] = arrY[i]  +1;

				} else if (b2 == 2) {
					arrY[i] = arrY[i];
				}

				if(arrX[i] < 0 )
					arrX[i] = 0;
				if(arrX[i] >99 )
					arrX[i] = 99;

				if(arrY[i] < 0 )
					arrY[i] = 0;
				if(arrY[i] >99 )
					arrY[i] = 99;
				
				newarrX[j * a1 + i] = arrX[i];
				newarrY[j * a1 + i] = arrY[i];
				add(arrX[i], arrY[i]);
				count++;
			}
		}

	}
	private  void add(int i, int j) {
		 Random rnd = new Random();
		int number = rnd.nextInt(3);
		
		if(number == 0)
			result[i][j] = 1;
		else 
			result[i][j] = 2;

	}
	public boolean Iswalkable(int i, int j){
		return gameSourceCollection[i][j]==null&&(nature[i][j]==NaturalSource.Brambles||nature[i][j]==NaturalSource.Ground||nature[i][j]==NaturalSource.Sand);
	}

}
