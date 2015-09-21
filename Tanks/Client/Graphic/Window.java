package Tanks.Client.Graphic;

import java.awt.*;
import java.awt.event.*;
import Tanks.Client.Log;
import Tanks.Client.Main;


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
    
    addMouseListener(new MouseAdapter() { 
      public void mousePressed(MouseEvent evt) { 
        window_MousePressed(evt);
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() { 
      public void mouseMoved(MouseEvent evt) { 
        window_MouseMoved(evt);
      }
    });
    addMouseListener(new MouseAdapter() { 
      public void mouseReleased(MouseEvent evt) { 
        window_MouseReleased(evt);
      }
    });
    addWindowFocusListener(new WindowAdapter() { 
      public void windowLostFocus(WindowEvent evt) { 
        window_WindowLostFocus(evt);
      }
    });
    addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent evt) { 
        window_MouseClicked(evt);
      }
    });
    addKeyListener(new KeyAdapter() { 
      public void keyPressed(KeyEvent evt) { 
        window_KeyPressed(evt);
      }
    });
    addFocusListener(new FocusAdapter() { 
      public void focusLost(FocusEvent evt) { 
        window_FocusLost(evt);
      }
    });
    // Ende Listener
    
    setVisible(true);
  } // end of public Window
  
  // Anfang Methoden
  public void window_MousePressed(MouseEvent evt) {
    Log.write("Mouse pressed: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_MousePressed
  
  public void window_MouseMoved(MouseEvent evt) {
    Log.write("Mouse moved: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_MouseMoved
  
  public void window_MouseReleased(MouseEvent evt) {
    Log.write("Mouse released: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_MouseReleased
  
  public void window_WindowLostFocus(WindowEvent evt) {
    Log.write("Window lost Focus: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_WindowLostFocus
  
  public void window_MouseClicked(MouseEvent evt) {
    Log.write("Mouse clicked: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_MouseClicked
  
  public void window_KeyPressed(KeyEvent evt) {
    Log.write("Key pressed: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_KeyPressed
  
  public void window_FocusLost(FocusEvent evt) {
    Log.write("Focus lost: " + evt.toString(), Log.LOGDEPTH_High);
  } // end of window_FocusLost
  
  // Ende Methoden
} // end of class Window
