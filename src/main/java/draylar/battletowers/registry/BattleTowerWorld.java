package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.tower.Tower;
import draylar.battletowers.world.BattleTowerPiece;
import draylar.battletowers.world.BattleTowerStructure;
import draylar.staticcontent.StaticContent;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Arrays;
import java.util.List;

public class BattleTowerWorld {

    public static final BattleTowerStructure BATTLE_TOWER = new BattleTowerStructure();
    public static final StructurePieceType PIECE = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("piece"), BattleTowerPiece::new);
    public static final ConfiguredStructureFeature<DefaultFeatureConfig, ? extends StructureFeature<DefaultFeatureConfig>> CONFIGURED_BATTLE_TOWER = BATTLE_TOWER.configure(DefaultFeatureConfig.DEFAULT);

    private static final List<Biome.Category> blacklistedCategories = Arrays.asList(Biome.Category.RIVER, Biome.Category.THEEND, Biome.Category.NONE, Biome.Category.NETHER, Biome.Category.OCEAN);

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, BattleTowers.id("configured_tower"), CONFIGURED_BATTLE_TOWER);
        // note to future self: DO NOT CHANGE REGISTRY ID

        // Register Battle Tower Structure
        FabricStructureBuilder
                .create(BattleTowers.id("battletower"), BATTLE_TOWER)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(BattleTowers.CONFIG.towerSpacing, BattleTowers.CONFIG.towerSeparation, 32132815)
                .adjustsSurface() // might need to remove
                .register();

        // Add BT Structure to biomes
        BiomeModifications
                .create(BattleTowers.id("battletower"))
                .add(
                        ModificationPhase.ADDITIONS,
                        context -> !blacklistedCategories.contains(context.getBiome().getCategory()),
                        context -> context.getGenerationSettings().addBuiltInStructure(CONFIGURED_BATTLE_TOWER));

        // Register pools
        StaticContent.load(BattleTowers.id("towers"), Tower.class);
    }
}
