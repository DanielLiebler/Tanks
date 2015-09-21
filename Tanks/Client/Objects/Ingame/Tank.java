package Tanks.Client.Objects.Ingame;

import Tanks.Client.Graphic.TextureManager; 
import Tanks.Client.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class Tank extends GameObject{
  
  public static final double SELECTED_ROT_SPEED = Math.PI * 0.02;
  
  protected static BufferedImage textureSelected;
  protected double selectedRot;
  protected BufferedImage textureTank;
  protected BufferedImage textureTurret;
  protected boolean isActive = true;
  protected boolean isSelected = false;       
  protected double[] movement = new double[]{0,0};
  public double tankRot, turretRot;
  
  public Tank(float x, float y, float szx, float szy){ 
    super((float)(x-0.5*szx),(float)(y-0.5*szy),(float)(szx*2),(float)(szy*2));
    int r,g,b;
    Random r1 = new Random();
    
    r = r1.nextInt(256);
    g = r1.nextInt(256);
    b = r1.nextInt(256);
    
    init(r,g,b);
  }
  public Tank(float x, float y, float szx, float szy, int r, int g, int b){ 
    super((float)(x-0.5*szx),(float)(y-0.5*szy),(float)(szx*2),(float)(szy*2)); 
    
    init(r,g,b);
  }
  public void init(int r, int g, int b){
    
    Log.write("Painting Tank: " + this + " r:" + r + ", g:" + g + ", b:" + b, Log.LOGDEPTH_Middle);
    textureSelected = TextureManager.getTexture("select.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), false);
    
    textureTank = new BufferedImage((int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), BufferedImage.TYPE_4BYTE_ABGR_PRE);  
    textureTank.getGraphics().drawImage(TextureManager.getTexture("tank.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), true), 0, 0, null);
    textureTank.getGraphics().drawImage(colorImage(TextureManager.getTexture("tank_color.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), true), r, g, b, 255), 0, 0, null);
    
    textureTurret = new BufferedImage((int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), BufferedImage.TYPE_4BYTE_ABGR_PRE);  
    textureTurret.getGraphics().drawImage(TextureManager.getTexture("turret.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), true), 0, 0, null);
    textureTurret.getGraphics().drawImage(colorImage(TextureManager.getTexture("turret_color.png", (int)(this.szx*Main.getMyWindow().getWidth()), (int)(this.szy*Main.getMyWindow().getHeight()), true), r, g, b, 255), 0, 0, null);
    
    
    tankRot = 0;
    turretRot = 0;
  }
  public void anim(double timeMultiplier){
    this.setPos((float)(getPos()[0] + movement[0]*timeMultiplier), (float)(getPos()[1] + movement[1]*timeMultiplier));
  }
  public void setMovement(double x, double y){
    movement = new double[]{x,y};
  }
  public boolean isActive(){
    return isActive;
  }
  public void selectTank(){
    isSelected = true;
    selectedRot = 0;
  }
  public void deSelectTank(){
    isSelected = false;
  }
  public float[] getCollisionBoxPos(){
    return new float[]{this.x + 0.25f*szx, this.y + 0.25f*szy};
  }
  public float[] getCollisionBoxSize(){
    return new float[]{this.szx/2, this.szy/2};
  }
  public BufferedImage getTexture(){
    BufferedImage ret = new BufferedImage(textureTank.getWidth(), textureTank.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics2D retGra = ret.createGraphics();
    
    AffineTransform aTransTank = new AffineTransform();  
    AffineTransform aTransTurret = new AffineTransform();
    
    aTransTank.translate(textureTank.getWidth()/2, textureTank.getHeight()/2); 
    aTransTank.rotate(tankRot);
    aTransTank.translate(-textureTank.getWidth()/2, -textureTank.getHeight()/2);
    
    aTransTurret.translate(textureTurret.getWidth()/2, textureTurret.getHeight()/2); 
    aTransTurret.rotate(turretRot);
    aTransTurret.translate(-textureTurret.getWidth()/2, -textureTurret.getHeight()/2);     
    
    
    
    
    retGra.drawImage(textureTank, aTransTank, null);
    retGra.drawImage(textureTurret, aTransTurret, null);
    
    if(isSelected){
      AffineTransform aTransSel = new AffineTransform();  
      aTransSel.translate(textureSelected.getWidth()/2, textureSelected.getHeight()/2); 
      aTransSel.rotate(selectedRot);
      aTransSel.translate(-textureSelected.getWidth()/2, -textureSelected.getHeight()/2);  
      retGra.drawImage(textureSelected, aTransSel, null);
      selectedRot += SELECTED_ROT_SPEED;
    }
    
    Log.write("Tank returned: " + ret.toString(), Log.LOGDEPTH_Irreal_High);
    
    return  ret;
  }
  protected BufferedImage colorImage(BufferedImage src, int r, int g, int b, int a){
    int j, k, red, green, blue, alpha;
    Graphics srcGra = src.getGraphics();
    for(j = 0; j < src.getWidth(); j++){
      for(k = 0; k < src.getHeight(); k++){
        Color c = new Color(src.getRGB(j, k));
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();  
        alpha = c.getAlpha();
        if(red == 255 && green == 255 && blue == 255){
          srcGra.setColor(new Color(r, g, b, a));
          srcGra.fillRect(j,k,1,1);
        }
      }
    }
    return src;
  }
}