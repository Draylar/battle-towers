package com.github.draylar.battletowers.common.world;

import com.github.draylar.battletowers.common.Structures;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class BattleTowerFeature extends AbstractTempleFeature<DefaultFeatureConfig> {
    public BattleTowerFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    @Override
    public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkZ, int i, Biome biome) {
        return true;
    }

    @Override
    public StructureStartFactory getStructureStartFactory() {
        return BattleTowerStructureStart::new;
    }

    @Override
    public String getName() {
        return "battle-tower";
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
            DefaultFeatureConfig defaultFeatureConfig = chunkGenerator.getStructureConfig(biome, Structures.BATTLETOWERFEATURE);

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
