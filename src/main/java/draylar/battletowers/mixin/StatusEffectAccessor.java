package draylar.battletowers.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Invoker
    static StatusEffect createStatusEffect(StatusEffectType type, int color) {
        throw new UnsupportedOperationException();
    }
}
