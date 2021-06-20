package system;

import java.util.EventObject;

public class FrameBeanEvent extends EventObject {
    private String evtInfo;
    
    public FrameBeanEvent(Object o, String s) {
        super(o);
        evtInfo = s;
    }
    
    public String getEvtInfo() {
        return evtInfo;
    }
}
