package com.github.draylar.battletowers.world;

import com.github.draylar.battletowers.registry.BattleTowerStructures;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerFeature extends AbstractTempleFeature<DefaultFeatureConfig> {

    public BattleTowerFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return BattleTowerStructureStart::new;
    }

    @Override
    public String getName() {
        return "battletower";
    }

    @Override
    public int getRadius() {
        return 8;
    }

    public static class BattleTowerStructureStart extends StructureStart {

        public BattleTowerStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
            super(feature, chunkX, chunkZ, box, references, l);
        }

        @Override
        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome) {
            DefaultFeatureConfig defaultFeatureConfig = chunkGenerator.getStructureConfig(biome, BattleTowerStructures.BATTLE_TOWER_FEATURE);

            int x = chunkX * 16;
            int z = chunkZ * 16;

            BlockPos startingPos = new BlockPos(x, 0, z);

            // randomized rotation breaks a LOT of stuff so we're removing it for now
            BlockRotation rotation = BlockRotation.NONE;

            BattleTowerGenerator.addParts(structureManager, startingPos, rotation, this.children, this.random, defaultFeatureConfig);
            this.setBoundingBoxFromChildren();
        }
    }
}
