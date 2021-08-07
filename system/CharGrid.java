package system;

import java.awt.*;

public class CharGrid {
  char[][] grid;
  int currow,curcol;
  float cs;
  
  void initgrid(){
    for(int i=0;i<grid.length;i++){
      for(int j=0;j<grid[i].length;j++){
        grid[i][j]=' ';
      }
    }
  }
  
  CharGrid(int r,int c,float cs){
    grid=new char[r][c];
    initgrid();
    currow=0;
    curcol=0;
    this.cs=cs;
  }
  
  void insch(char c){
    if(currow>=grid.length){
      throw new RuntimeException("exceeded row length");
    }
    grid[currow][curcol]=c;
    curcol+=1;
    if(curcol>=grid[0].length){
      currow+=1;
      curcol=0;
    }
  }
  
  void render(Graphics2D g2){
    for(int i=0;i<grid.length;i++){
      for(int j=0;j<grid[i].length;j++){
        g2.drawString(""+grid[i][j],i*cs,j*cs);
      }
    }
  }
  
  void insln(int lnum,String s){
    currow=lnum;
    curcol=0;
    for(int i=0;i<s.length();i++){
      insch(s.charAt(i));
    }
  }
  
  void insertString(int pos,String x){
    currow=pos/grid.length;
    curcol=pos%grid[0].length;
    for(int i=0;i<x.length();i++){
      insch(x.charAt(i));
    }
  }
  
  String getText(){
    StringBuffer buf=new StringBuffer();
    for(int i=0;i<grid.length;i++){
      for(int j=0;j<grid[i].length;j++){
        buf.append(grid[i][j]);
      }
    }
    return buf.toString();
  }
}

