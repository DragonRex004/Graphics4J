package de.dragonrex.lib.window;

import com.sun.jna.ptr.IntByReference;
import de.dragonrex.api.window.Window;
import io.github.libsdl4j.api.event.SDL_Event;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.video.SDL_Window;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.Sdl.SDL_Quit;
import static io.github.libsdl4j.api.SdlSubSystemConst.SDL_INIT_EVERYTHING;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;
import static io.github.libsdl4j.api.event.SDL_EventType.*;
import static io.github.libsdl4j.api.event.SdlEvents.SDL_PollEvent;
import static io.github.libsdl4j.api.keycode.SDL_Keycode.SDLK_SPACE;
import static io.github.libsdl4j.api.pixels.SdlPixelsConst.SDL_ALPHA_OPAQUE;
import static io.github.libsdl4j.api.render.SDL_RendererFlags.SDL_RENDERER_ACCELERATED;
import static io.github.libsdl4j.api.render.SdlRender.*;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_RESIZABLE;
import static io.github.libsdl4j.api.video.SDL_WindowFlags.SDL_WINDOW_SHOWN;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_CreateWindow;
import static io.github.libsdl4j.api.video.SdlVideo.SDL_GetWindowSize;
import static io.github.libsdl4j.api.video.SdlVideoConst.SDL_WINDOWPOS_CENTERED;

public class WindowImpl implements Window {
    private int width = 0;
    private int height = 0;
    private String title = "SDL2 Test";

    @Override
    public void createWindow(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        // Initialize SDL
        int result = SDL_Init(SDL_INIT_EVERYTHING);
        if (result != 0) {
            throw new IllegalStateException("Unable to initialize SDL library (Error code " + result + "): " + SDL_GetError());
        }

        // Create and init the window
        SDL_Window window = SDL_CreateWindow(title, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
        if (window == null) {
            throw new IllegalStateException("Unable to create SDL window: " + SDL_GetError());
        }

        // Create and init the renderer
        SDL_Renderer renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
        if (renderer == null) {
            throw new IllegalStateException("Unable to create SDL renderer: " + SDL_GetError());
        }

        SDL_SetRenderDrawColor(renderer, (byte) 0, (byte) 255, (byte) 0, (byte) SDL_ALPHA_OPAQUE);

        // Clear the window and make it all red
        SDL_RenderClear(renderer);

        // Render the changes above ( which up until now had just happened behind the scenes )
        SDL_RenderPresent(renderer);

        // Start an event loop and react to events
        SDL_Event evt = new SDL_Event();
        boolean shouldRun = true;
        while (shouldRun) {

            SDL_GetWindowSize(window, new IntByReference(this.width), new IntByReference(this.height));
            SDL_SetRenderDrawColor(renderer, (byte) 0, (byte) 255, (byte) 0, (byte) SDL_ALPHA_OPAQUE);
            SDL_RenderClear(renderer);

            SDL_Rect sdlRect = new SDL_Rect();
            sdlRect.x = 150;
            sdlRect.y = 150;
            sdlRect.w = 50;
            sdlRect.h = 50;
            SDL_SetRenderDrawColor(renderer, (byte) 255, (byte) 255, (byte) 0, (byte) SDL_ALPHA_OPAQUE);
            SDL_RenderFillRect(renderer, sdlRect);

            while (SDL_PollEvent(evt) != 0) {
                switch (evt.type) {
                    case SDL_QUIT:
                        shouldRun = false;
                        break;
                    case SDL_KEYDOWN:
                        if (evt.key.keysym.sym == SDLK_SPACE) {
                            System.out.println("SPACE pressed");
                        }
                        break;
                    case SDL_WINDOWEVENT:
                        System.out.println("Window event " + evt.window.event);
                    default:
                        break;
                }
            }
        }

        SDL_Quit();
    }
}
