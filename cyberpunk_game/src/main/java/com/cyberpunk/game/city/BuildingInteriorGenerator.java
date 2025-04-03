package com.cyberpunk.game.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Manages building interiors and furniture.
 */
public class BuildingInteriorGenerator {
    
    // Random generator
    private final Random random = new Random();
    
    // Furniture types by room type
    private Map<RoomType, List<FurnitureType>> furnitureByRoom;
    
    /**
     * Constructor.
     */
    public BuildingInteriorGenerator() {
        initializeFurnitureTypes();
    }
    
    /**
     * Initialize furniture types for different room types.
     */
    private void initializeFurnitureTypes() {
        furnitureByRoom = new HashMap<>();
        
        // Living room furniture
        List<FurnitureType> livingRoomFurniture = new ArrayList<>();
        livingRoomFurniture.add(FurnitureType.SOFA);
        livingRoomFurniture.add(FurnitureType.COFFEE_TABLE);
        livingRoomFurniture.add(FurnitureType.TV);
        livingRoomFurniture.add(FurnitureType.BOOKSHELF);
        livingRoomFurniture.add(FurnitureType.CHAIR);
        livingRoomFurniture.add(FurnitureType.PLANT);
        livingRoomFurniture.add(FurnitureType.LAMP);
        livingRoomFurniture.add(FurnitureType.HOLOGRAM_PROJECTOR);
        furnitureByRoom.put(RoomType.LIVING_ROOM, livingRoomFurniture);
        
        // Bedroom furniture
        List<FurnitureType> bedroomFurniture = new ArrayList<>();
        bedroomFurniture.add(FurnitureType.BED);
        bedroomFurniture.add(FurnitureType.DRESSER);
        bedroomFurniture.add(FurnitureType.NIGHTSTAND);
        bedroomFurniture.add(FurnitureType.WARDROBE);
        bedroomFurniture.add(FurnitureType.DESK);
        bedroomFurniture.add(FurnitureType.CHAIR);
        bedroomFurniture.add(FurnitureType.LAMP);
        bedroomFurniture.add(FurnitureType.COMPUTER);
        furnitureByRoom.put(RoomType.BEDROOM, bedroomFurniture);
        
        // Kitchen furniture
        List<FurnitureType> kitchenFurniture = new ArrayList<>();
        kitchenFurniture.add(FurnitureType.COUNTER);
        kitchenFurniture.add(FurnitureType.STOVE);
        kitchenFurniture.add(FurnitureType.REFRIGERATOR);
        kitchenFurniture.add(FurnitureType.SINK);
        kitchenFurniture.add(FurnitureType.TABLE);
        kitchenFurniture.add(FurnitureType.CHAIR);
        kitchenFurniture.add(FurnitureType.CABINET);
        kitchenFurniture.add(FurnitureType.FOOD_SYNTHESIZER);
        furnitureByRoom.put(RoomType.KITCHEN, kitchenFurniture);
        
        // Bathroom furniture
        List<FurnitureType> bathroomFurniture = new ArrayList<>();
        bathroomFurniture.add(FurnitureType.TOILET);
        bathroomFurniture.add(FurnitureType.SINK);
        bathroomFurniture.add(FurnitureType.SHOWER);
        bathroomFurniture.add(FurnitureType.BATHTUB);
        bathroomFurniture.add(FurnitureType.MIRROR);
        bathroomFurniture.add(FurnitureType.CABINET);
        furnitureByRoom.put(RoomType.BATHROOM, bathroomFurniture);
        
        // Office furniture
        List<FurnitureType> officeFurniture = new ArrayList<>();
        officeFurniture.add(FurnitureType.DESK);
        officeFurniture.add(FurnitureType.CHAIR);
        officeFurniture.add(FurnitureType.COMPUTER);
        officeFurniture.add(FurnitureType.BOOKSHELF);
        officeFurniture.add(FurnitureType.FILING_CABINET);
        officeFurniture.add(FurnitureType.PLANT);
        officeFurniture.add(FurnitureType.LAMP);
        officeFurniture.add(FurnitureType.HOLOGRAM_PROJECTOR);
        officeFurniture.add(FurnitureType.SERVER_RACK);
        furnitureByRoom.put(RoomType.OFFICE, officeFurniture);
        
        // Shop furniture
        List<FurnitureType> shopFurniture = new ArrayList<>();
        shopFurniture.add(FurnitureType.COUNTER);
        shopFurniture.add(FurnitureType.SHELF);
        shopFurniture.add(FurnitureType.DISPLAY_CASE);
        shopFurniture.add(FurnitureType.CASH_REGISTER);
        shopFurniture.add(FurnitureType.CHAIR);
        shopFurniture.add(FurnitureType.TABLE);
        shopFurniture.add(FurnitureType.VENDING_MACHINE);
        shopFurniture.add(FurnitureType.HOLOGRAM_DISPLAY);
        furnitureByRoom.put(RoomType.SHOP, shopFurniture);
        
        // Bar furniture
        List<FurnitureType> barFurniture = new ArrayList<>();
        barFurniture.add(FurnitureType.BAR_COUNTER);
        barFurniture.add(FurnitureType.BARSTOOL);
        barFurniture.add(FurnitureType.TABLE);
        barFurniture.add(FurnitureType.CHAIR);
        barFurniture.add(FurnitureType.SHELF);
        barFurniture.add(FurnitureType.JUKEBOX);
        barFurniture.add(FurnitureType.NEON_SIGN);
        barFurniture.add(FurnitureType.HOLOGRAM_DISPLAY);
        furnitureByRoom.put(RoomType.BAR, barFurniture);
        
        // Lab furniture
        List<FurnitureType> labFurniture = new ArrayList<>();
        labFurniture.add(FurnitureType.LAB_BENCH);
        labFurniture.add(FurnitureType.CHAIR);
        labFurniture.add(FurnitureType.COMPUTER);
        labFurniture.add(FurnitureType.SHELF);
        labFurniture.add(FurnitureType.CABINET);
        labFurniture.add(FurnitureType.LAB_EQUIPMENT);
        labFurniture.add(FurnitureType.SERVER_RACK);
        labFurniture.add(FurnitureType.HOLOGRAM_PROJECTOR);
        furnitureByRoom.put(RoomType.LAB, labFurniture);
        
        // Warehouse furniture
        List<FurnitureType> warehouseFurniture = new ArrayList<>();
        warehouseFurniture.add(FurnitureType.CRATE);
        warehouseFurniture.add(FurnitureType.SHELF);
        warehouseFurniture.add(FurnitureType.DESK);
        warehouseFurniture.add(FurnitureType.CHAIR);
        warehouseFurniture.add(FurnitureType.COMPUTER);
        warehouseFurniture.add(FurnitureType.FORKLIFT);
        furnitureByRoom.put(RoomType.WAREHOUSE, warehouseFurniture);
    }
    
    /**
     * Generate a building interior.
     * 
     * @param building The building to generate an interior for
     * @return The generated building interior
     */
    public BuildingInterior generateInterior(BuildingData building) {
        BuildingInterior interior = new BuildingInterior();
        
        // Set building reference
        interior.setBuilding(building);
        
        // Determine number of rooms based on building size
        int numFloors = building.getNumFloors();
        float floorArea = building.getWidth() * building.getDepth();
        
        // Generate rooms for each floor
        for (int floor = 0; floor < numFloors; floor++) {
            // Number of rooms on this floor
            int numRooms = (int) (floorArea / 100) + random.nextInt(3);
            numRooms = Math.max(1, Math.min(numRooms, 8)); // Between 1 and 8 rooms
            
            // Generate rooms
            List<Room> rooms = generateRooms(building, floor, numRooms);
            interior.addRooms(rooms);
            
            // Generate furniture for each room
            for (Room room : rooms) {
                List<Furniture> furniture = generateFurniture(room);
                interior.addFurniture(furniture);
            }
        }
        
        return interior;
    }
    
    /**
     * Generate rooms for a floor.
     * 
     * @param building The building
     * @param floor The floor number
     * @param numRooms Number of rooms to generate
     * @return List of generated rooms
     */
    private List<Room> generateRooms(BuildingData building, int floor, int numRooms) {
        List<Room> rooms = new ArrayList<>();
        
        // Building dimensions
        float width = building.getWidth();
        float depth = building.getDepth();
        float floorHeight = building.getHeight() / building.getNumFloors();
        
        // Determine room types based on building type and floor
        List<RoomType> roomTypes = determineRoomTypes(building, floor, numRooms);
        
        // Simple room layout: divide floor into grid
        int gridSize = (int) Math.ceil(Math.sqrt(numRooms));
        float roomWidth = width / gridSize;
        float roomDepth = depth / gridSize;
        
        // Generate rooms in grid
        int roomIndex = 0;
        for (int row = 0; row < gridSize && roomIndex < numRooms; row++) {
            for (int col = 0; col < gridSize && roomIndex < numRooms; col++) {
                Room room = new Room();
                
                // Set room position
                float roomX = -width/2 + col * roomWidth;
                float roomY = floor * floorHeight;
                float roomZ = -depth/2 + row * roomDepth;
                room.setPosition(roomX, roomY, roomZ);
                
                // Set room dimensions
                room.setSize(roomWidth, floorHeight, roomDepth);
                
                // Set room type
                room.setType(roomTypes.get(roomIndex));
                
                // Set floor number
                room.setFloor(floor);
                
                rooms.add(room);
                roomIndex++;
            }
        }
        
        return rooms;
    }
    
    /**
     * Determine room types for a floor.
     * 
     * @param building The building
     * @param floor The floor number
     * @param numRooms Number of rooms
     * @return List of room types
     */
    private List<RoomType> determineRoomTypes(BuildingData building, int floor, int numRooms) {
        List<RoomType> roomTypes = new ArrayList<>();
        
        // Determine room types based on building type
        switch (building.getType()) {
            case SKYSCRAPER:
                if (floor == 0) {
                    // Ground floor: lobby, offices, shops
                    roomTypes.add(RoomType.LOBBY);
                    if (building.hasShop()) {
                        roomTypes.add(RoomType.SHOP);
                    }
                    for (int i = roomTypes.size(); i < numRooms; i++) {
                        roomTypes.add(RoomType.OFFICE);
                    }
                } else if (floor < building.getNumFloors() / 3) {
                    // Lower floors: offices
                    for (int i = 0; i < numRooms; i++) {
                        roomTypes.add(RoomType.OFFICE);
                    }
                } else {
                    // Upper floors: apartments
                    for (int i = 0; i < numRooms; i++) {
                        if (i == 0) roomTypes.add(RoomType.LIVING_ROOM);
                        else if (i == 1) roomTypes.add(RoomType.KITCHEN);
                        else if (i == 2) roomTypes.add(RoomType.BATHROOM);
                        else roomTypes.add(RoomType.BEDROOM);
                    }
                }
                break;
                
            case APARTMENT:
                // Apartment building: apartments on all floors
                for (int i = 0; i < numRooms; i++) {
                    if (i == 0) roomTypes.add(RoomType.LIVING_ROOM);
                    else if (i == 1) roomTypes.add(RoomType.KITCHEN);
                    else if (i == 2) roomTypes.add(RoomType.BATHROOM);
                    else roomTypes.add(RoomType.BEDROOM);
                }
                break;
                
            case WAREHOUSE:
                // Warehouse: mostly storage
                if (floor == 0 && building.hasShop()) {
                    roomTypes.add(RoomType.SHOP);
                    roomTypes.add(RoomType.OFFICE);
                    for (int i = roomTypes.size(); i < numRooms; i++) {
                        roomTypes.add(RoomType.WAREHOUSE);
                    }
                } else {
                    for (int i = 0; i < numRooms; i++) {
                        roomTypes.add(RoomType.WAREHOUSE);
                    }
                }
                break;
                
            case ENTERTAINMENT:
                if (floor == 0) {
                    // Ground floor: bar, shop
                    roomTypes.add(RoomType.BAR);
                    if (building.hasShop()) {
                        roomTypes.add(RoomType.SHOP);
                    }
                    for (int i = roomTypes.size(); i < numRooms; i++) {
                        roomTypes.add(RoomType.LIVING_ROOM);
                    }
                } else {
                    // Upper floors: apartments or more entertainment
                    for (int i = 0; i < numRooms; i++) {
                        if (random.nextBoolean()) {
                            roomTypes.add(RoomType.BAR);
                        } else {
                            roomTypes.add(RoomType.LIVING_ROOM);
                        }
                    }
                }
                break;
                
            case SLUM:
                // Slum: small apartments
                for (int i = 0; i < numRooms; i++) {
                    if (floor == 0 && i == 0 && building.hasShop()) {
                        roomTypes.add(RoomType.SHOP);
                    } else if (i % 3 == 0) {
                        roomTypes.add(RoomType.LIVING_ROOM);
                    } else if (i % 3 == 1) {
                        roomTypes.add(RoomType.KITCHEN);
                    } else {
                        roomTypes.add(RoomType.BEDROOM);
                    }
                }
                break;
                
            case TECH:
                if (floor == 0) {
                    // Ground floor: lobby, shop
                    roomTypes.add(RoomType.LOBBY);
                    if (building.hasShop()) {
                        roomTypes.add(RoomType.SHOP);
                    }
                    for (int i = roomTypes.size(); i < numRooms; i++) {
                        roomTypes.add(RoomType.OFFICE);
                    }
                } else {
                    // Upper floors: labs, offices
                    for (int i = 0; i < numRooms; i++) {
                        if (random.nextBoolean()) {
                            roomTypes.add(RoomType.LAB);
                        } else {
                            roomTypes.add(RoomType.OFFICE);
                        }
                    }
                }
                break;
                
            case MARKET:
                // Market: mostly shops
                for (int i = 0; i < numRooms; i++) {
                    if (building.hasShop() || random.nextFloat() < 0.7f) {
                        roomTypes.add(RoomType.SHOP);
                    } else {
                        roomTypes.add(RoomType.WAREHOUSE);
                    }
                }
                break;
                
            case GANG_HIDEOUT:
                if (floor == 0) {
                    // Ground floor: bar, shop
                    roomTypes.add(RoomType.BAR);
                    if (building.hasShop()) {
                        roomTypes.add(RoomType.SHOP);
                    }
                    for (int i = roomTypes.size(); i < numRooms; i++) {
                        roomTypes.add(RoomType.LIVING_ROOM);
                    }
                } else {
                    // Upper floors: living quarters, offices
                    for (int i = 0; i < numRooms; i++) {
                        if (i % 3 == 0) {
                            roomTypes.add(RoomType.LIVING_ROOM);
                        } else if (i % 3 == 1) {
                            roomTypes.add(RoomType.BEDROOM);
                        } else {
                            roomTypes.add(RoomType.OFFICE);
                        }
                    }
                }
                break;
                
            default:
                // Default: mixed use
                for (int i = 0; i < numRooms; i++) {
                    RoomType[] types = RoomType.values();
                    roomTypes.add(types[random.nextInt(types.length)]);
                }
        }
        
        // Ensure we have enough room types
    
(Content truncated due to size limit. Use line ranges to read in chunks)