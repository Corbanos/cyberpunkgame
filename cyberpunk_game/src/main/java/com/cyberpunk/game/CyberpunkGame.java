package com.cyberpunk.game;

import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Main class for the Cyberpunk City Game.
 * This class handles the game initialization, main loop, and cleanup.
 */
public class CyberpunkGame {

    // Window handle
    private long window;
    
    // Window dimensions
    private int width = 1280;
    private int height = 720;
    
    // Window title
    private final String title = "Cyberpunk City";
    
    // Game states
    private boolean running = false;
    
    // Input handler
    private InputHandler inputHandler;
    
    // Game components
    private Renderer renderer;
    private GameWorld gameWorld;
    private UIManager uiManager;
    
    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {
        CyberpunkGame game = new CyberpunkGame();
        game.run();
    }
    
    /**
     * Run the game.
     */
    public void run() {
        System.out.println("LWJGL Version: " + Version.getVersion());
        
        try {
            init();
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    
    /**
     * Initialize the game.
     */
    private void init() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        
        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Set up input handling
        inputHandler = new InputHandler(window);
        
        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);
            
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            
            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        
        // Enable v-sync
        glfwSwapInterval(1);
        
        // Make the window visible
        glfwShowWindow(window);
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        
        // Initialize game components
        renderer = new Renderer();
        gameWorld = new GameWorld();
        uiManager = new UIManager(width, height);
        
        // Set the running flag
        running = true;
    }
    
    /**
     * Main game loop.
     */
    private void loop() {
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        // Game loop variables
        double lastTime = glfwGetTime();
        double delta = 0;
        double nowTime;
        double timePerFrame = 1.0 / 60.0; // Target 60 FPS
        
        // Game loop
        while (running && !glfwWindowShouldClose(window)) {
            // Calculate delta time
            nowTime = glfwGetTime();
            delta = nowTime - lastTime;
            lastTime = nowTime;
            
            // Process input
            inputHandler.processInput(delta);
            
            // Update game state
            update(delta);
            
            // Render
            render();
            
            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
    
    /**
     * Update game state.
     * 
     * @param delta Time since last update in seconds
     */
    private void update(double delta) {
        gameWorld.update(delta);
        uiManager.update(delta);
    }
    
    /**
     * Render the game.
     */
    private void render() {
        // Clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // Render the game world
        renderer.renderWorld(gameWorld);
        
        // Render the UI
        renderer.renderUI(uiManager);
    }
    
    /**
     * Clean up resources.
     */
    private void cleanup() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
