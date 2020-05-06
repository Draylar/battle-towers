package draylar.battletowers.world;

import draylar.battletowers.api.Towers;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerStructureStart extends StructureStart {

    public BattleTowerStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void init(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        if(Towers.hasCustomTower(biome)) {
            StructurePoolBasedGenerator.addPieces(Towers.getEntranceFor(biome).getEntrancePool(), 10, BattleTowerPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 150, z * 16), children, random, true, true);
        } else {
            StructurePoolBasedGenerator.addPieces(Towers.STONE.getEntrancePool(), 10, BattleTowerPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 0, z * 16), children, random, true, true);
        }

        setBoundingBoxFromChildren();
    }
}
