package com.github.draylar.battleTowers.common.blocks.boss_lock;

import com.github.draylar.battleTowers.common.Items;
import com.github.draylar.battleTowers.config.ConfigHolder;
import com.github.draylar.battleTowers.common.entity.tower_guard.TowerGuardEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.PositionedSoundInstance;
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
    public static final DirectionProperty FACING = HorizontalFacingBlock.field_11177;

    public BossLockBlock()
    {
        super(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().build());
        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerHorizontalFacing());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1)
    {
        stateFactory$Builder_1.with(new Property[]{FACING});
    }

    @Override
    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1)
    {
        int requiredCount = ConfigHolder.configInstance.requiredKeys;
        Item requiredItem = Registry.ITEM.get(new Identifier(ConfigHolder.configInstance.requiredItem));
        if(requiredItem == null) requiredItem = Items.bossKeyItem;

        ItemStack stack = playerEntity_1.inventory.main.get(playerEntity_1.inventory.selectedSlot);

        if(stack.getItem().equals(requiredItem) && stack.getAmount() >= requiredCount)
        {
            stack.setAmount(stack.getAmount() - requiredCount);

            world_1.setBlockState(blockPos_1, Blocks.AIR.getDefaultState());

            TowerGuardEntity towerGuardEntity = new TowerGuardEntity(world_1);
            towerGuardEntity.setPosition(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
            world_1.spawnEntity(towerGuardEntity);

            if(world_1.isClient)
            {
                MinecraftClient.getInstance().getSoundLoader().play(PositionedSoundInstance.master(SoundEvents.ENTITY_WITHER_SPAWN, 1.0f));
            }
        }

        else
            if(world_1.isClient)
                MinecraftClient.getInstance().world.playSound(blockPos_1, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCK, 1, 1, false);

        return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
    }
}
