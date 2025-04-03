package com.cyberpunk.game;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Handles all input for the game.
 */
public class InputHandler {
    
    // Window handle
    private final long window;
    
    // Key states
    private boolean[] keys = new boolean[GLFW_KEY_LAST + 1];
    private boolean[] keysPressed = new boolean[GLFW_KEY_LAST + 1];
    
    // Mouse states
    private double mouseX, mouseY;
    private boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    private boolean[] mouseButtonsPressed = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    
    /**
     * Constructor.
     * 
     * @param window GLFW window handle
     */
    public InputHandler(long window) {
        this.window = window;
        
        // Set up key callback
        glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            if (key >= 0 && key <= GLFW_KEY_LAST) {
                if (action == GLFW_PRESS) {
                    keys[key] = true;
                    keysPressed[key] = true;
                } else if (action == GLFW_RELEASE) {
                    keys[key] = false;
                }
            }
        });
        
        // Set up mouse position callback
        glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        });
        
        // Set up mouse button callback
        glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            if (button >= 0 && button <= GLFW_MOUSE_BUTTON_LAST) {
                if (action == GLFW_PRESS) {
                    mouseButtons[button] = true;
                    mouseButtonsPressed[button] = true;
                } else if (action == GLFW_RELEASE) {
                    mouseButtons[button] = false;
                }
            }
        });
    }
    
    /**
     * Process input.
     * 
     * @param delta Time since last update in seconds
     */
    public void processInput(double delta) {
        // Reset pressed states
        for (int i = 0; i < keysPressed.length; i++) {
            keysPressed[i] = false;
        }
        
        for (int i = 0; i < mouseButtonsPressed.length; i++) {
            mouseButtonsPressed[i] = false;
        }
        
        // Poll events
        glfwPollEvents();
        
        // Check for specific key presses
        if (isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window, true);
        }
        
        // Map key (M)
        if (isKeyPressed(GLFW_KEY_M)) {
            System.out.println("Map expanded/collapsed");
            // Will be implemented in UI manager
        }
        
        // Phone key (backtick/tilde)
        if (isKeyPressed(GLFW_KEY_GRAVE_ACCENT)) {
            System.out.println("Phone opened/closed");
            // Will be implemented in UI manager
        }
    }
    
    /**
     * Check if a key is currently held down.
     * 
     * @param key GLFW key code
     * @return true if the key is held down
     */
    public boolean isKeyDown(int key) {
        return key >= 0 && key <= GLFW_KEY_LAST && keys[key];
    }
    
    /**
     * Check if a key was just pressed this frame.
     * 
     * @param key GLFW key code
     * @return true if the key was just pressed
     */
    public boolean isKeyPressed(int key) {
        return key >= 0 && key <= GLFW_KEY_LAST && keysPressed[key];
    }
    
    /**
     * Check if a mouse button is currently held down.
     * 
     * @param button GLFW mouse button code
     * @return true if the button is held down
     */
    public boolean isMouseButtonDown(int button) {
        return button >= 0 && button <= GLFW_MOUSE_BUTTON_LAST && mouseButtons[button];
    }
    
    /**
     * Check if a mouse button was just pressed this frame.
     * 
     * @param button GLFW mouse button code
     * @return true if the button was just pressed
     */
    public boolean isMouseButtonPressed(int button) {
        return button >= 0 && button <= GLFW_MOUSE_BUTTON_LAST && mouseButtonsPressed[button];
    }
    
    /**
     * Get the current mouse X position.
     * 
     * @return mouse X position
     */
    public double getMouseX() {
        return mouseX;
    }
    
    /**
     * Get the current mouse Y position.
     * 
     * @return mouse Y position
     */
    public double getMouseY() {
        return mouseY;
    }
}
