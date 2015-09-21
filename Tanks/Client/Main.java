package Tanks.Client;

import Tanks.Client.Graphic.Window;
import Tanks.Client.Graphic.Screendrawer;


public class Main{
  private static final boolean LOG_ENABLED = true;
  private static final int LOG_LEVEL = Log.LOGDEPTH_Irreal_High;
  
  private static Window myWindow;
  private static boolean endGame = false;
  private static Screendrawer sd;
  
  public static void main(String[] args) {
    Log.enable(LOG_ENABLED);
    Log.logDepth = LOG_LEVEL;
    Log.write("Game started", Log.LOGDEPTH_NONE);
    myWindow = new Window("Window", true);
    sd = new Screendrawer();
    Log.write("Gameloop started", Log.LOGDEPTH_High);
    while (!endGame) { 
      sd.draw();
    }
    myWindow.dispose();
    Log.write("Game Closed", Log.LOGDEPTH_NONE);
  }
  
  
  public static Window getMyWindow(){
    return myWindow;
  }
  public static void setEndGame(boolean end){
    Log.write("Closing Game...", Log.LOGDEPTH_NONE);
    endGame = end;
  }
}