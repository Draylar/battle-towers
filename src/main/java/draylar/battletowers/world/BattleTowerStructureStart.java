package draylar.battletowers.world;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import robosky.structurehelpers.iface.ExtendedStructurePoolBasedGeneratorData;
import robosky.structurehelpers.structure.ExtendedStructures;
import robosky.structurehelpers.structure.pool.ElementRange;

import java.util.Arrays;

public class BattleTowerStructureStart extends StructureStart {

    public BattleTowerStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome, FeatureConfig featureConfig) {
        Tower tower = Towers.getEntranceFor(biome);

        if (tower != null) {
//            StructurePoolBasedGenerator.addPieces(
//                    new Identifier("battletowers", tower.getName() + "_entrances"),
//                    8,
//                    BattleTowerPiece::new,
//                    chunkGenerator,
//                    structureManager,
//                    new BlockPos(x * 16, 0, z * 16),
//                    children,
//                    random,
//                    true,
//                    true
//            );

            ExtendedStructurePoolBasedGeneratorData data = (ExtendedStructurePoolBasedGeneratorData) children;
            data.putElementMinMax(new Identifier("minecraft:empty"), ElementRange.of(new Identifier("minecraft:empty"), 0, 1));

            StructurePoolBasedGenerator.addPieces(
                    new Identifier("battletowers", tower.getName() + "_entrances"),
                    8,
                    BattleTowerPiece::new,
                    chunkGenerator,
                    structureManager,
                    new BlockPos(x * 16, 0, z * 16),
                    data,
                    random,
                    true,
                    true
            );

//            ExtendedStructures.addPieces(
//                    Arrays.asList(ElementRange.of(BattleTowers.id("stone/lookout"), 0, 1)),
//                    0,
//                    0,
//                    new Identifier("battletowers", tower.getName() + "_entrances"),
//                    8,
//                    BattleTowerPiece::new,
//                    chunkGenerator,
//                    structureManager,
//                    new BlockPos(x * 16, 0, z * 16),
//                    random,
//                    true,
//                    true
//            );

            setBoundingBoxFromChildren();
        }
    }
}
