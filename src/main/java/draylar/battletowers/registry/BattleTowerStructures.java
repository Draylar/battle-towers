package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.world.BattleTowerFeature;
import draylar.battletowers.world.BattleTowerPiece;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerStructures {

    public static final BattleTowerFeature FEATURE = new BattleTowerFeature();
    public static final StructurePieceType PIECE = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("piece"), BattleTowerPiece::new);
    public static ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> CONFIGURED = FEATURE.configure(DefaultFeatureConfig.DEFAULT);

    public static void init() {
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, BattleTowers.id("configured_tower"), CONFIGURED);
        // note to future self: DO NOT CHANGE REGISTRY ID

        FabricStructureBuilder
                .create(BattleTowers.id("battletower"), FEATURE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(BattleTowers.CONFIG.towerSpacing, BattleTowers.CONFIG.towerSeparation, 185815)
                .register();
    }
}
