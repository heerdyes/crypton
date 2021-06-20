package system;

import java.util.EventObject;

public class MenuBeanEvent extends EventObject {
    private String evtInfo;

    public MenuBeanEvent(Object o, String t) {
        super(o);
        evtInfo = t;
    }

    public String getEvtInfo() {
        return evtInfo;
    }
}
