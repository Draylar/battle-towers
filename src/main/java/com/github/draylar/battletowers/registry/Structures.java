package com.github.draylar.battletowers.registry;

import com.github.draylar.battletowers.BattleTowers;
import com.github.draylar.battletowers.world.BattleTowerFeature;
import com.github.draylar.battletowers.world.BattleTowerGenerator;
import net.minecraft.structure.StructurePieceType;
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
    public static StructureFeature<DefaultFeatureConfig> battleTowerFeature = registerFeature("battle_tower", new BattleTowerFeature());
    public static StructureFeature<?> battleTowerStructure = registerStructureFeature("battle_tower", battleTowerFeature);

    public static void init() {
        // add our structure to the structure list
        Feature.STRUCTURES.put("Battle Tower", battleTowerFeature);

        // register our structure in overworld biomes
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addStructureFeature(battleTowerFeature, new DefaultFeatureConfig());
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, Biome.configureFeature(battleTowerFeature, new DefaultFeatureConfig(), Decorator.CHANCE_HEIGHTMAP, new ChanceDecoratorConfig(BattleTowers.CONFIG.structureRarity)));
            }
        }
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String string_1, F feature_1) {
        return Registry.register(Registry.FEATURE, string_1, feature_1);
    }

    private static StructureFeature<?> registerStructureFeature(String string_1, StructureFeature<?> structureFeature_1) {
        return Registry.register(Registry.STRUCTURE_FEATURE, string_1.toLowerCase(Locale.ROOT), structureFeature_1);
    }
}
