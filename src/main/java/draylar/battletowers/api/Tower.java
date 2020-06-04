package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.Identifier;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.List;

public class Tower {

    private final Identifier entrancePool;
    private final Identifier outlinePool;
    private final Identifier topPool;
    private final Identifier bottomPool;

    public Tower(String id, ImmutableList<StructureProcessor> processors, List<Pair<StructurePoolElement, Integer>> floors) {
        this.entrancePool = BattleTowers.id(id + "_entrances");
        this.outlinePool = BattleTowers.id(id + "_outlines");
        this.topPool = BattleTowers.id(id + "_tops");
        this.bottomPool = BattleTowers.id(id + "_bottoms");

        // register entrance with no terminator
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        entrancePool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/entrance"), false, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        // register top pool terminator
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        topPool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/top"), false, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        // register layer pool with top pool terminator
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        outlinePool,
                        topPool,
                        floors,
                        StructurePool.Projection.RIGID
                )
        );

        // register bottom pool
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        bottomPool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/bottom"), true, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

    public Identifier getEntrancePool() {
        return entrancePool;
    }

    public Identifier getOutlinePool() {
        return outlinePool;
    }

    public Identifier getTopPool() {
        return topPool;
    }
}
