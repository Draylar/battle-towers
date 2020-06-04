package draylar.battletowers.api;

import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerEntry;

import java.util.List;

public class Floor {

    private final List<Identifier> chestLootTables;
    private final List<MobSpawnerEntry> spawnerEntries;

    public Floor(List<Identifier> chestLootTables, List<MobSpawnerEntry> spawnerEntries) {
        this.chestLootTables = chestLootTables;
        this.spawnerEntries = spawnerEntries;
    }

    public List<Identifier> getChestLootTables() {
        return chestLootTables;
    }

    public List<MobSpawnerEntry> getSpawnerEntries() {
        return spawnerEntries;
    }
}
