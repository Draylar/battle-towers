package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.world.BattleTowerFeature;
import draylar.battletowers.world.old.BattleTowerGenerator;
import draylar.battletowers.world.BattleTowerPiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Locale;

public class BattleTowerStructures {

    public static final StructurePieceType structurePieceType = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("battletower_layer"), BattleTowerGenerator.Piece::new);
    public static final StructureFeature<DefaultFeatureConfig> BATTLE_TOWER_FEATURE = registerFeature("battletower", new BattleTowerFeature());
    public static final StructureFeature<DefaultFeatureConfig> BATTLE_TOWER_STRUCTURE =  registerStructureFeature("battletower", BATTLE_TOWER_FEATURE);
    public static final StructurePieceType PIECE = Registry.register(Registry.STRUCTURE_PIECE, BattleTowers.id("piece"), BattleTowerPiece::new);

    public static void init() {
        // add our structure to the structure list
        Feature.STRUCTURES.put("BattleTower".toLowerCase(Locale.ROOT), BATTLE_TOWER_STRUCTURE);

        // register our structure in overworld biomes
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                biome.addStructureFeature(BattleTowerStructures.BATTLE_TOWER_STRUCTURE.configure(FeatureConfig.DEFAULT));
                biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, BattleTowerStructures.BATTLE_TOWER_FEATURE
                        .configure(FeatureConfig.DEFAULT)
                        .createDecoratedFeature(Decorator.NOPE.configure(NopeDecoratorConfig.DEFAULT)));
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
