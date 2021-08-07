package system;

public class TextBeanEvent{
    String evtInfo;
    
    public TextBeanEvent(String s) {
        evtInfo = s;
    }
    
    public String getEvtInfo(){
      return evtInfo;
    }
    
    public String toString(){
      return evtInfo;
    }
}
