package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import robosky.structurehelpers.structure.processor.RandomChanceProcessor;

import java.util.HashMap;
import java.util.Map;

public class Towers {

    public static final Tower STONE = new Tower(
            "stone",
            ImmutableList
                    .<StructureProcessor>builder()
                    .add(RandomChanceProcessor
                            .builder()
                            .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.CRACKED_STONE_BRICKS, 1))
                            .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.MOSSY_STONE_BRICKS, 1))
                            .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.STONE_BRICKS, 1))
                            .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.STONE, 1))
                            .add(Blocks.STONE_BRICKS.getDefaultState(), RandomChanceProcessor.Entry.of(Blocks.ANDESITE, 1))
                            .build()
                    )
                    .build()
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
