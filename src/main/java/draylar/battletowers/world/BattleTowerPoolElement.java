package draylar.battletowers.world;

import com.google.common.collect.ImmutableList;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Floor;
import draylar.battletowers.entity.block.ContentDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
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

    public BattleTowerPoolElement(Identifier location, boolean overwriteFluids, ImmutableList<StructureProcessor> processors) {
        super(location, overwriteFluids, processors);
    }

    @Override
    public boolean generate(StructureManager manager, ServerWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, BlockPos pos, BlockPos pos2, BlockRotation rotation, BlockBox box, Random rand, boolean b) {
        boolean generate = super.generate(manager, world, accessor, generator, pos, pos2, rotation, box, rand, b);

        // pos2 is the base position, so it accounts for the middle of the structure, but the y value stays at the bottom.
        // pos is the corner of each structure with the proper y value, but it is rotated.
        // we combine them to get the middle position of each floor

        Floor floor = Towers.getFloor(this.location());

        if(floor != null) {
            BlockPos deployerPos = new BlockPos(pos2.getX(), pos.getY() + 1, pos2.getZ());

            // some rooms call generate twice, so check for stacking content deployers
            if (!world.getBlockState(deployerPos.down()).getBlock().equals(BattleTowerBlocks.CONTENT_DEPLOYER)) {
                if(floor.placeContentDeployer()) {
                    world.setBlockState(deployerPos, BattleTowerBlocks.CONTENT_DEPLOYER.getDefaultState(), 3);
                    ContentDeployerBlockEntity contentDeployer = (ContentDeployerBlockEntity) world.getBlockEntity(deployerPos);

                    // apply floor data to content deployer
                    if (contentDeployer != null) {
                        contentDeployer.apply(floor);
                    }
                }
            }

//            // special-case mushroom lanterns
//            if(this.location().toString().equals("battletowers:mushroom/top")) {
//                // spawn 12 lanterns
//                for(int i = 0; i < 12; i++) {
//                    int x = rand.nextInt(24) - 48;
//                    int z = rand.nextInt(24) - 48;
//
//                    BlockPos testPos = deployerPos.add(x, 0, z);
//                    // test up to 10 blocks down for placement
//                    for(int y = 0; y < 10; y++) {
//                        BlockPos potentialPlacementPosition = testPos.down(y);
////
////                        // check if position is valid
////                        if(world.getBlockState(potentialPlacementPosition).isAir() && !world.getBlockState(potentialPlacementPosition.up()).isAir()) {
//                            // place chains
//                            int chainLength = rand.nextInt(24);
//                            for(int chainIndex = 0; chainIndex < chainLength; chainIndex++) {
//                                BlockPos placementPosition = potentialPlacementPosition.down(chainLength);
//                                world.setBlockState(placementPosition, Blocks.CHAIN.getDefaultState(), 3);
//                            }
//
//                            world.setBlockState(potentialPlacementPosition.down(chainLength), Blocks.SHROOMLIGHT.getDefaultState(), 3);
//
//                            break;
////                        }
//                    }
//                }
//            }
        }

        return generate;
    }
}
