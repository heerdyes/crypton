package system;

import java.util.EventObject;

public class DialogBeanEvent{
    private String evtInfo;
    
    public DialogBeanEvent(String s) {
        evtInfo = s;
    }
    
    public String getEvtInfo(){
      return evtInfo;
    }
}
