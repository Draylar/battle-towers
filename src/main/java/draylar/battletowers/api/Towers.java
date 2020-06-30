package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.tower.BiomeConditional;
import draylar.battletowers.api.tower.Floor;
import draylar.battletowers.api.tower.Tower;
import draylar.battletowers.world.BattleTowerPoolElement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;
import robosky.structurehelpers.structure.processor.WeightedChanceProcessor;

import java.util.*;

public class Towers {

    private static final Map<Identifier, Floor> CONTENT_PROVIDERS = new HashMap<>();
    private static final Random RAND = new Random();
    private static final Identifier DEFAULT_LOOT_TABLE = new Identifier("battletowers", "default");
    private static final Identifier DEFAULT_SPAWNER = new Identifier("minecraft", "zombie");
    public static final Map<BiomeConditional, Tower> BIOME_ENTRANCES = new HashMap<>();

    public static void init() {
        // todo: log invalid structure identifiers literally anywhere

        BattleTowers.TOWER_DATA.getTowers().forEach((id, tower) -> {
            BIOME_ENTRANCES.put(tower.getBiomeConditional(), tower);

            // build processors
            WeightedChanceProcessor.Builder builder = WeightedChanceProcessor.builder();

            tower.getProcessors().forEach((inputId, weightedOutputIds) -> {
                // input block (what to process)
                Block input = Registry.BLOCK.get(inputId);

                // for each output
                weightedOutputIds.forEach(weightedOutput -> {
                    int weight = weightedOutput.getWeight();

                    // add block if it is valid
                    if(Registry.BLOCK.get(weightedOutput.getId()) != Blocks.AIR && weight > 0) {
                        builder.add(input, WeightedChanceProcessor.Entry.of(Registry.BLOCK.get(weightedOutput.getId()), weight));
                    }
                });
            });

            ImmutableList<StructureProcessor> PROCESSORS = ImmutableList
                    .<StructureProcessor>builder().add(builder.build()).build();

            List<Pair<StructurePoolElement, Integer>> entranceElements = new ArrayList<>();
            List<Pair<StructurePoolElement, Integer>> layerElements = new ArrayList<>();
            List<Pair<StructurePoolElement, Integer>> roofElements = new ArrayList<>();
            List<Pair<StructurePoolElement, Integer>> bottomElements = new ArrayList<>();

            tower.getEntrances().forEach(elementTemplate -> {
                entranceElements.add(Pair.of(new ExtendedSinglePoolElement(elementTemplate.getId(), false, PROCESSORS), 1));
                CONTENT_PROVIDERS.put(elementTemplate.getId(), new Floor(elementTemplate.getLootTables(), elementTemplate.getEntities()));
            });

            tower.getLayers().forEach(elementTemplate -> {
                layerElements.add(Pair.of(new BattleTowerPoolElement(elementTemplate.getId(), false, PROCESSORS), 1));
                CONTENT_PROVIDERS.put(elementTemplate.getId(), new Floor(elementTemplate.getLootTables(), elementTemplate.getEntities()));
            });

            tower.getRoofs().forEach(elementTemplate -> {
                roofElements.add(Pair.of(new ExtendedSinglePoolElement(elementTemplate.getId(), false, PROCESSORS), 1));
                CONTENT_PROVIDERS.put(elementTemplate.getId(), new Floor(elementTemplate.getLootTables(), elementTemplate.getEntities()));
            });

            tower.getBottoms().forEach(elementTemplate -> {
                bottomElements.add(Pair.of(new ExtendedSinglePoolElement(elementTemplate.getId(), false, PROCESSORS), 1));
                CONTENT_PROVIDERS.put(elementTemplate.getId(), new Floor(elementTemplate.getLootTables(), elementTemplate.getEntities()));
            });

            // entrance pool
            StructurePoolBasedGenerator.REGISTRY.add(
                    new StructurePool(
                            BattleTowers.id(tower.getName() + "_entrances"),
                            new Identifier("empty"),
                            entranceElements,
                            StructurePool.Projection.RIGID
                    )
            );

            // layer pool
            // todo: outlines -> layers
            StructurePoolBasedGenerator.REGISTRY.add(
                    new StructurePool(
                            BattleTowers.id(tower.getName() + "_outlines"),
                            BattleTowers.id(tower.getName() + "_tops"),
                            layerElements,
                            StructurePool.Projection.RIGID
                    )
            );

            // roof pool
            // todo: tops -> roofs
            StructurePoolBasedGenerator.REGISTRY.add(
                    new StructurePool(
                            BattleTowers.id(tower.getName() + "_tops"),
                            new Identifier("empty"),
                            roofElements,
                            StructurePool.Projection.RIGID
                    )
            );

            // bottom pool
            StructurePoolBasedGenerator.REGISTRY.add(
                    new StructurePool(
                            BattleTowers.id(tower.getName() + "_bottoms"),
                            new Identifier("empty"),
                            bottomElements,
                            StructurePool.Projection.RIGID
                    )
            );
        });
    }

    private Towers() {
        // NO-OP
    }

    public static Tower getEntranceFor(Biome biome) {
        // todo: consider all possible entrances instead of only the first
        for(Map.Entry<BiomeConditional, Tower> conditional : BIOME_ENTRANCES.entrySet()) {
            if(conditional.getKey().isValid(biome)) {
                return conditional.getValue();
            }
        }

        return BattleTowers.TOWER_DATA.getDefaultTower();
    }

    public static Identifier getSpawnerEntryFor(Identifier floorID) {
        if(CONTENT_PROVIDERS.containsKey(floorID)) {
            List<Identifier> spawnerEntries = CONTENT_PROVIDERS.get(floorID).getSpawnerEntries();

            if(spawnerEntries == null) {
                spawnerEntries = new ArrayList<>();
                spawnerEntries.add(BattleTowers.id("zombie"));
            }

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

            if(lootTables == null) {
                lootTables = new ArrayList<>();
                lootTables.add(BattleTowers.id("default"));
            }

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
}
