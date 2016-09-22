package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

public class Settler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hunger;
	private int thirsty;
	private int health;
	private double X;
	private double Y;
	private int endX;
	private int endY;
	private String Source;
	private int container;
	private int count = 0;
	private ItemCollection item;
	private Timer animation;
	private boolean ReadyToGo;

	private Queue<String> command = new LinkedList<String>();;

	public Settler(int hunger, int thirsty, int health, double X, double Y, int endX, int endY, String Source, int container, ItemCollection item, boolean ReadyToGo) {
		this.hunger = hunger;
		this.thirsty = thirsty;
		this.health = health;
		this.X = X;
		this.Y = Y;
		this.endX = endX;
		this.endY = endY;
		this.Source = Source;
		this.container = container;
		this.item = item;
		this.ReadyToGo = ReadyToGo;
	}

	public Queue<String> getCommand() {
		return command;
	}

	public int getHunger() {
		return hunger;
	}
    public void ReadyToGo(){
    	ReadyToGo = true;
    }
    public boolean GetReadyToGo(){
    	return ReadyToGo;
    }
	public int getThirsty() {
		return thirsty;
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setLocation(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public void setEndLocation(int X, int Y) {
		this.endX = X;
		this.endY = Y;
	}

	public void add(String next) {
		command.add(next);
	}

	public void move() {
		animation = new Timer(20, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (command.size() != 0) {
					if (command.peek().equals("UP")) {
						Y = Y - 4 / 100.00;
						count++;
					} else if (command.peek().equals("DOWN")) {
						Y = Y + 4 / 100.00;
						count++;
					} else if (command.peek().equals("LEFT")) {
						X = X - 4 / 100.00;
						count++;
					} else if (command.peek().equals("RIGHT")) {
						X = X + 4 / 100.00;
						count++;
					} else {
						count++;
					}

				}
				if (count >= 25) {
					count = 0;
					if (command.peek().equals("tree")) {
						hunger = hunger + 500;
					//	GameRunner.Playsongs(0);
					} else if (command.peek().equals("water")) {
						thirsty = thirsty + 500;
						//GameRunner.Playsongs(1);
					} else if (command.peek().equals("Stone")) {
						if(item.contain("Hammer")){
							if (Source != null && Source.equals("Stone")) {
								
								addContainer();
								addContainer();
							} else {
								setType("Stone");
								container = 0;
								addContainer();
								addContainer();
							}
							
						}
						else{
						if (Source != null && Source.equals("Stone")) {
							addContainer();
						} else {

							setType("Stone");
							container = 0;
							addContainer();
						}
						}
					} else if (command.peek().equals("Mutou")) {
					if(item.contain("Saw")){
						if (Source != null && Source.equals("Mutou")) {
							addContainer();
							addContainer();
						} else {
							setType("Mutou");
							container = 0;
							addContainer();
							addContainer();
						}
				//		GameRunner.Playsongs(2);
					}
					else{
						if (Source != null && Source.equals("Mutou")) {
							addContainer();
							
						} else {
							setType("Mutou");
							container = 0;
							addContainer();
							
						}
				//		GameRunner.Playsongs(2);
					}
					
					
					} else if (command.peek().equals("Iron")) {
						if(item.contain("Spade")){
							if (Source != null && Source.equals("Iron")) {
								
								addContainer();
								addContainer();
							} else {
								setType("Iron");
								container = 0;
								addContainer();
								addContainer();
							}
							
						}
						else{
						if (Source != null && Source.equals("Iron")) {
							addContainer();
						} else {

							setType("Iron");
							container = 0;
							addContainer();
						}

					}
				}

					else if (command.peek().equals("build")) {
					}
					else if(command.peek().equals("Leave")){
						ReadyToGo();
					}
					command.poll();
					hunger--;
					thirsty--;
					animation.stop();
				}
			}
		});
		animation.start();
	}

	public void addContainer() {
		container++;
	}

	public int getContainer() {
		return container;
	}

	public void setType(String temp) {
		Source = temp;
	}

	public String getType() {
		return Source;
	}

	public void clean() {
		Source = null;
		container = 0;
	}

	public void setCount() {
		count = 25;
	}
	
	public void setHunger1(){
		hunger = 0;
	}
	public void setHunger2(){
		hunger = 101;
	}public void setHunger3(){
		hunger = 500;
	}
	public void setThirsty1(){
		thirsty = 0;
	}
	public void setThirsty2(){
		thirsty = 101;
	}
	public void setThirsty3(){
		thirsty = 500;
	}
	public void setContainer1(){
		container = 0;
	}
	public void setContainer2(){
		container = 101;
	}

	public int getHealth() {
		return health;
	}
	public void minusHealth(){
		health = health-20;
	}
	

}
