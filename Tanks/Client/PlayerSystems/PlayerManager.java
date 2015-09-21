package Tanks.Client.PlayerSystems;
               
import java.awt.event.*;
import java.util.*;

public class PlayerManager{
  private static ArrayList<Player> players = new ArrayList<Player>();
  private static int actPlayer = 0;
  
  public static void addPlayer(Player p1){
    players.add(p1);
  }
  public static ArrayList<Player> getPlayers() {
    return players;
  }
  
  public static void rotate(){
    actPlayer++;
    if (actPlayer > players.size()-1) {
      actPlayer = 0;
    } // end of if
    if (!players.get(actPlayer).isActive()) {
      rotate();
    }
  }
  public static void click(MouseEvent evt){
    if (players.size() > 0) players.get(actPlayer).click(evt);
  }
  public static void keyPressed(KeyEvent evt){
    if (players.size() > 0) players.get(actPlayer).keyPressed(evt);
  }
  public void mouseMoved(MouseEvent evt){
    if (players.size() > 0) players.get(actPlayer).mouseMoved(evt);
  }
}