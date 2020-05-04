package draylar.battletowers.world;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class BattleTowerStructureStart extends StructureStart {

    private static final Identifier ENTRANCES = BattleTowers.id("entrances");
    private static final Identifier OUTLINES = BattleTowers.id("outlines");

    static {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        ENTRANCES,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement(BattleTowers.id("default_entrance").toString()), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        OUTLINES,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new SinglePoolElement(BattleTowers.id("default_outline").toString()), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

    public BattleTowerStructureStart(StructureFeature<?> feature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
        super(feature, chunkX, chunkZ, box, references, l);
    }

    @Override
    public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome) {
        StructurePoolBasedGenerator.addPieces(ENTRANCES, 10, BattleTowerPiece::new, chunkGenerator, structureManager, new BlockPos(x * 16, 150, z * 16), children, random);
    }
}
