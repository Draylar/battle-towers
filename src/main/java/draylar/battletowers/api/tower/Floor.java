package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private final Identifier id = new Identifier("empty");
    private Boolean placeLadders;
    private Boolean placeChests;
    private Boolean placeSpawners;
    private Boolean placeBossLock;
    private List<Identifier> lootTables = new ArrayList<>();
    private List<Identifier> entities = new ArrayList<>();
    private int minSpawns = 0;
    private int maxSpawns = 0;

    // this constructor only exists to stop inlining of the above fields
    public Floor(Boolean placeLadders, Boolean placeChests, Boolean placeSpawners, Boolean placeBossLock, List<Identifier> lootTables, List<Identifier> entities, int minSpawns, int maxSpawns) {
        this.placeLadders = placeLadders;
        this.placeChests = placeChests;
        this.placeSpawners = placeSpawners;
        this.placeBossLock = placeBossLock;
        this.lootTables = lootTables;
        this.entities = entities;
        this.minSpawns = minSpawns;
        this.maxSpawns = maxSpawns;
    }

    public Identifier getId() {
        return id;
    }

    public List<Identifier> getLootTables() {
        return lootTables;
    }

    public List<Identifier> getEntities() {
        return entities;
    }

    public Boolean placeLadders() {
        return placeLadders;
    }

    public Boolean placeChests() {
        return placeChests;
    }

    public Boolean placeSpawners() {
        return placeSpawners;
    }

    public Boolean placeBossLock() {
        return placeBossLock;
    }

    public void setLootTables(List<Identifier> lootTables) {
        this.lootTables = lootTables;
    }

    public void setEntities(List<Identifier> entities) {
        this.entities = entities;
    }

    public void setPlaceLadders(boolean placeLadders) {
        this.placeLadders = placeLadders;
    }

    public void setPlaceChests(boolean placeChests) {
        this.placeChests = placeChests;
    }

    public void setPlaceSpawners(boolean placeSpawners) {
        this.placeSpawners = placeSpawners;
    }

    public void setPlaceBossLock(boolean placeBossLock) {
        this.placeBossLock = placeBossLock;
    }

    public int getMinSpawns() {
        return minSpawns;
    }

    public int getMaxSpawns() {
        return maxSpawns;
    }

    public boolean placeContentDeployer() {
        return placeChests || placeLadders || !placeSpawners || placeBossLock;
    }
}
