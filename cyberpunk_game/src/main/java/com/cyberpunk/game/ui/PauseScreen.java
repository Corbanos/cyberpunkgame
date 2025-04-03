package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector2f;

import com.cyberpunk.game.character.Player;

/**
 * Manages the pause screen in the game.
 */
public class PauseScreen {
    
    // UI properties
    private boolean visible;
    private Vector2f position;
    private float width;
    private float height;
    
    // Menu options
    private String[] menuOptions;
    private int selectedOption;
    
    // Player reference
    private Player player;
    
    /**
     * Constructor.
     * 
     * @param player Player reference
     */
    public PauseScreen(Player player) {
        this.player = player;
        
        visible = false;
        position = new Vector2f(0, 0); // Will be centered on screen
        width = 400;
        height = 500;
        
        menuOptions = new String[] {
            "Resume Game",
            "Character",
            "Inventory",
            "Map",
            "Settings",
            "Help",
            "Exit to Main Menu",
            "Quit Game"
        };
        
        selectedOption = 0;
    }
    
    /**
     * Update the pause screen.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Nothing to update if not visible
        if (!visible) {
            return;
        }
        
        // Update logic here if needed
    }
    
    /**
     * Render the pause screen.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        // Nothing to render if not visible
        if (!visible) {
            return;
        }
        
        // Center pause screen on screen
        position.x = (screenWidth - width) / 2;
        position.y = (screenHeight - height) / 2;
        
        // Draw semi-transparent overlay
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f); // Black, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(screenWidth, 0);
        GL11.glVertex2f(screenWidth, screenHeight);
        GL11.glVertex2f(0, screenHeight);
        GL11.glEnd();
        
        // Draw pause menu background
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.9f); // Dark gray, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw pause menu border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw pause menu title
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw menu options
        float optionHeight = 50;
        float optionPadding = 10;
        float startY = position.y + 100; // Start below title
        
        for (int i = 0; i < menuOptions.length; i++) {
            float optionY = startY + i * (optionHeight + optionPadding);
            
            // Draw option background
            if (i == selectedOption) {
                GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected option
            } else {
                GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
            }
            
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(position.x + 20, optionY);
            GL11.glVertex2f(position.x + width - 20, optionY);
            GL11.glVertex2f(position.x + width - 20, optionY + optionHeight);
            GL11.glVertex2f(position.x + 20, optionY + optionHeight);
            GL11.glEnd();
            
            // Draw option text
            // This would use a text rendering system
            // For now, just a placeholder
        }
    }
    
    /**
     * Toggle pause screen visibility.
     */
    public void toggleVisibility() {
        visible = !visible;
    }
    
    /**
     * Show the pause screen.
     */
    public void show() {
        visible = true;
    }
    
    /**
     * Hide the pause screen.
     */
    public void hide() {
        visible = false;
    }
    
    /**
     * Check if pause screen is visible.
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Select the next option.
     */
    public void selectNextOption() {
        selectedOption = (selectedOption + 1) % menuOptions.length;
    }
    
    /**
     * Select the previous option.
     */
    public void selectPreviousOption() {
        selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
    }
    
    /**
     * Get the selected option.
     * 
     * @return The selected option index
     */
    public int getSelectedOption() {
        return selectedOption;
    }
    
    /**
     * Get the selected option text.
     * 
     * @return The selected option text
     */
    public String getSelectedOptionText() {
        return menuOptions[selectedOption];
    }
    
    /**
     * Execute the selected option.
     * 
     * @return true if game should continue, false if game should exit
     */
    public boolean executeSelectedOption() {
        switch (selectedOption) {
            case 0: // Resume Game
                hide();
                return true;
                
            case 1: // Character
                // Open character screen
                // This would open the character screen
                // For now, just a placeholder
                return true;
                
            case 2: // Inventory
                // Open inventory
                // This would open the inventory
                // For now, just a placeholder
                return true;
                
            case 3: // Map
                // Open map
                // This would open the map
                // For now, just a placeholder
                return true;
                
            case 4: // Settings
                // Open settings
                // This would open the settings
                // For now, just a placeholder
                return true;
                
            case 5: // Help
                // Open help
                // This would open the help
                // For now, just a placeholder
                return true;
                
            case 6: // Exit to Main Menu
                // Exit to main menu
                // This would exit to the main menu
                // For now, just a placeholder
                return true;
                
            case 7: // Quit Game
                // Quit game
                return false;
                
            default:
                return true;
        }
    }
}
