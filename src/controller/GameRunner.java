package controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import model.GameSourceCollection;
import model.Hammer;
import model.House;
import model.HouseCollection;
import model.ItemCollection;
import model.NaturalSource;
import model.NaturalSourceCollection;
import model.Saw;
import model.Settler;
import model.SettlerCollection;
import model.Spade;
import model.cleat;
import model.deck;
import model.hull;
import model.anchor;
import model.mainsail;
import model.rudder;

import view.graphicView;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

public class GameRunner {

	public static void main(String[] args) {
		new GameRunner();

	}

	private Timer t;
	private Timer timerSource;
	public JFrame frame1 = new JFrame("Frame 1");
	private JButton Continue, NewGame, Quit, HammerButton, SawButton, SpadeButton;
	private JButton rudderButton, anchorButton, deckButton, sailButton, hullButton, cleatButton;
	private BufferedImage bf, bfsecond;
	private Image alltree, allstone, alliron;
	private ImageIcon continueImage, quitImage, newGameImage, hammerItem, sawItem, spadeItem;
	private ImageIcon rudderItem, anchorItem, deckItem, sailItem, hullItem, cleatItem;
	private String careerString;
	private int clock = 0;
	private Timer timer = null;
	private Timer animation = null;
	private NaturalSourceCollection nature;
	private GameSourceCollection game;
	private ItemCollection item;
	private HouseCollection houses;
	private SettlerCollection settler;
	private JPanel view;
	private LocalDate startingday;
	private LocalDate currentday;
	private static final String SAVED_COLLECTION_LOCATION = "savedState";
	private JPanel statusPanel;
	private boolean ReadyToGo = false;

	public GameRunner() {
		frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame1.setSize(1200, 840);
		frame1.setLocation(100, 20);
		frame1.setLayout(null);
		// setUp();
		getAllImage();

		try {
			bf = ImageIO.read(new File("src/a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
            File soundFile = new File("src/lovely.wav");
            AudioInputStream audioIn = AudioSystem
            .getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }


		frame1.setContentPane(new backImage(bf));

		Continue = new JButton();
		Continue.setIcon(continueImage);
		Continue.setLocation(698, 60);
		Continue.setSize(480, 120);

		Continue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					FileInputStream inFile = new FileInputStream(SAVED_COLLECTION_LOCATION);
					ObjectInputStream inputStream = new ObjectInputStream(inFile);
					nature = (NaturalSourceCollection) inputStream.readObject();
					game = (GameSourceCollection) inputStream.readObject();
					item = (ItemCollection) inputStream.readObject();
					houses = (HouseCollection) inputStream.readObject();
					settler = (SettlerCollection) inputStream.readObject();
					startingday = (LocalDate) inputStream.readObject();
					view = new graphicView(nature, game, item, settler, houses);

					inputStream.close();
					inFile.close();

				} catch (Exception exception) {
					exception.printStackTrace();
					startingday = LocalDate.now();
					nature = new NaturalSourceCollection();
					game = new GameSourceCollection(nature);
					item = new ItemCollection();
					houses = new HouseCollection(nature, game, item);
					settler = new SettlerCollection(nature, game, item, houses);
					view = new graphicView(nature, game, item, settler, houses);
				}
				frame1.setVisible(false);
				frame1.dispose();
				new SecondFrame();

			}

		});

		NewGame = new JButton();

		NewGame.setLocation(700, 180);
		NewGame.setSize(480, 120);
		NewGame.setIcon(newGameImage);
		NewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startingday = LocalDate.now();
				nature = new NaturalSourceCollection();
				game = new GameSourceCollection(nature);
				item = new ItemCollection();
				houses = new HouseCollection(nature, game, item);
				settler = new SettlerCollection(nature, game, item, houses);
				view = new graphicView(nature, game, item, settler, houses);

				frame1.setVisible(false);
				frame1.dispose();
				new SecondFrame();

			}

		});
		Quit = new JButton();
		Quit.setLocation(700, 300);
		Quit.setSize(480, 120);
		Quit.setIcon(quitImage);
		Quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame1.setVisible(false);
				frame1.dispose();
			}

		});

		frame1.add(Continue);
		frame1.add(NewGame);
		frame1.add(Quit);

		frame1.validate();
		frame1.setVisible(true);

	}

	private class WindowClosingListener extends WindowAdapter {

		public void windowClosing(WindowEvent we) {
			int selectedChoice = JOptionPane.showConfirmDialog(null, "Would you like to save your game progress?",
					"Application openning", JOptionPane.YES_NO_OPTION);
			if (selectedChoice == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else {
				try {
					FileOutputStream fos = new FileOutputStream(SAVED_COLLECTION_LOCATION);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(nature);
					oos.writeObject(game);
					oos.writeObject(item);
					oos.writeObject(houses);
					oos.writeObject(settler);
					oos.writeObject(startingday);
					oos.close();
					fos.close();

				} catch (Exception exception) {
					exception.printStackTrace();
				}
				System.exit(0);
			}
		}

	}

	private void getAllImage() {

		newGameImage = new ImageIcon("src/NewGame.jpg");
		quitImage = new ImageIcon("src/Quit.jpg");
		continueImage = new ImageIcon("src/newContinue.png");

		hammerItem = new ImageIcon("src/hammerItem.jpg");
		sawItem = new ImageIcon("src/sawItem.jpg");
		spadeItem = new ImageIcon("src/spadeItem.jpg");

		rudderItem = new ImageIcon("src/rudder.png");
		anchorItem = new ImageIcon("src/anchor.png");
		deckItem = new ImageIcon("src/deck.jpg");
		sailItem = new ImageIcon("src/sail.png");
		hullItem = new ImageIcon("src/bull.jpg");
		cleatItem = new ImageIcon("src/slate.png");

		try {
			alltree = ImageIO.read(new File("src/alltree.jpg"));
			allstone = ImageIO.read(new File("src/allstone.jpg"));
			alliron = ImageIO.read(new File("src/alliron.jpg"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class SecondFrame {

		private JFrame f = new JFrame("Settlement");
		private JScrollPane scrollp;
		private JMenuItem exit;

		public SecondFrame() {
			setUp();

		}

		private void setUp() {
			showMenuBar();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
			f.setSize(screensize.width - 10, screensize.height - 20);
			int x = (int) ((screensize.getWidth() - f.getWidth()) / 2);
			int y = (int) ((screensize.getHeight() - f.getHeight()) / 2);
			f.setLocation(x, y);
			f.setLayout(null);
			f.setVisible(true);

			scrollp = new JScrollPane(view);
			scrollp.setSize(screensize.width - 10, screensize.height - 90);
			scrollp.setLocation(200, 0);
			f.add(scrollp);

			statusPanel = new JPanel();
			statusPanel.setBounds(0, 0, 200, screensize.height - 90);

			statusPanel.setLayout(null);
			setUpStatuePanel();

			f.add(statusPanel);
			WindowClosingListener windowClosingListener = new WindowClosingListener();
			f.addWindowListener(windowClosingListener);
			timer = new Timer(500, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					forMoving();
				}

			});
			timer.start();

		}

		private void setUpStatuePanel() {
			Font font = new Font("Verdana", Font.BOLD, 20);

			JLabel agent = new JLabel("Agent status ");
			agent.setBounds(20, 0, 200, 50);
			agent.setFont(font);

			JLabel currentTime = new JLabel("Current time : ");
			currentTime.setBounds(20, 40, 100, 30);

			JLabel currentTimeText = new JLabel(
					new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
			currentTimeText.setBounds(120, 40, 100, 30);

			JLabel currentDate = new JLabel("Current data : ");
			currentDate.setBounds(20, 70, 100, 30);

			JLabel currentDateText = new JLabel(
					new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
			currentDateText.setBounds(120, 70, 100, 30);

			JLabel dayspast = new JLabel("Days you survived : ");
			dayspast.setBounds(20, 100, 130, 30);

			JLabel dayspastText = new JLabel();
			dayspastText.setBounds(150, 100, 100, 30);

			JLabel thirsty = new JLabel("Thirsty level : ");
			thirsty.setBounds(20, 130, 100, 30);

			JLabel tText = new JLabel();
			tText.setBounds(120, 130, 100, 30);

			JLabel hungry = new JLabel("Hungry level :");
			hungry.setBounds(20, 160, 100, 30);

			JLabel hText = new JLabel();
			hText.setBounds(120, 160, 100, 30);

			t = new Timer(100, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (settler.getSettlerCollection().size() != 0) {
						tText.setText(Integer.toString(settler.getSettlerCollection().get(0).getThirsty()));
						hText.setText(Integer.toString(settler.getSettlerCollection().get(0).getHunger()));
					}
					currentTimeText.setText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					currentDateText
							.setText(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));

					currentday = LocalDate.now();
					if (currentday != startingday) {

						Date a = java.sql.Date.valueOf(currentday);
						Date b = java.sql.Date.valueOf(startingday);

						long difference = a.getTime() - b.getTime();
						long dif = difference / (1000 * 60 * 60 * 24) + 1;

						dayspastText.setText(String.valueOf(dif));
					}
				}
			});

			t.start();
			statusPanel.add(agent);
			statusPanel.add(thirsty);
			statusPanel.add(tText);
			statusPanel.add(hungry);
			statusPanel.add(hText);
			statusPanel.add(dayspast);
			statusPanel.add(currentDate);
			statusPanel.add(currentTime);
			statusPanel.add(currentDateText);
			statusPanel.add(currentTimeText);
			statusPanel.add(dayspastText);

			JLabel ItemStatus = new JLabel("Item Status");
			ItemStatus.setBounds(20, 185, 200, 50);
			ItemStatus.setFont(font);

			ImageIcon a = new ImageIcon(alltree);
			JLabel treeIcon = new JLabel();
			treeIcon.setIcon(a);
			treeIcon.setBounds(20, 230, 30, 30);

			JLabel TreeText = new JLabel();
			TreeText.setBounds(80, 230, 80, 25);

			a = new ImageIcon(allstone);
			JLabel stoneIcon = new JLabel();
			stoneIcon.setIcon(a);
			stoneIcon.setBounds(20, 265, 30, 30);

			JLabel StoneText = new JLabel();
			StoneText.setBounds(80, 265, 80, 25);

			a = new ImageIcon(alliron);
			JLabel ironIcon = new JLabel();
			ironIcon.setIcon(a);
			ironIcon.setBounds(20, 300, 30, 30);

			JLabel IronText = new JLabel();
			IronText.setBounds(80, 300, 80, 25);

			timerSource = new Timer(100, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int tree = 0, iron = 0, stone = 0;

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}
						}
					}
					TreeText.setText(Integer.toString(tree));
					IronText.setText(Integer.toString(iron));
					StoneText.setText(Integer.toString(stone));

				}
			});

			timerSource.start();
			ButtonSetUp();
			statusPanel.add(ItemStatus);
			statusPanel.add(treeIcon);
			statusPanel.add(TreeText);
			statusPanel.add(stoneIcon);
			statusPanel.add(StoneText);
			statusPanel.add(ironIcon);
			statusPanel.add(IronText);

		}

		public void ButtonSetUp() {

			HammerButton = new JButton();
			HammerButton.setBounds(20, 335, 30, 30);
			HammerButton.setVisible(false);
			HammerButton.setIcon(hammerItem);

			HammerButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null,
							"When you own a Hammer you collcet stone will be two times faster!");

				}
			});

			SawButton = new JButton();
			SawButton.setBounds(20, 370, 30, 30);
			SawButton.setVisible(false);
			SawButton.setIcon(sawItem);

			SawButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null,
							"When you own a Saw you collcet wood will be two times faster!");
				}
			});

			SpadeButton = new JButton();
			SpadeButton.setBounds(20, 405, 30, 30);
			SpadeButton.setVisible(false);
			SpadeButton.setIcon(spadeItem);

			SpadeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null,
							"When you own a Spade you collcet iron will be two times faster!");
				}
			});
			// 1
			rudderButton = new JButton();
			rudderButton.setBounds(20, 440, 30, 30);
			rudderButton.setVisible(false);
			rudderButton.setIcon(rudderItem);

			rudderButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "rudder-componment to build a boat to leave here!");
				}
			});
			// 2
			anchorButton = new JButton();
			anchorButton.setBounds(20, 475, 30, 30);
			anchorButton.setVisible(false);
			anchorButton.setIcon(anchorItem);

			anchorButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "anchor-componment to build a boat to leave here!");
				}
			});
			// 3
			deckButton = new JButton();
			deckButton.setBounds(20, 510, 30, 30);
			deckButton.setVisible(false);
			deckButton.setIcon(deckItem);

			deckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "deck-componment to build a boat to leave here!");
				}
			});
			// 4
			sailButton = new JButton();
			sailButton.setBounds(20, 545, 30, 30);
			sailButton.setVisible(false);
			sailButton.setIcon(sailItem);

			sailButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "sail-componment to build a boat to leave here!");
				}
			});
			// 5
			hullButton = new JButton();
			hullButton.setBounds(20, 580, 30, 30);
			hullButton.setVisible(false);
			hullButton.setIcon(hullItem);

			hullButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "hull-componment to build a boat to leave here!");
				}
			});
			// 6
			cleatButton = new JButton();
			cleatButton.setBounds(20, 615, 30, 30);
			cleatButton.setVisible(false);
			cleatButton.setIcon(cleatItem);

			cleatButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "cleat-componment to build a boat to leave here!");
				}
			});

			statusPanel.add(HammerButton);
			statusPanel.add(SawButton);
			statusPanel.add(SpadeButton);
			statusPanel.add(rudderButton);
			statusPanel.add(anchorButton);
			statusPanel.add(deckButton);
			statusPanel.add(sailButton);
			statusPanel.add(hullButton);
			statusPanel.add(cleatButton);

		}

		private void showMenuBar() {
			final JMenuBar menuBar = new JMenuBar();

			JMenu myMenu = GameMenu();
			menuBar.add(myMenu);
			//
			myMenu = SetCarrerMenu();
			menuBar.add(myMenu);

			myMenu = BuildMenu();
			menuBar.add(myMenu);

			myMenu = CreatMenu();
			menuBar.add(myMenu);

			myMenu = BuildTheShip();
			menuBar.add(myMenu);
			f.setJMenuBar(menuBar);

		}

		private JMenu BuildTheShip() {
			JMenu myMenu = new JMenu("Build The Ship");
			JMenuItem myItem = new MyMenuItem("Let's get out of here!");
			myMenu.add(myItem);
			return myMenu;
		}

		private JMenu CreatMenu() {
			JMenu myMenu = new JMenu("Create");

			JMenuItem myItem = new MyMenuItem("Saw");
			myMenu.add(myItem);

			myItem = new MyMenuItem("Hammer");
			myMenu.add(myItem);

			myItem = new MyMenuItem("Spade");
			myMenu.add(myItem);

			myItem = new MyMenuItem("Settler");
			myMenu.add(myItem);
			myItem = new MyMenuItem("deck");
			myMenu.add(myItem);
			myItem = new MyMenuItem("rudder");
			myMenu.add(myItem);
			myItem = new MyMenuItem("mainsail");
			myMenu.add(myItem);
			myItem = new MyMenuItem("cleat");
			myMenu.add(myItem);
			myItem = new MyMenuItem("hull");
			myMenu.add(myItem);
			myItem = new MyMenuItem("anchor");
			myMenu.add(myItem);
			return myMenu;
		}

		private JMenu SetCarrerMenu() {
			JMenu myMenu = new JMenu("Career");

			JMenuItem myItem = new MyMenuItem("stoner");
			myMenu.add(myItem);

			myItem = new MyMenuItem("farmer");
			myMenu.add(myItem);

			myItem = new MyMenuItem("ironer");
			myMenu.add(myItem);
			return myMenu;
		}

		private JMenu GameMenu() {
			JMenu myMenu = new JMenu("Game");
			JMenuItem myItem = new MyMenuItem("Starting a new game");
			myMenu.add(myItem);
			myItem = new MyMenuItem("Exit with saving current progress");
			myMenu.add(myItem);
			myItem = new MyMenuItem("Exit without saving current progress");
			myMenu.add(myItem);
			return myMenu;
		}

		private JMenu BuildMenu() {
			JMenu myMenu = new JMenu("Build");
			JMenuItem myItem = new MyMenuItem("build house");
			myMenu.add(myItem);
			myItem = new MyMenuItem("build storage");
			myMenu.add(myItem);
			return myMenu;
		}

		private class MyMenuItem extends JMenuItem implements ActionListener {

			private int tree;
			private int iron;
			private int stone;

			public MyMenuItem(String text) {
				super(text);
				addActionListener(this);
			}

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("stoner")) {
					careerString = "Stone";
				} else if (e.getActionCommand().equals("ironer")) {
					careerString = "Iron";
				} else if (e.getActionCommand().equals("farmer")) {
					careerString = "Mutou";
				} else if (e.getActionCommand().equals("build house")) {

					view.addMouseListener(new java.awt.event.MouseAdapter() {

						public void mouseClicked(java.awt.event.MouseEvent evt) {
							fMouseClicked(evt);
						}

						private void fMouseClicked(MouseEvent evt) {
							int x = evt.getX() / 25;
							int y = evt.getY() / 25;
							for (int i = 0; i < 100; i++) {
								for (int j = 0; j < 100; j++) {
									if (houses.gethouseCollection()[i][j] == House.storage) {
										tree = houses.gethouseCollection()[i][j].getWood();
										iron = houses.gethouseCollection()[i][j].getIron();
										stone = houses.gethouseCollection()[i][j].getStone();
										break;
									}

								}
							}
							if (tree >= 50) {
								if (nature.getNaturalSourceCollection()[x][y] == NaturalSource.Ground
										&& game.getGameSourceCollection()[x][y] == null
										&& houses.gethouseCollection()[x][y] == null)
									houses.addToWaitingList(x, y, "SleepingHouse");
								for (int i = 0; i < 100; i++) {
									for (int j = 0; j < 100; j++) {
										if (houses.gethouseCollection()[i][j] == House.storage) {
											houses.gethouseCollection()[i][j].minusWood(50);

											break;
										}

									}
								}
							} else
								JOptionPane.showMessageDialog(null, "No enough source for building a SleepingHouse\n"
										+ "it will need " + "50 Trees");
							view.removeMouseListener(this);

							System.out.println("House");

						}
					});

				}

				else if (e.getActionCommand().equals("build storage")) {

					view.addMouseListener(new java.awt.event.MouseAdapter() {
						// MouseListener ml = new MouseAdapter() {

						public void mouseClicked(java.awt.event.MouseEvent evt) {
							fMouseClicked(evt);
						}

						private void fMouseClicked(MouseEvent evt) {
							tree = -1;
							int x = evt.getX() / 25;
							int y = evt.getY() / 25;
							System.out.println(x + " " + y);
							for (int i = 0; i < 100; i++) {
								for (int j = 0; j < 100; j++) {
									if (houses.gethouseCollection()[i][j] == House.storage) {
										tree = houses.gethouseCollection()[i][j].getWood();
										iron = houses.gethouseCollection()[i][j].getIron();
										stone = houses.gethouseCollection()[i][j].getStone();
										break;
									}

								}
							}
							if (tree >= 50 || tree == -1) {
								if (nature.getNaturalSourceCollection()[x][y] == NaturalSource.Ground
										&& game.getGameSourceCollection()[x][y] == null
										&& houses.gethouseCollection()[x][y] == null)
									houses.addToWaitingList(x, y, "Storage");
								System.out.println("Storage");
								for (int i = 0; i < 100; i++) {
									for (int j = 0; j < 100; j++) {
										if (houses.gethouseCollection()[i][j] == House.storage) {
											houses.gethouseCollection()[i][j].minusWood(50);

											break;
										}

									}
								}
							} else
								JOptionPane.showMessageDialog(null,
										"No enough source for building a storage\n" + "it will need " + "50 Trees");
							view.removeMouseListener(this);

						}
					});

				} else if (e.getActionCommand().equals("Starting a new game")) {
					nature = new NaturalSourceCollection();
					game = new GameSourceCollection(nature);
					item = new ItemCollection();
					houses = new HouseCollection(nature, game, item);
					settler = new SettlerCollection(nature, game, item, houses);
					view = new graphicView(nature, game, item, settler, houses);
					startingday = LocalDate.now();

				} else if (e.getActionCommand().equals("Exit with saving current progress")) {

					try {

						FileOutputStream fos = new FileOutputStream(SAVED_COLLECTION_LOCATION);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(nature);
						oos.writeObject(game);
						oos.writeObject(item);
						oos.writeObject(houses);
						oos.writeObject(settler);
						oos.writeObject(startingday);
						oos.close();
						fos.close();

					} catch (Exception exception) {
						exception.printStackTrace();
					}
					f.setVisible(false);
					f.dispose();
					System.exit(0);
				} else if (e.getActionCommand().equals("Exit without saving current progress")) {
					f.setVisible(false);
					f.dispose();
					System.exit(0);
				}

				else if (e.getActionCommand().equals("Saw")) {
					// boolean saw = true;

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new Saw().CostInTree() || iron < new Saw().CostInIron()
							|| stone < new Saw().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for Saw\n" + "it will need " + new Saw().CostInTree() + " Trees, "
										+ new Saw().CostInIron() + " Irons and " + new Saw().CostInStone() + " Stones");

					else {
						item.getItemCollection().add(new Saw());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new Saw().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new Saw().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new Saw().CostInStone());
									break;
								}

							}
						}
						SawButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Saw done");

					}

				} else if (e.getActionCommand().equals("Spade")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}
						}
					}
					if (tree < new Spade().CostInTree() || iron < new Spade().CostInIron()
							|| stone < new Spade().CostInStone())

						JOptionPane.showMessageDialog(null,
								"No enough source for Spade\n" + "it will need " + new Spade().CostInTree() + " Trees, "
										+ new Spade().CostInIron() + " Irons and " + new Spade().CostInStone()
										+ " Stones");

					else {
						item.getItemCollection().add(new Spade());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new Spade().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new Spade().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new Spade().CostInStone());
									break;
								}

							}
						}
						SpadeButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Spade done");
					}

				} else if (e.getActionCommand().equals("Settler")) {
					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < 100)
						JOptionPane.showMessageDialog(null,
								"No enough source for crate a settler\n" + "it will need " + "100 Trees");

					else {
						settler.createANewSettler();
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(100);

									break;
								}

							}
						}
					}

				} else if (e.getActionCommand().equals("Hammer")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new Hammer().CostInTree() || iron < new Hammer().CostInIron()
							|| stone < new Hammer().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for Hammer\n" + "it will need " + new Hammer().CostInTree()
										+ " Trees, " + new Hammer().CostInIron() + " Irons and "
										+ new Hammer().CostInStone() + " Stones");

					else {
						item.getItemCollection().add(new Hammer());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new Hammer().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new Hammer().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new Hammer().CostInStone());
									break;
								}

							}
						}
						HammerButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Hammer done");
					}

				} else if (e.getActionCommand().equals("deck")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new deck().CostInTree() || iron < new deck().CostInIron()
							|| stone < new deck().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for deck\n" + "it will need " + new deck().CostInTree() + " Trees, "
										+ new deck().CostInIron() + " Irons and " + new deck().CostInStone()
										+ " Stones");

					else {
						item.getItemCollection().add(new deck());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new deck().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new deck().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new deck().CostInStone());
									break;
								}

							}
						}
						deckButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Deck done");
					}

				} else if (e.getActionCommand().equals("rudder")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new rudder().CostInTree() || iron < new rudder().CostInIron()
							|| stone < new rudder().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for rudder\n" + "it will need " + new rudder().CostInTree()
										+ " Trees, " + new rudder().CostInIron() + " Irons and "
										+ new rudder().CostInStone() + " Stones");

					else {
						item.getItemCollection().add(new rudder());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new rudder().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new rudder().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new rudder().CostInStone());
									break;
								}

							}
						}
						rudderButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Rudder done");
					}

				} else if (e.getActionCommand().equals("mainsail")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new mainsail().CostInTree() || iron < new mainsail().CostInIron()
							|| stone < new mainsail().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for sail\n" + "it will need " + new mainsail().CostInTree()
										+ " Trees, " + new mainsail().CostInIron() + " Irons and "
										+ new mainsail().CostInStone() + " Stones");

					else {
						item.getItemCollection().add(new mainsail());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new mainsail().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new mainsail().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new mainsail().CostInStone());
									break;
								}

							}
						}
						sailButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Sail done");
					}

				} else if (e.getActionCommand().equals("cleat")) {
					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new cleat().CostInTree() || iron < new cleat().CostInIron()
							|| stone < new cleat().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for cleat\n" + "it will need " + new cleat().CostInTree() + " Trees, "
										+ new cleat().CostInIron() + " Irons and " + new cleat().CostInStone()
										+ " Stones");

					else {
						item.getItemCollection().add(new cleat());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new cleat().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new cleat().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new cleat().CostInStone());
									break;
								}

							}
						}
						cleatButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Cleat done");
					}

				} else if (e.getActionCommand().equals("hull")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new hull().CostInTree() || iron < new hull().CostInIron()
							|| stone < new hull().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for hull\n" + "it will need " + new hull().CostInTree() + " Trees, "
										+ new hull().CostInIron() + " Irons and " + new hull().CostInStone()
										+ " Stones");

					else {
						item.getItemCollection().add(new hull());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new hull().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new hull().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new hull().CostInStone());
									break;
								}

							}
						}
						hullButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Hull done");
					}

				} else if (e.getActionCommand().equals("anchor")) {

					for (int i = 0; i < 100; i++) {
						for (int j = 0; j < 100; j++) {
							if (houses.gethouseCollection()[i][j] == House.storage) {
								tree = houses.gethouseCollection()[i][j].getWood();
								iron = houses.gethouseCollection()[i][j].getIron();
								stone = houses.gethouseCollection()[i][j].getStone();
								break;
							}

						}
					}
					if (tree < new anchor().CostInTree() || iron < new anchor().CostInIron()
							|| stone < new anchor().CostInStone())
						JOptionPane.showMessageDialog(null,
								"No enough source for anchor\n" + "it will need " + new anchor().CostInTree()
										+ " Trees, " + new anchor().CostInIron() + " Irons and "
										+ new anchor().CostInStone() + " Stones");
					else {
						item.getItemCollection().add(new anchor());
						for (int i = 0; i < 100; i++) {
							for (int j = 0; j < 100; j++) {
								if (houses.gethouseCollection()[i][j] == House.storage) {
									houses.gethouseCollection()[i][j].minusWood(new anchor().CostInTree());
									houses.gethouseCollection()[i][j].minusIron(new anchor().CostInIron());
									houses.gethouseCollection()[i][j].minusStone(new anchor().CostInStone());
									break;
								}

							}
						}
						anchorButton.setVisible(true);
						JOptionPane.showMessageDialog(null, "Anchor done");
					}
				} else if (e.getActionCommand().equals("Let's get out of here!")) {
					if (item.contain("hull") && item.contain("anchor") && item.contain("cleat") && item.contain("deck")
							&& item.contain("mainsail") && item.contain("rudder")) {
						JOptionPane.showMessageDialog(null, "Let's go!");
						ReadyToGo = true;
					} else
						JOptionPane.showMessageDialog(null,
								"You need to build all the componments of a ship to get out of here!");
				}
			}
		}

		private int index;
		private int count;
		private void forMoving() {
			if (settler.getSettlerCollection().size() <= 0){
				try {
					FlyWeightExample aa = new FlyWeightExample();
					timer.stop();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			clock++;
			for (int i = 0; i < settler.getSettlerCollection().size(); i++) {
				index = i;
				if (nature
						.getNaturalSourceCollection()[(int)Math.round(settler.getSettlerCollection().get(i).getX())][(int) Math.round(settler
								.getSettlerCollection().get(i).getY())] == NaturalSource.Brambles)
					settler.getSettlerCollection().get(i).minusHealth();

				if (settler.getSettlerCollection().get(i).getHealth() <= 0
						|| settler.getSettlerCollection().get(i).getHunger() <= 0
						|| settler.getSettlerCollection().get(i).getThirsty() <= 0) {
					settler.getSettlerCollection().remove(i);
					view.repaint();
					continue;
				} 
				else if(ReadyToGo&&index == 0){
					if(settler.getSettlerCollection().get(i).GetReadyToGo()){
						try {
							FlyWeightWin aa = new FlyWeightWin();
							timer.stop();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					}
					if(settler.getSettlerCollection().get(i).getCommand().size()!=0){
						settler.getSettlerCollection().get(i).move();
					}
					else{
					settler.getNewCommand(i, careerString, true);
					settler.getSettlerCollection().get(i).move();
				
					}
					
					
				}
					else {
					if (settler.getSettlerCollection().get(i).getCommand().size() == 0) {
						settler.getNewCommand(i, careerString, false);
						settler.getSettlerCollection().get(i).setEndLocation(settler.getEndX(), settler.getEndY());
					}
					if (settler.getSettlerCollection().get(i).getCommand().peek().equals("findStorage")) {
						if (settler.getSettlerCollection().get(i).getType().equals("Stone"))
							houses.gethouseCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].addStone();
						if (settler.getSettlerCollection().get(i).getType().equals("Mutou"))
							houses.gethouseCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].addWood();
						if (settler.getSettlerCollection().get(i).getType().equals("Iron"))
							houses.gethouseCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].addIron();

						settler.getSettlerCollection().get(i).move();
						settler.getSettlerCollection().get(i).clean();

					} else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("tree")) {
						if(index == 0)
							GameRunner.Playsongs(0);
						if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
								.getSettlerCollection().get(i).getEndY()] != null) {
							game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].minus();
							if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].getValues() <= 0)
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()] = null;
							settler.getSettlerCollection().get(i).move();
						}
						view.repaint();
					} else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("Mutou")) {
						if(index == 0)
							GameRunner.Playsongs(2);
						if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
								.getSettlerCollection().get(i).getEndY()] != null) {
							if (item.contain("Saw")) {
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()].minus();
							}
							game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].minus();
							if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].getValues() <= 0)
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()] = null;

							settler.getSettlerCollection().get(i).move();

						}
						view.repaint();
					} 
					else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("water")){
						if(index == 0)
							GameRunner.Playsongs(1);
						
					}
					else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("Stone")) {
						
						if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
								.getSettlerCollection().get(i).getEndY()] != null) {
							if (item.contain("Hammer")) {
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()].minus();
							}
							game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].minus();
							if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].getValues() <= 0)
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()] = null;
							settler.getSettlerCollection().get(i).move();
						} else
							settler.getSettlerCollection().get(i).getCommand().poll();
						view.repaint();
					} else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("Iron")) {
						if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
								.getSettlerCollection().get(i).getEndY()] != null) {
							if (item.contain("Spade")) {
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()].minus();
							}
							game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].minus();
							if (game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()].getValues() <= 0)
								game.getGameSourceCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
										.getSettlerCollection().get(i).getEndY()] = null;
							settler.getSettlerCollection().get(i).move();
						}
						view.repaint();
					} else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("storage")
							&& houses.gethouseCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()] == null) {
						houses.addAStorage(settler.getSettlerCollection().get(i).getEndX(),
								settler.getSettlerCollection().get(i).getEndY());
						settler.getSettlerCollection().get(i).move();
						view.repaint();
					} else if (settler.getSettlerCollection().get(i).getCommand().peek().equals("sleepingHouse")
							&& houses.gethouseCollection()[settler.getSettlerCollection().get(i).getEndX()][settler
									.getSettlerCollection().get(i).getEndY()] == null) {
						houses.addASleepingRoom(settler.getSettlerCollection().get(i).getEndX(),
								settler.getSettlerCollection().get(i).getEndY());
						settler.getSettlerCollection().get(i).move();

						view.repaint();
					} else {
						Settler temp = settler.getSettlerCollection().get(i);
						temp.move();
						animation = new Timer(20, new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								view.repaint();
							}

						});
						animation.start();
					}
				}
			}
		}
	}

	class sourceImage extends JComponent {

		private Image backgroundImage;
		private int i = 5;
		private Image alltree;
		private Image allstone;
		private Image alliron;

		public sourceImage(Image backgroundImage, Image alltree, Image allstone, Image alliron) {

			this.backgroundImage = backgroundImage;

			this.alltree = alltree;
			this.allstone = allstone;
			this.alliron = alliron;

		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(backgroundImage, 0, 0, 300, 300, null);

			g.drawImage(alltree, 30, i, 30, 30, null);
			g.drawImage(allstone, 110, i, 30, 30, null);
			g.drawImage(alliron, 200, i, 30, 30, null);

		}
	}

	class backImage extends JComponent {

		private Image i;

		public backImage(Image i) {
			this.i = i;
		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(i, 0, 0, 1200, 800, null);
		}
	}

	public static synchronized void Playsongs(int index) {

		Clip clip = null;
		try {

			if (clip == null)
				clip = AudioSystem.getClip();
			else
				clip.close();

			if (index == 0) {

				File eatingFile = new File("src/songfiles/eating.wav");
				AudioInputStream eatingaudioIn = AudioSystem.getAudioInputStream(eatingFile);
				clip.setFramePosition(0);
				clip.open(eatingaudioIn);
				clip.start();

			}
			if (index == 1) {
				File drinkingFile = new File("src/songfiles/drinking.wav");
				AudioInputStream drinkingaudioIn = AudioSystem.getAudioInputStream(drinkingFile);
				clip.setFramePosition(0);
				clip.open(drinkingaudioIn);
				clip.start();
			}

			if (index == 2) {
				File buildingFile = new File("src/songfiles/building.wav");
				AudioInputStream buildingaudioIn = AudioSystem.getAudioInputStream(buildingFile);
				clip.setFramePosition(0);
				clip.open(buildingaudioIn);
				clip.start();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}