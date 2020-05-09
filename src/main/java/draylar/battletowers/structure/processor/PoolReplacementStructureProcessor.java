package draylar.battletowers.structure.processor;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.JigsawReplacementStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class PoolReplacementStructureProcessor extends StructureProcessor {

    public static final PoolReplacementStructureProcessor INSTANCE = new PoolReplacementStructureProcessor();

    @Override
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        return null;
    }

    @Override
    protected StructureProcessorType getType() {
        return null;
    }

    @Override
    protected <T> Dynamic<T> rawToDynamic(DynamicOps<T> dynamicOps) {
        return null;
    }
}
