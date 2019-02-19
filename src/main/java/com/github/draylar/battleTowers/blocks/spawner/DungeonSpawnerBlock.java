package com.github.draylar.battleTowers.blocks.spawner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DungeonSpawnerBlock extends BlockWithEntity
{
    public DungeonSpawnerBlock()
    {
        super(FabricBlockSettings.of(Material.ANVIL).hardness(2).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1)
    {
        return new DungeonSpawnerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1) {
        return ItemStack.EMPTY;
    }
}
