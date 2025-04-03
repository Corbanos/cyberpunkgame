package com.cyberpunk.game.ui;

import org.lwjgl.opengl.GL11;
import org.joml.Vector3f;
import org.joml.Vector2f;

import com.cyberpunk.game.character.Player;
import com.cyberpunk.game.city.CityGenerator;
import com.cyberpunk.game.city.BuildingData;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages the mini-map and expandable map in the game.
 */
public class MapSystem {
    
    // Map properties
    private boolean expanded;
    private float miniMapSize;
    private float expandedMapSize;
    private float mapScale;
    private Vector2f position;
    
    // Map elements
    private List<MapMarker> markers;
    
    // Player reference
    private Player player;
    
    // City reference
    private CityGenerator cityGenerator;
    
    /**
     * Constructor.
     * 
     * @param player Player reference
     * @param cityGenerator City generator reference
     */
    public MapSystem(Player player, CityGenerator cityGenerator) {
        this.player = player;
        this.cityGenerator = cityGenerator;
        
        expanded = false;
        miniMapSize = 150.0f; // Size in pixels
        expandedMapSize = 800.0f; // Size in pixels
        mapScale = 0.1f; // Scale factor (1 game unit = 0.1 map pixels)
        position = new Vector2f(20, 20); // Top-left corner position
        
        markers = new ArrayList<>();
        
        // Initialize map markers
        initializeMarkers();
    }
    
    /**
     * Initialize map markers.
     */
    private void initializeMarkers() {
        // Add markers for important locations
        
        // Add markers for all buildings
        List<BuildingData> buildings = cityGenerator.getBuildings();
        for (BuildingData building : buildings) {
            MapMarker.MarkerType markerType;
            
            // Determine marker type based on building type
            switch (building.getType()) {
                case SKYSCRAPER:
                    markerType = MapMarker.MarkerType.SKYSCRAPER;
                    break;
                case APARTMENT:
                    markerType = MapMarker.MarkerType.APARTMENT;
                    break;
                case WAREHOUSE:
                    markerType = MapMarker.MarkerType.WAREHOUSE;
                    break;
                case ENTERTAINMENT:
                    markerType = MapMarker.MarkerType.ENTERTAINMENT;
                    break;
                case SLUM:
                    markerType = MapMarker.MarkerType.SLUM;
                    break;
                case TECH:
                    markerType = MapMarker.MarkerType.TECH;
                    break;
                case MARKET:
                    markerType = MapMarker.MarkerType.MARKET;
                    break;
                case GANG_HIDEOUT:
                    markerType = MapMarker.MarkerType.GANG_HIDEOUT;
                    break;
                default:
                    markerType = MapMarker.MarkerType.BUILDING;
            }
            
            // Add marker
            MapMarker marker = new MapMarker(
                new Vector2f(building.getPosition().x, building.getPosition().z),
                markerType,
                building.getName()
            );
            
            markers.add(marker);
            
            // If building has a shop, add shop marker
            if (building.hasShop()) {
                MapMarker shopMarker = new MapMarker(
                    new Vector2f(building.getPosition().x, building.getPosition().z),
                    MapMarker.MarkerType.SHOP,
                    building.getShopName()
                );
                
                markers.add(shopMarker);
            }
        }
        
        // Add mission markers (would be updated dynamically during gameplay)
        // This is just a placeholder for initial setup
        markers.add(new MapMarker(
            new Vector2f(100, 100),
            MapMarker.MarkerType.MISSION,
            "Meet Don Vincenzo"
        ));
    }
    
    /**
     * Update the map system.
     * 
     * @param delta Time since last update in seconds
     */
    public void update(float delta) {
        // Update marker visibility based on player's knowledge
        // This would be implemented based on game state
    }
    
    /**
     * Toggle map expansion.
     */
    public void toggleExpanded() {
        expanded = !expanded;
    }
    
    /**
     * Render the map.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    public void render(int screenWidth, int screenHeight) {
        if (expanded) {
            renderExpandedMap(screenWidth, screenHeight);
        } else {
            renderMiniMap(screenWidth, screenHeight);
        }
    }
    
    /**
     * Render the mini-map.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    private void renderMiniMap(int screenWidth, int screenHeight) {
        // Calculate map position (top-right corner)
        float mapX = screenWidth - miniMapSize - 20;
        float mapY = 20;
        
        // Draw map background
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f); // Semi-transparent black
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(mapX, mapY);
        GL11.glVertex2f(mapX + miniMapSize, mapY);
        GL11.glVertex2f(mapX + miniMapSize, mapY + miniMapSize);
        GL11.glVertex2f(mapX, mapY + miniMapSize);
        GL11.glEnd();
        
        // Draw map border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(mapX, mapY);
        GL11.glVertex2f(mapX + miniMapSize, mapY);
        GL11.glVertex2f(mapX + miniMapSize, mapY + miniMapSize);
        GL11.glVertex2f(mapX, mapY + miniMapSize);
        GL11.glEnd();
        
        // Calculate map center (player position on map)
        float centerX = mapX + miniMapSize / 2;
        float centerY = mapY + miniMapSize / 2;
        
        // Draw map elements
        renderMapElements(centerX, centerY, miniMapSize / 2, mapScale);
        
        // Draw player marker
        renderPlayerMarker(centerX, centerY);
        
        // Draw "Press M to expand" text
        // This would use a text rendering system
        // For now, just a placeholder
    }
    
    /**
     * Render the expanded map.
     * 
     * @param screenWidth Screen width
     * @param screenHeight Screen height
     */
    private void renderExpandedMap(int screenWidth, int screenHeight) {
        // Calculate map position (centered)
        float mapX = (screenWidth - expandedMapSize) / 2;
        float mapY = (screenHeight - expandedMapSize) / 2;
        
        // Draw map background
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.9f); // Semi-transparent black
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(mapX, mapY);
        GL11.glVertex2f(mapX + expandedMapSize, mapY);
        GL11.glVertex2f(mapX + expandedMapSize, mapY + expandedMapSize);
        GL11.glVertex2f(mapX, mapY + expandedMapSize);
        GL11.glEnd();
        
        // Draw map border
        GL11.glColor4f(0.0f, 0.8f, 1.0f, 1.0f); // Cyan
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(mapX, mapY);
        GL11.glVertex2f(mapX + expandedMapSize, mapY);
        GL11.glVertex2f(mapX + expandedMapSize, mapY + expandedMapSize);
        GL11.glVertex2f(mapX, mapY + expandedMapSize);
        GL11.glEnd();
        
        // Calculate map center (player position on map)
        float centerX = mapX + expandedMapSize / 2;
        float centerY = mapY + expandedMapSize / 2;
        
        // Draw map elements
        renderMapElements(centerX, centerY, expandedMapSize / 2, mapScale * 3);
        
        // Draw player marker
        renderPlayerMarker(centerX, centerY);
        
        // Draw map legend
        renderMapLegend(mapX + 20, mapY + expandedMapSize - 150);
        
        // Draw district names
        renderDistrictNames(centerX, centerY, expandedMapSize / 2, mapScale * 3);
    }
    
    /**
     * Render map elements (buildings, roads, etc.).
     * 
     * @param centerX Center X position
     * @param centerY Center Y position
     * @param radius Map radius
     * @param scale Map scale
     */
    private void renderMapElements(float centerX, float centerY, float radius, float scale) {
        // Get player position
        Vector3f playerPos = player.getPosition();
        
        // Draw roads
        renderRoads(centerX, centerY, playerPos, scale);
        
        // Draw district boundaries
        renderDistrictBoundaries(centerX, centerY, playerPos, scale);
        
        // Draw markers
        for (MapMarker marker : markers) {
            // Calculate marker position on map
            float markerX = centerX + (marker.getPosition().x - playerPos.x) * scale;
            float markerY = centerY + (marker.getPosition().y - playerPos.z) * scale;
            
            // Skip if outside map bounds
            if (Math.abs(markerX - centerX) > radius || Math.abs(markerY - centerY) > radius) {
                continue;
            }
            
            // Draw marker
            renderMarker(markerX, markerY, marker.getType(), expanded);
        }
    }
    
    /**
     * Render roads on the map.
     * 
     * @param centerX Center X position
     * @param centerY Center Y position
     * @param playerPos Player position
     * @param scale Map scale
     */
    private void renderRoads(float centerX, float centerY, Vector3f playerPos, float scale) {
        // Get roads from city generator
        // This would use actual road data from the city generator
        // For now, just draw a simple grid
        
        GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.8f); // Gray
        GL11.glLineWidth(2.0f);
        
        // Draw horizontal roads
        GL11.glBegin(GL11.GL_LINES);
        for (int i = -5; i <= 5; i++) {
            float roadZ = i * 100; // Roads every 100 units
            float mapY = centerY + (roadZ - playerPos.z) * scale;
            
            GL11.glVertex2f(centerX - 1000 * scale, mapY);
            GL11.glVertex2f(centerX + 1000 * scale, mapY);
        }
        GL11.glEnd();
        
        // Draw vertical roads
        GL11.glBegin(GL11.GL_LINES);
        for (int i = -5; i <= 5; i++) {
            float roadX = i * 100; // Roads every 100 units
            float mapX = centerX + (roadX - playerPos.x) * scale;
            
            GL11.glVertex2f(mapX, centerY - 1000 * scale);
            GL11.glVertex2f(mapX, centerY + 1000 * scale);
        }
        GL11.glEnd();
        
        GL11.glLineWidth(1.0f);
    }
    
    /**
     * Render district boundaries on the map.
     * 
     * @param centerX Center X position
     * @param centerY Center Y position
     * @param playerPos Player position
     * @param scale Map scale
     */
    private void renderDistrictBoundaries(float centerX, float centerY, Vector3f playerPos, float scale) {
        // Get districts from city generator
        // This would use actual district data from the city generator
        // For now, just draw some simple boundaries
        
        GL11.glColor4f(0.8f, 0.8f, 0.0f, 0.5f); // Yellow
        GL11.glLineWidth(3.0f);
        
        // Draw a few district boundaries
        GL11.glBegin(GL11.GL_LINE_LOOP);
        float x1 = centerX + (-200 - playerPos.x) * scale;
        float y1 = centerY + (-200 - playerPos.z) * scale;
        float x2 = centerX + (200 - playerPos.x) * scale;
        float y2 = centerY + (200 - playerPos.z) * scale;
        
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x1, y2);
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_LINE_LOOP);
        x1 = centerX + (250 - playerPos.x) * scale;
        y1 = centerY + (-300 - playerPos.z) * scale;
        x2 = centerX + (500 - playerPos.x) * scale;
        y2 = centerY + (0 - playerPos.z) * scale;
        
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x1, y2);
        GL11.glEnd();
        
        GL11.glLineWidth(1.0f);
    }
    
    /**
     * Render district names on the map.
     * 
     * @param centerX Center X position
     * @param centerY Center Y position
     * @param radius Map radius
     * @param scale Map scale
     */
    private void renderDistrictNames(float centerX, float centerY, float radius, float scale) {
        // This would use a text rendering system
        // For now, just a placeholder
        
        // Only render district names on expanded map
        if (!expanded) {
            return;
        }
        
        // Get player position
        Vector3f playerPos = player.getPosition();
        
        // District positions and names
        String[] districtNames = {
            "Downtown", "Industrial Zone", "Residential Sector",
            "Neon Plaza", "Corporate District", "Slums"
        };
        
        float[][] districtPositions = {
            {0, 0}, {350, -150}, {-350, 150},
            {-250, -250}, {250, 250}, {-350, -350}
        };
        
        // Draw district names
        for (int i = 0; i < districtNames.length; i++) {
            float x = districtPositions[i][0];
            float z = districtPositions[i][1];
            
            float mapX = centerX + (x - playerPos.x) * scale;
            float mapY = centerY + (z - playerPos.z) * scale;
            
            // Skip if outside map bounds
            if (Math.abs(mapX - centerX) > radius || Math.abs(mapY - centerY) > radius) {
                continue;
            }
            
            // This would render text at the position
            // For now, just a placeholder
        }
    }
    
    /**
     * Render the player marker.
     * 
     * @param centerX Center X position
     * @param centerY Center Y position
     */
    private void renderPlayerMarker(float centerX, float centerY) {
        // Draw player marker (triangle pointing in player's direction)
        float size = expanded ? 10.0f : 5.0f;
        float rotation = player.getRotationY();
        
        GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); // Green
        
        GL11.glPushMatrix();
        GL11.glTranslatef(centerX, centerY, 0);
        GL11.glRotatef((float) Math.toDegrees(rotation), 0, 0, 1);
        
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(0, -size);
        GL11.glVertex2f(-size / 2, size / 2);
        GL11.glVertex2f(size / 2, size / 2);
        GL11.glEnd();
        
        GL11.glPopMatrix();
    }
    
    /**
     * Render a map marker.
     * 
     * @param x X position
     * @param y Y position
     * @param type Marker type
     * @param showLabel Whether to show the label
     */
    private void renderMarker(float x, float y, MapMarker.MarkerType type, boolean showLabel) {
        float size = expanded ? 6.0f : 3.0f;
        
        // Set color based on marker type
        switch (type) {
            case BUILDING:
                GL11.glColor4f(0.7f, 0.7f, 0.7f, 0.7f); // Gray
                break;
            case SKYSCRAPER:
                GL11.glColor4f(0.0f, 0.5f, 1.0f, 0.7f); // Blue
                break;
            case APARTMENT:
                GL11.glColor4f(0.5f, 0.5f, 1.0f, 0.7f); // Light blue
                break;
            case WAREHOUSE:
                GL11.glColor4f(0.7f, 0.5f, 0.3f, 0.7f); // Brown
                break;
            case ENTERTAINMENT:
                GL11.glColor4f(1.0f, 0.0f, 1.0f, 0.7f); // Magenta
                break;
            case SLUM:
                GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.7f); // Dark gray
                break;
            case TECH:
                GL11.glColor4f(0.0f, 1.0f, 1.0f, 0.7f); // Cyan
                break;
            case MARKET:
   
(Content truncated due to size limit. Use line ranges to read in chunks)