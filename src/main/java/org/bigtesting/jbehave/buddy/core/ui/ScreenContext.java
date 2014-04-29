package org.bigtesting.jbehave.buddy.core.ui;

public interface ScreenContext {

    void close();
    
    void setTitle(String title);
    
    void enableSaving(boolean enable);
}
