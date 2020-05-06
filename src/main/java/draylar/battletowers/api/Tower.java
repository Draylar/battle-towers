package draylar.battletowers.api;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import draylar.battletowers.BattleTowers;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.Identifier;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.List;

public class Tower {

    private final Identifier entrancePool;
    private final Identifier outlinePool;
    private final Identifier topPool;
    private ImmutableList<StructureProcessor> processors;

    public Tower(String id) {
        this.entrancePool = BattleTowers.id(id + "_entrances");
        this.outlinePool = BattleTowers.id(id +"_outlines");
        this.topPool = BattleTowers.id(id +"_tops");
        this.processors = ImmutableList.of();

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        entrancePool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/default_entrance"), false, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        outlinePool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/default_outline"), false, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        topPool,
                        new Identifier("empty"),
                        ImmutableList.of(
                                Pair.of(new ExtendedSinglePoolElement(BattleTowers.id(id + "/default_top"), false, processors), 1)
                        ),
                        StructurePool.Projection.RIGID
                )
        );
    }

    public Tower(String id, ImmutableList<StructureProcessor> processors) {
        this(id);
        this.processors = processors;
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

    public List<StructureProcessor> getProcessors() {
        return processors;
    }
}
