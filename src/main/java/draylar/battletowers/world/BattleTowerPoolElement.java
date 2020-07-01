package draylar.battletowers.world;

import com.google.common.collect.ImmutableList;
import draylar.battletowers.entity.ContentDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
import net.minecraft.fluid.Fluids;
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

import java.util.Map;
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

        // TODO:
        // content deployers need to know whether to place chests/spawners/ladders so they can be used on ALL FLOORS.
        // this INCLUDES the spawner box at the top of the tower and anything extra.
        // the top of the tower does not have a content deployer (same as some floors), so no ladder appears

        // pos2 is the base position, so it accounts for the middle of the structure, but the y value stays at the bottom.
        // pos is the corner of each structure with the proper y value, but it is rotated.
        // we combine them to get the middle position of each floor

        String location = this.location().toString();
        BlockPos deployerPos = new BlockPos(pos2.getX(), pos.getY() + 1, pos2.getZ());

        // todo: don't actually think you can poll for changed blocks at this stage
        // some floors have an extra layer at the bottom, so account for this by moving position up by 1
        if(!world.getBlockState(deployerPos).isAir()) {
            deployerPos = deployerPos.up();
        }

        // some rooms want to double generate for some reason e_e
        if(!world.getBlockState(deployerPos.down()).getBlock().equals(BattleTowerBlocks.CONTENT_DEPLOYER)) {

            // don't place content deployers in entrances
            if(!location.contains("entrance")) {
                world.setBlockState(deployerPos, BattleTowerBlocks.CONTENT_DEPLOYER.getDefaultState(), 3);
                ContentDeployerBlockEntity contentDeployer = (ContentDeployerBlockEntity) world.getBlockEntity(deployerPos);
                contentDeployer.setFloorID(new Identifier(location));

                if (location.contains("top")) {
                    contentDeployer.setPlaceLadders(true);
                    contentDeployer.setPlaceBossLock(true);
                } else if (location.contains("bottom")) {

                } else if (location.contains("lookout")) {
                    contentDeployer.setPlaceLadders(true);
                } else {
                    contentDeployer.setPlaceChests(true);
                    contentDeployer.setPlaceLadders(true);
                    contentDeployer.setPlaceSpawners(true);
                }
            }
        }

        return generate;
    }
}
