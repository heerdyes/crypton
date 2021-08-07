package system;

import java.util.*;
import java.awt.event.*;

class InDevCtl implements KeyListener, MouseListener{
  ArrayList<KbSensor> ksubs;
  ArrayList<MsSensor> msubs;
  
  InDevCtl(){
    ksubs=new ArrayList<>();
    msubs=new ArrayList<>();
  }
  
  public void bcastKbDown(int kc,char c){
    for(KbSensor kbs:ksubs){
      kbs.kbdown(kc,c);
    }
  }

  public void bcastKbUp(int kc,char c){
    for(KbSensor kbs:ksubs){
      kbs.kbup(kc,c);
    }
  }
  
  public void bcastMsDown(int x,int y){
    for(MsSensor mss:msubs){
      mss.msdown(x,y);
    }
  }

  public void bcastMsUp(int x,int y){
    for(MsSensor mss:msubs){
      mss.msup(x,y);
    }
  }
  
  public void registerKbSensor(KbSensor ks){
    ksubs.add(ks);
  }
  
  public void registerMsSensor(MsSensor ms){
    msubs.add(ms);
  }
  
  public void keyPressed(KeyEvent e){
    bcastKbDown(e.getKeyCode(),e.getKeyChar());
  }

  public void keyTyped(KeyEvent e){}
  public void keyReleased(KeyEvent e){
    bcastKbUp(e.getKeyCode(),e.getKeyChar());
  }
  
  public void mousePressed(MouseEvent e){
    bcastMsDown(e.getX(),e.getY());
  }
  public void mouseClicked(MouseEvent e){}
  public void mouseReleased(MouseEvent e){
    bcastMsUp(e.getX(),e.getY());
  }
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
}

