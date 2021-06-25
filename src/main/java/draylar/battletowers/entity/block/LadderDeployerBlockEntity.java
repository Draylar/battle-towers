package draylar.battletowers.entity.block;

import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/**
 * Responsible for deploying ladders vertically on a wall after a tower has been generated.
 * If {@link LadderDeployerBlockEntity#placeLadder} is true and this entity deploys ladders, it is replaced by another ladder instead of air.
 */
public class LadderDeployerBlockEntity extends BlockEntity {

    private boolean placeLadder = false;

    public LadderDeployerBlockEntity(BlockPos pos, BlockState state) {
        super(BattleTowerEntities.LADDER_DEPLOYER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, LadderDeployerBlockEntity blockEntity) {
        if (world != null) {
            Direction wallDirection = null;

            // find a solid wall we can place ladders on
            for (Direction direction : Direction.values()) {
                if (direction.getAxis() != Direction.Axis.Y) {
                    BlockState offsetState = world.getBlockState(pos.offset(direction));

                    if (offsetState.isSideSolidFullSquare(world, pos.offset(direction), direction.getOpposite())) {
                        wallDirection = direction;
                        break;
                    }
                }
            }

            // if we've found a wall, place ladders 8 deep
            if (wallDirection != null) {
                world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());

                for (int i = 0; i < 8; i++) {
                    BlockPos downPos = pos.down(i);
                    BlockState targetOverrideState = world.getBlockState(downPos);

                    // if we're going to replace a ladder deployer, don't, and tell that deployer it needs to replace itself with a ladder when done
                    if (targetOverrideState.getBlock().equals(BattleTowerBlocks.LADDER_DEPLOYER)) {
                        LadderDeployerBlockEntity be = (LadderDeployerBlockEntity) world.getBlockEntity(downPos);
                        be.toggleLadderPlacement(true);
                    } else {
                        // place ladder
                        world.setBlockState(downPos, Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, wallDirection.getOpposite()).with(LadderBlock.WATERLOGGED, targetOverrideState.getBlock().equals(Blocks.WATER)));
                    }
                }

            } else {
                System.out.printf("Ladder Deployer couldn't deploy ladders in a Battle Tower at %s. Was the area overwritten?%n", pos.toString());

            }
            if (blockEntity.placeLadder) {
                world.setBlockState(pos, world.getBlockState(pos.up()));
            } else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public void toggleLadderPlacement(boolean placeLadder) {
        this.placeLadder = placeLadder;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putBoolean("PlaceLadder", placeLadder);
        return super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        this.placeLadder = tag.getBoolean("PlaceLadder");
        super.readNbt(tag);
    }
}
