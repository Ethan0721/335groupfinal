package controller;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlyWeightExample extends JFrame {
  private GfxPanel tPanel;


  public FlyWeightExample() throws IOException {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Game Over");
    this.setSize(620, 150);
    this.setLocation(400,400);
    this.setAlwaysOnTop(true);
    tPanel = new GfxPanel("GAMEOVER");
    this.add(tPanel);

    Timer t = new Timer(100, new Ticker());
    t.start();
    this.setVisible(true);
  }

  private class Ticker implements ActionListener {

    public void actionPerformed(ActionEvent arg0) {
      tPanel.repaint();
    }
  }

  private class GfxPanel extends JPanel {
    private int amplitude = 10;
    private int currSpot;
    private int startx = 50;
    private int starty = 50;
    private boolean increasing = true;
    private ArrayList<LetterFlyweight> myMsg;

    public GfxPanel(String message) throws IOException {
      this.setBackground(Color.BLACK);
      myMsg = new ArrayList<LetterFlyweight>();
      currSpot = 0;
      for (int i = 0; i < message.length(); i++) {
        myMsg.add(LetterFlyweight.getLetter(message.charAt(i)));
      }
    }

    public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      this.setBackground(Color.black);
      g2.setColor(Color.black);
      g2.fillRect(0, 0, 800, 150);

      for (int j = 0; j < myMsg.size(); j++) {
        int differential;
        if (j % 2 == 0) {
          differential = currSpot;
        } else {
          differential = -currSpot;
        }
        g2.drawImage(myMsg.get(j).getImage(), startx + (75 * j), starty + differential, null);
      }
      if (increasing)
        currSpot++;
      else
        currSpot--;
    
      if (currSpot == amplitude) {
        increasing = false;

      } else if (currSpot == -amplitude)
        increasing = true;
    }
  }
}


class LetterFlyweight {
  private static final HashMap<Character, LetterFlyweight> myLetterTable = new HashMap<>();
  private final Image myImage;
  private final char myChar;

  private LetterFlyweight(Image aLetter, char aChar) {
    myImage = aLetter;
    myChar = aChar;
  }

  public static LetterFlyweight getLetter(char key) throws IOException {
	
	  if(myLetterTable.containsKey(key)){
		  return myLetterTable.get(key);
	  }
	  else {
		 
		  Image b = ImageIO.read(new File(key + ".JPG"));
		  LetterFlyweight a = new LetterFlyweight(b, key);
		  myLetterTable.put(key, a);
	  
	 
		  return myLetterTable.get(key);
	  
	  }
  }

  public char getChar() {
    return myChar;
  }

  public Image getImage() {
    return myImage;
  }
}
