package com.github.draylar.battleTowers.world;

import com.github.draylar.battleTowers.common.Structures;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class BattleTowerFeature extends AbstractTempleFeature<DefaultFeatureConfig>
{
    public BattleTowerFeature()
    {
        super(DefaultFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier()
    {
        return 14357618;
    }

    @Override
    public boolean shouldStartAt(ChunkGenerator<?> chunkGenerator_1, Random random_1, int int_1, int int_2)
    {
        return true;
    }

    @Override
    public StructureStartFactory getStructureStartFactory()
    {
        return BattleTowerStructureStart::new;
    }

    @Override
    public String getName()
    {
        return "battle-tower";
    }

    @Override
    public int getRadius()
    {
        return 8;
    }

    public static class BattleTowerStructureStart extends StructureStart
    {

        public BattleTowerStructureStart(StructureFeature<?> structureFeature_1, int int_1, int int_2, Biome biome_1, MutableIntBoundingBox mutableIntBoundingBox_1, int int_3, long long_1)
        {
            super(structureFeature_1, int_1, int_2, biome_1, mutableIntBoundingBox_1, int_3, long_1);
        }

        @Override
        public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int chunkX, int chunkZ, Biome biome)
        {
            DefaultFeatureConfig defaultFeatureConfig = chunkGenerator.getStructureConfig(biome, Structures.battleTowerFeature);

            int x = chunkX * 16;
            int z = chunkZ * 16;

            BlockPos startingPos = new BlockPos(x, 0, z);


            // randomized rotation breaks a LOT of stuff so we're removing it for now
            Rotation rotation = Rotation.ROT_0;

            BattleTowerGenerator.addParts(structureManager, startingPos, rotation, this.children, this.random, defaultFeatureConfig);
            this.setBoundingBoxFromChildren();
        }
    }
}
