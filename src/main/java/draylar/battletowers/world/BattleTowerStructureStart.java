package draylar.battletowers.world;

import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.structure.ExtendedStructures;

import java.util.List;

public class BattleTowerStructureStart extends StructureStart<DefaultFeatureConfig> {

    public BattleTowerStructureStart(StructureFeature<DefaultFeatureConfig> feature, int chunkX, int chunkZ, BlockBox box, int references, long seed) {
        super(feature, chunkX, chunkZ, box, references, seed);
    }

    @Override
    public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, DefaultFeatureConfig featureConfig) {
        Tower tower = Towers.getEntranceFor(biome);

        if (tower != null) {
            List<PoolStructurePiece> pieces = ExtendedStructures.addPieces(
                    tower.getLimits(),
                    0,
                    150,
                    new Identifier("battletowers", tower.getName() + "_entrances"),
                    8,
                    BattleTowerPiece::new,
                    chunkGenerator,
                    structureManager,
                    new BlockPos(x * 16, 0, z * 16),
                    random,
                    true,
                    true
            );

            this.children.addAll(pieces);
            setBoundingBoxFromChildren();
        }
    }
}
