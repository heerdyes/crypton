package system;

import java.util.EventObject;

public class DialogBeanEvent extends EventObject {
    private String evtInfo;
    
    public DialogBeanEvent(Object o, String s) {
        super(o);
        evtInfo = s;
    }
}
