package draylar.battletowers.world;

import com.google.common.collect.ImmutableList;
import draylar.battletowers.entity.ContentDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.Random;

public class BattleTowerPoolElement extends ExtendedSinglePoolElement {

    public BattleTowerPoolElement(Identifier location) {
        super(location);
    }

    public BattleTowerPoolElement(Identifier location, boolean overwriteFluids, ImmutableList<StructureProcessor> processors) {
        super(location, overwriteFluids, processors);
    }

    @Override
    public boolean generate(StructureManager manager, ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, BlockPos pos, BlockPos pos2, BlockRotation rotation, BlockBox box, Random rand, boolean b) {
        boolean generate = super.generate(manager, world, accessor, generator, pos, pos2, rotation, box, rand, b);

        // pos2 is the base position, so it accounts for the middle of the structure, but the y value stays at the bottom.
        // pos is the corner of each structure with the proper y value, but it is rotated.
        // we combine them to get the middle position of each floor

        String location = this.location().toString();
        BlockPos deployerPos = new BlockPos(pos2.getX(), pos.getY() + 1, pos2.getZ());

        // some floors have an extra layer at the bottom, so account for this by moving position up by 1
        if(!world.getBlockState(deployerPos).isAir()) {
            deployerPos = deployerPos.up();
        }

        // some rooms want to double generate for some reason e_e
        if(!world.getBlockState(deployerPos.down()).getBlock().equals(BattleTowerBlocks.CONTENT_DEPLOYER)) {
            world.setBlockState(deployerPos, BattleTowerBlocks.CONTENT_DEPLOYER.getDefaultState(), 3);
            ContentDeployerBlockEntity contentDeployer = (ContentDeployerBlockEntity) world.getBlockEntity(deployerPos);
            contentDeployer.setFloorID(new Identifier(location));
        }

        return generate;
    }
}
