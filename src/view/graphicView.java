package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.GameSource;
import model.GameSourceCollection;
import model.House;
import model.HouseCollection;
import model.ItemCollection;
import model.NaturalSource;
import model.NaturalSourceCollection;
import model.Settler;
import model.SettlerCollection;
import model.item;

public class graphicView extends JPanel {
	private NaturalSource[][] naturalSourceCollection;
	private GameSource[][] gameSourceCollection;
	private House[][] houseCollection;
	private ArrayList<item> itemCollection;
	private ArrayList<Settler> settlerCollection;
	private NaturalSourceCollection nature;
	private GameSourceCollection game;
	private ItemCollection item;
	private SettlerCollection settler;
	private HouseCollection house;
	private Timer t;
	private Image iron, stone, tree, sand, sea, gold, water, ground, block, worker, sleepingRoom, storage, Mutou, trap,
			niubi, boat;
	private Queue<String> command;
	private int timeBased;

	public graphicView(NaturalSourceCollection nature, GameSourceCollection game, ItemCollection item,
			SettlerCollection settler, HouseCollection house) {
		this.nature = nature;
		this.game = game;
		this.item = item;
		this.settler = settler;
		this.house = house;
		this.setBackground(Color.white);
		this.setSize(2000, 1800);
		this.setPreferredSize(new Dimension(2700, 2600));

		getAllImages();
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				int a = calendar.get(Calendar.HOUR_OF_DAY);

				if (a > 5 && a < 9) {
					timeBased = 0;
				} else if (a > 17 || a <= 5) {
					timeBased = 1;
				} else {
					timeBased = 2;
				}

			}
		});
		timer.start();
	}

	public void paintComponent(Graphics g) {
		gameSourceCollection = game.getGameSourceCollection();
		naturalSourceCollection = nature.getNaturalSourceCollection();
		itemCollection = item.getItemCollection();
		settlerCollection = settler.getSettlerCollection();
		houseCollection = house.gethouseCollection();

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {

				if (naturalSourceCollection[i][j] == NaturalSource.Water)
					g2.drawImage(water, i * 25, j * 25, 25, 25, this);
				if (naturalSourceCollection[i][j] == NaturalSource.Sand)
					g2.drawImage(sand, i * 25, j * 25, 25, 25, this);
				if (naturalSourceCollection[i][j] == NaturalSource.Sea)
					g2.drawImage(sea, i * 25, j * 25, 25, 25, this);
				if (naturalSourceCollection[i][j] == NaturalSource.Ground)
					g2.drawImage(ground, i * 25, j * 25, 25, 25, this);
				if (naturalSourceCollection[i][j] == NaturalSource.Brambles){
					g2.drawImage(ground, i * 25, j * 25, 25, 25, this);
					g2.drawImage(trap, i * 25, j * 25, 25, 25, this);
				}
				if (gameSourceCollection[i][j] != null) {

					if (gameSourceCollection[i][j].getChar() == 'S') {
						g2.drawImage(stone, i * 25, j * 25, 25, 25, this);
						g2.drawString(Integer.toString(gameSourceCollection[i][j].getValues()), i * 25, j * 25 + 25);
					}
					if (gameSourceCollection[i][j].getChar() == 'I') {
						g2.drawImage(iron, i * 25, j * 25, 25, 25, this);
						g2.drawString(Integer.toString(gameSourceCollection[i][j].getValues()), i * 25, j * 25 + 25);
					}
					if (gameSourceCollection[i][j].getChar() == 'G') {
						g2.drawImage(gold, i * 25, j * 25, 25, 25, this);
						g2.drawString(Integer.toString(gameSourceCollection[i][j].getValues()), i * 25, j * 25 + 25);
					}
					if (gameSourceCollection[i][j].getChar() == 'T') {
						g2.drawImage(tree, i * 25, j * 25, 25, 25, this);
						g2.drawString(Integer.toString(gameSourceCollection[i][j].getValues()), i * 25, j * 25 + 25);
					}

					if (gameSourceCollection[i][j].getChar() == 'M') {
						g2.drawImage(Mutou, i * 25, j * 25, 25, 25, this);
						g2.drawString(Integer.toString(gameSourceCollection[i][j].getValues()), i * 25, j * 25 + 25);
					}

				}

				if (houseCollection[i][j] == House.sleepingRoom) {
					g2.drawImage(sleepingRoom, i * 25, j * 25, 25, 25, this);

				}
				if (houseCollection[i][j] == House.storage) {
					g2.drawImage(storage, i * 25, j * 25, 25, 25, this);

				}

			}

		}
		for (int k = 0; k < settlerCollection.size(); k++) {
			double tempX = settlerCollection.get(k).getX() * 100 / 100.00;
			double tempY = settlerCollection.get(k).getY() * 100 / 100.00;
			int tempHealth = settlerCollection.get(k).getHealth();

			if (k != 0) {
				g2.drawRect((int) (tempX * 25), (int) (tempY * 25), tempHealth / 4, 1);
				g2.drawImage(worker, (int) (tempX * 25), (int) (tempY * 25), 25, 25, this);
			}
			if (k == 0) {
				g2.drawRect((int) (tempX * 25), (int) (tempY * 25), tempHealth / 4, 1);
				g2.drawImage(niubi, (int) (tempX * 25), (int) (tempY * 25), 25, 25, this);
			}
		}
		if (timeBased == 0) {
			int alpha = 100;
			Color cmorning = new Color(255, 194, 179, alpha);
			cmorning.brighter();
			g2.setColor(cmorning);
			g2.fillRect(0, 0, 2500, 2500);
		} else if (timeBased == 1) {
			int alpha = 100;
			Color night = new Color(0, 77, 0, alpha);
			night.brighter();
			g2.setColor(night);
			g2.fillRect(0, 0, 2500, 2500);
		} else if (timeBased == 2) {
			int alpha = 30;
			Color noon = new Color(255, 255, 51, alpha);
			noon.brighter();
			g2.setColor(noon);
			g2.fillRect(0, 0, 2500, 2500);
		}
		if(settlerCollection.size()!=0&&settlerCollection.get(0).GetReadyToGo()){
			double tempX = settlerCollection.get(0).getX()*100/100.00;
			double tempY = settlerCollection.get(0).getY()*100/100.00;
			g2.drawImage(boat, (int)(tempX*25)-100 , (int)(tempY*25)-150 , 200, 200, this);
		}

	}

	private void getAllImages() {
		try {
			iron = ImageIO.read(new File("src/cristalmine.png"));
			stone = ImageIO.read(new File("src/stone.jpg"));
			sand = ImageIO.read(new File("src/sand.jpg"));
			Mutou = ImageIO.read(new File("src/treeset2.png"));
			sea = ImageIO.read(new File("src/newsea.png"));
			water = ImageIO.read(new File("src/newwater.jpg"));
			ground = ImageIO.read(new File("src/ground.jpg"));
			block = ImageIO.read(new File("src/block.jpg"));
			worker = ImageIO.read(new File("src/newviking.png"));
			sleepingRoom = ImageIO.read(new File("src/house1.png"));
			storage = ImageIO.read(new File("src/storage.png"));
			tree = ImageIO.read(new File("src/foods.png"));
			trap = ImageIO.read(new File("src/spikeweed.png"));
			niubi = ImageIO.read(new File("src/worker.jpg"));
			boat =  ImageIO.read(new File("src/boat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
