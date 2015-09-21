package Tanks.Client;

import Tanks.Client.Objects.Ingame.GameObject;
import Tanks.Client.Graphic.Window;
import Tanks.Client.Graphic.Screendrawer;
import java.awt.event.*;
import java.util.*;
import Tanks.Client.Objects.Gui.*;  
import Tanks.Client.Objects.Ingame.*;
import Tanks.Client.PlayerSystems.*;


public class Main{
  private static final boolean LOG_ENABLED = false;
  private static final int LOG_LEVEL = Log.LOGDEPTH_NONE;
  
  private static Window myWindow;
  private static boolean endGame = false;
  private static Screendrawer sd;
  
  public static int MOUSEBUTTON_SELECT_TANK = MouseEvent.BUTTON1;  
  public static int MOUSEBUTTON_MOVE_TANK = MouseEvent.BUTTON2;
  
  public static final int KEY_FORWARD     = KeyEvent.VK_W;  
  public static final int KEY_FORWARD_SEC = KeyEvent.VK_UP;
  public static final int KEY_LEFT        = KeyEvent.VK_A;
  public static final int KEY_LEFT_SEC    = KeyEvent.VK_LEFT;
  public static final int KEY_RIGHT       = KeyEvent.VK_D;
  public static final int KEY_RIGHT_SEC   = KeyEvent.VK_RIGHT;
  public static final int KEY_BACK        = KeyEvent.VK_S;
  public static final int KEY_BACK_SEC    = KeyEvent.VK_DOWN;
  public static final int KEY_SHOOT       = KeyEvent.VK_SPACE;        
  public static final int KEY_SHOOT_SEC   = -2;
  //public static int KEY_ = KeyEvent;
  
  public static void main(String[] args) {
    Log.enable(LOG_ENABLED);
    Log.logDepth = LOG_LEVEL;
    Log.write("Game started", Log.LOGDEPTH_NONE);
    myWindow = new Window("Window", true);
    sd = new Screendrawer();                             
      startGame(2,new int[][]{{0,0,255},{0,255,0}}, 6, new String[]{"XxX", "YyY"},0.045f,0.08f); //Wie vom Hauptmen�
    Log.write("Gameloop started", Log.LOGDEPTH_High);
    while (!endGame) { 
      sd.draw();
      
      for (int i = 0; i < GameObject.objects.size(); i++) {
        GameObject.objects.get(i).anim(1);
      } // end of for
    }
    myWindow.dispose();
    Log.write("Game Closed", Log.LOGDEPTH_NONE);
  }
  public static void startGame(int localplayers, int[][] playercolors, int tankcount, String[] names, float szx, float szy){    
    //test ini
    ArrayList<String> text = new ArrayList<String>();
    text.add("Hallo");                                               
    text.add("Welt");
    text.add("Hallo");                          
    text.add("Welt");
    GUIElement g1 = new GUIElement(0f,0f,0.4f,0.4f,true,text);//, TextureManager.getTexture("gras.png", (int)(0.4*Main.getMyWindow().getWidth()), (int)(0.4*Main.getMyWindow().getHeight())));  
    sd.addGui(g1); 
    
    
    //real ini
    
    for (int i = 0; i < playercolors.length; i++) {      
      ArrayList<Tank> tanks = new ArrayList<Tank>();
      for (int j = 0; j < tankcount; j++) {
        Log.write("Tank added", Log.LOGDEPTH_Middle);
        tanks.add(new Tank(sd.game.getTeamStarts(i).get(j)[0], sd.game.getTeamStarts(i).get(j)[1], szx, szy, playercolors[i][0],playercolors[i][1],playercolors[i][2]));
      } // end of for
      PlayerManager.addPlayer(new Player(names[i], tanks));
    } // end of for
    
    PlayerManager.getActPlayer().iniNewRound();
    
    sd.setRenderGamefield(true);
  }
  
  
  public static Window getMyWindow(){
    return myWindow;
  }
  public static void setEndGame(boolean end){
    Log.write("Closing Game...", Log.LOGDEPTH_NONE);
    endGame = end;
  }
  public static Screendrawer getSD(){
    return sd;
  }
}