package Tanks.Client.Objects.Ingame;

import java.awt.image.*;


public abstract class GameObject{
  protected float x;
  protected float y;         
  protected float szx;
  protected float szy;
  // Ende Attribute 
  
  public GameObject(){}
  public GameObject(float x, float y, float szx, float szy){
    this.x = x;
    this.y = y;
    this.szx = szx;
    this.szy = szy;
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
  // Ende Methoden
}