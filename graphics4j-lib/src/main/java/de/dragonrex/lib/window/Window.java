package de.dragonrex.lib.window;

import de.dragonrex.api.handler.IKeyboardHandler;
import de.dragonrex.api.handler.ILoopHandler;
import de.dragonrex.api.utils.IColor;
import de.dragonrex.api.window.IWindow;
import de.dragonrex.lib.utils.Color;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.Sdl.SDL_Quit;
import static io.github.libsdl4j.api.SdlSubSystemConst.SDL_INIT_EVERYTHING;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.render.SDL_RendererFlags.SDL_RENDERER_ACCELERATED;
import static io.github.libsdl4j.api.render.SdlRender.*;
import static io.github.libsdl4j.api.video.SDL_WindowEventID.*;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.*;
import static io.github.libsdl4j.api.video.SdlVideo.*;
import static io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED;

public class Window implements IWindow {
    private String id;
    private SDL_Window window;
    private SDL_Renderer renderer;
    private int width = 0;
    private int height = 0;
    private String title = "SDL2 Test";
    private boolean isDebug = false;
    private boolean isVisible = false;
    private IColor color;
    private IKeyboardHandler keyboardHandler = (_) -> {};
    private ILoopHandler loopHandler = () -> {};

    public Window() {
        this.color = new Color(0, 0, 0, 0);
    }

    @Override
    public void createWindow(String id, String title, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.title = title;

        // Initialize SDL
        int result = SDL_Init(SDL_INIT_EVERYTHING);
        if (result != 0) {
            throw new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError());
        }

        // Create and init the window
        this.window = SDL_CreateWindow(title, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, SDL_WINDOW_SHOWN);
        if (this.window == null) {
            throw new IllegalStateException("Unable to create SDL window: " + SDL_GetError());
        }

        // Create and init the renderer
        this.renderer = SDL_CreateRenderer(this.window, -1, SDL_RENDERER_ACCELERATED);
        if (this.renderer == null) {
            throw new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError());
        }

        SDL_SetRenderDrawColor(this.renderer, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        // Clear the window and make it all red
        SDL_RenderClear(this.renderer);

        // Render the changes above ( which up until now had just happened behind the scenes )
        SDL_RenderPresent(this.renderer);

        // Start an event loop and react to events
        SDL_Event event = new SDL_Event();
        boolean shouldRun = true;
        while (shouldRun) {
            this.loopHandler.loop();
            SDL_SetRenderDrawColor(this.renderer, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            SDL_RenderClear(this.renderer);
            SDL_RenderPresent(this.renderer);
            while (SDL_PollEvent(event) != 0) {
                switch (event.type) {
                    case SDL_QUIT:
                        shouldRun = false;
                        break;
                    case SDL_KEYDOWN:
                        if(isDebug) {
                            System.out.println("Key Pressed: " + event.key.keysym.sym);
                        }
                        this.keyboardHandler.onKeyPressed(event.key.keysym.sym);
                        break;
                    case SDL_WINDOWEVENT:
                        switch (event.window.event) {
                            case SDL_WINDOWEVENT_SHOWN:
                                if(isDebug) System.out.println("Window Shown!");
                                break;
                            case SDL_WINDOWEVENT_HIDDEN:
                                if(isDebug) System.out.println("Window Hidden!");
                                break;
                            case SDL_WINDOWEVENT_ENTER:
                                if(isDebug) System.out.println("Mouse Enter Window!");
                                break;
                            case SDL_WINDOWEVENT_LEAVE:
                                if(isDebug) System.out.println("Mouse Leave Window!");
                                break;
                            case SDL_WINDOWEVENT_RESIZED:
                                if(isDebug) System.out.println("Window Size changed: Width = "
                                        + event.window.data1 + ", Height = " + event.window.data2);
                                break;
                            case SDL_WINDOWEVENT_CLOSE:
                                if(isDebug) System.out.println("Window Closed!");
                                shouldRun = false;
                                break;
                            case SDL_WINDOWEVENT_FOCUS_GAINED:
                                if(isDebug) System.out.println("Window Focused!");
                                break;
                            case SDL_WINDOWEVENT_FOCUS_LOST:
                                if(isDebug) System.out.println("Window UnFocused!");
                                break;
                            case SDL_WINDOWEVENT_RESTORED:
                                if(isDebug) System.out.println("Window Restored!");
                                break;
                            case SDL_WINDOWEVENT_MAXIMIZED:
                                if(isDebug) System.out.println("Window Maximized!");
                                break;
                            case SDL_WINDOWEVENT_MINIMIZED:
                                if(isDebug) System.out.println("Window Minimized!");
                                break;
                            case SDL_WINDOWEVENT_MOVED:
                                if(isDebug) System.out.println("Window Position changed: X= "
                                        + event.window.data1 + ", Y= " + event.window.data2);
                                break;
                            case SDL_WINDOWEVENT_EXPOSED:
                                if(isDebug) System.out.println("Window Exposed!");
                                break;
                            default:
                                if(isDebug) System.out.println("Other Window Event: " + event.window.event);
                                break;
                        }
                        break;
                }
            }
        }

        SDL_Quit();
    }

    @Override
    public String getID() {
        return "";
    }

    public IKeyboardHandler getKeyboardHandler() {
        return this.keyboardHandler;
    }

    @Override
    public void setKeyboardHandler(IKeyboardHandler keyboard) {
        this.keyboardHandler = keyboard;
    }

    @Override
    public ILoopHandler getLoop() {
        return this.loopHandler;
    }

    @Override
    public void setLoop(ILoopHandler loop) {
        this.loopHandler = loop;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public IColor getColor() {
        return color;
    }

    public void setColor(IColor color) {
        this.color = color;
    }

    @Override
    public void show() {
        SDL_ShowWindow(this.window);
        this.isVisible = true;
    }

    @Override
    public void hide() {
        SDL_HideWindow(this.window);
        this.isVisible = false;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }
}
