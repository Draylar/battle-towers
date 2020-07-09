package draylar.battletowers.mixin;

import draylar.battletowers.api.spawner.MobSpawnerAccessor;
import draylar.battletowers.registry.BattleTowerItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin extends Block {

    public SpawnerBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            MobSpawnerBlockEntity be = (MobSpawnerBlockEntity) world.getBlockEntity(pos);

            if (((MobSpawnerAccessor) be).isTowerSpawner()) {
                ItemScatterer.spawn(be.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BattleTowerItems.BOSS_KEY));
            }
        }

        super.onBreak(world, pos, state, player);
    }
}
