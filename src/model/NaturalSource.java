package model;

import java.io.Serializable;

/**
 * the natural source which has no values belongs to them 
 * it includes water/ sand / block / ground /sea
 * 
 * 1. Except ground no buildings can build on them. 
 * 2. Sea around this small island u can't excape if you don't make your own ship.
 * 3. Water is necessay for the role in every day and there is no max limit for the water.
 * 4. You can only drink water not sea.
 * 5. You can walk through on sand but you can't walk though block. 
 * 
 * Author:
 */

public enum NaturalSource implements Serializable {
	Water(),
	Sand(),
	Block(),
	Sea(),
	Ground(),
	Brambles();
	

	
	
	private NaturalSource (){
		
	}
	
}
