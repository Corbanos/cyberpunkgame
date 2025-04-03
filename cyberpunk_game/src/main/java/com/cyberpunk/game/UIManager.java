package com.cyberpunk.game;

import static org.lwjgl.opengl.GL11.*;

/**
 * Manages all UI elements in the game.
 */
public class UIManager {
    
    // UI dimensions
    private int width;
    private int height;
    
    // UI states
    private boolean mapExpanded = false;
    private boolean phoneOpen = false;
    private boolean inventoryOpen = false;
    private boolean pauseMenuOpen = false;
    
    // UI components
    private MiniMap miniMap;
    private Phone phone;
    private Inventory inventory;
    private PauseMenu pauseMenu;
    private TutorialTips tutorialTips;
    
    /**
     * Constructor.
     * 
     * @param width Screen width
     * @param height Screen height
     */
    public UIManager(int width, int height) {
        this.width = width;
        this.height = height;
        
        // Initialize UI components
        miniMap = new MiniMap(50, 50, 200, 200);
        phone = new Phone(width / 2 - 150, height / 2 - 250, 300, 500);
        inventory = new InventoryUI(width / 2 - 200, height / 2 - 150, 400, 300);
        pauseMenu = new PauseMenu(width / 2 - 150, height / 2 - 200, 300, 400);
        tutorialTips = new TutorialTips(20, 20);
        
        // Add initial tutorial tips
        tutorialTips.addTip("Press W, A, S, D to move");
        tutorialTips.addTip("Press M to open the map");
        tutorialTips.addTip("Press ~/` to open your phone");
        tutorialTips.addTip("Press E to interact with objects");
        tutorialTips.addTip("Press F to enter/exit vehicles");
    }
    
    /**
     * Update the UI.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(double delta) {
        // Update UI components
        miniMap.update(delta);
        
        if (phoneOpen) {
            phone.update(delta);
        }
        
        if (inventoryOpen) {
            inventory.update(delta);
        }
        
        if (pauseMenuOpen) {
            pauseMenu.update(delta);
        }
        
        tutorialTips.update(delta);
    }
    
    /**
     * Render the UI.
     */
    public void render() {
        // Always render mini-map
        if (!mapExpanded) {
            miniMap.render();
        } else {
            // Render expanded map
            miniMap.renderExpanded(width / 2 - 300, height / 2 - 300, 600, 600);
        }
        
        // Render phone if open
        if (phoneOpen) {
            phone.render();
        }
        
        // Render inventory if open
        if (inventoryOpen) {
            inventory.render();
        }
        
        // Render pause menu if open
        if (pauseMenuOpen) {
            pauseMenu.render();
        }
        
        // Always render tutorial tips
        tutorialTips.render();
    }
    
    /**
     * Toggle the expanded map.
     */
    public void toggleMap() {
        mapExpanded = !mapExpanded;
    }
    
    /**
     * Toggle the phone.
     */
    public void togglePhone() {
        phoneOpen = !phoneOpen;
    }
    
    /**
     * Toggle the inventory.
     */
    public void toggleInventory() {
        inventoryOpen = !inventoryOpen;
    }
    
    /**
     * Toggle the pause menu.
     */
    public void togglePauseMenu() {
        pauseMenuOpen = !pauseMenuOpen;
    }
    
    /**
     * Check if the map is expanded.
     * 
     * @return true if the map is expanded
     */
    public boolean isMapExpanded() {
        return mapExpanded;
    }
    
    /**
     * Check if the phone is open.
     * 
     * @return true if the phone is open
     */
    public boolean isPhoneOpen() {
        return phoneOpen;
    }
    
    /**
     * Check if the inventory is open.
     * 
     * @return true if the inventory is open
     */
    public boolean isInventoryOpen() {
        return inventoryOpen;
    }
    
    /**
     * Check if the pause menu is open.
     * 
     * @return true if the pause menu is open
     */
    public boolean isPauseMenuOpen() {
        return pauseMenuOpen;
    }
}

/**
 * Represents the mini-map in the corner of the screen.
 */
class MiniMap {
    private int x, y, width, height;
    
    public MiniMap(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(double delta) {
        // Update mini-map (e.g., rotate with player)
    }
    
    public void render() {
        // Render mini-map background
        glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render mini-map border
        glColor3f(0.8f, 0.8f, 0.8f);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render map elements (placeholder)
        glColor3f(0.2f, 0.7f, 0.2f);
        glBegin(GL_QUADS);
        glVertex2f(x + width/2 - 50, y + height/2 - 50);
        glVertex2f(x + width/2 + 50, y + height/2 - 50);
        glVertex2f(x + width/2 + 50, y + height/2 + 50);
        glVertex2f(x + width/2 - 50, y + height/2 + 50);
        glEnd();
        
        // Render player position (red dot)
        glColor3f(1.0f, 0.0f, 0.0f);
        glPointSize(5.0f);
        glBegin(GL_POINTS);
        glVertex2f(x + width/2, y + height/2);
        glEnd();
    }
    
    public void renderExpanded(int x, int y, int width, int height) {
        // Render expanded map background
        glColor4f(0.0f, 0.0f, 0.0f, 0.9f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render expanded map border
        glColor3f(0.8f, 0.8f, 0.8f);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render map elements (placeholder)
        glColor3f(0.2f, 0.7f, 0.2f);
        glBegin(GL_QUADS);
        glVertex2f(x + width/2 - 200, y + height/2 - 200);
        glVertex2f(x + width/2 + 200, y + height/2 - 200);
        glVertex2f(x + width/2 + 200, y + height/2 + 200);
        glVertex2f(x + width/2 - 200, y + height/2 + 200);
        glEnd();
        
        // Render player position (red dot)
        glColor3f(1.0f, 0.0f, 0.0f);
        glPointSize(8.0f);
        glBegin(GL_POINTS);
        glVertex2f(x + width/2, y + height/2);
        glEnd();
        
        // Render map legend
        glColor3f(1.0f, 1.0f, 1.0f);
        renderText(x + 20, y + 20, "Cyberpunk City Map");
        renderText(x + 20, y + 40, "Red dot: Player position");
        renderText(x + 20, y + 60, "Green: Buildings");
        renderText(x + 20, y + 80, "Press M to close");
    }
    
    private void renderText(int x, int y, String text) {
        // Placeholder for text rendering
        // In a real implementation, this would use a proper text rendering system
    }
}

/**
 * Represents the phone interface.
 */
class Phone {
    private int x, y, width, height;
    private int currentTab = 0;
    private final String[] tabs = {"Contacts", "Messages", "Map", "Missions", "Settings"};
    
    public Phone(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(double delta) {
        // Update phone interface
    }
    
    public void render() {
        // Render phone background
        glColor4f(0.1f, 0.1f, 0.1f, 0.9f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render phone border
        glColor3f(0.7f, 0.7f, 0.7f);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render tabs
        int tabWidth = width / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            if (i == currentTab) {
                glColor3f(0.2f, 0.6f, 0.8f);
            } else {
                glColor3f(0.3f, 0.3f, 0.3f);
            }
            
            glBegin(GL_QUADS);
            glVertex2f(x + i * tabWidth, y);
            glVertex2f(x + (i + 1) * tabWidth, y);
            glVertex2f(x + (i + 1) * tabWidth, y + 30);
            glVertex2f(x + i * tabWidth, y + 30);
            glEnd();
            
            // Tab text would be rendered here
        }
        
        // Render content based on current tab
        renderTabContent();
    }
    
    private void renderTabContent() {
        // Placeholder for tab content rendering
        // This would render different content based on the currentTab
    }
    
    public void setTab(int tab) {
        if (tab >= 0 && tab < tabs.length) {
            currentTab = tab;
        }
    }
}

/**
 * Represents the inventory UI.
 */
class InventoryUI {
    private int x, y, width, height;
    private int selectedSlot = 0;
    private final int rows = 4;
    private final int cols = 9;
    
    public InventoryUI(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(double delta) {
        // Update inventory UI
    }
    
    public void render() {
        // Render inventory background
        glColor4f(0.2f, 0.2f, 0.2f, 0.9f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render inventory border
        glColor3f(0.5f, 0.5f, 0.5f);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render inventory slots
        int slotSize = 40;
        int padding = 5;
        int startX = x + (width - (cols * slotSize + (cols - 1) * padding)) / 2;
        int startY = y + 50;
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int slotX = startX + col * (slotSize + padding);
                int slotY = startY + row * (slotSize + padding);
                int slotIndex = row * cols + col;
                
                // Highlight selected slot
                if (slotIndex == selectedSlot) {
                    glColor3f(0.5f, 0.5f, 0.8f);
                } else {
                    glColor3f(0.3f, 0.3f, 0.3f);
                }
                
                glBegin(GL_QUADS);
                glVertex2f(slotX, slotY);
                glVertex2f(slotX + slotSize, slotY);
                glVertex2f(slotX + slotSize, slotY + slotSize);
                glVertex2f(slotX, slotY + slotSize);
                glEnd();
                
                // Slot border
                glColor3f(0.4f, 0.4f, 0.4f);
                glBegin(GL_LINE_LOOP);
                glVertex2f(slotX, slotY);
                glVertex2f(slotX + slotSize, slotY);
                glVertex2f(slotX + slotSize, slotY + slotSize);
                glVertex2f(slotX, slotY + slotSize);
                glEnd();
                
                // Item rendering would go here
            }
        }
        
        // Render inventory title
        glColor3f(1.0f, 1.0f, 1.0f);
        // renderText(x + width/2 - 40, y + 20, "Inventory");
    }
    
    public void selectSlot(int slot) {
        if (slot >= 0 && slot < rows * cols) {
            selectedSlot = slot;
        }
    }
}

/**
 * Represents the pause menu.
 */
class PauseMenu {
    private int x, y, width, height;
    private final String[] options = {"Resume", "Settings", "Character", "Save Game", "Load Game", "Exit to Main Menu", "Exit Game"};
    private int selectedOption = 0;
    
    public PauseMenu(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void update(double delta) {
        // Update pause menu
    }
    
    public void render() {
        // Render pause menu background
        glColor4f(0.0f, 0.0f, 0.0f, 0.8f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render pause menu border
        glColor3f(0.6f, 0.6f, 0.6f);
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
        
        // Render pause menu title
        glColor3f(1.0f, 1.0f, 1.0f);
        // renderText(x + width/2 - 40, y + 30, "Paused");
        
        // Render menu options
        int optionHeight = 40;
        for (int i = 0; i < options.length; i++) {
            int optionY = y + 80 + i * optionHeight;
            
            // Highlight selected option
            if (i == selectedOption) {
                glColor3f(0.4f, 0.6f, 1.0f);
            } else {
                glColor3f(0.8f, 0.8f, 0.8f);
            }
            
            // renderText(x + width/2 - 50, optionY, options[i]);
        }
    }
    
    public void selectOption(int option) {
        if (option >= 0 && option < options.length) {
            selectedOption = option;
        }
    }
    
    public int getSelectedOption() {
        return selectedOption;
    }
}

/**
 * Manages tutorial tips shown in the top-left corner.
 */
class TutorialTips {
    private int x, y;
    private final java.util.List<String> tips = new java.util.ArrayList<>();
    private int currentTip = 0;
    private double tipTimer = 0;
    private final double tipDuration = 5.0; // Show each tip for 5 seconds
    
    public TutorialTips(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void addTip(String tip) {
        tips.add(tip);
    }
    
    public void update(double delta) {
        if (tips.isEmpty()) {
            return;
        }
        
        tipTimer += delta;
        if (tipTimer >= tipDuration) {
            tipTimer = 0;
            currentTip = (currentTip + 1) % tips.size();
        }
    }
    
    public void render() {
        if (tips.isEmpty()) {
            return;
        }
        
        // Render tip background
        String tip = tips.get(currentTip);
        int tipWidth = tip.length() * 8; // Approximate width based on text length
        int tipHeight = 20;
        
        glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + tipWidth, y);
        glVertex2f(x + tipWidth, y + tipHeight);
        glVertex2f(x, y + tipHeight);
        glEnd();
        
        // Render tip text
        glColor3f(1.0f, 1.0f, 0.0f); // Yellow text for tips
        // renderText(x + 5, y + 5, tip);
    }
}
