package com.cyberpunk.game.character;

import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the player character in the game.
 */
public class Player {
    
    // Player position and orientation
    private Vector3f position;
    private float rotationY; // Rotation around Y axis (in radians)
    
    // Player movement
    private float moveSpeed = 5.0f; // Units per second
    private float rotateSpeed = 2.0f; // Radians per second
    private float jumpHeight = 1.0f;
    private boolean isJumping = false;
    private float verticalVelocity = 0.0f;
    
    // Player stats
    private int health = 100;
    private int maxHealth = 100;
    private int stamina = 100;
    private int maxStamina = 100;
    
    // Player inventory
    private Inventory inventory;
    
    // Player equipment
    private Weapon equippedWeapon;
    private List<Weapon> weapons;
    
    // Player state
    private boolean inVehicle = false;
    private Vehicle currentVehicle;
    private boolean inBuilding = false;
    private Building currentBuilding;
    
    // Player appearance
    private PlayerAppearance appearance;
    
    // Player skills
    private PlayerSkills skills;
    
    /**
     * Constructor.
     */
    public Player() {
        position = new Vector3f(0, 1.8f, 0); // Start at origin, eye height 1.8
        rotationY = 0.0f;
        
        inventory = new Inventory(36); // 36 slots like Minecraft
        weapons = new ArrayList<>();
        appearance = new PlayerAppearance();
        skills = new PlayerSkills();
    }
    
    /**
     * Update player state.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Apply gravity if not on ground
        if (position.y > 1.8f || verticalVelocity != 0) {
            verticalVelocity -= 9.8f * delta; // Apply gravity
            position.y += verticalVelocity * delta;
            
            // Check if landed
            if (position.y < 1.8f) {
                position.y = 1.8f;
                verticalVelocity = 0;
                isJumping = false;
            }
        }
        
        // Regenerate stamina
        if (stamina < maxStamina) {
            stamina += 5 * delta; // Regenerate 5 stamina per second
            if (stamina > maxStamina) {
                stamina = maxStamina;
            }
        }
    }
    
    /**
     * Move the player forward.
     * 
     * @param delta Time since last update in seconds
     */
    public void moveForward(float delta) {
        float dx = (float) Math.sin(rotationY) * moveSpeed * delta;
        float dz = (float) Math.cos(rotationY) * moveSpeed * delta;
        
        position.x += dx;
        position.z += dz;
    }
    
    /**
     * Move the player backward.
     * 
     * @param delta Time since last update in seconds
     */
    public void moveBackward(float delta) {
        float dx = (float) Math.sin(rotationY) * moveSpeed * delta;
        float dz = (float) Math.cos(rotationY) * moveSpeed * delta;
        
        position.x -= dx;
        position.z -= dz;
    }
    
    /**
     * Strafe the player left.
     * 
     * @param delta Time since last update in seconds
     */
    public void strafeLeft(float delta) {
        float dx = (float) Math.sin(rotationY - Math.PI/2) * moveSpeed * delta;
        float dz = (float) Math.cos(rotationY - Math.PI/2) * moveSpeed * delta;
        
        position.x += dx;
        position.z += dz;
    }
    
    /**
     * Strafe the player right.
     * 
     * @param delta Time since last update in seconds
     */
    public void strafeRight(float delta) {
        float dx = (float) Math.sin(rotationY + Math.PI/2) * moveSpeed * delta;
        float dz = (float) Math.cos(rotationY + Math.PI/2) * moveSpeed * delta;
        
        position.x += dx;
        position.z += dz;
    }
    
    /**
     * Rotate the player left.
     * 
     * @param delta Time since last update in seconds
     */
    public void rotateLeft(float delta) {
        rotationY -= rotateSpeed * delta;
    }
    
    /**
     * Rotate the player right.
     * 
     * @param delta Time since last update in seconds
     */
    public void rotateRight(float delta) {
        rotationY += rotateSpeed * delta;
    }
    
    /**
     * Make the player jump.
     */
    public void jump() {
        if (!isJumping && stamina >= 10) {
            isJumping = true;
            verticalVelocity = (float) Math.sqrt(2 * 9.8f * jumpHeight);
            stamina -= 10; // Jumping costs 10 stamina
        }
    }
    
    /**
     * Make the player sprint.
     * 
     * @param sprinting Whether the player is sprinting
     */
    public void setSprinting(boolean sprinting) {
        if (sprinting && stamina > 0) {
            moveSpeed = 8.0f; // Faster when sprinting
        } else {
            moveSpeed = 5.0f; // Normal speed
        }
    }
    
    /**
     * Consume stamina for sprinting.
     * 
     * @param delta Time since last update in seconds
     */
    public void consumeSprintStamina(float delta) {
        stamina -= 15 * delta; // Consume 15 stamina per second while sprinting
        if (stamina < 0) {
            stamina = 0;
            setSprinting(false);
        }
    }
    
    /**
     * Interact with the world.
     * 
     * @return true if interaction was successful
     */
    public boolean interact() {
        // Check for vehicle interaction
        if (!inVehicle) {
            // Check for nearby vehicles to enter
            Vehicle nearbyVehicle = findNearbyVehicle();
            if (nearbyVehicle != null) {
                enterVehicle(nearbyVehicle);
                return true;
            }
        } else {
            // Exit current vehicle
            exitVehicle();
            return true;
        }
        
        // Check for building interaction
        if (!inBuilding) {
            // Check for nearby buildings to enter
            Building nearbyBuilding = findNearbyBuilding();
            if (nearbyBuilding != null) {
                enterBuilding(nearbyBuilding);
                return true;
            }
        } else {
            // Check if at exit
            if (isAtBuildingExit()) {
                exitBuilding();
                return true;
            }
        }
        
        // Check for item pickup
        Item nearbyItem = findNearbyItem();
        if (nearbyItem != null) {
            pickupItem(nearbyItem);
            return true;
        }
        
        // Check for NPC interaction
        NPC nearbyNPC = findNearbyNPC();
        if (nearbyNPC != null) {
            talkToNPC(nearbyNPC);
            return true;
        }
        
        // Check for shop interaction
        Shop nearbyShop = findNearbyShop();
        if (nearbyShop != null) {
            interactWithShop(nearbyShop);
            return true;
        }
        
        return false;
    }
    
    /**
     * Find a nearby vehicle.
     * 
     * @return The nearby vehicle, or null if none found
     */
    private Vehicle findNearbyVehicle() {
        // This would use the game world to find nearby vehicles
        // For now, return null as a placeholder
        return null;
    }
    
    /**
     * Enter a vehicle.
     * 
     * @param vehicle The vehicle to enter
     */
    public void enterVehicle(Vehicle vehicle) {
        if (!inVehicle) {
            currentVehicle = vehicle;
            inVehicle = true;
            
            // Hijack the vehicle if it's not already hijacked
            if (!vehicle.isHijacked()) {
                vehicle.hijack();
            }
            
            // Update player position to match vehicle
            position.x = vehicle.getPosition().x;
            position.y = vehicle.getPosition().y + 1.0f; // Sit above vehicle
            position.z = vehicle.getPosition().z;
        }
    }
    
    /**
     * Exit the current vehicle.
     */
    public void exitVehicle() {
        if (inVehicle) {
            // Place player next to vehicle
            position.x = currentVehicle.getPosition().x + 2.0f;
            position.z = currentVehicle.getPosition().z;
            
            currentVehicle = null;
            inVehicle = false;
        }
    }
    
    /**
     * Find a nearby building.
     * 
     * @return The nearby building, or null if none found
     */
    private Building findNearbyBuilding() {
        // This would use the game world to find nearby buildings
        // For now, return null as a placeholder
        return null;
    }
    
    /**
     * Enter a building.
     * 
     * @param building The building to enter
     */
    public void enterBuilding(Building building) {
        if (!inBuilding) {
            currentBuilding = building;
            inBuilding = true;
            
            // Update player position to inside building
            position.x = building.getPosition().x;
            position.y = building.getPosition().y + 1.8f; // Eye height
            position.z = building.getPosition().z;
        }
    }
    
    /**
     * Check if player is at a building exit.
     * 
     * @return true if at an exit
     */
    private boolean isAtBuildingExit() {
        // This would check if player is near a door or exit
        // For now, return false as a placeholder
        return false;
    }
    
    /**
     * Exit the current building.
     */
    public void exitBuilding() {
        if (inBuilding) {
            // Place player outside building
            position.x = currentBuilding.getPosition().x + 5.0f;
            position.z = currentBuilding.getPosition().z;
            
            currentBuilding = null;
            inBuilding = false;
        }
    }
    
    /**
     * Find a nearby item.
     * 
     * @return The nearby item, or null if none found
     */
    private Item findNearbyItem() {
        // This would use the game world to find nearby items
        // For now, return null as a placeholder
        return null;
    }
    
    /**
     * Pick up an item.
     * 
     * @param item The item to pick up
     * @return true if item was picked up
     */
    public boolean pickupItem(Item item) {
        return inventory.addItem(item);
    }
    
    /**
     * Find a nearby NPC.
     * 
     * @return The nearby NPC, or null if none found
     */
    private NPC findNearbyNPC() {
        // This would use the game world to find nearby NPCs
        // For now, return null as a placeholder
        return null;
    }
    
    /**
     * Talk to an NPC.
     * 
     * @param npc The NPC to talk to
     */
    public void talkToNPC(NPC npc) {
        // This would initiate dialogue with the NPC
        // For now, just a placeholder
    }
    
    /**
     * Find a nearby shop.
     * 
     * @return The nearby shop, or null if none found
     */
    private Shop findNearbyShop() {
        // This would use the game world to find nearby shops
        // For now, return null as a placeholder
        return null;
    }
    
    /**
     * Interact with a shop.
     * 
     * @param shop The shop to interact with
     */
    public void interactWithShop(Shop shop) {
        // This would open the shop interface
        // For now, just a placeholder
    }
    
    /**
     * Attack with the equipped weapon.
     * 
     * @return true if attack was successful
     */
    public boolean attack() {
        if (equippedWeapon != null && equippedWeapon.canFire()) {
            equippedWeapon.fire();
            return true;
        }
        return false;
    }
    
    /**
     * Reload the equipped weapon.
     * 
     * @return true if reload was successful
     */
    public boolean reload() {
        if (equippedWeapon != null) {
            return equippedWeapon.reload();
        }
        return false;
    }
    
    /**
     * Equip a weapon.
     * 
     * @param weapon The weapon to equip
     */
    public void equipWeapon(Weapon weapon) {
        if (weapons.contains(weapon)) {
            equippedWeapon = weapon;
        }
    }
    
    /**
     * Add a weapon to the player's arsenal.
     * 
     * @param weapon The weapon to add
     */
    public void addWeapon(Weapon weapon) {
        if (!weapons.contains(weapon)) {
            weapons.add(weapon);
            if (equippedWeapon == null) {
                equippedWeapon = weapon;
            }
        }
    }
    
    /**
     * Take damage.
     * 
     * @param amount Amount of damage to take
     * @return true if player is still alive
     */
    public boolean takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            return false; // Player died
        }
        return true; // Player still alive
    }
    
    /**
     * Heal the player.
     * 
     * @param amount Amount of health to restore
     */
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    /**
     * Get the player's position.
     * 
     * @return The player's position
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Set the player's position.
     * 
     * @param position The new position
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    /**
     * Set the player's position.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    /**
     * Get the player's rotation around the Y axis.
     * 
     * @return The player's Y rotation in radians
     */
    public float getRotationY() {
        return rotationY;
    }
    
    /**
     * Set the player's rotation around the Y axis.
     * 
     * @param rotationY The new Y rotation in radians
     */
    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }
    
    /**
     * Get the player's inventory.
     * 
     * @return The player's inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * Get the player's equipped weapon.
     * 
     * @return The equipped weapon
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    
    /**
     * Get the player's weapons.
     * 
     * @return The player's weapons
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }
    
    /**
     * Check if the player is in a vehicle.
     * 
     * @return true if in a vehicle
     */
    public boolean isInVehicle() {
        return inVehicle;
    }
    
    /**
     * Get the player's current vehicle.
     * 
     * @return The current vehicle
     */
    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
    
    /**
     * Check if the player is in a building.
     * 
     * @return true if in a building
     */
    public boolean isInBuilding() {
        return inBuilding;
    }
    
    /**
     * Get the player's current building.
     * 
     * @return The current building
     */
    public Building getCurrentBuilding() {
        return currentBuilding;
    }
    
    /**
     * Get the player's health.
     * 
     * @return The player's health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Get the player's maximum health.
     * 
     * @return The player's maximum health
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Get the player's stamina.
     * 
     * @return The player's stamina
     */
    public int getStamina() {
        return stamina;
    }
    
    /**
     * Get the player's maximum stamina.
     * 
     * @return The player's maximum s
(Content truncated due to size limit. Use line ranges to read in chunks)