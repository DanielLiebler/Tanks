package Tanks.Client.Graphic;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import Tanks.Client.Log;
import Tanks.Client.Main; 
import Tanks.Client.Objects.Gui.Menu;
import Tanks.Client.Objects.Gui.GUIElement;
import Tanks.Client.PlayerSystems.*;


public class Window extends Frame {
  
  public Panel display = new Panel(null);
  
  public Window(String title, boolean fullscreen){
    this(title, fullscreen, 300, 300);
  }
  public Window(String title, boolean fullscreen, int frameWid, int frameHei) { 
    // Frame-Initialisierung
    super(title);
    
    int frameWidth = frameWid; 
    int frameHeight = frameHei; 
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    
    if(fullscreen){
      frameWidth = d.width;
      frameHeight = d.height;
    }
    
    Log.write("Creating Window: " + title + ", Fullscreen: " + fullscreen + ", Size: " + frameWidth + "," + frameHeight, Log.LOGDEPTH_High);
    
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { Main.setEndGame(true); }
    });
    
    setUndecorated(true);     
    setResizable(false);          
    setSize(frameWidth, frameHeight);
    if(fullscreen){
      setLocation(0,0);
    }else{
      int x = (d.width - getSize().width) / 2;
      int y = (d.height - getSize().height) / 2;
      setLocation(x, y); 
    }
    
    add(display);
    // Anfang Listener
    
    display.addMouseListener(new MouseAdapter() { 
      public void mousePressed(MouseEvent evt) { 
        window_MousePressed(evt);
      }
      public void mouseReleased(MouseEvent evt) { 
        window_MouseReleased(evt);
      }
      public void mouseClicked(MouseEvent evt) { 
        window_MouseClicked(evt);
      }
    });
    display.addKeyListener(new KeyAdapter() { 
      public void keyPressed(KeyEvent evt) { 
        window_KeyPressed(evt);
      }
      public void keyReleased(KeyEvent evt) {
        PlayerManager.keyReleased(evt);
      }
    });
    display.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent evt){
        PlayerManager.mouseMoved(evt);
      }
      public void mouseDragged(MouseEvent evt){
        PlayerManager.mouseMoved(evt);
      }            
    });
    // Ende Listener
    
    setVisible(true);
  } // end of public Window
  
  // Anfang Methoden
  public void window_MousePressed(MouseEvent evt) {
    Log.write("Mouse pressed: " + evt.toString(), Log.LOGDEPTH_NONE);
  } // end of window_MousePressed
  
  public void window_MouseReleased(MouseEvent evt) {
    Log.write("Mouse released: " + evt.toString(), Log.LOGDEPTH_NONE);
  } // end of window_MouseReleased
  
  public void window_MouseClicked(MouseEvent evt) {
    Log.write("Mouse clicked: " + evt.toString(), Log.LOGDEPTH_NONE);
    ArrayList<GUIElement> guiElements = Main.getSD().getGUI();
    
    for (int i = 0; i < guiElements.size(); i++) {
      if(guiElements.get(i).onClick(evt) == true){
        System.out.println("catching keystroke");
        return;
      }
    } // end of for
    
    PlayerManager.click(evt);
  } // end of window_MouseClicked
  
  public void window_KeyPressed(KeyEvent evt) {
    Log.write("Key pressed: " + evt.toString(), Log.LOGDEPTH_NONE); 
    if(evt.getKeyCode() == Main.KEY_MENU || evt.getKeyCode() == Main.KEY_MENU_SEC){
      Menu.showPauseMenu(!Menu.getPauseMenuShown());  
    }else{
      PlayerManager.keyPressed(evt);   
    }
  } // end of window_KeyPressed
  
  // Ende Methoden
} // end of class Window
