package Tanks.Client.PlayerSystems;

import Tanks.Client.*;
import Tanks.Client.Objects.Ingame.*;   
import java.awt.event.*;
import java.util.*;

public class Player{
  private static final double speed = 0.01;
  
  
  private ArrayList<Tank> myTanks = new ArrayList<Tank>();
  private String myName;
  private boolean isActive = true;
  private int selectedTank = -1;
  
  public Player(String name, ArrayList<Tank> tanks){
    myTanks = tanks;
    myName = name;
    PlayerManager.addPlayer(this);
  }
  
  public ArrayList<Tank> getTanks(){
    return myTanks;
  }
  
  public void click(MouseEvent evt){
    if(evt.getButton() == Main.MOUSEBUTTON_SELECT_TANK){ 
      Log.write("Player recieved Click", Log.LOGDEPTH_Middle);
      for (int i = 0; i<myTanks.size(); i++) {
        Tank actTank = myTanks.get(i);
        if(  (evt.getX() > actTank.getCollisionBoxPos()[0]*Main.getMyWindow().getWidth())  &&  (evt.getX() < actTank.getCollisionBoxPos()[0]*Main.getMyWindow().getWidth()+actTank.getCollisionBoxSize()[0]*Main.getMyWindow().getWidth())  &&  (evt.getY() > actTank.getCollisionBoxPos()[1]*Main.getMyWindow().getHeight())  &&  (evt.getY() < actTank.getCollisionBoxPos()[1]*Main.getMyWindow().getHeight()+actTank.getCollisionBoxSize()[1]*Main.getMyWindow().getHeight())  ){
          Log.write("Found Tank", Log.LOGDEPTH_Middle);
          if(actTank.isActive()){
            if(selectedTank >= 0) myTanks.get(selectedTank).deSelectTank(); 
            myTanks.get(i).selectTank();
            selectedTank = i;
          }
          
          break;
        }
      }
    }
  }
  public boolean isActive(){
    return isActive;
  }
  public void mouseMoved(MouseEvent evt){
    
  }
  public void keyPressed(KeyEvent evt){
    switch(evt.getKeyCode()){
      case Main.KEY_FORWARD: case Main.KEY_FORWARD_SEC:
      if(selectedTank >= 0){
        myTanks.get(selectedTank).setMovement(0,speed);
      }
      
      break;
      case Main.KEY_LEFT: case Main.KEY_LEFT_SEC: 
      if(selectedTank >= 0){
        myTanks.get(selectedTank).setMovement(-speed, 0);
      }
      
      break;
      case Main.KEY_RIGHT: case Main.KEY_RIGHT_SEC:
      if(selectedTank >= 0){
        myTanks.get(selectedTank).setMovement(speed, 0);
      }
      
      break;
      case Main.KEY_BACK: case Main.KEY_BACK_SEC: 
      if(selectedTank >= 0){
        myTanks.get(selectedTank).setMovement(0,-speed);
      }
      
      break;
      case Main.KEY_SHOOT: case Main.KEY_SHOOT_SEC:
      
      break;
      
    }
  }
}