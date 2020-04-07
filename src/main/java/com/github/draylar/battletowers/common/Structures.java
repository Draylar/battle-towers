package com.github.draylar.battletowers.common;

import com.github.draylar.battletowers.BattleTowers;
import com.github.draylar.battletowers.common.world.BattleTowerFeature;
import com.github.draylar.battletowers.common.world.BattleTowerGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Locale;

public class Structures {
    public static final StructurePieceType structurePieceType = Registry.register(Registry.STRUCTURE_PIECE, "battletower_layer", BattleTowerGenerator.Piece::new);
    public static StructureFeature<DefaultFeatureConfig> BATTLETOWERFEATURE = Registry.register(Registry.STRUCTURE_FEATURE,
            new Identifier("battletowers", "battletower"), new BattleTowerFeature());
    //public static StructureFeature<?> battleTowerStructure =  registerStructureFeature("battle_tower", battleTowerFeature);

    public static void init() {
        // add our structure to the structure list
        Feature.STRUCTURES.put("Battle_Tower", BATTLETOWERFEATURE);

        // register our structure in overworld biomes
        //ffrann: trying to copy someone's code let's see if it'll work
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addStructureFeature(Structures.BATTLETOWERFEATURE.configure(FeatureConfig.DEFAULT));
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Structures.BATTLETOWERFEATURE
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.CHANCE_HEIGHTMAP.configure(new ChanceDecoratorConfig(BattleTowers.CONFIG.structureRarity))));

            }
        }
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String string_1, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier("battle-towers", string_1), feature);
    }


    private static StructureFeature<?> registerStructureFeature(String string, StructureFeature<?> structureFeature) {
        return Registry.register(Registry.STRUCTURE_FEATURE, string.toLowerCase(Locale.ROOT), structureFeature);
    }


}
