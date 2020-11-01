package draylar.battletowers.mixin;

import draylar.battletowers.api.spawner.SpawnerManipulator;
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

    /**
     * Configures behavior for vanilla Spawners dropping keys when broken.
     * <p>
     * Spawner BEs store information on whether they should drop keys through {@link SpawnerManipulator}.
     *
     * @param world   world the break is occurring inside
     * @param pos     position the break is occurring at
     * @param state   state being broken
     * @param player  player doing the break
     */
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            MobSpawnerBlockEntity be = (MobSpawnerBlockEntity) world.getBlockEntity(pos);
            assert be != null;

            if (((SpawnerManipulator) be).isTowerSpawner()) {
                ItemScatterer.spawn(be.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BattleTowerItems.BOSS_KEY));
            }
        }

        super.onBreak(world, pos, state, player);
    }
}
