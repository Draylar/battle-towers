package draylar.battletowers.api.tower;

import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerEntry;

import java.util.List;
import java.util.Set;

public class Floor {

    private final List<Identifier> chestLootTables;
    private final List<Identifier> spawnerEntries;

    public Floor(List<Identifier> chestLootTables, List<Identifier> spawnerEntries) {
        this.chestLootTables = chestLootTables;
        this.spawnerEntries = spawnerEntries;
    }

    public List<Identifier> getChestLootTables() {
        return chestLootTables;
    }

    public List<Identifier> getSpawnerEntries() {
        return spawnerEntries;
    }
}
