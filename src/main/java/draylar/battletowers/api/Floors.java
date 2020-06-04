package draylar.battletowers.api;

import net.minecraft.util.Identifier;
import net.minecraft.world.MobSpawnerEntry;

import java.util.*;

public class Floors {

    private static final Map<Identifier, Floor> CONTENT_PROVIDERS = new HashMap<>();
    private static final Random RAND = new Random();
    private static final Identifier DEFAULT_LOOT_TABLE = new Identifier("battletowers", "default");
    private static final MobSpawnerEntry DEFAULT_SPAWNER = new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build();

    public static void init() {
        register(new Identifier("battletowers", "stone/blacksmith"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/blacksmith")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "wither_skeleton")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/default"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "spider")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/lake"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/lake")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "guardian")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "drowned")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "pufferfish")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/library"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/library")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "witch")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "spider")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/mineshaft"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/mineshaft")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "cave_spider")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "spider")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/original"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "spider")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build()
                )
        ));

        register(new Identifier("battletowers", "stone/plains"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList(
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "enderman")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "zombie")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "skeleton")).build(),
                        new MobSpawnerEntryBuilder(new Identifier("minecraft", "spider")).build()
                )
        ));
    }

    public static MobSpawnerEntry getSpawnerEntryFor(Identifier floorID) {
        if(CONTENT_PROVIDERS.containsKey(floorID)) {
            List<MobSpawnerEntry> spawnerEntries = CONTENT_PROVIDERS.get(floorID).getSpawnerEntries();

            if(!spawnerEntries.isEmpty()) {
                return spawnerEntries.get(RAND.nextInt(spawnerEntries.size()));
            } else {
                System.out.println(String.format("[Battle Towers] floor %s's content provider has no valid spawners. Falling back to defaults.", floorID.toString()));
            }
        } else {
            System.out.println(String.format("[Battle Towers] floor %s doesn't have a registered content provider. Falling back to defaults.", floorID.toString()));
        }

        return DEFAULT_SPAWNER;
    }


    public static Identifier getLootTableFor(Identifier floorID) {
        if(CONTENT_PROVIDERS.containsKey(floorID)) {
            List<Identifier> lootTables = CONTENT_PROVIDERS.get(floorID).getChestLootTables();

            if(!lootTables.isEmpty()) {
                return lootTables.get(RAND.nextInt(lootTables.size()));
            } else {
                System.out.println(String.format("[Battle Towers] floor %s's content provider has no valid loot tables. Falling back to defaults.", floorID.toString()));
            }
        } else {
            System.out.println(String.format("[Battle Towers] floor %s doesn't have a registered content provider. Falling back to defaults.", floorID.toString()));
        }

        return DEFAULT_LOOT_TABLE;
    }

    public static void register(Identifier floorID, Floor contentProvider) {
        CONTENT_PROVIDERS.put(floorID, contentProvider);
    }
}
