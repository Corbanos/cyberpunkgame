package com.cyberpunk.game.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the city layout and generation.
 */
public class CityGenerator {
    
    // City dimensions
    private final int citySize = 1000; // Size of the city in meters
    private final int blockSize = 50; // Size of a city block in meters
    private final int numBlocks; // Number of blocks in each direction
    
    // City components
    private List<District> districts;
    private List<Road> roads;
    
    // Random generator
    private final Random random = new Random();
    
    /**
     * Constructor.
     */
    public CityGenerator() {
        numBlocks = citySize / blockSize;
        districts = new ArrayList<>();
        roads = new ArrayList<>();
        
        // Initialize districts and roads
        initializeDistricts();
        initializeRoads();
    }
    
    /**
     * Initialize city districts.
     */
    private void initializeDistricts() {
        // Create different districts with varying characteristics
        
        // Downtown/Central district (high-rise buildings, corporate)
        District downtown = new District("Downtown", District.Type.CORPORATE);
        downtown.setBounds(-citySize/4, -citySize/4, citySize/4, citySize/4);
        districts.add(downtown);
        
        // Industrial district (factories, warehouses)
        District industrial = new District("Industrial Zone", District.Type.INDUSTRIAL);
        industrial.setBounds(-citySize/2, -citySize/2, -citySize/4, citySize/4);
        districts.add(industrial);
        
        // Residential district (apartments, small shops)
        District residential = new District("Residential Sector", District.Type.RESIDENTIAL);
        residential.setBounds(-citySize/4, citySize/4, citySize/4, citySize/2);
        districts.add(residential);
        
        // Entertainment district (clubs, bars, shops)
        District entertainment = new District("Neon Plaza", District.Type.ENTERTAINMENT);
        entertainment.setBounds(citySize/4, -citySize/4, citySize/2, citySize/4);
        districts.add(entertainment);
        
        // Slums (run-down buildings, high crime)
        District slums = new District("The Sprawl", District.Type.SLUMS);
        slums.setBounds(-citySize/2, citySize/4, -citySize/4, citySize/2);
        districts.add(slums);
        
        // Tech district (research labs, tech companies)
        District tech = new District("Silicon Heights", District.Type.TECH);
        tech.setBounds(citySize/4, citySize/4, citySize/2, citySize/2);
        districts.add(tech);
        
        // Market district (shops, vendors)
        District market = new District("Market Row", District.Type.MARKET);
        market.setBounds(citySize/4, -citySize/2, citySize/2, -citySize/4);
        districts.add(market);
        
        // Gang territory (dangerous, controlled by gangs)
        District gangTerritory = new District("Red Zone", District.Type.GANG_TERRITORY);
        gangTerritory.setBounds(-citySize/4, -citySize/2, citySize/4, -citySize/4);
        districts.add(gangTerritory);
    }
    
    /**
     * Initialize city roads.
     */
    private void initializeRoads() {
        // Create main roads
        for (int i = -numBlocks/2; i <= numBlocks/2; i++) {
            // Horizontal roads
            Road horizontalRoad = new Road();
            horizontalRoad.setStart(-citySize/2, 0, i * blockSize);
            horizontalRoad.setEnd(citySize/2, 0, i * blockSize);
            horizontalRoad.setWidth(i % 3 == 0 ? 15 : 10); // Main roads are wider
            roads.add(horizontalRoad);
            
            // Vertical roads
            Road verticalRoad = new Road();
            verticalRoad.setStart(i * blockSize, 0, -citySize/2);
            verticalRoad.setEnd(i * blockSize, 0, citySize/2);
            verticalRoad.setWidth(i % 3 == 0 ? 15 : 10); // Main roads are wider
            roads.add(verticalRoad);
        }
    }
    
    /**
     * Generate buildings for the city.
     * 
     * @return List of generated buildings
     */
    public List<BuildingData> generateBuildings() {
        List<BuildingData> buildings = new ArrayList<>();
        
        // Generate buildings for each district
        for (District district : districts) {
            int numBuildingsInDistrict = random.nextInt(20) + 30; // 30-50 buildings per district
            
            for (int i = 0; i < numBuildingsInDistrict; i++) {
                BuildingData building = new BuildingData();
                
                // Set building position within district bounds
                float x = random.nextFloat() * (district.getMaxX() - district.getMinX()) + district.getMinX();
                float z = random.nextFloat() * (district.getMaxZ() - district.getMinZ()) + district.getMinZ();
                
                // Snap to grid for better city layout
                x = Math.round(x / 10) * 10;
                z = Math.round(z / 10) * 10;
                
                building.setPosition(x, 0, z);
                
                // Set building properties based on district type
                setBuildingPropertiesByDistrict(building, district);
                
                // Add building to list
                buildings.add(building);
            }
        }
        
        return buildings;
    }
    
    /**
     * Set building properties based on district type.
     * 
     * @param building The building to set properties for
     * @param district The district the building is in
     */
    private void setBuildingPropertiesByDistrict(BuildingData building, District district) {
        switch (district.getType()) {
            case CORPORATE:
                building.setType(BuildingData.Type.SKYSCRAPER);
                building.setHeight(50 + random.nextInt(150)); // 50-200m tall
                building.setWidth(30 + random.nextInt(20)); // 30-50m wide
                building.setDepth(30 + random.nextInt(20)); // 30-50m deep
                building.setNumFloors(20 + random.nextInt(60)); // 20-80 floors
                
                // 20% chance of being a shop
                if (random.nextFloat() < 0.2f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.CORPORATE);
                }
                break;
                
            case INDUSTRIAL:
                building.setType(BuildingData.Type.WAREHOUSE);
                building.setHeight(10 + random.nextInt(20)); // 10-30m tall
                building.setWidth(40 + random.nextInt(60)); // 40-100m wide
                building.setDepth(40 + random.nextInt(60)); // 40-100m deep
                building.setNumFloors(1 + random.nextInt(3)); // 1-3 floors
                
                // 10% chance of being a shop
                if (random.nextFloat() < 0.1f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.INDUSTRIAL);
                }
                break;
                
            case RESIDENTIAL:
                building.setType(BuildingData.Type.APARTMENT);
                building.setHeight(15 + random.nextInt(35)); // 15-50m tall
                building.setWidth(20 + random.nextInt(15)); // 20-35m wide
                building.setDepth(20 + random.nextInt(15)); // 20-35m deep
                building.setNumFloors(5 + random.nextInt(15)); // 5-20 floors
                
                // 30% chance of being a shop
                if (random.nextFloat() < 0.3f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.CONVENIENCE);
                }
                break;
                
            case ENTERTAINMENT:
                building.setType(BuildingData.Type.ENTERTAINMENT);
                building.setHeight(10 + random.nextInt(30)); // 10-40m tall
                building.setWidth(25 + random.nextInt(25)); // 25-50m wide
                building.setDepth(25 + random.nextInt(25)); // 25-50m deep
                building.setNumFloors(2 + random.nextInt(8)); // 2-10 floors
                
                // 70% chance of being a shop
                if (random.nextFloat() < 0.7f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.ENTERTAINMENT);
                }
                break;
                
            case SLUMS:
                building.setType(BuildingData.Type.SLUM);
                building.setHeight(5 + random.nextInt(15)); // 5-20m tall
                building.setWidth(15 + random.nextInt(15)); // 15-30m wide
                building.setDepth(15 + random.nextInt(15)); // 15-30m deep
                building.setNumFloors(1 + random.nextInt(5)); // 1-6 floors
                
                // 40% chance of being a shop
                if (random.nextFloat() < 0.4f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.BLACK_MARKET);
                }
                break;
                
            case TECH:
                building.setType(BuildingData.Type.TECH);
                building.setHeight(20 + random.nextInt(40)); // 20-60m tall
                building.setWidth(30 + random.nextInt(20)); // 30-50m wide
                building.setDepth(30 + random.nextInt(20)); // 30-50m deep
                building.setNumFloors(5 + random.nextInt(15)); // 5-20 floors
                
                // 30% chance of being a shop
                if (random.nextFloat() < 0.3f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.TECH);
                }
                break;
                
            case MARKET:
                building.setType(BuildingData.Type.MARKET);
                building.setHeight(5 + random.nextInt(15)); // 5-20m tall
                building.setWidth(20 + random.nextInt(30)); // 20-50m wide
                building.setDepth(20 + random.nextInt(30)); // 20-50m deep
                building.setNumFloors(1 + random.nextInt(3)); // 1-3 floors
                
                // 90% chance of being a shop
                if (random.nextFloat() < 0.9f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.values()[random.nextInt(ShopData.Type.values().length)]);
                }
                break;
                
            case GANG_TERRITORY:
                building.setType(BuildingData.Type.GANG_HIDEOUT);
                building.setHeight(10 + random.nextInt(20)); // 10-30m tall
                building.setWidth(20 + random.nextInt(20)); // 20-40m wide
                building.setDepth(20 + random.nextInt(20)); // 20-40m deep
                building.setNumFloors(2 + random.nextInt(6)); // 2-8 floors
                
                // 50% chance of being a shop
                if (random.nextFloat() < 0.5f) {
                    building.setHasShop(true);
                    building.setShopType(ShopData.Type.WEAPONS);
                }
                break;
        }
    }
    
    /**
     * Generate shops for the city.
     * 
     * @param buildings List of buildings to place shops in
     * @return List of generated shops
     */
    public List<ShopData> generateShops(List<BuildingData> buildings) {
        List<ShopData> shops = new ArrayList<>();
        
        for (BuildingData building : buildings) {
            if (building.hasShop()) {
                ShopData shop = new ShopData();
                shop.setPosition(building.getX(), building.getY(), building.getZ());
                shop.setType(building.getShopType());
                shop.setBuilding(building);
                
                // Generate shop name based on type
                shop.setName(generateShopName(shop.getType()));
                
                shops.add(shop);
            }
        }
        
        return shops;
    }
    
    /**
     * Generate a shop name based on shop type.
     * 
     * @param type Shop type
     * @return Generated shop name
     */
    private String generateShopName(ShopData.Type type) {
        String[] prefixes = {
            "Neo", "Cyber", "Digital", "Chrome", "Neon", "Synth", "Tech", "Pulse", "Byte", "Quantum",
            "Flux", "Vector", "Pixel", "Grid", "Void", "Static", "Glitch", "Echo", "Surge", "Apex"
        };
        
        String[] suffixes = {
            "Hub", "Mart", "Shop", "Store", "Emporium", "Depot", "Exchange", "Market", "Bazaar", "Outlet",
            "Corner", "Spot", "Place", "Zone", "Center", "Point", "Junction", "Terminal", "Station", "Port"
        };
        
        String prefix = prefixes[random.nextInt(prefixes.length)];
        String suffix = suffixes[random.nextInt(suffixes.length)];
        
        switch (type) {
            case WEAPONS:
                return prefix + " Arms";
            case TECH:
                return prefix + " " + suffix;
            case CLOTHING:
                return prefix + " Threads";
            case FOOD:
                return prefix + " Eats";
            case MEDICAL:
                return prefix + " Meds";
            case BLACK_MARKET:
                return "The " + prefix + " " + suffix;
            case ENTERTAINMENT:
                return prefix + " Lounge";
            case CONVENIENCE:
                return prefix + " Mart";
            case CORPORATE:
                return prefix + " Corp";
            case INDUSTRIAL:
                return prefix + " Industrial";
            default:
                return prefix + " " + suffix;
        }
    }
    
    /**
     * Generate vehicles for the city.
     * 
     * @return List of generated vehicles
     */
    public List<VehicleData> generateVehicles() {
        List<VehicleData> vehicles = new ArrayList<>();
        
        // Generate vehicles on roads
        int numVehicles = 100 + random.nextInt(100); // 100-200 vehicles
        
        for (int i = 0; i < numVehicles; i++) {
            VehicleData vehicle = new VehicleData();
            
            // Select a random road
            Road road = roads.get(random.nextInt(roads.length()));
            
            // Position vehicle on the road
            float roadProgress = random.nextFloat();
            float x = road.getStartX() + (road.getEndX() - road.getStartX()) * roadProgress;
            float z = road.getStartZ() + (road.getEndZ() - road.getStartZ()) * roadProgress;
            
            // Add some randomness to position (to avoid vehicles being perfectly aligned)
            x += (random.nextFloat() - 0.5f) * 5;
            z += (random.nextFloat() - 0.5f) * 5;
            
            vehicle.setPosition(x, 0, z);
            
            // Set vehicle type
            VehicleData.Type[] types = VehicleData.Type.values();
            vehicle.setType(types[random.nextInt(types.length)]);
            
            // Set vehicle properties based on type
            setVehiclePropertiesByType(vehicle);
            
            vehicles.add(vehicle);
        }
        
        return vehicles;
    }
    
    /**
     * Set vehicle properties based on vehicle type.
     * 
     * @param vehicle The vehicle to set properties for
     */
    private void setVehiclePropertiesByType(VehicleData vehicle) {
        switch (vehicle.getType()) {
            case CAR:
                vehicle.setMaxSpeed(120 + random.nextInt(80)); // 120-200 km/h
                vehicle.setAcceleration(5 + random.nextFloat() * 5); // 5-10 m/s²
                vehicle.setHandling(0.6f + random.nextFloat() * 0.3f); // 0.6-0.9
                vehicle.setDurability(70 + random.nextInt(30)); // 70-100
                break;
                
            case MOTORCYCLE:
                vehicle.setMaxSpeed(150 + random.nextInt(100)); // 150-250 km/h
       
(Content truncated due to size limit. Use line ranges to read in chunks)