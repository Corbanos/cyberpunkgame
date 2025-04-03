package com.cyberpunk.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game world including the city, buildings, NPCs, and player.
 */
public class GameWorld {
    
    // Player
    private Player player;
    
    // City components
    private List<Building> buildings;
    private List<Vehicle> vehicles;
    private List<NPC> npcs;
    private List<Shop> shops;
    
    // World dimensions
    private final float worldSize = 200.0f;
    
    /**
     * Constructor.
     */
    public GameWorld() {
        // Initialize lists
        buildings = new ArrayList<>();
        vehicles = new ArrayList<>();
        npcs = new ArrayList<>();
        shops = new ArrayList<>();
        
        // Create player
        player = new Player();
        player.setPosition(0, 1.8f, 0); // Start at origin, eye height 1.8
        
        // Initialize world
        initializeWorld();
    }
    
    /**
     * Initialize the game world.
     */
    private void initializeWorld() {
        // Create buildings
        createBuildings();
        
        // Create vehicles
        createVehicles();
        
        // Create NPCs
        createNPCs();
        
        // Create shops
        createShops();
    }
    
    /**
     * Create buildings in the world.
     */
    private void createBuildings() {
        // This is a placeholder for building creation
        // In the future, this will load building data from files
        
        // Create a few sample buildings
        for (int i = 0; i < 20; i++) {
            float x = (float) (Math.random() * worldSize - worldSize/2);
            float z = (float) (Math.random() * worldSize - worldSize/2);
            
            // Avoid placing buildings at the origin
            if (Math.abs(x) < 10 && Math.abs(z) < 10) {
                continue;
            }
            
            Building building = new Building();
            building.setPosition(x, 0, z);
            building.setSize(10 + (float) (Math.random() * 10), 
                            20 + (float) (Math.random() * 30), 
                            10 + (float) (Math.random() * 10));
            
            buildings.add(building);
            
            // Some buildings are shops
            if (Math.random() < 0.3) {
                Shop shop = new Shop();
                shop.setPosition(x, 0, z);
                shop.setBuilding(building);
                shop.setName("Shop " + shops.size());
                shops.add(shop);
            }
        }
    }
    
    /**
     * Create vehicles in the world.
     */
    private void createVehicles() {
        // This is a placeholder for vehicle creation
        // In the future, this will load vehicle data from files
        
        // Create a few sample vehicles
        for (int i = 0; i < 10; i++) {
            float x = (float) (Math.random() * worldSize - worldSize/2);
            float z = (float) (Math.random() * worldSize - worldSize/2);
            
            Vehicle vehicle = new Vehicle();
            vehicle.setPosition(x, 0, z);
            vehicle.setType(Vehicle.Type.values()[(int) (Math.random() * Vehicle.Type.values().length)]);
            
            vehicles.add(vehicle);
        }
    }
    
    /**
     * Create NPCs in the world.
     */
    private void createNPCs() {
        // This is a placeholder for NPC creation
        // In the future, this will load NPC data from files
        
        // Create a few sample NPCs
        for (int i = 0; i < 30; i++) {
            float x = (float) (Math.random() * worldSize - worldSize/2);
            float z = (float) (Math.random() * worldSize - worldSize/2);
            
            NPC npc = new NPC();
            npc.setPosition(x, 0, z);
            npc.setType(NPC.Type.values()[(int) (Math.random() * NPC.Type.values().length)]);
            
            npcs.add(npc);
        }
    }
    
    /**
     * Create shops in the world.
     */
    private void createShops() {
        // Shops are created along with buildings
    }
    
    /**
     * Update the game world.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(double delta) {
        // Update player
        player.update(delta);
        
        // Update buildings
        for (Building building : buildings) {
            building.update(delta);
        }
        
        // Update vehicles
        for (Vehicle vehicle : vehicles) {
            vehicle.update(delta);
        }
        
        // Update NPCs
        for (NPC npc : npcs) {
            npc.update(delta);
        }
        
        // Update shops
        for (Shop shop : shops) {
            shop.update(delta);
        }
        
        // Check for collisions
        checkCollisions();
    }
    
    /**
     * Check for collisions between entities.
     */
    private void checkCollisions() {
        // This is a placeholder for collision detection
        // In the future, this will use a spatial partitioning system for efficiency
    }
    
    /**
     * Get the player.
     * 
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Get the list of buildings.
     * 
     * @return the buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }
    
    /**
     * Get the list of vehicles.
     * 
     * @return the vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    
    /**
     * Get the list of NPCs.
     * 
     * @return the NPCs
     */
    public List<NPC> getNpcs() {
        return npcs;
    }
    
    /**
     * Get the list of shops.
     * 
     * @return the shops
     */
    public List<Shop> getShops() {
        return shops;
    }
}

/**
 * Base class for all entities in the game world.
 */
class Entity {
    protected float x, y, z;
    
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    
    public void update(double delta) {
        // Base update method, to be overridden by subclasses
    }
}

/**
 * Represents the player character.
 */
class Player extends Entity {
    private float speed = 5.0f;
    private float rotationY = 0.0f;
    private Inventory inventory;
    
    public Player() {
        inventory = new Inventory(36); // 36 slots like Minecraft
    }
    
    @Override
    public void update(double delta) {
        // Player movement will be controlled by input handler
    }
    
    public Inventory getInventory() {
        return inventory;
    }
}

/**
 * Represents a building in the city.
 */
class Building extends Entity {
    private float width, height, depth;
    private boolean canEnter = true;
    private List<Furniture> furniture;
    
    public Building() {
        furniture = new ArrayList<>();
    }
    
    public void setSize(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getDepth() { return depth; }
    
    @Override
    public void update(double delta) {
        // Buildings don't need to be updated every frame
    }
}

/**
 * Represents furniture inside buildings.
 */
class Furniture extends Entity {
    private String type;
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}

/**
 * Represents a vehicle that can be hijacked.
 */
class Vehicle extends Entity {
    public enum Type { CAR, MOTORCYCLE, TRUCK, FLYING_CAR }
    
    private Type type;
    private float speed = 0.0f;
    private float maxSpeed = 20.0f;
    private boolean hijacked = false;
    
    public void setType(Type type) {
        this.type = type;
        
        // Set properties based on type
        switch (type) {
            case CAR:
                maxSpeed = 20.0f;
                break;
            case MOTORCYCLE:
                maxSpeed = 25.0f;
                break;
            case TRUCK:
                maxSpeed = 15.0f;
                break;
            case FLYING_CAR:
                maxSpeed = 30.0f;
                break;
        }
    }
    
    public Type getType() {
        return type;
    }
    
    public void hijack() {
        hijacked = true;
    }
    
    public boolean isHijacked() {
        return hijacked;
    }
    
    @Override
    public void update(double delta) {
        // Vehicle movement logic will go here
    }
}

/**
 * Represents an NPC in the city.
 */
class NPC extends Entity {
    public enum Type { CIVILIAN, GANG_MEMBER, POLICE, VENDOR }
    
    private Type type;
    private float speed = 1.0f;
    private Building targetBuilding;
    private boolean insideBuilding = false;
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setTargetBuilding(Building building) {
        this.targetBuilding = building;
    }
    
    @Override
    public void update(double delta) {
        // NPC AI logic will go here
        // For now, just random movement
        if (Math.random() < 0.01) {
            x += (Math.random() - 0.5) * speed;
            z += (Math.random() - 0.5) * speed;
        }
    }
}

/**
 * Represents a shop in the city.
 */
class Shop extends Entity {
    private String name;
    private Building building;
    private List<Item> inventory;
    
    public Shop() {
        inventory = new ArrayList<>();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setBuilding(Building building) {
        this.building = building;
    }
    
    public Building getBuilding() {
        return building;
    }
    
    @Override
    public void update(double delta) {
        // Shops don't need to be updated every frame
    }
}

/**
 * Represents an item in the game.
 */
class Item {
    public enum Type { WEAPON, AMMO, KEY_ITEM, CONSUMABLE }
    
    private String name;
    private Type type;
    private int stackSize = 1;
    
    public Item(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public Type getType() {
        return type;
    }
    
    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }
    
    public int getStackSize() {
        return stackSize;
    }
}

/**
 * Represents the player's inventory.
 */
class Inventory {
    private Item[] items;
    
    public Inventory(int size) {
        items = new Item[size];
    }
    
    public boolean addItem(Item item) {
        // Find an empty slot
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }
        
        return false; // Inventory full
    }
    
    public Item getItem(int slot) {
        if (slot >= 0 && slot < items.length) {
            return items[slot];
        }
        
        return null;
    }
    
    public Item removeItem(int slot) {
        if (slot >= 0 && slot < items.length) {
            Item item = items[slot];
            items[slot] = null;
            return item;
        }
        
        return null;
    }
    
    public int getSize() {
        return items.length;
    }
}
