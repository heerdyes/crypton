package system;

import java.awt.*;
import java.util.*;

class Slate extends Canvas {
  ArrayList<ABox> boxes;
  
  Slate(){
    boxes=new ArrayList<>();
  }
  
  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2=(Graphics2D)g;
    for(ABox b:boxes){
      b.render(g2);
    }
  }
  
  void addbox(ABox b){
    boxes.add(b);
    repaint();
  }
  
  public void update(Graphics g){
    paint(g);
  }
}

