package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class ItemCollection extends Observable implements Serializable{
	
	private ArrayList<item> itemCollection;
	public ItemCollection() {
		setup();
	}

	public void setup() {
		itemCollection = new ArrayList<item>();
		itemCollection.add(new deck());
	}
	public ArrayList<item> getItemCollection(){
		return itemCollection;
	}

	public boolean contain(String string) {
		for(int i=0;i<itemCollection.size();i++){
			if(itemCollection.get(i).getName().equals(string))
				return true;
		}
		return false;
	}
	
	public void additem(item a){
		itemCollection.add(a);
	}
}
