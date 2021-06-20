package system;

import java.util.EventListener;

public interface FrameBeanListener extends EventListener {
    public void frameBeanActionPerformed(FrameBeanEvent fbe);
}
