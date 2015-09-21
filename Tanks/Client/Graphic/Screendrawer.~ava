package Tanks.Client.Graphic;

import Tanks.Client.Log;
import Tanks.Client.Main;    
import Tanks.Client.Objects.Ingame.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

public class Screendrawer{
  
  private BufferedImage gameField;
  private Graphics gameFieldDrawer; 
  private boolean gameFieldChanged = true;
  private BufferedImage tanksImage;
  private Graphics tanksImageDrawer;
  private boolean tanksImageChanged = true;
  private boolean updatedScreensize = true;
  
  private Gamefield game = new Gamefield("Level1.txt");
  
  private boolean renderGamefield = true;
  private boolean pauseGame = false;
  
  private Tank t1 = new Tank(0.19f,0.5f,0.18f,0.32f,64,64,255);
  private float movement = 0.01f;
  
  public void draw(){
    Log.write("starting new Frame", Log.LOGDEPTH_Irreal_High); 
    
    if (this.gameField == null || updatedScreensize) {
      this.gameField = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
      this.gameFieldDrawer = this.gameField.getGraphics();
      updatedScreensize = false;
    }
    if (this.tanksImage == null || updatedScreensize) {
      this.tanksImage = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
      this.tanksImageDrawer = this.tanksImage.getGraphics();
      updatedScreensize = false;
    }
    
    ImageAvailabilityChecker screenBufferObserver = new ImageAvailabilityChecker();
    
    BufferedImage aktFrame = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics aktFrameDrawer = aktFrame.getGraphics();
    
    if(renderGamefield && !pauseGame){
      
      //Spielfeld rendern
      if (gameFieldChanged) { 
        gameFieldDrawer.drawImage(TextureManager.getTexture("gras.png", Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight()), 0, 0, screenBufferObserver);
        for (int i = 0; i < game.getObstacleLength(); i++) {
          Obstacle obs = game.getObstacle(i);
          int[] x = new int[obs.getPointLength()];
          int[] y = new int[obs.getPointLength()];
          int n;
          for (n = 0; n<obs.getPointLength(); n++) {
            x[n] = (int)(obs.getPoint(n)[0] * Main.getMyWindow().getWidth());        
            y[n] = (int)(obs.getPoint(n)[1] * Main.getMyWindow().getHeight());
          } // end of for
          gameFieldDrawer.setColor(Color.RED);
          gameFieldDrawer.fillPolygon(x,y,n);
        }
        
        gameFieldChanged = false;
      } 
      aktFrameDrawer.drawImage(gameField, 0, 0, screenBufferObserver);
      
      //Tanks rendern
      if (tanksImageChanged) {
        //this.tanksImage = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        aktFrameDrawer.drawImage(t1.getTexture(), (int)(t1.getPos()[0]*Main.getMyWindow().getWidth()), (int)(t1.getPos()[1]*Main.getMyWindow().getHeight()), screenBufferObserver);
        t1.turretRot += Math.PI/100; 
        //t1.tankRot -= Math.PI/150;
        if(t1.getPos()[1] < 0.2){
          movement = 0.01f;
        }else if (t1.getPos()[1] > 0.8){
          movement = -0.01f;
        }
        t1.setPos(t1.getPos()[0], t1.getPos()[1] + movement);
        tanksImageChanged = true;
      }else{
        tanksImageChanged = true;
      }
      aktFrameDrawer.drawImage(tanksImage, 0, 0, screenBufferObserver);
      
      //Items rendern
      
    }
    //GUI rendern
    
    //rendern Ende
    
    while (Main.getMyWindow().display.getGraphics() == null) { 
      Log.write("waiting for displays Graphics", Log.LOGDEPTH_High);
    }
    Log.write("Frame: " + aktFrame, Log.LOGDEPTH_Irreal_High);
    Main.getMyWindow().display.getGraphics().drawImage(aktFrame, 0, 0, Color.BLACK, screenBufferObserver);
  }
}