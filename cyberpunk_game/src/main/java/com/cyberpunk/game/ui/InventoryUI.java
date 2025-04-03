package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector2f;

import com.cyberpunk.game.character.Player;
import com.cyberpunk.game.character.Item;
import com.cyberpunk.game.character.Weapon;
import com.cyberpunk.game.character.WeaponType;

import java.util.List;

/**
 * Manages the inventory UI in the game.
 */
public class InventoryUI {
    
    // UI properties
    private boolean visible;
    private Vector2f position;
    private float width;
    private float height;
    private int rows;
    private int columns;
    private float slotSize;
    private float slotPadding;
    
    // Selected slot
    private int selectedSlot;
    
    // Player reference
    private Player player;
    
    // Textures
    private int inventoryBackgroundTexture;
    private int slotTexture;
    private int selectedSlotTexture;
    
    /**
     * Constructor.
     * 
     * @param player Player reference
     */
    public InventoryUI(Player player) {
        this.player = player;
        
        visible = false;
        position = new Vector2f(0, 0); // Will be centered on screen
        rows = 4;
        columns = 9;
        slotSize = 64;
        slotPadding = 4;
        
        width = columns * (slotSize + slotPadding) + slotPadding;
        height = rows * (slotSize + slotPadding) + slotPadding;
        
        selectedSlot = 0;
        
        // Load textures
        loadTextures();
    }
    
    /**
     * Load textures.
     */
    private void loadTextures() {
        // This would load actual textures
        // For now, just use placeholder IDs
        inventoryBackgroundTexture = 1;
        slotTexture = 2;
        selectedSlotTexture = 3;
    }
    
    /**
     * Update the inventory UI.
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
     * Render the inventory UI.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        // Nothing to render if not visible
        if (!visible) {
            return;
        }
        
        // Center inventory on screen
        position.x = (screenWidth - width) / 2;
        position.y = (screenHeight - height) / 2;
        
        // Draw inventory background
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.9f); // Dark gray, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw inventory border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(position.x, position.y);
        GL11.glVertex2f(position.x + width, position.y);
        GL11.glVertex2f(position.x + width, position.y + height);
        GL11.glVertex2f(position.x, position.y + height);
        GL11.glEnd();
        
        // Draw inventory title
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Draw inventory slots
        renderSlots();
        
        // Draw item details for selected slot
        renderItemDetails(screenWidth, screenHeight);
    }
    
    /**
     * Render inventory slots.
     */
    private void renderSlots() {
        // Get player inventory
        Item[] items = player.getInventory().getItems();
        
        // Draw slots
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int slotIndex = row * columns + col;
                
                // Calculate slot position
                float slotX = position.x + slotPadding + col * (slotSize + slotPadding);
                float slotY = position.y + slotPadding + row * (slotSize + slotPadding);
                
                // Draw slot background
                if (slotIndex == selectedSlot) {
                    GL11.glColor4f(0.3f, 0.6f, 0.9f, 0.7f); // Highlighted blue for selected slot
                } else {
                    GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.7f); // Dark gray
                }
                
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(slotX, slotY);
                GL11.glVertex2f(slotX + slotSize, slotY);
                GL11.glVertex2f(slotX + slotSize, slotY + slotSize);
                GL11.glVertex2f(slotX, slotY + slotSize);
                GL11.glEnd();
                
                // Draw slot border
                GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray
                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex2f(slotX, slotY);
                GL11.glVertex2f(slotX + slotSize, slotY);
                GL11.glVertex2f(slotX + slotSize, slotY + slotSize);
                GL11.glVertex2f(slotX, slotY + slotSize);
                GL11.glEnd();
                
                // Draw item if slot has an item
                if (slotIndex < items.length && items[slotIndex] != null) {
                    renderItem(items[slotIndex], slotX, slotY);
                }
            }
        }
    }
    
    /**
     * Render an item in a slot.
     * 
     * @param item The item to render
     * @param x Slot X position
     * @param y Slot Y position
     */
    private void renderItem(Item item, float x, float y) {
        // Draw item icon
        // This would use item textures
        // For now, just draw a colored rectangle based on item type
        
        float padding = 8;
        
        switch (item.getType()) {
            case WEAPON:
                GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // Red for weapons
                break;
            case AMMO:
                GL11.glColor4f(1.0f, 0.5f, 0.0f, 1.0f); // Orange for ammo
                break;
            case ARMOR:
                GL11.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); // Blue for armor
                break;
            case CONSUMABLE:
                GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); // Green for consumables
                break;
            case KEY_ITEM:
                GL11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f); // Yellow for key items
                break;
            case CRAFTING_MATERIAL:
                GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Gray for crafting materials
                break;
            case DRUG:
                GL11.glColor4f(1.0f, 0.0f, 1.0f, 1.0f); // Magenta for drugs
                break;
            default:
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // White for unknown
        }
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x + padding, y + padding);
        GL11.glVertex2f(x + slotSize - padding, y + padding);
        GL11.glVertex2f(x + slotSize - padding, y + slotSize - padding);
        GL11.glVertex2f(x + padding, y + slotSize - padding);
        GL11.glEnd();
        
        // Draw item quantity if stackable
        if (item.isStackable() && item.getQuantity() > 1) {
            // This would use a text rendering system
            // For now, just a placeholder
        }
    }
    
    /**
     * Render item details for the selected slot.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    private void renderItemDetails(int screenWidth, int screenHeight) {
        // Get selected item
        Item[] items = player.getInventory().getItems();
        if (selectedSlot >= items.length || items[selectedSlot] == null) {
            return; // No item selected
        }
        
        Item selectedItem = items[selectedSlot];
        
        // Calculate details panel position
        float detailsWidth = 300;
        float detailsHeight = 200;
        float detailsX = position.x + width + 20;
        float detailsY = position.y;
        
        // Ensure details panel stays on screen
        if (detailsX + detailsWidth > screenWidth) {
            detailsX = position.x - detailsWidth - 20;
        }
        
        // Draw details background
        GL11.glColor4f(0.1f, 0.1f, 0.1f, 0.9f); // Dark gray, semi-transparent
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(detailsX, detailsY);
        GL11.glVertex2f(detailsX + detailsWidth, detailsY);
        GL11.glVertex2f(detailsX + detailsWidth, detailsY + detailsHeight);
        GL11.glVertex2f(detailsX, detailsY + detailsHeight);
        GL11.glEnd();
        
        // Draw details border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(detailsX, detailsY);
        GL11.glVertex2f(detailsX + detailsWidth, detailsY);
        GL11.glVertex2f(detailsX + detailsWidth, detailsY + detailsHeight);
        GL11.glVertex2f(detailsX, detailsY + detailsHeight);
        GL11.glEnd();
        
        // Draw item details
        // This would use a text rendering system
        // For now, just a placeholder
    }
    
    /**
     * Toggle inventory visibility.
     */
    public void toggleVisibility() {
        visible = !visible;
    }
    
    /**
     * Show the inventory.
     */
    public void show() {
        visible = true;
    }
    
    /**
     * Hide the inventory.
     */
    public void hide() {
        visible = false;
    }
    
    /**
     * Check if inventory is visible.
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        return visible;
    }
    
    /**
     * Select the next slot.
     */
    public void selectNextSlot() {
        selectedSlot = (selectedSlot + 1) % (rows * columns);
    }
    
    /**
     * Select the previous slot.
     */
    public void selectPreviousSlot() {
        selectedSlot = (selectedSlot - 1 + rows * columns) % (rows * columns);
    }
    
    /**
     * Select the slot above.
     */
    public void selectSlotAbove() {
        int row = selectedSlot / columns;
        int col = selectedSlot % columns;
        
        row = (row - 1 + rows) % rows;
        selectedSlot = row * columns + col;
    }
    
    /**
     * Select the slot below.
     */
    public void selectSlotBelow() {
        int row = selectedSlot / columns;
        int col = selectedSlot % columns;
        
        row = (row + 1) % rows;
        selectedSlot = row * columns + col;
    }
    
    /**
     * Use the selected item.
     * 
     * @return true if item was used
     */
    public boolean useSelectedItem() {
        Item[] items = player.getInventory().getItems();
        if (selectedSlot >= items.length || items[selectedSlot] == null) {
            return false; // No item selected
        }
        
        Item selectedItem = items[selectedSlot];
        
        // Handle different item types
        switch (selectedItem.getType()) {
            case WEAPON:
                // Equip weapon
                if (selectedItem instanceof Weapon) {
                    player.equipWeapon((Weapon) selectedItem);
                    return true;
                }
                break;
                
            case CONSUMABLE:
                // Use consumable
                // This would implement consumable effects
                // For now, just remove the item
                selectedItem.decreaseQuantity(1);
                if (selectedItem.getQuantity() <= 0) {
                    player.getInventory().removeItem(selectedSlot);
                }
                return true;
                
            case DRUG:
                // Use drug
                // This would implement drug effects
                // For now, just remove the item
                selectedItem.decreaseQuantity(1);
                if (selectedItem.getQuantity() <= 0) {
                    player.getInventory().removeItem(selectedSlot);
                }
                return true;
                
            default:
                // Other items can't be used directly
                return false;
        }
        
        return false;
    }
    
    /**
     * Drop the selected item.
     * 
     * @return true if item was dropped
     */
    public boolean dropSelectedItem() {
        Item[] items = player.getInventory().getItems();
        if (selectedSlot >= items.length || items[selectedSlot] == null) {
            return false; // No item selected
        }
        
        // Remove item from inventory
        player.getInventory().removeItem(selectedSlot);
        
        // This would spawn the item in the world
        // For now, just a placeholder
        
        return true;
    }
    
    /**
     * Get the selected slot.
     * 
     * @return The selected slot index
     */
    public int getSelectedSlot() {
        return selectedSlot;
    }
    
    /**
     * Set the selected slot.
     * 
     * @param slot The slot index to select
     */
    public void setSelectedSlot(int slot) {
        if (slot >= 0 && slot < rows * columns) {
            selectedSlot = slot;
        }
    }
}
