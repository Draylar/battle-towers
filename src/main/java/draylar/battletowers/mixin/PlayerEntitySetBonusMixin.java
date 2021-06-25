package draylar.battletowers.mixin;

import draylar.battletowers.item.KeyArmorItem;
import draylar.battletowers.registry.BattleTowerStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntitySetBonusMixin extends LivingEntity {

    @Shadow
    @Final
    public PlayerInventory inventory;

    protected PlayerEntitySetBonusMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void onTick(CallbackInfo ci) {
        if (!world.isClient && age % 20 == 0) {
            // Ensure all armor stacks are key armor
            for (ItemStack armor : inventory.armor) {
                if (!(armor.getItem() instanceof KeyArmorItem)) {
                    return;
                }
            }

            // Apply effects
            this.addStatusEffect(new StatusEffectInstance(BattleTowerStatusEffects.GUARDIAN_CONQUEROR, 2 * 20, 0, true, true));
        }
    }
}
