package system;

import java.util.EventListener;

public interface TextBeanListener extends EventListener {
    public void textBeanActionPerformed(TextBeanEvent tbe);
}
