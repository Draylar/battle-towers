package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.spawning.BiomeConditional;
import draylar.battletowers.api.tower.Floor;
import draylar.battletowers.api.tower.FloorCollection;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import robosky.structurehelpers.structure.pool.ElementRange;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;
import robosky.structurehelpers.structure.processor.WeightedChanceProcessor;

import java.util.*;
import java.util.function.Function;

public class Towers {

    public static final Map<BiomeConditional, Tower> BIOME_ENTRANCES = new HashMap<>();
    private static final Random RAND = new Random();
    private static final Identifier DEFAULT_LOOT_TABLE = new Identifier("battletowers", "default");
    private static final List<Identifier> DEFAULT_SPAWNERS = Arrays.asList(new Identifier("minecraft", "zombie"), new Identifier("minecraft", "skeleton"), new Identifier("minecraft", "spider"));
    private static final Map<Identifier, Floor> FLOOR_DATA = new HashMap<>();
    public static Tower DEFAULT_TOWER = null;

    private Towers() {
        // NO-OP
    }

    public static void register(Identifier id, Tower tower) {
        BIOME_ENTRANCES.put(tower.getBiomeConditional(), tower);

        // build processors
        ImmutableList<StructureProcessor> processors = collectProcessors(tower);

        StructurePool startPool = initializeFloorCollection(tower, tower.getEntrances(), processors, BattleTowers.id(tower.getName() + "_entrances"), new Identifier("empty"));
        initializeFloorCollection(tower, tower.getLayers(), processors, BattleTowers.id(tower.getName() + "_outlines"), BattleTowers.id(tower.getName() + "_tops"));
        initializeFloorCollection(tower, tower.getRoofs(), processors, BattleTowers.id(tower.getName() + "_tops"), new Identifier("empty"));
        initializeFloorCollection(tower, tower.getBottoms(), processors, BattleTowers.id(tower.getName() + "_bottoms"), new Identifier("empty"));

        tower.setStartPool(startPool);

        // register extra pools
        if (tower.getExtraPools() != null) {
            tower.getExtraPools().forEach(extraPool -> {
                List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements = new ArrayList<>();
                extraPool.elements().forEach((identifier, integer) -> elements.add(Pair.of(ExtendedSinglePoolElement.of(identifier, false, processors), integer)));

                registerPool(
                        extraPool.id(),
                        extraPool.terminator() == null ? new Identifier("empty") : extraPool.terminator(),
                        elements
                );
            });
        }
    }

    private static StructurePool initializeFloorCollection(Tower tower, FloorCollection collection, ImmutableList<StructureProcessor> processors, Identifier towerId, Identifier terminators) {
        List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements = new ArrayList<>();
        initializeFloors(tower, collection, elements, processors);
        return registerPool(towerId, terminators, elements);
    }

    private static ImmutableList<StructureProcessor> collectProcessors(Tower tower) {
        WeightedChanceProcessor.Builder builder = WeightedChanceProcessor.builder();
        tower.getProcessors().forEach((inputId, weightedOutputIds) -> {
            // input block (what to process)
            Block input = Registry.BLOCK.get(inputId);

            // for each output
            weightedOutputIds.forEach(weightedOutput -> {
                int weight = weightedOutput.getWeight();

                // add block if it is valid
                if (Registry.BLOCK.get(weightedOutput.getId()) != Blocks.AIR && weight > 0) {
                    builder.add(input, WeightedChanceProcessor.Entry.of(Registry.BLOCK.get(weightedOutput.getId()), weight));
                }
            });
        });

        return ImmutableList.<StructureProcessor>builder().add(builder.build()).build();
    }

    private static StructurePool registerPool(Identifier id, Identifier terminators, List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements) {
        return StructurePools.register(
                new StructurePool(
                        id,
                        terminators,
                        elements,
                        StructurePool.Projection.RIGID
                )
        );
    }

    private static void initializeFloors(Tower tower, FloorCollection floors, List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements, ImmutableList<StructureProcessor> processors) {
        floors.getFloors().forEach(floor -> {
            elements.add(Pair.of(ExtendedSinglePoolElement.of(floor.getId(), false, processors), 1));
            floors.applyDefaults(floor);
            FLOOR_DATA.put(floor.getId(), floor);

            // if max spawns or min spawns has been set, save data to tower
            // if max is 0, set to 100 (user did not define a value), otherwise set max to the highest between the min and max to avoid breaking the system
            if (floor.getMaxSpawns() != 0 || floor.getMinSpawns() != 0) {
                int max = Math.max(floor.getMinSpawns(), floor.getMaxSpawns());
                tower.addLimit(ElementRange.of(floor.getId(), floor.getMinSpawns(), max == 0 ? 100 : Math.max(floor.getMinSpawns(), floor.getMaxSpawns())));
            }
        });
    }

    /**
     * Returns an appropriate tower for the given {@link Biome}.
     * <p>
     * If there is no appropriate tower available, the default (stone) tower is returned.
     *
     * @param biome biome to check for an appropriate tower type in
     * @return an appropriate tower for the given biome, or the default (stone) tower if none exist
     */
    public static Tower getTowerFor(Biome biome) {
        // todo: consider all possible entrances instead of only the first
        for (Map.Entry<BiomeConditional, Tower> conditional : BIOME_ENTRANCES.entrySet()) {
            if (conditional.getKey().isValid(biome)) {
                return conditional.getValue();
            }
        }

        return DEFAULT_TOWER;
    }

    public static Identifier getSpawnerEntryFor(Identifier floorID) {
        if (FLOOR_DATA.containsKey(floorID)) {
            List<Identifier> spawnerEntries = FLOOR_DATA.get(floorID).getEntities();

            if (spawnerEntries == null) {
                spawnerEntries = new ArrayList<>();
                spawnerEntries.add(new Identifier("minecraft:zombie"));
            }

            if (!spawnerEntries.isEmpty()) {
                return spawnerEntries.get(RAND.nextInt(spawnerEntries.size()));
            } else {
                System.out.printf("[Battle Towers] floor %s's content provider has no valid spawners. Falling back to defaults.%n", floorID.toString());
            }
        } else {
            System.out.printf("[Battle Towers] floor %s doesn't have a registered content provider. Falling back to defaults.%n", floorID.toString());
        }

        return DEFAULT_SPAWNERS.get(RAND.nextInt(DEFAULT_SPAWNERS.size()));
    }

    public static Identifier getLootTableFor(Identifier floorID) {
        if (FLOOR_DATA.containsKey(floorID)) {
            List<Identifier> lootTables = FLOOR_DATA.get(floorID).getLootTables();

            if (lootTables == null) {
                lootTables = new ArrayList<>();
                lootTables.add(BattleTowers.id("default"));
            }

            if (!lootTables.isEmpty()) {
                return lootTables.get(RAND.nextInt(lootTables.size()));
            } else {
                System.out.printf("[Battle Towers] floor %s's content provider has no valid loot tables. Falling back to defaults.%n", floorID.toString());
            }
        } else {
            System.out.printf("[Battle Towers] floor %s doesn't have a registered content provider. Falling back to defaults.%n", floorID.toString());
        }

        return DEFAULT_LOOT_TABLE;
    }

    public static Floor getFloor(Identifier id) {
        return FLOOR_DATA.get(id);
    }
}
