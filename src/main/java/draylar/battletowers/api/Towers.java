package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;
import robosky.structurehelpers.structure.processor.RandomChanceProcessor;

import java.util.HashMap;
import java.util.Map;

public class Towers {

    public static final ImmutableList<StructureProcessor> STONE_PROCESSORS = ImmutableList
            .<StructureProcessor>builder()
            .add(RandomChanceProcessor
                    .builder()
                    .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.CRACKED_STONE_BRICKS, 1))
                    .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.MOSSY_STONE_BRICKS, 1))
                    .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.STONE_BRICKS, 1))
                    .build()
            )
            .build();

    public static final Tower STONE = new Tower(
            "stone",
            STONE_PROCESSORS,
            ImmutableList.of(
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/blacksmith"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/default"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/lake"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/library"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/lookout"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/mineshaft"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/original"), false, STONE_PROCESSORS), 1),
                    Pair.of(new ExtendedSinglePoolElement(BattleTowers.id("stone/plains"), false, STONE_PROCESSORS), 1)
            )
    );

//    public static final Tower OCEANIC = new Tower("oceanic", ImmutableList
//            .<StructureProcessor>builder()
//            .add(RandomChanceProcessor
//                    .builder()
//                    .add(Blocks.PRISMARINE, RandomChanceProcessor.Entry.of(Blocks.PRISMARINE.getDefaultState(), 1))
//                    .add(Blocks.PRISMARINE, RandomChanceProcessor.Entry.of(Blocks.PRISMARINE_BRICKS.getDefaultState(), 1))
//                    .add(Blocks.PRISMARINE, RandomChanceProcessor.Entry.of(Blocks.DARK_PRISMARINE.getDefaultState(), 1))
//                    .build())
//            .add(RandomChanceProcessor
//                    .builder()
//                    .add(
//                            Blocks.PRISMARINE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM),
//                            RandomChanceProcessor.Entry.of(Blocks.PRISMARINE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM), 1))
//                    .build())
//            .add(RandomChanceProcessor
//                    .builder()
//                    .add(
//                            Blocks.PRISMARINE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP),
//                            RandomChanceProcessor.Entry.of(Blocks.PRISMARINE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP), 1))
//                    .build())
//            .build());

    public static final Map<BiomeConditional, Tower> BIOME_ENTRANCES = new HashMap<>();

    public static void init() {
//        BIOME_ENTRANCES.put(
//                biome -> biome.getCategory().equals(Biome.Category.NETHER),
//                NETHER
//        );
//
//        BIOME_ENTRANCES.put(
//                biome -> biome.getCategory().equals(Biome.Category.OCEAN),
//                OCEANIC
//        );
    }

    public static boolean hasCustomTower(Biome biome) {
        for(BiomeConditional conditional : BIOME_ENTRANCES.keySet()) {
            if(conditional.isValid(biome)) {
                return true;
            }
        }

        return false;
    }

    private Towers() {
        // NO-OP
    }

    public static Tower getEntranceFor(Biome biome) {
        for(Map.Entry<BiomeConditional, Tower> conditional : BIOME_ENTRANCES.entrySet()) {
            if(conditional.getKey().isValid(biome)) {
                return conditional.getValue();
            }
        }

        return STONE;
    }
}
