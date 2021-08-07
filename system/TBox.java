package system;

import java.awt.*;

class TBox extends ABox{
  CharGrid cg;
  
  TBox(double x,double y,int rows,int cols,float sz){
    super(x,y,cols*sz,rows*sz);
    this.cg=new CharGrid(rows,cols,sz);
  }
  
  void render(Graphics2D g2){
    super.render(g2);
    cg.render(g2);
  }
}

