package Tanks.Client.PlayerSystems;

import Tanks.Client.*;
import Tanks.Client.Objects.Animation;
import Tanks.Client.Objects.Ingame.*;   
import Tanks.Client.Objects.Gui.Menu;
import java.awt.event.*;
import java.util.*;

public class Player implements Animation{
  public static final int MODE_IDLE = 0;   
  public static final int MODE_DRIVE = 1;
  public static final int MODE_SHOOT = 2;  
  public static final int AIM_IDLE = 0;   
  public static final int AIM_LEFT = 1;
  public static final int AIM_RIGHT = 2;
  
  public static final float driveDis = 0.1f;
  
  private static final double speed = 0.002;
  private double aimSpeed = Math.PI/100;
  private double aimRads = 0;
  private double aimSize = Math.PI/2;
  private double aimRot = 0;
  private int aimNew = 0;
  private int aim = AIM_IDLE;
  private int mode = MODE_IDLE;
  
  private ArrayList<Tank> myTanks = new ArrayList<Tank>();
  private String myName;
  private boolean isActive = true;
  private int selectedTank = -1;
  
  public Player(String name, ArrayList<Tank> tanks){
    myTanks = tanks;
    myName = name;
    PlayerManager.addPlayer(this);
    animObj.add(this);
  }
  
  public ArrayList<Tank> getTanks(){
    return myTanks;
  }
  public Tank getActTank(){
    if (selectedTank >= 0) return myTanks.get(selectedTank);
    return null;
  }
  
  public int getMode(){
    return mode;
  }
  public void setShooting(){  
    setIdle();
    mode = MODE_SHOOT;
  }
  public void setDriving(){
    setIdle();
    mode = MODE_DRIVE;
  }
  public void setIdle(){
    Main.getSD().setLine(0,0,0,0);
    mode = MODE_IDLE;
  }
  public void iniNewRound(){
    changeFog();
    if(selectedTank >= 0) myTanks.get(selectedTank).selectTank();
  }
  public void changeFog(){
    ArrayList<float[]> fog = new  ArrayList<float[]>();
    for (int i = 0; i < myTanks.size(); i++) {
      fog.add(new float[]{(float)(myTanks.get(i).getCollisionBoxPos()[0] + myTanks.get(i).getCollisionBoxSize()[0]*0.5), (float)(myTanks.get(i).getCollisionBoxPos()[1] + myTanks.get(i).getCollisionBoxSize()[1]*0.5)});
    }   
    Main.getSD().setFog(fog); 
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
            mouseMoved(evt);
          }
          
          break;
        }
      }
    }else if(evt.getButton() == Main.MOUSEBUTTON_MOVE_TANK && mode == MODE_DRIVE){    
      int x1 = (int)((myTanks.get(selectedTank).getCollisionBoxPos()[0] + myTanks.get(selectedTank).getCollisionBoxSize()[0]*0.5)*Main.getMyWindow().getWidth());   
      int x2 = evt.getX();
      int y1 = (int)((myTanks.get(selectedTank).getCollisionBoxPos()[1] + myTanks.get(selectedTank).getCollisionBoxSize()[1]*0.5)*Main.getMyWindow().getHeight());
      int y2 = evt.getY();   
      double spdX = (double)(x2-x1)/(double)(Main.getMyWindow().getWidth()), spdY = (double)(y2-y1)/(double)(Main.getMyWindow().getHeight());
      double l = Math.sqrt(spdX*spdX+spdY*spdY);
      double x = speed*spdX/l;
      double y = speed*spdY/l;
      
      if (Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)) > driveDis*Main.getMyWindow().getWidth()) {
        
        
        int nx = (int)(driveDis*Main.getMyWindow().getWidth()*((x2-x1)/Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))));
        int ny = (int)(driveDis*Main.getMyWindow().getWidth()*((y2-y1)/Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))));
        myTanks.get(selectedTank).setMovement(x,y,(float)( (double)(x1+nx) / (double)(Main.getMyWindow().getWidth()) ),(float)( (double)(y1+ny) / (double)(Main.getMyWindow().getHeight()) ));
      } else{
        myTanks.get(selectedTank).setMovement(x,y,(float)( (double)(x2) / (double)(Main.getMyWindow().getWidth()) ),(float)( (double)(y2) / (double)(Main.getMyWindow().getHeight()) ));
      }
      
      
      setIdle();
    }
  }
  public void iniEndRound(){
    if(selectedTank >= 0) myTanks.get(selectedTank).deSelectTank();
  }
  public boolean isActive(){
    return isActive;
  }
  public void anim(double timeMultiplier){
    if (mode == MODE_SHOOT) {
      if (aim != AIM_IDLE) {
        if(aim == AIM_RIGHT){
          aimRads += timeMultiplier*aimSpeed;
          myTanks.get(selectedTank).turretRot += timeMultiplier*aimSpeed;
        }else if(aim == AIM_LEFT){           
          aimRads -= timeMultiplier*aimSpeed;     
          myTanks.get(selectedTank).turretRot -= timeMultiplier*aimSpeed;
        }
        while(aimRads > 2*Math.PI){
          aimRads -= 2*Math.PI;
        }
      } // end of if      
      
      if (aimNew <= 0) {                  
        Random r1 = new Random();
        aimRot = r1.nextDouble();
        aimNew = 10;
      }else{
        aimNew--;
      }
      double dest = myTanks.get(selectedTank).turretRot + (aimRot-0.5)*aimSpeed;
      double upper = aimRads + aimSize/2, lower = aimRads - aimSize/2;
      if (dest > upper) {
        dest = upper;
        aimRot = aimRot*-1;
      } else if (dest < lower){
        dest = lower;        
        aimRot = aimRot*-1;
      } // end of if-else
      myTanks.get(selectedTank).turretRot = dest;  
    }
  }
  public void mouseMoved(MouseEvent evt){
    if(mode == MODE_DRIVE && selectedTank >= 0){
      int x1 = (int)((myTanks.get(selectedTank).getCollisionBoxPos()[0] + myTanks.get(selectedTank).getCollisionBoxSize()[0]*0.5)*Main.getMyWindow().getWidth());   
      int x2 = evt.getX();
      int y1 = (int)((myTanks.get(selectedTank).getCollisionBoxPos()[1] + myTanks.get(selectedTank).getCollisionBoxSize()[1]*0.5)*Main.getMyWindow().getHeight());
      int y2 = evt.getY();
      double acath = (x2-x1), gcath = (y2-y1);
      
      if (Math.sqrt(acath*acath+gcath*gcath) > driveDis*Main.getMyWindow().getWidth()) {
        
        
        int x = (int)(driveDis*Main.getMyWindow().getWidth()*(acath/Math.sqrt(acath*acath+gcath*gcath)));
        int y = (int)(driveDis*Main.getMyWindow().getWidth()*(gcath/Math.sqrt(acath*acath+gcath*gcath)));
        Main.getSD().setLine(x1, y1, x1 + x, y1 + y);
      } else{
        Main.getSD().setLine(x1, y1, x2, y2);
      }
      
      if (acath == 0) {
        if (gcath > 0) {
          myTanks.get(selectedTank).tankRot = Math.PI;
        } else {
          myTanks.get(selectedTank).tankRot = 0;
        } // end of if-else
      } else if(gcath == 0){ 
        if (acath > 0) {
          myTanks.get(selectedTank).tankRot = Math.PI * 0.5;
        } else {
          myTanks.get(selectedTank).tankRot = Math.PI * 1.5;
        } // end of if-else
      } else {                   
        myTanks.get(selectedTank).tankRot = Math.atan((gcath/acath)) + Math.PI * 0.5;                                                                         
        if(myTanks.get(selectedTank).tankRot > Math.PI*2) myTanks.get(selectedTank).tankRot = myTanks.get(selectedTank).tankRot - 2*Math.PI;
      } // end of if-else
      Log.write("Rot: " + (myTanks.get(selectedTank).tankRot), Log.LOGDEPTH_Irreal_High);
    }
  }
  public void keyPressed(KeyEvent evt){
    switch(evt.getKeyCode()){
      case Main.KEY_LEFT: case Main.KEY_LEFT_SEC: 
      if(mode == MODE_SHOOT && selectedTank >= 0){
        aim = AIM_LEFT;
      }
      
      break;
      case Main.KEY_RIGHT: case Main.KEY_RIGHT_SEC:
      if(mode == MODE_SHOOT && selectedTank >= 0){
        aim = AIM_RIGHT;
      }
      
      break;
      case Main.KEY_SEL_SHOOT: 
      if(selectedTank >= 0){
        setShooting();
      }
      
      break;
      case Main.KEY_SEL_DRIVE: 
      if(selectedTank >= 0){
        setDriving();
      }
      
      break; 
      case Main.KEY_SEL_IDLE: 
      if(selectedTank >= 0){
        setIdle();
      }
      
      break;
      case Main.KEY_SHOOT: case Main.KEY_SHOOT_SEC:
      if (mode == MODE_SHOOT && selectedTank >= 0) {
        
      } 
      break;
      case Main.KEY_MENU: case Main.KEY_MENU_SEC:
      Menu.showPauseMenu(!Menu.getPauseMenuShown());
      break;
    }
  }
  public void keyReleased(KeyEvent evt){
    switch(evt.getKeyCode()){
      case Main.KEY_LEFT: case Main.KEY_LEFT_SEC: case Main.KEY_RIGHT: case Main.KEY_RIGHT_SEC:
      if(mode == MODE_SHOOT && selectedTank >= 0){
        aim = AIM_IDLE;
      }
      
      break;
    }
  }
}