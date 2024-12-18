package de.dragonrex.api.window;

import de.dragonrex.api.handler.IKeyboardHandler;
import de.dragonrex.api.handler.ILoopHandler;
import de.dragonrex.api.utils.IColor;

import java.util.function.Consumer;

public interface IWindow {
    void createWindow(String id, String title, int width, int height);
    String getID();
    IKeyboardHandler getKeyboardHandler();
    void setKeyboardHandler(IKeyboardHandler keyboard);
    ILoopHandler getLoop();
    void setLoop(ILoopHandler loop);
    boolean isDebug();
    void setDebug(boolean debug);
    String getTitle();
    void setTitle(String title);
    int getHeight();
    void setHeight(int height);
    int getWidth();
    void setWidth(int width);
    IColor getColor();
    void setColor(IColor color);
    void show();
    void hide();
    boolean isVisible();
}
