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
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BossLockBlock extends Block
{
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public BossLockBlock()
    {
        super(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().build());
        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerLookDirection());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1)
    {
        stateFactory$Builder_1.add(new Property[]{FACING});
    }

    @Override
    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1)
    {
        int requiredCount = BattleTowers.CONFIG.requiredKeys;
        Item requiredItem = Registry.ITEM.get(new Identifier(BattleTowers.CONFIG.requiredItem));
        if(requiredItem == null) requiredItem = Items.KEY;

        ItemStack stack = playerEntity_1.inventory.main.get(playerEntity_1.inventory.selectedSlot);

        if(stack.getItem().equals(requiredItem) && stack.getCount() >= requiredCount)
        {
            stack.setCount(stack.getCount() - requiredCount);

            world_1.setBlockState(blockPos_1, Blocks.AIR.getDefaultState());

            TowerGuardEntity towerGuardEntity = new TowerGuardEntity(Entities.TOWER_GUARD, world_1);
            towerGuardEntity.setPosition(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
            world_1.spawnEntity(towerGuardEntity);

            if(world_1.isClient)
            {
                playSpawnSound(blockPos_1);
            }
        }

        else
        {
            if(world_1.isClient)
            {
                playDenySound(blockPos_1);
            }
        }


        return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
    }

    @Environment(EnvType.CLIENT)
    private void playDenySound(BlockPos pos)
    {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS, 1, 1, false);
    }

    @Environment(EnvType.CLIENT)
    private void playSpawnSound(BlockPos pos)
    {
        MinecraftClient.getInstance().world.playSound(pos, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1, 1, false);
    }
}
