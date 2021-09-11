package draylar.battletowers.world;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.pool.ExtendedStructurePoolFeatureConfig;

public class BattleTowerStructure extends StructureFeature<DefaultFeatureConfig> {

    public BattleTowerStructure() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom random, ChunkPos pos, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig config, HeightLimitView world) {
        int height = chunkGenerator.getHeight(chunkPos.x << 4, chunkPos.z << 4, Heightmap.Type.WORLD_SURFACE_WG, world);
        return height < BattleTowers.CONFIG.maxHeight;
    }


    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, ChunkPos chunkPos, int i, long l) {
            super(structureFeature, chunkPos, i, l);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos pos, Biome biome, DefaultFeatureConfig config, HeightLimitView world) {
            Tower tower = Towers.getTowerFor(biome);

            if (tower != null) {
                StructurePoolBasedGenerator.generate(
                        registryManager,
                        new ExtendedStructurePoolFeatureConfig(tower.getLimits(), 0, 150, tower::getStartPool, 8),
                        BattleTowerPiece::new,
                        chunkGenerator,
                        manager,
                        new BlockPos(pos.x * 16, 0, pos.z * 16),
                        this,
                        this.random,
                        true,
                        true,
                        world
                );

                setBoundingBoxFromChildren();
            }
        }
    }
}
