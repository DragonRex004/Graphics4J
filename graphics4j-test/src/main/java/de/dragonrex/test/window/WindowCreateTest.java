package de.dragonrex.test.window;

import de.dragonrex.lib.utils.Color;
import de.dragonrex.lib.window.Window;

import io.github.libsdl4j.api.keycode.SDL_Keycode;

public class WindowCreateTest {

    public static void main(String[] args) {
        Window window = new Window();
        window.setColor(new Color(255, 255, 255, 0));
        window.setDebug(true);
        window.setKeyboardHandler((key) -> {
            switch (key) {
                case SDL_Keycode.SDLK_SPACE -> System.out.println("Space Pressed!");
                case SDL_Keycode.SDLK_F -> System.out.println("F Pressed!");
            }
        });
        window.setLoop(() -> {

        });
        window.createWindow("Test Window", "Hello World!", 800, 400);
    }
}
