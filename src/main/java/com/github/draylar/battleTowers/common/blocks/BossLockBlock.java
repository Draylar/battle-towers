package com.github.draylar.battleTowers.common.blocks;

import com.github.draylar.battleTowers.BattleTowers;
import com.github.draylar.battleTowers.common.Entities;
import com.github.draylar.battleTowers.common.Items;
import com.github.draylar.battleTowers.common.entity.TowerGuardEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.World;

public class BossLockBlock extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public BossLockBlock() {
        super(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().build());
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerLookDirection());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int requiredCount = BattleTowers.CONFIG.requiredKeys;
        Item requiredItem = Registry.ITEM.get(new Identifier(BattleTowers.CONFIG.requiredItem));
        if (requiredItem == null) requiredItem = Items.KEY;

        ItemStack stack = player.inventory.main.get(player.inventory.selectedSlot);

        if (stack.getItem().equals(requiredItem) && stack.getCount() >= requiredCount) {
            stack.setCount(stack.getCount() - requiredCount);

            world.setBlockState(pos, Blocks.AIR.getDefaultState());

            TowerGuardEntity towerGuardEntity = new TowerGuardEntity(Entities.TOWER_GUARD, world);

            towerGuardEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
            world.spawnEntity(towerGuardEntity);


            if (world.isClient) {
                playSpawnSound(pos);
            }
        } else {
            if (world.isClient) {
                playDenySound(pos);
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }


    @Environment(EnvType.CLIENT)
    private void playDenySound(BlockPos pos) {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 1, 1, false);
    }

    @Environment(EnvType.CLIENT)
    private void playSpawnSound(BlockPos pos) {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1, 1, false);
    }
}
