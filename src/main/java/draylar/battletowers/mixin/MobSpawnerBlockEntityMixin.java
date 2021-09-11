package draylar.battletowers.mixin;

import draylar.battletowers.api.spawner.SpawnerManipulator;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawnerBlockEntity.class)
public class MobSpawnerBlockEntityMixin implements SpawnerManipulator {

    private boolean isTowerSpawner = false;

    @Inject(
            method = "writeNbt",
            at = @At("HEAD")
    )
    private void toTag(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cir) {
        tag.putBoolean("IsTowerSpawner", isTowerSpawner);
    }

    @Inject(
            method = "readNbt",
            at = @At("HEAD")
    )
    private void fromTag(NbtCompound tag, CallbackInfo ci) {
        this.isTowerSpawner = tag.getBoolean("IsTowerSpawner");
    }

    @Override
    public boolean isTowerSpawner() {
        return isTowerSpawner;
    }

    @Override
    public void setTowerSpawner(boolean val) {
        this.isTowerSpawner = val;
    }
}
