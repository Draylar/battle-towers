package draylar.battletowers.api;

import net.minecraft.util.Identifier;

import java.util.*;

public class Floors {

    private static final Map<Identifier, Floor> CONTENT_PROVIDERS = new HashMap<>();
    private static final Random RAND = new Random();
    private static final Identifier DEFAULT_LOOT_TABLE = new Identifier("battletowers", "default");

    public static void init() {
        register(new Identifier("battletowers", "stone/blacksmith"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/blacksmith")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/default"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/lake"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/lake")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/library"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/library")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/mineshaft"), new Floor(
                Arrays.asList(new Identifier("battletowers:stone/mineshaft")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/original"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList()
        ));

        register(new Identifier("battletowers", "stone/plains"), new Floor(
                Arrays.asList(new Identifier("battletowers:default")),
                Arrays.asList()
        ));
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
