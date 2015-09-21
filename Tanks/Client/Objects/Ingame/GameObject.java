package Tanks.Client.Objects.Ingame;

import java.awt.image.*;
import java.util.*;


public abstract class GameObject{
  protected float x;
  protected float y;         
  protected float szx;
  protected float szy;
  public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
  // Ende Attribute 
  
  public GameObject(float x, float y, float szx, float szy){
    this.x = x;
    this.y = y;
    this.szx = szx;
    this.szy = szy;
    objects.add(this);
  }
  // Anfang Methoden
  
  public abstract BufferedImage getTexture(); 
  
  public float[] getPos(){
    return new float[]{x,y};
  }
  public float[] getSize(){
    return new float[]{szx,szy};
  }
  public void setPos(float x, float y){
    this.x = x;
    this.y = y;
  }
  public void anim(double timeMultiplier){}
  // Ende Methoden
}