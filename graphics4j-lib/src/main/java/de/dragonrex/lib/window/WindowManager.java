package de.dragonrex.lib.window;

import de.dragonrex.api.window.IWindow;
import de.dragonrex.api.window.IWindowManager;

import java.util.HashMap;
import java.util.Map;

public class WindowManager implements IWindowManager {
    private Map<String, IWindow> windows;

    public WindowManager() {
        this.windows = new HashMap<>();
    }

    @Override
    public Map<String, IWindow> getWindows() {
        return this.windows;
    }

    @Override
    public IWindow getWindowById(String id) {
        return this.windows.get(id);
    }

    @Override
    public void addWindow(IWindow window) {
        this.windows.put(window.getID(), window);
    }

    @Override
    public void setWindows(IWindow... windows) {
        for (IWindow window : windows) {
            addWindow(window);
        }
    }

    @Override
    public void showWindow(String id) {
        getWindowById(id).show();
    }

    @Override
    public void showWindow(IWindow window) {
        window.show();
    }

    @Override
    public void hideWindow(String id) {
        getWindowById(id).hide();
    }

    @Override
    public void hideWindow(IWindow window) {
        window.hide();
    }
}
