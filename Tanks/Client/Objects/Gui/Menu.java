package Tanks.Client.Objects.Gui;

import Tanks.Client.Main;
import java.util.*;
public class Menu{
  public static final int MENU_PAUSE = 1;
  
  public static Menu pauseMenu;
  private static boolean pauseMenuShown = false;
  
  private ArrayList<GUIElement> myObjects = new ArrayList(); 
  
  public Menu(int type, Object[] args){
    switch (type) {
      case MENU_PAUSE : 
      initMenuPause();
      break;      
    } // end of switch
  }
  
  private void initMenuPause(){
    myObjects.add(new GUIElement(0.3f,0.1f,0.4f,0.1f,true,new String[]{"Continue Game"})); 
    myObjects.get(myObjects.size()-1).addCallback(new Callback(){
      public void run(){
        Menu.pauseMenu.showMenu(false);
      }
    });  
    myObjects.add(new GUIElement(0.3f,0.2f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.3f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.4f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.5f,0.4f,0.1f,true,new String[]{"Exit Game"}));
    showMenu(true);
    pauseMenu = this;
  }
  private void showMenu(boolean e){
    
    System.out.println("test " + this + e);
    if (e) {
      for (int i = 0; i<myObjects.size(); i++) {
        Main.getSD().addGui(myObjects.get(i));
      } // end of for
    }else{
      for (int i = 0; i<myObjects.size(); i++) {
        Main.getSD().removeGui(myObjects.get(i));
      } // end of for      
    }
  }
  public static void showPauseMenu(boolean e){
    if(pauseMenu == null){
      pauseMenu = new Menu(MENU_PAUSE, null);
    }
    pauseMenuShown=e;
    pauseMenu.showMenu(e);
  }
  public static boolean getPauseMenuShown(){
    return pauseMenuShown;
  }
}