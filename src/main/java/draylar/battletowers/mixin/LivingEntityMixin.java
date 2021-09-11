package draylar.battletowers.mixin;

import draylar.battletowers.entity.TowerGuardianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract DamageTracker getDamageTracker();

    @Redirect(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageTracker;update()V")
    )
    private void cancelTowerGuardianDamageTrackerUpdate(DamageTracker damageTracker) {
        if (!((Object) this instanceof TowerGuardianEntity)) {
            this.getDamageTracker().update();
        }
    }

    @Inject(
            method = "onDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V")
    )
    private void updateTowerGuardianDamageTracker(DamageSource source, CallbackInfo ci) {
        if ((Object) this instanceof TowerGuardianEntity) {
            this.getDamageTracker().update();
        }
    }
}
