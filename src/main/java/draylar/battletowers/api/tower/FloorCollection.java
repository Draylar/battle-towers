package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FloorCollection {

    private boolean placeLadders;
    private boolean placeChests;
    private boolean placeSpawners;
    private boolean placeBossLock;
    private final List<Identifier> lootTables = new ArrayList<>();
    private final List<Identifier> entities = new ArrayList<>();
    private final List<Floor> floors = new ArrayList<>();

    public FloorCollection(boolean placeLadders, boolean placeChests, boolean placeSpawners, boolean placeBossLock) {
        this.placeLadders = placeLadders;
        this.placeChests = placeChests;
        this.placeSpawners = placeSpawners;
        this.placeBossLock = placeBossLock;
    }

    public boolean isPlaceLadders() {
        return placeLadders;
    }

    public boolean isPlaceChests() {
        return placeChests;
    }

    public boolean isPlaceSpawners() {
        return placeSpawners;
    }

    public boolean placeBossLock() {
        return placeBossLock;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void applyDefaults(Floor floor) {
        if(floor.placeLadders() == null) {
            floor.setPlaceLadders(this.placeLadders);
        }

        if(floor.placeChests() == null) {
            floor.setPlaceChests(this.placeChests);
        }

        if(floor.placeSpawners() == null) {
            floor.setPlaceSpawners(this.placeSpawners);
        }

        if(floor.placeBossLock() == null) {
            floor.setPlaceBossLock(this.placeBossLock);
        }

        if(entities != null && !entities.isEmpty()) {
            floor.setEntities(entities);
        }

        if(lootTables != null && !lootTables.isEmpty()) {
            floor.setLootTables(lootTables);
        }
    }
}
