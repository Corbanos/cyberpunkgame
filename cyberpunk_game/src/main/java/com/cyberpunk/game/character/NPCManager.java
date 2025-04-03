package com.cyberpunk.game.character;

import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages NPCs in the game.
 */
public class NPCManager {
    
    // List of NPCs
    private List<NPC> npcs;
    
    // Random generator
    private final Random random = new Random();
    
    /**
     * Constructor.
     */
    public NPCManager() {
        npcs = new ArrayList<>();
    }
    
    /**
     * Generate NPCs for the city.
     * 
     * @param numCivilians Number of civilian NPCs
     * @param numGangMembers Number of gang member NPCs
     * @param numPolice Number of police NPCs
     * @param numVendors Number of vendor NPCs
     * @param citySize Size of the city
     */
    public void generateNPCs(int numCivilians, int numGangMembers, int numPolice, int numVendors, float citySize) {
        // Generate civilian NPCs
        for (int i = 0; i < numCivilians; i++) {
            NPC npc = new NPC("Civilian " + i, NPCType.CIVILIAN);
            
            // Random position within city bounds
            float x = (random.nextFloat() * 2 - 1) * citySize / 2;
            float z = (random.nextFloat() * 2 - 1) * citySize / 2;
            npc.setPosition(x, 0, z);
            
            // Random appearance
            randomizeAppearance(npc);
            
            npcs.add(npc);
        }
        
        // Generate gang member NPCs
        for (int i = 0; i < numGangMembers; i++) {
            NPC npc = new NPC("Gang Member " + i, NPCType.GANG_MEMBER);
            
            // Random position within city bounds
            float x = (random.nextFloat() * 2 - 1) * citySize / 2;
            float z = (random.nextFloat() * 2 - 1) * citySize / 2;
            npc.setPosition(x, 0, z);
            
            // Random appearance
            randomizeAppearance(npc);
            
            // Gang members have weapons
            Weapon weapon;
            if (random.nextFloat() < 0.7f) {
                weapon = new Weapon("Pistol", WeaponType.PISTOL);
            } else if (random.nextFloat() < 0.9f) {
                weapon = new Weapon("SMG", WeaponType.SMG);
            } else {
                weapon = new Weapon("Assault Rifle", WeaponType.ASSAULT_RIFLE);
            }
            npc.setWeapon(weapon);
            
            npcs.add(npc);
        }
        
        // Generate police NPCs
        for (int i = 0; i < numPolice; i++) {
            NPC npc = new NPC("Police Officer " + i, NPCType.POLICE);
            
            // Random position within city bounds
            float x = (random.nextFloat() * 2 - 1) * citySize / 2;
            float z = (random.nextFloat() * 2 - 1) * citySize / 2;
            npc.setPosition(x, 0, z);
            
            // Random appearance
            randomizeAppearance(npc);
            
            // Police have pistols
            Weapon weapon = new Weapon("Police Pistol", WeaponType.PISTOL);
            npc.setWeapon(weapon);
            
            npcs.add(npc);
        }
        
        // Generate vendor NPCs
        for (int i = 0; i < numVendors; i++) {
            NPC npc = new NPC("Vendor " + i, NPCType.VENDOR);
            
            // Random position within city bounds
            float x = (random.nextFloat() * 2 - 1) * citySize / 2;
            float z = (random.nextFloat() * 2 - 1) * citySize / 2;
            npc.setPosition(x, 0, z);
            
            // Random appearance
            randomizeAppearance(npc);
            
            npcs.add(npc);
        }
    }
    
    /**
     * Randomize NPC appearance.
     * 
     * @param npc The NPC to randomize appearance for
     */
    private void randomizeAppearance(NPC npc) {
        NPCAppearance appearance = npc.getAppearance();
        
        // Random gender
        NPCAppearance.Gender[] genders = NPCAppearance.Gender.values();
        appearance.setGender(genders[random.nextInt(genders.length)]);
        
        // Random body type
        NPCAppearance.BodyType[] bodyTypes = NPCAppearance.BodyType.values();
        appearance.setBodyType(bodyTypes[random.nextInt(bodyTypes.length)]);
        
        // Random hair style
        NPCAppearance.HairStyle[] hairStyles = NPCAppearance.HairStyle.values();
        appearance.setHairStyle(hairStyles[random.nextInt(hairStyles.length)]);
        
        // Random colors
        appearance.setHairColor(random.nextInt(0xFFFFFF));
        appearance.setSkinColor(0xFFD700 + random.nextInt(0x8B4513 - 0xFFD700));
        appearance.setEyeColor(random.nextInt(0xFFFFFF));
        
        // Random clothing
        appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.SHIRT, random.nextInt(0xFFFFFF)));
        appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.PANTS, random.nextInt(0xFFFFFF)));
        appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.SHOES, random.nextInt(0xFFFFFF)));
        
        // Random chance for additional clothing
        if (random.nextBoolean()) {
            appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.JACKET, random.nextInt(0xFFFFFF)));
        }
        
        if (random.nextBoolean()) {
            appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.HAT, random.nextInt(0xFFFFFF)));
        }
        
        if (random.nextBoolean()) {
            appearance.addClothing(new NPCClothing(NPCClothing.ClothingType.GLASSES, random.nextInt(0xFFFFFF)));
        }
        
        // Cybernetics (more common for gang members)
        if (npc.getType() == NPCType.GANG_MEMBER || random.nextFloat() < 0.3f) {
            NPCCybernetic.CyberneticType[] cyberTypes = NPCCybernetic.CyberneticType.values();
            appearance.addCybernetic(new NPCCybernetic(cyberTypes[random.nextInt(cyberTypes.length)], 1 + random.nextInt(3)));
        }
    }
    
    /**
     * Update all NPCs.
     * 
     * @param delta Time since last update in seconds
     * @param player The player character
     * @param buildings List of buildings in the city
     */
    public void update(float delta, Player player, List<Building> buildings) {
        for (NPC npc : npcs) {
            npc.update(delta, player, buildings);
        }
    }
    
    /**
     * Get all NPCs.
     * 
     * @return List of NPCs
     */
    public List<NPC> getNPCs() {
        return npcs;
    }
    
    /**
     * Get NPCs of a specific type.
     * 
     * @param type NPC type
     * @return List of NPCs of the specified type
     */
    public List<NPC> getNPCsByType(NPCType type) {
        List<NPC> result = new ArrayList<>();
        for (NPC npc : npcs) {
            if (npc.getType() == type) {
                result.add(npc);
            }
        }
        return result;
    }
    
    /**
     * Get NPCs within a certain range of a position.
     * 
     * @param position The position
     * @param range The range
     * @return List of NPCs within range
     */
    public List<NPC> getNPCsInRange(Vector3f position, float range) {
        List<NPC> result = new ArrayList<>();
        for (NPC npc : npcs) {
            float dx = npc.getPosition().x - position.x;
            float dz = npc.getPosition().z - position.z;
            float distanceSquared = dx * dx + dz * dz;
            
            if (distanceSquared <= range * range) {
                result.add(npc);
            }
        }
        return result;
    }
    
    /**
     * Add an NPC.
     * 
     * @param npc The NPC to add
     */
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    
    /**
     * Remove an NPC.
     * 
     * @param npc The NPC to remove
     */
    public void removeNPC(NPC npc) {
        npcs.remove(npc);
    }
}

/**
 * Represents an NPC in the game.
 */
public class NPC {
    
    // NPC properties
    private String name;
    private NPCType type;
    private Vector3f position;
    private float rotationY;
    private NPCState state;
    private NPCAppearance appearance;
    
    // NPC movement
    private float moveSpeed;
    private Vector3f targetPosition;
    private float pathUpdateTimer;
    private final float PATH_UPDATE_INTERVAL = 3.0f; // Update path every 3 seconds
    
    // NPC behavior
    private Building targetBuilding;
    private boolean insideBuilding;
    private float buildingStayTimer;
    private float buildingStayDuration;
    
    // NPC combat
    private int health;
    private int maxHealth;
    private Weapon weapon;
    private boolean hostile;
    private NPC targetNPC;
    private Player targetPlayer;
    
    // Random generator
    private final Random random = new Random();
    
    /**
     * Constructor.
     * 
     * @param name NPC name
     * @param type NPC type
     */
    public NPC(String name, NPCType type) {
        this.name = name;
        this.type = type;
        position = new Vector3f();
        rotationY = 0.0f;
        state = NPCState.IDLE;
        appearance = new NPCAppearance();
        
        // Set properties based on NPC type
        switch (type) {
            case CIVILIAN:
                moveSpeed = 1.0f + random.nextFloat() * 0.5f; // 1.0-1.5
                maxHealth = 50;
                hostile = false;
                break;
            case GANG_MEMBER:
                moveSpeed = 1.2f + random.nextFloat() * 0.8f; // 1.2-2.0
                maxHealth = 80;
                hostile = true;
                break;
            case POLICE:
                moveSpeed = 1.5f + random.nextFloat() * 0.5f; // 1.5-2.0
                maxHealth = 100;
                hostile = false;
                break;
            case VENDOR:
                moveSpeed = 0.8f + random.nextFloat() * 0.4f; // 0.8-1.2
                maxHealth = 60;
                hostile = false;
                break;
        }
        
        health = maxHealth;
        insideBuilding = false;
        pathUpdateTimer = 0.0f;
    }
    
    /**
     * Update NPC state.
     * 
     * @param delta Time since last update in seconds
     * @param player The player character
     * @param buildings List of buildings in the city
     */
    public void update(float delta, Player player, List<Building> buildings) {
        // Update path finding timer
        pathUpdateTimer += delta;
        
        // Update behavior based on state
        switch (state) {
            case IDLE:
                updateIdleState(delta, player, buildings);
                break;
            case WALKING:
                updateWalkingState(delta, player, buildings);
                break;
            case ENTERING_BUILDING:
                updateEnteringBuildingState(delta);
                break;
            case INSIDE_BUILDING:
                updateInsideBuildingState(delta);
                break;
            case EXITING_BUILDING:
                updateExitingBuildingState(delta);
                break;
            case COMBAT:
                updateCombatState(delta, player);
                break;
            case FLEEING:
                updateFleeingState(delta, player);
                break;
        }
        
        // Check for player interaction
        checkPlayerInteraction(player);
    }
    
    /**
     * Update idle state.
     * 
     * @param delta Time since last update in seconds
     * @param player The player character
     * @param buildings List of buildings in the city
     */
    private void updateIdleState(float delta, Player player, List<Building> buildings) {
        // Chance to start walking
        if (random.nextFloat() < 0.01f) {
            // Choose random target position
            float x = position.x + (random.nextFloat() * 40 - 20); // +/- 20 units
            float z = position.z + (random.nextFloat() * 40 - 20); // +/- 20 units
            targetPosition = new Vector3f(x, 0, z);
            state = NPCState.WALKING;
        }
        
        // Chance to enter a building
        if (!insideBuilding && random.nextFloat() < 0.005f) {
            // Find nearby building
            Building nearestBuilding = findNearestBuilding(buildings, 20.0f);
            if (nearestBuilding != null) {
                targetBuilding = nearestBuilding;
                targetPosition = new Vector3f(targetBuilding.getPosition());
                state = NPCState.WALKING;
            }
        }
        
        // Check for combat (gang members)
        if (type == NPCType.GANG_MEMBER && hostile) {
            // Check if player is nearby and visible
            float distanceToPlayer = distanceTo(player.getPosition());
            if (distanceToPlayer < 15.0f && canSee(player.getPosition())) {
                targetPlayer = player;
                state = NPCState.COMBAT;
            }
        }
    }
    
    /**
     * Update walking state.
     * 
     * @param delta Time since last update in seconds
     * @param player The player character
     * @param buildings List of buildings in the city
     */
    private void updateWalkingState(float delta, Player player, List<Building> buildings) {
        // Move towards target position
        moveTowards(targetPosition, delta);
        
        // Check if reached target
        float distanceToTarget = distanceTo(targetPosition);
        if (distanceToTarget < 1.0f) {
            // If target is a building, enter it
            if (targetBuilding != null && distanceToTarget < 2.0f) {
                state = NPCState.ENTERING_BUILDING;
            } else {
                // Otherwise, go back to idle
                state = NPCState.IDLE;
                targetPosition = null;
            }
        }
        
        // Check for obstacles and update path if needed
        if (pathUpdateTimer >= PATH_UPDATE_INTERVAL) {
            pathUpdateTimer = 0.0f;
            
            // Check if path is blocked
            if (isPathBlocked(targetPosition, buildings)) {
                // Find new path
                Vector3f newTarget = findNewPath(targetPosition, buildings);
                if (newTarget != null) {
                    targetPosition = newTarget;
                } else {
                    // Can't find path, go back to idle
                    state = NPCState.IDLE;
                    targetPosition = null;
                }
            }
        }
        
        // Check for combat (gang members)
        if (type == NPCType.GANG_MEMBER && hostile) {
            // Check if player is nearby and visible
            float distanceToPlayer = distanceTo(player.getPosition());
            if (distanceToPlayer < 15.0f && canSee(player.getPosition())) {
                targetPlayer = player;
                state = NPCState.COMBAT;
            }
        }
    }
    
    /**
     * Update entering building state.
     * 
     * @param delta Time since last update in seconds
     */
    private void updateEnteringBuildingState(float delta) {
        // Animation/transition for entering building
        // For now, just instantly teleport inside
        position = new Vector3f(targetBuilding.getPosition());
        insideBuilding = true;
        state = NPCState.INSIDE_BUILDING;
        
        // Determine how long to stay in the building
        buildingStayDuration = 30.0f + random.nextFloat() * 90.0f; // 30-120 seconds
        buildingStayTimer = 0.0f;
    }
    
    /**
     * Update inside building state.
     * 
     * @param delta Time since last update in seconds
     */
    private void updateInsideBuildingState(float delta) {
        // Update timer
        buildingStayTimer += delta;
        
        // Check if it's time to leave
        if (buildingStayTimer >= buildingStayDuration) {
            state = NPCState.EXITING_BUILDING;
        }
    }
    
    /**
     * Update exiting building state.
     * 
     * @param delta Time since last update in seconds
     */
    private void updateExitingBuildingState(float delta) {
        // Animation/transition for exiting buil
(Content truncated due to size limit. Use line ranges to read in chunks)