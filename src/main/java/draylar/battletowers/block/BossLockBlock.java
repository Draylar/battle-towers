package draylar.battletowers.block;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.TowerGuardEntity;
import draylar.battletowers.registry.BattleTowerEntities;
import draylar.battletowers.registry.BattleTowerItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BossLockBlock extends Block {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 15, 16);

    public BossLockBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().nonOpaque().build());
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerFacing());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int requiredKeys = BattleTowers.CONFIG.requiredKeys;
        Item requiredItem = Registry.ITEM.get(new Identifier(BattleTowers.CONFIG.requiredItem));

        if (requiredItem == Items.AIR) {
            requiredItem = BattleTowerItems.BOSS_KEY;
        }

        ItemStack stack = player.inventory.main.get(player.inventory.selectedSlot);

        if (stack.getItem().equals(requiredItem) && stack.getCount() >= requiredKeys) {
            if(!world.isClient) {
                stack.setCount(stack.getCount() - requiredKeys);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());

                TowerGuardEntity towerGuardEntity = new TowerGuardEntity(BattleTowerEntities.TOWER_GUARD, world);
                towerGuardEntity.updateTrackedPosition(pos.getX(), pos.getY(), pos.getZ());
                towerGuardEntity.updatePosition(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(towerGuardEntity);

            } else {
                playSpawnSound(pos);
            }
            return ActionResult.PASS;
        } else {
            if (world.isClient) {
                playDenySound(pos);
                return ActionResult.FAIL;
            }
        }

        return ActionResult.PASS;
    }

    @Environment(EnvType.CLIENT)
    private void playDenySound(BlockPos pos) {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 1, 1, false);
    }

    @Environment(EnvType.CLIENT)
    private void playSpawnSound(BlockPos pos) {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1, 1, false);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
