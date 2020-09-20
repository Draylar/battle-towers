package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.world.BattleTowerFeature;
import draylar.battletowers.world.BattleTowerPiece;
import net.earthcomputer.libstructure.LibStructure;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerStructures {

    public static final StructurePieceType PIECE = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("piece"), BattleTowerPiece::new);
    public static ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> CONFIGURED_STRUCTURE_FEATURE;

    public static void init() {
        BattleTowerFeature battleTowerFeature = new BattleTowerFeature();
        CONFIGURED_STRUCTURE_FEATURE = battleTowerFeature.configure(DefaultFeatureConfig.DEFAULT);
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, BattleTowers.id("configured_tower"), CONFIGURED_STRUCTURE_FEATURE);
        // note to future self: DO NOT CHANGE REGISTRY ID

        LibStructure.registerStructure(
                BattleTowers.id("battletower"),
                battleTowerFeature,
                GenerationStep.Feature.SURFACE_STRUCTURES,
                new StructureConfig(BattleTowers.CONFIG.towerSpacing, BattleTowers.CONFIG.towerSeparation, 185815),
                CONFIGURED_STRUCTURE_FEATURE
        );
    }
}
