package de.dragonrex.api.window;

import java.util.Map;

public interface IWindowManager {
    Map<String, IWindow> getWindows();
    IWindow getWindowById(String id);
    void addWindow(IWindow window);
    void setWindows(IWindow... windows);
    void showWindow(String id);
    void showWindow(IWindow window);
    void hideWindow(String id);
    void hideWindow(IWindow window);
}
