package draylar.battletowers.mixin;

import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {

    @Redirect(
            method = "readCustomDataFromTag",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/EndermanEntity;angerFromTag(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/CompoundTag;)V")
    )
    private void patchEndermanSpawner(EndermanEntity entity, ServerWorld world, CompoundTag tag) {
        if(!entity.world.isClient) {
            entity.angerFromTag((ServerWorld) entity.world, tag);
        }
    }
}
