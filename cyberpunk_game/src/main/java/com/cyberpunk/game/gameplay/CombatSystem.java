package com.cyberpunk.game.gameplay;

import com.cyberpunk.game.character.Player;
import com.cyberpunk.game.character.NPC;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the combat system in the game.
 */
public class CombatSystem {
    
    // Random generator
    private final Random random = new Random();
    
    // Bullet tracer effects
    private List<BulletTracer> bulletTracers;
    
    // Impact effects
    private List<ImpactEffect> impactEffects;
    
    // Damage numbers
    private List<DamageNumber> damageNumbers;
    
    /**
     * Constructor.
     */
    public CombatSystem() {
        bulletTracers = new ArrayList<>();
        impactEffects = new ArrayList<>();
        damageNumbers = new ArrayList<>();
    }
    
    /**
     * Update the combat system.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Update bullet tracers
        for (int i = bulletTracers.size() - 1; i >= 0; i--) {
            BulletTracer tracer = bulletTracers.get(i);
            tracer.update(delta);
            if (tracer.isFinished()) {
                bulletTracers.remove(i);
            }
        }
        
        // Update impact effects
        for (int i = impactEffects.size() - 1; i >= 0; i--) {
            ImpactEffect effect = impactEffects.get(i);
            effect.update(delta);
            if (effect.isFinished()) {
                impactEffects.remove(i);
            }
        }
        
        // Update damage numbers
        for (int i = damageNumbers.size() - 1; i >= 0; i--) {
            DamageNumber number = damageNumbers.get(i);
            number.update(delta);
            if (number.isFinished()) {
                damageNumbers.remove(i);
            }
        }
    }
    
    /**
     * Process a player attack.
     * 
     * @param player The player
     * @param npcs List of NPCs
     * @return true if attack hit something
     */
    public boolean processPlayerAttack(Player player, List<NPC> npcs) {
        if (player.getEquippedWeapon() == null || !player.attack()) {
            return false; // No weapon or couldn't attack
        }
        
        // Get weapon properties
        Weapon weapon = player.getEquippedWeapon();
        float range = getWeaponRange(weapon);
        float spread = getWeaponSpread(weapon);
        
        // Calculate attack direction (where player is looking)
        Vector3f position = player.getPosition();
        float rotationY = player.getRotationY();
        
        Vector3f direction = new Vector3f(
            (float) Math.sin(rotationY),
            0,
            (float) Math.cos(rotationY)
        );
        
        // Apply weapon spread
        if (spread > 0) {
            float spreadX = (random.nextFloat() * 2 - 1) * spread;
            float spreadZ = (random.nextFloat() * 2 - 1) * spread;
            direction.x += spreadX;
            direction.z += spreadZ;
            direction.normalize();
        }
        
        // Create bullet tracer effect
        Vector3f tracerEnd = new Vector3f(
            position.x + direction.x * range,
            position.y,
            position.z + direction.z * range
        );
        
        BulletTracer tracer = new BulletTracer(
            new Vector3f(position.x, position.y + 1.6f, position.z), // Eye height
            tracerEnd,
            0.2f // Duration in seconds
        );
        bulletTracers.add(tracer);
        
        // Check for hits
        NPC hitNPC = null;
        float closestDistance = Float.MAX_VALUE;
        
        for (NPC npc : npcs) {
            // Skip NPCs inside buildings
            if (npc.isInsideBuilding()) {
                continue;
            }
            
            // Calculate distance to NPC
            Vector3f npcPosition = npc.getPosition();
            float dx = npcPosition.x - position.x;
            float dz = npcPosition.z - position.z;
            float distanceToNPC = (float) Math.sqrt(dx * dx + dz * dz);
            
            // Skip if too far
            if (distanceToNPC > range) {
                continue;
            }
            
            // Calculate angle to NPC
            float angleToNPC = (float) Math.atan2(dx, dz);
            float angleDifference = Math.abs(normalizeAngle(angleToNPC - rotationY));
            
            // Skip if not in front of player (within 45 degrees)
            if (angleDifference > Math.PI / 4) {
                continue;
            }
            
            // Calculate perpendicular distance to firing line
            float perpDistance = (float) (Math.sin(angleDifference) * distanceToNPC);
            
            // Skip if not close enough to firing line
            float hitThreshold = 0.5f + (distanceToNPC / range) * spread * 5.0f;
            if (perpDistance > hitThreshold) {
                continue;
            }
            
            // This NPC is hit, check if closest
            if (distanceToNPC < closestDistance) {
                hitNPC = npc;
                closestDistance = distanceToNPC;
            }
        }
        
        // Process hit
        if (hitNPC != null) {
            // Calculate damage with random variation
            int damage = weapon.getDamage();
            float variation = 0.2f; // 20% variation
            damage = (int) (damage * (1.0f - variation + random.nextFloat() * variation * 2));
            
            // Apply damage
            hitNPC.takeDamage(damage);
            
            // Create impact effect
            ImpactEffect impact = new ImpactEffect(
                new Vector3f(hitNPC.getPosition().x, hitNPC.getPosition().y + 1.0f, hitNPC.getPosition().z),
                0.5f // Duration in seconds
            );
            impactEffects.add(impact);
            
            // Create damage number
            DamageNumber damageNumber = new DamageNumber(
                new Vector3f(hitNPC.getPosition().x, hitNPC.getPosition().y + 1.5f, hitNPC.getPosition().z),
                damage,
                1.0f // Duration in seconds
            );
            damageNumbers.add(damageNumber);
            
            return true;
        }
        
        // No hit, create impact at end of tracer
        ImpactEffect impact = new ImpactEffect(tracerEnd, 0.3f);
        impactEffects.add(impact);
        
        return false;
    }
    
    /**
     * Process an NPC attack.
     * 
     * @param npc The attacking NPC
     * @param target The target (player or another NPC)
     * @param isPlayer true if target is player
     * @return true if attack hit
     */
    public boolean processNPCAttack(NPC npc, Object target, boolean isPlayer) {
        if (npc.getWeapon() == null) {
            return false; // No weapon
        }
        
        // Get weapon properties
        Weapon weapon = npc.getWeapon();
        float range = getWeaponRange(weapon);
        float spread = getWeaponSpread(weapon) * 1.5f; // NPCs are less accurate
        
        // Get positions
        Vector3f npcPosition = npc.getPosition();
        Vector3f targetPosition;
        
        if (isPlayer) {
            targetPosition = ((Player) target).getPosition();
        } else {
            targetPosition = ((NPC) target).getPosition();
        }
        
        // Calculate direction to target
        Vector3f direction = new Vector3f(
            targetPosition.x - npcPosition.x,
            0,
            targetPosition.z - npcPosition.z
        );
        float distance = direction.length();
        
        // Skip if too far
        if (distance > range) {
            return false;
        }
        
        direction.normalize();
        
        // Apply weapon spread
        if (spread > 0) {
            float spreadX = (random.nextFloat() * 2 - 1) * spread;
            float spreadZ = (random.nextFloat() * 2 - 1) * spread;
            direction.x += spreadX;
            direction.z += spreadZ;
            direction.normalize();
        }
        
        // Create bullet tracer effect
        Vector3f tracerEnd = new Vector3f(
            npcPosition.x + direction.x * range,
            npcPosition.y,
            npcPosition.z + direction.z * range
        );
        
        BulletTracer tracer = new BulletTracer(
            new Vector3f(npcPosition.x, npcPosition.y + 1.6f, npcPosition.z), // Eye height
            tracerEnd,
            0.2f // Duration in seconds
        );
        bulletTracers.add(tracer);
        
        // Calculate hit chance based on distance
        float hitChance = 1.0f - (distance / range) * 0.7f;
        
        // Adjust hit chance based on weapon type
        switch (weapon.getWeaponType()) {
            case PISTOL:
                hitChance *= 0.8f;
                break;
            case SHOTGUN:
                hitChance *= 0.6f;
                break;
            case ASSAULT_RIFLE:
                hitChance *= 0.7f;
                break;
            case SNIPER_RIFLE:
                hitChance *= 0.9f;
                break;
            case SMG:
                hitChance *= 0.6f;
                break;
        }
        
        // Check for hit
        if (random.nextFloat() < hitChance) {
            // Calculate damage with random variation
            int damage = weapon.getDamage();
            float variation = 0.2f; // 20% variation
            damage = (int) (damage * (1.0f - variation + random.nextFloat() * variation * 2));
            
            // Apply damage
            if (isPlayer) {
                ((Player) target).takeDamage(damage);
            } else {
                ((NPC) target).takeDamage(damage);
            }
            
            // Create impact effect
            ImpactEffect impact = new ImpactEffect(
                new Vector3f(targetPosition.x, targetPosition.y + 1.0f, targetPosition.z),
                0.5f // Duration in seconds
            );
            impactEffects.add(impact);
            
            // Create damage number
            DamageNumber damageNumber = new DamageNumber(
                new Vector3f(targetPosition.x, targetPosition.y + 1.5f, targetPosition.z),
                damage,
                1.0f // Duration in seconds
            );
            damageNumbers.add(damageNumber);
            
            return true;
        } else {
            // Miss, create impact at end of tracer
            ImpactEffect impact = new ImpactEffect(tracerEnd, 0.3f);
            impactEffects.add(impact);
            
            return false;
        }
    }
    
    /**
     * Get the range of a weapon.
     * 
     * @param weapon The weapon
     * @return The range in game units
     */
    private float getWeaponRange(Weapon weapon) {
        switch (weapon.getWeaponType()) {
            case PISTOL:
                return 30.0f;
            case SHOTGUN:
                return 15.0f;
            case ASSAULT_RIFLE:
                return 50.0f;
            case SNIPER_RIFLE:
                return 100.0f;
            case SMG:
                return 25.0f;
            case MELEE:
                return 2.0f;
            default:
                return 20.0f;
        }
    }
    
    /**
     * Get the spread of a weapon.
     * 
     * @param weapon The weapon
     * @return The spread factor
     */
    private float getWeaponSpread(Weapon weapon) {
        switch (weapon.getWeaponType()) {
            case PISTOL:
                return 0.05f;
            case SHOTGUN:
                return 0.2f;
            case ASSAULT_RIFLE:
                return 0.08f;
            case SNIPER_RIFLE:
                return 0.01f;
            case SMG:
                return 0.15f;
            case MELEE:
                return 0.0f;
            default:
                return 0.1f;
        }
    }
    
    /**
     * Normalize an angle to the range [-PI, PI].
     * 
     * @param angle The angle to normalize
     * @return The normalized angle
     */
    private float normalizeAngle(float angle) {
        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
    
    /**
     * Get all bullet tracers.
     * 
     * @return List of bullet tracers
     */
    public List<BulletTracer> getBulletTracers() {
        return bulletTracers;
    }
    
    /**
     * Get all impact effects.
     * 
     * @return List of impact effects
     */
    public List<ImpactEffect> getImpactEffects() {
        return impactEffects;
    }
    
    /**
     * Get all damage numbers.
     * 
     * @return List of damage numbers
     */
    public List<DamageNumber> getDamageNumbers() {
        return damageNumbers;
    }
}

/**
 * Represents a bullet tracer effect.
 */
class BulletTracer {
    private Vector3f start;
    private Vector3f end;
    private float duration;
    private float timer;
    
    /**
     * Constructor.
     * 
     * @param start Start position
     * @param end End position
     * @param duration Effect duration in seconds
     */
    public BulletTracer(Vector3f start, Vector3f end, float duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.timer = 0.0f;
    }
    
    /**
     * Update the tracer.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        timer += delta;
    }
    
    /**
     * Check if the tracer effect is finished.
     * 
     * @return true if finished
     */
    public boolean isFinished() {
        return timer >= duration;
    }
    
    /**
     * Get the start position.
     * 
     * @return The start position
     */
    public Vector3f getStart() {
        return start;
    }
    
    /**
     * Get the end position.
     * 
     * @return The end position
     */
    public Vector3f getEnd() {
        return end;
    }
    
    /**
     * Get the completion ratio (0.0 to 1.0).
     * 
     * @return The completion ratio
     */
    public float getCompletionRatio() {
        return Math.min(timer / duration, 1.0f);
    }
}

/**
 * Represents an impact effect.
 */
class ImpactEffect {
    private Vector3f position;
    private float duration;
    private float timer;
    
    /**
     * Constructor.
     * 
     * @param position Effect position
     * @param duration Effect duration in seconds
     */
    public ImpactEffect(Vector3f position, float duration) {
        this.position = position;
        this.duration = duration;
        this.timer = 0.0f;
    }
    
    /**
     * Update the effect.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        timer += delta;
    }
    
    /**
     * Check if the effect is finished.
     * 
     * @return true if finished
     */
    public boolean isFinished() {
        return timer >= duration;
    }
    
    /**
     * Get the position.
     * 
     * @return The position
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Get the completion ratio (0.0 to 1.0).
     * 
     * @return The completion ratio
     */
    public float getCompletionRatio() {
        return Math.min(timer / duration, 1.0f);
    }
}

/**
 * Represents a damage number effect.
 */
class DamageNumber {
    private Vector3f position;
    private int damage;
    private float duration;
    private float timer;
    
    /**
     * Constructor.
     * 
     * @param position Effect position
     * @param damage Damage amount
     * @param duration Effect duration in seconds
     */
    public DamageNumber(Vector3f position, int damage, float duration) {
        this.position = position;
        this.damage = damage;
        this.duration = duration;
        this.timer = 0.0f;
    }
    
    /**
     * Update the effect.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        timer += delta;
    }
    
    /**
     * Check if the effect is finished.
     * 
     * @return true if finished
     */
    public boolean isFinished() {
        return timer >= duration;
    }
    
    /**
     * Get the position.
     * 
     * @return The position
     */
    public Vector3f getPosition() {
        return position;
    }
    
    /**
     * Get the damage amount.
     * 
     * @return The damage amount
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * Get the completion ratio (0.0 to 1.0).
     * 
     * @return The completion ratio
     */
    public float getCompletionRatio() {
        return Math.min(timer / duration, 1.0f);
    }
}