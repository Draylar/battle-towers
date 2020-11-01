package draylar.battletowers.world;

import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.pool.ExtendedStructurePoolFeatureConfig;

public class BattleTowerStructureStart extends MarginedStructureStart<DefaultFeatureConfig> {

    public BattleTowerStructureStart(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
        super(structureFeature, i, j, blockBox, k, l);
    }

    @Override
    public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
        Tower tower = Towers.getTowerFor(biome);

        if (tower != null) {
            StructurePoolBasedGenerator.method_30419(
                    registryManager,
                    new ExtendedStructurePoolFeatureConfig(tower.getLimits(), 0, 150, tower::getStartPool, 8),
                    BattleTowerPiece::new,
                    chunkGenerator,
                    manager,
                    new BlockPos(chunkX * 16, 0, chunkZ * 16),
                    this.children,
                    this.random,
                    true,
                    true
            );

            setBoundingBoxFromChildren();
        }
    }
}
