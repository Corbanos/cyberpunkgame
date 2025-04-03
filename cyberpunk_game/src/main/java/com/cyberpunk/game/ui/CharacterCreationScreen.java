package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector2f;
import org.joml.Vector3f;

import com.cyberpunk.game.character.Player;

import java.util.Random;

/**
 * Manages the character creation screen in the game.
 */
public class CharacterCreationScreen {
    
    // UI properties
    private boolean visible;
    private Vector2f position;
    private float width;
    private float height;
    
    // Character properties
    private String characterName;
    private int gender; // 0 = male, 1 = female
    private int bodyType; // 0-3
    private int faceType; // 0-5
    private int hairStyle; // 0-7
    private int hairColor; // 0-9
    private int eyeColor; // 0-5
    private int skinTone; // 0-7
    private int[] cybernetics; // Array of cybernetic enhancements
    private int[] tattoos; // Array of tattoo selections
    private int[] scars; // Array of scar selections
    
    // Current tab
    private int currentTab; // 0 = basic, 1 = appearance, 2 = cybernetics, 3 = background
    
    // Current selection
    private int currentSelection;
    
    // Random generator
    private Random random;
    
    // Player reference
    private Player player;
    
    // Character preview rotation
    private float previewRotation;
    
    /**
     * Constructor.
     * 
     * @param player Player reference
     */
    public CharacterCreationScreen(Player player) {
        this.player = player;
        
        visible = false;
        position = new Vector2f(0, 0); // Will be positioned on screen
        width = 1200;
        height = 800;
        
        random = new Random();
        
        // Initialize with default values
        characterName = "V";
        gender = 0;
        bodyType = 0;
        faceType = 0;
        hairStyle = 0;
        hairColor = 0;
        eyeColor = 0;
        skinTone = 0;
        cybernetics = new int[5];
        tattoos = new int[3];
        scars = new int[2];
        
        currentTab = 0;
        currentSelection = 0;
        
        previewRotation = 0.0f;
    }
    
    /**
     * Update the character creation screen.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Nothing to update if not visible
        if (!visible) {
            return;
        }
        
        // Rotate character preview
        previewRotation += delta * 0.2f;
        if (previewRotation > Math.PI * 2) {
            previewRotation -= Math.PI * 2;
        }
    }
    
    /**
     * Render the character creation screen.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        // Nothing to render if not visible
        if (!visible) {
            return;
        }
        
        // Center character creation screen on screen
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
        
        // Draw character creation background
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.9f); // Dark gray, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw character creation border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw character creation title
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw tabs
        renderTabs();
        
        // Draw character preview
        renderCharacterPreview();
        
        // Draw current tab content
        switch (currentTab) {
            case 0:
                renderBasicTab();
                break;
            case 1:
                renderAppearanceTab();
                break;
            case 2:
                renderCyberneticsTab();
                break;
            case 3:
                renderBackgroundTab();
                break;
        }
        
        // Draw navigation buttons
        renderNavigationButtons();
    }
    
    /**
     * Render the tabs.
     */
    private void renderTabs() {
        String[] tabNames = {
            "Basic", "Appearance", "Cybernetics", "Background"
        };
        
        float tabWidth = width / tabNames.length;
        float tabHeight = 40;
        
        for (int i = 0; i < tabNames.length; i++) {
            float tabX = position.x + i * tabWidth;
            float tabY = position.y;
            
            // Draw tab background
            if (i == currentTab) {
                GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected tab
            } else {
                GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
            }
            
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(tabX, tabY);
            GL11.glVertex2f(tabX + tabWidth, tabY);
            GL11.glVertex2f(tabX + tabWidth, tabY + tabHeight);
            GL11.glVertex2f(tabX, tabY + tabHeight);
            GL11.glEnd();
            
            // Draw tab border
            GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex2f(tabX, tabY);
            GL11.glVertex2f(tabX + tabWidth, tabY);
            GL11.glVertex2f(tabX + tabWidth, tabY + tabHeight);
            GL11.glVertex2f(tabX, tabY + tabHeight);
            GL11.glEnd();
            
            // Draw tab name
            // This would use a text rendering system
            // For now, just a placeholder
        }
    }
    
    /**
     * Render the character preview.
     */
    private void renderCharacterPreview() {
        // Calculate preview area
        float previewWidth = width * 0.4f;
        float previewHeight = height - 100;
        float previewX = position.x + width - previewWidth - 20;
        float previewY = position.y + 60;
        
        // Draw preview background
        GL11.glColor4f(0.15f, 0.15f, 0.15f, 0.8f); // Dark gray
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(previewX, previewY);
        GL11.glVertex2f(previewX + previewWidth, previewY);
        GL11.glVertex2f(previewX + previewWidth, previewY + previewHeight);
        GL11.glVertex2f(previewX, previewY + previewHeight);
        GL11.glEnd();
        
        // Draw preview border
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(previewX, previewY);
        GL11.glVertex2f(previewX + previewWidth, previewY);
        GL11.glVertex2f(previewX + previewWidth, previewY + previewHeight);
        GL11.glVertex2f(previewX, previewY + previewHeight);
        GL11.glEnd();
        
        // Draw character preview
        // This would render a 3D model of the character
        // For now, just draw a placeholder
        
        float centerX = previewX + previewWidth / 2;
        float centerY = previewY + previewHeight / 2;
        float radius = Math.min(previewWidth, previewHeight) / 4;
        
        // Draw character body
        GL11.glColor4f(0.8f, 0.8f, 0.8f, 1.0f); // Light gray
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(centerX - radius / 2, centerY);
        GL11.glVertex2f(centerX + radius / 2, centerY);
        GL11.glVertex2f(centerX + radius / 2, centerY + radius * 2);
        GL11.glVertex2f(centerX - radius / 2, centerY + radius * 2);
        GL11.glEnd();
        
        // Draw character head
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(centerX, centerY - radius);
        for (int i = 0; i <= 10; i++) {
            float angle = (float) (i * Math.PI / 10);
            GL11.glVertex2f(
                centerX + (float) Math.cos(angle) * radius,
                centerY - radius + (float) Math.sin(angle) * radius
            );
        }
        GL11.glEnd();
        
        // Draw character arms
        GL11.glBegin(GL11.GL_QUADS);
        // Left arm
        float armAngle = (float) (Math.sin(previewRotation) * 0.3f);
        float armX = (float) (Math.cos(armAngle) * radius);
        float armY = (float) (Math.sin(armAngle) * radius);
        GL11.glVertex2f(centerX - radius / 2, centerY + radius / 2);
        GL11.glVertex2f(centerX - radius / 2 - radius / 4, centerY + radius / 2);
        GL11.glVertex2f(centerX - radius / 2 - radius / 4 - armX, centerY + radius / 2 + radius - armY);
        GL11.glVertex2f(centerX - radius / 2 - armX, centerY + radius / 2 + radius - armY);
        
        // Right arm
        armAngle = (float) (Math.sin(previewRotation + Math.PI) * 0.3f);
        armX = (float) (Math.cos(armAngle) * radius);
        armY = (float) (Math.sin(armAngle) * radius);
        GL11.glVertex2f(centerX + radius / 2, centerY + radius / 2);
        GL11.glVertex2f(centerX + radius / 2 + radius / 4, centerY + radius / 2);
        GL11.glVertex2f(centerX + radius / 2 + radius / 4 + armX, centerY + radius / 2 + radius - armY);
        GL11.glVertex2f(centerX + radius / 2 + armX, centerY + radius / 2 + radius - armY);
        GL11.glEnd();
        
        // Draw character legs
        GL11.glBegin(GL11.GL_QUADS);
        // Left leg
        GL11.glVertex2f(centerX - radius / 2, centerY + radius * 2);
        GL11.glVertex2f(centerX - radius / 4, centerY + radius * 2);
        GL11.glVertex2f(centerX - radius / 4, centerY + radius * 3);
        GL11.glVertex2f(centerX - radius / 2, centerY + radius * 3);
        
        // Right leg
        GL11.glVertex2f(centerX + radius / 4, centerY + radius * 2);
        GL11.glVertex2f(centerX + radius / 2, centerY + radius * 2);
        GL11.glVertex2f(centerX + radius / 2, centerY + radius * 3);
        GL11.glVertex2f(centerX + radius / 4, centerY + radius * 3);
        GL11.glEnd();
        
        // Draw cybernetic enhancements
        if (cybernetics[0] > 0) { // Arm enhancement
            GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(centerX - radius / 2, centerY + radius / 2);
            GL11.glVertex2f(centerX - radius / 2 - radius / 4, centerY + radius / 2);
            GL11.glVertex2f(centerX - radius / 2 - radius / 4, centerY + radius / 2 + radius);
            GL11.glVertex2f(centerX - radius / 2, centerY + radius / 2 + radius);
            GL11.glEnd();
        }
        
        if (cybernetics[1] > 0) { // Eye enhancement
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // Red
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(centerX - radius / 4, centerY - radius / 2);
            GL11.glVertex2f(centerX, centerY - radius / 2);
            GL11.glVertex2f(centerX, centerY - radius / 4);
            GL11.glVertex2f(centerX - radius / 4, centerY - radius / 4);
            GL11.glEnd();
        }
    }
    
    /**
     * Render the basic tab.
     */
    private void renderBasicTab() {
        // Calculate content area
        float contentWidth = width * 0.5f;
        float contentHeight = height - 100;
        float contentX = position.x + 20;
        float contentY = position.y + 60;
        
        // Draw name input
        float inputY = contentY + 50;
        
        GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(contentX, inputY);
        GL11.glVertex2f(contentX + contentWidth - 40, inputY);
        GL11.glVertex2f(contentX + contentWidth - 40, inputY + 40);
        GL11.glVertex2f(contentX, inputY + 40);
        GL11.glEnd();
        
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(contentX, inputY);
        GL11.glVertex2f(contentX + contentWidth - 40, inputY);
        GL11.glVertex2f(contentX + contentWidth - 40, inputY + 40);
        GL11.glVertex2f(contentX, inputY + 40);
        GL11.glEnd();
        
        // Draw name label
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw gender selection
        float genderY = inputY + 80;
        
        // Male option
        if (gender == 0) {
            GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected option
        } else {
            GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
        }
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(contentX, genderY);
        GL11.glVertex2f(contentX + contentWidth / 2 - 20, genderY);
        GL11.glVertex2f(contentX + contentWidth / 2 - 20, genderY + 40);
        GL11.glVertex2f(contentX, genderY + 40);
        GL11.glEnd();
        
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(contentX, genderY);
        GL11.glVertex2f(contentX + contentWidth / 2 - 20, genderY);
        GL11.glVertex2f(contentX + contentWidth / 2 - 20, genderY + 40);
        GL11.glVertex2f(contentX, genderY + 40);
        GL11.glEnd();
        
        // Female option
        if (gender == 1) {
            GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected option
        } else {
            GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
        }
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(contentX + contentWidth / 2 + 20, genderY);
        GL11.glVertex2f(contentX + contentWidth - 40, genderY);
        GL11.glVertex2f(contentX + contentWidth - 40, genderY + 40);
        GL11.glVertex2f(contentX + contentWidth / 2 + 20, genderY + 40);
        GL11.glEnd();
        
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(contentX + contentWidth / 2 + 20, genderY);
        GL11.glVertex2f(contentX + contentWidth - 40, genderY);
        GL11.glVertex2f(contentX + contentWidth - 40, genderY + 40);
        GL11.glVertex2f(contentX + contentWidth / 2 + 20, genderY + 40);
        GL11.glEnd();
        
        // Draw gender labels
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw body type selection
        float bodyTypeY = genderY + 80;
        
        String[] bodyTypes = {
            "Athletic", "Slim", "Heavy", "Muscular"
        };
        
        for (int i = 0; i < bodyTypes.length; i++) {
            float optionX = contentX + (i % 2) * (contentWidth / 2);
            float optionY = bodyTypeY + (i / 2) * 50;
            
            if (bodyType == i) {
                GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected option
            } else {
                GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
            }
            
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(optionX, optionY);
            GL11.glVertex2f(optionX + contentWidth / 2 - 10, optionY);
            GL11.glVertex2f(optionX + contentWidth / 2 - 10, optionY + 40);
            GL11.glVertex2f(optionX, optionY + 40);
            GL11.glEnd();
            
         
(Content truncated due to size limit. Use line ranges to read in chunks)