package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.world.BattleTowerFeature;
import draylar.battletowers.world.BattleTowerPiece;
import net.earthcomputer.libstructure.LibStructure;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.*;

import java.util.Locale;

public class BattleTowerStructures {

    public static final StructurePieceType PIECE = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("piece"), BattleTowerPiece::new);

    public static void init() {
        BattleTowerFeature battleTowerFeature = new BattleTowerFeature();
        DefaultFeatureConfig battleTowerConfig = DefaultFeatureConfig.INSTANCE;
        ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> configuredBattleTowerFeature =
                battleTowerFeature.configure(battleTowerConfig);

        LibStructure.registerSurfaceAdjustingStructure(
                BattleTowers.id("battletower"),
                battleTowerFeature,
                GenerationStep.Feature.SURFACE_STRUCTURES,
                new StructureConfig(32, 8, 185815),
                configuredBattleTowerFeature
        );

        // register our structure in overworld biomes
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() != Biome.Category.RIVER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addStructureFeature(configuredBattleTowerFeature);
//                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, BattleTowerStructures.BATTLE_TOWER_FEATURE
//                        .configure(FeatureConfig.DEFAULT)
//                        .createDecoratedFeature(Decorator.NOPE.configure(NopeDecoratorConfig.DEFAULT)));
            }
        }
    }

    private static <Config extends FeatureConfig, F extends Feature<Config>> F registerFeature(String name, F feature) {
        return Registry.register(Registry.FEATURE, BattleTowers.id(name), feature);
    }

    private static <Config extends FeatureConfig> StructureFeature<Config> registerStructureFeature(String name, StructureFeature<Config> structureFeature) {
        return Registry.register(Registry.STRUCTURE_FEATURE, BattleTowers.id(name), structureFeature);
    }
}
