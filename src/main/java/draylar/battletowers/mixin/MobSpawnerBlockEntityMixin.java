package draylar.battletowers.mixin;

import draylar.battletowers.api.spawner.SpawnerManipulator;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawnerBlockEntity.class)
public class MobSpawnerBlockEntityMixin implements SpawnerManipulator {

    private boolean isTowerSpawner = false;

    @Inject(
            method = "toTag",
            at = @At("HEAD")
    )
    private void toTag(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        tag.putBoolean("IsTowerSpawner", isTowerSpawner);
    }

    @Inject(
            method = "fromTag",
            at = @At("HEAD")
    )
    private void fromTag(BlockState state, CompoundTag tag, CallbackInfo ci) {
        this.isTowerSpawner = tag.getBoolean("IsTowerSpawner");
    }

    @Override
    public void setTowerSpawner(boolean val) {
        this.isTowerSpawner = val;
    }

    @Override
    public boolean isTowerSpawner() {
        return isTowerSpawner;
    }
}
