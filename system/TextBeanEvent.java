package system;

import java.util.EventObject;

public class TextBeanEvent extends EventObject {
    private String evtInfo;
    
    public TextBeanEvent(Object o, String s) {
        super(o);
        evtInfo = s;
    }
}
