package system;

public class KbdCtlSignal{
  String msg;
  String kcid;
  
  KbdCtlSignal(String kid,String m){
    kcid=kid;
    msg=m;
  }
  
  public String toString(){
    return String.format("[%s] %s",kcid,msg);
  }
}

