package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Manages the tutorial tip system in the game.
 */
public class TutorialTipSystem {
    
    // Tip properties
    private Queue<TutorialTip> tipQueue;
    private TutorialTip currentTip;
    private float displayTime;
    private float maxDisplayTime;
    private boolean tipVisible;
    
    // UI properties
    private Vector2f position;
    private float width;
    private float height;
    
    // All tips
    private List<TutorialTip> allTips;
    
    /**
     * Constructor.
     */
    public TutorialTipSystem() {
        tipQueue = new LinkedList<>();
        currentTip = null;
        displayTime = 0.0f;
        maxDisplayTime = 5.0f; // Display each tip for 5 seconds
        tipVisible = false;
        
        position = new Vector2f(20, 20); // Top-left corner
        width = 400;
        height = 80;
        
        allTips = new ArrayList<>();
        
        // Initialize tips
        initializeTips();
    }
    
    /**
     * Initialize tutorial tips.
     */
    private void initializeTips() {
        // Movement tips
        allTips.add(new TutorialTip(
            "Movement",
            "Use WASD keys to move around the city.",
            "movement"
        ));
        
        allTips.add(new TutorialTip(
            "Sprint",
            "Hold Shift while moving to sprint.",
            "sprint"
        ));
        
        allTips.add(new TutorialTip(
            "Jump",
            "Press Space to jump over obstacles.",
            "jump"
        ));
        
        // Interaction tips
        allTips.add(new TutorialTip(
            "Interaction",
            "Press E to interact with objects and NPCs.",
            "interact"
        ));
        
        allTips.add(new TutorialTip(
            "Enter Buildings",
            "Press E near doors to enter buildings.",
            "enter_building"
        ));
        
        // Combat tips
        allTips.add(new TutorialTip(
            "Combat",
            "Left-click to attack with equipped weapon.",
            "attack"
        ));
        
        allTips.add(new TutorialTip(
            "Aim",
            "Right-click to aim your weapon for better accuracy.",
            "aim"
        ));
        
        allTips.add(new TutorialTip(
            "Reload",
            "Press R to reload your weapon.",
            "reload"
        ));
        
        // Vehicle tips
        allTips.add(new TutorialTip(
            "Hijack Vehicle",
            "Press F near a vehicle to hijack it.",
            "hijack"
        ));
        
        allTips.add(new TutorialTip(
            "Drive",
            "Use WASD to drive, Space to brake, and Shift to boost.",
            "drive"
        ));
        
        // UI tips
        allTips.add(new TutorialTip(
            "Inventory",
            "Press I to open your inventory.",
            "inventory"
        ));
        
        allTips.add(new TutorialTip(
            "Map",
            "Press M to expand the mini-map.",
            "map"
        ));
        
        allTips.add(new TutorialTip(
            "Phone",
            "Press `/~ to open your phone interface.",
            "phone"
        ));
        
        allTips.add(new TutorialTip(
            "Pause",
            "Press Escape to pause the game.",
            "pause"
        ));
        
        // Mission tips
        allTips.add(new TutorialTip(
            "Missions",
            "Check your phone for available missions.",
            "missions"
        ));
        
        allTips.add(new TutorialTip(
            "Objectives",
            "Current mission objectives are shown on your HUD.",
            "objectives"
        ));
        
        // Gameplay tips
        allTips.add(new TutorialTip(
            "Reputation",
            "Your actions affect your reputation with different factions.",
            "reputation"
        ));
        
        allTips.add(new TutorialTip(
            "Drugs",
            "You can buy and sell drugs for profit, but be careful of the police.",
            "drugs"
        ));
        
        allTips.add(new TutorialTip(
            "Gangs",
            "Different gangs control different areas of the city.",
            "gangs"
        ));
    }
    
    /**
     * Update the tutorial tip system.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Update current tip display time
        if (currentTip != null) {
            displayTime += delta;
            
            // Check if tip should be hidden
            if (displayTime >= maxDisplayTime) {
                currentTip = null;
                tipVisible = false;
                
                // Check for next tip
                if (!tipQueue.isEmpty()) {
                    currentTip = tipQueue.poll();
                    displayTime = 0.0f;
                    tipVisible = true;
                }
            }
        } else if (!tipQueue.isEmpty()) {
            // Show next tip
            currentTip = tipQueue.poll();
            displayTime = 0.0f;
            tipVisible = true;
        }
    }
    
    /**
     * Render the tutorial tip.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        // Nothing to render if no tip is visible
        if (!tipVisible || currentTip == null) {
            return;
        }
        
        // Draw tip background
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f); // Black, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw tip border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw tip title
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw tip content
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw progress bar
        float progressWidth = (width - 20) * (displayTime / maxDisplayTime);
        
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 0.5f); // Cyan, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x + 10, position.y + height - 15);
        GL11.glVertex2f(position.x + 10 + progressWidth, position.y + height - 15);
        GL11.glVertex2f(position.x + 10 + progressWidth, position.y + height - 10);
        GL11.glVertex2f(position.x + 10, position.y + height - 10);
        GL11.glEnd();
    }
    
    /**
     * Show a specific tip.
     * 
     * @param tipId Tip identifier
     */
    public void showTip(String tipId) {
        // Find tip by ID
        for (TutorialTip tip : allTips) {
            if (tip.getId().equals(tipId)) {
                // Add to queue
                tipQueue.add(tip);
                
                // If no tip is currently showing, show this one immediately
                if (currentTip == null) {
                    currentTip = tipQueue.poll();
                    displayTime = 0.0f;
                    tipVisible = true;
                }
                
                break;
            }
        }
    }
    
    /**
     * Show the initial tips.
     */
    public void showInitialTips() {
        // Add initial tips to queue
        showTip("movement");
        showTip("interact");
        showTip("inventory");
        showTip("map");
        showTip("phone");
    }
    
    /**
     * Clear all tips.
     */
    public void clearTips() {
        tipQueue.clear();
        currentTip = null;
        tipVisible = false;
    }
    
    /**
     * Skip the current tip.
     */
    public void skipCurrentTip() {
        if (currentTip != null) {
            currentTip = null;
            tipVisible = false;
            
            // Check for next tip
            if (!tipQueue.isEmpty()) {
                currentTip = tipQueue.poll();
                displayTime = 0.0f;
                tipVisible = true;
            }
        }
    }
    
    /**
     * Check if a tip is currently visible.
     * 
     * @return true if a tip is visible
     */
    public boolean isTipVisible() {
        return tipVisible;
    }
    
    /**
     * Get the current tip.
     * 
     * @return The current tip, or null if none
     */
    public TutorialTip getCurrentTip() {
        return currentTip;
    }
}

/**
 * Represents a tutorial tip.
 */
class TutorialTip {
    private String title;
    private String content;
    private String id;
    
    /**
     * Constructor.
     * 
     * @param title Tip title
     * @param content Tip content
     * @param id Tip identifier
     */
    public TutorialTip(String title, String content, String id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }
    
    /**
     * Get the tip title.
     * 
     * @return The tip title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get the tip content.
     * 
     * @return The tip content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Get the tip identifier.
     * 
     * @return The tip identifier
     */
    public String getId() {
        return id;
    }
}
