package Tanks.Client.Graphic;

import Tanks.Client.PlayerSystems.*;;
import Tanks.Client.Log;
import Tanks.Client.Main;    
import Tanks.Client.Objects.Ingame.*;
import Tanks.Client.Objects.Gui.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

public class Screendrawer{
  private static final boolean LOG_FRAME_TIME_ALLOCATION = false;
  
  private BufferedImage gameField;
  private Graphics gameFieldDrawer; 
  private boolean gameFieldChanged = true;
  private boolean updatedScreensize = true;
  private ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
  
  private ArrayList<float[]> fog_pos = new ArrayList<float[]>();    
  private boolean fog = true;
  private BufferedImage fogCopy;
  private boolean fog_changed = true;
  private float fog_r = 0.12f;
  
  private ArrayList<FogHole> holes = new ArrayList<FogHole>();
  
    private int[][] line = new int[][]{{0,0},{0,0}}; 
  private int[] point = new int[]{0,0};
  
  public Gamefield game = new Gamefield("Level1.txt");
  
  private boolean renderGamefield = false;
  private boolean pauseGame = false;
  
  private long lastTime = 0;
  private double fps = 0;
  private boolean fps_enable = true;
  public Screendrawer(){                  
    ArrayList<float[]> fog_pos2 = new ArrayList<float[]>();    
    fog_pos2.add(new float[]{0.1f,1.6f/9});  
    fog_pos2.add(new float[]{0.3f,1.6f/9}); 
    fog_pos2.add(new float[]{0.4f,1.6f/9}); 
    setFog(fog_pos2);
  }
  
  public void setLine(int x1, int y1, int x2, int y2){
      line = new int[][]{{x1,y1},{x2,y2}}; 
    setPoint(x2,y2);
  }
  public void setPoint(int x, int y){
    point = new int[]{x,y};
  }
  public void setFog(ArrayList<float[]> fog_pos){
    this.fog_pos = fog_pos;
    holes = new ArrayList<FogHole>();
    for (int i  = 0; i < fog_pos.size(); i++) {
      holes.add(new FogHole(fog_pos.get(i), fog_r));
    } // end of for
    fog_changed = true;
  }
  public void setRenderGamefield(boolean b){
    renderGamefield = b;
  }
  public void pauseGame(){
    pauseGame = true;
  }
  public void continueGame(){
    pauseGame = false;
  }
  public void addGui(GUIElement g){
    guiElements.add(g);
  }
  public void removeGui(GUIElement g){
    guiElements.remove(g);
  }
  public void draw(){
    Log.write("starting new Frame", Log.LOGDEPTH_Irreal_High); 
    
    if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Start Frame:                " + System.nanoTime());
    if(fps_enable){
      fps = 1000/(double)(System.currentTimeMillis()-lastTime);
      //Log.write("FPS: " + fps, Log.LOGDEPTH_NONE);
    }
    
    lastTime = System.currentTimeMillis();
    
    if (this.gameField == null || updatedScreensize) {
      this.gameField = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
      this.gameFieldDrawer = this.gameField.getGraphics();
      updatedScreensize = false;
    }
    
    ImageAvailabilityChecker screenBufferObserver = new ImageAvailabilityChecker();
    
    BufferedImage aktFrame = new BufferedImage((int) Main.getMyWindow().getWidth(), (int) Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics aktFrameDrawer = aktFrame.getGraphics();
    
    if(LOG_FRAME_TIME_ALLOCATION) System.out.println("initialized BufferedImages: " + System.nanoTime());
    if(renderGamefield && !pauseGame){
      
      //Spielfeld rendern
      if (gameFieldChanged) {
        Log.write("Rendering Gamefield", Log.LOGDEPTH_High); 
        gameFieldDrawer.drawImage(TextureManager.getTexture(game.getBackTexture(), Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight(), false), 0, 0, screenBufferObserver);
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
      if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Drawn Gamefield:            " + System.nanoTime());
      
      //Tanks rendern
      if(PlayerManager.getActPlayer().getMode() == Player.MODE_DRIVE){
        aktFrameDrawer.setColor(Color.WHITE);
        aktFrameDrawer.drawLine(line[0][0], line[0][1], line[1][0], line[1][1]);
        aktFrameDrawer.fillOval(line[1][0] - 10, line[1][1] - 10, 20, 20);
        
        Tank t1 = PlayerManager.getActPlayer().getActTank();
        aktFrameDrawer.drawOval((int)((t1.getCollisionBoxPos()[0] + t1.getCollisionBoxSize()[0]/2 - Player.driveDis)*Main.getMyWindow().getWidth()), (int)((t1.getCollisionBoxPos()[1] + t1.getCollisionBoxSize()[1]/2)*Main.getMyWindow().getHeight() - Player.driveDis*Main.getMyWindow().getWidth()), (int)(Player.driveDis*Main.getMyWindow().getWidth()*2), (int)(Player.driveDis*Main.getMyWindow().getWidth()*2));
      }
      for (int i = 0; i < PlayerManager.getPlayers().size(); i++) {
        for (int j = 0; j < PlayerManager.getPlayers().get(i).getTanks().size(); j++) {
          Tank t = PlayerManager.getPlayers().get(i).getTanks().get(j);                                   
          aktFrameDrawer.drawImage(t.getTexture(), (int)(t.getPos()[0]*Main.getMyWindow().getWidth()), (int)(t.getPos()[1]*Main.getMyWindow().getHeight()), screenBufferObserver);
        } // end of for
      } // end of for                                                                   
      if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Drawn Tanks:                " + System.nanoTime());
      
      //Fog of war rendern
      
      //Fog erste Variante:  
      //if(fog) aktFrameDrawer.drawImage(getFogOfWar(),0,0,screenBufferObserver);  
      
      //Fog zweite Variante:
      //aktFrame = FogHole.getFog(aktFrame, TextureManager.getTexture("fog_of_war.png", Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight(), false), holes);
      
      //Fog dritte Variante:
      ///*
      if (fog) {
        aktFrameDrawer.drawImage(FogHole.getFog(TextureManager.getTexture("fog_of_war.png", Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight(), false), holes, fog_changed, false),0,0,screenBufferObserver);
        if (fog_changed) fog_changed = false;   
      }      //*/
      
      if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Drawn Fog:                  " + System.nanoTime());
      
      //Items rendern
      
    }
    //GUI rendern
    for (int i = 0; i<guiElements.size(); i++) {
      aktFrameDrawer.drawImage(guiElements.get(i).getTexture(), (int)(guiElements.get(i).getPos()[0]*Main.getMyWindow().getWidth()), (int)(guiElements.get(i).getPos()[1]*Main.getMyWindow().getHeight()), screenBufferObserver);
    } // end of for                        
    if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Drawn GUI:                  " + System.nanoTime());
    
    if(fps_enable){
      aktFrameDrawer.setColor(Color.BLUE);
      aktFrameDrawer.setFont(new Font("fps", Font.PLAIN, 30));
      aktFrameDrawer.drawString("FPS: " + fps, 10, 100);
      //Main.getMyWindow().display.getGraphics().clearRect(10,10,300,300);
      //Main.getMyWindow().display.getGraphics().drawString("FPS: " + fps, 10, 100); 
      if(LOG_FRAME_TIME_ALLOCATION) System.out.println("Drawn FPS:                  " + System.nanoTime());
    }
    
    //rendern Ende
    
    while (Main.getMyWindow().display.getGraphics() == null) { 
      Log.write("waiting for displays Graphics", Log.LOGDEPTH_High);
    }
    Log.write("Frame: " + aktFrame, Log.LOGDEPTH_Irreal_High);
    Main.getMyWindow().display.getGraphics().drawImage(aktFrame, 0, 0, Color.BLACK, screenBufferObserver);     
    if(LOG_FRAME_TIME_ALLOCATION) System.out.println("End of Frame:               " + System.nanoTime());
  }
  
  public BufferedImage getFogOfWar(){
    if(fog_changed){
      Log.write("Rendering Fog of War", Log.LOGDEPTH_High);
      BufferedImage fow = new BufferedImage(Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
      Graphics2D fowG = fow.createGraphics();
      fowG.drawImage(TextureManager.getTexture("fog_of_war.png", Main.getMyWindow().getWidth(), Main.getMyWindow().getHeight(), false),0,0,null);
      fowG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      float blessRate = 0.05f;
      for (int j = 0; j < fog_pos.size(); j++) {
        for (float i = 0f; i <= 1f; i += 1f) {
          fowG.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 1f-i));  
          fowG.fillOval((int)((fog_pos.get(j)[0] - fog_r - ((1f-i)*blessRate))*Main.getMyWindow().getWidth()) , (int)((fog_pos.get(j)[1]*Main.getMyWindow().getHeight()) - ((fog_r+(1f-i)*blessRate)*Main.getMyWindow().getWidth())) , (int)((fog_r+(1f-i)*blessRate)*2*Main.getMyWindow().getWidth()) , (int)((fog_r+(1f-i)*blessRate)*2*Main.getMyWindow().getWidth()));
        } // end of for                                                                                                                                                                                                              
      }
      fogCopy = fow;
      fog_changed = false;
      return fow;            
    }else{
      return fogCopy;
    }
  }
  
  private void drawHole(Graphics2D g2d, BufferedImage bi, int[] mid, int r, int blessDis){
    
    Color c = new Color(0,0,0,0);
    bi.setRGB(mid[0],mid[1],c.getRGB()); 
  }
  public ArrayList<GUIElement> getGUI(){
    return guiElements;
  }
}

