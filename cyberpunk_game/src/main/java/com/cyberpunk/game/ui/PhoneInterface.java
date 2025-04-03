package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector2f;

import com.cyberpunk.game.character.Player;
import com.cyberpunk.game.gameplay.MissionSystem;
import com.cyberpunk.game.gameplay.RelationshipSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the phone interface in the game.
 */
public class PhoneInterface {
    
    // UI properties
    private boolean visible;
    private Vector2f position;
    private float width;
    private float height;
    
    // Current app
    private PhoneApp currentApp;
    
    // Apps
    private List<PhoneApp> apps;
    
    // Player reference
    private Player player;
    
    // Mission system reference
    private MissionSystem missionSystem;
    
    // Relationship system reference
    private RelationshipSystem relationshipSystem;
    
    /**
     * Constructor.
     * 
     * @param player Player reference
     * @param missionSystem Mission system reference
     * @param relationshipSystem Relationship system reference
     */
    public PhoneInterface(Player player, MissionSystem missionSystem, RelationshipSystem relationshipSystem) {
        this.player = player;
        this.missionSystem = missionSystem;
        this.relationshipSystem = relationshipSystem;
        
        visible = false;
        position = new Vector2f(0, 0); // Will be positioned on screen
        width = 400;
        height = 600;
        
        apps = new ArrayList<>();
        
        // Initialize apps
        initializeApps();
        
        // Set default app
        currentApp = apps.get(0); // Home screen
    }
    
    /**
     * Initialize phone apps.
     */
    private void initializeApps() {
        // Home screen
        apps.add(new HomeApp(this));
        
        // Contacts app
        apps.add(new ContactsApp(this, relationshipSystem));
        
        // Messages app
        apps.add(new MessagesApp(this));
        
        // Missions app
        apps.add(new MissionsApp(this, missionSystem));
        
        // Map app
        apps.add(new MapApp(this));
        
        // Inventory app
        apps.add(new InventoryApp(this, player));
        
        // Settings app
        apps.add(new SettingsApp(this));
    }
    
    /**
     * Update the phone interface.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Nothing to update if not visible
        if (!visible) {
            return;
        }
        
        // Update current app
        if (currentApp != null) {
            currentApp.update(delta);
        }
    }
    
    /**
     * Render the phone interface.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        // Nothing to render if not visible
        if (!visible) {
            return;
        }
        
        // Position phone on screen
        position.x = (screenWidth - width) / 2;
        position.y = (screenHeight - height) / 2;
        
        // Draw phone background
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.9f); // Dark gray, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw phone border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw status bar
        renderStatusBar();
        
        // Draw current app
        if (currentApp != null) {
            currentApp.render(position.x, position.y + 30, width, height - 60);
        }
        
        // Draw navigation bar
        renderNavigationBar();
    }
    
    /**
     * Render the status bar.
     */
    private void renderStatusBar() {
        // Draw status bar background
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.8f); // Black, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + 30);
        GL11.glVertex2f(position.x, position.y + 30);
        GL11.glEnd();
        
        // Draw time
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw battery icon
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); // Green
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x + width - 40, position.y + 10);
        GL11.glVertex2f(position.x + width - 10, position.y + 10);
        GL11.glVertex2f(position.x + width - 10, position.y + 20);
        GL11.glVertex2f(position.x + width - 40, position.y + 20);
        GL11.glEnd();
        
        // Draw signal icon
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); // Green
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(position.x + width - 60, position.y + 20);
        GL11.glVertex2f(position.x + width - 50, position.y + 10);
        GL11.glVertex2f(position.x + width - 40, position.y + 20);
        GL11.glEnd();
    }
    
    /**
     * Render the navigation bar.
     */
    private void renderNavigationBar() {
        // Draw navigation bar background
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.8f); // Black, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y + height - 30);
        GL11.glVertex2f(position.x + width, position.y + height - 30);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw back button
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // White
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(position.x + 20, position.y + height - 15);
        GL11.glVertex2f(position.x + 30, position.y + height - 10);
        GL11.glVertex2f(position.x + 30, position.y + height - 20);
        GL11.glEnd();
        
        // Draw home button
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // White
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i <= 10; i++) {
            float angle = (float) (i * 2 * Math.PI / 10);
            GL11.glVertex2f(
                position.x + width / 2 + (float) Math.cos(angle) * 10,
                position.y + height - 15 + (float) Math.sin(angle) * 10
            );
        }
        GL11.glEnd();
        
        // Draw recent apps button
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // White
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 20);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 20);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 17);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 17);
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 15);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 15);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 12);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 12);
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 10);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 10);
        GL11.glVertex2f(position.x + width - 10, position.y + height - 7);
        GL11.glVertex2f(position.x + width - 30, position.y + height - 7);
        GL11.glEnd();
    }
    
    /**
     * Toggle phone visibility.
     */
    public void toggleVisibility() {
        visible = !visible;
    }
    
    /**
     * Show the phone.
     */
    public void show() {
        visible = true;
    }
    
    /**
     * Hide the phone.
     */
    public void hide() {
        visible = false;
    }
    
    /**
     * Check if phone is visible.
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Open an app.
     * 
     * @param appName App name
     * @return true if app was opened
     */
    public boolean openApp(String appName) {
        for (PhoneApp app : apps) {
            if (app.getName().equalsIgnoreCase(appName)) {
                currentApp = app;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Go back to previous app or home screen.
     */
    public void goBack() {
        // If current app is not home, go to home
        if (currentApp != null && !currentApp.getName().equals("Home")) {
            currentApp = apps.get(0); // Home screen
        } else {
            // If already at home, close phone
            hide();
        }
    }
    
    /**
     * Go to home screen.
     */
    public void goHome() {
        currentApp = apps.get(0); // Home screen
    }
    
    /**
     * Get the player reference.
     * 
     * @return The player reference
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Get the mission system reference.
     * 
     * @return The mission system reference
     */
    public MissionSystem getMissionSystem() {
        return missionSystem;
    }
    
    /**
     * Get the relationship system reference.
     * 
     * @return The relationship system reference
     */
    public RelationshipSystem getRelationshipSystem() {
        return relationshipSystem;
    }
}

/**
 * Base class for phone apps.
 */
abstract class PhoneApp {
    protected String name;
    protected PhoneInterface phoneInterface;
    
    /**
     * Constructor.
     * 
     * @param name App name
     * @param phoneInterface Phone interface reference
     */
    public PhoneApp(String name, PhoneInterface phoneInterface) {
        this.name = name;
        this.phoneInterface = phoneInterface;
    }
    
    /**
     * Update the app.
     * 
     * @param delta Time since last update in seconds
     */
    public abstract void update(float delta);
    
    /**
     * Render the app.
     * 
     * @param x X position
     * @param y Y position
     * @param width Width
     * @param height Height
     */
    public abstract void render(float x, float y, float width, float height);
    
    /**
     * Get the app name.
     * 
     * @return The app name
     */
    public String getName() {
        return name;
    }
}

/**
 * Home screen app.
 */
class HomeApp extends PhoneApp {
    
    /**
     * Constructor.
     * 
     * @param phoneInterface Phone interface reference
     */
    public HomeApp(PhoneInterface phoneInterface) {
        super("Home", phoneInterface);
    }
    
    @Override
    public void update(float delta) {
        // Nothing to update
    }
    
    @Override
    public void render(float x, float y, float width, float height) {
        // Draw app grid
        int rows = 4;
        int cols = 3;
        float iconSize = 64;
        float padding = (width - cols * iconSize) / (cols + 1);
        float verticalPadding = (height - rows * iconSize) / (rows + 1);
        
        // App names
        String[] appNames = {
            "Contacts", "Messages", "Missions",
            "Map", "Inventory", "Settings",
            "Camera", "Music", "News",
            "Browser", "Calculator", "Notes"
        };
        
        // Draw app icons
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                if (index < appNames.length) {
                    float iconX = x + padding + col * (iconSize + padding);
                    float iconY = y + verticalPadding + row * (iconSize + verticalPadding);
                    
                    // Draw icon background
                    GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.8f); // Dark gray
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2f(iconX, iconY);
                    GL11.glVertex2f(iconX + iconSize, iconY);
                    GL11.glVertex2f(iconX + iconSize, iconY + iconSize);
                    GL11.glVertex2f(iconX, iconY + iconSize);
                    GL11.glEnd();
                    
                    // Draw icon border
                    GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
                    GL11.glBegin(GL11.GL_LINE_LOOP);
                    GL11.glVertex2f(iconX, iconY);
                    GL11.glVertex2f(iconX + iconSize, iconY);
                    GL11.glVertex2f(iconX + iconSize, iconY + iconSize);
                    GL11.glVertex2f(iconX, iconY + iconSize);
                    GL11.glEnd();
                    
                    // Draw app name
                    // This would use a text rendering system
                    // For now, just a placeholder
                }
            }
        }
    }
}

/**
 * Contacts app.
 */
class ContactsApp extends PhoneApp {
    
    private RelationshipSystem relationshipSystem;
    
    /**
     * Constructor.
     * 
     * @param phoneInterface Phone interface reference
     * @param relationshipSystem Relationship system reference
     */
    public ContactsApp(PhoneInterface phoneInterface, RelationshipSystem relationshipSystem) {
        super("Contacts", phoneInterface);
        this.relationshipSystem = relationshipSystem;
    }
    
    @Override
    public void update(float delta) {
        // Nothing to update
    }
    
    @Override
    public void render(float x, float y, float width, float height) {
        // Draw app header
        GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.8f); // Dark gray
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + 40);
        GL11.glVertex2f(x, y + 40);
        GL11.glEnd();
        
        // Draw app title
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw contact list
        // This would use actual contacts from the relationship system
        // For now, just draw some placeholder contacts
        
        String[] contacts = {
            "Don Vincenzo", "Detective Reeves", "Dr. Nakamura",
            "Chrome Dragons Leader", "Neon Vipers Leader", "Broker",
            "Tower Owner", "Security Chief", "VIP"
        };
        
        float contactHeight = 50;
        float contactPadding = 5;
        
        for (int i = 0; i < contacts.length; i++) {
            float contactY = y + 50 + i * (contactHeight + contactPadding);
            
            // Skip if outside visible area
            if (contactY > y + height) {
                break;
            }
            
            // Draw contact background
            GL11.glColor4f(0.15f, 0.15f, 0.15f, 0.8f); // Dark gray
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(x + 10, contactY);
            GL11.glVertex2f(x + width - 10, contactY);
            GL11.glVertex2f(x + width - 10, contactY + contactHeight);
            GL11.glVertex2f(x + 10, contactY + contactHeight);
            GL11.glEnd();
            
            // Draw contact name
            // This would use a text rendering system
            // For now, just a placeholder
            
            // Draw contact avatar
            GL11.glColor4f(0.3f, 0.3f, 0.3f, 1.0f); // Gray
            GL
(Content truncated due to size limit. Use line ranges to read in chunks)