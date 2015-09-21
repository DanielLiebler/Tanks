package Tanks.Client.Objects.Gui;

import Tanks.Client.Main;
import java.util.*;
public class Menu{
  public static final int MENU_PAUSE = 1;
  
  private ArrayList<GUIElement> myObjects = new ArrayList(); 
  
  public Menu(int type, Object[] args){
    switch (type) {
      case MENU_PAUSE : 
      initMenuPause(args);
      break;      
    } // end of switch
  }
  
  private void initMenuPause(Object[] args){
    myObjects.add(new GUIElement(0.3f,0.1f,0.4f,0.1f,true,new String[]{"Continue Game"}));   
    myObjects.add(new GUIElement(0.3f,0.2f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.3f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.4f,0.4f,0.1f,true,new String[]{"Continue Game"}));
    myObjects.add(new GUIElement(0.3f,0.5f,0.4f,0.1f,true,new String[]{"Exit Game"}));
    for (int i = 0; i<myObjects.size(); i++) {
      Main.getSD().addGui(myObjects.get(i));
    } // end of for
  }
}